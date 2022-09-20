package com.ledao.activity.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.ledao.activity.mapper.SysApplyInMapper;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.core.dao.entity.SysRole;
import com.ledao.common.core.dao.entity.SysUser;
import com.ledao.system.mapper.SysRoleMapper;
import com.ledao.system.mapper.SysUserMapper;
import com.ledao.system.service.ISysConfigService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import com.ledao.activity.mapper.SysApplyWorkflowMapper;
import com.ledao.activity.dao.SysApplyIn;
import com.ledao.activity.dao.SysApplyWorkflow;
import com.ledao.activity.service.ISysApplyWorkflowService;
import com.ledao.common.core.text.Convert;

import javax.jms.Queue;

/**
 * 档案出入库审批流程Service业务层处理
 * 
 * @author lxz
 * @date 2021-08-04
 */
@Service
public class SysApplyWorkflowServiceImpl implements ISysApplyWorkflowService 
{
    @Autowired
    private SysApplyWorkflowMapper sysApplyWorkflowMapper;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private SysApplyInMapper sysApplyInMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * 查询档案出入库审批流程
     * 
     * @param workflowId 档案出入库审批流程ID
     * @return 档案出入库审批流程
     */
    @Override
    public SysApplyWorkflow selectSysApplyWorkflowById(Long workflowId)
    {
        return sysApplyWorkflowMapper.selectSysApplyWorkflowById(workflowId);
    }

    /**
     * 查询档案出入库审批流程列表
     * 
     * @param sysApplyWorkflow 档案出入库审批流程
     * @return 档案出入库审批流程
     */
    @Override
    public List<SysApplyWorkflow> selectSysApplyWorkflowList(SysApplyWorkflow sysApplyWorkflow)
    {
        return sysApplyWorkflowMapper.selectSysApplyWorkflowList(sysApplyWorkflow);
    }

    /**
     * 新增档案出入库审批流程
     * 
     * @param sysApplyWorkflow 档案出入库审批流程
     * @return 结果
     */
    @Override
    public int insertSysApplyWorkflow(SysApplyWorkflow sysApplyWorkflow)
    {
        sysApplyWorkflow.setCreateTime(DateUtils.getNowDate());
        return sysApplyWorkflowMapper.insertSysApplyWorkflow(sysApplyWorkflow);
    }

    @Override
    public AjaxResult sendLittleMsg(JSONObject parm){
/*        JSONObject parm = new JSONObject();
        parm.put("thing6","测试1");
        parm.put("thing4","测试2");
        parm.put("thing7","测试3");
        parm.put("time4",DateUtils.getNowDate());*/

        // 创建名称为zyQueue的队列
        Queue queue = new ActiveMQQueue("zyQueueCommon");
        String dataStr = JSONObject.toJSONString(parm);
        // 向队列发送消息
        jmsMessagingTemplate.convertAndSend(queue, dataStr);
        return AjaxResult.success();
    }

    @Override
    public void sendTaskMsg(List<SysUser> us, Map<String, String> parmStr) {
        for (SysUser u:us) {
            if (StringUtils.isNotEmpty(u.getComOpenId())){
                //发送消息到投后部部门经理
                JSONObject parm = new JSONObject();
                //发布人
                parm.put("first",parmStr.get("first"));
                parm.put("word1",parmStr.get("word1"));
//                        计划时间
                parm.put("word2", StringUtils.isNotEmpty(parmStr.get("word2"))?parmStr.get("word2"):"-");
//                        任务名称
                parm.put("word3",StringUtils.isNotEmpty(parmStr.get("word3"))?parmStr.get("word3"):"-");
//                        任务状态
                parm.put("word4",StringUtils.isNotEmpty(parmStr.get("word4"))?parmStr.get("word4"):"-");
                parm.put("word5","-");

//                        任务接收人
                parm.put("toUser",u.getComOpenId());
//                parm.put("toUser","o_gyCwh9IvRICHvI_Z9pWejZ3-nw");
                String accessToken = configService.getWechatComAccessToken();
                parm.put("accessToken",accessToken);
                // 创建名称为投后队列
                Queue queue = new ActiveMQQueue("ThQueueCommonUsal");
                String dataStr = JSONObject.toJSONString(parm);
                // 向队列发送消息
                jmsMessagingTemplate.convertAndSend(queue, dataStr);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        System.out.println("-------设定要指定任务--------");
                    }
                }, 2000);
            }
        }
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
     * 修改档案出入库审批流程
     * 
     * @param sysApplyWorkflow 档案出入库审批流程
     * @return 结果
     */
    @Override
    public int updateSysApplyWorkflow(SysApplyWorkflow sysApplyWorkflow)
    {
        sysApplyWorkflow.setUpdateTime(DateUtils.getNowDate());
        return sysApplyWorkflowMapper.updateSysApplyWorkflow(sysApplyWorkflow);
    }

    /**
     * 删除档案出入库审批流程对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysApplyWorkflowByIds(String ids)
    {
        return sysApplyWorkflowMapper.deleteSysApplyWorkflowByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除档案出入库审批流程信息
     * 
     * @param workflowId 档案出入库审批流程ID
     * @return 结果
     */
    @Override
    public int deleteSysApplyWorkflowById(Long workflowId)
    {
        return sysApplyWorkflowMapper.deleteSysApplyWorkflowById(workflowId);
    }

    /**
     * 根据applyId获取审批流程
     * @param sysApplyIn
     * @return
     */
	@Override
	public List<SysApplyWorkflow> selectSysApplyWorkflowList(SysApplyIn sysApplyIn) {
		return sysApplyWorkflowMapper.selectSysApplyWorkflowList(sysApplyIn);
	}
}
