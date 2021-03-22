package com.ledao.system.dao;

import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 工作日志对象 sys_work_log
 *
 * @author lxz
 * @date 2021-03-18
 */
public class SysWorkLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long workLogId;

    /**
     * 日志标题
     */
    private String workLogTitle;

    /**
     * 日志类型
     */
    @Excel(name = "日志类型")
    private String workLogType;

    /**
     * 今日完成工作
     */
    @Excel(name = "今日完成工作")
    private String finishedWorkToday;

    /**
     * 今日未完成工作
     */
    @Excel(name = "今日未完成工作")
    private String unfinishedWorkToday;

    /**
     * 需协调工作
     */
    @Excel(name = "需协调工作")
    private String coordinationWork;

    /**
     * 明日计划
     */
    @Excel(name = "明日计划")
    private String tomorrowPlanWork;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remarks;

    /**
     * 附件
     */
    @Excel(name = "附件")
    private String enclosure;

    /**
     * 附件地址
     */
    @Excel(name = "附件地址")
    private String enclosureUrl;

    /**
     * 图片
     */
    @Excel(name = "图片")
    private String picture;

    /**
     * 图片地址
     */
    @Excel(name = "图片地址")
    private String pictureUrl;

    /**
     * 分享人id
     */
    @Excel(name = "分享人id")
    private String shareUserId;

    /**
     * 发送到人
     */
    @Excel(name = "发送到人")
    private String shareUserName;

    /**
     * 分享群id
     */
    @Excel(name = "分享群id")
    private String shareGroupId;

    /**
     * 发送到群
     */
    @Excel(name = "发送到群")
    private String shareGroupName;

    /**
     * 仅接收人可见，不可转发
     */
    @Excel(name = "仅接收人可见，不可转发")
    private String forwardFlag;

    /**
     * 开始时间
     */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     */
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 创建人名称
     */
    @Excel(name = "创建人名称")
    private String creator;

    /**
     * 删除标志(0正常，2删除)
     */
    private String delFlag;

    /**
     * 所属部门id
     */
    @Excel(name = "所属部门id")
    private Long deptId;

    /**
     * 所属部门
     */
    @Excel(name = "所属部门")
    private String deptName;

    public void setWorkLogId(Long workLogId) {
        this.workLogId = workLogId;
    }

    public Long getWorkLogId() {
        return workLogId;
    }

    public String getWorkLogTitle() {
        return workLogTitle;
    }

    public void setWorkLogTitle(String workLogTitle) {
        this.workLogTitle = workLogTitle;
    }

    public void setWorkLogType(String workLogType) {
        this.workLogType = workLogType;
    }

    public String getWorkLogType() {
        return workLogType;
    }

    public void setFinishedWorkToday(String finishedWorkToday) {
        this.finishedWorkToday = finishedWorkToday;
    }

    public String getFinishedWorkToday() {
        return finishedWorkToday;
    }

    public void setUnfinishedWorkToday(String unfinishedWorkToday) {
        this.unfinishedWorkToday = unfinishedWorkToday;
    }

    public String getUnfinishedWorkToday() {
        return unfinishedWorkToday;
    }

    public void setCoordinationWork(String coordinationWork) {
        this.coordinationWork = coordinationWork;
    }

    public String getCoordinationWork() {
        return coordinationWork;
    }

    public void setTomorrowPlanWork(String tomorrowPlanWork) {
        this.tomorrowPlanWork = tomorrowPlanWork;
    }

    public String getTomorrowPlanWork() {
        return tomorrowPlanWork;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosureUrl(String enclosureUrl) {
        this.enclosureUrl = enclosureUrl;
    }

    public String getEnclosureUrl() {
        return enclosureUrl;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setShareUserId(String shareUserId) {
        this.shareUserId = shareUserId;
    }

    public String getShareUserId() {
        return shareUserId;
    }

    public void setShareUserName(String shareUserName) {
        this.shareUserName = shareUserName;
    }

    public String getShareUserName() {
        return shareUserName;
    }

    public void setShareGroupId(String shareGroupId) {
        this.shareGroupId = shareGroupId;
    }

    public String getShareGroupId() {
        return shareGroupId;
    }

    public void setShareGroupName(String shareGroupName) {
        this.shareGroupName = shareGroupName;
    }

    public String getShareGroupName() {
        return shareGroupName;
    }

    public void setForwardFlag(String forwardFlag) {
        this.forwardFlag = forwardFlag;
    }

    public String getForwardFlag() {
        return forwardFlag;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("workLogId", getWorkLogId())
                .append("workLogTitle", getWorkLogTitle())
                .append("workLogType", getWorkLogType())
                .append("finishedWorkToday", getFinishedWorkToday())
                .append("unfinishedWorkToday", getUnfinishedWorkToday())
                .append("coordinationWork", getCoordinationWork())
                .append("tomorrowPlanWork", getTomorrowPlanWork())
                .append("remarks", getRemarks())
                .append("enclosure", getEnclosure())
                .append("enclosureUrl", getEnclosureUrl())
                .append("picture", getPicture())
                .append("pictureUrl", getPictureUrl())
                .append("shareUserId", getShareUserId())
                .append("shareUserName", getShareUserName())
                .append("shareGroupId", getShareGroupId())
                .append("shareGroupName", getShareGroupName())
                .append("forwardFlag", getForwardFlag())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("createBy", getCreateBy())
                .append("creator", getCreator())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .append("deptId", getDeptId())
                .append("deptName", getDeptName())
                .toString();
    }
}