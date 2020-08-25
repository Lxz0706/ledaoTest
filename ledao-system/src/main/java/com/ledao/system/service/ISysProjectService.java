package com.ledao.system.service;

import java.util.List;

import com.ledao.system.domain.SysProject;

/**
 * 投后部项目管理Service接口
 *
 * @author ledao
 * @date 2020-08-06
 */
public interface ISysProjectService {
    /**
     * 查询投后部项目管理
     *
     * @param projectId 投后部项目管理ID
     * @return 投后部项目管理
     */
    public SysProject selectSysProjectById(Long projectId);

    /**
     * 查询投后部项目管理列表
     *
     * @param sysProject 投后部项目管理
     * @return 投后部项目管理集合
     */
    public List<SysProject> selectSysProjectList(SysProject sysProject);

    /**
     * 新增投后部项目管理
     *
     * @param sysProject 投后部项目管理
     * @return 结果
     */
    public int insertSysProject(SysProject sysProject);

    /**
     * 修改投后部项目管理
     *
     * @param sysProject 投后部项目管理
     * @return 结果
     */
    public int updateSysProject(SysProject sysProject);

    /**
     * 批量删除投后部项目管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectByIds(String ids);

    /**
     * 删除投后部项目管理信息
     *
     * @param projectId 投后部项目管理ID
     * @return 结果
     */
    public int deleteSysProjectById(Long projectId);

    /**
     * 根据父级ID查询
     *
     * @param sysProject
     * @return 结果
     */
    public List<SysProject> selectSysProjectByParentId(SysProject sysProject);

    /**
     * 根据projectId查询结果
     *
     * @param projectIds
     * @return 结果
     */
    public List<SysProject> selectSysProjectByProjectId(String projectIds);

    /**
     * 查询出名字相同的
     *
     * @param sysProject
     * @return 结果
     */
    public List<SysProject> selectProject(SysProject sysProject);
}
