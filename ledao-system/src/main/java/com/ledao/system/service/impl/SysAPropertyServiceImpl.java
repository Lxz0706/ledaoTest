package com.ledao.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysAPropertyMapper;
import com.ledao.system.dao.SysAProperty;
import com.ledao.system.service.ISysAPropertyService;
import com.ledao.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author lxz
 * @date 2020-10-20
 */
@Service
public class SysAPropertyServiceImpl implements ISysAPropertyService {
    @Autowired
    private SysAPropertyMapper sysAPropertyMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public SysAProperty selectSysAPropertyById(Long id) {
        return sysAPropertyMapper.selectSysAPropertyById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param sysAProperty 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysAProperty> selectSysAPropertyList(SysAProperty sysAProperty) {
        return sysAPropertyMapper.selectSysAPropertyList(sysAProperty);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param sysAProperty 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysAProperty(SysAProperty sysAProperty) {
        return sysAPropertyMapper.insertSysAProperty(sysAProperty);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param sysAProperty 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysAProperty(SysAProperty sysAProperty) {
        return sysAPropertyMapper.updateSysAProperty(sysAProperty);
    }

    /**
     * 删除【请填写功能名称】对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysAPropertyByIds(String ids) {
        return sysAPropertyMapper.deleteSysAPropertyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSysAPropertyById(Long id) {
        return sysAPropertyMapper.deleteSysAPropertyById(id);
    }
}
