package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysProjectContract;

/**
 * 投后部项目管理合同本金Mapper接口
 *
 * @author ledao
 * @date 2020-08-06
 */
public interface SysProjectContractMapper {
    /**
     * 查询投后部项目管理合同本金
     *
     * @param contractId 投后部项目管理合同本金ID
     * @return 投后部项目管理合同本金
     */
    public SysProjectContract selectSysProjectContractById(Long contractId);

    /**
     * 查询投后部项目管理合同本金列表
     *
     * @param sysProjectContract 投后部项目管理合同本金
     * @return 投后部项目管理合同本金集合
     */
    public List<SysProjectContract> selectSysProjectContractList(SysProjectContract sysProjectContract);

    /**
     * 新增投后部项目管理合同本金
     *
     * @param sysProjectContract 投后部项目管理合同本金
     * @return 结果
     */
    public int insertSysProjectContract(SysProjectContract sysProjectContract);

    /**
     * 修改投后部项目管理合同本金
     *
     * @param sysProjectContract 投后部项目管理合同本金
     * @return 结果
     */
    public int updateSysProjectContract(SysProjectContract sysProjectContract);

    /**
     * 删除投后部项目管理合同本金
     *
     * @param contractId 投后部项目管理合同本金ID
     * @return 结果
     */
    public int deleteSysProjectContractById(Long contractId);

    /**
     * 批量删除投后部项目管理合同本金
     *
     * @param contractIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectContractByIds(String[] contractIds);

    /**
     * 根据项目ID查询
     *
     * @param projectId 根据需要查询的projectId
     * @return 结果
     */
    public List<SysProjectContract> selectSysProjectContractByProjectId(String[] projectId);
}
