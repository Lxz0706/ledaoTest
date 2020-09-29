package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysProjectmanagent;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ledao
 * @date 2020-08-26
 */
public interface SysProjectmanagentMapper {
    /**
     * 查询【请填写功能名称】
     *
     * @param projectManagementId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SysProjectmanagent selectSysProjectmanagentById(Long projectManagementId);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param sysProjectmanagent 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SysProjectmanagent> selectSysProjectmanagentList(SysProjectmanagent sysProjectmanagent);

    /**
     * 新增【请填写功能名称】
     *
     * @param sysProjectmanagent 【请填写功能名称】
     * @return 结果
     */
    public int insertSysProjectmanagent(SysProjectmanagent sysProjectmanagent);

    /**
     * 修改【请填写功能名称】
     *
     * @param sysProjectmanagent 【请填写功能名称】
     * @return 结果
     */
    public int updateSysProjectmanagent(SysProjectmanagent sysProjectmanagent);

    /**
     * 删除【请填写功能名称】
     *
     * @param projectManagementId 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSysProjectmanagentById(Long projectManagementId);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param projectManagementIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectmanagentByIds(String[] projectManagementIds);
}
