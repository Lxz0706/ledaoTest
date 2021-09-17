package com.ledao.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import com.ledao.common.utils.DateUtils;
import com.ledao.system.dao.SysDictData;
import com.ledao.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysManageTaskMapper;
import com.ledao.system.dao.SysManageTask;
import com.ledao.system.service.ISysManageTaskService;
import com.ledao.common.core.text.Convert;

/**
 * 处置回现任务Service业务层处理
 * 
 * @author lxz
 * @date 2021-09-03
 */
@Service
public class SysManageTaskServiceImpl implements ISysManageTaskService 
{
    @Autowired
    private SysManageTaskMapper sysManageTaskMapper;
    @Autowired
    private ISysDictDataService sysDictDataService;

    /**
     * 查询处置回现任务
     * 
     * @param id 处置回现任务ID
     * @return 处置回现任务
     */
    @Override
    public SysManageTask selectSysManageTaskById(Long id)
    {
        return sysManageTaskMapper.selectSysManageTaskById(id);
    }

    /**
     * 查询处置回现任务列表
     * 
     * @param sysManageTask 处置回现任务
     * @return 处置回现任务
     */
    @Override
    public List<SysManageTask> selectSysManageTaskList(SysManageTask sysManageTask)
    {
        List<SysManageTask> list = sysManageTaskMapper.selectSysManageTaskList(sysManageTask);
        for (SysManageTask ta:list) {
            if ("other".equals(ta.getNodeStatu())){
                ta.setNodeStatu(ta.getRemarks1());
            }else{
                ta.setNodeStatu(sysDictDataService.selectDictLabel("subtask_project_ststus",ta.getNodeStatu()));
            }
        }
        return list;
    }

    /**
     * 新增处置回现任务
     * 
     * @param sysManageTask 处置回现任务
     * @return 结果
     */
    @Override
    public int insertSysManageTask(SysManageTask sysManageTask)
    {
        sysManageTask.setCreateTime(DateUtils.getNowDate());
        if (sysManageTask.getPlanEndTime()!=null && sysManageTask.getRealEndTime() !=null){
            long planEndTime = sysManageTask.getPlanEndTime().getTime();
            long realEndTime = sysManageTask.getRealEndTime().getTime();
            if (realEndTime-planEndTime>0){
                long days = DateUtils.differentDays(sysManageTask.getPlanEndTime(),sysManageTask.getRealEndTime());
                sysManageTask.setOverDay(days);
                sysManageTask.setTaskStatu("later");
            }else if (realEndTime-planEndTime<0){
                sysManageTask.setTaskStatu("preTime");
            }else{
                sysManageTask.setTaskStatu("onTime");
            }
        }else {
            sysManageTask.setTaskStatu("");
        }
        return sysManageTaskMapper.insertSysManageTask(sysManageTask);
    }

    /**
     * 修改处置回现任务
     * 
     * @param sysManageTask 处置回现任务
     * @return 结果
     */
    @Override
    public int updateSysManageTask(SysManageTask sysManageTask)
    {
        sysManageTask.setUpdateTime(DateUtils.getNowDate());
        if (sysManageTask.getPlanEndTime()!=null && sysManageTask.getPlanEndTime() !=null){
            long planEndTime = sysManageTask.getPlanEndTime().getTime();
            long realEndTime = sysManageTask.getRealEndTime().getTime();
            long days = DateUtils.differentDays(sysManageTask.getPlanEndTime(),sysManageTask.getRealEndTime());
            if (realEndTime-planEndTime>0){
                sysManageTask.setOverDay(days);
                sysManageTask.setTaskStatu("later");
            }else if (realEndTime-planEndTime<0){
                sysManageTask.setOverDay(0L);
                sysManageTask.setTaskStatu("preTime");
            }else{
                sysManageTask.setOverDay(0L);
                sysManageTask.setTaskStatu("onTime");
            }
        }else {
            sysManageTask.setTaskStatu("");
        }
        return sysManageTaskMapper.updateSysManageTask(sysManageTask);
    }

    /**
     * 删除处置回现任务对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysManageTaskByIds(String ids)
    {
        return sysManageTaskMapper.deleteSysManageTaskByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除处置回现任务信息
     * 
     * @param id 处置回现任务ID
     * @return 结果
     */
    @Override
    public int deleteSysManageTaskById(Long id)
    {
        return sysManageTaskMapper.deleteSysManageTaskById(id);
    }

    @Override
    public int updateTask(SysManageTask s) {
        return sysManageTaskMapper.updateSysManageTask(s);
    }
}
