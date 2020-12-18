package com.ledao.system.service;

import java.util.List;

import com.ledao.system.dao.SysBusinesscard;

/**
 * 【请填写功能名称】Service接口
 *
 * @author lxz
 * @date 2020-12-07
 */
public interface ISysBusinesscardService {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SysBusinesscard selectSysBusinesscardById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param sysBusinesscard 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SysBusinesscard> selectSysBusinesscardList(SysBusinesscard sysBusinesscard);

    /**
     * 新增【请填写功能名称】
     *
     * @param sysBusinesscard 【请填写功能名称】
     * @return 结果
     */
    public int insertSysBusinesscard(SysBusinesscard sysBusinesscard);

    /**
     * 修改【请填写功能名称】
     *
     * @param sysBusinesscard 【请填写功能名称】
     * @return 结果
     */
    public int updateSysBusinesscard(SysBusinesscard sysBusinesscard);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysBusinesscardByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSysBusinesscardById(Long id);
}
