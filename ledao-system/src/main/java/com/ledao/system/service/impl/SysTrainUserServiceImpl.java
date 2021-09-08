package com.ledao.system.service.impl;

import java.util.Date;
import java.util.List;

import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.system.dao.SysTrainAdmin;
import com.ledao.system.dao.SysUser;
import com.ledao.system.mapper.SysTrainAdminMapper;
import com.ledao.system.mapper.SysUserMapper;
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

    @Autowired
    private SysTrainAdminMapper sysTrainAdminMapper;

    @Autowired
    private SysUserMapper userMapper;

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
    public AjaxResult insertSysTrainUser(SysTrainUser sysTrainUser)
    {
        SysTrainAdmin trainAdmin = sysTrainAdminMapper.selectSysTrainAdminById(sysTrainUser.getTrainId());
        if (trainAdmin==null){
            return AjaxResult.error("未找到该会议");
        }
        long startTime = trainAdmin.getQrcodeStartTime().getTime();
        long endTime = trainAdmin.getQrcodeEndTime().getTime();
        long nowTime =DateUtils.getNowDate().getTime();
        if (startTime-nowTime>0 || endTime - nowTime <0){
            return AjaxResult.error("不在签到时间内");
        }
        if (StringUtils.isEmpty(sysTrainUser.getOpenId())){
            return AjaxResult.error("未收到openid");
        }
        SysUser u = new SysUser();
        u.setOpenId(sysTrainUser.getOpenId());
        List<SysUser> us = userMapper.selectUserList(u);
        if (us==null || us.size()==0){
            return AjaxResult.error("当前用户未入库");
        }
        SysUser loUser = us.get(0);

        SysTrainUser sysTrain = new SysTrainUser();
        sysTrain.setLoginName(loUser.getLoginName());
        List<SysTrainUser> trs =sysTrainUserMapper.selectSysTrainUserList(sysTrain);
        if (trs!=null && trs.size()>0){
            return AjaxResult.success("当前用户已签到");
        }
        sysTrainUser.setLoginName(loUser.getLoginName());
        sysTrainUser.setCreateTime(DateUtils.getNowDate());
        sysTrainUserMapper.insertSysTrainUser(sysTrainUser);
        return AjaxResult.success();
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
