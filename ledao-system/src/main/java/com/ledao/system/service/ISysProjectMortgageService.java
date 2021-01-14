package com.ledao.system.service;

import java.util.List;

import com.ledao.system.dao.SysProjectContract;
import com.ledao.system.dao.SysProjectMortgage;

/**
 * 投后部项目管理抵押物Service接口
 *
 * @author ledao
 * @date 2020-08-06
 */
public interface ISysProjectMortgageService {
    /**
     * 查询投后部项目管理抵押物
     *
     * @param mortgageId 投后部项目管理抵押物ID
     * @return 投后部项目管理抵押物
     */
    public SysProjectMortgage selectSysProjectMortgageById(Long mortgageId);

    /**
     * 查询投后部项目管理抵押物列表
     *
     * @param sysProjectMortgage 投后部项目管理抵押物
     * @return 投后部项目管理抵押物集合
     */
    public List<SysProjectMortgage> selectSysProjectMortgageList(SysProjectMortgage sysProjectMortgage);

    /**
     * 新增投后部项目管理抵押物
     *
     * @param sysProjectMortgage 投后部项目管理抵押物
     * @return 结果
     */
    public int insertSysProjectMortgage(SysProjectMortgage sysProjectMortgage);

    /**
     * 修改投后部项目管理抵押物
     *
     * @param sysProjectMortgage 投后部项目管理抵押物
     * @return 结果
     */
    public int updateSysProjectMortgage(SysProjectMortgage sysProjectMortgage);

    /**
     * 批量删除投后部项目管理抵押物
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectMortgageByIds(String ids);

    /**
     * 删除投后部项目管理抵押物信息
     *
     * @param mortgageId 投后部项目管理抵押物ID
     * @return 结果
     */
    public int deleteSysProjectMortgageById(Long mortgageId);

    /**
     * 根据projectId查询合同本金
     *
     * @param projectId 项目ID
     * @return 结果
     */
    public List<SysProjectMortgage> selectSysProjectMortgageByProjectId(String projectId);

    /**
     * 根据projectId查询
     *
     * @param projectId
     * @return 结果
     */
    public SysProjectMortgage selectProjectMortgageByProjectId(Long projectId);

    /**
     * 根据projectId查询
     *
     * @param projectId
     * @return 结果
     */
    public List<SysProjectMortgage> selectProjectMortgageByProjectIds(Long projectId);
}
