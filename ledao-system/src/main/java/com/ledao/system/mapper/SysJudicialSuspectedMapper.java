package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysJudicialSuspected;

/**
 * 司法拍卖项目Mapper接口
 *
 * @author lxz
 * @date 2021-01-14
 */
public interface SysJudicialSuspectedMapper {
    /**
     * 查询司法拍卖项目
     *
     * @param id 司法拍卖项目ID
     * @return 司法拍卖项目
     */
    public SysJudicialSuspected selectSysJudicialSuspectedById(Long id);

    /**
     * 查询司法拍卖项目列表
     *
     * @param sysJudicialSuspected 司法拍卖项目
     * @return 司法拍卖项目集合
     */
    public List<SysJudicialSuspected> selectSysJudicialSuspectedList(SysJudicialSuspected sysJudicialSuspected);

    /**
     * 新增司法拍卖项目
     *
     * @param sysJudicialSuspected 司法拍卖项目
     * @return 结果
     */
    public int insertSysJudicialSuspected(SysJudicialSuspected sysJudicialSuspected);

    /**
     * 修改司法拍卖项目
     *
     * @param sysJudicialSuspected 司法拍卖项目
     * @return 结果
     */
    public int updateSysJudicialSuspected(SysJudicialSuspected sysJudicialSuspected);

    /**
     * 删除司法拍卖项目
     *
     * @param id 司法拍卖项目ID
     * @return 结果
     */
    public int deleteSysJudicialSuspectedById(Long id);

    /**
     * 批量删除司法拍卖项目
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysJudicialSuspectedByIds(String[] ids);

    /**
     * 根据itemID查询
     *
     * @param itemId
     * @return 结果
     */
    public SysJudicialSuspected selectByItemId(String itemId);
}
