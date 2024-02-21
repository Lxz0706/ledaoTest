package com.ledao.system.service.impl;

import java.util.List;
import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysReservationMapper;
import com.ledao.system.dao.SysReservation;
import com.ledao.system.service.ISysReservationService;
import com.ledao.common.core.text.Convert;

/**
 * 预约Service业务层处理
 * 
 * @author lxz
 * @date 2023-11-17
 */
@Service
public class SysReservationServiceImpl implements ISysReservationService 
{
    @Autowired
    private SysReservationMapper sysReservationMapper;

    /**
     * 查询预约
     * 
     * @param reservationId 预约ID
     * @return 预约
     */
    @Override
    public SysReservation selectSysReservationById(Long reservationId)
    {
        return sysReservationMapper.selectSysReservationById(reservationId);
    }

    /**
     * 查询预约列表
     * 
     * @param sysReservation 预约
     * @return 预约
     */
    @Override
    public List<SysReservation> selectSysReservationList(SysReservation sysReservation)
    {
        return sysReservationMapper.selectSysReservationList(sysReservation);
    }

    /**
     * 新增预约
     * 
     * @param sysReservation 预约
     * @return 结果
     */
    @Override
    public int insertSysReservation(SysReservation sysReservation)
    {
        sysReservation.setCreateTime(DateUtils.getNowDate());
        return sysReservationMapper.insertSysReservation(sysReservation);
    }

    /**
     * 修改预约
     * 
     * @param sysReservation 预约
     * @return 结果
     */
    @Override
    public int updateSysReservation(SysReservation sysReservation)
    {
        sysReservation.setUpdateTime(DateUtils.getNowDate());
        return sysReservationMapper.updateSysReservation(sysReservation);
    }

    /**
     * 删除预约对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysReservationByIds(String ids)
    {
        return sysReservationMapper.deleteSysReservationByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除预约信息
     * 
     * @param reservationId 预约ID
     * @return 结果
     */
    @Override
    public int deleteSysReservationById(Long reservationId)
    {
        return sysReservationMapper.deleteSysReservationById(reservationId);
    }
}
