package com.ledao.system.service;

import java.util.List;

import com.ledao.system.dao.SysProjectType;

/**
 * 【请填写功能名称】Service接口
 *
 * @author lxz
 * @date 2020-12-15
 */
public interface ISysProjectTypeService {
    /**
     * 查询【请填写功能名称】
     *
     * @param projectType 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SysProjectType selectSysProjectTypeById(String projectType);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param sysProjectType 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SysProjectType> selectSysProjectTypeList(SysProjectType sysProjectType);

    /**
     * 新增【请填写功能名称】
     *
     * @param sysProjectType 【请填写功能名称】
     * @return 结果
     */
    public int insertSysProjectType(SysProjectType sysProjectType);

    /**
     * 修改【请填写功能名称】
     *
     * @param sysProjectType 【请填写功能名称】
     * @return 结果
     */
    public int updateSysProjectType(SysProjectType sysProjectType);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectTypeByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param projectType 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSysProjectTypeById(String projectType);
}
