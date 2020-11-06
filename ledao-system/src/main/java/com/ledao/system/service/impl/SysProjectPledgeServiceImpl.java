package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import com.ledao.system.dao.SysProjectMortgage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysProjectPledgeMapper;
import com.ledao.system.dao.SysProjectPledge;
import com.ledao.system.service.ISysProjectPledgeService;
import com.ledao.common.core.text.Convert;

/**
 * 投后项目质押物Service业务层处理
 *
 * @author lxz
 * @date 2020-10-28
 */
@Service
public class SysProjectPledgeServiceImpl implements ISysProjectPledgeService {
    @Autowired
    private SysProjectPledgeMapper sysProjectPledgeMapper;

    /**
     * 查询投后项目质押物
     *
     * @param pledgeId 投后项目质押物ID
     * @return 投后项目质押物
     */
    @Override
    public SysProjectPledge selectSysProjectPledgeById(Long pledgeId) {
        return sysProjectPledgeMapper.selectSysProjectPledgeById(pledgeId);
    }

    /**
     * 查询投后项目质押物列表
     *
     * @param sysProjectPledge 投后项目质押物
     * @return 投后项目质押物
     */
    @Override
    public List<SysProjectPledge> selectSysProjectPledgeList(SysProjectPledge sysProjectPledge) {
        return sysProjectPledgeMapper.selectSysProjectPledgeList(sysProjectPledge);
    }

    /**
     * 新增投后项目质押物
     *
     * @param sysProjectPledge 投后项目质押物
     * @return 结果
     */
    @Override
    public int insertSysProjectPledge(SysProjectPledge sysProjectPledge) {
        sysProjectPledge.setCreateTime(DateUtils.getNowDate());
        return sysProjectPledgeMapper.insertSysProjectPledge(sysProjectPledge);
    }

    /**
     * 修改投后项目质押物
     *
     * @param sysProjectPledge 投后项目质押物
     * @return 结果
     */
    @Override
    public int updateSysProjectPledge(SysProjectPledge sysProjectPledge) {
        sysProjectPledge.setUpdateTime(DateUtils.getNowDate());
        return sysProjectPledgeMapper.updateSysProjectPledge(sysProjectPledge);
    }

    /**
     * 删除投后项目质押物对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectPledgeByIds(String ids) {
        return sysProjectPledgeMapper.deleteSysProjectPledgeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除投后项目质押物信息
     *
     * @param pledgeId 投后项目质押物ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectPledgeById(Long pledgeId) {
        return sysProjectPledgeMapper.deleteSysProjectPledgeById(pledgeId);
    }

    /**
     * 根据projectId查询抵押物
     *
     * @param projectId
     * @return 结果
     */
    public List<SysProjectPledge> selectPledgeByProjectId(String projectId) {
        return sysProjectPledgeMapper.selectPledgeByProjectId(Convert.toStrArray(projectId));
    }
}
