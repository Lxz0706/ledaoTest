package com.ledao.system.mapper;

import java.util.List;
import com.ledao.system.dao.SysProjectRecovered;

/**
 * 已回收Mapper接口
 * 
 * @author ledao
 * @date 2020-09-03
 */
public interface SysProjectRecoveredMapper 
{
    /**
     * 查询已回收
     * 
     * @param id 已回收ID
     * @return 已回收
     */
    public SysProjectRecovered selectSysProjectRecoveredById(Long id);

    /**
     * 查询已回收列表
     * 
     * @param sysProjectRecovered 已回收
     * @return 已回收集合
     */
    public List<SysProjectRecovered> selectSysProjectRecoveredList(SysProjectRecovered sysProjectRecovered);

    /**
     * 新增已回收
     * 
     * @param sysProjectRecovered 已回收
     * @return 结果
     */
    public int insertSysProjectRecovered(SysProjectRecovered sysProjectRecovered);

    /**
     * 修改已回收
     * 
     * @param sysProjectRecovered 已回收
     * @return 结果
     */
    public int updateSysProjectRecovered(SysProjectRecovered sysProjectRecovered);

    /**
     * 删除已回收
     * 
     * @param id 已回收ID
     * @return 结果
     */
    public int deleteSysProjectRecoveredById(Long id);

    /**
     * 批量删除已回收
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectRecoveredByIds(String[] ids);
}
