package com.ledao.common.annotation;

import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;

/**
 * @ClassName ExcelModel
 * @Description TODO
 * @Author 87852
 * @Date 2021/7/13 10:35
 * @Version 1.0
 */
public class ExcelModel implements Serializable {

    @ExcelProperty(value = {"姓名"}, index = 0)
    @Excel(name = "姓名")
    private String name;

    @ExcelProperty(value = {"用户id"}, index = 1)
    @Excel(name = "用户id")
    private String userId;

    @ExcelProperty(value = {"考勤组"}, index = 2)
    @Excel(name = "考勤组")
    private String groupId;

    @ExcelProperty(value = {"上下班"}, index = 3)
    @Excel(name = "上下班")
    private String checkType;

    @ExcelProperty(value = {"时间"}, index = 4)
    @Excel(name = "时间")
    private String checkTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ExcelModel) {
            ExcelModel excelModel = (ExcelModel) o;
            return this.name.equals(excelModel.name)
                    && this.userId.equals(excelModel.userId)
                    && this.groupId.equals(excelModel.groupId)
                    && this.checkType.equals(excelModel.checkType)
                    && this.checkTime.equals(excelModel.checkTime);
        }
        return super.equals(o);
    }
}
