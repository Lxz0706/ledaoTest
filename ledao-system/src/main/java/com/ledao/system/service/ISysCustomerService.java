package com.ledao.system.service;

import java.util.List;

import com.ledao.system.dao.SysCustomer;

/**
 * 客户库Service接口
 *
 * @author lxz
 * @date 2020-11-18
 */
public interface ISysCustomerService {
    /**
     * 查询客户库
     *
     * @param customerId 客户库ID
     * @return 客户库
     */
    public SysCustomer selectSysCustomerById(Long customerId);

    /**
     * 查询客户库列表
     *
     * @param sysCustomer 客户库
     * @return 客户库集合
     */
    public List<SysCustomer> selectSysCustomerList(SysCustomer sysCustomer);

    /**
     * 新增客户库
     *
     * @param sysCustomer 客户库
     * @return 结果
     */
    public int insertSysCustomer(SysCustomer sysCustomer);

    /**
     * 修改客户库
     *
     * @param sysCustomer 客户库
     * @return 结果
     */
    public int updateSysCustomer(SysCustomer sysCustomer);

    /**
     * 批量删除客户库
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysCustomerByIds(String ids);

    /**
     * 删除客户库信息
     *
     * @param customerId 客户库ID
     * @return 结果
     */
    public int deleteSysCustomerById(Long customerId);

    /**
     * 校验手机号码是否唯一
     *
     * @param sysCustomer 用户信息
     * @return 结果
     */
    public String checkPhoneUnique(SysCustomer sysCustomer);

    /**
     * 校验手机号码是否唯一
     *
     * @param sysCustomer 用户信息
     * @return 结果
     */
    public String checkWeChatNumberUnique(SysCustomer sysCustomer);

    /**
     * 数据查询
     *
     * @param sysCustomer
     * @return 查询结果
     */
    public List<SysCustomer> queryAll(SysCustomer sysCustomer);
}
