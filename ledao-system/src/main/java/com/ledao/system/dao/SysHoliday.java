package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 节假日对象 sys_holiday
 *
 * @author lxz
 * @date 2022-11-01
 */
public class SysHoliday extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 节假日
     */
    @Excel(name = "节假日")
    private String holiday;

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getHoliday() {
        return holiday;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("holiday", getHoliday())
                .toString();
    }
}
