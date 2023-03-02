package com.ledao.system.service;

import java.util.List;

import com.ledao.system.dao.SysValuationModel;

/**
 * 估值计算Service接口
 *
 * @author lxz
 * @date 2022-11-08
 */
public interface ISysValuationModelService {
    /**
     * 查询估值计算
     *
     * @param valuationId 估值计算ID
     * @return 估值计算
     */
    public SysValuationModel selectSysValuationModelById(Long valuationId);

    /**
     * 查询估值计算列表
     *
     * @param sysValuationModel 估值计算
     * @return 估值计算集合
     */
    public List<SysValuationModel> selectSysValuationModelList(SysValuationModel sysValuationModel);

    /**
     * 新增估值计算
     *
     * @param sysValuationModel 估值计算
     * @return 结果
     */
    public int insertSysValuationModel(SysValuationModel sysValuationModel);

    /**
     * 修改估值计算
     *
     * @param sysValuationModel 估值计算
     * @return 结果
     */
    public int updateSysValuationModel(SysValuationModel sysValuationModel);

    /**
     * 批量删除估值计算
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysValuationModelByIds(String ids);

    /**
     * 删除估值计算信息
     *
     * @param valuationId 估值计算ID
     * @return 结果
     */
    public int deleteSysValuationModelById(Long valuationId);
}
