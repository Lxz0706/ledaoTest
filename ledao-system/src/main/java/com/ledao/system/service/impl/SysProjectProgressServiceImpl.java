package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysProjectProgressMapper;
import com.ledao.system.domain.SysProjectProgress;
import com.ledao.system.service.ISysProjectProgressService;
import com.ledao.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ledao
 * @date 2020-08-26
 */
@Service
public class SysProjectProgressServiceImpl implements ISysProjectProgressService {
    @Autowired
    private SysProjectProgressMapper sysProjectProgressMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param progressId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public SysProjectProgress selectSysProjectProgressById(Long progressId) {
        return sysProjectProgressMapper.selectSysProjectProgressById(progressId);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param sysProjectProgress 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysProjectProgress> selectSysProjectProgressList(SysProjectProgress sysProjectProgress) {
        return sysProjectProgressMapper.selectSysProjectProgressList(sysProjectProgress);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param sysProjectProgress 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysProjectProgress(SysProjectProgress sysProjectProgress) {
        sysProjectProgress.setCreateTime(DateUtils.getNowDate());
        return sysProjectProgressMapper.insertSysProjectProgress(sysProjectProgress);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param sysProjectProgress 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysProjectProgress(SysProjectProgress sysProjectProgress) {
        sysProjectProgress.setUpdateTime(DateUtils.getNowDate());
        return sysProjectProgressMapper.updateSysProjectProgress(sysProjectProgress);
    }

    /**
     * 删除【请填写功能名称】对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectProgressByIds(String ids) {
        return sysProjectProgressMapper.deleteSysProjectProgressByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param progressId 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectProgressById(Long progressId) {
        return sysProjectProgressMapper.deleteSysProjectProgressById(progressId);
    }
}
