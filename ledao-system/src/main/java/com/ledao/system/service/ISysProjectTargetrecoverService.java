package com.ledao.system.service;

import java.util.List;

import com.ledao.system.dao.SysProjectTargetrecover;

/**
 * 目标回收Service接口
 *
 * @author ledao
 * @date 2020-09-03
 */
public interface ISysProjectTargetrecoverService {
    /**
     * 查询目标回收
     *
     * @param id 目标回收ID
     * @return 目标回收
     */
    public SysProjectTargetrecover selectSysProjectTargetrecoverById(Long id);

    /**
     * 查询目标回收列表
     *
     * @param sysProjectTargetrecover 目标回收
     * @return 目标回收集合
     */
    public List<SysProjectTargetrecover> selectSysProjectTargetrecoverList(SysProjectTargetrecover sysProjectTargetrecover);

    /**
     * 新增目标回收
     *
     * @param sysProjectTargetrecover 目标回收
     * @return 结果
     */
    public int insertSysProjectTargetrecover(SysProjectTargetrecover sysProjectTargetrecover);

    /**
     * 修改目标回收
     *
     * @param sysProjectTargetrecover 目标回收
     * @return 结果
     */
    public int updateSysProjectTargetrecover(SysProjectTargetrecover sysProjectTargetrecover);

    /**
     * 批量删除目标回收
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectTargetrecoverByIds(String ids);

    /**
     * 删除目标回收信息
     *
     * @param id 目标回收ID
     * @return 结果
     */
    public int deleteSysProjectTargetrecoverById(Long id);
}
