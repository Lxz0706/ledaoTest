package com.ledao.system.dao;

import javax.validation.constraints.*;

import com.ledao.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ledao.common.core.dao.BaseEntity;

/**
 * 通知公告表 sys_notice
 *
 * @author lxz
 */
public class SysNotice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 公告ID
     */
    private Long noticeId;

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告类型（1通知公告 2消息）
     */
    private String noticeType;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 公告状态（0正常 1关闭）
     */
    private String status;

    /**
     * 接收人ID
     **/
    private String receiverId;


    /**
     * 接收人
     */
    private String receiver;

    /**
     * 是否已读
     */
    private String readFlag;

    /**
     * 共享部门id
     */
    @Excel(name = "共享部门id")
    private String shareDeptId;

    /**
     * 共享部门名称
     */
    @Excel(name = "共享部门名称")
    private String shareDeptName;

    private String shareDeptAndUser;

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    @NotBlank(message = "公告标题不能为空")
    @Size(min = 0, max = 50, message = "公告标题不能超过50个字符")
    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }

    public String getShareDeptId() {
        return shareDeptId;
    }

    public void setShareDeptId(String shareDeptId) {
        this.shareDeptId = shareDeptId;
    }

    public String getShareDeptName() {
        return shareDeptName;
    }

    public void setShareDeptName(String shareDeptName) {
        this.shareDeptName = shareDeptName;
    }

    public String getShareDeptAndUser() {
        return shareDeptAndUser;
    }

    public void setShareDeptAndUser(String shareDeptAndUser) {
        this.shareDeptAndUser = shareDeptAndUser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("noticeId", getNoticeId())
                .append("noticeTitle", getNoticeTitle())
                .append("noticeType", getNoticeType())
                .append("noticeContent", getNoticeContent())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("receiver", getReceiver())
                .append("readFlag", getReadFlag())
                .append("shareDeptId", getShareDeptId())
                .append("shareDeptName", getShareDeptName())
                .toString();
    }
}
