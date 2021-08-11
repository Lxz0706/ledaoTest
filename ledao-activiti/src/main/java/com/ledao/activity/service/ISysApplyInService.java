package com.ledao.activity.service;

import java.util.List;
import com.ledao.activity.dao.SysApplyIn;
import com.ledao.common.core.dao.AjaxResult;

/**
 * 档案入库申请Service接口
 * 
 * @author lxz
 * @date 2021-08-04
 */
public interface ISysApplyInService 
{
    /**
     * 查询档案入库申请
     * 
     * @param applyId 档案入库申请ID
     * @return 档案入库申请
     */
    public SysApplyIn selectSysApplyInById(Long applyId);

    /**
     * 查询档案入库申请列表
     * 
     * @param sysApplyIn 档案入库申请
     * @return 档案入库申请集合
     */
    public List<SysApplyIn> selectSysApplyInList(SysApplyIn sysApplyIn);

    /**
     * 新增档案入库申请
     * 
     * @param sysApplyIn 档案入库申请
     * @return 结果
     */
    public int insertSysApplyIn(SysApplyIn sysApplyIn);

    /**
     * 修改档案入库申请
     * 
     * @param sysApplyIn 档案入库申请
     * @return 结果
     */
    public int updateSysApplyIn(SysApplyIn sysApplyIn);

    /**
     * 批量删除档案入库申请
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysApplyInByIds(String ids);

    /**
     * 删除档案入库申请信息
     * 
     * @param applyId 档案入库申请ID
     * @return 结果
     */
    public int deleteSysApplyInById(Long applyId);

    /**
     * 查询自己的工作流
     * 
     * @param applyId 查询自己的工作流
     * @return 结果
     */
	public List<SysApplyIn> listByMe(SysApplyIn sysApplyIn);


    /**
     * 修改申请
     *
     * @param applyId 查询自己的工作流
     * @param sysApplyIn
     * @return 结果
     */
    int editSave(SysApplyIn sysApplyIn);

    /**
     * 修改申请
     *
     * @param applyId 申请状态修改
     * @param sysApplyIn
     * @return 结果
     */
    AjaxResult applyEditSave(SysApplyIn sysApplyIn);
}