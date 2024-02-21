package com.ledao.system.service;

import java.util.List;
import com.ledao.system.dao.SysReservation;

/**
 * 预约Service接口
 * 
 * @author lxz
 * @date 2023-11-17
 */
public interface ISysReservationService 
{
    /**
     * 查询预约
     * 
     * @param reservationId 预约ID
     * @return 预约
     */
    public SysReservation selectSysReservationById(Long reservationId);

    /**
     * 查询预约列表
     * 
     * @param sysReservation 预约
     * @return 预约集合
     */
    public List<SysReservation> selectSysReservationList(SysReservation sysReservation);

    /**
     * 新增预约
     * 
     * @param sysReservation 预约
     * @return 结果
     */
    public int insertSysReservation(SysReservation sysReservation);

    /**
     * 修改预约
     * 
     * @param sysReservation 预约
     * @return 结果
     */
    public int updateSysReservation(SysReservation sysReservation);

    /**
     * 批量删除预约
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysReservationByIds(String ids);

    /**
     * 删除预约信息
     * 
     * @param reservationId 预约ID
     * @return 结果
     */
    public int deleteSysReservationById(Long reservationId);
}
