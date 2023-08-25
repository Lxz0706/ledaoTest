package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 日志对象 sys_journal
 *
 * @author lxz
 * @date 2021-09-05
 */
public class SysJournal extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 部门id
     */
    @Excel(name = "部门id")
    private String depId;

    private String deptName;

    private String userName;

    /**
     * 项目id
     */
    @Excel(name = "项目id")
    private String proId;

    private String projectName;

    /**
     * 日志内容
     */
    @Excel(name = "日志内容")
    private String workDetail;

    /**
     * 分享人
     */
    @Excel(name = "分享人")
    private String shared;

    /**
     * 模块顺序
     */
    @Excel(name = "模块顺序")
    private Long modularOrder;

    /**
     * 预备字段1
     */
    @Excel(name = "预备字段1")
    private String remarks1;

    /**
     * 预备字段2
     */
    @Excel(name = "预备字段2")
    private String remarks2;

    /**
     * 预备字段3
     */
    @Excel(name = "预备字段3")
    private String remarks3;

    /**
     * 创建人名称
     */
    @Excel(name = "创建人名称")
    private String creator;

    /**
     * 修改人名称
     */
    @Excel(name = "修改人名称")
    private String reviser;

    private long deptId;

    private String selectTime;

    /**
     * 是否正式员工
     */
    private String formalFlag;

    private String beginTime;

    private String endTime;

    private String projectType;

    private String name;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getDepId() {
        return depId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProId() {
        return proId;
    }

    public void setWorkDetail(String workDetail) {
        this.workDetail = workDetail;
    }

    public String getWorkDetail() {
        return workDetail;
    }

    public void setShared(String shared) {
        this.shared = shared;
    }

    public String getShared() {
        return shared;
    }

    public void setModularOrder(Long modularOrder) {
        this.modularOrder = modularOrder;
    }

    public Long getModularOrder() {
        return modularOrder;
    }

    public void setRemarks1(String remarks1) {
        this.remarks1 = remarks1;
    }

    public String getRemarks1() {
        return remarks1;
    }

    public void setRemarks2(String remarks2) {
        this.remarks2 = remarks2;
    }

    public String getRemarks2() {
        return remarks2;
    }

    public void setRemarks3(String remarks3) {
        this.remarks3 = remarks3;
    }

    public String getRemarks3() {
        return remarks3;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    public void setReviser(String reviser) {
        this.reviser = reviser;
    }

    public String getReviser() {
        return reviser;
    }

    public String getSelectTime() {
        return selectTime;
    }

    public void setSelectTime(String selectTime) {
        this.selectTime = selectTime;
    }

    public String getFormalFlag() {
        return formalFlag;
    }

    public void setFormalFlag(String formalFlag) {
        this.formalFlag = formalFlag;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("depId", getDepId())
                .append("proId", getProId())
                .append("workDetail", getWorkDetail())
                .append("shared", getShared())
                .append("modularOrder", getModularOrder())
                .append("remarks1", getRemarks1())
                .append("remarks2", getRemarks2())
                .append("remarks3", getRemarks3())
                .append("createBy", getCreateBy())
                .append("creator", getCreator())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("reviser", getReviser())
                .append("updateTime", getUpdateTime())
                .append("projectName", getProjectName())
                .append("selectTime", getSelectTime())
                .append("formalFlag", getFormalFlag())
                .append("projectType", getProjectType())
                .toString();
    }
}
