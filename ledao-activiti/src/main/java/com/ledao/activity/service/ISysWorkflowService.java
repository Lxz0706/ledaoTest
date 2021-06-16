package com.ledao.activity.service;

import java.util.List;
import java.util.Map;

import com.ledao.activity.dao.BizLeaveVo;
import com.ledao.activity.dao.SysWorkFlowVo;
import com.ledao.activity.dao.SysWorkflow;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 档案管理出入库流程Service接口
 *
 * @author lxz
 * @date 2021-05-26
 */
public interface ISysWorkflowService {
    /**
     * 查询档案管理出入库流程
     *
     * @param workFlowId 档案管理出入库流程ID
     * @return 档案管理出入库流程
     */
    public SysWorkFlowVo selectSysWorkflowById(Long workFlowId);

    /**
     * 查询档案管理出入库流程列表
     *
     * @param sysWorkFlowVo 档案管理出入库流程
     * @return 档案管理出入库流程集合
     */
    public List<SysWorkFlowVo> selectSysWorkflowList(SysWorkFlowVo sysWorkFlowVo);

    /**
     * 新增档案管理出入库流程
     *
     * @param sysWorkFlowVo 档案管理出入库流程
     * @return 结果
     */
    public int insertSysWorkflow(SysWorkFlowVo sysWorkFlowVo);

    /**
     * 修改档案管理出入库流程
     *
     * @param sysWorkFlowVo 档案管理出入库流程
     * @return 结果
     */
    public int updateSysWorkflow(SysWorkFlowVo sysWorkFlowVo);

    /**
     * 批量删除档案管理出入库流程
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysWorkflowByIds(String ids);

    /**
     * 删除档案管理出入库流程信息
     *
     * @param workFlowId 档案管理出入库流程ID
     * @return 结果
     */
    public int deleteSysWorkflowById(Long workFlowId);

    /**
     * 启动流程
     *
     * @param entity
     * @param applyUserId
     * @return
     */
    public ProcessInstance submitApply(SysWorkFlowVo entity, String applyUserId, String key, Map<String, Object> variables);

    /**
     * 查询我的待办列表
     *
     * @param userId
     * @return
     */
    public List<SysWorkFlowVo> findTodoTasks(SysWorkFlowVo sysWorkFlowVo, String userId);

    public List<SysWorkFlowVo> findDoneTasks(SysWorkFlowVo sysWorkFlowVo, String userId);
}
