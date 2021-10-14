package com.ledao.activity.service.impl;

import java.util.List;
import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.activity.mapper.SysWorkflowProcessMapper;
import com.ledao.activity.dao.SysWorkflowProcess;
import com.ledao.activity.service.ISysWorkflowProcessService;
import com.ledao.common.core.text.Convert;

/**
 * 各个流程节点审批图Service业务层处理
 * 
 * @author lxz
 * @date 2021-10-13
 */
@Service
public class SysWorkflowProcessServiceImpl implements ISysWorkflowProcessService 
{
    @Autowired
    private SysWorkflowProcessMapper sysWorkflowProcessMapper;

    /**
     * 查询各个流程节点审批图
     * 
     * @param id 各个流程节点审批图ID
     * @return 各个流程节点审批图
     */
    @Override
    public SysWorkflowProcess selectSysWorkflowProcessById(Long id)
    {
        return sysWorkflowProcessMapper.selectSysWorkflowProcessById(id);
    }

    /**
     * 查询各个流程节点审批图列表
     * 
     * @param sysWorkflowProcess 各个流程节点审批图
     * @return 各个流程节点审批图
     */
    @Override
    public List<SysWorkflowProcess> selectSysWorkflowProcessList(SysWorkflowProcess sysWorkflowProcess)
    {
        return sysWorkflowProcessMapper.selectSysWorkflowProcessList(sysWorkflowProcess);
    }

    /**
     * 新增各个流程节点审批图
     * 
     * @param sysWorkflowProcess 各个流程节点审批图
     * @return 结果
     */
    @Override
    public int insertSysWorkflowProcess(SysWorkflowProcess sysWorkflowProcess)
    {
        sysWorkflowProcess.setCreateTime(DateUtils.getNowDate());
        return sysWorkflowProcessMapper.insertSysWorkflowProcess(sysWorkflowProcess);
    }

    /**
     * 修改各个流程节点审批图
     * 
     * @param sysWorkflowProcess 各个流程节点审批图
     * @return 结果
     */
    @Override
    public int updateSysWorkflowProcess(SysWorkflowProcess sysWorkflowProcess)
    {
        sysWorkflowProcess.setUpdateTime(DateUtils.getNowDate());
        return sysWorkflowProcessMapper.updateSysWorkflowProcess(sysWorkflowProcess);
    }

    /**
     * 删除各个流程节点审批图对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysWorkflowProcessByIds(String ids)
    {
        return sysWorkflowProcessMapper.deleteSysWorkflowProcessByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除各个流程节点审批图信息
     * 
     * @param id 各个流程节点审批图ID
     * @return 结果
     */
    @Override
    public int deleteSysWorkflowProcessById(Long id)
    {
        return sysWorkflowProcessMapper.deleteSysWorkflowProcessById(id);
    }
}
