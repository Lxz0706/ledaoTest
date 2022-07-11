package com.ledao.activity.listener;/**
 * @author 名字
 * @Title:WorkTicketListener
 * @ProjectName ledaoTest
 * #Descrption:
 * @date 2021/5/20 9:01
 */

import com.ledao.activity.dao.SysWorkFlowVo;
import com.ledao.activity.service.ISysWorkflowService;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysNotice;
import com.ledao.common.core.dao.entity.SysUser;
import com.ledao.system.service.ISysNoticeService;
import com.ledao.system.service.ISysUserService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName WorkTicketListener
 * @Description TODO
 * @Author 87852
 * @Date 2021/5/20 9:01
 * @Version 1.0
 */
@Component
@Transactional
public class WorkTicketListener implements TaskListener {

    @Autowired
    private ISysWorkflowService sysWorkflowService;

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    ISysNoticeService sysNoticeService;

    @Override
    public void notify(DelegateTask delegateTask) {
        SysWorkFlowVo sysWorkFlowVo = sysWorkflowService.selectSysWorkflowById(new Long(delegateTask.getExecution().getProcessInstanceBusinessKey()));
        Object realityStartTime = delegateTask.getVariable("realityStartTime");
        sysWorkFlowVo.setRealityStartTime((Date) realityStartTime);
        Object realityEndTime = delegateTask.getVariable("realityEndTime");
        sysWorkFlowVo.setRealityEndTime((Date) realityEndTime);

        //添加消息
        SysNotice sysNotice = new SysNotice();
        //设置抄送人
        SysUser sysUser = sysUserService.selectUserByLoginName("yangxudong");
        SysUser sysUser1 = sysUserService.selectUserByLoginName("xulinyi");
        SysUser sysUser2 = sysUserService.selectUserByLoginName("qianwanping");

        String userId = sysUser.getUserId() + "," + sysUser1.getUserId() + "," + sysUser2.getUserId();
        String userName = sysUser.getUserName() + "," + sysUser1.getUserName() + "," + sysUser2.getUserName();
        sysNotice.setReceiverId(userId);
        sysNotice.setReceiver(userName);
        sysNotice.setNoticeType("2");
        sysNotice.setNoticeTitle(sysWorkFlowVo.getApplyUserName() + "提交的" + sysWorkFlowVo.getCorporateName() + "入库流程结束");
        sysNotice.setStatus("0");
        sysNotice.setCreateBy(ShiroUtils.getLoginName());
        sysNoticeService.insertNotice(sysNotice);
        sysWorkflowService.updateSysWorkflow(sysWorkFlowVo);
    }
}
