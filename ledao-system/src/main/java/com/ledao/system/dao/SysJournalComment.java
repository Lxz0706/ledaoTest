package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 日志评论对象 sys_journal_comment
 * 
 * @author lxz
 * @date 2021-09-05
 */
public class SysJournalComment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 日志 */
    @Excel(name = "日志")
    private String journalId;

    /** 父评论id */
    @Excel(name = "父评论id")
    private String parentId;

    /** 日志内容 */
    @Excel(name = "日志内容")
    private String workDetail;

    /** 预备字段1 */
    @Excel(name = "预备字段1")
    private String remarks1;

    /** 预备字段2 */
    @Excel(name = "预备字段2")
    private String remarks2;

    /** 预备字段3 */
    @Excel(name = "预备字段3")
    private String remarks3;

    /** 创建人名称 */
    @Excel(name = "创建人名称")
    private String creator;

    /** 修改人名称 */
    @Excel(name = "修改人名称")
    private String reviser;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setJournalId(String journalId) 
    {
        this.journalId = journalId;
    }

    public String getJournalId() 
    {
        return journalId;
    }
    public void setParentId(String parentId) 
    {
        this.parentId = parentId;
    }

    public String getParentId() 
    {
        return parentId;
    }
    public void setWorkDetail(String workDetail) 
    {
        this.workDetail = workDetail;
    }

    public String getWorkDetail() 
    {
        return workDetail;
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
            .append("journalId", getJournalId())
            .append("parentId", getParentId())
            .append("workDetail", getWorkDetail())
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
