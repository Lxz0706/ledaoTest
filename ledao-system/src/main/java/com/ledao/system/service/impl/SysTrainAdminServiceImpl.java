package com.ledao.system.service.impl;

import java.util.List;
import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysTrainAdminMapper;
import com.ledao.system.dao.SysTrainAdmin;
import com.ledao.system.service.ISysTrainAdminService;
import com.ledao.common.core.text.Convert;

/**
 * 签到管理Service业务层处理
 * 
 * @author lxz
 * @date 2021-08-29
 */
@Service
public class SysTrainAdminServiceImpl implements ISysTrainAdminService 
{
    @Autowired
    private SysTrainAdminMapper sysTrainAdminMapper;

    /**
     * 查询签到管理
     * 
     * @param trainId 签到管理ID
     * @return 签到管理
     */
    @Override
    public SysTrainAdmin selectSysTrainAdminById(Long trainId)
    {
        return sysTrainAdminMapper.selectSysTrainAdminById(trainId);
    }

    /**
     * 查询签到管理列表
     * 
     * @param sysTrainAdmin 签到管理
     * @return 签到管理
     */
    @Override
    public List<SysTrainAdmin> selectSysTrainAdminList(SysTrainAdmin sysTrainAdmin)
    {
        return sysTrainAdminMapper.selectSysTrainAdminList(sysTrainAdmin);
    }

    /**
     * 新增签到管理
     * 
     * @param sysTrainAdmin 签到管理
     * @return 结果
     */
    @Override
    public int insertSysTrainAdmin(SysTrainAdmin sysTrainAdmin)
    {
        sysTrainAdmin.setCreateTime(DateUtils.getNowDate());
        return sysTrainAdminMapper.insertSysTrainAdmin(sysTrainAdmin);
    }

    /**
     * 修改签到管理
     * 
     * @param sysTrainAdmin 签到管理
     * @return 结果
     */
    @Override
    public int updateSysTrainAdmin(SysTrainAdmin sysTrainAdmin)
    {
        sysTrainAdmin.setUpdateTime(DateUtils.getNowDate());
        return sysTrainAdminMapper.updateSysTrainAdmin(sysTrainAdmin);
    }

    /**
     * 删除签到管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysTrainAdminByIds(String ids)
    {
        return sysTrainAdminMapper.deleteSysTrainAdminByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除签到管理信息
     * 
     * @param trainId 签到管理ID
     * @return 结果
     */
    @Override
    public int deleteSysTrainAdminById(Long trainId)
    {
        return sysTrainAdminMapper.deleteSysTrainAdminById(trainId);
    }
}
