package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysProjectPledge;

/**
 * 投后项目质押物Mapper接口
 *
 * @author lxz
 * @date 2020-10-28
 */
public interface SysProjectPledgeMapper {
    /**
     * 查询投后项目质押物
     *
     * @param pledgeId 投后项目质押物ID
     * @return 投后项目质押物
     */
    public SysProjectPledge selectSysProjectPledgeById(Long pledgeId);

    /**
     * 查询投后项目质押物列表
     *
     * @param sysProjectPledge 投后项目质押物
     * @return 投后项目质押物集合
     */
    public List<SysProjectPledge> selectSysProjectPledgeList(SysProjectPledge sysProjectPledge);

    /**
     * 新增投后项目质押物
     *
     * @param sysProjectPledge 投后项目质押物
     * @return 结果
     */
    public int insertSysProjectPledge(SysProjectPledge sysProjectPledge);

    /**
     * 修改投后项目质押物
     *
     * @param sysProjectPledge 投后项目质押物
     * @return 结果
     */
    public int updateSysProjectPledge(SysProjectPledge sysProjectPledge);

    /**
     * 删除投后项目质押物
     *
     * @param pledgeId 投后项目质押物ID
     * @return 结果
     */
    public int deleteSysProjectPledgeById(Long pledgeId);

    /**
     * 批量删除投后项目质押物
     *
     * @param pledgeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectPledgeByIds(String[] pledgeIds);

    /**
     * 根据projectId查询质押物
     *
     * @param projectId
     * @return 结果
     */
    public List<SysProjectPledge> selectPledgeByProjectId(String[] projectId);

    /**
     * 根据projectId查询
     */
    public List<SysProjectPledge> selectSysPledgeByProjectId(Long projectId);
}
