package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysOperLog;

/**
 * 操作日志 数据层
 *
 * @author lxz
 */
public interface SysOperLogMapper {
    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    public void insertOperlog(SysOperLog operLog);

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    public List<SysOperLog> selectOperLogList(SysOperLog operLog);

    /**
     * 批量删除系统操作日志
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    public int deleteOperLogByIds(String[] ids);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    public SysOperLog selectOperLogById(Long operId);

    /**
     * 清空操作日志
     */
    public void cleanOperLog();

    /**
     * 根据title分组查询（A资产，网拍线索，星标库）
     *
     * @param sysOperLog
     * @return
     */
    public List<SysOperLog> selectOperLogByTitle(SysOperLog sysOperLog);

    /**
     * 根据人员进行分组查询
     *
     * @param sysOperLog
     * @return
     */
    public List<SysOperLog> selectOperLogByOperName(SysOperLog sysOperLog);

    /**
     * 根据时间进行分组查询
     *
     * @param sysOperLog
     * @return
     */
    public List<SysOperLog> selectSysOperLogByOperTime(SysOperLog sysOperLog);
}
