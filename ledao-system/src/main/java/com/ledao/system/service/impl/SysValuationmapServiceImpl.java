package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.annotation.DataSource;
import com.ledao.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysValuationmapMapper;
import com.ledao.system.dao.SysValuationmap;
import com.ledao.system.service.ISysValuationmapService;
import com.ledao.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author lxz
 * @date 2021-02-22
 */
@Service
public class SysValuationmapServiceImpl implements ISysValuationmapService {
    @Autowired
    private SysValuationmapMapper sysValuationmapMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public SysValuationmap selectSysValuationmapById(Long id) {
        return sysValuationmapMapper.selectSysValuationmapById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param sysValuationmap 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysValuationmap> selectSysValuationmapList(SysValuationmap sysValuationmap) {
        return sysValuationmapMapper.selectSysValuationmapList(sysValuationmap);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param sysValuationmap 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysValuationmap(SysValuationmap sysValuationmap) {
        return sysValuationmapMapper.insertSysValuationmap(sysValuationmap);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param sysValuationmap 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysValuationmap(SysValuationmap sysValuationmap) {
        return sysValuationmapMapper.updateSysValuationmap(sysValuationmap);
    }

    /**
     * 删除【请填写功能名称】对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysValuationmapByIds(String ids) {
        return sysValuationmapMapper.deleteSysValuationmapByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSysValuationmapById(Long id) {
        return sysValuationmapMapper.deleteSysValuationmapById(id);
    }
}
