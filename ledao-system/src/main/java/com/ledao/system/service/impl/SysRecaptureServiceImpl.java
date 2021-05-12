package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysRecaptureMapper;
import com.ledao.system.dao.SysRecapture;
import com.ledao.system.service.ISysRecaptureService;
import com.ledao.common.core.text.Convert;

/**
 * 投后项目现金回现Service业务层处理
 *
 * @author lxz
 * @date 2020-11-23
 */
@Service
public class SysRecaptureServiceImpl implements ISysRecaptureService {
    @Autowired
    private SysRecaptureMapper sysRecaptureMapper;

    /**
     * 查询投后项目现金回现
     *
     * @param recaptureId 投后项目现金回现ID
     * @return 投后项目现金回现
     */
    @Override
    public SysRecapture selectSysRecaptureById(Long recaptureId) {
        return sysRecaptureMapper.selectSysRecaptureById(recaptureId);
    }

    /**
     * 查询投后项目现金回现列表
     *
     * @param sysRecapture 投后项目现金回现
     * @return 投后项目现金回现
     */
    @Override
    public List<SysRecapture> selectSysRecaptureList(SysRecapture sysRecapture) {
        return sysRecaptureMapper.selectSysRecaptureList(sysRecapture);
    }

    /**
     * 新增投后项目现金回现
     *
     * @param sysRecapture 投后项目现金回现
     * @return 结果
     */
    @Override
    public int insertSysRecapture(SysRecapture sysRecapture) {
        sysRecapture.setCreateTime(DateUtils.getNowDate());
        return sysRecaptureMapper.insertSysRecapture(sysRecapture);
    }

    /**
     * 修改投后项目现金回现
     *
     * @param sysRecapture 投后项目现金回现
     * @return 结果
     */
    @Override
    public int updateSysRecapture(SysRecapture sysRecapture) {
        sysRecapture.setUpdateTime(DateUtils.getNowDate());
        return sysRecaptureMapper.updateSysRecapture(sysRecapture);
    }

    /**
     * 删除投后项目现金回现对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysRecaptureByIds(String ids) {
        return sysRecaptureMapper.deleteSysRecaptureByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除投后项目现金回现信息
     *
     * @param recaptureId 投后项目现金回现ID
     * @return 结果
     */
    @Override
    public int deleteSysRecaptureById(Long recaptureId) {
        return sysRecaptureMapper.deleteSysRecaptureById(recaptureId);
    }

    /**
     * 根据项目id查询
     *
     * @param projectId
     * @return 结果
     */
    @Override
    public List<SysRecapture> selectSysRecaptureByProjectId(Long projectId) {
        return sysRecaptureMapper.selectSysRecaptureByProjectId(projectId);
    }
}
