package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysProjectContractMapper;
import com.ledao.system.dao.SysProjectContract;
import com.ledao.system.service.ISysProjectContractService;
import com.ledao.common.core.text.Convert;

/**
 * 投后部项目管理合同本金Service业务层处理
 *
 * @author ledao
 * @date 2020-08-06
 */
@Service
public class SysProjectContractServiceImpl implements ISysProjectContractService {
    @Autowired
    private SysProjectContractMapper sysProjectContractMapper;

    /**
     * 查询投后部项目管理合同本金
     *
     * @param contractId 投后部项目管理合同本金ID
     * @return 投后部项目管理合同本金
     */
    @Override
    public SysProjectContract selectSysProjectContractById(Long contractId) {
        return sysProjectContractMapper.selectSysProjectContractById(contractId);
    }

    /**
     * 查询投后部项目管理合同本金列表
     *
     * @param sysProjectContract 投后部项目管理合同本金
     * @return 投后部项目管理合同本金
     */
    @Override
    public List<SysProjectContract> selectSysProjectContractList(SysProjectContract sysProjectContract) {
        return sysProjectContractMapper.selectSysProjectContractList(sysProjectContract);
    }

    /**
     * 新增投后部项目管理合同本金
     *
     * @param sysProjectContract 投后部项目管理合同本金
     * @return 结果
     */
    @Override
    public int insertSysProjectContract(SysProjectContract sysProjectContract) {
        sysProjectContract.setCreateTime(DateUtils.getNowDate());
        return sysProjectContractMapper.insertSysProjectContract(sysProjectContract);
    }

    /**
     * 修改投后部项目管理合同本金
     *
     * @param sysProjectContract 投后部项目管理合同本金
     * @return 结果
     */
    @Override
    public int updateSysProjectContract(SysProjectContract sysProjectContract) {
        sysProjectContract.setUpdateTime(DateUtils.getNowDate());
        return sysProjectContractMapper.updateSysProjectContract(sysProjectContract);
    }

    /**
     * 删除投后部项目管理合同本金对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectContractByIds(String ids) {
        return sysProjectContractMapper.deleteSysProjectContractByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除投后部项目管理合同本金信息
     *
     * @param contractId 投后部项目管理合同本金ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectContractById(Long contractId) {
        return sysProjectContractMapper.deleteSysProjectContractById(contractId);
    }

    /**
     * 根据projectId查询合同本金
     *
     * @param projectId
     * @return 结果
     */
    @Override
    public List<SysProjectContract> selectSysProjectContractByProjectId(String projectId) {
        return sysProjectContractMapper.selectSysProjectContractByProjectId(Convert.toStrArray(projectId));
    }

    /**
     * 根据projectId查询
     *
     * @param projectId
     * @return 结果
     */
    @Override
    public SysProjectContract selectProjectContractByProjectId(Long projectId){
        return sysProjectContractMapper.selectProjectContractByProjectId(projectId);
    }
}
