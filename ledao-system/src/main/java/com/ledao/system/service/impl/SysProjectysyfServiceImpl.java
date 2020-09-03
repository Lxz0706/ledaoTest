package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysProjectysyfMapper;
import com.ledao.system.domain.SysProjectysyf;
import com.ledao.system.service.ISysProjectysyfService;
import com.ledao.common.core.text.Convert;

/**
 * 流转已收已付Service业务层处理
 *
 * @author ledao
 * @date 2020-08-31
 */
@Service
public class SysProjectysyfServiceImpl implements ISysProjectysyfService {
    @Autowired
    private SysProjectysyfMapper sysProjectysyfMapper;

    /**
     * 查询流转已收已付
     *
     * @param id 流转已收已付ID
     * @return 流转已收已付
     */
    @Override
    public SysProjectysyf selectSysProjectysyfById(Long id) {
        return sysProjectysyfMapper.selectSysProjectysyfById(id);
    }

    /**
     * 查询流转已收已付列表
     *
     * @param sysProjectysyf 流转已收已付
     * @return 流转已收已付
     */
    @Override
    public List<SysProjectysyf> selectSysProjectysyfList(SysProjectysyf sysProjectysyf) {
        return sysProjectysyfMapper.selectSysProjectysyfList(sysProjectysyf);
    }

    /**
     * 新增流转已收已付
     *
     * @param sysProjectysyf 流转已收已付
     * @return 结果
     */
    @Override
    public int insertSysProjectysyf(SysProjectysyf sysProjectysyf) {
        sysProjectysyf.setCreateTime(DateUtils.getNowDate());
        return sysProjectysyfMapper.insertSysProjectysyf(sysProjectysyf);
    }

    /**
     * 修改流转已收已付
     *
     * @param sysProjectysyf 流转已收已付
     * @return 结果
     */
    @Override
    public int updateSysProjectysyf(SysProjectysyf sysProjectysyf) {
        sysProjectysyf.setUpdateTime(DateUtils.getNowDate());
        return sysProjectysyfMapper.updateSysProjectysyf(sysProjectysyf);
    }

    /**
     * 删除流转已收已付对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectysyfByIds(String ids) {
        return sysProjectysyfMapper.deleteSysProjectysyfByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除流转已收已付信息
     *
     * @param id 流转已收已付ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectysyfById(Long id) {
        return sysProjectysyfMapper.deleteSysProjectysyfById(id);
    }
}
