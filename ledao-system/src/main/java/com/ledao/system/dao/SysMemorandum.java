package com.ledao.system.dao;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 备忘录对象 sys_memorandum
 *
 * @author lxz
 * @date 2021-03-04
 */
public class SysMemorandum extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long memorandumId;

    /**
     * 事项
     */
    @Excel(name = "事项")
    private String matter;

    /**
     * 开始时间
     */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    public void setMemorandumId(Long memorandumId) {
        this.memorandumId = memorandumId;
    }

    public Long getMemorandumId() {
        return memorandumId;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public String getMatter() {
        return matter;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("memorandumId", getMemorandumId())
                .append("matter", getMatter())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
