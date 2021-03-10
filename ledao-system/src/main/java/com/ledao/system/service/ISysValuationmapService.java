package com.ledao.system.service;

import java.util.List;

import com.ledao.system.dao.SysValuationmap;

/**
 * 【请填写功能名称】Service接口
 *
 * @author lxz
 * @date 2021-02-22
 */
public interface ISysValuationmapService {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SysValuationmap selectSysValuationmapById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param sysValuationmap 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SysValuationmap> selectSysValuationmapList(SysValuationmap sysValuationmap);

    /**
     * 新增【请填写功能名称】
     *
     * @param sysValuationmap 【请填写功能名称】
     * @return 结果
     */
    public int insertSysValuationmap(SysValuationmap sysValuationmap);

    /**
     * 修改【请填写功能名称】
     *
     * @param sysValuationmap 【请填写功能名称】
     * @return 结果
     */
    public int updateSysValuationmap(SysValuationmap sysValuationmap);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysValuationmapByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSysValuationmapById(Long id);
}
