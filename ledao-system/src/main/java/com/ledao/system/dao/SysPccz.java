package com.ledao.system.dao;

import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 破产网对象 sys_pccz
 *
 * @author lxz
 * @date 2022-10-18
 */
public class SysPccz extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 关键词
     */
    @Excel(name = "关键词")
    private String keyword;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private String itemType;

    /**
     * 公开人
     */
    @Excel(name = "公开人")
    private String gkr;

    /**
     * 网址
     */
    @Excel(name = "网址")
    private String url;

    /**
     * 状态（0 未推送 1 已推送）
     */
    @Excel(name = "状态", readConverterExp = "0=,未=推送,1=,已=推送")
    private String status;

    /**
     * 公开时间
     */
    @Excel(name = "公开时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishDate;

    /**
     * 创建时间（数据创建时间）
     */
    @Excel(name = "创建时间", readConverterExp = "数=据创建时间")
    private Date uploadtime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setGkr(String gkr) {
        this.gkr = gkr;
    }

    public String getGkr() {
        return gkr;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }

    public Date getUploadtime() {
        return uploadtime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("keyword", getKeyword())
                .append("title", getTitle())
                .append("itemType", getItemType())
                .append("gkr", getGkr())
                .append("url", getUrl())
                .append("status", getStatus())
                .append("publishDate", getPublishDate())
                .append("uploadtime", getUploadtime())
                .toString();
    }
}
