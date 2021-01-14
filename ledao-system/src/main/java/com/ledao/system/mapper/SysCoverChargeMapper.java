package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysCoverCharge;

/**
 * 流转服务费Mapper接口
 *
 * @author lxz
 * @date 2020-12-18
 */
public interface SysCoverChargeMapper {
    /**
     * 查询流转服务费
     *
     * @param id 流转服务费ID
     * @return 流转服务费
     */
    public SysCoverCharge selectSysCoverChargeById(Long id);

    /**
     * 查询流转服务费列表
     *
     * @param sysCoverCharge 流转服务费
     * @return 流转服务费集合
     */
    public List<SysCoverCharge> selectSysCoverChargeList(SysCoverCharge sysCoverCharge);

    /**
     * 新增流转服务费
     *
     * @param sysCoverCharge 流转服务费
     * @return 结果
     */
    public int insertSysCoverCharge(SysCoverCharge sysCoverCharge);

    /**
     * 修改流转服务费
     *
     * @param sysCoverCharge 流转服务费
     * @return 结果
     */
    public int updateSysCoverCharge(SysCoverCharge sysCoverCharge);

    /**
     * 删除流转服务费
     *
     * @param id 流转服务费ID
     * @return 结果
     */
    public int deleteSysCoverChargeById(Long id);

    /**
     * 批量删除流转服务费
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysCoverChargeByIds(String[] ids);
}
