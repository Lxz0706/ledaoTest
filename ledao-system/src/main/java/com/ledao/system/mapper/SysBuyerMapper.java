package com.ledao.system.mapper;

import java.util.List;
import com.ledao.system.dao.SysBuyer;

/**
 * 购房人基本信息Mapper接口
 * 
 * @author lxz
 * @date 2023-12-27
 */
public interface SysBuyerMapper 
{
    /**
     * 查询购房人基本信息
     * 
     * @param buyerId 购房人基本信息ID
     * @return 购房人基本信息
     */
    public SysBuyer selectSysBuyerById(Long buyerId);

    /**
     * 查询购房人基本信息列表
     * 
     * @param sysBuyer 购房人基本信息
     * @return 购房人基本信息集合
     */
    public List<SysBuyer> selectSysBuyerList(SysBuyer sysBuyer);

    /**
     * 新增购房人基本信息
     * 
     * @param sysBuyer 购房人基本信息
     * @return 结果
     */
    public int insertSysBuyer(SysBuyer sysBuyer);

    /**
     * 修改购房人基本信息
     * 
     * @param sysBuyer 购房人基本信息
     * @return 结果
     */
    public int updateSysBuyer(SysBuyer sysBuyer);

    /**
     * 删除购房人基本信息
     * 
     * @param buyerId 购房人基本信息ID
     * @return 结果
     */
    public int deleteSysBuyerById(Long buyerId);

    /**
     * 批量删除购房人基本信息
     * 
     * @param buyerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysBuyerByIds(String[] buyerIds);
}
