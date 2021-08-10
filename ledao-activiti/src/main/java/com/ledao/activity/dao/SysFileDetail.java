package com.ledao.activity.dao;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;

/**
 * 档案详情对象 sys_file_detail
 * 
 * @author lxz
 * @date 2021-08-04
 */
public class SysFileDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long fileId;

    /** 档案名称 */
    @Excel(name = "档案名称")
    private String fileName;

    /** 档案路径 */
    @Excel(name = "档案路径")
    private String fileUrl;

    /** 创建人名称 */
    @Excel(name = "创建人名称")
    private String creator;

    /** 修改人名称 */
    @Excel(name = "修改人名称")
    private String reviser;
    

	/** 档案id */
    @Excel(name = "档案id")
    private Long documentFileId;
    
    public Long getDocumentFileId() {
    	return documentFileId;
    }
    
    public void setDocumentFileId(Long documentFileId) {
    	this.documentFileId = documentFileId;
    }

    public void setFileId(Long fileId) 
    {
        this.fileId = fileId;
    }

    public Long getFileId() 
    {
        return fileId;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setFileUrl(String fileUrl) 
    {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() 
    {
        return fileUrl;
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
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getFileId())
            .append("fileName", getFileName())
            .append("fileUrl", getFileUrl())
            .append("createBy", getCreateBy())
            .append("creator", getCreator())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("reviser", getReviser())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
