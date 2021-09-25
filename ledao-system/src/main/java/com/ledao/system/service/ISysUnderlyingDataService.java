package com.ledao.system.service;

import java.util.List;

import com.ledao.common.core.dao.AjaxResult;
import com.ledao.system.dao.SysUnderlyingData;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

/**
 * 底层资料Service接口
 * 
 * @author lxz
 * @date 2021-09-07
 */
public interface ISysUnderlyingDataService 
{
    /**
     * 查询底层资料
     * 
     * @param fileId 底层资料ID
     * @return 底层资料
     */
    public SysUnderlyingData selectSysUnderlyingDataById(Long fileId);

    /**
     * 查询底层资料列表
     * 
     * @param sysUnderlyingData 底层资料
     * @return 底层资料集合
     */
    public List<SysUnderlyingData> selectSysUnderlyingDataList(SysUnderlyingData sysUnderlyingData);

    /**
     * 新增底层资料
     * 
     * @param sysUnderlyingData 底层资料
     * @return 结果
     */
    public AjaxResult insertSysUnderlyingData(SysUnderlyingData sysUnderlyingData, MultipartFile[] files);

    /**
     * 修改底层资料
     * 
     * @param sysUnderlyingData 底层资料
     * @return 结果
     */
    public int updateSysUnderlyingData(SysUnderlyingData sysUnderlyingData);

    /**
     * 批量删除底层资料
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysUnderlyingDataByIds(String ids);

    /**
     * 删除底层资料信息
     * 
     * @param fileId 底层资料ID
     * @return 结果
     */
    public int deleteSysUnderlyingDataById(Long fileId);


    public List<SysUnderlyingData> selectSysUnderlyingDataByPid(Long projectId,Long projectType);

    List<SysUnderlyingData> selectSysUnderlyingProDataList(SysUnderlyingData sysUnderlyingData);

    List<SysUnderlyingData> selectSysUnderlyingNoLikeDataList(SysUnderlyingData sysUnderlyingData);

    List<SysUnderlyingData> selectSysUnderlyingNoLikeDetailDataList(SysUnderlyingData sysUnderlyingData);

    List<SysUnderlyingData> selectSysUnderlyingProList(SysUnderlyingData sysUnderlyingData);

    List<SysUnderlyingData> selectSysUnderlyingDebtList(SysUnderlyingData sysUnderlyingData);
}
