package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;

import java.util.Date;

/**
 * @author lxz
 * @date 2022/4/21
 */
public class SysJournalCreator {

    @Excel(name = "名称")
    private String userName;

    @Excel(name = "时间")
    private String beginTime;

    @Excel(name = "部门")
    private String deptName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
