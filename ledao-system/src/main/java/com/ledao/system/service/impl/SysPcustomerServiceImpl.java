package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysPcustomerMapper;
import com.ledao.system.dao.SysPcustomer;
import com.ledao.system.service.ISysPcustomerService;
import com.ledao.common.core.text.Convert;

/**
 * 项目客户Service业务层处理
 *
 * @author lxz
 * @date 2021-02-02
 */
@Service
public class SysPcustomerServiceImpl implements ISysPcustomerService {
    @Autowired
    private SysPcustomerMapper sysPcustomerMapper;

    /**
     * 查询项目客户
     *
     * @param dealCustomerId 项目客户ID
     * @return 项目客户
     */
    @Override
    public SysPcustomer selectSysPcustomerById(Long dealCustomerId) {
        return sysPcustomerMapper.selectSysPcustomerById(dealCustomerId);
    }

    /**
     * 查询项目客户列表
     *
     * @param sysPcustomer 项目客户
     * @return 项目客户
     */
    @Override
    public List<SysPcustomer> selectSysPcustomerList(SysPcustomer sysPcustomer) {
        return sysPcustomerMapper.selectSysPcustomerList(sysPcustomer);
    }

    /**
     * 新增项目客户
     *
     * @param sysPcustomer 项目客户
     * @return 结果
     */
    @Override
    public int insertSysPcustomer(SysPcustomer sysPcustomer) {
        sysPcustomer.setCreateTime(DateUtils.getNowDate());
        return sysPcustomerMapper.insertSysPcustomer(sysPcustomer);
    }

    /**
     * 修改项目客户
     *
     * @param sysPcustomer 项目客户
     * @return 结果
     */
    @Override
    public int updateSysPcustomer(SysPcustomer sysPcustomer) {
        sysPcustomer.setUpdateTime(DateUtils.getNowDate());
        return sysPcustomerMapper.updateSysPcustomer(sysPcustomer);
    }

    /**
     * 删除项目客户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysPcustomerByIds(String ids) {
        return sysPcustomerMapper.deleteSysPcustomerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除项目客户信息
     *
     * @param dealCustomerId 项目客户ID
     * @return 结果
     */
    @Override
    public int deleteSysPcustomerById(Long dealCustomerId) {
        return sysPcustomerMapper.deleteSysPcustomerById(dealCustomerId);
    }

    /**
     * 根据项目id查询
     *
     * @param sysPcustomer
     * @return 数据结果
     */
    @Override
    public List<SysPcustomer> selectPCustomerByProjectId(SysPcustomer sysPcustomer) {
        return sysPcustomerMapper.selectPCustomerByProjectId(sysPcustomer);
    }
}
