package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysCustomerMapper;
import com.ledao.system.dao.SysCustomer;
import com.ledao.system.service.ISysCustomerService;
import com.ledao.common.core.text.Convert;

/**
 * 客户库Service业务层处理
 *
 * @author lxz
 * @date 2020-11-18
 */
@Service
public class SysCustomerServiceImpl implements ISysCustomerService {
    @Autowired
    private SysCustomerMapper sysCustomerMapper;

    /**
     * 查询客户库
     *
     * @param customerId 客户库ID
     * @return 客户库
     */
    @Override
    public SysCustomer selectSysCustomerById(Long customerId) {
        return sysCustomerMapper.selectSysCustomerById(customerId);
    }

    /**
     * 查询客户库列表
     *
     * @param sysCustomer 客户库
     * @return 客户库
     */
    @Override
    public List<SysCustomer> selectSysCustomerList(SysCustomer sysCustomer) {
        return sysCustomerMapper.selectSysCustomerList(sysCustomer);
    }

    /**
     * 新增客户库
     *
     * @param sysCustomer 客户库
     * @return 结果
     */
    @Override
    public int insertSysCustomer(SysCustomer sysCustomer) {
        sysCustomer.setCreateTime(DateUtils.getNowDate());
        return sysCustomerMapper.insertSysCustomer(sysCustomer);
    }

    /**
     * 修改客户库
     *
     * @param sysCustomer 客户库
     * @return 结果
     */
    @Override
    public int updateSysCustomer(SysCustomer sysCustomer) {
        sysCustomer.setUpdateTime(DateUtils.getNowDate());
        return sysCustomerMapper.updateSysCustomer(sysCustomer);
    }

    /**
     * 删除客户库对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerByIds(String ids) {
        return sysCustomerMapper.deleteSysCustomerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除客户库信息
     *
     * @param customerId 客户库ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerById(Long customerId) {
        return sysCustomerMapper.deleteSysCustomerById(customerId);
    }
}
