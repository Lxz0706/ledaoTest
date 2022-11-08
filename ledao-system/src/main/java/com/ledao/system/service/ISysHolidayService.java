package com.ledao.system.service;

import java.util.List;

import com.ledao.system.dao.SysHoliday;

/**
 * 节假日Service接口
 *
 * @author lxz
 * @date 2022-11-01
 */
public interface ISysHolidayService {
    /**
     * 查询节假日
     *
     * @param holiday 节假日ID
     * @return 节假日
     */
    public SysHoliday selectSysHolidayById(String holiday);

    /**
     * 查询节假日列表
     *
     * @param sysHoliday 节假日
     * @return 节假日集合
     */
    public List<SysHoliday> selectSysHolidayList(SysHoliday sysHoliday);

    /**
     * 新增节假日
     *
     * @param sysHoliday 节假日
     * @return 结果
     */
    public int insertSysHoliday(SysHoliday sysHoliday);

    /**
     * 修改节假日
     *
     * @param sysHoliday 节假日
     * @return 结果
     */
    public int updateSysHoliday(SysHoliday sysHoliday);

    /**
     * 批量删除节假日
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysHolidayByIds(String ids);

    /**
     * 删除节假日信息
     *
     * @param holiday 节假日ID
     * @return 结果
     */
    public int deleteSysHolidayById(String holiday);

    /**
     * 删除数据
     *
     * @return
     */
    public int deleteSysHoliday();
}
