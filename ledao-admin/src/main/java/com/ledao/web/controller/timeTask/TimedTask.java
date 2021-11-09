package com.ledao.web.controller.timeTask;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ledao.activity.dao.SysApplyIn;
import com.ledao.activity.dao.SysApplyWorkflow;
import com.ledao.activity.dao.SysWorkflowProcess;
import com.ledao.activity.mapper.SysApplyWorkflowMapper;
import com.ledao.activity.mapper.SysWorkflowProcessMapper;
import com.ledao.activity.service.ISysApplyInService;
import com.ledao.common.constant.WeChatConstants;
import com.ledao.common.message.Template;
import com.ledao.common.message.TemplateParam;
import com.ledao.common.message.WechatMessageUtil;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.http.CommonUtil;
import com.ledao.common.utils.qrCode.WxQrCode;
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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysJournalService sysJournalService;

    @Autowired
    private ISysDictDataService sysDictDataService;

    @Autowired
    private ISysProjectProgressService sysProjectProgressService;

    @Autowired
    private IJudicialUpdataService judicialUpdataService;

    @Autowired
    private ISysApplyInService sysApplyInService;

    @Autowired
    private SysApplyWorkflowMapper sysApplyWorkflowMapper;

    @Autowired
    private SysWorkflowProcessMapper sysWorkflowProcessMapper;

    @Autowired
    private ISysConfigService sysConfigService;


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
                                    for (String string : sysProject1.getProjectManagerId().split(",")) {
                                        SysUser sysUser = sysUserService.selectUserById(Long.valueOf(string));
                                        sysNotice.setCreateBy(sysUser.getLoginName());
                                    }
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
                                    for (String string : sysProject1.getProjectManagerId().split(",")) {
                                        SysUser sysUser = sysUserService.selectUserById(Long.valueOf(string));
                                        sysNotice.setCreateBy(sysUser.getLoginName());
                                    }
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
                                    SysProjectZck sysProjectZck = sysProjectZckService.selectSysProjectZckById(sysProject1.getProjectZckId());
                                    SysNotice sysNotice = new SysNotice();
                                    sysNotice.setNoticeTitle(sysProjectZck.getZckName() + "中的" + sysProject1.getProjectName() + "执行时效为" + DateUtils.dateTime(sysProject1.getLimitationExecution()) + "的消息提醒");
                                    sysNotice.setNoticeContent(sysProjectZck.getZckName() + "中的" + sysProject1.getProjectName() + "执行时效为" + DateUtils.dateTime(sysProject1.getLimitationExecution()) + "的消息提醒");
                                    if (sysProject1.getProjectManagerId().equals(sysUser1.getUserId()) || sysProject1.getProjectManagerId().equals(sysUser2.getUserId())) {
                                        sysNotice.setReceiverId(sysProject1.getProjectManagerId());
                                        sysNotice.setReceiver(sysProject1.getProjectManager());
                                        sysNotice.setShareDeptAndUser(sysProject1.getProjectManager());
                                    } else {
                                        sysNotice.setReceiverId(sysProject1.getProjectManagerId() + "," + sysUser1.getUserId());
                                        sysNotice.setReceiver(sysProject1.getProjectManager() + "," + sysUser1.getUserName());
                                        sysNotice.setShareDeptAndUser(sysProject1.getProjectManager() + "," + sysUser1.getUserName());
                                    }

                                    for (String string : sysProject1.getProjectManagerId().split(",")) {
                                        SysUser sysUser = sysUserService.selectUserById(Long.valueOf(string));
                                        sysNotice.setCreateBy(sysUser.getLoginName());
                                    }
                                    sysNotice.setStatus("0");
                                    sysNotice.setNoticeType("3");
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
     * 获取公众号的unionid及openid
     */
    public void getWeChatComOpenid() {
        String accessToken = null;
        try {
            accessToken = configService.getWechatComAccessToken();
            com.alibaba.fastjson.JSONObject res = WechatMessageUtil.getAllUser(accessToken, "");
            List<String> openIds = new ArrayList<>();
            Map m = (Map) res.get("data");
            openIds = (List<String>) m.get("openid");
//            WechatMessageUtil.batchGetUserUnionId(accessToken,openIds);
            String batchUrl = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
            String url = batchUrl.replace("ACCESS_TOKEN", accessToken);

       /* MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("access_token", accessToken);
        URI batchUri = HttpUtils.getURIwithParams(batchUrl, params);*/


            int maxCount = 100;
            int cout = openIds.size() / maxCount;
            if (openIds.size() % maxCount != 0) {
                cout++;
            }
            if (openIds.size() < maxCount) {
                maxCount = openIds.size();
            }
            for (int j = 0; j < cout; j++) {
                JSONObject request = new JSONObject();
                JSONArray openidList = new JSONArray();
                for (int i = 0; i < maxCount; i++) {
                    if (3 * j + i == openIds.size()) {
                        continue;
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("lang", "zh_CN");
                    jsonObject.put("openid", openIds.get(maxCount * j + i));
                    openidList.add(jsonObject);
                }
                request.put("user_list", openidList);
                JSONObject jsonResult = CommonUtil.wxMessageModeSendUrl(request, url);
                JSONArray data = (JSONArray) jsonResult.get("user_info_list");
                for (int i = 0; i < data.size(); i++) {
                    String openid = (String) data.getJSONObject(i).get("openid");
                    String unionid = (String) data.getJSONObject(i).get("unionid");
                    SysUser use = new SysUser();
                    use.setUnionId(unionid);
                    List<SysUser> uList = userMapper.selectUserList(use);
                    if (uList != null && uList.size() > 0) {
                        SysUser newUser = uList.get(0);
                        if (StringUtils.isEmpty(newUser.getUnionId())) {
                            newUser.setComOpenId(openid);
                            userMapper.updateUser(newUser);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 周五将日志推送给小组长
     */
    public void sendJourToXzz() {
        SysUser sysUser = new SysUser();
        sysUser.setRoleKey("thbzz");
        List<SysUser> users = sysUserService.selectUserByRoleKey(sysUser);
        List<String> loginNames = new ArrayList<>();
        for (SysUser u : users) {
            loginNames.add(u.getLoginName());
        }
        String date = DateUtils.formatDateByPattern(new Date(), "yyyyMMdd");
        SysJournal sj = new SysJournal();
        sj.getParams().put("beginTime", date);
        ;
        List<SysJournal> jours = sysJournalService.selectSysJournalList(sj);
        for (SysJournal j : jours) {
            if (loginNames.contains(j.getCreateBy()) && StringUtils.isNotEmpty(j.getProId())) {
                SysUser user = sysUserService.selectUserByLoginName(j.getCreateBy());
                SysProject sysProject = new SysProject();
                sysProject.setProjectId(Long.valueOf(j.getProId()));
                sysProject.setProjectManagerId(user.getUserId().toString());
                List<SysProject> pros = sysProjectService.selectSysProjectList(sysProject);
                if (pros != null && pros.size() > 0) {
//                    pros.get(0).getProjectId();
                    SysProjectProgress pro = new SysProjectProgress();
                    pro.setProjectManagementId(pros.get(0).getProjectId());
                    pro.setProgress(j.getWorkDetail());
                    pro.setDelFlag("0");
                    pro.setProject("Y");
                    sysProjectProgressService.insertSysProjectProgress(pro);
                }
            }
        }
    }

    public void xmlzb() {
        try {
            //项目流转表自投类
            xmlzbAuto();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //项目流转表服务类
            xmlzbServe();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 项目流转表消息推送（自投类每天十点）
     *
     * @throws ParseException
     */
    private void xmlzbAuto() throws ParseException {
        Map<String, String> parmStr = new HashMap<>();
        parmStr.put("first", "您有一个项目流转表自投类任务提醒");
        SysProjectUncollectedMoney sysProjectUncollectedMoney = new SysProjectUncollectedMoney();
        sysProjectUncollectedMoney.setState("否");
        List<SysProjectUncollectedMoney> list = sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyList(sysProjectUncollectedMoney);
        for (SysProjectUncollectedMoney mon : list) {
            if (DateUtils.differentDays(new Date(), mon.getTime()) < 8) {
                if (DateUtils.differentDays(new Date(), mon.getTime()) == 7 || DateUtils.differentDays(mon.getTime(), new Date()) % 7 == 0) {
                    List<SysUser> users = new ArrayList<>();
                    List<String> userStrs = new ArrayList<>();
                    users.add(sysUserService.selectUserByLoginName("wangziyuan"));
                    userStrs.add("wangziyuan");
                    /*users.add(sysUserService.selectUserByLoginName("zhangyi"));
                    userStrs.add("zhangyi");
                    users.add(sysUserService.selectUserByLoginName("jianghui"));
                    userStrs.add("jianghui");*/
                    SysUser u = sysUserService.selectUserByLoginName(mon.getCreateBy());
                    if (!userStrs.contains(u.getLoginName())) {
                        users.add(u);
                        userStrs.add(u.getLoginName());
                    }
                    if (u.getDirectorId() != null) {
                        SysUser udirector = sysUserService.selectUserById(u.getDirectorId());
                        if (!userStrs.contains(udirector.getLoginName())) {
                            users.add(udirector);
                            userStrs.add(udirector.getLoginName());
                        }
                    }
                    SysProjectmanagent manage = sysProjectmanagentService.selectSysProjectmanagentById(mon.getProjectManagementId());
                    parmStr.put("word1", StringUtils.isNotEmpty(manage.getProjectManagementName()) ? manage.getProjectManagementName() : "");
                    parmStr.put("word2", "-");
                    parmStr.put("word3", "截止日期" + DateUtils.formatDateByPattern(mon.getTime(), "yyyy-MM-dd") + "," + mon.getFundType() + "/" + mon.getAmountMoney());
                    System.out.println(parmStr);
                    sendJournalTask(users, parmStr);
                }
            }
        }

    }

    /**
     * 项目流转表服务类
     *
     * @throws Exception
     */
    private void xmlzbServe() throws Exception {
        String dateIn = DateUtils.formatDateByPattern(new Date(), "MMdd");
        String[] dateArray = {"0329", "0330", "0331", "0628", "0629", "0630", "0928", "0929", "0930", "1229", "1230", "1231", "1103"};
        String[] keys = sysConfigService.selectConfigByKey("sysXmlzb").split(",");
        if (!Arrays.asList(keys).contains(dateIn)) {
            return;
        }
        Map<String, String> parmStr = new HashMap<>();
        parmStr.put("first", "您有一个项目流转表服务类任务提醒");
        SysCoverCharge sp = new SysCoverCharge();
        sp.setState("否");
        List<SysCoverCharge> list = sysCoverChargeService.selectSysCoverChargeList(sp);
        for (SysCoverCharge mon : list) {
            String date = DateUtils.formatDateByPattern(mon.getPaidDate(), "yyyyMM");
            if (date.equals(DateUtils.getPreMonthByCount(1)) || date.equals(DateUtils.getPreMonthByCount(2)) || date.equals(DateUtils.getPreMonthByCount(3))) {
//            if (DateUtils.differentDays(new Date(),mon.getPaidDate())==7 || DateUtils.differentDays(mon.getPaidDate(),new Date())%7==0){
                List<SysUser> users = new ArrayList<>();
                List<String> userStrs = new ArrayList<>();
                users.add(sysUserService.selectUserByLoginName("wangziyuan"));
                userStrs.add("wangziyuan");
                /*users.add(sysUserService.selectUserByLoginName("zhangyi"));
                userStrs.add("zhangyi");
                users.add(sysUserService.selectUserByLoginName("jianghui"));
                userStrs.add("jianghui");*/
                SysUser u = sysUserService.selectUserByLoginName(mon.getCreateBy());
                if (!userStrs.contains(u.getLoginName())) {
                    users.add(u);
                    userStrs.add(u.getLoginName());
                }
                if (u.getDirectorId() != null) {
                    SysUser udirector = sysUserService.selectUserById(u.getDirectorId());
                    if (!userStrs.contains(udirector.getLoginName())) {
                        users.add(udirector);
                        userStrs.add(udirector.getLoginName());
                    }
                }
//                users.add(sysUserService.selectUserById(u.getDirectorId()));
                SysProjectmanagent manage = sysProjectmanagentService.selectSysProjectmanagentById(mon.getProjectManagementId());
                parmStr.put("word1", manage.getProjectManagementName());
                parmStr.put("word2", "-");
                parmStr.put("word3", "截止日期" + DateUtils.formatDateByPattern(mon.getPaidDate(), "yyyy-MM-dd") + "," + mon.getFundType() + "/" + mon.getAmountPaid());
                System.out.println(parmStr);
                sendJournalTask(users, parmStr);
            }
        }
    }


    private List<SysUser> getUsers(String roleKey) {
        List<SysUser> users = new ArrayList<>();
        SysRole r = new SysRole();
        r.setRoleKey(roleKey);
        List<SysRole> ros = roleMapper.selectRoleList(r);
        if (ros != null && ros.size() > 0) {
            SysUser userRoles = new SysUser();
            userRoles.setRoleId(ros.get(0).getRoleId());
            List<SysUser> us = userMapper.selectAllocatedList(userRoles);
            for (SysUser u : us) {
                users.add(u);
            }
        }
        return users;
    }

    /**
     * 日常任务
     *
     * @throws ParseException
     */
    public void dailyTask() throws ParseException {
        SysManageTask sysManageTask = new SysManageTask();
        List<SysManageTask> task = sysManageTaskService.selectSysManageTaskList(sysManageTask);
        for (SysManageTask s : task) {
            if (s.getRealEndTime() == null && s.getPlanEndTime() != null) {
                long planEndTime = s.getPlanEndTime().getTime();
                long realEndTime = (new Date()).getTime();
                if (realEndTime - planEndTime > 0) {
                    long days = DateUtils.differentDays(s.getPlanEndTime(), new Date());
                    s.setOverDay(days);
                    s.setTaskStatu("later");
                    sysManageTaskService.updateTask(s);
                }
            }
        }

        //查询超时未归还
        Map<String, String> parmStr = new HashMap<>();
        parmStr.put("first", "您有一个档案超时未归还提醒");
        parmStr.put("word2", "请及时归还档案");
        List<SysApplyIn> sysApply = new ArrayList<>();
        List<SysApplyIn> sysApplyIn = sysApplyInService.selectNotReturned();

        for (SysApplyIn sysApp : sysApplyIn) {
            if (DateUtils.differentDays(sysApp.getPlanReturnTime(), new Date()) % 3 == 0) {
                parmStr.put("word1", "档案超时未还提醒");
                List<SysUser> users = new ArrayList<>();
                List<SysUser> usersAdmin = new ArrayList<>();
                users.add(sysUserService.selectUserByLoginName(sysApp.getApplyUser()));
                //推送给处理他的档案管理员
                SysApplyWorkflow workflow = new SysApplyWorkflow();
                workflow.setApplyId(sysApp.getApplyId());
                List<SysApplyWorkflow> workflows = sysApplyWorkflowMapper.selectSysApplyWorkflowList(workflow);
                if (workflows != null && workflows.size() > 0) {
                    usersAdmin.add(sysUserService.selectUserByLoginName(workflows.get(0).getApproveUser()));
                }
                String planReturnTime = "-";
                String realGetTime = "-";
                if (StringUtils.isNotNull(sysApp.getPlanReturnTime())) {
                    planReturnTime = DateUtils.formatDateByPattern(sysApp.getPlanReturnTime(), "yyyy-MM-dd");
                }
                SysWorkflowProcess w = new SysWorkflowProcess();
                w.setApplyId(sysApp.getApplyId());
                w.setShowLable("8");
                List<SysWorkflowProcess> wfs = sysWorkflowProcessMapper.selectSysWorkflowProcessList(w);
                if (wfs != null && wfs.size() > 0) {
                    if (wfs.get(0).getApplyTime() != null) {
                        realGetTime = DateUtils.formatDateByPattern(wfs.get(0).getApplyTime(), "yyyy-MM-dd");
                    }
                }
                parmStr.put("word5", "档案出库时间：" + realGetTime + "至" + planReturnTime);
                sendDailyUsalTask(users, parmStr);
                parmStr.put("first", sysUserService.selectUserByLoginName(sysApp.getApplyUser()).getUserName() + "有一个档案超时未归还");
                parmStr.put("word2", "请及时追踪档案");
                sendDailyUsalTask(usersAdmin, parmStr);
            }
        }

    }

    /**
     * 每天21点提醒所有未填写日志的人填日志
     *
     * @throws ParseException
     */
    public void dailyWorkTask() throws ParseException {
        try {
            dailyTask();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //判断是否是周五
        boolean isWeekEndDay = false;
        boolean isWeekRest = false;
        String weekStr = DateUtils.getWeekOfDate(new Date());
        if ("星期五".equals(weekStr)) {
            isWeekEndDay = true;
        }
        if ("星期六".equals(weekStr) || "星期日".equals(weekStr)) {
            isWeekRest = true;
        }

        if (!isWeekRest) {
            String date = DateUtils.formatDateByPattern(new Date(), "yyyyMMdd");
            List<SysUser> users = sysUserService.selectAllUserDepRole();
            Map<String, String> parmStr = new HashMap<>();
            parmStr.put("first", "您有一个日志任务提醒");
            parmStr.put("word1", "日志未填写提醒");
            parmStr.put("word2", "请及时填写日志");
            List<String> sendUsers = new ArrayList<>();
            List<SysUser> us = new ArrayList<>();
            for (SysUser u : users) {
                if (StringUtils.isNotNull(u.getComOpenId())) {
                    SysJournal sj = new SysJournal();
                    sj.setCreateBy(u.getLoginName());
                    sj.getParams().put("beginTime", date);
                    List<SysJournal> jours = sysJournalService.selectSysJournalList(sj);
                    if (jours == null || jours.size() == 0) {
                        if (isWeekEndDay && "1".equals(u.getRemainFlag())) {
                            if (!sendUsers.contains(u.getLoginName())) {
                                //每周五提醒
                                us.add(u);
                                sendUsers.add(u.getLoginName());
                            }

                        } else if ("0".equals(u.getRemainFlag())) {
                            //每天提醒
                            if (!sendUsers.contains(u.getLoginName())) {
                                //每周五提醒
                                us.add(u);
                                sendUsers.add(u.getLoginName());
                            }
                        }
                        /*List<SysUser> us = new ArrayList<>();
                        us.add(u);
                        sendDailyUsalTask(us,parmStr);*/
                    }
                }
            }
            sendDailyUsalTask(us, parmStr);
        }
    }

    /**
     * 投后总任务消息推送
     *
     * @throws ParseException
     */
    public void totalTask() throws ParseException {

        SysManageTask sysManageTask = new SysManageTask();
        sysManageTask.setTaskType("0");
        List<SysManageTask> task = sysManageTaskService.selectSysManageTaskList(sysManageTask);
        for (SysManageTask s : task) {
            if (s.getRealEndTime() == null && s.getPlanEndTime() != null) {
                long planEndTime = s.getPlanEndTime().getTime();
                long realEndTime = (new Date()).getTime();
                if (realEndTime - planEndTime > 0) {
                    long days = DateUtils.differentDays(s.getPlanEndTime(), new Date());
                    s.setOverDay(days);
                    s.setTaskStatu("later");
                    sysManageTaskService.updateTask(s);
                }
            }
            //向许凯、江辉发送消息推送总任务
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
            String date = DateUtils.formatDateByPattern(new Date(), "yyyyMM");
            String planBeginDate = dateFormat.format(s.getPlanBeginTime());
            if (date.equals(planBeginDate)) {
//                List<SysUser> users = getUsers("thbManager");
                List<SysUser> users = new ArrayList<>();
                users.add(userMapper.selectUserByLoginName("xukai"));
                users.add(userMapper.selectUserByLoginName("jianghui"));
                sendTaskMsg(users, s, "您有一个投后项目总任务提醒");
            }
        }
    }

    /**
     * 日志推送公共方法
     *
     * @param
     */
    private void sendDailyJournalTask(List<SysUser> us, Map<String, String> parmStr) {
        for (SysUser u : us) {
            if (StringUtils.isNotEmpty(u.getComOpenId()) && !"n".equals(u.getIsDailyRemind())) {
                //发送消息到投后部部门经理
                JSONObject parm = new JSONObject();
                //发布人
                parm.put("first", parmStr.get("first"));
                parm.put("word1", parmStr.get("word1"));
//                        计划时间
                parm.put("word2", StringUtils.isNotEmpty(parmStr.get("word2")) ? parmStr.get("word2") : "-");
//                        任务名称
                parm.put("word3", StringUtils.isNotEmpty(parmStr.get("word3")) ? parmStr.get("word3") : "-");
//                        任务状态
                parm.put("word4", StringUtils.isNotEmpty(parmStr.get("word4")) ? parmStr.get("word4") : "-");
                parm.put("word5", "-");

//                        任务接收人
                parm.put("toUser", u.getComOpenId());
//                parm.put("toUser","o_gyCwh9IvRICHvI_Z9pWejZ3-nw");
                String accessToken = configService.getWechatComAccessToken();
                parm.put("accessToken", accessToken);
                // 创建名称为投后队列
                Queue queue = new ActiveMQQueue("ThQueueCommon");
                String dataStr = JSONObject.toJSONString(parm);
                System.out.println("--------------------推送日报填写提醒给：" + u.getLoginName() + "----------------------");
                // 向队列发送消息
                jmsMessagingTemplate.convertAndSend(queue, dataStr);
                stadingTime();
            }
        }
    }

    /**
     * 日志推送公共方法
     *
     * @param
     */
    private void sendDailyUsalTask(List<SysUser> us, Map<String, String> parmStr) {
        for (SysUser u : us) {
            if (StringUtils.isNotEmpty(u.getComOpenId()) && !"n".equals(u.getIsDailyRemind())) {
                //发送消息到投后部部门经理
                JSONObject parm = new JSONObject();
                //发布人
                parm.put("first", parmStr.get("first"));
                parm.put("word1", parmStr.get("word1"));
//                        计划时间
                parm.put("word2", StringUtils.isNotEmpty(parmStr.get("word2")) ? parmStr.get("word2") : "-");
//                        任务名称
                parm.put("word3", StringUtils.isNotEmpty(parmStr.get("word3")) ? parmStr.get("word3") : "-");
//                        任务状态
                parm.put("word4", StringUtils.isNotEmpty(parmStr.get("word4")) ? parmStr.get("word4") : "-");
                parm.put("word5", StringUtils.isNotEmpty(parmStr.get("word5")) ? parmStr.get("word5") : "-");

//                        任务接收人
                parm.put("toUser", u.getComOpenId());
//                parm.put("toUser","o_gyCwh9IvRICHvI_Z9pWejZ3-nw");
                parm.put("url", "common/journalInfo/journal");
                String accessToken = configService.getWechatComAccessToken();
                parm.put("accessToken", accessToken);
                // 创建名称为投后队列
                Queue queue = new ActiveMQQueue("ThQueueCommonUsal");
                String dataStr = JSONObject.toJSONString(parm);
                System.out.println("--------------------推送提醒给：" + u.getLoginName() + "----------------------");
                // 向队列发送消息
                jmsMessagingTemplate.convertAndSend(queue, dataStr);
                stadingTime();
            }
        }
    }

    /**
     * 日志推送公共方法
     *
     * @param
     */
    private void sendJournalTask(List<SysUser> us, Map<String, String> parmStr) {
        for (SysUser u : us) {
            if (StringUtils.isNotEmpty(u.getComOpenId())) {
                //发送消息到投后部部门经理
                JSONObject parm = new JSONObject();
                //发布人
                parm.put("first", parmStr.get("first"));
                parm.put("word1", parmStr.get("word1"));
//                        计划时间
                parm.put("word2", StringUtils.isNotEmpty(parmStr.get("word2")) ? parmStr.get("word2") : "-");
//                        任务名称
                parm.put("word3", StringUtils.isNotEmpty(parmStr.get("word3")) ? parmStr.get("word3") : "-");
//                        任务状态
                parm.put("word4", StringUtils.isNotEmpty(parmStr.get("word4")) ? parmStr.get("word4") : "-");
                parm.put("word5", "-");

//                        任务接收人
                parm.put("toUser", u.getComOpenId());
//                parm.put("toUser","o_gyCwh9IvRICHvI_Z9pWejZ3-nw");
                String accessToken = configService.getWechatComAccessToken();
                parm.put("accessToken", accessToken);
                // 创建名称为投后队列
                Queue queue = new ActiveMQQueue("ThQueueCommon");
                String dataStr = JSONObject.toJSONString(parm);
                // 向队列发送消息
                jmsMessagingTemplate.convertAndSend(queue, dataStr);
                stadingTime();
                System.out.println("--------------------推送提醒给：" + u.getLoginName() + "----------------------");
            }
        }
    }

    private void sendTaskMsg(List<SysUser> users, SysManageTask s, String firstWord) {
        for (SysUser u : users) {
            if (StringUtils.isNotEmpty(u.getComOpenId())) {
                //发送消息到投后部部门经理
                JSONObject parm = new JSONObject();
                //发布人
                parm.put("first", firstWord);
                if ("0".equals(s.getTaskType())) {
                    parm.put("word1", s.getZckName());
                } else {
                    parm.put("word1", s.getProjectName());
                }

//                        计划时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                parm.put("word2", sdf.format(s.getPlanBeginTime()) + " 至 " + sdf.format(s.getPlanEndTime()) + (StringUtils.isNotEmpty(s.getNodeStatu()) ? "(" + s.getNodeStatu() + ")" : ""));
                parm.put("word3", "回收金额" + s.getPlanBackMoney());
                //                        任务名称
//                parm.put("word3","许凯");
//                        任务状态
                parm.put("word4", StringUtils.isNotEmpty(s.getTaskStatu()) ? sysDictDataService.selectDictLabel("manage_task", s.getTaskStatu()) : "-");


//                        任务接收人
                parm.put("toUser", u.getComOpenId());
//                parm.put("toUser","o_gyCwh9IvRICHvI_Z9pWejZ3-nw");
                String accessToken = configService.getWechatComAccessToken();
                parm.put("accessToken", accessToken);
                // 创建名称为投后队列
                Queue queue = new ActiveMQQueue("ThQueueCommon");
                String dataStr = JSONObject.toJSONString(parm);
                // 向队列发送消息
                jmsMessagingTemplate.convertAndSend(queue, dataStr);
                stadingTime();
            }

        }
    }

    private void stadingTime() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("-------设定要指定任务--------");
            }
        }, 10000);
    }

    /**
     * 投后子任务消息推送
     *
     * @throws ParseException
     */
    public void subTask() throws ParseException {
        SysProjectZck sysProjectZck = new SysProjectZck();
        List<SysProjectZck> zcks = sysProjectZckService.selectSysProjectZckAll(sysProjectZck);
        for (SysProjectZck zc : zcks) {
            SysProject sysProject = new SysProject();
            sysProject.setProjectZckId(zc.getProjectZckId());
            List<SysProject> pros = sysProjectService.selectSysProjectList(sysProject);
            for (SysProject p : pros) {
                SysManageTask sysManageTask = new SysManageTask();
                sysManageTask.setProId(p.getProjectId());
                sysManageTask.setTaskType("1");
                List<SysManageTask> tasks = sysManageTaskService.selectSysManageTaskList(sysManageTask);
                for (SysManageTask t : tasks) {
                    //子任务，只对未成功的预计结束-15天的件进行消息推送，推送对象。项目经理、徐凯、江辉
                    if (StringUtils.isNull(t.getRealEndTime()) && DateUtils.differentDays(new Date(), t.getPlanEndTime()) == 15) {
                        List<SysUser> users = new ArrayList<>();
                        users.add(userMapper.selectUserByLoginName("xukai"));
                        users.add(userMapper.selectUserByLoginName("jianghui"));
                        for (String string : p.getProjectManagerId().split(",")) {
                            users.add(userMapper.selectUserById(Long.valueOf(string)));
                        }
                        sendTaskMsg(users, t, "您有一个投后项目子任务提醒");
                    }
                }
            }
        }
    }

    /**
     * 网拍线索消息提醒
     *
     * @throws ParseException
     */
    public void racquetClues() throws ParseException {

        JudicialUpdata jud = new JudicialUpdata();
        List<JudicialUpdata> list = judicialUpdataService.selectJudicialUpdataList(jud);

        Map<String, String> parmStr = new HashMap<>();
        List<SysUser> usersAll = new ArrayList<>();
        parmStr.put("first", "您有一个网拍线索消息提醒");
        /*users.add(sysUserService.selectUserByLoginName("wangziyuan"));
        users.add(sysUserService.selectUserByLoginName("zhangyi"));
        users.add(sysUserService.selectUserByLoginName("jianghui"));*/
//        SysUser u = sysUserService.selectUserByLoginName(mon.getCreateBy());
//        users.add(u);
//        users.add(sysUserService.selectUserById(u.getDirectorId()));
//        SysProjectmanagent manage = sysProjectmanagentService.selectSysProjectmanagentById(mon.getProjectManagementId());
        parmStr.put("word1", "此次变更数量为：" + list.size());
        parmStr.put("word2", "-");
        parmStr.put("word3", "-");
        parmStr.put("word4", "-");
        System.out.println(parmStr);
        List<SysUser> users = getUsers("thbManager");
        usersAll.addAll(users);
        List<SysUser> users2 = getUsers("thbManager2");
        usersAll.addAll(users2);
        List<SysUser> users3 = getUsers("thbzz");
        usersAll.addAll(users3);
        List<SysUser> users4 = getUsers("thbCommon");
        usersAll.addAll(users4);
        sendDailyUsalTask(usersAll, parmStr);
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


    public static void main(String[] args) throws ParseException {
        /*int year = DateUtils.yearDateDiff("2020-10-04", "2021-07-01");
        System.out.print(year);*/
        testJson();
    }

    public static void testJson() {
        List<String> strs = new ArrayList<>();
        strs.add("1111111111");
        strs.add("2222222222");
        strs.add("333333333");
        strs.add("4444444444");
        strs.add("555555555");
        strs.add("6666666666");
        strs.add("777777777777");
        strs.add("88888888");

        int cout = strs.size() / 3;
        if (strs.size() % 3 != 0) {
            cout++;
        }

        for (int j = 0; j < cout; j++) {
            for (int i = 0; i < 3; i++) {
                if (3 * j + i == strs.size()) {
                    continue;
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("lang", "zh_CN");
                jsonObject.put("openid", strs.get(3 * j + i));
                System.out.println(jsonObject.toJSONString());
            }
        }


        /*JSONObject data1 = new JSONObject();
        data1.put("openid","111111111111");
        data1.put("unionid","432fewf");
        JSONObject data2 = new JSONObject();
        data2.put("openid","222222222");
        data2.put("unionid","432fewf");
        JSONObject data3 = new JSONObject();
        data3.put("openid","3333333333333");
        data3.put("unionid","432fewf");
        JSONObject data4 = new JSONObject();
        data4.put("openid","444444444444");
        data4.put("unionid","432fewf");
        JSONObject data5 = new JSONObject();
        data5.put("openid","55555555555555");
        data5.put("unionid","432fewf");
        JSONArray jarr = new JSONArray();
        jarr.add(data1);
        jarr.add(data2);
        jarr.add(data3);
        jarr.add(data4);
        jarr.add(data5);
        String jsonStr = JSONArray.toJSONString(jarr);
        System.out.println(jsonStr);

        JSONArray data = jarr;

        int cout=data.size()/2;
        for(int j=0;j<cout;j++){
            for (int i=0;i<2;i++){
                String openid = (String) data.getJSONObject(i).get("openid");
                String unionid = (String) data.getJSONObject(i).get("unionid");
                System.out.println(openid);
                System.out.println(unionid);
            }
        }*/



       /* for(int i=0;i<data.size();i++) {
            String openid = (String) data.getJSONObject(i).get("openid");
            String unionid = (String) data.getJSONObject(i).get("unionid");
            System.out.println(openid);
            System.out.println(unionid);
            *//*SysUser use = new SysUser();
            use.setUnionId(unionid);
            List<SysUser> uList = userMapper.selectUnallocatedList(use);
            if (uList!=null && uList.size()>0){
                SysUser newUser =  uList.get(0);
                if (StringUtils.isEmpty(newUser.getUnionId())){
                    newUser.setComOpenId(openid);
                    userMapper.updateUser(newUser);
                }
            }*//*
        }*/
    }
}
