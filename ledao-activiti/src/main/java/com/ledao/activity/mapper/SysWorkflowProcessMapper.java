package com.ledao.activity.mapper;

import java.util.List;
import com.ledao.activity.dao.SysWorkflowProcess;

/**
 * 各个流程节点审批图Mapper接口
 * 
 * @author lxz
 * @date 2021-10-13
 */
public interface SysWorkflowProcessMapper 
{
    /**
     * 查询各个流程节点审批图
     * 
     * @param id 各个流程节点审批图ID
     * @return 各个流程节点审批图
     */
    public SysWorkflowProcess selectSysWorkflowProcessById(Long id);

    /**
     * 查询各个流程节点审批图列表
     * 
     * @param sysWorkflowProcess 各个流程节点审批图
     * @return 各个流程节点审批图集合
     */
    public List<SysWorkflowProcess> selectSysWorkflowProcessList(SysWorkflowProcess sysWorkflowProcess);

    /**
     * 新增各个流程节点审批图
     * 
     * @param sysWorkflowProcess 各个流程节点审批图
     * @return 结果
     */
    public int insertSysWorkflowProcess(SysWorkflowProcess sysWorkflowProcess);

    /**
     * 修改各个流程节点审批图
     * 
     * @param sysWorkflowProcess 各个流程节点审批图
     * @return 结果
     */
    public int updateSysWorkflowProcess(SysWorkflowProcess sysWorkflowProcess);

    /**
     * 删除各个流程节点审批图
     * 
     * @param id 各个流程节点审批图ID
     * @return 结果
     */
    public int deleteSysWorkflowProcessById(Long id);

    /**
     * 批量删除各个流程节点审批图
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysWorkflowProcessByIds(String[] ids);

    void deleteSysWorkflowProcessByApplyId(Long applyId);
}
