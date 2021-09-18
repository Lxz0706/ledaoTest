package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 底层资料对象 sys_underlying_data
 * 
 * @author lxz
 * @date 2021-09-07
 */
public class SysUnderlyingData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long fileId;

    /** 父集id */
    @Excel(name = "父集id")
    private Long parentId;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 文件类型 */
    @Excel(name = "文件类型")
    private String fileType;

    /** 文件大小 */
    @Excel(name = "文件大小")
    private Double fileSize;

    /** 是否可下载 */
    @Excel(name = "是否可下载")
    private String downloadableFlag;

    /** 创建人名称 */
    @Excel(name = "创建人名称")
    private String createor;

    /** 浏览次数 */
    @Excel(name = "浏览次数")
    private Long previewCount;

    /** 下载次数 */
    @Excel(name = "下载次数")
    private Long downloadsCount;

    /** 更新人名称 */
    @Excel(name = "更新人名称")
    private String updateor;

    @Excel(name = "项目id")
    private Long projectId;

    @Excel(name = "文件路径")
    private String fileUrl;

    @Excel(name = "项目类型")
    private Long projectType;

    private String proType;

    private String proName;

    private String zckName;

    public String getZckName() {
        return zckName;
    }

    public void setZckName(String zckName) {
        this.zckName = zckName;
    }

    private String createorName;

    public String getCreateorName() {
        return createorName;
    }

    public void setCreateorName(String createorName) {
        this.createorName = createorName;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public void setFileId(Long fileId)
    {
        this.fileId = fileId;
    }

    public Long getFileId() 
    {
        return fileId;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setFileType(String fileType) 
    {
        this.fileType = fileType;
    }

    public String getFileType() 
    {
        return fileType;
    }
    public void setFileSize(Double fileSize) 
    {
        this.fileSize = fileSize;
    }

    public Double getFileSize() 
    {
        return fileSize;
    }
    public void setDownloadableFlag(String downloadableFlag) 
    {
        this.downloadableFlag = downloadableFlag;
    }

    public String getDownloadableFlag() 
    {
        return downloadableFlag;
    }
    public void setCreateor(String createor) 
    {
        this.createor = createor;
    }

    public String getCreateor() 
    {
        return createor;
    }
    public void setPreviewCount(Long previewCount) 
    {
        this.previewCount = previewCount;
    }

    public Long getPreviewCount() 
    {
        return previewCount;
    }
    public void setDownloadsCount(Long downloadsCount) 
    {
        this.downloadsCount = downloadsCount;
    }

    public Long getDownloadsCount() 
    {
        return downloadsCount;
    }
    public void setUpdateor(String updateor) 
    {
        this.updateor = updateor;
    }

    public String getUpdateor() 
    {
        return updateor;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public Long getProjectType() {
        return projectType;
    }

    public void setProjectType(Long projectType) {
        this.projectType = projectType;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getFileId())
            .append("parentId", getParentId())
            .append("fileName", getFileName())
            .append("fileType", getFileType())
            .append("fileSize", getFileSize())
            .append("downloadableFlag", getDownloadableFlag())
            .append("createTime", getCreateTime())
            .append("createor", getCreateor())
            .append("previewCount", getPreviewCount())
            .append("downloadsCount", getDownloadsCount())
            .append("updateTime", getUpdateTime())
            .append("updateor", getUpdateor())
            .append("updateBy", getUpdateBy())
            .append("projectId", getProjectId())
            .append("fileUrl", getFileUrl())
            .append("createBy", getCreateBy())
            .append("projectType", getProjectType())
            .toString();
    }
}
