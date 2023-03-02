package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysValuationMapper;
import com.ledao.system.dao.SysValuation;
import com.ledao.system.service.ISysValuationService;
import com.ledao.common.core.text.Convert;

/**
 * 估值计算主Service业务层处理
 *
 * @author lxz
 * @date 2022-11-11
 */
@Service
public class SysValuationServiceImpl implements ISysValuationService {
    @Autowired
    private SysValuationMapper sysValuationMapper;

    /**
     * 查询估值计算主
     *
     * @param valuationId 估值计算主ID
     * @return 估值计算主
     */
    @Override
    public SysValuation selectSysValuationById(Long valuationId) {
        return sysValuationMapper.selectSysValuationById(valuationId);
    }

    /**
     * 查询估值计算主列表
     *
     * @param sysValuation 估值计算主
     * @return 估值计算主
     */
    @Override
    public List<SysValuation> selectSysValuationList(SysValuation sysValuation) {
        return sysValuationMapper.selectSysValuationList(sysValuation);
    }

    /**
     * 新增估值计算主
     *
     * @param sysValuation 估值计算主
     * @return 结果
     */
    @Override
    public int insertSysValuation(SysValuation sysValuation) {
        sysValuation.setCreateTime(DateUtils.getNowDate());
        return sysValuationMapper.insertSysValuation(sysValuation);
    }

    /**
     * 修改估值计算主
     *
     * @param sysValuation 估值计算主
     * @return 结果
     */
    @Override
    public int updateSysValuation(SysValuation sysValuation) {
        sysValuation.setUpdateTime(DateUtils.getNowDate());
        return sysValuationMapper.updateSysValuation(sysValuation);
    }

    /**
     * 删除估值计算主对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysValuationByIds(String ids) {
        return sysValuationMapper.deleteSysValuationByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除估值计算主信息
     *
     * @param valuationId 估值计算主ID
     * @return 结果
     */
    @Override
    public int deleteSysValuationById(Long valuationId) {
        return sysValuationMapper.deleteSysValuationById(valuationId);
    }
}