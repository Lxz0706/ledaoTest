package com.ledao.activity.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.ledao.activity.dao.*;
import com.ledao.activity.mapper.*;
import com.ledao.activity.service.*;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.system.dao.*;
import com.ledao.system.mapper.*;
import com.ledao.system.service.ISysConfigService;
import com.ledao.system.service.ISysDictDataService;
import com.ledao.system.service.impl.SysConfigServiceImpl;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ledao.common.core.text.Convert;
import com.ledao.common.utils.DateUtils;
import com.ledao.framework.util.ShiroUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    private SysApplyOutDetailMapper sysApplyOutDetailMapper;

    @Autowired
    private SysZckMapper SysZckMapper;

    @Autowired
    private IProcessService processService;

    @Autowired
    private ISysApplyWorkflowService iSysApplyWorkflowService;

    @Autowired
    private IBizTodoItemService bizTodoItemService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SysZcbMapper sysZcbMapper;
    @Autowired
    private SysZckMapper sysZckMapper;

    @Autowired
    private SysProjectZckMapper sysProjectZckMapper;

    @Autowired
    private SysProjectMapper sysProjectMapper;

    @Autowired
    private SysBgczzckMapper sysBgczzckMapper;

    @Autowired
    private ISysConfigService configService;


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
        SysUser u = userMapper.selectUserByLoginName(applyIn.getApplyUser());
        applyIn.setApplyUserName(u.getUserName());
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
            SysApplyIn a = sysApplyInMapper.selectSysApplyInById(sysApplyIn.getApplyId());
            if ("0".equals(sysApplyIn.getApplyType())) {
                /*if ("5".equals(sysApplyIn.getApproveStatu())){
                    if (StringUtils.isNotEmpty(a.getRealCreateBy())){
                        sysUser = userMapper.selectUserByLoginName(a.getRealCreateBy());
                    }
                }*/
                //获取申请人的个人信息
                sysUser = userMapper.selectUserByLoginName(a.getApplyUser());
                //根据提交人查询是否存在直接主管
                if (StringUtils.isNotEmpty(sysUser.getDirector()) && StringUtils.isNotEmpty(sysUser.getDirectorId().toString()) && "5".equals(sysApplyIn.getApproveStatu())) {
                    key = "document_rk_zg";
                    SysUser sysUser1 = userMapper.selectUserById(sysUser.getDirectorId());
                    //动态设置审批人员
                    variables.put("userId", sysUser1.getLoginName());
                    users.add(sysUser1.getLoginName());
                } else {
                    //没有直接审批人，转到档案管理员下
                    List<String> jls = getUsers("documentAdmin");
                    List<String> js = new ArrayList<>();
                    for (String n: jls){
                        if (!n.equals(a.getApplyUser()) && !n.equals(a.getRealCreateBy())){
                            js.add(n);
                        }
                    }
                    users.addAll(js);
                    key = "document_rk_zgNo";
                }
                itemName = sysApplyIn.getCreator() + "提交的入库申请";
            } else {
                itemName = sysApplyIn.getCreator() + "提交的出库申请";

                List<String> userList = new ArrayList<>();
                userList.add("yangxu");
                userList.add("yangxudong");
                variables.put("assigneeList", userList);

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
                    boolean isNormalWork = false;
                    SysApplyWorkflow workflow = new SysApplyWorkflow();
                    workflow.setApplyId(sysApplyIn.getApplyId());
                    List<SysApplyWorkflow> workflows = sysApplyWorkflowMapper.selectSysApplyWorkflowList(workflow);
                    if (workflows!=null && workflows.size()>0){
                        SysUser u = userMapper.selectUserByLoginName(workflows.get(0).getApproveUser());
                        for (SysRole r:u.getRoles()) {
                            if ("flgw".equals(r.getRoleKey())){
                                isNormalWork = true;
                            }
                        }
                    }
                    if (isNormalWork){
                        List<String> jls = getUsers("documentAdmin");
                        List<String> js = new ArrayList<>();
                        for (String n: jls){
                            if (!n.equals(a.getApplyUser()) && !n.equals(a.getRealCreateBy())){
                                js.add(n);
                            }
                        }
                        users.addAll(js);
                    }else{
                        List<String> jls = getUsers("flgw");
                        users.addAll(jls);
                    }
                }else{
                    //判断是否存在直接主管
                    if (StringUtils.isNotEmpty(a.getRealCreateBy()) && "0".equals(sysApplyIn.getApproveStatu())){
                        sysUser = userMapper.selectUserByLoginName(a.getApplyUser());
                    }
                    if (StringUtils.isNotEmpty(sysUser.getDirector()) && StringUtils.isNotEmpty(sysUser.getDirectorId().toString())) {
                        SysUser sysUser1 = userMapper.selectUserById(sysUser.getDirectorId());
                        users.add(sysUser1.getLoginName());
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
            if (!Arrays.asList(applyStatusList).contains(a.getApproveStatu())){
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
	public List<SysApplyIn> listDownByMe(SysApplyIn sysApplyIn) {
		return sysApplyInMapper.listDownByMe(sysApplyIn);
	}

	public AjaxResult submitApply(SysApplyIn sysApplyInEntity,SysApplyIn sysApplyIn){
	    String loginUser = ShiroUtils.getLoginName();
        String[] applyStatusList = {"0","4","2"};
        // 可提交审批的状态     0保存；4撤回；2拒绝
        if (!Arrays.asList(applyStatusList).contains(sysApplyInEntity.getApproveStatu())){
            return AjaxResult.error("非待审批状态");
        }
        if ((sysApplyInEntity.getCreateBy().equals(loginUser) && "0".equals(sysApplyInEntity.getApproveStatu()))||
                (!"0".equals(sysApplyInEntity.getApproveStatu())&& sysApplyInEntity.getApplyUser().equals(loginUser)) ){
        }else{
            return AjaxResult.error("非创建人无法提交审批");
        }
        if ("1".equals(sysApplyIn.getApplyType())){
            //如果是出库申请
            SysApplyOutDetail detail = new SysApplyOutDetail();
            detail.setApplyId(sysApplyIn.getApplyId());
            List<SysApplyOutDetail> des = sysApplyOutDetailMapper.selectSysApplyOutDetailList(detail);
            if (des!=null && des.size()>0){
                for (SysApplyOutDetail d:des){
                    /////////////
                    if (StringUtils.isEmpty(d.getIsElec())){
                        return AjaxResult.error("档案借出信息填写不完整，请补充之后提交");
                    }
                }
            }else{
                return AjaxResult.error("无借出档案明细，请添加档案");
            }
        }else{
            //入库申请
            SysDocumentFile sysDocumentFile = new SysDocumentFile();
            sysDocumentFile.setApplyId(sysApplyIn.getApplyId());
            List<SysDocumentFile> doc = documentFileMapper.selectSysDocumentFileList(sysDocumentFile);
            if (doc==null || doc.size()==0){
                return AjaxResult.error("无档案明细，请添加档案");
            }else{
                for (SysDocumentFile d : doc){
                    SysFileDetail sysFileDetail = new SysFileDetail();
                    sysFileDetail.setDocumentFileId(d.getDocumentId());
                    List<SysFileDetail> fs = fileDetailMapper.selectSysFileDetailList(sysFileDetail);
                    if (fs==null ||fs.size()==0){
                        return AjaxResult.error("存在档案无附件");
                    }
                }
            }
        }
//        List<String> users = getApplyNextUser(sysApplyIn);
        List<String> users = submitApplyInfo(sysApplyIn);
        sysApplyInEntity.setApplyTime(DateUtils.getNowDate());
        sysApplyInEntity.setApproveUser(String.join(",",users));
        sysApplyInEntity.setApproveStatu(sysApplyIn.getApproveStatu());
        sysApplyInEntity.setUpdateTime(new Date());
        sendMsg(users,sysApplyInEntity);
        sysApplyInMapper.updateSysApplyIn(sysApplyInEntity);
        return AjaxResult.success();
    }

    public void sendMsg(List<String> users,SysApplyIn sysApplyInEntity){
        for (String u : users){
            SysUser us = userMapper.selectUserByLoginName(u);
            String appName = "";
            if ("0".equals(sysApplyInEntity.getApplyType())){
                appName = "档案入库申请";
            }else{
                appName = "档案出库申请";
            }
            if (us!=null && StringUtils.isNotEmpty(us.getOpenId())){

                String first = "";
                if ("2".equals(sysApplyInEntity.getApproveStatu())){
                    first = "您的申请被拒绝";
                }else if ("3".equals(sysApplyInEntity.getApproveStatu())){
                    first = "您提交的申请已处理";
                } else{
                    first = "您有一条流程需要审批";
                }
                JSONObject parm = new JSONObject();
                parm.put("first",first);
                parm.put("toUser",us.getComOpenId());
                parm.put("word1",appName);
                parm.put("word2",dictDataService.selectDictLabel("apply_statu",sysApplyInEntity.getApproveStatu()));
                parm.put("word3",sysApplyInEntity.getApplyTime());
                String thing14 = "-";
                if (StringUtils.isNotEmpty(sysApplyInEntity.getRemarks())){
                    thing14 = sysApplyInEntity.getRemarks();
                }
//                thing14 = "申请测试申请测试申请测试申请测试申请测试申请测试申请测试申请测试申请测试申请测试申请测试申请测试申请测试申请测试申请测试申请测试申请测试申请测试申请测试申请测试申请测试申请测试申请测试申请测试请测试";
                parm.put("word5",thing14);
                String accessToken = configService.getWechatComAccessToken();
                parm.put("accessToken",accessToken);
                iSysApplyWorkflowService.sendLittleMsg(parm);
            }
        }
//        完成
        /*if ("3".equals(sysApplyInEntity.getApproveStatu())){
            SysUser us = userMapper.selectUserByLoginName(sysApplyInEntity.getApplyUser());
            JSONObject parm = new JSONObject();
            parm.put("toUser",us.getOpenId());
//            parm.put("thing6",appName);
            parm.put("thing4",userMapper.selectUserByLoginName(sysApplyInEntity.getApplyUser()).getUserName());
            parm.put("time8",DateUtils.getNowDate());
            parm.put("thing7",us.getUserName());
            parm.put("time5",DateUtils.getNowDate());
            iSysApplyWorkflowService.sendLittleMsg(parm);
        }*/
    }

    public List<String> submitApplyInfo(SysApplyIn sysApplyIn){
            Map<String, Object> variables = new HashMap<>();
            String key = "";
            String itemName = "";
            SysUser sysUser = ShiroUtils.getSysUser();
            List<String> users = new ArrayList<>();
                //入库申请
                SysApplyIn a = sysApplyInMapper.selectSysApplyInById(sysApplyIn.getApplyId());
                if ("0".equals(sysApplyIn.getApplyType())) {
                    //获取申请人的个人信息
                    sysUser = userMapper.selectUserByLoginName(a.getApplyUser());
                    //根据提交人查询是否存在直接主管
                    if (StringUtils.isNotEmpty(sysUser.getDirector()) && StringUtils.isNotEmpty(sysUser.getDirectorId().toString()) && "5".equals(sysApplyIn.getApproveStatu())) {
                        key = "document_rk_zg";
                        SysUser sysUser1 = userMapper.selectUserById(sysUser.getDirectorId());
                        //动态设置审批人员
                        variables.put("userId", sysUser1.getLoginName());
                        users.add(sysUser1.getLoginName());
                    } else {
                        //没有直接审批人，转到档案管理员下
                        List<String> jls = getUsers("documentAdmin");
                        List<String> js = new ArrayList<>();
                        for (String n: jls){
                            if (!n.equals(a.getApplyUser()) && !n.equals(a.getRealCreateBy())){
                                js.add(n);
                            }
                        }
                        users.addAll(js);
                        key = "document_rk_zgNo";
                    }
                    itemName = sysApplyIn.getCreator() + "提交的入库申请";
                } else {
                    itemName = sysApplyIn.getCreator() + "提交的出库申请";

                    List<String> userList = new ArrayList<>();
                    userList.add("yangxu");
                    userList.add("yangxudong");
                    variables.put("assigneeList", userList);

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
                        List<String> js = new ArrayList<>();
                        for (String n: jls){
                            if (!n.equals(a.getApplyUser()) && !n.equals(a.getRealCreateBy())){
                                js.add(n);
                            }
                        }
                        users.addAll(js);
                    }else{
                        //判断是否存在直接主管
                        if (StringUtils.isNotEmpty(a.getRealCreateBy()) && "0".equals(sysApplyIn.getApproveStatu())){
                            sysUser = userMapper.selectUserByLoginName(a.getApplyUser());
                        }
                        if (StringUtils.isNotEmpty(sysUser.getDirector()) && StringUtils.isNotEmpty(sysUser.getDirectorId().toString())) {
                            SysUser sysUser1 = userMapper.selectUserById(sysUser.getDirectorId());
                            users.add(sysUser1.getLoginName());
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

        /*ProcessInstance processInstance = processService.submitApply(a.getApplyUser(), a.getApplyId().toString(), itemName, a.getRemarks(), key, variables);
        String processInstanceId = processInstance.getId();
        a.setInstanceId(processInstanceId); // 建立双向关系
        sysApplyInMapper.updateSysApplyIn(a);*/
        return users;
    }

    @Override
    public AjaxResult applyEditSave(SysApplyIn sysApplyIn, HttpServletRequest request) {
        log.info("开始调用工作流，审批状态{}",sysApplyIn.getApproveStatu());
        SysUser currentUser = ShiroUtils.getSysUser();
        String loginUser = currentUser.getLoginName();
        SysApplyIn sysApplyInEntity = sysApplyInMapper.selectSysApplyInById(sysApplyIn.getApplyId());
        if (sysApplyInEntity==null || sysApplyInEntity.getApplyId()==null){
            return AjaxResult.error("无该申请");
        }
        SysApplyWorkflow workflow = new SysApplyWorkflow();
        workflow.setRemarks(sysApplyIn.getRemarks());

        String key = "";
        Map<String, Object> variables = new HashMap<>();
        List<String> users = new ArrayList<>();

        //提交审批，寻找下一审批人
        if("5".equals(sysApplyIn.getApproveStatu())) {
            return submitApply(sysApplyInEntity,sysApplyIn);
        }
        //撤回
        if("4".equals(sysApplyIn.getApproveStatu())) {
            if (!sysApplyInEntity.getApplyUser().equals(loginUser)){
                return AjaxResult.error("非创建人无法撤回");
            }
            if(!"5".equals(sysApplyInEntity.getApproveStatu())){
                return AjaxResult.error("该申请不可撤回");
            }
            sysApplyInEntity.setApproveStatu(sysApplyIn.getApproveStatu());
            sysApplyInEntity.setApproveUser("");
//            saveWorkFlow(sysApplyInEntity,workflow);
        }
        //审批拒绝，回到申请人
        if("2".equals(sysApplyIn.getApproveStatu())){
            sysApplyInEntity.setApproveUser("");
            sysApplyInEntity.setApproveStatu(sysApplyIn.getApproveStatu());
            workflow.setRemarks(sysApplyIn.getRemarks());
            sysApplyInEntity.setRemarks(null);
            saveWorkFlow(sysApplyInEntity,workflow);
            users.add(sysApplyInEntity.getApplyUser());
            sendMsg(users,sysApplyInEntity);
        }

        //同意审批
        if("6".equals(sysApplyIn.getApproveStatu())){
            List<SysRole> ros = currentUser.getRoles();
            List<String> ids = new ArrayList<>();
            for (SysRole r:ros) {
                ids.add(r.getRoleKey());
            }
            if(ids.contains("documentAdmin")){
//                档案管理员
                if("1".equals(sysApplyIn.getApplyType())){
//                    出库，档案管理员进行出库确认
                    if ("1".equals(sysApplyInEntity.getApproveStatu())){
//                        boolean isAllNotReturn = true;
                        if (!"0".equals(sysApplyInEntity.getIsOut())){
                            return AjaxResult.error("档案未出库，无法完成借出审批");
                        }
                        sysApplyIn.setApproveStatu("7");
                        sysApplyInEntity.setApproveStatu("7");
                        sysApplyInEntity.setApproveUser("");
                    }else if ("9".equals(sysApplyInEntity.getApproveStatu())){
                        if (!"0".equals(sysApplyInEntity.getIsReceived())){
                            return AjaxResult.error("档案未收到，无法完成借出审批");
                        }
                        sysApplyInEntity.setRealReturnTime(DateUtils.getNowDate());
                        sysApplyIn.setApproveStatu("3");
                        sysApplyInEntity.setApproveStatu("3");
                        sysApplyInEntity.setApproveUser("");
                    }
                }else{
//                    入库
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
                }

            }else{
                if (ids.contains("flgw") && !sysApplyInEntity.getApproveUser().equals(ShiroUtils.getLoginName())){
                    List<String> idsStr = new ArrayList<>(Arrays.asList(sysApplyInEntity.getApproveUser().split(",")));
                    idsStr.remove(ShiroUtils.getLoginName());
                    sysApplyInEntity.setApproveUser(String.join(",",idsStr));
                    sysApplyInEntity.setApproveStatu("1");
                }else{
                    //1表示审批中
                    users = getApplyNextUser(sysApplyIn);
                    sysApplyInEntity.setApproveUser(String.join(",",users));
                    sysApplyInEntity.setApproveStatu("1");


                    if (StringUtils.isNotEmpty(sysApplyInEntity.getInstanceId())){
                        BizTodoItem bizTodoItem = new BizTodoItem();
                        bizTodoItem.setInstanceId(sysApplyInEntity.getInstanceId());
                        List<BizTodoItem>  bizs =bizTodoItemService.selectBizTodoItemList(bizTodoItem);
                        if (bizs!=null && bizs.size()>0){
                            //获取对应的流程id
                            String taskId = bizs.get(0).getTaskId();
                            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
                            key = task.getProcessDefinitionId().substring(0, task.getProcessDefinitionId().indexOf(":"));
                            processService.complete(taskId, sysApplyInEntity.getInstanceId(), sysApplyInEntity.getCreator() + "提交入库申请", sysApplyInEntity.getRemarks(), key, new HashMap<String, Object>(), request);
                        }
                    }
                    /*if (saveEntityBoolean) {
                        sysWorkflowService.updateSysWorkflow(sysWorkFlowVo);
                    }*/

//                    bizTodoItemService.insertTodoItem(sysApplyInEntity.getInstanceId(),"档案入库申请","","document_rk_zg");
                }
            }
            saveWorkFlow(sysApplyInEntity,workflow);
            if ("3".equals(sysApplyInEntity.getApproveStatu()) || "2".equals(sysApplyInEntity.getApproveStatu())){
                users.add(sysApplyInEntity.getApplyUser());
            }
            sendMsg(users,sysApplyInEntity);
        }
        sysApplyInEntity.setUpdateTime(new Date());
        sysApplyInMapper.updateSysApplyIn(sysApplyInEntity);
        return AjaxResult.success();
    }

    private int saveWorkFlow(SysApplyIn sysApplyIn,SysApplyWorkflow workflow){
	    if("1".equals(sysApplyIn.getApproveStatu())){
            workflow.setApproveStatu("6");
        }else{
            workflow.setApproveStatu(sysApplyIn.getApproveStatu());
        }
        workflow.setApplyId(sysApplyIn.getApplyId());
        workflow.setApproveUser(ShiroUtils.getLoginName());
        workflow.setCreateBy(ShiroUtils.getLoginName());
        workflow.setCreateTime(new Date());
        return sysApplyWorkflowMapper.insertSysApplyWorkflow(workflow);
    }

    @Override
    public List<SysApplyIn> listUnDownByMe(SysApplyIn sysApplyIn) {
        return sysApplyInMapper.listUnDownByMe(sysApplyIn);
    }

    @Override
    public List<SysApplyIn> selectSysApplyInListUser(SysApplyIn sysApplyIn) {
        return sysApplyInMapper.selectSysApplyInListUser(sysApplyIn);
    }
   /* private List<SysApplyIn> addDobtName(List<SysApplyIn> sysApplyIns){
	    for (SysApplyIn s:sysApplyIns){
	        if ("inve".equals(s.getRoleType())){
                SysZck sz = SysZckMapper.selectSysZckById(Long.parseLong(s.getDebtorName()));
	            s.setDebtorNameTip(sz.getProjectName());
            }
        }
	    return sysApplyIns;
    }*/

    @Override
    public String checkUserRole(SysUser u) {
	    //投后部---投后部项目 0
        String[] stbList = new String[] { "thbManager", "thbManager2", "thbzz", "thbCommon"};
        List<String> stbs = Arrays.asList(stbList);
        //并购重组---大型单体 1
        String[] dxdtList = new String[] { "bgczCommon", "bgczManager" };
        List<String> dxdts = Arrays.asList(dxdtList);
        //投资部---资产包 2
        String[] tzbList = new String[] { "investmentCommon", "investmentManager", "investmentManager2", "tzbzz"};
        List<String> tzbs = Arrays.asList(tzbList);
//        SysUser u = ShiroUtils.getSysUser();
        List<SysRole> rs = u.getRoles();

        for (SysRole r: rs){
            if (stbs.contains(r.getRoleKey())){
                return "thb";
            }else if(dxdts.contains(r.getRoleKey())){
                return "bg";
            }else if(tzbs.contains(r.getRoleKey())){
                return "inve";
            }
        }
        return null;
    }

    @Override
    public List<SysApplyIn> selectSysApplyInDocDetailList(SysApplyIn sysApplyIn) {
        return sysApplyInMapper.selectSysApplyInDocDetailList(sysApplyIn);
    }

    @Override
    public List<SysApplyIn> listInOutDetail(SysApplyIn sysApplyIn) {
        return sysApplyInMapper.listInOutDetail(sysApplyIn);
    }

    @Override
    public AjaxResult importApplyIn(MultipartFile file) {
        try {

            //获取申请列表
            String documentType = "";
            MultipartFile file2 = file;
            List<SysApplyInImport> ApplyList = new ArrayList<>();
            List<SysApplyInImportDaily> ApplyListDaily = new ArrayList<>();
            List<SysApplyInImportFile> filesList = new ArrayList<>();
            List<SysApplyInImportFileDaily> filesListDaily = new ArrayList<>();
            if ("项目类数据导入.xlsx".equals(file.getOriginalFilename())){
                documentType = "0";
                ExcelUtil ex = new ExcelUtil(SysApplyInImport.class);
                ApplyList =  ex.importExcel("项目类",file.getInputStream());
                //获取附件列表
                ExcelUtil fileDetails = new ExcelUtil(SysApplyInImportFile.class);
                filesList =  fileDetails.importExcel("档案信息",file2.getInputStream());
            } else if ("日常经营类数据导入.xlsx".equals(file.getOriginalFilename())){
                documentType = "1";
                ExcelUtil ex = new ExcelUtil(SysApplyInImportDaily.class);
                ApplyListDaily =  ex.importExcel("日常经营类",file.getInputStream());
                //获取附件列表
                ExcelUtil fileDetails = new ExcelUtil(SysApplyInImportFileDaily.class);
                filesListDaily =  fileDetails.importExcel("档案信息",file2.getInputStream());
            }else if("合同类数据导入.xlsx".equals(file.getOriginalFilename())){
                documentType = "2";
                ExcelUtil ex = new ExcelUtil(SysApplyInImportDaily.class);
                ApplyListDaily =  ex.importExcel("合同类",file.getInputStream());
                //获取附件列表
                ExcelUtil fileDetails = new ExcelUtil(SysApplyInImportFileDaily.class);
                filesListDaily =  fileDetails.importExcel("档案信息",file2.getInputStream());
            }else{
                return AjaxResult.error("上传文件错误，请重新上传");
            }

            //种类(companyName)
            List<SysDictData> dailyDocumentType = dictDataService.selectDictDataByType("daily_document_type");

            List<SysDictData> contractDocumentType = dictDataService.selectDictDataByType("contract_document_type");
            //附件类型字典项(companyName)
            List<SysDictData> companyNameDict = dictDataService.selectDictDataByType("sys_company_name");
            //附件类型字典项(fileType)
            List<SysDictData> documentFileTypeDict = dictDataService.selectDictDataByType("document_file_type");
            //扫描件类型字典项(fileScanType)
            List<SysDictData> documentScanTypeDict = dictDataService.selectDictDataByType("document_scan_type");
            //档案状态字典项(documentStatu)
            List<SysDictData> documentInStatusDict = dictDataService.selectDictDataByType("document_in_status");

            List<SysDictData> busiDocumentTypeDict = dictDataService.selectDictDataByType("busi_document_type");
            //文件类型字典项(fileGetType)
            List<SysDictData> documentInTypeDict = dictDataService.selectDictDataByType("document_in_type");
            //档案级别字典项(documentLevel)
            List<SysDictData> documentLevelDict = dictDataService.selectDictDataByType("document_level");

            for (SysApplyInImport s:ApplyList) {
                for (SysDictData d : companyNameDict ) {
                    if (d.getDictLabel().equals(s.getCompanyNameLab())){
                        s.setCompanyName(d.getDictValue());
                    }
                }
                SysUser appUser = userMapper.selectUserByUserName(s.getApplyUserLab());
                s.setApplyUser(appUser!=null?appUser.getLoginName():s.getApplyUserLab());
                appUser = userMapper.selectUserByUserName(s.getRealCreateNameLab());
                s.setRealCreateName(appUser!=null?appUser.getLoginName():s.getRealCreateNameLab());
                appUser = userMapper.selectUserByUserName(s.getReviserNameLab());
                s.setReviserName(appUser!=null?appUser.getLoginName():s.getReviserNameLab());
            }
            for (SysApplyInImportDaily s:ApplyListDaily) {
                for (SysDictData d : companyNameDict ) {
                    if (d.getDictLabel().equals(s.getCompanyNameLab())){
                        s.setCompanyName(d.getDictValue());
                    }
                }
                SysUser appUser = userMapper.selectUserByUserName(s.getApplyUserLab());
                s.setApplyUser(appUser!=null?appUser.getLoginName():s.getApplyUserLab());
                appUser = userMapper.selectUserByUserName(s.getRealCreateNameLab());
                s.setRealCreateName(appUser!=null?appUser.getLoginName():s.getRealCreateNameLab());
                appUser = userMapper.selectUserByUserName(s.getReviserNameLab());
                s.setReviserName(appUser!=null?appUser.getLoginName():s.getReviserNameLab());
            }

            //为附件匹配字典项
            for (SysApplyInImportFileDaily f:filesListDaily) {
                if ("1".equals(documentType)){
                    for (SysDictData d : dailyDocumentType ) {
                        if (d.getDictLabel().equals(f.getDailyDocumentTypeLab())){
                            f.setDailyDocumentType(d.getDictValue());
                        }
                    }
                }else{
                    for (SysDictData d : contractDocumentType ) {
                        if (d.getDictLabel().equals(f.getDailyDocumentTypeLab())){
                            f.setDailyDocumentType(d.getDictValue());
                        }
                    }
                }
                for (SysDictData d : companyNameDict ) {
                    if (d.getDictLabel().equals(f.getCompanyNameLab())){
                        f.setCompanyName(d.getDictValue());
                    }
                }
                for (SysDictData d : documentFileTypeDict ) {
                    if (d.getDictLabel().equals(f.getFileTypeLab())){
                        f.setFileType(d.getDictValue());
                    }
                }
                for (SysDictData d : documentScanTypeDict ) {
                    if (d.getDictLabel().equals(f.getFileScanTypeLab())){
                        f.setFileScanType(d.getDictValue());
                    }
                }
                for (SysDictData d : documentInStatusDict ) {
                    if (d.getDictLabel().equals(f.getDocumentStatuLab())){
                        f.setDocumentStatu(d.getDictValue());
                    }
                }
                for (SysDictData d : busiDocumentTypeDict ) {
                    if (d.getDictLabel().equals(f.getBusiDocumentTypeLab())){
                        f.setBusiDocumentType(d.getDictValue());
                    }
                }
                for (SysDictData d : documentInTypeDict ) {
                    if (d.getDictLabel().equals(f.getFileGetTypeLab())){
                        f.setFileGetType(d.getDictValue());
                    }
                }
                for (SysDictData d : documentLevelDict ) {
                    if (d.getDictLabel().equals(f.getDocumentLevelLab())){
                        f.setDocumentLevel(d.getDictValue());
                    }
                }
            }

            //为附件匹配字典项
            for (SysApplyInImportFile f:filesList) {
                for (SysDictData d : companyNameDict ) {
                    if (d.getDictLabel().equals(f.getCompanyNameLab())){
                        f.setCompanyName(d.getDictValue());
                    }
                }
                for (SysDictData d : documentFileTypeDict ) {
                    if (d.getDictLabel().equals(f.getFileTypeLab())){
                        f.setFileType(d.getDictValue());
                    }
                }
                for (SysDictData d : documentScanTypeDict ) {
                    if (d.getDictLabel().equals(f.getFileScanTypeLab())){
                        f.setFileScanType(d.getDictValue());
                    }
                }
                for (SysDictData d : documentInStatusDict ) {
                    if (d.getDictLabel().equals(f.getDocumentStatuLab())){
                        f.setDocumentStatu(d.getDictValue());
                    }
                }
                for (SysDictData d : busiDocumentTypeDict ) {
                    if (d.getDictLabel().equals(f.getBusiDocumentTypeLab())){
                        f.setBusiDocumentType(d.getDictValue());
                    }
                }
                for (SysDictData d : documentInTypeDict ) {
                    if (d.getDictLabel().equals(f.getFileGetTypeLab())){
                        f.setFileGetType(d.getDictValue());
                    }
                }
                for (SysDictData d : documentLevelDict ) {
                    if (d.getDictLabel().equals(f.getDocumentLevelLab())){
                        f.setDocumentLevel(d.getDictValue());
                    }
                }
            }
            //将档案归类，每个档案归类到每个申请下面
            for (SysApplyInImport apply:ApplyList) {
                if ("0".equals(documentType)){
                    List<SysApplyInImportFile> files = new ArrayList<>();
                    for (SysApplyInImportFile fi:filesList){
                        if (apply.getProjectName().equals(fi.getProjectName()) && apply.getCompanyNameLab().equals(fi.getCompanyNameLab())&&
                                apply.getDebtorName().equals(fi.getDebtorName())){
                            files.add(fi);
                        }
                    }
                    apply.setFiles(files);
                }
            }
            for (SysApplyInImportDaily apply:ApplyListDaily) {
                List<SysApplyInImportFileDaily> filesD = new ArrayList<>();
                for (SysApplyInImportFileDaily fi:filesListDaily){
                    if (apply.getCompanyNameLab().equals(fi.getCompanyNameLab())){
                        filesD.add(fi);
                    }
                }
                apply.setFilesDaily(filesD);
            }
            //数据封装完成开始进行插入
            if ("0".equals(documentType)){
                for (SysApplyInImport apply:ApplyList) {
                    SysApplyIn applyIn = new SysApplyIn();
                    if ("投资部".equals(apply.getDepName())){
                        SysZcb sysZcb = new SysZcb();
                        sysZcb.setAssetPackageName(apply.getProjectName());
                        List<SysZcb>  zcb = sysZcbMapper.selectSysZcbListNoLike(sysZcb);
                        if (zcb!=null && zcb.size()>0){
                            applyIn.setProjectName(zcb.get(0).getAssetPackageName());
                            applyIn.setProjectId(zcb.get(0).getId());

                            SysZck sysZck = new SysZck();
                            sysZck.setProjectName(apply.getDebtorName());
                            sysZck.setZcbId(zcb.get(0).getId());
                            List<SysZck>  zcks = sysZckMapper.selectSysZckListNoLike(sysZck);
                            if (zcks!=null && zcks.size()>0){
                                applyIn.setDebtorName(zcks.get(0).getProjectName());
                                applyIn.setDebtorId(zcks.get(0).getId());
                                applyIn.setRoleType("inve");
                                applyIn.setDocumentType(documentType);
                                insertApply(apply,applyIn);
                            }
                        }

                    }else if ("投后部".equals(apply.getDepName())){
                        SysProjectZck sysProjectZck = new SysProjectZck();
                        sysProjectZck.setZckName(apply.getProjectName());
                        List<SysProjectZck> zcks = sysProjectZckMapper.selectSysProjectZckAllNoLike(sysProjectZck);
                        if (zcks!=null && zcks.size()>0){
                            applyIn.setProjectName(zcks.get(0).getZckName());
                            applyIn.setProjectId(zcks.get(0).getProjectZckId());

                            SysProject sysProject = new SysProject();
                            sysProject.setProjectZckId(zcks.get(0).getProjectZckId());
                            sysProject.setProjectName(apply.getDebtorName());
                            List<SysProject>  pros = sysProjectMapper.selectSysProjectListNoLike(sysProject);
                            if (pros!=null && pros.size()>0){
                                applyIn.setDebtorName(pros.get(0).getProjectName());
                                applyIn.setDebtorId(pros.get(0).getProjectId());
                                applyIn.setDocumentType(documentType);
                                applyIn.setRoleType("thb");
                                insertApply(apply,applyIn);
                            }
                        }
                    }else if ("大型单体".equals(apply.getDepName())){
                        SysBgczzck sysBgczzck = new SysBgczzck();
                        sysBgczzck.setProjectName(apply.getProjectName());
                        List<SysBgczzck> zcks = sysBgczzckMapper.selectSysBgczzckListUsefulNoLike(sysBgczzck);
                        if (zcks!=null && zcks.size()>0){
                            applyIn.setProjectName(zcks.get(0).getProjectName());
                            applyIn.setProjectId(zcks.get(0).getId());
                            applyIn.setDocumentType(documentType);

                            applyIn.setRoleType("bg");
                            insertApply(apply,applyIn);

                        }
                    }
                }
            }else{
                for (SysApplyInImportDaily apply:ApplyListDaily) {
                    SysApplyIn applyIn = new SysApplyIn();
                    applyIn.setDocumentType(documentType);
                    insertApplyDaily(apply,applyIn);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return AjaxResult.success();
    }

    @Override
    public List<SysApplyIn> selectSysApplyInDocDetailZcbList(SysApplyIn sysApplyIn) {
        return sysApplyInMapper.selectSysApplyInDocDetailZcbList(sysApplyIn);
    }

    @Override
    public List<SysApplyIn> selectSysApplyInDocByPNameDetailList(SysApplyIn sysApplyIn) {
        return sysApplyInMapper.selectSysApplyInDocByPNameDetailList(sysApplyIn);
    }

    @Override
    public List<SysApplyIn> selectSysApplyInDailyDetailList(SysApplyIn sysApplyIn) {
        return sysApplyInMapper.selectSysApplyInDailyDetailList(sysApplyIn);
    }

    public int insertApply(SysApplyInImport apply,SysApplyIn applyIn){
        applyIn.setApplyUser(apply.getApplyUser());
        applyIn.setApplyTime(apply.getApplyTime());
        applyIn.setApproveStatu("3");
        applyIn.setReviser(apply.getReviserName());
        applyIn.setRealCreateBy(apply.getRealCreateName());
        applyIn.setCompanyName(apply.getCompanyName());
        applyIn.setRemarks(apply.getRemark());

        applyIn.setInType("import");
        applyIn.setApplyType("0");
        applyIn.setCreateTime(new Date());
        applyIn.setUpdateTime(new Date());
        sysApplyInMapper.insertSysApplyIn(applyIn);
        for (SysApplyInImportFile f :apply.getFiles()) {
            SysDocumentFile doc = new SysDocumentFile();
            doc.setApplyId(applyIn.getApplyId());
            doc.setAssetNumber(f.getAssetNumber());
            doc.setContractNo(f.getContractNo());
            doc.setFileName(f.getFileName());
            doc.setFileType(f.getFileType());
            doc.setDocumentGetType(f.getBusiDocumentType());
            doc.setFileScanType(f.getFileScanType());
            doc.setCounts(f.getCounts());
            doc.setPages(f.getPages());
            doc.setDocumentStatu(f.getDocumentStatu());
            doc.setCabinetNo(f.getCabinetNo());
            doc.setBagNo(f.getBagNo());
            doc.setFileGetType(f.getFileGetType());
            doc.setDocumentType(applyIn.getDocumentType());
            doc.setDocumentLevel(f.getDocumentLevel());
            doc.setCreateTime(new Date());
            doc.setUpdateTime(new Date());
            doc.setCreateBy(ShiroUtils.getLoginName());
            documentFileMapper.insertSysDocumentFile(doc);
            SysFileDetail fileDetail = new SysFileDetail();
            fileDetail.setCreateTime(new Date());
            fileDetail.setUpdateTime(new Date());
            fileDetail.setCreateBy(ShiroUtils.getLoginName());
            fileDetail.setDocumentFileId(doc.getDocumentId());
            fileDetail.setFileUrl(f.getFileUrl());
            fileDetail.setFileType(f.getFileDetailType());
            fileDetail.setFileName(f.getFileUrl().substring(f.getFileUrl().lastIndexOf("\\")+1));
            fileDetailMapper.insertSysFileDetail(fileDetail);
        }
        return 0;
    }

    public int insertApplyDaily(SysApplyInImportDaily apply,SysApplyIn applyIn){
        applyIn.setApplyUser(apply.getApplyUser());
        applyIn.setApplyTime(apply.getApplyTime());
        applyIn.setApproveStatu("3");
        applyIn.setReviser(apply.getReviserName());
        applyIn.setRealCreateBy(apply.getRealCreateName());
        applyIn.setCompanyName(apply.getCompanyName());
        applyIn.setRemarks(apply.getRemark());

        applyIn.setInType("import");
        applyIn.setApplyType("0");
        applyIn.setCreateTime(new Date());
        applyIn.setUpdateTime(new Date());
        sysApplyInMapper.insertSysApplyIn(applyIn);
        for (SysApplyInImportFileDaily f :apply.getFilesDaily()) {
            SysDocumentFile doc = new SysDocumentFile();
            doc.setApplyId(applyIn.getApplyId());
            doc.setFileName(f.getFileName());
            doc.setFileType(f.getFileType());
            doc.setFileScanType(f.getFileScanType());
            if ("1".equals(applyIn.getDocumentType())){
                doc.setDailyDocumentType(f.getDailyDocumentType());
            }else{
                doc.setDailyDocumentTypeContract(f.getDailyDocumentType());
            }
            doc.setDocumentGetType(f.getBusiDocumentType());
            doc.setCounts(f.getCounts());
            doc.setPages(f.getPages());
            doc.setDocumentStatu(f.getDocumentStatu());
            doc.setCabinetNo(f.getCabinetNo());
            doc.setBagNo(f.getBagNo());
            doc.setFileGetType(f.getFileGetType());
            doc.setDocumentType(applyIn.getDocumentType());
            doc.setDocumentLevel(f.getDocumentLevel());
            doc.setCreateTime(new Date());
            doc.setUpdateTime(new Date());
            doc.setCreateBy(ShiroUtils.getLoginName());
            documentFileMapper.insertSysDocumentFile(doc);
            SysFileDetail fileDetail = new SysFileDetail();
            fileDetail.setCreateTime(new Date());
            fileDetail.setUpdateTime(new Date());
            fileDetail.setCreateBy(ShiroUtils.getLoginName());
            fileDetail.setDocumentFileId(doc.getDocumentId());
            fileDetail.setFileUrl(f.getFileUrl());
            fileDetail.setFileType(f.getFileDetailType());
            fileDetail.setFileName(f.getFileUrl().substring(f.getFileUrl().lastIndexOf("\\")+1));
            fileDetailMapper.insertSysFileDetail(fileDetail);
        }
        return 0;
    }

    @Override
    public int editSave(SysApplyIn sysApplyIn) {
        sysApplyIn.setReviser(ShiroUtils.getLoginName());
        sysApplyIn.setUpdateTime(new Date());
        SysApplyIn sin = sysApplyInMapper.selectSysApplyInById(sysApplyIn.getApplyId());
        if ("7".equals(sin.getApproveStatu()) && "0".equals(sysApplyIn.getIsReceive())){
            SysApplyOutDetail sd = new SysApplyOutDetail();
            sd.setApplyId(sysApplyIn.getApplyId());
            List<SysApplyOutDetail> dfs =  sysApplyOutDetailMapper.selectSysApplyOutDetailList(sd);
            if ("1".equals(sin.getIsReturn())){
                sysApplyIn.setApproveStatu("3");
                //出库不归还
                for (SysApplyOutDetail df:dfs) {
                    SysDocumentFile sysDocumentFile = new SysDocumentFile();
                    sysDocumentFile.setDocumentStatu("2");
                    sysDocumentFile.setDocumentId(df.getDocumentId());
                    documentFileMapper.updateSysDocumentFile(sysDocumentFile);
                }
            }else{
                sysApplyIn.setApproveStatu("8");
                //出库待归还
                for (SysApplyOutDetail df:dfs) {
                    SysDocumentFile sysDocumentFile = new SysDocumentFile();
                    sysDocumentFile.setDocumentStatu("0");
                    sysDocumentFile.setDocumentId(df.getDocumentId());
                    documentFileMapper.updateSysDocumentFile(sysDocumentFile);
                }
            }
        }
        if ("8".equals(sin.getApproveStatu()) && "0".equals(sysApplyIn.getIsReturned())){
            sysApplyIn.setApproveStatu("9");
        }
        if ("9".equals(sin.getApproveStatu()) && "0".equals(sysApplyIn.getIsReceived())){
            sysApplyIn.setApproveStatu("3");
            sysApplyIn.setRealReturnTime(DateUtils.getNowDate());
            //已归还
            SysApplyOutDetail sd = new SysApplyOutDetail();
            sd.setApplyId(sysApplyIn.getApplyId());
            List<SysApplyOutDetail> dfs =  sysApplyOutDetailMapper.selectSysApplyOutDetailList(sd);
            for (SysApplyOutDetail df:dfs) {
                SysDocumentFile sysDocumentFile = new SysDocumentFile();
                sysDocumentFile.setDocumentStatu("1");
                sysDocumentFile.setDocumentId(df.getDocumentId());
                documentFileMapper.updateSysDocumentFile(sysDocumentFile);
            }
        }
	    return sysApplyInMapper.updateSysApplyIn(sysApplyIn);
    }
}
