package com.ledao.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysHolidayMapper;
import com.ledao.system.dao.SysHoliday;
import com.ledao.system.service.ISysHolidayService;
import com.ledao.common.core.text.Convert;

/**
 * 节假日Service业务层处理
 *
 * @author lxz
 * @date 2022-11-01
 */
@Service
public class SysHolidayServiceImpl implements ISysHolidayService {
    @Autowired
    private SysHolidayMapper sysHolidayMapper;

    /**
     * 查询节假日
     *
     * @param holiday 节假日ID
     * @return 节假日
     */
    @Override
    public SysHoliday selectSysHolidayById(String holiday) {
        return sysHolidayMapper.selectSysHolidayById(holiday);
    }

    /**
     * 查询节假日列表
     *
     * @param sysHoliday 节假日
     * @return 节假日
     */
    @Override
    public List<SysHoliday> selectSysHolidayList(SysHoliday sysHoliday) {
        return sysHolidayMapper.selectSysHolidayList(sysHoliday);
    }

    /**
     * 新增节假日
     *
     * @param sysHoliday 节假日
     * @return 结果
     */
    @Override
    public int insertSysHoliday(SysHoliday sysHoliday) {
        return sysHolidayMapper.insertSysHoliday(sysHoliday);
    }

    /**
     * 修改节假日
     *
     * @param sysHoliday 节假日
     * @return 结果
     */
    @Override
    public int updateSysHoliday(SysHoliday sysHoliday) {
        return sysHolidayMapper.updateSysHoliday(sysHoliday);
    }

    /**
     * 删除节假日对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysHolidayByIds(String ids) {
        return sysHolidayMapper.deleteSysHolidayByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除节假日信息
     *
     * @param holiday 节假日ID
     * @return 结果
     */
    @Override
    public int deleteSysHolidayById(String holiday) {
        return sysHolidayMapper.deleteSysHolidayById(holiday);
    }

    /**
     * 删除数据
     *
     * @return
     */
    public int deleteSysHoliday() {
        return sysHolidayMapper.deleteSysHoliday();
    }
}
