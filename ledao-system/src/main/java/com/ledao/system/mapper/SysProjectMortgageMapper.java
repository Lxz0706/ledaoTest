package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysProjectBail;
import com.ledao.system.dao.SysProjectMortgage;

/**
 * 投后部项目管理抵押物Mapper接口
 *
 * @author ledao
 * @date 2020-08-06
 */
public interface SysProjectMortgageMapper {
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
     * 删除投后部项目管理抵押物
     *
     * @param mortgageId 投后部项目管理抵押物ID
     * @return 结果
     */
    public int deleteSysProjectMortgageById(Long mortgageId);

    /**
     * 批量删除投后部项目管理抵押物
     *
     * @param mortgageIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectMortgageByIds(String[] mortgageIds);

    /**
     * 根据projectId查询保证人信息
     *
     * @param projectId 项目管理projectId
     * @return 结果
     */
    public List<SysProjectMortgage> selectSysProjectMortgageByProjectId(String[] projectId);

    /*
     * 根据projectId查询
     *
     * @param projectId
     * @return 结果
     * */
    public SysProjectMortgage selectProjectMortgageByProjectId(Long projectId);
}
