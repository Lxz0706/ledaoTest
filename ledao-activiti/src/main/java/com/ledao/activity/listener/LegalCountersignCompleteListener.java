package com.ledao.activity.listener;/**
 * @author 名字
 * @Title:legalCountersignCompleteListener
 * @ProjectName ledaoTest
 * #Descrption:
 * @date 2021/6/4 13:13
 */

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName legalCountersignCompleteListener
 * @Description TODO
 * @Author 87852
 * @Date 2021/6/4 13:13
 * @Version 1.0
 */
@Component
public class LegalCountersignCompleteListener implements TaskListener {

    private static final long serialVersionUID = 1L;

    @Override
    public void notify(DelegateTask delegateTask) {
        Boolean approved = (Boolean) delegateTask.getVariable("flgwApproved");
        System.out.print("是否同意：=====" + approved+"--------");
        if (approved) {
            Long agreeCounter = (Long) delegateTask.getVariable("approvedCounter");
            delegateTask.setVariable("approvedCounter", agreeCounter + 1);
        }
    }
}