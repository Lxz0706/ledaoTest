package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysAnalysisMapper;
import com.ledao.system.dao.SysAnalysis;
import com.ledao.system.service.ISysAnalysisService;
import com.ledao.common.core.text.Convert;

/**
 * 系统模块分析Service业务层处理
 *
 * @author lxz
 * @date 2021-04-06
 */
@Service
public class SysAnalysisServiceImpl implements ISysAnalysisService {
    @Autowired
    private SysAnalysisMapper sysAnalysisMapper;

    /**
     * 查询系统模块分析
     *
     * @param analysisId 系统模块分析ID
     * @return 系统模块分析
     */
    @Override
    public SysAnalysis selectSysAnalysisById(Long analysisId) {
        return sysAnalysisMapper.selectSysAnalysisById(analysisId);
    }

    /**
     * 查询系统模块分析列表
     *
     * @param sysAnalysis 系统模块分析
     * @return 系统模块分析
     */
    @Override
    public List<SysAnalysis> selectSysAnalysisList(SysAnalysis sysAnalysis) {
        return sysAnalysisMapper.selectSysAnalysisList(sysAnalysis);
    }

    /**
     * 新增系统模块分析
     *
     * @param sysAnalysis 系统模块分析
     * @return 结果
     */
    @Override
    public int insertSysAnalysis(SysAnalysis sysAnalysis) {
        sysAnalysis.setCreateTime(DateUtils.getNowDate());
        return sysAnalysisMapper.insertSysAnalysis(sysAnalysis);
    }

    /**
     * 修改系统模块分析
     *
     * @param sysAnalysis 系统模块分析
     * @return 结果
     */
    @Override
    public int updateSysAnalysis(SysAnalysis sysAnalysis) {
        sysAnalysis.setUpdateTime(DateUtils.getNowDate());
        return sysAnalysisMapper.updateSysAnalysis(sysAnalysis);
    }

    /**
     * 删除系统模块分析对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysAnalysisByIds(String ids) {
        return sysAnalysisMapper.deleteSysAnalysisByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除系统模块分析信息
     *
     * @param analysisId 系统模块分析ID
     * @return 结果
     */
    @Override
    public int deleteSysAnalysisById(Long analysisId) {
        return sysAnalysisMapper.deleteSysAnalysisById(analysisId);
    }
}
