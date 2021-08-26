package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysProjectZck;

/**
 * 项目管理资产库Mapper接口
 *
 * @author ledao
 * @date 2020-08-12
 */
public interface SysProjectZckMapper {
    /**
     * 查询项目管理资产库
     *
     * @param projectZckId 项目管理资产库ID
     * @return 项目管理资产库
     */
    public SysProjectZck selectSysProjectZckById(Long projectZckId);

    /**
     * 查询项目管理资产库列表
     *
     * @param sysProjectZck 项目管理资产库
     * @return 项目管理资产库集合
     */
    public List<SysProjectZck> selectSysProjectZckList(SysProjectZck sysProjectZck);

    /**
     * 新增项目管理资产库
     *
     * @param sysProjectZck 项目管理资产库
     * @return 结果
     */
    public int insertSysProjectZck(SysProjectZck sysProjectZck);

    /**
     * 修改项目管理资产库
     *
     * @param sysProjectZck 项目管理资产库
     * @return 结果
     */
    public int updateSysProjectZck(SysProjectZck sysProjectZck);

    /**
     * 删除项目管理资产库
     *
     * @param projectZckId 项目管理资产库ID
     * @return 结果
     */
    public int deleteSysProjectZckById(Long projectZckId);

    /**
     * 批量删除项目管理资产库
     *
     * @param projectZckIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectZckByIds(String[] projectZckIds);

    /**
     * 根据id数组查询
     *
     * @param ids 数组
     * @return 结果
     */
    public List<SysProjectZck> selectSysProjectZckByIds(String[] ids);

    /**
     * @return
     */
    public List<SysProjectZck> selectSysProjectZckByType();

    /**
     * 根据资产包状态进行分组查询出数量
     *
     * @param projectZckType
     * @return
     */
    public List<SysProjectZck> selectProjectZckGroupByType(String projectZckType);
}
