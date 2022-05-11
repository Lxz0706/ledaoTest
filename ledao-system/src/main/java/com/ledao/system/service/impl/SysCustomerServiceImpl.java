package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.annotation.DataScope;
import com.ledao.common.constant.UserConstants;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.system.dao.SysUser;
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
    @DataScope(deptAlias = "t2", userAlias = "t2")
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

    /**
     * 校验手机号码是否唯一
     *
     * @param sysCustomer 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysCustomer sysCustomer) {
        //for (String string : sysCustomer.getContactNumber().split(",")) {
        Long customerId = StringUtils.isNull(sysCustomer.getCustomerId()) ? -1L : sysCustomer.getCustomerId();
        SysCustomer sysCustomer1 = new SysCustomer();
        sysCustomer1.setContactNumber(sysCustomer.getContactNumber());
        SysCustomer info = sysCustomerMapper.checkPhoneUnique(sysCustomer1);
        if (StringUtils.isNotNull(info) && info.getCustomerId().longValue() != customerId.longValue()) {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        // }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 校验微信号码是否唯一
     *
     * @param sysCustomer 用户信息
     * @return
     */
    @Override
    public String checkWeChatNumberUnique(SysCustomer sysCustomer) {

        // for (String string : sysCustomer.getWeChatNumber().split(",")) {
        Long customerId = StringUtils.isNull(sysCustomer.getCustomerId()) ? -1L : sysCustomer.getCustomerId();
        SysCustomer sysCustomer1 = new SysCustomer();
        sysCustomer1.setWeChatNumber(sysCustomer.getWeChatNumber());
        SysCustomer info = sysCustomerMapper.checkWeChatNumberUnique(sysCustomer1);
        if (StringUtils.isNotNull(info) && info.getCustomerId().longValue() != customerId.longValue()) {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        //  }

        return UserConstants.USER_PHONE_UNIQUE;
    }


    /**
     * 数据查询
     *
     * @param sysCustomer
     * @return 查询结果
     */
    @Override
    public List<SysCustomer> queryAll(SysCustomer sysCustomer) {
        return sysCustomerMapper.queryAll(sysCustomer);
    }

    /**
     * 根据传递来的数据进行分组查询
     */
    @Override
    public List<SysCustomer> selectSysCustomerByParam(SysCustomer sysCustomer, String param) {
        if ("city".equals(param)) {
            return sysCustomerMapper.selectSysCustomerByCity(sysCustomer);
        } else if ("customerLable".equals(param)) {
            return sysCustomerMapper.selectSysCustomerByCustomerLable(sysCustomer);
        } else if ("deptType".equals(param)) {
            return sysCustomerMapper.selectSysCustomerByDeptType(sysCustomer);
        } else {
            return sysCustomerMapper.selectSysCustomerByDeptId(sysCustomer);
        }
    }

    /**
     * 按照在职人员进行分组统计
     *
     * @param sysCustomer
     * @return
     */
    @Override
    public List<SysCustomer> selectSysCustomerForCreator(SysCustomer sysCustomer) {
        return sysCustomerMapper.selectSysCustomerForCreator(sysCustomer);
    }
}
