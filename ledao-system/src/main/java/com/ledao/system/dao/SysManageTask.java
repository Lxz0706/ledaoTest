package com.ledao.system.dao;

import java.util.Date;
import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 处置回现任务对象 sys_manage_task
 * 
 * @author lxz
 * @date 2021-09-03
 */
public class SysManageTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 项目id */
    @Excel(name = "项目id")
    private Long proId;

    /** 任务类型 总任务0，子任务1 */
    @Excel(name = "任务类型 总任务0，子任务1")
    private String taskType;

    /** 预计开始时间 */
    @Excel(name = "预计开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planBeginTime;

    /** 实际开始时间 */
    @Excel(name = "实际开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date realBeginTime;

    /** 预计结束时间 */
    @Excel(name = "预计结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planEndTime;

    /** 实际结束时间 */
    @Excel(name = "实际结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date realEndTime;

    /** 预计回收金额 */
    @Excel(name = "预计回收金额")
    private Long planBackMoney;

    /** 实际回收金额 */
    @Excel(name = "实际回收金额")
    private Long realBackMoney;

    /** 超时时长(天) */
    @Excel(name = "超时时长(天)")
    private Long overDay;

    /** 节点状态 */
    @Excel(name = "节点状态")
    private String nodeStatu;

    /** 状态 */
    @Excel(name = "状态")
    private String taskStatu;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 备用字段1 */
    @Excel(name = "备用字段1")
    private String remarks1;

    /** 备用字段2 */
    @Excel(name = "备用字段2")
    private String remarks2;

    /** 备用字段3 */
    @Excel(name = "备用字段3")
    private String remarks3;

    /** 创建人名称 */
    @Excel(name = "创建人名称")
    private String creator;

    /** 修改人名称 */
    @Excel(name = "修改人名称")
    private String reviser;

    private String zckName;

    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getZckName() {
        return zckName;
    }

    public void setZckName(String zckName) {
        this.zckName = zckName;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProId(Long proId) 
    {
        this.proId = proId;
    }

    public Long getProId() 
    {
        return proId;
    }
    public void setTaskType(String taskType)
    {
        this.taskType = taskType;
    }

    public String getTaskType()
    {
        return taskType;
    }
    public void setPlanBeginTime(Date planBeginTime) 
    {
        this.planBeginTime = planBeginTime;
    }

    public Date getPlanBeginTime() 
    {
        return planBeginTime;
    }
    public void setRealBeginTime(Date realBeginTime) 
    {
        this.realBeginTime = realBeginTime;
    }

    public Date getRealBeginTime() 
    {
        return realBeginTime;
    }
    public void setPlanEndTime(Date planEndTime) 
    {
        this.planEndTime = planEndTime;
    }

    public Date getPlanEndTime() 
    {
        return planEndTime;
    }
    public void setRealEndTime(Date realEndTime) 
    {
        this.realEndTime = realEndTime;
    }

    public Date getRealEndTime() 
    {
        return realEndTime;
    }
    public void setPlanBackMoney(Long planBackMoney) 
    {
        this.planBackMoney = planBackMoney;
    }

    public Long getPlanBackMoney() 
    {
        return planBackMoney;
    }
    public void setRealBackMoney(Long realBackMoney) 
    {
        this.realBackMoney = realBackMoney;
    }

    public Long getRealBackMoney() 
    {
        return realBackMoney;
    }
    public void setOverDay(Long overDay) 
    {
        this.overDay = overDay;
    }

    public Long getOverDay() 
    {
        return overDay;
    }
    public void setNodeStatu(String nodeStatu)
    {
        this.nodeStatu = nodeStatu;
    }

    public String getNodeStatu()
    {
        return nodeStatu;
    }
    public void setTaskStatu(String taskStatu) 
    {
        this.taskStatu = taskStatu;
    }

    public String getTaskStatu() 
    {
        return taskStatu;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setRemarks1(String remarks1) 
    {
        this.remarks1 = remarks1;
    }

    public String getRemarks1() 
    {
        return remarks1;
    }
    public void setRemarks2(String remarks2) 
    {
        this.remarks2 = remarks2;
    }

    public String getRemarks2() 
    {
        return remarks2;
    }
    public void setRemarks3(String remarks3) 
    {
        this.remarks3 = remarks3;
    }

    public String getRemarks3() 
    {
        return remarks3;
    }
    public void setCreator(String creator) 
    {
        this.creator = creator;
    }

    public String getCreator() 
    {
        return creator;
    }
    public void setReviser(String reviser) 
    {
        this.reviser = reviser;
    }

    public String getReviser() 
    {
        return reviser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("proId", getProId())
            .append("taskType", getTaskType())
            .append("planBeginTime", getPlanBeginTime())
            .append("realBeginTime", getRealBeginTime())
            .append("planEndTime", getPlanEndTime())
            .append("realEndTime", getRealEndTime())
            .append("planBackMoney", getPlanBackMoney())
            .append("realBackMoney", getRealBackMoney())
            .append("overDay", getOverDay())
            .append("nodeStatu", getNodeStatu())
            .append("taskStatu", getTaskStatu())
            .append("remarks", getRemarks())
            .append("remarks1", getRemarks1())
            .append("remarks2", getRemarks2())
            .append("remarks3", getRemarks3())
            .append("createBy", getCreateBy())
            .append("creator", getCreator())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("reviser", getReviser())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
