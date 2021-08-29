package com.ledao.system.service.impl;

import java.util.List;
import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysTrainUserMapper;
import com.ledao.system.dao.SysTrainUser;
import com.ledao.system.service.ISysTrainUserService;
import com.ledao.common.core.text.Convert;

/**
 * 签到人员Service业务层处理
 * 
 * @author lxz
 * @date 2021-08-29
 */
@Service
public class SysTrainUserServiceImpl implements ISysTrainUserService 
{
    @Autowired
    private SysTrainUserMapper sysTrainUserMapper;

    /**
     * 查询签到人员
     * 
     * @param id 签到人员ID
     * @return 签到人员
     */
    @Override
    public SysTrainUser selectSysTrainUserById(Long id)
    {
        return sysTrainUserMapper.selectSysTrainUserById(id);
    }

    /**
     * 查询签到人员列表
     * 
     * @param sysTrainUser 签到人员
     * @return 签到人员
     */
    @Override
    public List<SysTrainUser> selectSysTrainUserList(SysTrainUser sysTrainUser)
    {
        return sysTrainUserMapper.selectSysTrainUserList(sysTrainUser);
    }

    /**
     * 新增签到人员
     * 
     * @param sysTrainUser 签到人员
     * @return 结果
     */
    @Override
    public int insertSysTrainUser(SysTrainUser sysTrainUser)
    {
        sysTrainUser.setCreateTime(DateUtils.getNowDate());
        return sysTrainUserMapper.insertSysTrainUser(sysTrainUser);
    }

    /**
     * 修改签到人员
     * 
     * @param sysTrainUser 签到人员
     * @return 结果
     */
    @Override
    public int updateSysTrainUser(SysTrainUser sysTrainUser)
    {
        sysTrainUser.setUpdateTime(DateUtils.getNowDate());
        return sysTrainUserMapper.updateSysTrainUser(sysTrainUser);
    }

    /**
     * 删除签到人员对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysTrainUserByIds(String ids)
    {
        return sysTrainUserMapper.deleteSysTrainUserByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除签到人员信息
     * 
     * @param id 签到人员ID
     * @return 结果
     */
    @Override
    public int deleteSysTrainUserById(Long id)
    {
        return sysTrainUserMapper.deleteSysTrainUserById(id);
    }
}
