package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysSubscribe;

/**
 * 订阅号用户信息Mapper接口
 *
 * @author lxz
 * @date 2022-08-23
 */
public interface SysSubscribeMapper {
    /**
     * 查询订阅号用户信息
     *
     * @param id 订阅号用户信息ID
     * @return 订阅号用户信息
     */
    public SysSubscribe selectSysSubscribeById(Integer id);

    /**
     * 查询订阅号用户信息列表
     *
     * @param sysSubscribe 订阅号用户信息
     * @return 订阅号用户信息集合
     */
    public List<SysSubscribe> selectSysSubscribeList(SysSubscribe sysSubscribe);

    /**
     * 新增订阅号用户信息
     *
     * @param sysSubscribe 订阅号用户信息
     * @return 结果
     */
    public int insertSysSubscribe(SysSubscribe sysSubscribe);

    /**
     * 修改订阅号用户信息
     *
     * @param sysSubscribe 订阅号用户信息
     * @return 结果
     */
    public int updateSysSubscribe(SysSubscribe sysSubscribe);

    /**
     * 删除订阅号用户信息
     *
     * @param id 订阅号用户信息ID
     * @return 结果
     */
    public int deleteSysSubscribeById(Integer id);

    /**
     * 批量删除订阅号用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysSubscribeByIds(String[] ids);

    /**
     * 清空关注表
     *
     * @return
     */
    public int deleteSysSubscribe();

    /**
     * 根据openID获取用户信息
     *
     * @param openId
     * @return
     */
    public SysSubscribe selectSysSubscribeByOpenId(String openId);
}
