package com.ledao.activity.service.impl;

import java.util.*;

import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.StringUtils;
import com.ledao.system.mapper.SysDocumentMapper;
import com.ledao.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ledao.activity.dao.SysApplyIn;
import com.ledao.activity.dao.SysApplyWorkflow;
import com.ledao.activity.dao.SysDocumentFile;
import com.ledao.activity.dao.SysFileDetail;
import com.ledao.activity.mapper.SysApplyInMapper;
import com.ledao.activity.mapper.SysApplyWorkflowMapper;
import com.ledao.activity.mapper.SysDocumentFileMapper;
import com.ledao.activity.mapper.SysFileDetailMapper;
import com.ledao.activity.service.ISysApplyInService;
import com.ledao.common.core.text.Convert;
import com.ledao.common.utils.DateUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysUser;

/**
 * 档案入库申请Service业务层处理
 * 
 * @author lxz
 * @date 2021-08-04
 */
@Service
@Transactional
public class SysApplyInServiceImpl implements ISysApplyInService 
{
    @Autowired
    private SysApplyInMapper sysApplyInMapper;
    
    @Autowired
    private SysApplyWorkflowMapper sysApplyWorkflowMapper;

    @Autowired
    private SysUserMapper userMapper;
    
    @Autowired
    private SysDocumentFileMapper documentFileMapper;
    
    @Autowired
    private SysFileDetailMapper fileDetailMapper;

    /**
     * 查询档案入库申请
     * 
     * @param applyId 档案入库申请ID
     * @return 档案入库申请
     */
    @Override
    public SysApplyIn selectSysApplyInById(Long applyId)
    {
    	SysApplyIn applyIn = sysApplyInMapper.selectSysApplyInById(applyId);
    	SysDocumentFile documentFile = new SysDocumentFile();
    	documentFile.setApplyId(applyId);
    	List<SysDocumentFile> documentFiles = documentFileMapper.selectSysDocumentFileList(documentFile);
    	for (SysDocumentFile sysDocumentFile : documentFiles) {
			SysFileDetail detail = new SysFileDetail();
			detail.setDocumentFileId(sysDocumentFile.getDocumentId());
			List<SysFileDetail> details = fileDetailMapper.selectSysFileDetailList(detail);
			sysDocumentFile.setFileDetails(details);
		}
    	applyIn.setDocumentFiles(documentFiles);
        return applyIn;
    }

    /**
     * 查询档案入库申请列表
     * 
     * @param sysApplyIn 档案入库申请
     * @return 档案入库申请
     */
    @Override
    public List<SysApplyIn> selectSysApplyInList(SysApplyIn sysApplyIn)
    {
        return sysApplyInMapper.selectSysApplyInList(sysApplyIn);
    }

    /**
     * 新增档案入库申请
     * 
     * @param sysApplyIn 档案入库申请
     * @return 结果
     */
    @Override
    public int insertSysApplyIn(SysApplyIn sysApplyIn)
    {
    	return sysApplyInMapper.insertSysApplyIn(sysApplyIn);
    }

    public List<String> getApplyNextUser(SysApplyIn sysApplyIn){
        Map<String, Object> variables = new HashMap<>();
        String key = "";
        String itemName = "";
        SysUser sysUser = ShiroUtils.getSysUser();
        List<String> users = new ArrayList<>();
        if (StringUtils.isNotNull(sysUser)) {
            //入库申请
            if ("0".equals(sysApplyIn.getApplyType())) {
                //根据提交人查询是否存在直接主管
                if (StringUtils.isNotEmpty(sysUser.getDirector()) && StringUtils.isNotEmpty(sysUser.getDirectorId().toString())) {
                    key = "document_rk_zg";
                    SysUser sysUser1 = userMapper.selectUserById(sysUser.getDirectorId());
                    //动态设置审批人员
                    variables.put("userId", sysUser1.getLoginName());
                    users.add(sysUser1.getLoginName());
                } else {
                    key = "document_rk_zgNo";
                }
                itemName = sysApplyIn.getCreator() + "提交的入库申请";
            } else {
                itemName = sysApplyIn.getCreator() + "提交的出库申请";

                List<String> userList = new ArrayList<>();
                userList.add("yangxu");
                userList.add("yangxudong");
                variables.put("assigneeList", userList);
                users.add("yangxu");
                users.add("yangxudong");

                //判断是否存在直接主管
                if (StringUtils.isNotEmpty(sysUser.getDirector()) && StringUtils.isNotEmpty(sysUser.getDirectorId().toString())) {
                    SysUser sysUser1 = userMapper.selectUserById(sysUser.getDirectorId());
                    //判断是否存在二级主管
                    if (StringUtils.isNotEmpty(sysUser1.getDirector()) && StringUtils.isNotEmpty(sysUser1.getDirectorId().toString())) {
                        //判断是否存在三级主管
                        SysUser sysUser2 = userMapper.selectUserById(sysUser1.getDirectorId());
                        if (StringUtils.isNotEmpty(sysUser2.getDirectorId().toString()) && StringUtils.isNotEmpty(sysUser2.getDirector())) {
                            SysUser sysUser3 = userMapper.selectUserById(sysUser2.getDirectorId());
                            variables.put("zgUser", sysUser1.getLoginName());
                            variables.put("ejzgUser", sysUser2.getLoginName());
                            variables.put("sjzgUser", sysUser3.getLoginName());
                            users.add(sysUser1.getLoginName());
                            users.add(sysUser2.getLoginName());
                            users.add(sysUser3.getLoginName());
                            /*if ("否".equals(entity.getDocumentRevertFlag())) {
                                key = "document_ck_sjzgNo";
                            } else {
                                key = "document_ck_sjzg";
                            }*/
                        } else {

                            variables.put("zgUser", sysUser.getLoginName());
                            variables.put("ejzgUser", sysUser1.getLoginName());
                            users.add(sysUser.getLoginName());
                            users.add(sysUser1.getLoginName());

                           /* if ("否".equals(entity.getDocumentRevertFlag())) {
                                key = "document_ck_ejzgNo";
                            } else {
                                key = "document_ck_ejzg";
                            }*/
                        }
                    } else {
                        variables.put("zgUser", sysUser1.getLoginName());
                       /* if ("N".equals(entity.getDocumentRevertFlag())) {
                            key = "document_zjzgNo";
                        } else {
                            key = "document_zjzg";
                        }*/
                    }
                } else {
                    /*if ("否".equals(entity.getDocumentRevertFlag())) {
                        key = "document_ghNo";
                    } else {
                        key = "document_gh";
                    }*/
                }
            }
        }
        return users;
    }

    /**
     * 修改档案入库申请
     * 
     * @param sysApplyIn 档案入库申请
     * @return 结果
     */
    @Override
    public int updateSysApplyIn(SysApplyIn sysApplyIn)
    {
        sysApplyIn.setUpdateTime(DateUtils.getNowDate());
        return sysApplyInMapper.updateSysApplyIn(sysApplyIn);
    }

    /**
     * 删除档案入库申请对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysApplyInByIds(String ids)
    {
        return sysApplyInMapper.deleteSysApplyInByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除档案入库申请信息
     * 
     * @param applyId 档案入库申请ID
     * @return 结果
     */
    @Override
    public int deleteSysApplyInById(Long applyId)
    {
        return sysApplyInMapper.deleteSysApplyInById(applyId);
    }

    /**
     * 查询自己的工作流
     */
	@Override
	public List<SysApplyIn> listDownByMe(String username) {
		return sysApplyInMapper.listDownByMe(username);
	}

    @Override
    public AjaxResult applyEditSave(SysApplyIn sysApplyIn) {
        SysUser currentUser = ShiroUtils.getSysUser();
        String loginUser = currentUser.getLoginName();
        SysApplyIn sysApplyInEntity = sysApplyInMapper.selectSysApplyInById(sysApplyIn.getApplyId());
        if (sysApplyInEntity==null || sysApplyInEntity.getApplyId()==null){
            return AjaxResult.error("无该申请");
        }
        //审批通过，寻找下一审批人
        if("5".equals(sysApplyIn.getApproveStatu())) {
            if (!"0".equals(sysApplyInEntity.getApproveStatu())){
                return AjaxResult.error("非待审批状态");
            }
            if (!sysApplyInEntity.getCreateBy().equals(loginUser)){
                return AjaxResult.error("非创建人无法提交审批");
            }
            List<String> users = getApplyNextUser(sysApplyIn);
            sysApplyInEntity.setApproveUser(String.join(",",users));
        }
        //撤回
        if("4".equals(sysApplyIn.getApproveStatu())) {
            if (!sysApplyInEntity.getCreateBy().equals(loginUser)){
                return AjaxResult.error("非创建人无法撤回");
            }
            if(!"5".equals(sysApplyInEntity.getApproveStatu())){
                return AjaxResult.error("无法撤回");
            }
        }
        //审批拒绝，回到申请人
        if("2".equals(sysApplyIn.getApproveStatu())){
            sysApplyInEntity.setApproveUser(sysApplyIn.getApplyUser());
        }
        sysApplyInEntity.setUpdateTime(new Date());
        sysApplyInEntity.setApproveStatu(sysApplyIn.getApproveStatu());
        sysApplyInMapper.updateSysApplyIn(sysApplyInEntity);
        SysApplyWorkflow workflow = new SysApplyWorkflow();
        workflow.setApplyId(sysApplyIn.getApplyId());
        workflow.setApproveStatu(sysApplyIn.getApproveStatu());
        workflow.setApproveUser(loginUser);
        workflow.setCreateBy(loginUser);
        sysApplyWorkflowMapper.insertSysApplyWorkflow(workflow);
        return AjaxResult.success();
    }

    @Override
    public List<SysApplyIn> listUnDownByMe(String username) {
        return sysApplyInMapper.listUnDownByMe(username);
    }

    @Override
    public int editSave(SysApplyIn sysApplyIn) {
        sysApplyIn.setUpdateTime(new Date());
	    return sysApplyInMapper.updateSysApplyIn(sysApplyIn);
    }
}
