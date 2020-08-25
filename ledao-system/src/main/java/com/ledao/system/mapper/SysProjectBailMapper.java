package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.domain.SysProjectBail;

/**
 * 投后部项目管理保证人Mapper接口
 *
 * @author ledao
 * @date 2020-08-06
 */
public interface SysProjectBailMapper {
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
     * 删除投后部项目管理保证人
     *
     * @param bailId 投后部项目管理保证人ID
     * @return 结果
     */
    public int deleteSysProjectBailById(Long bailId);

    /**
     * 批量删除投后部项目管理保证人
     *
     * @param bailIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectBailByIds(String[] bailIds);

    /**
     * 根据projectId查询保证人信息
     *
     * @param projectId 项目管理projectId
     * @return 结果
     */
    public List<SysProjectBail> selectSysProjectBailByProjectId(String[] projectId);
}
