package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysNamecardMapper;
import com.ledao.system.dao.SysNamecard;
import com.ledao.system.service.ISysNamecardService;
import com.ledao.common.core.text.Convert;

/**
 * 名片Service业务层处理
 *
 * @author lxz
 * @date 2020-12-07
 */
@Service
public class SysNamecardServiceImpl implements ISysNamecardService {
    @Autowired
    private SysNamecardMapper sysNamecardMapper;

    /**
     * 查询名片
     *
     * @param cardId 名片ID
     * @return 名片
     */
    @Override
    public SysNamecard selectSysNamecardById(Long cardId) {
        return sysNamecardMapper.selectSysNamecardById(cardId);
    }

    /**
     * 查询名片列表
     *
     * @param sysNamecard 名片
     * @return 名片
     */
    @Override
    public List<SysNamecard> selectSysNamecardList(SysNamecard sysNamecard) {
        return sysNamecardMapper.selectSysNamecardList(sysNamecard);
    }

    /**
     * 新增名片
     *
     * @param sysNamecard 名片
     * @return 结果
     */
    @Override
    public int insertSysNamecard(SysNamecard sysNamecard) {
        sysNamecard.setCreateTime(DateUtils.getNowDate());
        return sysNamecardMapper.insertSysNamecard(sysNamecard);
    }

    /**
     * 修改名片
     *
     * @param sysNamecard 名片
     * @return 结果
     */
    @Override
    public int updateSysNamecard(SysNamecard sysNamecard) {
        sysNamecard.setUpdateTime(DateUtils.getNowDate());
        return sysNamecardMapper.updateSysNamecard(sysNamecard);
    }

    /**
     * 删除名片对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysNamecardByIds(String ids) {
        return sysNamecardMapper.deleteSysNamecardByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除名片信息
     *
     * @param cardId 名片ID
     * @return 结果
     */
    @Override
    public int deleteSysNamecardById(Long cardId) {
        return sysNamecardMapper.deleteSysNamecardById(cardId);
    }

    /**
     * 根据cardId查询集合
     *
     * @param ids
     * @result 结果
     */
    @Override
    public List<SysNamecard> selectSysNamecardListByCardId(String ids) {
        return sysNamecardMapper.selectSysNamecardListByCardId(Convert.toStrArray(ids));
    }
}
