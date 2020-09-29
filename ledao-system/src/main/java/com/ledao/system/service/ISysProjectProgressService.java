package com.ledao.system.service;

import java.util.List;

import com.ledao.system.dao.SysProjectProgress;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ledao
 * @date 2020-08-26
 */
public interface ISysProjectProgressService {
    /**
     * 查询【请填写功能名称】
     *
     * @param progressId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SysProjectProgress selectSysProjectProgressById(Long progressId);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param sysProjectProgress 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SysProjectProgress> selectSysProjectProgressList(SysProjectProgress sysProjectProgress);

    /**
     * 新增【请填写功能名称】
     *
     * @param sysProjectProgress 【请填写功能名称】
     * @return 结果
     */
    public int insertSysProjectProgress(SysProjectProgress sysProjectProgress);

    /**
     * 修改【请填写功能名称】
     *
     * @param sysProjectProgress 【请填写功能名称】
     * @return 结果
     */
    public int updateSysProjectProgress(SysProjectProgress sysProjectProgress);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectProgressByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param progressId 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSysProjectProgressById(Long progressId);
}
