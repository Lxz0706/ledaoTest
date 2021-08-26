package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysProjectMapper;
import com.ledao.system.dao.SysProject;
import com.ledao.system.service.ISysProjectService;
import com.ledao.common.core.text.Convert;

/**
 * 投后部项目管理Service业务层处理
 *
 * @author ledao
 * @date 2020-08-06
 */
@Service
public class SysProjectServiceImpl implements ISysProjectService {
    @Autowired
    private SysProjectMapper sysProjectMapper;

    /**
     * 查询投后部项目管理
     *
     * @param projectId 投后部项目管理ID
     * @return 投后部项目管理
     */
    @Override
    public SysProject selectSysProjectById(Long projectId) {
        return sysProjectMapper.selectSysProjectById(projectId);
    }

    /**
     * 查询投后部项目管理列表
     *
     * @param sysProject 投后部项目管理
     * @return 投后部项目管理
     */
    @Override
    public List<SysProject> selectSysProjectList(SysProject sysProject) {
        return sysProjectMapper.selectSysProjectList(sysProject);
    }

    /**
     * 新增投后部项目管理
     *
     * @param sysProject 投后部项目管理
     * @return 结果
     */
    @Override
    public int insertSysProject(SysProject sysProject) {
        sysProject.setCreateTime(DateUtils.getNowDate());
        return sysProjectMapper.insertSysProject(sysProject);
    }

    /**
     * 修改投后部项目管理
     *
     * @param sysProject 投后部项目管理
     * @return 结果
     */
    @Override
    public int updateSysProject(SysProject sysProject) {
        sysProject.setUpdateTime(DateUtils.getNowDate());
        return sysProjectMapper.updateSysProject(sysProject);
    }

    /**
     * 删除投后部项目管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectByIds(String ids) {
        return sysProjectMapper.deleteSysProjectByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除投后部项目管理信息
     *
     * @param projectId 投后部项目管理ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectById(Long projectId) {
        return sysProjectMapper.deleteSysProjectById(projectId);
    }


    /**
     * 根据父级ID查询
     *
     * @param sysProject
     * @return 结果
     */
    @Override
    public List<SysProject> selectSysProjectByParentId(SysProject sysProject) {
        return sysProjectMapper.selectSysProjectByParentId(sysProject);
    }

    /**
     * 根据projectId查询结果
     *
     * @param projectIds
     * @return 结果
     */
    @Override
    public List<SysProject> selectSysProjectByProjectId(String projectIds) {
        return sysProjectMapper.selectSysProjectByProjectId(Convert.toStrArray(projectIds));
    }

    /**
     * 查询出名字相同的
     *
     * @param sysProject
     * @return 结果
     */
    @Override
    public List<SysProject> selectProject(SysProject sysProject) {
        return sysProjectMapper.selectProject(sysProject);
    }

    /**
     * 根据资产库id查询数据
     *
     * @param zckId
     * @return 结果
     */
    @Override
    public List<SysProject> selectSysProjectByProjectZckId(String zckId) {
        return sysProjectMapper.selectSysProjectByProjectZckId(Convert.toStrArray(zckId));
    }

    /**
     * 根据资产库id查询数据
     *
     * @param projectZckType
     * @return 结果
     */
    @Override
    public SysProject selectCountByProjectZckType(String projectZckType) {
        return sysProjectMapper.selectCountByProjectZckType(projectZckType);
    }

    /**
     * 根据projectId查询总的本金余额
     *
     * @param projectId
     * @return
     */
    @Override
    public SysProject selectTotalPrincipalBalanceByParentId(Long projectId) {
        return sysProjectMapper.selectTotalPrincipalBalanceByParentId(projectId);
    }
}
