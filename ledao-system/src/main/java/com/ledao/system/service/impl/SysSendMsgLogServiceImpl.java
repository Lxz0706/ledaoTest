package com.ledao.system.service.impl;

import com.ledao.common.core.text.Convert;
import com.ledao.common.utils.DateUtils;
import com.ledao.system.dao.SysSendMsgLog;
import com.ledao.system.dao.SysUser;
import com.ledao.system.mapper.SysSendMsgLogMapper;
import com.ledao.system.mapper.SysUserMapper;
import com.ledao.system.service.ISysSendMsgLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微信消息推送记录Service业务层处理
 *
 * @author lxz
 * @date 2021-11-17
 */
@Service
public class SysSendMsgLogServiceImpl implements ISysSendMsgLogService {
    @Autowired
    private SysSendMsgLogMapper sysSendMsgLogMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询微信消息推送记录
     *
     * @param id 微信消息推送记录ID
     * @return 微信消息推送记录
     */
    @Override
    public SysSendMsgLog selectSysSendMsgLogById(Long id) {
        return sysSendMsgLogMapper.selectSysSendMsgLogById(id);
    }

    /**
     * 查询微信消息推送记录列表
     *
     * @param sysSendMsgLog 微信消息推送记录
     * @return 微信消息推送记录
     */
    @Override
    public List<SysSendMsgLog> selectSysSendMsgLogList(SysSendMsgLog sysSendMsgLog) {
        return sysSendMsgLogMapper.selectSysSendMsgLogList(sysSendMsgLog);
    }

    /**
     * 新增微信消息推送记录
     *
     * @param sysSendMsgLog 微信消息推送记录
     * @return 结果
     */
    @Override
    public int insertSysSendMsgLog(SysSendMsgLog sysSendMsgLog) {
        SysUser u = new SysUser();
        u.setComOpenId(sysSendMsgLog.getToUser());
        List<SysUser> users = sysUserMapper.selectUserList(u);
        if (users != null && users.size() > 0) {
            sysSendMsgLog.setToUser(users.get(0).getLoginName());
        }
        sysSendMsgLog.setCreateTime(DateUtils.getNowDate());
        return sysSendMsgLogMapper.insertSysSendMsgLog(sysSendMsgLog);
    }

    /**
     * 修改微信消息推送记录
     *
     * @param sysSendMsgLog 微信消息推送记录
     * @return 结果
     */
    @Override
    public int updateSysSendMsgLog(SysSendMsgLog sysSendMsgLog) {
        return sysSendMsgLogMapper.updateSysSendMsgLog(sysSendMsgLog);
    }

    /**
     * 删除微信消息推送记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysSendMsgLogByIds(String ids) {
        return sysSendMsgLogMapper.deleteSysSendMsgLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除微信消息推送记录信息
     *
     * @param id 微信消息推送记录ID
     * @return 结果
     */
    @Override
    public int deleteSysSendMsgLogById(Long id) {
        return sysSendMsgLogMapper.deleteSysSendMsgLogById(id);
    }
}
