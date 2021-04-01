package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysSubgroup;

/**
 * 我的小组Mapper接口
 *
 * @author lxz
 * @date 2021-03-26
 */
public interface SysSubgroupMapper {
    /**
     * 查询我的小组
     *
     * @param subgroupId 我的小组ID
     * @return 我的小组
     */
    public SysSubgroup selectSysSubgroupById(Long subgroupId);

    /**
     * 查询我的小组列表
     *
     * @param sysSubgroup 我的小组
     * @return 我的小组集合
     */
    public List<SysSubgroup> selectSysSubgroupList(SysSubgroup sysSubgroup);

    /**
     * 新增我的小组
     *
     * @param sysSubgroup 我的小组
     * @return 结果
     */
    public int insertSysSubgroup(SysSubgroup sysSubgroup);

    /**
     * 修改我的小组
     *
     * @param sysSubgroup 我的小组
     * @return 结果
     */
    public int updateSysSubgroup(SysSubgroup sysSubgroup);

    /**
     * 删除我的小组
     *
     * @param subgroupId 我的小组ID
     * @return 结果
     */
    public int deleteSysSubgroupById(Long subgroupId);

    /**
     * 批量删除我的小组
     *
     * @param subgroupIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysSubgroupByIds(String[] subgroupIds);
}
