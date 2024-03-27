package com.ledao.system.dao;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 员工岗位异动对象 sys_reshuffle
 *
 * @author lxz
 * @date 2024-03-19
 */
@ExcelIgnoreUnannotated
@ColumnWidth(20)
@HeadRowHeight(14)
@HeadFontStyle(fontHeightInPoints = 11)
public class SysReshuffle extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long reshuffleId;

    /**
     * 员工id
     */
    //@ExcelProperty(value = "员工id")
    private Long staffId;

    @ExcelProperty(value = "员工名称")
    private String staffName;

    /**
     * 异动前岗位
     */
    @ExcelProperty(value = "异动前岗位")
    private String transferBefore;

    /**
     * 异动后岗位
     */
    @ExcelProperty(value = "异动后岗位")
    private String transferAfter;

    /**
     * 异动类型
     */
    @ExcelProperty(value = "异动类型")
    private String transferType;

    public void setReshuffleId(Long reshuffleId) {
        this.reshuffleId = reshuffleId;
    }

    public Long getReshuffleId() {
        return reshuffleId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setTransferBefore(String transferBefore) {
        this.transferBefore = transferBefore;
    }

    public String getTransferBefore() {
        return transferBefore;
    }

    public void setTransferAfter(String transferAfter) {
        this.transferAfter = transferAfter;
    }

    public String getTransferAfter() {
        return transferAfter;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferType() {
        return transferType;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("reshuffleId", getReshuffleId())
                .append("staffId", getStaffId())
                .append("transferBefore", getTransferBefore())
                .append("transferAfter", getTransferAfter())
                .append("transferType", getTransferType())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
