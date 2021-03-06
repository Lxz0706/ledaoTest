package com.ledao.system.service;

import java.util.List;

import com.ledao.system.dao.SysItem;

/**
 * 项目选择Service接口
 *
 * @author lxz
 * @date 2020-12-02
 */
public interface ISysItemService {
    /**
     * 查询项目选择
     *
     * @param itemId 项目选择ID
     * @return 项目选择
     */
    public SysItem selectSysItemById(Long itemId);

    /**
     * 查询项目选择列表
     *
     * @param sysItem 项目选择
     * @return 项目选择集合
     */
    public List<SysItem> selectSysItemList(SysItem sysItem);

    /**
     * 新增项目选择
     *
     * @param sysItem 项目选择
     * @return 结果
     */
    public int insertSysItem(SysItem sysItem);

    /**
     * 修改项目选择
     *
     * @param sysItem 项目选择
     * @return 结果
     */
    public int updateSysItem(SysItem sysItem);

    /**
     * 批量删除项目选择
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysItemByIds(String ids);

    /**
     * 删除项目选择信息
     *
     * @param itemId 项目选择ID
     * @return 结果
     */
    public int deleteSysItemById(Long itemId);

    /**
     * 根据customerId查询
     *
     * @param customerId
     * @return 结果
     */
    public List<SysItem> selectItemByCustomerId(Long customerId);
}
