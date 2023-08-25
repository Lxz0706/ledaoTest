package com.ledao.system.service.impl;

import java.util.List;
import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysTrainingMapper;
import com.ledao.system.dao.SysTraining;
import com.ledao.system.service.ISysTrainingService;
import com.ledao.common.core.text.Convert;

/**
 * 学习培训Service业务层处理
 * 
 * @author lxz
 * @date 2023-07-13
 */
@Service
public class SysTrainingServiceImpl implements ISysTrainingService 
{
    @Autowired
    private SysTrainingMapper sysTrainingMapper;

    /**
     * 查询学习培训
     * 
     * @param trainId 学习培训ID
     * @return 学习培训
     */
    @Override
    public SysTraining selectSysTrainingById(Long trainId)
    {
        return sysTrainingMapper.selectSysTrainingById(trainId);
    }

    /**
     * 查询学习培训列表
     * 
     * @param sysTraining 学习培训
     * @return 学习培训
     */
    @Override
    public List<SysTraining> selectSysTrainingList(SysTraining sysTraining)
    {
        return sysTrainingMapper.selectSysTrainingList(sysTraining);
    }

    /**
     * 新增学习培训
     * 
     * @param sysTraining 学习培训
     * @return 结果
     */
    @Override
    public int insertSysTraining(SysTraining sysTraining)
    {
        sysTraining.setCreateTime(DateUtils.getNowDate());
        return sysTrainingMapper.insertSysTraining(sysTraining);
    }

    /**
     * 修改学习培训
     * 
     * @param sysTraining 学习培训
     * @return 结果
     */
    @Override
    public int updateSysTraining(SysTraining sysTraining)
    {
        sysTraining.setUpdateTime(DateUtils.getNowDate());
        return sysTrainingMapper.updateSysTraining(sysTraining);
    }

    /**
     * 删除学习培训对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysTrainingByIds(String ids)
    {
        return sysTrainingMapper.deleteSysTrainingByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除学习培训信息
     * 
     * @param trainId 学习培训ID
     * @return 结果
     */
    @Override
    public int deleteSysTrainingById(Long trainId)
    {
        return sysTrainingMapper.deleteSysTrainingById(trainId);
    }
}
