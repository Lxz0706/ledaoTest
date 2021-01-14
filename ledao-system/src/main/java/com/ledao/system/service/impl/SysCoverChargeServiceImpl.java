package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysCoverChargeMapper;
import com.ledao.system.dao.SysCoverCharge;
import com.ledao.system.service.ISysCoverChargeService;
import com.ledao.common.core.text.Convert;

/**
 * 流转服务费Service业务层处理
 *
 * @author lxz
 * @date 2020-12-18
 */
@Service
public class SysCoverChargeServiceImpl implements ISysCoverChargeService {
    @Autowired
    private SysCoverChargeMapper sysCoverChargeMapper;

    /**
     * 查询流转服务费
     *
     * @param id 流转服务费ID
     * @return 流转服务费
     */
    @Override
    public SysCoverCharge selectSysCoverChargeById(Long id) {
        return sysCoverChargeMapper.selectSysCoverChargeById(id);
    }

    /**
     * 查询流转服务费列表
     *
     * @param sysCoverCharge 流转服务费
     * @return 流转服务费
     */
    @Override
    public List<SysCoverCharge> selectSysCoverChargeList(SysCoverCharge sysCoverCharge) {
        return sysCoverChargeMapper.selectSysCoverChargeList(sysCoverCharge);
    }

    /**
     * 新增流转服务费
     *
     * @param sysCoverCharge 流转服务费
     * @return 结果
     */
    @Override
    public int insertSysCoverCharge(SysCoverCharge sysCoverCharge) {
        sysCoverCharge.setCreateTime(DateUtils.getNowDate());
        return sysCoverChargeMapper.insertSysCoverCharge(sysCoverCharge);
    }

    /**
     * 修改流转服务费
     *
     * @param sysCoverCharge 流转服务费
     * @return 结果
     */
    @Override
    public int updateSysCoverCharge(SysCoverCharge sysCoverCharge) {
        sysCoverCharge.setUpdateTime(DateUtils.getNowDate());
        return sysCoverChargeMapper.updateSysCoverCharge(sysCoverCharge);
    }

    /**
     * 删除流转服务费对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysCoverChargeByIds(String ids) {
        return sysCoverChargeMapper.deleteSysCoverChargeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除流转服务费信息
     *
     * @param id 流转服务费ID
     * @return 结果
     */
    @Override
    public int deleteSysCoverChargeById(Long id) {
        return sysCoverChargeMapper.deleteSysCoverChargeById(id);
    }
}
