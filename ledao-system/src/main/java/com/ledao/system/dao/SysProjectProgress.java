package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 sys_project_progress
 *
 * @author ledao
 * @date 2020-08-26
 */
public class SysProjectProgress extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long progressId;

    /**
     * 项目进度表ID
     */
    @Excel(name = "项目进度表ID")
    private Long projectManagementId;

    /**
     * 进度情况
     */
    @Excel(name = "进度情况")
    private String progress;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @Excel(name = "删除标志", readConverterExp = "0=代表存在,2=代表删除")
    private String delFlag;


    /**
     * 是否是投后项目
     */
    private String project;

    private String projectManagementName;

    public void setProgressId(Long progressId) {
        this.progressId = progressId;
    }

    public Long getProgressId() {
        return progressId;
    }

    public void setProjectManagementId(Long projectManagementId) {
        this.projectManagementId = projectManagementId;
    }

    public Long getProjectManagementId() {
        return projectManagementId;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getProgress() {
        return progress;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getProjectManagementName() {
        return projectManagementName;
    }

    public void setProjectManagementName(String projectManagementName) {
        this.projectManagementName = projectManagementName;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("progressId", getProgressId())
                .append("projectManagementId", getProjectManagementId())
                .append("progress", getProgress())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isProject", getProject())
                .toString();
    }
}
