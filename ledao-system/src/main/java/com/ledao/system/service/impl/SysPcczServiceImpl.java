package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.annotation.DataSource;
import com.ledao.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysPcczMapper;
import com.ledao.system.dao.SysPccz;
import com.ledao.system.service.ISysPcczService;
import com.ledao.common.core.text.Convert;

/**
 * 破产网Service业务层处理
 *
 * @author lxz
 * @date 2022-10-18
 */
@Service
@DataSource(DataSourceType.SLAVE)
public class SysPcczServiceImpl implements ISysPcczService {
    @Autowired
    private SysPcczMapper sysPcczMapper;

    /**
     * 查询破产网
     *
     * @param id 破产网ID
     * @return 破产网
     */
    @Override
    public SysPccz selectSysPcczById(Long id) {
        return sysPcczMapper.selectSysPcczById(id);
    }

    /**
     * 查询破产网列表
     *
     * @param sysPccz 破产网
     * @return 破产网
     */
    @Override
    public List<SysPccz> selectSysPcczList(SysPccz sysPccz) {
        return sysPcczMapper.selectSysPcczList(sysPccz);
    }

    /**
     * 新增破产网
     *
     * @param sysPccz 破产网
     * @return 结果
     */
    @Override
    public int insertSysPccz(SysPccz sysPccz) {
        return sysPcczMapper.insertSysPccz(sysPccz);
    }

    /**
     * 修改破产网
     *
     * @param sysPccz 破产网
     * @return 结果
     */
    @Override
    public int updateSysPccz(SysPccz sysPccz) {
        return sysPcczMapper.updateSysPccz(sysPccz);
    }

    /**
     * 删除破产网对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysPcczByIds(String ids) {
        return sysPcczMapper.deleteSysPcczByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除破产网信息
     *
     * @param id 破产网ID
     * @return 结果
     */
    @Override
    public int deleteSysPcczById(Long id) {
        return sysPcczMapper.deleteSysPcczById(id);
    }
}
