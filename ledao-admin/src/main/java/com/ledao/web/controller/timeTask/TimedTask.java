package com.ledao.web.controller.timeTask;

import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.system.domain.*;
import com.ledao.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component("timedTask")
@Lazy(false)
public class TimedTask {
    @Autowired
    private ISysProjectmanagentService sysProjectmanagentService;

    @Autowired
    private ISysProjectProgressService sysProjectProgressService;

    @Autowired
    private ISysProjectysyfService sysProjectysyfService;

    @Autowired
    private ISysProjectRecoveredService sysProjectRecoveredService;

    @Autowired
    private ISysProjectTargetrecoverService sysProjectTargetrecoverService;

    @Autowired
    private ISysProjectUncollectedMoneyService sysProjectUncollectedMoneyService;

    @Autowired
    private ISysNoticeService sysNoticeService;

    @Autowired
    private ISysUserService sysUserService;

    public void timedTasks() {
        System.out.println("进来了！！！！！！！！！！");
        SysNotice sysNotice = new SysNotice();
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
}
