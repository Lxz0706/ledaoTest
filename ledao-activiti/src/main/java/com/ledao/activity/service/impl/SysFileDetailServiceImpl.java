package com.ledao.activity.service.impl;

import java.util.List;
import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.activity.mapper.SysFileDetailMapper;
import com.ledao.activity.dao.SysFileDetail;
import com.ledao.activity.service.ISysFileDetailService;
import com.ledao.common.core.text.Convert;

/**
 * 档案详情Service业务层处理
 * 
 * @author lxz
 * @date 2021-08-04
 */
@Service
public class SysFileDetailServiceImpl implements ISysFileDetailService 
{
    @Autowired
    private SysFileDetailMapper sysFileDetailMapper;

    /**
     * 查询档案详情
     * 
     * @param fileId 档案详情ID
     * @return 档案详情
     */
    @Override
    public SysFileDetail selectSysFileDetailById(Long fileId)
    {
        return sysFileDetailMapper.selectSysFileDetailById(fileId);
    }

    /**
     * 查询档案详情列表
     * 
     * @param sysFileDetail 档案详情
     * @return 档案详情
     */
    @Override
    public List<SysFileDetail> selectSysFileDetailList(SysFileDetail sysFileDetail)
    {
        return sysFileDetailMapper.selectSysFileDetailList(sysFileDetail);
    }

    /**
     * 新增档案详情
     * 
     * @param sysFileDetail 档案详情
     * @return 结果
     */
    @Override
    public int insertSysFileDetail(SysFileDetail sysFileDetail)
    {
        sysFileDetail.setCreateTime(DateUtils.getNowDate());
        return sysFileDetailMapper.insertSysFileDetail(sysFileDetail);
    }

    /**
     * 修改档案详情
     * 
     * @param sysFileDetail 档案详情
     * @return 结果
     */
    @Override
    public int updateSysFileDetail(SysFileDetail sysFileDetail)
    {
        sysFileDetail.setUpdateTime(DateUtils.getNowDate());
        return sysFileDetailMapper.updateSysFileDetail(sysFileDetail);
    }

    /**
     * 删除档案详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysFileDetailByIds(String ids)
    {
        return sysFileDetailMapper.deleteSysFileDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除档案详情信息
     * 
     * @param fileId 档案详情ID
     * @return 结果
     */
    @Override
    public int deleteSysFileDetailById(Long fileId)
    {
        return sysFileDetailMapper.deleteSysFileDetailById(fileId);
    }
}
