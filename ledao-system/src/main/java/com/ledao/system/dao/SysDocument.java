package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 文件管理对象 sys_document
 *
 * @author lxz
 * @date 2021-02-23
 */
public class SysDocument extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long fileId;

    /**
     * 父级id
     */
    @Excel(name = "父级id")
    private Long parentId;

    /**
     * 文件名称
     */
    @Excel(name = "文件名称")
    private String fileName;

    /**
     * 文件类型
     */
    @Excel(name = "文件类型")
    private String fileType;

    /**
     * 文件用途
     */
    @Excel(name = "文件用途")
    private String documentType;

    /**
     * 文件大小
     */
    @Excel(name = "文件大小")
    private Double fileSize;

    /**
     * 文件版本
     */
    @Excel(name = "文件版本")
    private String fileVersion;

    /**
     * 是否可下载
     */
    @Excel(name = "是否可下载")
    private String downloadableFlag;

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

    /**
     * 共享人id
     */
    @Excel(name = "共享人id")
    private String shareUserId;

    /**
     * 共享人名称
     */
    @Excel(name = "共享人名称")
    private String shareUserName;

    /**
     * 文件路径
     */
    @Excel(name = "文件路径")
    private String fileUrl;

    /**
     * 创建人名称
     */
    private String createor;

    /**
     * 修改人名称
     */
    private String updateor;

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setFileSize(Double fileSize) {
        this.fileSize = fileSize;
    }

    public Double getFileSize() {
        return fileSize;
    }

    public void setFileVersion(String fileVersion) {
        this.fileVersion = fileVersion;
    }

    public String getFileVersion() {
        return fileVersion;
    }

    public void setDownloadableFlag(String downloadableFlag) {
        this.downloadableFlag = downloadableFlag;
    }

    public String getDownloadableFlag() {
        return downloadableFlag;
    }

    public void setShareDeptId(String shareDeptId) {
        this.shareDeptId = shareDeptId;
    }

    public String getShareDeptId() {
        return shareDeptId;
    }

    public void setShareDeptName(String shareDeptName) {
        this.shareDeptName = shareDeptName;
    }

    public String getShareDeptName() {
        return shareDeptName;
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

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setCreateor(String createor) {
        this.createor = createor;
    }

    public String getCreateor() {
        return createor;
    }

    public void setUpdateor(String updateor) {
        this.updateor = updateor;
    }

    public String getUpdateor() {
        return updateor;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("fileId", getFileId())
                .append("parentId", getParentId())
                .append("fileName", getFileName())
                .append("fileType", getFileType())
                .append("documentType", getDocumentType())
                .append("fileSize", getFileSize())
                .append("fileVersion", getFileVersion())
                .append("downloadableFlag", getDownloadableFlag())
                .append("shareDeptId", getShareDeptId())
                .append("shareDeptName", getShareDeptName())
                .append("shareUserId", getShareUserId())
                .append("shareUserName", getShareUserName())
                .append("fileUrl", getFileUrl())
                .append("createBy", getCreateBy())
                .append("createor", getCreateor())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateor", getUpdateor())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
