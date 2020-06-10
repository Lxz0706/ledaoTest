package com.ledao.system.domain;

import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 工作日志对象 sys_work_log
 *
 * @author ledao
 * @date 2020-06-09
 */
public class SysWorkLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 开始时间
     */
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dateStarted;

    /**
     * 结束时间
     */
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dateEnd;

    /**
     * 明日工作计划
     */
    @Excel(name = "明日工作计划")
    private String tomorrowWorkPlan;

    /**
     * 今天工作总结
     */
    @Excel(name = "今天工作总结")
    private String todayWorkSummar;

    /**
     * 所属部门
     */
    @Excel(name = "所属部门")
    private Integer deptId;

    /**
     * 所属角色
     */
    @Excel(name = "所属角色")
    private String roleId;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Integer userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    public Date getDateStarted() {
        return dateStarted;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setTomorrowWorkPlan(String tomorrowWorkPlan) {
        this.tomorrowWorkPlan = tomorrowWorkPlan;
    }

    public String getTomorrowWorkPlan() {
        return tomorrowWorkPlan;
    }

    public void setTodayWorkSummar(String todayWorkSummar) {
        this.todayWorkSummar = todayWorkSummar;
    }

    public String getTodayWorkSummar() {
        return todayWorkSummar;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("dateStarted", getDateStarted())
                .append("dateEnd", getDateEnd())
                .append("tomorrowWorkPlan", getTomorrowWorkPlan())
                .append("todayWorkSummar", getTodayWorkSummar())
                /*.append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())*/
                .append("deptId", getDeptId())
                .append("roleId", getRoleId())
                .append("userId", getUserId())
                .toString();
    }
}
