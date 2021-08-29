package com.ledao.system.mapper;

import java.util.List;
import com.ledao.system.dao.SysTrainUser;

/**
 * 签到人员Mapper接口
 * 
 * @author lxz
 * @date 2021-08-29
 */
public interface SysTrainUserMapper 
{
    /**
     * 查询签到人员
     * 
     * @param id 签到人员ID
     * @return 签到人员
     */
    public SysTrainUser selectSysTrainUserById(Long id);

    /**
     * 查询签到人员列表
     * 
     * @param sysTrainUser 签到人员
     * @return 签到人员集合
     */
    public List<SysTrainUser> selectSysTrainUserList(SysTrainUser sysTrainUser);

    /**
     * 新增签到人员
     * 
     * @param sysTrainUser 签到人员
     * @return 结果
     */
    public int insertSysTrainUser(SysTrainUser sysTrainUser);

    /**
     * 修改签到人员
     * 
     * @param sysTrainUser 签到人员
     * @return 结果
     */
    public int updateSysTrainUser(SysTrainUser sysTrainUser);

    /**
     * 删除签到人员
     * 
     * @param id 签到人员ID
     * @return 结果
     */
    public int deleteSysTrainUserById(Long id);

    /**
     * 批量删除签到人员
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysTrainUserByIds(String[] ids);
}
