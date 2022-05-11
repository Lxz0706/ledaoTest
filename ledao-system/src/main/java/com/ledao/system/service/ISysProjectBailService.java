package com.ledao.system.service;

import java.util.List;

import com.ledao.system.dao.SysProject;
import com.ledao.system.dao.SysProjectBail;

/**
 * 投后部项目管理保证人Service接口
 *
 * @author ledao
 * @date 2020-08-06
 */
public interface ISysProjectBailService {
    /**
     * 查询投后部项目管理保证人
     *
     * @param bailId 投后部项目管理保证人ID
     * @return 投后部项目管理保证人
     */
    public SysProjectBail selectSysProjectBailById(Long bailId);

    /**
     * 查询投后部项目管理保证人列表
     *
     * @param sysProjectBail 投后部项目管理保证人
     * @return 投后部项目管理保证人集合
     */
    public List<SysProjectBail> selectSysProjectBailList(SysProjectBail sysProjectBail);

    /**
     * 新增投后部项目管理保证人
     *
     * @param sysProjectBail 投后部项目管理保证人
     * @return 结果
     */
    public int insertSysProjectBail(SysProjectBail sysProjectBail);

    /**
     * 修改投后部项目管理保证人
     *
     * @param sysProjectBail 投后部项目管理保证人
     * @return 结果
     */
    public int updateSysProjectBail(SysProjectBail sysProjectBail);

    /**
     * 批量删除投后部项目管理保证人
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectBailByIds(String ids);

    /**
     * 删除投后部项目管理保证人信息
     *
     * @param bailId 投后部项目管理保证人ID
     * @return 结果
     */
    public int deleteSysProjectBailById(Long bailId);


    /**
     * 根据projectId查询保证人信息
     *
     * @param projectId 项目管理projectId
     * @return 结果
     */
    public List<SysProjectBail> selectSysProjectBailByProjectId(String projectId);

    /**
     * 根据projectId查询
     *
     * @param projectId
     * @return 结果
     */
    public SysProjectBail selectProjectBailByProjectId(Long projectId);

    /**
     * 根据projectId查询
     *
     * @param projectId
     * @return 结果
     */
    public List<SysProjectBail> selectProjectBailListByProjectId(Long projectId);

    /**
     * 根据项目查询出子级保证人
     *
     * @param sysProject
     * @return
     */
    public SysProjectBail selectGuarantorsByProject(SysProject sysProject);
}
