package com.ledao.activity.mapper;

import java.util.List;
import com.ledao.activity.dao.SysApplyIn;
import org.apache.ibatis.annotations.Param;

/**
 * 档案入库申请Mapper接口
 * 
 * @author lxz
 * @date 2021-08-04
 */
public interface SysApplyInMapper 
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
     * 删除档案入库申请
     * 
     * @param applyId 档案入库申请ID
     * @return 结果
     */
    public int deleteSysApplyInById(Long applyId);

    /**
     * 批量删除档案入库申请
     * 
     * @param applyIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysApplyInByIds(String[] applyIds);

    /**
     * 我的已办
     * 
     * @param applyIds 我的已办
     * @return 结果
     */
	public List<SysApplyIn> listDownByMe(@Param("username") String username);

    /**
     * 我的待办
     *
     * @param applyIds 我的待办
     * @return 结果
     */
    List<SysApplyIn> listUnDownByMe(@Param("username") String username);

    /**
     * 我的申请
     *
     * @param applyIds 我的申请
     * @return 结果
     */
    List<SysApplyIn> applyListByMe(@Param("username") String username);

    /**
     * 根据applyId获取申请
     *
     * @param applyIds 我的申请
     * @return 结果
     */
    List<SysApplyIn> selectSysApplyInListByIds(String[] idsArr);
}
