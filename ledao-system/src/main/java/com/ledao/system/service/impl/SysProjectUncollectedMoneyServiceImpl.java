package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysProjectUncollectedMoneyMapper;
import com.ledao.system.dao.SysProjectUncollectedMoney;
import com.ledao.system.service.ISysProjectUncollectedMoneyService;
import com.ledao.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ledao
 * @date 2020-08-31
 */
@Service
public class SysProjectUncollectedMoneyServiceImpl implements ISysProjectUncollectedMoneyService {
    @Autowired
    private SysProjectUncollectedMoneyMapper sysProjectUncollectedMoneyMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public SysProjectUncollectedMoney selectSysProjectUncollectedMoneyById(Long id) {
        return sysProjectUncollectedMoneyMapper.selectSysProjectUncollectedMoneyById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param sysProjectUncollectedMoney 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysProjectUncollectedMoney> selectSysProjectUncollectedMoneyList(SysProjectUncollectedMoney sysProjectUncollectedMoney) {
        return sysProjectUncollectedMoneyMapper.selectSysProjectUncollectedMoneyList(sysProjectUncollectedMoney);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param sysProjectUncollectedMoney 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysProjectUncollectedMoney(SysProjectUncollectedMoney sysProjectUncollectedMoney) {
        sysProjectUncollectedMoney.setCreateTime(DateUtils.getNowDate());
        return sysProjectUncollectedMoneyMapper.insertSysProjectUncollectedMoney(sysProjectUncollectedMoney);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param sysProjectUncollectedMoney 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysProjectUncollectedMoney(SysProjectUncollectedMoney sysProjectUncollectedMoney) {
        sysProjectUncollectedMoney.setUpdateTime(DateUtils.getNowDate());
        return sysProjectUncollectedMoneyMapper.updateSysProjectUncollectedMoney(sysProjectUncollectedMoney);
    }

    /**
     * 删除【请填写功能名称】对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectUncollectedMoneyByIds(String ids) {
        return sysProjectUncollectedMoneyMapper.deleteSysProjectUncollectedMoneyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectUncollectedMoneyById(Long id) {
        return sysProjectUncollectedMoneyMapper.deleteSysProjectUncollectedMoneyById(id);
    }
}
