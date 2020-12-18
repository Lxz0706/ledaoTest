package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysItemMapper;
import com.ledao.system.dao.SysItem;
import com.ledao.system.service.ISysItemService;
import com.ledao.common.core.text.Convert;

/**
 * 项目选择Service业务层处理
 *
 * @author lxz
 * @date 2020-12-02
 */
@Service
public class SysItemServiceImpl implements ISysItemService {
    @Autowired
    private SysItemMapper sysItemMapper;

    /**
     * 查询项目选择
     *
     * @param itemId 项目选择ID
     * @return 项目选择
     */
    @Override
    public SysItem selectSysItemById(Long itemId) {
        return sysItemMapper.selectSysItemById(itemId);
    }

    /**
     * 查询项目选择列表
     *
     * @param sysItem 项目选择
     * @return 项目选择
     */
    @Override
    public List<SysItem> selectSysItemList(SysItem sysItem) {
        return sysItemMapper.selectSysItemList(sysItem);
    }

    /**
     * 新增项目选择
     *
     * @param sysItem 项目选择
     * @return 结果
     */
    @Override
    public int insertSysItem(SysItem sysItem) {
        sysItem.setCreateTime(DateUtils.getNowDate());
        return sysItemMapper.insertSysItem(sysItem);
    }

    /**
     * 修改项目选择
     *
     * @param sysItem 项目选择
     * @return 结果
     */
    @Override
    public int updateSysItem(SysItem sysItem) {
        sysItem.setUpdateTime(DateUtils.getNowDate());
        return sysItemMapper.updateSysItem(sysItem);
    }

    /**
     * 删除项目选择对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysItemByIds(String ids) {
        return sysItemMapper.deleteSysItemByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除项目选择信息
     *
     * @param itemId 项目选择ID
     * @return 结果
     */
    @Override
    public int deleteSysItemById(Long itemId) {
        return sysItemMapper.deleteSysItemById(itemId);
    }
}
