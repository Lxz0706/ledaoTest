package com.ledao.activity.service.impl;

import java.util.*;

import com.ledao.activity.controller.ProcessDefinitionController;
import com.ledao.activity.service.ISysDocumentFileService;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.StringUtils;
import com.ledao.system.dao.SysRole;
import com.ledao.system.mapper.SysDocumentMapper;
import com.ledao.system.mapper.SysRoleMapper;
import com.ledao.system.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserMapper userMapper;
    
    @Autowired
    private SysDocumentFileMapper documentFileMapper;
    
    @Autowired
    private SysFileDetailMapper fileDetailMapper;

    @Autowired
    private ISysDocumentFileService sysDocumentFileService;

    private static final Logger log = LoggerFactory.getLogger(SysApplyInServiceImpl.class);

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
                    //没有直接审批人，转到档案管理员下
                    List<String> jls = getUsers("documentAdmin");
                    users.addAll(jls);
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

                List<SysRole> rflgw = ShiroUtils.getSysUser().getRoles();
                List<String> ids = new ArrayList<>();
                for (SysRole r:rflgw) {
                    ids.add(r.getRoleKey());
                }
//                当前操作人为法律顾问
                if(ids.contains("flgw")){
                    //发给总经理
                    List<String> jls = getUsers("zjl");
                    users.addAll(jls);
                }else if(ids.contains("zjl")){
                    List<String> jls = getUsers("documentAdmin");
                    users.addAll(jls);
                }else{
                    //判断是否存在直接主管
                    if (StringUtils.isNotEmpty(sysUser.getDirector()) && StringUtils.isNotEmpty(sysUser.getDirectorId().toString())) {
                        users.add(sysUser.getDirector());
                        //                    SysUser sysUser1 = userMapper.selectUserById(sysUser.getDirectorId());
                        //判断是否存在二级主管
                    /*if (StringUtils.isNotEmpty(sysUser1.getDirector()) && StringUtils.isNotEmpty(sysUser1.getDirectorId().toString())) {
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
                            *//*if ("否".equals(entity.getDocumentRevertFlag())) {
                                key = "document_ck_sjzgNo";
                            } else {
                                key = "document_ck_sjzg";
                            }*//*
                        } else {

                            variables.put("zgUser", sysUser.getLoginName());
                            variables.put("ejzgUser", sysUser1.getLoginName());
                            users.add(sysUser.getLoginName());
                            users.add(sysUser1.getLoginName());

                           *//* if ("否".equals(entity.getDocumentRevertFlag())) {
                                key = "document_ck_ejzgNo";
                            } else {
                                key = "document_ck_ejzg";
                            }*//*
                        }
                    } else {
                        variables.put("zgUser", sysUser1.getLoginName());
                       *//* if ("N".equals(entity.getDocumentRevertFlag())) {
                            key = "document_zjzgNo";
                        } else {
                            key = "document_zjzg";
                        }*//*
                    }*/
                    } else {
                    /*if ("否".equals(entity.getDocumentRevertFlag())) {
                        key = "document_ghNo";
                    } else {
                        key = "document_gh";
                    }*/
                        //没有直属领导，直接提交给法律顾问
                        //没有直接审批人，转到档案管理员下
                        //没有直接审批人，转到档案管理员下
                        List<String> jls = getUsers("flgw");
                        users.addAll(jls);
                    }
                }


            }
        }
        return users;
    }

    private List<String> getUsers(String roleKey){
        List<String> users = new ArrayList<>();
        SysRole r = new SysRole();
        r.setRoleKey(roleKey);
        List<SysRole> ros = roleMapper.selectRoleList(r);
        if (ros!=null && ros.size()>0){
            SysUser userRoles = new SysUser();
            userRoles.setRoleId(ros.get(0).getRoleId());
            List<SysUser> us =  userMapper.selectAllocatedList(userRoles);
            for (SysUser u: us) {
                users.add(u.getLoginName());
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
    public AjaxResult deleteSysApplyInByIds(String ids)
    {
        String[] idsArr = Convert.toStrArray(ids);
        List<SysApplyIn> apps = sysApplyInMapper.selectSysApplyInListByIds(idsArr);
        String[] applyStatusList = {"0","4"};
        String delDocumentIds = "";
        for (SysApplyIn a:apps) {
            // 可提交审批的状态     0保存；4撤回;
            if (!Arrays.asList(applyStatusList).contains(a.getApproveStatu()) || !a.getApplyUser().equals(ShiroUtils.getLoginName())){
                return AjaxResult.error("存在不可删除的申请");
            }
            SysDocumentFile d = new SysDocumentFile();
            d.setApplyId(a.getApplyId());
            List<SysDocumentFile> documentFiles = documentFileMapper.selectSysDocumentFileList(d);
            for (SysDocumentFile f:documentFiles) {
                delDocumentIds = delDocumentIds+String.valueOf(f.getDocumentId())+",";
            }
        }
        if (StringUtils.isNotEmpty(delDocumentIds)){
            sysDocumentFileService.deleteSysDocumentFileByIds(delDocumentIds.substring(0,delDocumentIds.length()-1));
        }
        sysApplyInMapper.deleteSysApplyInByIds(idsArr);
        return AjaxResult.success();
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
        log.info("开始调用工作流，审批状态{}",sysApplyIn.getApproveStatu());
        SysUser currentUser = ShiroUtils.getSysUser();
        String loginUser = currentUser.getLoginName();
        SysApplyIn sysApplyInEntity = sysApplyInMapper.selectSysApplyInById(sysApplyIn.getApplyId());
        if (sysApplyInEntity==null || sysApplyInEntity.getApplyId()==null){
            return AjaxResult.error("无该申请");
        }
        SysApplyWorkflow workflow = new SysApplyWorkflow();
        //提交审批，寻找下一审批人
        if("5".equals(sysApplyIn.getApproveStatu())) {
            String[] applyStatusList = {"0","4","2"};
            // 可提交审批的状态     0保存；4撤回；2拒绝
            if (!Arrays.asList(applyStatusList).contains(sysApplyInEntity.getApproveStatu())){
                return AjaxResult.error("非待审批状态");
            }
            if (!sysApplyInEntity.getCreateBy().equals(loginUser)){
                return AjaxResult.error("非创建人无法提交审批");
            }
            SysDocumentFile sysDocumentFile = new SysDocumentFile();
            sysDocumentFile.setApplyId(sysApplyIn.getApplyId());
            List<SysDocumentFile> doc = documentFileMapper.selectSysDocumentFileList(sysDocumentFile);
            if (doc==null || doc.size()==0){
                return AjaxResult.error("无档案明细，请添加档案");
            }
            List<String> users = getApplyNextUser(sysApplyIn);
            sysApplyInEntity.setApproveUser(String.join(",",users));
            sysApplyInEntity.setApproveStatu(sysApplyIn.getApproveStatu());
        }
        //撤回
        if("4".equals(sysApplyIn.getApproveStatu())) {
            if (!sysApplyInEntity.getCreateBy().equals(loginUser)){
                return AjaxResult.error("非创建人无法撤回");
            }
            if(!"5".equals(sysApplyInEntity.getApproveStatu())){
                return AjaxResult.error("该申请不可撤回");
            }
            sysApplyInEntity.setApproveStatu(sysApplyIn.getApproveStatu());
            sysApplyInEntity.setApproveUser("");
        }
        //审批拒绝，回到申请人
        if("2".equals(sysApplyIn.getApproveStatu())){
            sysApplyInEntity.setApproveUser("");
            sysApplyInEntity.setApproveStatu(sysApplyIn.getApproveStatu());
            workflow.setRemarks(sysApplyIn.getRemarks());
            sysApplyInEntity.setRemarks(null);

        }
        //同意审批
        if("6".equals(sysApplyIn.getApproveStatu())){
            List<SysRole> ros = currentUser.getRoles();
            List<String> ids = new ArrayList<>();
            for (SysRole r:ros) {
                ids.add(r.getRoleKey());
            }
            if(ids.contains("documentAdmin")){
                //判断所有的档案状态是否时”在库“，是才能完成，否则无法完成
                SysDocumentFile df = new SysDocumentFile();
                df.setApplyId(sysApplyIn.getApplyId());
                List<SysDocumentFile> doc = documentFileMapper.selectSysDocumentFileList(df);
                for(SysDocumentFile d : doc){
                    if (!"1".equals(d.getDocumentStatu())){
                        return AjaxResult.error("存在档案未在库，无法完成审批");
                    }
                }
                sysApplyIn.setApproveStatu("3");
                sysApplyInEntity.setApproveStatu("3");
                sysApplyInEntity.setApproveUser("");
            }else{
                //1表示审批中
                List<String> users = getApplyNextUser(sysApplyIn);
                sysApplyInEntity.setApproveUser(String.join(",",users));
                sysApplyInEntity.setApproveStatu("1");
            }
        }
        sysApplyInEntity.setUpdateTime(new Date());
        sysApplyInMapper.updateSysApplyIn(sysApplyInEntity);
        workflow.setApplyId(sysApplyIn.getApplyId());
        workflow.setApproveStatu(sysApplyIn.getApproveStatu());
        workflow.setApproveUser(loginUser);
        workflow.setCreateBy(loginUser);
        workflow.setCreateTime(new Date());
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
