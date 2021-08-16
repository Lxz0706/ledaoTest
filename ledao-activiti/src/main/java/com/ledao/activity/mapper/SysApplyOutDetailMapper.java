package com.ledao.activity.mapper;

import java.util.List;
import com.ledao.activity.dao.SysApplyOutDetail;

/**
 * 档案出库详情记录Mapper接口
 * 
 * @author lxz
 * @date 2021-08-10
 */
public interface SysApplyOutDetailMapper 
{
    /**
     * 查询档案出库详情记录
     * 
     * @param outDetailId 档案出库详情记录ID
     * @return 档案出库详情记录
     */
    public SysApplyOutDetail selectSysApplyOutDetailById(Long outDetailId);

    /**
     * 查询档案出库详情记录列表
     * 
     * @param sysApplyOutDetail 档案出库详情记录
     * @return 档案出库详情记录集合
     */
    public List<SysApplyOutDetail> selectSysApplyOutDetailList(SysApplyOutDetail sysApplyOutDetail);

    /**
     * 新增档案出库详情记录
     * 
     * @param sysApplyOutDetail 档案出库详情记录
     * @return 结果
     */
    public int insertSysApplyOutDetail(SysApplyOutDetail sysApplyOutDetail);

    /**
     * 修改档案出库详情记录
     * 
     * @param sysApplyOutDetail 档案出库详情记录
     * @return 结果
     */
    public int updateSysApplyOutDetail(SysApplyOutDetail sysApplyOutDetail);

    /**
     * 删除档案出库详情记录
     * 
     * @param outDetailId 档案出库详情记录ID
     * @return 结果
     */
    public int deleteSysApplyOutDetailById(Long outDetailId);

    /**
     * 批量删除档案出库详情记录
     * 
     * @param outDetailIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysApplyOutDetailByIds(String[] outDetailIds);

    /**
     * 批量删除档案出库详情记录
     *
     * @param outDetailIds 需要删除的数据ID
     * @return 结果
     */
    public int  deleteSysApplyOutDetailByApplyId(String applyId);

    /**
     * 查询档案出库详情记录列表
     *
     * @param sysApplyOutDetail 档案出库详情记录
     * @return 档案出库详情记录集合
     */
    List<SysApplyOutDetail> listDocumentAndDetail(SysApplyOutDetail sysApplyOutDetail);
}
