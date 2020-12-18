package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysNamecard;

/**
 * 名片Mapper接口
 *
 * @author lxz
 * @date 2020-12-07
 */
public interface SysNamecardMapper {
    /**
     * 查询名片
     *
     * @param cardId 名片ID
     * @return 名片
     */
    public SysNamecard selectSysNamecardById(Long cardId);

    /**
     * 查询名片列表
     *
     * @param sysNamecard 名片
     * @return 名片集合
     */
    public List<SysNamecard> selectSysNamecardList(SysNamecard sysNamecard);

    /**
     * 新增名片
     *
     * @param sysNamecard 名片
     * @return 结果
     */
    public int insertSysNamecard(SysNamecard sysNamecard);

    /**
     * 修改名片
     *
     * @param sysNamecard 名片
     * @return 结果
     */
    public int updateSysNamecard(SysNamecard sysNamecard);

    /**
     * 删除名片
     *
     * @param cardId 名片ID
     * @return 结果
     */
    public int deleteSysNamecardById(Long cardId);

    /**
     * 批量删除名片
     *
     * @param cardIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysNamecardByIds(String[] cardIds);

    /**
     * 根据cardId查询集合
     *
     * @param ids
     * @result 结果
     */
    public List<SysNamecard> selectSysNamecardListByCardId(String[] ids);
}
