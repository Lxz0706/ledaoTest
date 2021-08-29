package com.ledao.system.service;

import java.util.List;
import com.ledao.system.dao.SysTrainAdmin;

/**
 * 签到管理Service接口
 * 
 * @author lxz
 * @date 2021-08-29
 */
public interface ISysTrainAdminService 
{
    /**
     * 查询签到管理
     * 
     * @param trainId 签到管理ID
     * @return 签到管理
     */
    public SysTrainAdmin selectSysTrainAdminById(Long trainId);

    /**
     * 查询签到管理列表
     * 
     * @param sysTrainAdmin 签到管理
     * @return 签到管理集合
     */
    public List<SysTrainAdmin> selectSysTrainAdminList(SysTrainAdmin sysTrainAdmin);

    /**
     * 新增签到管理
     * 
     * @param sysTrainAdmin 签到管理
     * @return 结果
     */
    public int insertSysTrainAdmin(SysTrainAdmin sysTrainAdmin);

    /**
     * 修改签到管理
     * 
     * @param sysTrainAdmin 签到管理
     * @return 结果
     */
    public int updateSysTrainAdmin(SysTrainAdmin sysTrainAdmin);

    /**
     * 批量删除签到管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysTrainAdminByIds(String ids);

    /**
     * 删除签到管理信息
     * 
     * @param trainId 签到管理ID
     * @return 结果
     */
    public int deleteSysTrainAdminById(Long trainId);
}
