package com.ledao.system.mapper;

import java.util.List;
import com.ledao.system.dao.SysManageTask;

/**
 * 处置回现任务Mapper接口
 * 
 * @author lxz
 * @date 2021-09-03
 */
public interface SysManageTaskMapper 
{
    /**
     * 查询处置回现任务
     * 
     * @param id 处置回现任务ID
     * @return 处置回现任务
     */
    public SysManageTask selectSysManageTaskById(Long id);

    /**
     * 查询处置回现任务列表
     * 
     * @param sysManageTask 处置回现任务
     * @return 处置回现任务集合
     */
    public List<SysManageTask> selectSysManageTaskList(SysManageTask sysManageTask);

    /**
     * 新增处置回现任务
     * 
     * @param sysManageTask 处置回现任务
     * @return 结果
     */
    public int insertSysManageTask(SysManageTask sysManageTask);

    /**
     * 修改处置回现任务
     * 
     * @param sysManageTask 处置回现任务
     * @return 结果
     */
    public int updateSysManageTask(SysManageTask sysManageTask);

    /**
     * 删除处置回现任务
     * 
     * @param id 处置回现任务ID
     * @return 结果
     */
    public int deleteSysManageTaskById(Long id);

    /**
     * 批量删除处置回现任务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysManageTaskByIds(String[] ids);
}
