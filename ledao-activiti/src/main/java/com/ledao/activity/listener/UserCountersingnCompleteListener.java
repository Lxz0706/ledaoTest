package com.ledao.activity.listener;/**
 * @author 名字
 * @Title:UserCountersingnCompleteListener
 * @ProjectName ledaoTest
 * #Descrption:
 * @date 2021/6/4 13:57
 */

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @ClassName UserCountersingnCompleteListener
 * @Description TODO
 * @Author 87852
 * @Date 2021/6/4 13:57
 * @Version 1.0
 */
@Component
@Transactional
public class UserCountersingnCompleteListener implements TaskListener {
    @Override

    public void notify(DelegateTask delegateTask) {
        String[] empLoyees = {"yangxu", "yangxudong"};

// 会签设置审核人/

        delegateTask.addCandidateUsers(Arrays.asList(empLoyees));

    }

}
