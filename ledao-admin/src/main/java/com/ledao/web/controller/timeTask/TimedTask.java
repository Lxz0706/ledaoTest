package com.ledao.web.controller.timeTask;

import com.alibaba.fastjson.JSONObject;
import com.ledao.common.constant.WeChatConstants;
import com.ledao.common.message.Template;
import com.ledao.common.message.TemplateParam;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.framework.web.dao.server.Sys;
import com.ledao.system.dao.*;
import com.ledao.system.mapper.SysRoleMapper;
import com.ledao.system.mapper.SysUserMapper;
import com.ledao.system.service.*;

import org.apache.activemq.command.ActiveMQQueue;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.SpringVersion;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.jms.Queue;

@Component("timedTask")
public class TimedTask {
    @Autowired
    private ISysProjectmanagentService sysProjectmanagentService;

    @Autowired
    private ISysProjectTargetrecoverService sysProjectTargetrecoverService;

    @Autowired
    private ISysProjectUncollectedMoneyService sysProjectUncollectedMoneyService;

    @Autowired
    private ISysNoticeService sysNoticeService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysProjectService sysProjectService;

    @Autowired
    private ISysProjectZckService sysProjectZckService;

    @Autowired
    private ISysProjectContractService sysProjectContractService;

    @Autowired
    private ISysCoverChargeService sysCoverChargeService;

    @Autowired
    private ISysCustomerService sysCustomerService;

    @Autowired
    private ISysStaffService sysStaffService;
    
    @Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private ISysManageTaskService sysManageTaskService;

    public void timeTask() throws ParseException {
        //应收应付未收服务费消息提醒
          projectUncollectedMoney();
        //待结算服务费消息提醒
          projectTargetRecover();

        //投后项目消息提醒
         sysProject();

        //投后项目利息自动计算
         updateInterest();
        //ss();

        secretaryLingAnalysis();
    }

    //档案管理流程信息
    public void workflow() {
        SysNotice sysNotice = new SysNotice();
        sysNotice.setCreateBy("admin");
        sysNotice.setStatus("0");
        sysNotice.setNoticeTitle("这是测试数据");
        sysNotice.setNoticeType("2");
        sysNotice.setNoticeContent("测试数据！！！！！！！");
        System.out.print("新建消息");
        sysNoticeService.insertNotice(sysNotice);
    }
    
    /**
     * 项目流转表消息推送
     * @throws ParseException
     */
    public void xmlzb() throws ParseException {
        SysProjectUncollectedMoney sysProjectUncollectedMoney = new SysProjectUncollectedMoney();
        List<SysProjectUncollectedMoney> list = sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyList(sysProjectUncollectedMoney);
        System.out.print(list.toString());
        
        // 创建名称为zyQueue的队列
 		Queue queue = new ActiveMQQueue("zyQueue1");
 		// 向队列发送消息
 		jmsMessagingTemplate.convertAndSend(queue, "这是一个队列消息！");
    }

    private List<SysUser> getUsers(String roleKey){
        List<SysUser> users = new ArrayList<>();
        SysRole r = new SysRole();
        r.setRoleKey(roleKey);
        List<SysRole> ros = roleMapper.selectRoleList(r);
        if (ros!=null && ros.size()>0){
            SysUser userRoles = new SysUser();
            userRoles.setRoleId(ros.get(0).getRoleId());
            List<SysUser> us =  userMapper.selectAllocatedList(userRoles);
            for (SysUser u: us) {
                users.add(u);
            }
        }
        return users;
    }


    /**
     *投后总任务消息推送
     * @throws ParseException
     */
    public void totalTask() throws ParseException {

        SysManageTask sysManageTask = new SysManageTask();
        sysManageTask.setTaskType("0");
        List<SysManageTask> task =  sysManageTaskService.selectSysManageTaskList(sysManageTask);
        for (SysManageTask s: task){
            if (s.getRealEndTime()==null){
                long planEndTime = sysManageTask.getPlanEndTime().getTime();
                long realEndTime = (new Date()).getTime();
                if (realEndTime-planEndTime>0){
                    long days = DateUtils.differentDays(sysManageTask.getPlanEndTime(),sysManageTask.getRealEndTime());
                    sysManageTask.setOverDay(days);
                    sysManageTask.setTaskStatu("later");
                    sysManageTaskService.updateSysManageTask(sysManageTask);
                }

            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
            String date = DateUtils.getPreMonth();
            String planBeginDate =  dateFormat.format(s.getPlanBeginTime());
            if (date.equals(planBeginDate)){
                List<SysUser> users = getUsers("thbManager");
                for (SysUser u:users){
                    if (StringUtils.isNotEmpty(u.getOpenId())){
                        //发送消息到投后部部门经理
                        JSONObject parm = new JSONObject();
                        //发布人
                        parm.put("thing16","测试1");
//                        计划时间
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        parm.put("time4", sdf.format(new Date())+" 至 "+sdf.format(new Date()));
//                        任务名称
                        parm.put("thing1","任务名称测试");
//                        任务状态
                        parm.put("phrase7","测试2");
//                        任务接收人
                        parm.put("thing23","任务状态测试");
                        parm.put("toUser",u.getOpenId());
                        // 创建名称为投后队列
                        Queue queue = new ActiveMQQueue("ThQueueCommon");
                        String dataStr = JSONObject.toJSONString(parm);
                        // 向队列发送消息
                        jmsMessagingTemplate.convertAndSend(queue, dataStr);
                    }
                }
            }
        }

    }

    /**
     *投后子任务消息推送
     * @throws ParseException
     */
    public void subTask() throws ParseException {
        List<String> idszck = new ArrayList<>();
        List<String> idspro = new ArrayList<>();


        SysProjectZck sysProjectZck = new SysProjectZck();
        sysProjectZck.setProjectZckType("automaticSwitch");
        List<SysProjectZck> zcks = sysProjectZckService.selectSysProjectZckList(sysProjectZck);
        for (SysProjectZck zc:zcks){
            SysProject sysProject = new SysProject();
            sysProject.setProjectZckId(zc.getProjectZckId());
            List<SysProject> pros = sysProjectService.selectSysProjectList(sysProject);
            for (SysProject p: pros){
                /*SysUser u = sysUserService.selectUserByUserName(p.getProjectManager());
                if (StringUtils.isNotEmpty(u.getOpenId())){
                    SysManageTask sysManageTask = new SysManageTask();
                    sysManageTask.setProId(p.getpId());
                    List<SysManageTask> tasks = sysManageTaskService.selectSysManageTaskList(sysManageTask);
                    for (SysManageTask t:tasks) {

                    }
                }*/
                idspro.add(String.valueOf(p.getProjectId()));
            }
            idszck.add(String.valueOf(zc.getProjectZckId()));
        }

        String idszckStr = String.join(",",idszck);
        String idsproStr = String.join(",",idspro);
        System.out.println(idszckStr);
        System.out.println(idsproStr);


        /*List<SysUser> users = getUsers("thbManager");
        for (SysUser u:users){
            if (StringUtils.isNotEmpty(u.getOpenId())){
                //发送消息到投后部部门经理
                JSONObject parm = new JSONObject();
                parm.put("thing16","测试1");
                parm.put("thing20","测试2");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                parm.put("time4", sdf.format(new Date())+" 至 "+sdf.format(new Date()));
                parm.put("thing1","任务名称测试");
                parm.put("phrase7","任务状态测试");
                parm.put("toUser",u.getOpenId());
                // 创建名称为投后队列
                Queue queue = new ActiveMQQueue("ThQueueCommon");
                String dataStr = JSONObject.toJSONString(parm);
                // 向队列发送消息
                jmsMessagingTemplate.convertAndSend(queue, dataStr);
            }
        }*/
    }
    
    /**
     * 网拍线索消息提醒
     * @throws ParseException
     */
    public void racquetClues() throws ParseException {

		List<TemplateParam> paras=new ArrayList<TemplateParam>();  

		paras.add(new TemplateParam("keyword1","test1",""));  //维修工单
		paras.add(new TemplateParam("keyword2","test2",""));  //维修地址
		paras.add(new TemplateParam("keyword3","test3","")); //完成时间 
		paras.add(new TemplateParam("keyword4","test4",""));  //备注
		paras.add(new TemplateParam("keyword5","test5",""));  //维修工程师
        JSONObject parm = new JSONObject();
        parm.put("thing6","测试1");
        parm.put("thing4","测试2");
        parm.put("thing7","测试3");
        parm.put("time4",DateUtils.getNowDate());
        
        // 创建名称为zyQueue的队列
 		Queue queue = new ActiveMQQueue("zyQueueCommon");
 		String dataStr = JSONObject.toJSONString(parm);
 		// 向队列发送消息
 		jmsMessagingTemplate.convertAndSend(queue, dataStr);
    }

    public void ss() {
        SysCustomer sysCustomer = new SysCustomer();
        List<SysCustomer> sysCustomerList = sysCustomerService.selectSysCustomerList(sysCustomer);
        for (SysCustomer sysCustomer1 : sysCustomerList) {
            SysUser sysUser = sysUserService.selectUserByLoginName(sysCustomer1.getCreateBy());
            sysCustomer1.setCreator(sysUser.getUserName());
            sysCustomerService.updateSysCustomer(sysCustomer1);
        }
    }

    public void projectUncollectedMoney() {
        //查询出接收人名称和ID
        SysUser sysUser = sysUserService.selectUserByLoginName("wangziyuan");
        //应收应付未收服务费消息提醒
        SysProjectUncollectedMoney sysProjectUncollectedMoney = new SysProjectUncollectedMoney();
        sysProjectUncollectedMoney.setState("否");
        List<SysProjectUncollectedMoney> sysProjectUncollectedMoneyList = sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyList(sysProjectUncollectedMoney);
        for (SysProjectUncollectedMoney projectUncollectedMoney : sysProjectUncollectedMoneyList) {
            if (StringUtils.isNotNull(projectUncollectedMoney.getTime())) {
                // if (DateUtils.timeDifference(projectUncollectedMoney.getTime(), new Date(), 30)) {
                if (StringUtils.isNotNull(projectUncollectedMoney.getProjectManagementId())) {
                    SysProjectmanagent sysProjectmanagent = sysProjectmanagentService.selectSysProjectmanagentById(projectUncollectedMoney.getProjectManagementId());
                    if (StringUtils.isNotNull(sysProjectmanagent) && StringUtils.isNotNull(sysProjectmanagent.getProjectManagementName())) {
                        SysNotice sysNotice = new SysNotice();
                        int days = 0;
                        if (projectUncollectedMoney.getTime().before(new Date())) {
                            days = DateUtils.differentDays(projectUncollectedMoney.getTime(), new Date());
                        } else if (new Date().before(projectUncollectedMoney.getTime())) {
                            days = DateUtils.differentDays(new Date(), projectUncollectedMoney.getTime());
                        }
                        if (days <= 30) {
                            sysNotice.setNoticeTitle(sysProjectmanagent.getProjectType() + "：" + sysProjectmanagent.getProjectManagementName() + "在" + DateUtils.dateTime(projectUncollectedMoney.getTime())
                                    + "有一笔金额为：" + projectUncollectedMoney.getAmountMoney() + "的" + projectUncollectedMoney.getFundType() + "（未确认）"
                                    + "，剩余：" + days + "天");
                            sysNotice.setNoticeContent(sysProjectmanagent.getProjectType() + "：" + sysProjectmanagent.getProjectManagementName() + "在" + DateUtils.dateTime(projectUncollectedMoney.getTime())
                                    + "有一笔金额为：" + projectUncollectedMoney.getAmountMoney() + "的" + projectUncollectedMoney.getFundType() + "（未确认）"
                                    + "，剩余：" + days + "天");
                        } else {
                            sysNotice.setNoticeTitle(sysProjectmanagent.getProjectType() + "：" + sysProjectmanagent.getProjectManagementName() + "在" + DateUtils.dateTime(projectUncollectedMoney.getTime())
                                    + "有一笔金额为：" + projectUncollectedMoney.getAmountMoney() + "的" + projectUncollectedMoney.getFundType() + "（未确认）"
                                    + "，已逾期：" + days + "天");
                            sysNotice.setNoticeContent(sysProjectmanagent.getProjectType() + "：" + sysProjectmanagent.getProjectManagementName() + "在" + DateUtils.dateTime(projectUncollectedMoney.getTime())
                                    + "有一笔金额为：" + projectUncollectedMoney.getAmountMoney() + "的" + projectUncollectedMoney.getFundType() + "（未确认）"
                                    + "，已逾期：" + days + "天");
                        }

                        sysNotice.setNoticeType("3");
                        sysNotice.setStatus("0");
                        sysNotice.setReceiverId(sysUser.getUserId().toString());
                        sysNotice.setReceiver(sysUser.getUserName());
                        sysNotice.setCreateBy(sysUser.getLoginName());
                        sysNotice.setShareDeptAndUser(sysUser.getUserName());
                        sysNoticeService.insertNotice(sysNotice);
                    }
                }
            }
            // }
        }
    }

    public void projectTargetRecover() {
        //查询出接收人名称和ID
        SysUser sysUser = sysUserService.selectUserByLoginName("wangziyuan");
        //目标回收金额消息提醒
        SysCoverCharge sysCoverCharge = new SysCoverCharge();
        sysCoverCharge.setState("否");
        List<SysCoverCharge> sysCoverChargeList = sysCoverChargeService.selectSysCoverChargeList(sysCoverCharge);
        for (SysCoverCharge sysCoverCharge1 : sysCoverChargeList) {
            if (StringUtils.isNotNull(sysCoverCharge1)) {
                if (StringUtils.isNotNull(sysCoverCharge1.getPaidDate())) {
                    // if (DateUtils.timeDifference(sysCoverCharge1.getPaidDate(), new Date(), 30)) {
                    if (StringUtils.isNotNull(sysCoverCharge1.getProjectManagementId())) {
                        SysProjectmanagent sysProjectmanagent = sysProjectmanagentService.selectSysProjectmanagentById(sysCoverCharge1.getProjectManagementId());
                        if (StringUtils.isNotNull(sysProjectmanagent) && StringUtils.isNotNull(sysProjectmanagent.getProjectManagementName())) {
                            SysNotice sysNotice = new SysNotice();
                            int days = 0;
                            if (sysCoverCharge1.getPaidDate().before(new Date())) {
                                days = DateUtils.differentDays(sysCoverCharge1.getPaidDate(), new Date());
                            } else if (new Date().before(sysCoverCharge1.getPaidDate())) {
                                days = DateUtils.differentDays(new Date(), sysCoverCharge1.getPaidDate());
                            }

                            if (days <= 30) {
                                sysNotice.setNoticeTitle(sysProjectmanagent.getProjectType() + "：" + sysProjectmanagent.getProjectManagementName()
                                        + "在" + DateUtils.dateTime(sysCoverCharge1.getPaidDate())
                                        + "有一笔金额为：" + sysCoverCharge1.getAmountPaid()
                                        + "元的目标回收金额（未确认），剩余：" + days + "天");
                                sysNotice.setNoticeContent(sysProjectmanagent.getProjectType() + "：" + sysProjectmanagent.getProjectManagementName()
                                        + "在" + DateUtils.dateTime(sysCoverCharge1.getPaidDate())
                                        + "有一笔金额为：" + sysCoverCharge1.getAmountPaid()
                                        + "元的目标回收金额（未确认），剩余：" + days + "天");
                            } else {
                                sysNotice.setNoticeTitle(sysProjectmanagent.getProjectType() + "：" + sysProjectmanagent.getProjectManagementName()
                                        + "在" + DateUtils.dateTime(sysCoverCharge1.getPaidDate())
                                        + "有一笔金额为：" + sysCoverCharge1.getAmountPaid()
                                        + "元的目标回收金额（未确认），已逾期：" + days + "天");
                                sysNotice.setNoticeContent(sysProjectmanagent.getProjectType() + "：" + sysProjectmanagent.getProjectManagementName()
                                        + "在" + DateUtils.dateTime(sysCoverCharge1.getPaidDate())
                                        + "有一笔金额为：" + sysCoverCharge1.getAmountPaid()
                                        + "元的目标回收金额（未确认），距离天数为：" + days + "天");
                            }


                            sysNotice.setNoticeType("3");
                            sysNotice.setStatus("0");
                            sysNotice.setReceiverId(sysUser.getUserId().toString());
                            sysNotice.setReceiver(sysUser.getUserName());
                            sysNotice.setCreateBy(sysUser.getLoginName());
                            sysNotice.setShareDeptAndUser(sysUser.getUserName());
                            sysNoticeService.insertNotice(sysNotice);
                        }
                    }
                    //}
                }
            }

        }
    }


    //投后项目消息提醒
    public void sysProject() {
        SysProject sysProject = new SysProject();
        SysUser sysUser1 = sysUserService.selectUserByLoginName("xukai");
        SysUser sysUser2 = sysUserService.selectUserByLoginName("jianghui");
        List<SysProject> sysProjectList = sysProjectService.selectSysProjectList(sysProject);
        if (StringUtils.isNotNull(sysProject)) {
            for (SysProject sysProject1 : sysProjectList) {
                if ("处置中".equals(sysProject1.getDebtStatus())) {
                    if ("0".equals(sysProject1.getLimitation())) {
                        //诉讼时效提醒
                        if (StringUtils.isNotNull(sysProject1.getLimitationAction())) {
                            Date limitationAction = DateUtils.parseDate(DateUtils.getDateToString(nextMonth(sysProject1.getLimitationExecution(), 30)));
                            if (DateUtils.timeDifference(new Date(), limitationAction, 0)) {
                                if (StringUtils.isNotNull(sysProject1.getProjectManager()) && StringUtils.isNotNull(sysProject1.getProjectManagerId())) {
                                    SysUser sysUser = sysUserService.selectUserById(sysProject1.getProjectManagerId());
                                    SysProjectZck sysProjectZck = sysProjectZckService.selectSysProjectZckById(sysProject1.getProjectZckId());
                                    SysNotice sysNotice = new SysNotice();
                                    sysNotice.setNoticeTitle(sysProjectZck.getZckName() + "中的" + sysProject1.getProjectName() + "诉讼时效为" + DateUtils.dateTime(sysProject1.getLimitationAction()) + "的消息提醒");
                                    sysNotice.setNoticeContent(sysProjectZck.getZckName() + "中的" + sysProject1.getProjectName() + "诉讼时效为" + DateUtils.dateTime(sysProject1.getLimitationAction()) + "的消息提醒");
                                    if (sysProject1.getProjectManagerId().equals(sysUser1.getUserId()) || sysProject1.getProjectManagerId().equals(sysUser2.getUserId())) {
                                        sysNotice.setReceiverId(sysProject1.getProjectManagerId().toString());
                                        sysNotice.setReceiver(sysProject1.getProjectManager());
                                        sysNotice.setShareDeptAndUser(sysProject1.getProjectManager());
                                    } else {
                                        sysNotice.setReceiverId(sysProject1.getProjectManagerId() + "," + sysUser1.getUserId());
                                        sysNotice.setReceiver(sysProject1.getProjectManager() + "," + sysUser1.getUserName());
                                        sysNotice.setShareDeptAndUser(sysProject1.getProjectManager() + "," + sysUser1.getUserName());
                                    }
                                    sysNotice.setStatus("0");
                                    sysNotice.setNoticeType("3");
                                    sysNotice.setCreateBy(sysUser.getLoginName());
                                    sysNoticeService.insertNotice(sysNotice);

                                }
                            }
                        }
                    }

                    if ("0".equals(sysProject1.getSeizure())) {
                        //查封日期提醒
                        if (StringUtils.isNotNull(sysProject1.getSealUpDate())) {
                            Date sealUpDate = DateUtils.parseDate(DateUtils.getDateToString(nextMonth(sysProject1.getLimitationExecution(), 30)));
                            if (DateUtils.timeDifference(new Date(), sealUpDate, 0)) {
                                if (StringUtils.isNotNull(sysProject1.getProjectManager()) && StringUtils.isNotNull(sysProject1.getProjectManagerId())) {
                                    SysUser sysUser = sysUserService.selectUserById(sysProject1.getProjectManagerId());
                                    SysProjectZck sysProjectZck = sysProjectZckService.selectSysProjectZckById(sysProject1.getProjectZckId());
                                    SysNotice sysNotice = new SysNotice();
                                    sysNotice.setNoticeTitle(sysProjectZck.getZckName() + "中的" + sysProject1.getProjectName() + "的消息提醒");
                                    sysNotice.setNoticeContent(sysProjectZck.getZckName() + "中的" + sysProject1.getProjectName() + "的消息提醒");
                                    sysNotice.setReceiverId(sysProject1.getProjectManagerId() + "," + sysUser1.getUserId());
                                    sysNotice.setReceiver(sysProject1.getProjectManager() + "," + sysUser1.getUserName());
                                    if (sysProject1.getProjectManagerId().equals(sysUser1.getUserId()) || sysProject1.getProjectManagerId().equals(sysUser2.getUserId())) {
                                        sysNotice.setReceiverId(sysProject1.getProjectManagerId().toString());
                                        sysNotice.setReceiver(sysProject1.getProjectManager());
                                        sysNotice.setShareDeptAndUser(sysProject1.getProjectManager());
                                    } else {
                                        sysNotice.setReceiverId(sysProject1.getProjectManagerId() + "," + sysUser1.getUserId() + "," + sysUser2.getUserId());
                                        sysNotice.setReceiver(sysProject1.getProjectManager() + "," + sysUser1.getUserName() + "," + sysUser2.getUserName());
                                        sysNotice.setShareDeptAndUser(sysProject1.getProjectManager() + "," + sysUser1.getUserName() + "," + sysUser2.getUserName());
                                    }
                                    sysNotice.setStatus("0");
                                    sysNotice.setNoticeType("3");
                                    sysNotice.setCreateBy(sysUser.getLoginName());
                                    sysNoticeService.insertNotice(sysNotice);

                                }
                            }
                        }
                    }
                    if ("0".equals(sysProject1.getAgeing())) {
                        //执行时效提醒
                        if (StringUtils.isNotNull(sysProject1.getLimitationExecution())) {
                            Date limitationExecution = DateUtils.parseDate(DateUtils.getDateToString(nextMonth(sysProject1.getLimitationExecution(), 18)));
                            if (DateUtils.timeDifference(new Date(), limitationExecution, 0)) {
                                if (StringUtils.isNotNull(sysProject1.getProjectManager()) && StringUtils.isNotNull(sysProject1.getProjectManagerId())) {
                                    SysUser sysUser = sysUserService.selectUserById(sysProject1.getProjectManagerId());
                                    SysProjectZck sysProjectZck = sysProjectZckService.selectSysProjectZckById(sysProject1.getProjectZckId());
                                    SysNotice sysNotice = new SysNotice();
                                    sysNotice.setNoticeTitle(sysProjectZck.getZckName() + "中的" + sysProject1.getProjectName() + "执行时效为" + DateUtils.dateTime(sysProject1.getLimitationExecution()) + "的消息提醒");
                                    sysNotice.setNoticeContent(sysProjectZck.getZckName() + "中的" + sysProject1.getProjectName() + "执行时效为" + DateUtils.dateTime(sysProject1.getLimitationExecution()) + "的消息提醒");
                                    if (sysProject1.getProjectManagerId().equals(sysUser1.getUserId()) || sysProject1.getProjectManagerId().equals(sysUser2.getUserId())) {
                                        sysNotice.setReceiverId(sysProject1.getProjectManagerId().toString());
                                        sysNotice.setReceiver(sysProject1.getProjectManager());
                                        sysNotice.setShareDeptAndUser(sysProject1.getProjectManager());
                                    } else {
                                        sysNotice.setReceiverId(sysProject1.getProjectManagerId() + "," + sysUser1.getUserId());
                                        sysNotice.setReceiver(sysProject1.getProjectManager() + "," + sysUser1.getUserName());
                                        sysNotice.setShareDeptAndUser(sysProject1.getProjectManager() + "," + sysUser1.getUserName());
                                    }
                                    sysNotice.setStatus("0");
                                    sysNotice.setNoticeType("3");
                                    sysNotice.setCreateBy(sysUser.getLoginName());
                                    sysNoticeService.insertNotice(sysNotice);

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static Long nextMonth(Date date, int flag) {
        Long res = 0L;

        //日历对象
        Calendar calendar = Calendar.getInstance();
        //设置当前日期
        calendar.setTime(date);
        //月份减一为-1，加一为1
        calendar.add(Calendar.MONTH, flag);
        res = calendar.getTime().getTime();
        return res;

    }

    public static Boolean dateTime(Date date) {
        boolean flag = false;
        if (StringUtils.isNotNull(date)) {
            Date dates = DateUtils.addTime(date, 30, 2);
            if (DateUtils.timeDifference(new Date(), dates, 0)) {
                flag = true;
            }
        }
        return flag;
    }

    //投后项目利息自动更新
    public void updateInterest() {
        SysProjectContract sysProjectContract = new SysProjectContract();
        List<SysProjectContract> sysProjectContractList = sysProjectContractService.selectSysProjectContractList(sysProjectContract);
        for (SysProjectContract sysProjectContract1 : sysProjectContractList) {
            if (StringUtils.isNotNull(sysProjectContract1.getCapital()) && StringUtils.isNotNull(sysProjectContract1.getStartTime())
                    && StringUtils.isNotNull(sysProjectContract1.getEndTime()) && StringUtils.isNotNull(sysProjectContract1.getInterest())) {
                sysProjectContract1.setContractId(sysProjectContract1.getContractId());
                DecimalFormat DF = new DecimalFormat("0.000");
                //计算天数
                //利息天数/360
                BigDecimal lxDay = new BigDecimal(DateUtils.differentDays(sysProjectContract1.getStartTime(), sysProjectContract1.getEndTime())).divide(new BigDecimal(360), 10, BigDecimal.ROUND_HALF_DOWN);


                Calendar c = Calendar.getInstance();
                c.setTime(sysProjectContract1.getEndTime());
                c.add(Calendar.DAY_OF_MONTH, 1);
                //逾期和利息复利天数
                BigDecimal yqDay = new BigDecimal(DateUtils.differentDays(c.getTime(), new Date())).divide(new BigDecimal(360), 10, BigDecimal.ROUND_HALF_DOWN);


                //计算利率
                BigDecimal lv = sysProjectContract1.getInterestRate().divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_UP);


                //利息
                //本金*（合同到期日-合同起始日）/360*利率
                sysProjectContract1.setInterest((sysProjectContract1.getCapital().multiply(lxDay).multiply(lv)).setScale(3, BigDecimal.ROUND_HALF_DOWN));

                //逾期利息
                //本金*（当前日期-合同到期日）/360*利率*1.5
                sysProjectContract1.setOverdueInterest((sysProjectContract1.getCapital().multiply(yqDay)
                        .multiply(lv).multiply(new BigDecimal(1.5))).setScale(3, BigDecimal.ROUND_HALF_UP));

                //利息复利
                //利息*（当前日期-合同到期日）/360*利率*1.5
                sysProjectContract1.setCompoundInterest((sysProjectContract1.getInterest().multiply(yqDay).
                        multiply(lv).multiply(new BigDecimal(1.5))).setScale(3, BigDecimal.ROUND_HALF_UP));
                sysProjectContractService.updateSysProjectContract(sysProjectContract1);
            }
        }
    }


    //人事部门员工司龄计算
    public void secretaryLingAnalysis() throws ParseException {
        SysStaff sysStaff = new SysStaff();
        sysStaff.setStatus("0");
        List<SysStaff> sysStaffList = sysStaffService.selectSysStaffList(sysStaff);
        SysUser sysUser = sysUserService.selectUserByLoginName("miaoqing");
        for (SysStaff sysStaff1 : sysStaffList) {
            if (StringUtils.isNotNull(sysStaff1.getAccounteEntryDate())) {
                String oldYearDate = DateUtils.parseDateToStr("YYYY", sysStaff1.getAccounteEntryDate());
                String oldMonthDate = DateUtils.parseDateToStr("MM-dd", sysStaff1.getAccounteEntryDate());
                String newMonthDate = DateUtils.parseDateToStr("MM-dd", DateUtils.getNowDate());
                if (2020 <= Integer.valueOf(oldYearDate).intValue()) {
                    if (oldMonthDate.equals(newMonthDate)) {
                        sysStaff1.setSecretaryLing(Long.valueOf(DateUtils.yearDateDiff(DateUtils.dateTime(sysStaff1.getAccounteEntryDate()), DateUtils.dateTime(DateUtils.getNowDate()))));
                        sysStaffService.updateSysStaff(sysStaff1);
                        SysNotice sysNotice = new SysNotice();
                        sysNotice.setNoticeTitle(sysStaff1.getStaffName() + "司龄增加1年");
                        sysNotice.setNoticeType("3");
                        sysNotice.setStatus("0");
                        sysNotice.setReceiver(sysUser.getUserName());
                        sysNotice.setReceiverId(sysUser.getUserId().toString());
                        sysNotice.setCreateBy(sysUser.getLoginName());
                        sysNotice.setShareDeptAndUser(sysUser.getUserName());
                        sysNoticeService.insertNotice(sysNotice);
                    }
                } else {
                    if ("01-01".equals(newMonthDate)) {
                        sysStaff1.setSecretaryLing(Long.valueOf(DateUtils.yearDateDiff(DateUtils.dateTime(sysStaff1.getAccounteEntryDate()), DateUtils.dateTime(DateUtils.getNowDate()))));
                        sysStaffService.updateSysStaff(sysStaff1);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws ParseException {
        int year = DateUtils.yearDateDiff("2020-10-04", "2021-07-01");
        System.out.print(year);
    }
}
