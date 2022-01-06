package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.annotation.DataSource;
import com.ledao.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysJudicialSuspectedMapper;
import com.ledao.system.dao.SysJudicialSuspected;
import com.ledao.system.service.ISysJudicialSuspectedService;
import com.ledao.common.core.text.Convert;

/**
 * 司法拍卖项目Service业务层处理
 *
 * @author lxz
 * @date 2021-01-14
 */
@Service
@DataSource(value = DataSourceType.SLAVE1)
public class SysJudicialSuspectedServiceImpl implements ISysJudicialSuspectedService {
    @Autowired
    private SysJudicialSuspectedMapper sysJudicialSuspectedMapper;

    /**
     * 查询司法拍卖项目
     *
     * @param id 司法拍卖项目ID
     * @return 司法拍卖项目
     */
    @Override
    public SysJudicialSuspected selectSysJudicialSuspectedById(Long id) {
        return sysJudicialSuspectedMapper.selectSysJudicialSuspectedById(id);
    }

    /**
     * 查询司法拍卖项目列表
     *
     * @param sysJudicialSuspected 司法拍卖项目
     * @return 司法拍卖项目
     */
    @Override
    public List<SysJudicialSuspected> selectSysJudicialSuspectedList(SysJudicialSuspected sysJudicialSuspected) {
        return sysJudicialSuspectedMapper.selectSysJudicialSuspectedList(sysJudicialSuspected);
    }

    /**
     * 新增司法拍卖项目
     *
     * @param sysJudicialSuspected 司法拍卖项目
     * @return 结果
     */
    @Override
    public int insertSysJudicialSuspected(SysJudicialSuspected sysJudicialSuspected) {
        return sysJudicialSuspectedMapper.insertSysJudicialSuspected(sysJudicialSuspected);
    }

    /**
     * 修改司法拍卖项目
     *
     * @param sysJudicialSuspected 司法拍卖项目
     * @return 结果
     */
    @Override
    public int updateSysJudicialSuspected(SysJudicialSuspected sysJudicialSuspected) {
        return sysJudicialSuspectedMapper.updateSysJudicialSuspected(sysJudicialSuspected);
    }

    /**
     * 删除司法拍卖项目对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysJudicialSuspectedByIds(String ids) {
        return sysJudicialSuspectedMapper.deleteSysJudicialSuspectedByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除司法拍卖项目信息
     *
     * @param id 司法拍卖项目ID
     * @return 结果
     */
    @Override
    public int deleteSysJudicialSuspectedById(Long id) {
        return sysJudicialSuspectedMapper.deleteSysJudicialSuspectedById(id);
    }

    /**
     * 根据itemID查询
     *
     * @param itemId
     * @return 结果
     */
    @Override
    public SysJudicialSuspected selectByItemId(String itemId) {
        return sysJudicialSuspectedMapper.selectByItemId(itemId);
    }
}
