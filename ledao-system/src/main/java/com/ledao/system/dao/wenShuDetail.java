package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 文书网数据对象 wenshu_detail
 *
 * @author lxz
 * @date 2022-12-14
 */
public class wenShuDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 关联id
     */
    private String itemCourtId;

    /**
     * 文本所在行数
     */
    @Excel(name = "文本所在行数")
    private Integer itemIndex;

    /**
     * 文本
     */
    @Excel(name = "文本")
    private String itemData;

    public void setItemCourtId(String itemCourtId) {
        this.itemCourtId = itemCourtId;
    }

    public String getItemCourtId() {
        return itemCourtId;
    }

    public void setItemIndex(Integer itemIndex) {
        this.itemIndex = itemIndex;
    }

    public Integer getItemIndex() {
        return itemIndex;
    }

    public void setItemData(String itemData) {
        this.itemData = itemData;
    }

    public String getItemData() {
        return itemData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("itemCourtId", getItemCourtId())
                .append("itemIndex", getItemIndex())
                .append("itemData", getItemData())
                .toString();
    }
}
