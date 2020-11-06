package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 sys_a_property
 *
 * @author lxz
 * @date 2020-10-20
 */
public class SysAProperty extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 项目id
     */
    @Excel(name = "项目id")
    private String itemId;

    /**
     * 项目标题
     */
    @Excel(name = "项目标题")
    private String itemTitle;

    /**
     * 项目时间
     */
    @Excel(name = "项目时间")
    private String itemTime;

    /**
     * 项目转让方
     */
    @Excel(name = "项目转让方")
    private String itemTransferor;

    /**
     * 项目受让方
     */
    @Excel(name = "项目受让方")
    private String itemTransferee;

    /**
     * 项目金额
     */
    @Excel(name = "项目金额")
    private String itemAmount;

    /**
     * 项目url
     */
    @Excel(name = "项目url")
    private String itemUrl;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTime(String itemTime) {
        this.itemTime = itemTime;
    }

    public String getItemTime() {
        return itemTime;
    }

    public void setItemTransferor(String itemTransferor) {
        this.itemTransferor = itemTransferor;
    }

    public String getItemTransferor() {
        return itemTransferor;
    }

    public void setItemTransferee(String itemTransferee) {
        this.itemTransferee = itemTransferee;
    }

    public String getItemTransferee() {
        return itemTransferee;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getItemAmount() {
        return itemAmount;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id" , getId())
                .append("itemId" , getItemId())
                .append("itemTitle" , getItemTitle())
                .append("itemTime" , getItemTime())
                .append("itemTransferor" , getItemTransferor())
                .append("itemTransferee" , getItemTransferee())
                .append("itemAmount" , getItemAmount())
                .append("itemUrl" , getItemUrl())
                .toString();
    }
}
