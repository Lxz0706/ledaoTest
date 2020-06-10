package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.domain.SysWorkLog;

/**
 * 工作日志Mapper接口
 *
 * @author ledao
 * @date 2020-06-09
 */
public interface SysWorkLogMapper {
    /**
     * 查询工作日志
     *
     * @param id 工作日志ID
     * @return 工作日志
     */
    public SysWorkLog selectSysWorkLogById(Long id);

    /**
     * 查询工作日志列表
     *
     * @param sysWorkLog 工作日志
     * @return 工作日志集合
     */
    public List<SysWorkLog> selectSysWorkLogList(SysWorkLog sysWorkLog);

    /**
     * 新增工作日志
     *
     * @param sysWorkLog 工作日志
     * @return 结果
     */
    public int insertSysWorkLog(SysWorkLog sysWorkLog);

    /**
     * 修改工作日志
     *
     * @param sysWorkLog 工作日志
     * @return 结果
     */
    public int updateSysWorkLog(SysWorkLog sysWorkLog);

    /**
     * 删除工作日志
     *
     * @param id 工作日志ID
     * @return 结果
     */
    public int deleteSysWorkLogById(Long id);

    /**
     * 批量删除工作日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysWorkLogByIds(String[] ids);
}
