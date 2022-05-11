package com.ledao.system.mapper;

import com.ledao.system.dao.SysSendMsgLog;

import java.util.List;

/**
 * 微信消息推送记录Mapper接口
 * 
 * @author lxz
 * @date 2021-11-17
 */
public interface SysSendMsgLogMapper 
{
    /**
     * 查询微信消息推送记录
     * 
     * @param id 微信消息推送记录ID
     * @return 微信消息推送记录
     */
    public SysSendMsgLog selectSysSendMsgLogById(Long id);

    /**
     * 查询微信消息推送记录列表
     * 
     * @param sysSendMsgLog 微信消息推送记录
     * @return 微信消息推送记录集合
     */
    public List<SysSendMsgLog> selectSysSendMsgLogList(SysSendMsgLog sysSendMsgLog);

    /**
     * 新增微信消息推送记录
     * 
     * @param sysSendMsgLog 微信消息推送记录
     * @return 结果
     */
    public int insertSysSendMsgLog(SysSendMsgLog sysSendMsgLog);

    /**
     * 修改微信消息推送记录
     * 
     * @param sysSendMsgLog 微信消息推送记录
     * @return 结果
     */
    public int updateSysSendMsgLog(SysSendMsgLog sysSendMsgLog);

    /**
     * 删除微信消息推送记录
     * 
     * @param id 微信消息推送记录ID
     * @return 结果
     */
    public int deleteSysSendMsgLogById(Long id);

    /**
     * 批量删除微信消息推送记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysSendMsgLogByIds(String[] ids);
}
