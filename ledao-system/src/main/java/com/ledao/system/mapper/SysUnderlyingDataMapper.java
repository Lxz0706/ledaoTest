package com.ledao.system.mapper;

import java.util.List;
import com.ledao.system.dao.SysUnderlyingData;

/**
 * 底层资料Mapper接口
 * 
 * @author lxz
 * @date 2021-09-07
 */
public interface SysUnderlyingDataMapper 
{
    /**
     * 查询底层资料
     * 
     * @param fileId 底层资料ID
     * @return 底层资料
     */
    public SysUnderlyingData selectSysUnderlyingDataById(Long fileId);

    /**
     * 查询底层资料列表
     * 
     * @param sysUnderlyingData 底层资料
     * @return 底层资料集合
     */
    public List<SysUnderlyingData> selectSysUnderlyingDataList(SysUnderlyingData sysUnderlyingData);

    /**
     * 新增底层资料
     * 
     * @param sysUnderlyingData 底层资料
     * @return 结果
     */
    public int insertSysUnderlyingData(SysUnderlyingData sysUnderlyingData);

    /**
     * 修改底层资料
     * 
     * @param sysUnderlyingData 底层资料
     * @return 结果
     */
    public int updateSysUnderlyingData(SysUnderlyingData sysUnderlyingData);

    /**
     * 删除底层资料
     * 
     * @param fileId 底层资料ID
     * @return 结果
     */
    public int deleteSysUnderlyingDataById(Long fileId);

    /**
     * 批量删除底层资料
     * 
     * @param fileIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysUnderlyingDataByIds(String[] fileIds);

    /**
     * 根据项目id查询底层资料
     * @param projectId
     * @return
     */
    List<SysUnderlyingData> selectSysUnderlyingDataByPid(Long projectId);
}
