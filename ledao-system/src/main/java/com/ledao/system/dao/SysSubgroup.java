package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 我的小组对象 sys_subgroup
 *
 * @author lxz
 * @date 2021-03-26
 */
public class SysSubgroup extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 组id
     */
    private Long subgroupId;

    /**
     * 小组名称
     */
    @Excel(name = "小组名称")
    private String subgroupName;

    /**
     * 小组成员id
     */
    @Excel(name = "小组成员id")
    private String subgroupUserId;

    /**
     * 小组成员
     */
    @Excel(name = "小组成员")
    private String subgroupUserName;

    public void setSubgroupId(Long subgroupId) {
        this.subgroupId = subgroupId;
    }

    public Long getSubgroupId() {
        return subgroupId;
    }

    public void setSubgroupName(String subgroupName) {
        this.subgroupName = subgroupName;
    }

    public String getSubgroupName() {
        return subgroupName;
    }

    public void setSubgroupUserId(String subgroupUserId) {
        this.subgroupUserId = subgroupUserId;
    }

    public String getSubgroupUserId() {
        return subgroupUserId;
    }

    public void setSubgroupUserName(String subgroupUserName) {
        this.subgroupUserName = subgroupUserName;
    }

    public String getSubgroupUserName() {
        return subgroupUserName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("subgroupId", getSubgroupId())
                .append("subgroupName", getSubgroupName())
                .append("subgroupUserId", getSubgroupUserId())
                .append("subgroupUserName", getSubgroupUserName())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
