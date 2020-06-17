package com.ledao.system.domain;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 司法对象 sys_judicial
 *
 * @author lxz
 * @date 2020-06-09
 */
public class SysJudicial extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 所有人
     */
    @Excel(name = "所有人")
    private String owner;

    /**
     * 所有人类型
     */
    @Excel(name = "所有人类型")
    private String ownertype;

    /**
     * 网站链接
     */
    @Excel(name = "网站链接")
    private String websiteLinks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwnertype(String ownertype) {
        this.ownertype = ownertype;
    }

    public String getOwnertype() {
        return ownertype;
    }

    public void setWebsiteLinks(String websiteLinks) {
        this.websiteLinks = websiteLinks;
    }

    public String getWebsiteLinks() {
        return websiteLinks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("owner", getOwner())
                .append("ownertype", getOwnertype())
                .append("websiteLinks", getWebsiteLinks())
                .toString();
    }
}
