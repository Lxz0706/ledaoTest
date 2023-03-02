package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 估值计算主对象 sys_valuation
 *
 * @author lxz
 * @date 2022-11-11
 */
public class SysValuation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long valuationId;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private String itemType;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String itemStatus;

    /***
     * itemID
     */
    private String itemId;

    private String creator;

    public void setValuationId(Long valuationId) {
        this.valuationId = valuationId;
    }

    public Long getValuationId() {
        return valuationId;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("valuationId", getValuationId())
                .append("itemType", getItemType())
                .append("itemStatus", getItemStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("itemId", getItemId())
                .toString();
    }
}