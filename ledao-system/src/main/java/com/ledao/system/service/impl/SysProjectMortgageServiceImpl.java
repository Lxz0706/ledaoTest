package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import com.ledao.system.dao.SysProjectContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysProjectMortgageMapper;
import com.ledao.system.dao.SysProjectMortgage;
import com.ledao.system.service.ISysProjectMortgageService;
import com.ledao.common.core.text.Convert;

/**
 * 投后部项目管理抵押物Service业务层处理
 *
 * @author ledao
 * @date 2020-08-06
 */
@Service
public class SysProjectMortgageServiceImpl implements ISysProjectMortgageService {
    @Autowired
    private SysProjectMortgageMapper sysProjectMortgageMapper;

    /**
     * 查询投后部项目管理抵押物
     *
     * @param mortgageId 投后部项目管理抵押物ID
     * @return 投后部项目管理抵押物
     */
    @Override
    public SysProjectMortgage selectSysProjectMortgageById(Long mortgageId) {
        return sysProjectMortgageMapper.selectSysProjectMortgageById(mortgageId);
    }

    /**
     * 查询投后部项目管理抵押物列表
     *
     * @param sysProjectMortgage 投后部项目管理抵押物
     * @return 投后部项目管理抵押物
     */
    @Override
    public List<SysProjectMortgage> selectSysProjectMortgageList(SysProjectMortgage sysProjectMortgage) {
        return sysProjectMortgageMapper.selectSysProjectMortgageList(sysProjectMortgage);
    }

    /**
     * 新增投后部项目管理抵押物
     *
     * @param sysProjectMortgage 投后部项目管理抵押物
     * @return 结果
     */
    @Override
    public int insertSysProjectMortgage(SysProjectMortgage sysProjectMortgage) {
        sysProjectMortgage.setCreateTime(DateUtils.getNowDate());
        return sysProjectMortgageMapper.insertSysProjectMortgage(sysProjectMortgage);
    }

    /**
     * 修改投后部项目管理抵押物
     *
     * @param sysProjectMortgage 投后部项目管理抵押物
     * @return 结果
     */
    @Override
    public int updateSysProjectMortgage(SysProjectMortgage sysProjectMortgage) {
        sysProjectMortgage.setUpdateTime(DateUtils.getNowDate());
        return sysProjectMortgageMapper.updateSysProjectMortgage(sysProjectMortgage);
    }

    /**
     * 删除投后部项目管理抵押物对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectMortgageByIds(String ids) {
        return sysProjectMortgageMapper.deleteSysProjectMortgageByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除投后部项目管理抵押物信息
     *
     * @param mortgageId 投后部项目管理抵押物ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectMortgageById(Long mortgageId) {
        return sysProjectMortgageMapper.deleteSysProjectMortgageById(mortgageId);
    }


    /**
     * 根据projectId查询抵押物
     *
     * @param projectId
     * @return 结果
     */
    public List<SysProjectMortgage> selectSysProjectMortgageByProjectId(String projectId) {
        return sysProjectMortgageMapper.selectSysProjectMortgageByProjectId(Convert.toStrArray(projectId));
    }

    /**
     * 根据projectId查询
     *
     * @param projectId
     * @return 结果
     */
    public SysProjectMortgage selectProjectMortgageByProjectId(Long projectId){
        return sysProjectMortgageMapper.selectProjectMortgageByProjectId(projectId);
    }

    /**
     * 根据projectId查询
     *
     * @param projectId
     * @return 结果
     */
    public List<SysProjectMortgage> selectProjectMortgageByProjectIds(Long projectId){
        return sysProjectMortgageMapper.selectProjectMortgageByProjectIds(projectId);
    }
}
