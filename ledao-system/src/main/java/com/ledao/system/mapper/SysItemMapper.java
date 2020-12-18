package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysItem;

/**
 * 项目选择Mapper接口
 *
 * @author lxz
 * @date 2020-12-02
 */
public interface SysItemMapper {
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
     * 删除项目选择
     *
     * @param itemId 项目选择ID
     * @return 结果
     */
    public int deleteSysItemById(Long itemId);

    /**
     * 批量删除项目选择
     *
     * @param itemIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysItemByIds(String[] itemIds);
}
