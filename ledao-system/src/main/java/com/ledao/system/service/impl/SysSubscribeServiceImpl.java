package com.ledao.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysSubscribeMapper;
import com.ledao.system.dao.SysSubscribe;
import com.ledao.system.service.ISysSubscribeService;
import com.ledao.common.core.text.Convert;

/**
 * 订阅号用户信息Service业务层处理
 *
 * @author lxz
 * @date 2022-08-23
 */
@Service
public class SysSubscribeServiceImpl implements ISysSubscribeService {
    @Autowired
    private SysSubscribeMapper sysSubscribeMapper;

    /**
     * 查询订阅号用户信息
     *
     * @param id 订阅号用户信息ID
     * @return 订阅号用户信息
     */
    @Override
    public SysSubscribe selectSysSubscribeById(Integer id) {
        return sysSubscribeMapper.selectSysSubscribeById(id);
    }

    /**
     * 查询订阅号用户信息列表
     *
     * @param sysSubscribe 订阅号用户信息
     * @return 订阅号用户信息
     */
    @Override
    public List<SysSubscribe> selectSysSubscribeList(SysSubscribe sysSubscribe) {
        return sysSubscribeMapper.selectSysSubscribeList(sysSubscribe);
    }

    /**
     * 新增订阅号用户信息
     *
     * @param sysSubscribe 订阅号用户信息
     * @return 结果
     */
    @Override
    public int insertSysSubscribe(SysSubscribe sysSubscribe) {
        return sysSubscribeMapper.insertSysSubscribe(sysSubscribe);
    }

    /**
     * 修改订阅号用户信息
     *
     * @param sysSubscribe 订阅号用户信息
     * @return 结果
     */
    @Override
    public int updateSysSubscribe(SysSubscribe sysSubscribe) {
        return sysSubscribeMapper.updateSysSubscribe(sysSubscribe);
    }

    /**
     * 删除订阅号用户信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysSubscribeByIds(String ids) {
        return sysSubscribeMapper.deleteSysSubscribeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除订阅号用户信息信息
     *
     * @param id 订阅号用户信息ID
     * @return 结果
     */
    @Override
    public int deleteSysSubscribeById(Integer id) {
        return sysSubscribeMapper.deleteSysSubscribeById(id);
    }

    /**
     * 清空关注表
     *
     * @return
     */
    @Override
    public int deleteSysSubscribe() {
        return sysSubscribeMapper.deleteSysSubscribe();
    }

    /**
     * 根据openID获取用户信息
     *
     * @param openId
     * @return
     */
    @Override
    public SysSubscribe selectSysSubscribeByOpenId(String openId) {
        return sysSubscribeMapper.selectSysSubscribeByOpenId(openId);
    }
}
