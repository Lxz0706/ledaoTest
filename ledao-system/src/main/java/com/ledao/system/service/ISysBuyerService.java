package com.ledao.system.service;

import com.ledao.system.dao.SysBuyer;

import java.util.List;

/**
 * 购房人基本信息Service接口
 *
 * @author lxz
 * @date 2023-12-27
 */
public interface ISysBuyerService {
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
     * 批量删除购房人基本信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysBuyerByIds(String ids);

    /**
     * 删除购房人基本信息信息
     *
     * @param buyerId 购房人基本信息ID
     * @return 结果
     */
    public int deleteSysBuyerById(Long buyerId);

    /**
     * 导入用户数据
     *
     * @param sysBuyerList    用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    public String importBuyer(List<SysBuyer> sysBuyerList, Boolean isUpdateSupport, String operName);
}
