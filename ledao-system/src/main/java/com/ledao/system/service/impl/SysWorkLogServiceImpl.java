package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import com.ledao.system.dao.SysWorkLog;
import com.ledao.system.mapper.SysWorkLogMapper;
import com.ledao.system.service.ISysWorkLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.common.core.text.Convert;

/**
 * 工作日志Service业务层处理
 *
 * @author lxz
 * @date 2020-06-09
 */
@Service
public class SysWorkLogServiceImpl implements ISysWorkLogService {
    @Autowired
    private SysWorkLogMapper sysWorkLogMapper;

    /**
     * 查询工作日志
     *
     * @param id 工作日志ID
     * @return 工作日志
     */
    @Override
    public SysWorkLog selectSysWorkLogById(Long id) {
        return sysWorkLogMapper.selectSysWorkLogById(id);
    }

    /**
     * 查询工作日志列表
     *
     * @param sysWorkLog 工作日志
     * @return 工作日志
     */
    @Override
    public List<SysWorkLog> selectSysWorkLogList(SysWorkLog sysWorkLog) {
        return sysWorkLogMapper.selectSysWorkLogList(sysWorkLog);
    }

    /**
     * 新增工作日志
     *
     * @param sysWorkLog 工作日志
     * @return 结果
     */
    @Override
    public int insertSysWorkLog(SysWorkLog sysWorkLog) {
        sysWorkLog.setCreateTime(DateUtils.getNowDate());
        return sysWorkLogMapper.insertSysWorkLog(sysWorkLog);
    }

    /**
     * 修改工作日志
     *
     * @param sysWorkLog 工作日志
     * @return 结果
     */
    @Override
    public int updateSysWorkLog(SysWorkLog sysWorkLog) {
        sysWorkLog.setUpdateTime(DateUtils.getNowDate());
        return sysWorkLogMapper.updateSysWorkLog(sysWorkLog);
    }

    /**
     * 删除工作日志对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysWorkLogByIds(String ids) {
        return sysWorkLogMapper.deleteSysWorkLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除工作日志信息
     *
     * @param id 工作日志ID
     * @return 结果
     */
    @Override
    public int deleteSysWorkLogById(Long id) {
        return sysWorkLogMapper.deleteSysWorkLogById(id);
    }
}
