package com.ledao.activity.mapper;

import java.util.List;
import com.ledao.activity.dao.SysFileDetail;

/**
 * 档案详情Mapper接口
 * 
 * @author lxz
 * @date 2021-08-04
 */
public interface SysFileDetailMapper 
{
    /**
     * 查询档案详情
     * 
     * @param fileId 档案详情ID
     * @return 档案详情
     */
    public SysFileDetail selectSysFileDetailById(Long fileId);

    /**
     * 查询档案详情列表
     * 
     * @param sysFileDetail 档案详情
     * @return 档案详情集合
     */
    public List<SysFileDetail> selectSysFileDetailList(SysFileDetail sysFileDetail);

    /**
     * 新增档案详情
     * 
     * @param sysFileDetail 档案详情
     * @return 结果
     */
    public int insertSysFileDetail(SysFileDetail sysFileDetail);

    /**
     * 修改档案详情
     * 
     * @param sysFileDetail 档案详情
     * @return 结果
     */
    public int updateSysFileDetail(SysFileDetail sysFileDetail);

    /**
     * 删除档案详情
     * 
     * @param fileId 档案详情ID
     * @return 结果
     */
    public int deleteSysFileDetailById(Long fileId);

    /**
     * 批量删除档案详情
     * 
     * @param fileIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysFileDetailByIds(String[] fileIds);
}
