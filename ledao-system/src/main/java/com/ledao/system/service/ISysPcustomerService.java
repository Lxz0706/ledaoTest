package com.ledao.system.service;

import java.util.List;

import com.ledao.system.dao.SysPcustomer;

/**
 * 项目客户Service接口
 *
 * @author lxz
 * @date 2021-02-02
 */
public interface ISysPcustomerService {
    /**
     * 查询项目客户
     *
     * @param dealCustomerId 项目客户ID
     * @return 项目客户
     */
    public SysPcustomer selectSysPcustomerById(Long dealCustomerId);

    /**
     * 查询项目客户列表
     *
     * @param sysPcustomer 项目客户
     * @return 项目客户集合
     */
    public List<SysPcustomer> selectSysPcustomerList(SysPcustomer sysPcustomer);

    /**
     * 新增项目客户
     *
     * @param sysPcustomer 项目客户
     * @return 结果
     */
    public int insertSysPcustomer(SysPcustomer sysPcustomer);

    /**
     * 修改项目客户
     *
     * @param sysPcustomer 项目客户
     * @return 结果
     */
    public int updateSysPcustomer(SysPcustomer sysPcustomer);

    /**
     * 批量删除项目客户
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysPcustomerByIds(String ids);

    /**
     * 删除项目客户信息
     *
     * @param dealCustomerId 项目客户ID
     * @return 结果
     */
    public int deleteSysPcustomerById(Long dealCustomerId);

    /**
     * 根据项目id查询
     *
     * @param sysPcustomer
     * @return 数据结果
     */
    public List<SysPcustomer> selectPCustomerByProjectId(SysPcustomer sysPcustomer);
}
