package com.ledao.system.service.impl;

import com.ledao.common.core.text.Convert;
import com.ledao.system.dao.SysReservationDate;
import com.ledao.system.mapper.SysReservationDateMapper;
import com.ledao.system.service.ISysReservationDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 预约时间设置Service业务层处理
 *
 * @author lxz
 * @date 2023-11-17
 */
@Service
public class SysReservationDateServiceImpl implements ISysReservationDateService {
    @Autowired
    private SysReservationDateMapper sysReservationDateMapper;

    /**
     * 查询预约时间设置
     *
     * @param reservationDateId 预约时间设置ID
     * @return 预约时间设置
     */
    @Override
    public SysReservationDate selectSysReservationDateById(Long reservationDateId) {
        return sysReservationDateMapper.selectSysReservationDateById(reservationDateId);
    }

    /**
     * 查询预约时间设置列表
     *
     * @param sysReservationDate 预约时间设置
     * @return 预约时间设置
     */
    @Override
    public List<SysReservationDate> selectSysReservationDateList(SysReservationDate sysReservationDate) {
        return sysReservationDateMapper.selectSysReservationDateList(sysReservationDate);
    }

    /**
     * 新增预约时间设置
     *
     * @param sysReservationDate 预约时间设置
     * @return 结果
     */
    @Override
    public int insertSysReservationDate(SysReservationDate sysReservationDate) {
        return sysReservationDateMapper.insertSysReservationDate(sysReservationDate);
    }

    /**
     * 修改预约时间设置
     *
     * @param sysReservationDate 预约时间设置
     * @return 结果
     */
    @Override
    public int updateSysReservationDate(SysReservationDate sysReservationDate) {
        return sysReservationDateMapper.updateSysReservationDate(sysReservationDate);
    }

    /**
     * 删除预约时间设置对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysReservationDateByIds(String ids) {
        return sysReservationDateMapper.deleteSysReservationDateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除预约时间设置信息
     *
     * @param reservationDateId 预约时间设置ID
     * @return 结果
     */
    @Override
    public int deleteSysReservationDateById(Long reservationDateId) {
        return sysReservationDateMapper.deleteSysReservationDateById(reservationDateId);
    }

    @Override
    public int deleteSysReservationDate() {
        return sysReservationDateMapper.deleteSysReservationDate();
    }
}
