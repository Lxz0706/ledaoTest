package com.ledao.system.mapper;

import java.util.List;
import com.ledao.system.dao.SysJournal;

/**
 * 日志Mapper接口
 * 
 * @author lxz
 * @date 2021-09-05
 */
public interface SysJournalMapper 
{
    /**
     * 查询日志
     * 
     * @param id 日志ID
     * @return 日志
     */
    public SysJournal selectSysJournalById(Long id);

    /**
     * 查询日志列表
     * 
     * @param sysJournal 日志
     * @return 日志集合
     */
    public List<SysJournal> selectSysJournalList(SysJournal sysJournal);

    /**
     * 新增日志
     * 
     * @param sysJournal 日志
     * @return 结果
     */
    public int insertSysJournal(SysJournal sysJournal);

    /**
     * 修改日志
     * 
     * @param sysJournal 日志
     * @return 结果
     */
    public int updateSysJournal(SysJournal sysJournal);

    /**
     * 删除日志
     * 
     * @param id 日志ID
     * @return 结果
     */
    public int deleteSysJournalById(Long id);

    /**
     * 批量删除日志
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysJournalByIds(String[] ids);
}
