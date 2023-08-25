package com.ledao.system.mapper;

import java.util.List;
import com.ledao.system.dao.SysTraining;

/**
 * 学习培训Mapper接口
 * 
 * @author lxz
 * @date 2023-07-13
 */
public interface SysTrainingMapper 
{
    /**
     * 查询学习培训
     * 
     * @param trainId 学习培训ID
     * @return 学习培训
     */
    public SysTraining selectSysTrainingById(Long trainId);

    /**
     * 查询学习培训列表
     * 
     * @param sysTraining 学习培训
     * @return 学习培训集合
     */
    public List<SysTraining> selectSysTrainingList(SysTraining sysTraining);

    /**
     * 新增学习培训
     * 
     * @param sysTraining 学习培训
     * @return 结果
     */
    public int insertSysTraining(SysTraining sysTraining);

    /**
     * 修改学习培训
     * 
     * @param sysTraining 学习培训
     * @return 结果
     */
    public int updateSysTraining(SysTraining sysTraining);

    /**
     * 删除学习培训
     * 
     * @param trainId 学习培训ID
     * @return 结果
     */
    public int deleteSysTrainingById(Long trainId);

    /**
     * 批量删除学习培训
     * 
     * @param trainIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysTrainingByIds(String[] trainIds);
}
