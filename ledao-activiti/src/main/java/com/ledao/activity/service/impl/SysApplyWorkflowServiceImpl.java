package com.ledao.activity.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.DateUtils;
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
