package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysProject;
import com.ledao.system.dao.SysZck;

/**
 * 投后部项目管理Mapper接口
 *
 * @author ledao
 * @date 2020-08-06
 */
public interface SysProjectMapper {
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
     * 删除投后部项目管理
     *
     * @param projectId 投后部项目管理ID
     * @return 结果
     */
    public int deleteSysProjectById(Long projectId);

    /**
     * 批量删除投后部项目管理
     *
     * @param projectIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectByIds(String[] projectIds);

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
    public List<SysProject> selectSysProjectByProjectId(String[] projectIds);

    /**
     * 查询出名字相同的
     *
     * @param sysProject
     * @return 结果
     */
    public List<SysProject> selectProject(SysProject sysProject);

    /**
     * 根据资产库id查询数据
     *
     * @param zckId
     * @return 结果
     */
    public List<SysProject> selectSysProjectByProjectZckId(String[] zckId);

    /**
     * 根据资产库类型查询数据
     *
     * @param projectZckType
     * @return 结果
     */
    public SysProject selectCountByProjectZckType(String projectZckType);

    /**
     * 根据projectId查询总的本金余额
     *
     * @param projectId
     * @return
     */
    public SysProject selectTotalPrincipalBalanceByParentId(Long projectId);
}
