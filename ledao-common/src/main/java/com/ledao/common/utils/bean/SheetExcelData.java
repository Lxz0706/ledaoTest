package com.ledao.common.utils.bean;

import java.util.List;

/**
 * @author lxz
 * @date 2022/4/28
 */
public class SheetExcelData<T> {
    /**
     * 数据
     */
    private List<T> dataList;

    /**
     * sheet名
     */
    private String sheetName;

    /**
     * 对象类型
     */
    private Class tClass;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Class gettClass() {
        return tClass;
    }

    public void settClass(Class tClass) {
        this.tClass = tClass;
    }
}
