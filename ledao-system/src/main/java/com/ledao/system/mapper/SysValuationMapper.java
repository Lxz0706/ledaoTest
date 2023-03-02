package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysValuation;

/**
 * 估值计算主Mapper接口
 *
 * @author lxz
 * @date 2022-11-11
 */
public interface SysValuationMapper {
    /**
     * 查询估值计算主
     *
     * @param valuationId 估值计算主ID
     * @return 估值计算主
     */
    public SysValuation selectSysValuationById(Long valuationId);

    /**
     * 查询估值计算主列表
     *
     * @param sysValuation 估值计算主
     * @return 估值计算主集合
     */
    public List<SysValuation> selectSysValuationList(SysValuation sysValuation);

    /**
     * 新增估值计算主
     *
     * @param sysValuation 估值计算主
     * @return 结果
     */
    public int insertSysValuation(SysValuation sysValuation);

    /**
     * 修改估值计算主
     *
     * @param sysValuation 估值计算主
     * @return 结果
     */
    public int updateSysValuation(SysValuation sysValuation);

    /**
     * 删除估值计算主
     *
     * @param valuationId 估值计算主ID
     * @return 结果
     */
    public int deleteSysValuationById(Long valuationId);

    /**
     * 批量删除估值计算主
     *
     * @param valuationIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysValuationByIds(String[] valuationIds);
}