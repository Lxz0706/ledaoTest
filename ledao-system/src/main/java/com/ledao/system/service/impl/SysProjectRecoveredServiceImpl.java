package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysProjectRecoveredMapper;
import com.ledao.system.dao.SysProjectRecovered;
import com.ledao.system.service.ISysProjectRecoveredService;
import com.ledao.common.core.text.Convert;

/**
 * 已回收Service业务层处理
 *
 * @author ledao
 * @date 2020-09-03
 */
@Service
public class SysProjectRecoveredServiceImpl implements ISysProjectRecoveredService {
    @Autowired
    private SysProjectRecoveredMapper sysProjectRecoveredMapper;

    /**
     * 查询已回收
     *
     * @param id 已回收ID
     * @return 已回收
     */
    @Override
    public SysProjectRecovered selectSysProjectRecoveredById(Long id) {
        return sysProjectRecoveredMapper.selectSysProjectRecoveredById(id);
    }

    /**
     * 查询已回收列表
     *
     * @param sysProjectRecovered 已回收
     * @return 已回收
     */
    @Override
    public List<SysProjectRecovered> selectSysProjectRecoveredList(SysProjectRecovered sysProjectRecovered) {
        return sysProjectRecoveredMapper.selectSysProjectRecoveredList(sysProjectRecovered);
    }

    /**
     * 新增已回收
     *
     * @param sysProjectRecovered 已回收
     * @return 结果
     */
    @Override
    public int insertSysProjectRecovered(SysProjectRecovered sysProjectRecovered) {
        sysProjectRecovered.setCreateTime(DateUtils.getNowDate());
        return sysProjectRecoveredMapper.insertSysProjectRecovered(sysProjectRecovered);
    }

    /**
     * 修改已回收
     *
     * @param sysProjectRecovered 已回收
     * @return 结果
     */
    @Override
    public int updateSysProjectRecovered(SysProjectRecovered sysProjectRecovered) {
        sysProjectRecovered.setUpdateTime(DateUtils.getNowDate());
        return sysProjectRecoveredMapper.updateSysProjectRecovered(sysProjectRecovered);
    }

    /**
     * 删除已回收对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectRecoveredByIds(String ids) {
        return sysProjectRecoveredMapper.deleteSysProjectRecoveredByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除已回收信息
     *
     * @param id 已回收ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectRecoveredById(Long id) {
        return sysProjectRecoveredMapper.deleteSysProjectRecoveredById(id);
    }
}
