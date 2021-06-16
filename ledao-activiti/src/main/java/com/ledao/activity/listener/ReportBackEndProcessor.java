package com.ledao.activity.listener;

import com.ledao.activity.service.IBizLeaveService;
import com.ledao.activity.dao.BizLeaveVo;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysNotice;
import com.ledao.system.service.ISysNoticeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <b>监听器使用范例</b>：销假后处理器
 * <p>
 * 设置销假时间
 * </p>
 * <p>
 * 使用Spring代理，可以注入Bean，管理事物
 * </p>
 *
 * @author HenryYan
 */
@Component
@Transactional
public class ReportBackEndProcessor implements TaskListener {

    private static final long serialVersionUID = 1L;

    @Autowired
    IBizLeaveService bizLeaveService;

    @Autowired
    ISysNoticeService sysNoticeService;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.activiti.engine.delegate.TaskListener#notify(org.activiti.engine.delegate
     * .DelegateTask)
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        BizLeaveVo leave = bizLeaveService.selectBizLeaveById(new Long(delegateTask.getExecution().getProcessInstanceBusinessKey()));
        Object realityStartTime = delegateTask.getVariable("realityStartTime");
        leave.setRealityStartTime((Date) realityStartTime);
        Object realityEndTime = delegateTask.getVariable("realityEndTime");
        leave.setRealityEndTime((Date) realityEndTime);
        SysNotice sysNotice = new SysNotice();
        sysNotice.setReceiverId(ShiroUtils.getUserId().toString());
        sysNotice.setReceiver(ShiroUtils.getSysUser().getUserName());
        sysNotice.setNoticeType("3");
        sysNotice.setNoticeTitle(leave.getTitle() + "流程结束");
        sysNotice.setStatus("0");
        sysNotice.setCreateBy(ShiroUtils.getLoginName());
        sysNoticeService.insertNotice(sysNotice);
        bizLeaveService.updateBizLeave(leave);
    }

}
