package com.ledao.activity.mapper;

import java.util.List;

import com.ledao.activity.dao.SysApplyIn;
import com.ledao.activity.dao.SysApplyWorkflow;

/**
 * 档案出入库审批流程Mapper接口
 * 
 * @author lxz
 * @date 2021-08-04
 */
public interface SysApplyWorkflowMapper 
{
    /**
     * 查询档案出入库审批流程
     * 
     * @param workflowId 档案出入库审批流程ID
     * @return 档案出入库审批流程
     */
    public SysApplyWorkflow selectSysApplyWorkflowById(Long workflowId);

    /**
     * 查询档案出入库审批流程列表
     * 
     * @param sysApplyWorkflow 档案出入库审批流程
     * @return 档案出入库审批流程集合
     */
    public List<SysApplyWorkflow> selectSysApplyWorkflowList(SysApplyWorkflow sysApplyWorkflow);

    /**
     * 新增档案出入库审批流程
     * 
     * @param sysApplyWorkflow 档案出入库审批流程
     * @return 结果
     */
    public int insertSysApplyWorkflow(SysApplyWorkflow sysApplyWorkflow);

    /**
     * 修改档案出入库审批流程
     * 
     * @param sysApplyWorkflow 档案出入库审批流程
     * @return 结果
     */
    public int updateSysApplyWorkflow(SysApplyWorkflow sysApplyWorkflow);

    /**
     * 删除档案出入库审批流程
     * 
     * @param workflowId 档案出入库审批流程ID
     * @return 结果
     */
    public int deleteSysApplyWorkflowById(Long workflowId);

    /**
     * 批量删除档案出入库审批流程
     * 
     * @param workflowIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysApplyWorkflowByIds(String[] workflowIds);

    /**
     * 根据applyId获取审批流程
     * @param sysApplyIn
     * @return
     */
	public List<SysApplyWorkflow> selectSysApplyWorkflowList(SysApplyIn sysApplyIn);

}