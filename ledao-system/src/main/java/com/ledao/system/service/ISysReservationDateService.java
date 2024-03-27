package com.ledao.system.service;

import com.ledao.system.dao.SysReservationDate;

import java.util.List;

/**
 * 预约时间设置Service接口
 *
 * @author lxz
 * @date 2023-11-17
 */
public interface ISysReservationDateService {
    /**
     * 查询预约时间设置
     *
     * @param reservationDateId 预约时间设置ID
     * @return 预约时间设置
     */
    public SysReservationDate selectSysReservationDateById(Long reservationDateId);

    /**
     * 查询预约时间设置列表
     *
     * @param sysReservationDate 预约时间设置
     * @return 预约时间设置集合
     */
    public List<SysReservationDate> selectSysReservationDateList(SysReservationDate sysReservationDate);

    /**
     * 新增预约时间设置
     *
     * @param sysReservationDate 预约时间设置
     * @return 结果
     */
    public int insertSysReservationDate(SysReservationDate sysReservationDate);

    /**
     * 修改预约时间设置
     *
     * @param sysReservationDate 预约时间设置
     * @return 结果
     */
    public int updateSysReservationDate(SysReservationDate sysReservationDate);

    /**
     * 批量删除预约时间设置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysReservationDateByIds(String ids);

    /**
     * 删除预约时间设置信息
     *
     * @param reservationDateId 预约时间设置ID
     * @return 结果
     */
    public int deleteSysReservationDateById(Long reservationDateId);

    public int deleteSysReservationDate();
}