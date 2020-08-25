package com.ledao.system.service;

import java.util.List;

import com.ledao.system.domain.SysProject;
import com.ledao.system.domain.SysProjectContract;

/**
 * 投后部项目管理合同本金Service接口
 *
 * @author ledao
 * @date 2020-08-06
 */
public interface ISysProjectContractService {
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
     * 批量删除投后部项目管理合同本金
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectContractByIds(String ids);

    /**
     * 删除投后部项目管理合同本金信息
     *
     * @param contractId 投后部项目管理合同本金ID
     * @return 结果
     */
    public int deleteSysProjectContractById(Long contractId);

    /**
     * 根据projectId查询合同本金
     *
     * @param projectId 项目ID
     * @return 结果
     */
    public List<SysProjectContract> selectSysProjectContractByProjectId(String projectId);
}
