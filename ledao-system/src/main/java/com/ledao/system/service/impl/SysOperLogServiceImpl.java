package com.ledao.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.common.core.text.Convert;
import com.ledao.system.dao.SysOperLog;
import com.ledao.system.mapper.SysOperLogMapper;
import com.ledao.system.service.ISysOperLogService;

/**
 * 操作日志 服务层处理
 *
 * @author lxz
 */
@Service
public class SysOperLogServiceImpl implements ISysOperLogService {
    @Autowired
    private SysOperLogMapper operLogMapper;

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(SysOperLog operLog) {
        operLogMapper.insertOperlog(operLog);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SysOperLog> selectOperLogList(SysOperLog operLog) {
        return operLogMapper.selectOperLogList(operLog);
    }

    /**
     * 批量删除系统操作日志
     *
     * @param ids 需要删除的数据
     * @return
     */
    @Override
    public int deleteOperLogByIds(String ids) {
        return operLogMapper.deleteOperLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysOperLog selectOperLogById(Long operId) {
        return operLogMapper.selectOperLogById(operId);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        operLogMapper.cleanOperLog();
    }

    /**
     * 根据title分组查询（A资产，网拍线索，星标库）
     *
     * @param sysOperLog
     * @return
     */
    @Override
    public List<SysOperLog> selectOperLogByTitle(SysOperLog sysOperLog) {
        return operLogMapper.selectOperLogByTitle(sysOperLog);
    }

    /**
     * 根据人员进行分组查询
     *
     * @param sysOperLog
     * @return
     */
    @Override
    public List<SysOperLog> selectOperLogByOperName(SysOperLog sysOperLog) {
        return operLogMapper.selectOperLogByOperName(sysOperLog);
    }

    /**
     * 根据时间进行分组查询
     *
     * @param sysOperLog
     * @return
     */
    @Override
    public List<SysOperLog> selectSysOperLogByOperTime(SysOperLog sysOperLog) {
        return operLogMapper.selectSysOperLogByOperTime(sysOperLog);
    }
}
