package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 公司部门对象 sys_department
 *
 * @author lxz
 * @date 2021-06-21
 */
public class SysDepartment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    private Long departmentId;

    /**
     * 部门名称
     */
    @Excel(name = "部门名称")
    private String departmentName;

    /**
     * 父级id
     */
    @Excel(name = "父级id")
    private Long pId;

    /**
     * 排列顺序
     */
    @Excel(name = "排列顺序")
    private String orderNum;

    /**
     * 部门领导id
     */
    @Excel(name = "部门领导id")
    private Long managerId;

    /**
     * 部门领导名称
     */
    @Excel(name = "部门领导名称")
    private String managerName;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remarks;

    /**
     * 部门状态
     */
    @Excel(name = "部门状态")
    private String status;

    /**
     * 删除状态
     */
    private String delFlag;

    private String parentName;

    /**
     * 祖级列表
     */
    private String ancestors;

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @NotBlank(message = "部门名称不能为空")
    @Size(min = 0, max = 30, message = "部门名称长度不能超过30个字符")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Long getpId() {
        return pId;
    }

    @NotBlank(message = "显示顺序不能为空")
    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("departmentId", getDepartmentId())
                .append("departmentName", getDepartmentName())
                .append("pId", getpId())
                .append("orderNum", getOrderNum())
                .append("managerId", getManagerId())
                .append("managerName", getManagerName())
                .append("remarks", getRemarks())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("ancestors", getAncestors())
                .append("parentName", getParentName())
                .toString();
    }
}
