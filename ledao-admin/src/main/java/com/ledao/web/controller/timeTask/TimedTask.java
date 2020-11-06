package com.ledao.web.controller.timeTask;

import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.framework.web.dao.server.Sys;
import com.ledao.system.dao.*;
import com.ledao.system.service.*;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

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

    public void timeTask() {
        //应收应付未收服务费消息提醒
        projectUncollectedMoney();
        //目标回收金额消息提醒
        projectTargetRecover();

        //投后项目消息提醒
        sysProject();
    }

    public void projectUncollectedMoney() {
        //查询出接收人名称和ID
        SysUser sysUser = sysUserService.selectUserByLoginName("wangziyuan");
        //应收应付未收服务费消息提醒
        SysProjectUncollectedMoney sysProjectUncollectedMoney = new SysProjectUncollectedMoney();
        List<SysProjectUncollectedMoney> sysProjectUncollectedMoneyList = sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyList(sysProjectUncollectedMoney);
        for (SysProjectUncollectedMoney projectUncollectedMoney : sysProjectUncollectedMoneyList) {
            if (StringUtils.isNotNull(projectUncollectedMoney.getTime())) {
                if (DateUtils.timeDifference(new Date(), projectUncollectedMoney.getTime(), 90)) {
                    if (StringUtils.isNotNull(projectUncollectedMoney.getProjectManagementId())) {
                        SysProjectmanagent sysProjectmanagent = sysProjectmanagentService.selectSysProjectmanagentById(projectUncollectedMoney.getProjectManagementId());
                        if (StringUtils.isNotNull(sysProjectmanagent) && StringUtils.isNotNull(sysProjectmanagent.getProjectManagementName())) {
                            SysNotice sysNotice = new SysNotice();
                            sysNotice.setNoticeTitle(sysProjectmanagent.getProjectManagementName() + "在" + DateUtils.dateTime(projectUncollectedMoney.getTime())
                                    + "有一笔：" + projectUncollectedMoney.getFundType() + "，金额为：" + projectUncollectedMoney.getAmountMoney()
                                    + "，距离天数为：" + DateUtils.differentDays(new Date(), projectUncollectedMoney.getTime()));
                            sysNotice.setNoticeType("3");
                            sysNotice.setNoticeContent(sysProjectmanagent.getProjectManagementName() + "在" + DateUtils.dateTime(projectUncollectedMoney.getTime())
                                    + "有一笔：" + projectUncollectedMoney.getFundType() + "，金额为：" + projectUncollectedMoney.getAmountMoney()
                                    + "，距离天数为：" + DateUtils.differentDays(new Date(), projectUncollectedMoney.getTime()));
                            sysNotice.setStatus("0");
                            sysNotice.setReceiverId(sysUser.getUserId().toString());
                            sysNotice.setReceiver(sysUser.getUserName());
                            sysNotice.setCreateBy(sysUser.getLoginName());
                            sysNoticeService.insertNotice(sysNotice);
                        }
                    }
                }
            }
        }
    }

    public void projectTargetRecover() {
        //查询出接收人名称和ID
        SysUser sysUser = sysUserService.selectUserByLoginName("wangziyuan");
        //目标回收金额消息提醒
        SysProjectTargetrecover sysProjectTargetrecover = new SysProjectTargetrecover();
        List<SysProjectTargetrecover> sysProjectTargetrecoverList = sysProjectTargetrecoverService.selectSysProjectTargetrecoverList(sysProjectTargetrecover);
        for (SysProjectTargetrecover projectTargetrecover : sysProjectTargetrecoverList) {
            if (StringUtils.isNotNull(projectTargetrecover)) {
                if (StringUtils.isNotNull(projectTargetrecover.getTargetRecoveryDate())) {
                    if (DateUtils.timeDifference(new Date(), projectTargetrecover.getTargetRecoveryDate(), 90)) {
                        if (StringUtils.isNotNull(projectTargetrecover.getProjectManagementId())) {
                            SysProjectmanagent sysProjectmanagent = sysProjectmanagentService.selectSysProjectmanagentById(projectTargetrecover.getProjectManagementId());
                            if (StringUtils.isNotNull(sysProjectmanagent) && StringUtils.isNotNull(sysProjectmanagent.getProjectManagementName())) {
                                SysNotice sysNotice = new SysNotice();
                                sysNotice.setNoticeTitle(sysProjectmanagent.getProjectManagementName() + "在" + DateUtils.dateTime(projectTargetrecover.getTargetRecoveryDate())
                                        + "有一笔：目标回收金额，金额为：" + projectTargetrecover.getTargetRecoveryAmount()
                                        + "，距离天数为：" + DateUtils.differentDays(new Date(), projectTargetrecover.getTargetRecoveryDate()));
                                sysNotice.setNoticeType("3");
                                sysNotice.setNoticeContent(sysProjectmanagent.getProjectManagementName() + "在" + DateUtils.dateTime(projectTargetrecover.getTargetRecoveryDate())
                                        + "有一笔：目标回收金额，金额为：" + projectTargetrecover.getTargetRecoveryAmount()
                                        + "，距离天数为：" + DateUtils.differentDays(new Date(), projectTargetrecover.getTargetRecoveryDate()));
                                sysNotice.setStatus("0");
                                sysNotice.setReceiverId(sysUser.getUserId().toString());
                                sysNotice.setReceiver(sysUser.getUserName());
                                sysNotice.setCreateBy(sysUser.getLoginName());
                                sysNoticeService.insertNotice(sysNotice);
                            }
                        }
                    }
                }
            }

        }
    }


    //投后项目消息提醒
    public void sysProject() {
        SysProject sysProject = new SysProject();
        SysUser sysUser1 = sysUserService.selectUserByLoginName("xukai");
        List<SysProject> sysProjectList = sysProjectService.selectSysProjectList(sysProject);
        if (StringUtils.isNotNull(sysProject)) {
            for (SysProject sysProject1 : sysProjectList) {
                if (StringUtils.isNotNull(sysProject1.getPaymentReceivedDate())) {
                    Date limitationAction = DateUtils.addTime(sysProject1.getLimitationAction(), 3, 1);
                    if (DateUtils.timeDifference(new Date(), limitationAction, 90)) {
                        if (StringUtils.isNotNull(sysProject1.getProjectManager()) && StringUtils.isNotNull(sysProject1.getProjectManagerId())) {
                            SysUser sysUser = sysUserService.selectUserById(sysProject1.getProjectManagerId());
                            SysProjectZck sysProjectZck = sysProjectZckService.selectSysProjectZckById(sysProject1.getProjectZckId());
                            SysNotice sysNotice = new SysNotice();
                            sysNotice.setNoticeTitle(sysProjectZck.getZckName() + "中的" + sysProject1.getProjectName() + "的消息提醒");
                            sysNotice.setNoticeContent(sysProjectZck.getZckName() + "中的" + sysProject1.getProjectName() + "的消息提醒");
                            sysNotice.setReceiverId(sysProject1.getProjectManagerId() + "," + sysUser1.getUserId());
                            sysNotice.setReceiver(sysProject1.getProjectManager() + "," + sysUser1.getUserName());
                            if (sysProject1.getProjectManagerId().equals(sysUser1.getUserId())) {
                                sysNotice.setReceiverId(sysProject1.getProjectManagerId().toString());
                                sysNotice.setReceiver(sysProject1.getProjectManager());
                            } else {
                                sysNotice.setReceiverId(sysProject1.getProjectManagerId() + "," + sysUser1.getUserId());
                                sysNotice.setReceiver(sysProject1.getProjectManager() + "," + sysUser1.getUserName());
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
