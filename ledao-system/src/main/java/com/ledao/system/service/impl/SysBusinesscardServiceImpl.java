package com.ledao.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysBusinesscardMapper;
import com.ledao.system.dao.SysBusinesscard;
import com.ledao.system.service.ISysBusinesscardService;
import com.ledao.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author lxz
 * @date 2020-12-07
 */
@Service
public class SysBusinesscardServiceImpl implements ISysBusinesscardService {
    @Autowired
    private SysBusinesscardMapper sysBusinesscardMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public SysBusinesscard selectSysBusinesscardById(Long id) {
        return sysBusinesscardMapper.selectSysBusinesscardById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param sysBusinesscard 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysBusinesscard> selectSysBusinesscardList(SysBusinesscard sysBusinesscard) {
        return sysBusinesscardMapper.selectSysBusinesscardList(sysBusinesscard);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param sysBusinesscard 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysBusinesscard(SysBusinesscard sysBusinesscard) {
        return sysBusinesscardMapper.insertSysBusinesscard(sysBusinesscard);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param sysBusinesscard 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysBusinesscard(SysBusinesscard sysBusinesscard) {
        return sysBusinesscardMapper.updateSysBusinesscard(sysBusinesscard);
    }

    /**
     * 删除【请填写功能名称】对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysBusinesscardByIds(String ids) {
        return sysBusinesscardMapper.deleteSysBusinesscardByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSysBusinesscardById(Long id) {
        return sysBusinesscardMapper.deleteSysBusinesscardById(id);
    }
}
