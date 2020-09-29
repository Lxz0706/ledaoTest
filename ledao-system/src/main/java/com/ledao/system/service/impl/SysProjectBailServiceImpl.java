package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysProjectBailMapper;
import com.ledao.system.dao.SysProjectBail;
import com.ledao.system.service.ISysProjectBailService;
import com.ledao.common.core.text.Convert;

/**
 * 投后部项目管理保证人Service业务层处理
 *
 * @author ledao
 * @date 2020-08-06
 */
@Service
public class SysProjectBailServiceImpl implements ISysProjectBailService {
    @Autowired
    private SysProjectBailMapper sysProjectBailMapper;

    /**
     * 查询投后部项目管理保证人
     *
     * @param bailId 投后部项目管理保证人ID
     * @return 投后部项目管理保证人
     */
    @Override
    public SysProjectBail selectSysProjectBailById(Long bailId) {
        return sysProjectBailMapper.selectSysProjectBailById(bailId);
    }

    /**
     * 查询投后部项目管理保证人列表
     *
     * @param sysProjectBail 投后部项目管理保证人
     * @return 投后部项目管理保证人
     */
    @Override
    public List<SysProjectBail> selectSysProjectBailList(SysProjectBail sysProjectBail) {
        return sysProjectBailMapper.selectSysProjectBailList(sysProjectBail);
    }

    /**
     * 新增投后部项目管理保证人
     *
     * @param sysProjectBail 投后部项目管理保证人
     * @return 结果
     */
    @Override
    public int insertSysProjectBail(SysProjectBail sysProjectBail) {
        sysProjectBail.setCreateTime(DateUtils.getNowDate());
        return sysProjectBailMapper.insertSysProjectBail(sysProjectBail);
    }

    /**
     * 修改投后部项目管理保证人
     *
     * @param sysProjectBail 投后部项目管理保证人
     * @return 结果
     */
    @Override
    public int updateSysProjectBail(SysProjectBail sysProjectBail) {
        sysProjectBail.setUpdateTime(DateUtils.getNowDate());
        return sysProjectBailMapper.updateSysProjectBail(sysProjectBail);
    }

    /**
     * 删除投后部项目管理保证人对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectBailByIds(String ids) {
        return sysProjectBailMapper.deleteSysProjectBailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除投后部项目管理保证人信息
     *
     * @param bailId 投后部项目管理保证人ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectBailById(Long bailId) {
        return sysProjectBailMapper.deleteSysProjectBailById(bailId);
    }

    /**
     * 根据projectId查询保证人信息
     *
     * @param projectId 项目管理projectId
     * @return 结果
     */
    public List<SysProjectBail> selectSysProjectBailByProjectId(String projectId) {
        return sysProjectBailMapper.selectSysProjectBailByProjectId(Convert.toStrArray(projectId));
    }
}
