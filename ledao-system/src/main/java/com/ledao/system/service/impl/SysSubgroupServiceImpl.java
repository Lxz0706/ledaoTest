package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysSubgroupMapper;
import com.ledao.system.dao.SysSubgroup;
import com.ledao.system.service.ISysSubgroupService;
import com.ledao.common.core.text.Convert;

/**
 * 我的小组Service业务层处理
 *
 * @author lxz
 * @date 2021-03-26
 */
@Service
public class SysSubgroupServiceImpl implements ISysSubgroupService {
    @Autowired
    private SysSubgroupMapper sysSubgroupMapper;

    /**
     * 查询我的小组
     *
     * @param subgroupId 我的小组ID
     * @return 我的小组
     */
    @Override
    public SysSubgroup selectSysSubgroupById(Long subgroupId) {
        return sysSubgroupMapper.selectSysSubgroupById(subgroupId);
    }

    /**
     * 查询我的小组列表
     *
     * @param sysSubgroup 我的小组
     * @return 我的小组
     */
    @Override
    public List<SysSubgroup> selectSysSubgroupList(SysSubgroup sysSubgroup) {
        return sysSubgroupMapper.selectSysSubgroupList(sysSubgroup);
    }

    /**
     * 新增我的小组
     *
     * @param sysSubgroup 我的小组
     * @return 结果
     */
    @Override
    public int insertSysSubgroup(SysSubgroup sysSubgroup) {
        sysSubgroup.setCreateTime(DateUtils.getNowDate());
        return sysSubgroupMapper.insertSysSubgroup(sysSubgroup);
    }

    /**
     * 修改我的小组
     *
     * @param sysSubgroup 我的小组
     * @return 结果
     */
    @Override
    public int updateSysSubgroup(SysSubgroup sysSubgroup) {
        sysSubgroup.setUpdateTime(DateUtils.getNowDate());
        return sysSubgroupMapper.updateSysSubgroup(sysSubgroup);
    }

    /**
     * 删除我的小组对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysSubgroupByIds(String ids) {
        return sysSubgroupMapper.deleteSysSubgroupByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除我的小组信息
     *
     * @param subgroupId 我的小组ID
     * @return 结果
     */
    @Override
    public int deleteSysSubgroupById(Long subgroupId) {
        return sysSubgroupMapper.deleteSysSubgroupById(subgroupId);
    }
}
