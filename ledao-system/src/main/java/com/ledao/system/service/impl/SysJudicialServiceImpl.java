package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.system.domain.SysJudicial;
import com.ledao.system.mapper.SysJudicialMapper;
import com.ledao.system.service.ISysJudicialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.common.core.text.Convert;

/**
 * 司法Service业务层处理
 *
 * @author lxz
 * @date 2020-06-09
 */
@Service
public class SysJudicialServiceImpl implements ISysJudicialService {
    @Autowired
    private SysJudicialMapper sysJudicialMapper;

    /**
     * 查询司法
     *
     * @param id 司法ID
     * @return 司法
     */
    @Override
    public SysJudicial selectSysJudicialById(Long id) {
        return sysJudicialMapper.selectSysJudicialById(id);
    }

    /**
     * 查询司法列表
     *
     * @param sysJudicial 司法
     * @return 司法
     */
    @Override
    public List<SysJudicial> selectSysJudicialList(SysJudicial sysJudicial) {
        return sysJudicialMapper.selectSysJudicialList(sysJudicial);
    }

    /**
     * 新增司法
     *
     * @param sysJudicial 司法
     * @return 结果
     */
    @Override
    public int insertSysJudicial(SysJudicial sysJudicial) {
        return sysJudicialMapper.insertSysJudicial(sysJudicial);
    }

    /**
     * 修改司法
     *
     * @param sysJudicial 司法
     * @return 结果
     */
    @Override
    public int updateSysJudicial(SysJudicial sysJudicial) {
        return sysJudicialMapper.updateSysJudicial(sysJudicial);
    }

    /**
     * 删除司法对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysJudicialByIds(String ids) {
        return sysJudicialMapper.deleteSysJudicialByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除司法信息
     *
     * @param id 司法ID
     * @return 结果
     */
    @Override
    public int deleteSysJudicialById(Long id) {
        return sysJudicialMapper.deleteSysJudicialById(id);
    }
}
