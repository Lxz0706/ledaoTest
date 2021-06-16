package com.ledao.activity.mapper;

import java.util.List;

import com.ledao.activity.dao.SysWorkFlowVo;
import com.ledao.activity.dao.SysWorkflow;

/**
 * 档案管理出入库流程Mapper接口
 *
 * @author lxz
 * @date 2021-05-26
 */
public interface SysWorkflowMapper {
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
     * 删除档案管理出入库流程
     *
     * @param workFlowId 档案管理出入库流程ID
     * @return 结果
     */
    public int deleteSysWorkflowById(Long workFlowId);

    /**
     * 批量删除档案管理出入库流程
     *
     * @param workFlowIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysWorkflowByIds(String[] workFlowIds);
}
