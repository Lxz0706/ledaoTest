package com.ledao.system.service.impl;

import java.util.List;
import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysUnderlyingDataMapper;
import com.ledao.system.dao.SysUnderlyingData;
import com.ledao.system.service.ISysUnderlyingDataService;
import com.ledao.common.core.text.Convert;

/**
 * 底层资料Service业务层处理
 * 
 * @author lxz
 * @date 2021-09-07
 */
@Service
public class SysUnderlyingDataServiceImpl implements ISysUnderlyingDataService 
{
    @Autowired
    private SysUnderlyingDataMapper sysUnderlyingDataMapper;

    /**
     * 查询底层资料
     * 
     * @param fileId 底层资料ID
     * @return 底层资料
     */
    @Override
    public SysUnderlyingData selectSysUnderlyingDataById(Long fileId)
    {
        return sysUnderlyingDataMapper.selectSysUnderlyingDataById(fileId);
    }

    /**
     * 查询底层资料列表
     * 
     * @param sysUnderlyingData 底层资料
     * @return 底层资料
     */
    @Override
    public List<SysUnderlyingData> selectSysUnderlyingDataList(SysUnderlyingData sysUnderlyingData)
    {
        return sysUnderlyingDataMapper.selectSysUnderlyingDataList(sysUnderlyingData);
    }

    /**
     * 新增底层资料
     * 
     * @param sysUnderlyingData 底层资料
     * @return 结果
     */
    @Override
    public int insertSysUnderlyingData(SysUnderlyingData sysUnderlyingData)
    {
        sysUnderlyingData.setCreateTime(DateUtils.getNowDate());
        return sysUnderlyingDataMapper.insertSysUnderlyingData(sysUnderlyingData);
    }

    /**
     * 修改底层资料
     * 
     * @param sysUnderlyingData 底层资料
     * @return 结果
     */
    @Override
    public int updateSysUnderlyingData(SysUnderlyingData sysUnderlyingData)
    {
        sysUnderlyingData.setUpdateTime(DateUtils.getNowDate());
        return sysUnderlyingDataMapper.updateSysUnderlyingData(sysUnderlyingData);
    }

    /**
     * 删除底层资料对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysUnderlyingDataByIds(String ids)
    {
        return sysUnderlyingDataMapper.deleteSysUnderlyingDataByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除底层资料信息
     * 
     * @param fileId 底层资料ID
     * @return 结果
     */
    @Override
    public int deleteSysUnderlyingDataById(Long fileId)
    {
        return sysUnderlyingDataMapper.deleteSysUnderlyingDataById(fileId);
    }

    @Override
    public List<SysUnderlyingData> selectSysUnderlyingDataByPid(Long projectId,Long projectType) {
        return sysUnderlyingDataMapper.selectSysUnderlyingDataByPid(projectId, projectType);
    }

    @Override
    public List<SysUnderlyingData> selectSysUnderlyingProDataList(SysUnderlyingData sysUnderlyingData) {
        return sysUnderlyingDataMapper.selectSysUnderlyingProDataList(sysUnderlyingData);
    }
}
