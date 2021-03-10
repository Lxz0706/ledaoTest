package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysMemorandumMapper;
import com.ledao.system.dao.SysMemorandum;
import com.ledao.system.service.ISysMemorandumService;
import com.ledao.common.core.text.Convert;

/**
 * 备忘录Service业务层处理
 *
 * @author lxz
 * @date 2021-03-04
 */
@Service
public class SysMemorandumServiceImpl implements ISysMemorandumService {
    @Autowired
    private SysMemorandumMapper sysMemorandumMapper;

    /**
     * 查询备忘录
     *
     * @param memorandumId 备忘录ID
     * @return 备忘录
     */
    @Override
    public SysMemorandum selectSysMemorandumById(Long memorandumId) {
        return sysMemorandumMapper.selectSysMemorandumById(memorandumId);
    }

    /**
     * 查询备忘录列表
     *
     * @param sysMemorandum 备忘录
     * @return 备忘录
     */
    @Override
    public List<SysMemorandum> selectSysMemorandumList(SysMemorandum sysMemorandum) {
        return sysMemorandumMapper.selectSysMemorandumList(sysMemorandum);
    }

    /**
     * 新增备忘录
     *
     * @param sysMemorandum 备忘录
     * @return 结果
     */
    @Override
    public int insertSysMemorandum(SysMemorandum sysMemorandum) {
        sysMemorandum.setCreateTime(DateUtils.getNowDate());
        return sysMemorandumMapper.insertSysMemorandum(sysMemorandum);
    }

    /**
     * 修改备忘录
     *
     * @param sysMemorandum 备忘录
     * @return 结果
     */
    @Override
    public int updateSysMemorandum(SysMemorandum sysMemorandum) {
        sysMemorandum.setUpdateTime(DateUtils.getNowDate());
        return sysMemorandumMapper.updateSysMemorandum(sysMemorandum);
    }

    /**
     * 删除备忘录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysMemorandumByIds(String ids) {
        return sysMemorandumMapper.deleteSysMemorandumByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除备忘录信息
     *
     * @param memorandumId 备忘录ID
     * @return 结果
     */
    @Override
    public int deleteSysMemorandumById(Long memorandumId) {
        return sysMemorandumMapper.deleteSysMemorandumById(memorandumId);
    }
}
