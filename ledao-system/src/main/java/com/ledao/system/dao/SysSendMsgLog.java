package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 微信消息推送记录对象 sys_send_msg_log
 *
 * @author lxz
 * @date 2021-11-17
 */
public class SysSendMsgLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 任务日志ID
     */
    private Long id;

    /**
     * 任务名称
     */
    @Excel(name = "任务名称")
    private String toUser;

    /**
     * 发送信息
     */
    @Excel(name = "发送信息")
    private String sendMsg;

    /**
     * 调用目标字符串
     */
    @Excel(name = "调用目标字符串")
    private String resMsg;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setSendMsg(String sendMsg) {
        this.sendMsg = sendMsg;
    }

    public String getSendMsg() {
        return sendMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getResMsg() {
        return resMsg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("toUser", getToUser())
                .append("sendMsg", getSendMsg())
                .append("resMsg", getResMsg())
                .append("createTime", getCreateTime())
                .toString();
    }
}
