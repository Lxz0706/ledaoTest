package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 预约时间设置对象 sys_reservation_date
 *
 * @author lxz
 * @date 2023-11-17
 */
public class SysReservationDate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long reservationDateId;

    /**
     * 预约时间
     */
    @Excel(name = "预约时间")
    private String reservationDate;

    public void setReservationDateId(Long reservationDateId) {
        this.reservationDateId = reservationDateId;
    }

    public Long getReservationDateId() {
        return reservationDateId;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("reservationDateId", getReservationDateId())
                .append("reservationDate", getReservationDate())
                .toString();
    }
}
