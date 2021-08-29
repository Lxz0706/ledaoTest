package com.ledao.system.dao;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 签到管理对象 sys_train_admin
 * 
 * @author lxz
 * @date 2021-08-29
 */
public class SysTrainAdmin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long trainId;

    /** 课程名称 */
    @Excel(name = "课程名称")
    private String trainName;

    /** 创建部门 */
    @Excel(name = "创建部门")
    private String trainDep;

    /** 培训讲师 */
    @Excel(name = "培训讲师")
    private String trainTeacher;

    /** 培训地点 */
    @Excel(name = "培训地点")
    private String trainPlace;

    /** 培训类型 */
    @Excel(name = "培训类型")
    private String trainType;

    /** 培训开始日期 */
    @Excel(name = "培训开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 培训结束日期 */
    @Excel(name = "培训结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 培训开始时间 */
    @Excel(name = "培训开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 培训结束时间 */
    @Excel(name = "培训结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 二维码有效开始时间 */
    @Excel(name = "二维码有效开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date qrcodeStartTime;

    /** 二维码有效结束时间 */
    @Excel(name = "二维码有效结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date qrcodeEndTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 预留字段1 */
    @Excel(name = "预留字段1")
    private String remark1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String remark2;

    /** 预留字段3 */
    @Excel(name = "预留字段3")
    private String remark3;

    public void setTrainId(Long trainId) 
    {
        this.trainId = trainId;
    }

    public Long getTrainId() 
    {
        return trainId;
    }
    public void setTrainName(String trainName) 
    {
        this.trainName = trainName;
    }

    public String getTrainName() 
    {
        return trainName;
    }
    public void setTrainDep(String trainDep) 
    {
        this.trainDep = trainDep;
    }

    public String getTrainDep() 
    {
        return trainDep;
    }
    public void setTrainTeacher(String trainTeacher) 
    {
        this.trainTeacher = trainTeacher;
    }

    public String getTrainTeacher() 
    {
        return trainTeacher;
    }
    public void setTrainPlace(String trainPlace) 
    {
        this.trainPlace = trainPlace;
    }

    public String getTrainPlace() 
    {
        return trainPlace;
    }
    public void setTrainType(String trainType) 
    {
        this.trainType = trainType;
    }

    public String getTrainType() 
    {
        return trainType;
    }
    public void setStartDate(Date startDate) 
    {
        this.startDate = startDate;
    }

    public Date getStartDate() 
    {
        return startDate;
    }
    public void setEndDate(Date endDate) 
    {
        this.endDate = endDate;
    }

    public Date getEndDate() 
    {
        return endDate;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setQrcodeStartTime(Date qrcodeStartTime) 
    {
        this.qrcodeStartTime = qrcodeStartTime;
    }

    public Date getQrcodeStartTime() 
    {
        return qrcodeStartTime;
    }
    public void setQrcodeEndTime(Date qrcodeEndTime) 
    {
        this.qrcodeEndTime = qrcodeEndTime;
    }

    public Date getQrcodeEndTime() 
    {
        return qrcodeEndTime;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setRemark1(String remark1) 
    {
        this.remark1 = remark1;
    }

    public String getRemark1() 
    {
        return remark1;
    }
    public void setRemark2(String remark2) 
    {
        this.remark2 = remark2;
    }

    public String getRemark2() 
    {
        return remark2;
    }
    public void setRemark3(String remark3) 
    {
        this.remark3 = remark3;
    }

    public String getRemark3() 
    {
        return remark3;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("trainId", getTrainId())
            .append("trainName", getTrainName())
            .append("trainDep", getTrainDep())
            .append("trainTeacher", getTrainTeacher())
            .append("trainPlace", getTrainPlace())
            .append("trainType", getTrainType())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("qrcodeStartTime", getQrcodeStartTime())
            .append("qrcodeEndTime", getQrcodeEndTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark1", getRemark1())
            .append("remark2", getRemark2())
            .append("remark3", getRemark3())
            .toString();
    }
}
