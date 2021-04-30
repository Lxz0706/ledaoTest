package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysAnalysis;

/**
 * 系统模块分析Mapper接口
 *
 * @author lxz
 * @date 2021-04-06
 */
public interface SysAnalysisMapper {
    /**
     * 查询系统模块分析
     *
     * @param analysisId 系统模块分析ID
     * @return 系统模块分析
     */
    public SysAnalysis selectSysAnalysisById(Long analysisId);

    /**
     * 查询系统模块分析列表
     *
     * @param sysAnalysis 系统模块分析
     * @return 系统模块分析集合
     */
    public List<SysAnalysis> selectSysAnalysisList(SysAnalysis sysAnalysis);

    /**
     * 新增系统模块分析
     *
     * @param sysAnalysis 系统模块分析
     * @return 结果
     */
    public int insertSysAnalysis(SysAnalysis sysAnalysis);

    /**
     * 修改系统模块分析
     *
     * @param sysAnalysis 系统模块分析
     * @return 结果
     */
    public int updateSysAnalysis(SysAnalysis sysAnalysis);

    /**
     * 删除系统模块分析
     *
     * @param analysisId 系统模块分析ID
     * @return 结果
     */
    public int deleteSysAnalysisById(Long analysisId);

    /**
     * 批量删除系统模块分析
     *
     * @param analysisIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysAnalysisByIds(String[] analysisIds);
}
