package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysAProperty;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author lxz
 * @date 2020-10-20
 */
public interface SysAPropertyMapper {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SysAProperty selectSysAPropertyById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param sysAProperty 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SysAProperty> selectSysAPropertyList(SysAProperty sysAProperty);

    /**
     * 新增【请填写功能名称】
     *
     * @param sysAProperty 【请填写功能名称】
     * @return 结果
     */
    public int insertSysAProperty(SysAProperty sysAProperty);

    /**
     * 修改【请填写功能名称】
     *
     * @param sysAProperty 【请填写功能名称】
     * @return 结果
     */
    public int updateSysAProperty(SysAProperty sysAProperty);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSysAPropertyById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysAPropertyByIds(String[] ids);
}
