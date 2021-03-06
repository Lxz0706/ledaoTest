package com.ledao.common.core.page;

import com.ledao.common.constant.Constants;
import com.ledao.common.utils.StringUtils;

/**
 * 分页数据
 *
 * @author lxz
 */
public class PageDao {
    /**
     * 当前记录起始索引
     */
    private Integer pageNum;
    /**
     * 每页显示记录数
     */
    private Integer pageSize;
    /**
     * 排序列
     */
    private String orderByColumn;
    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    private String isAsc;

    public String getOrderBy() {
        if (StringUtils.isEmpty(orderByColumn)) {
            return "";
        }
        if ("floorage".equals(orderByColumn) || "landArea".equals(orderByColumn)
                || "totalPrice".equals(orderByColumn) || "capValue".equals(orderByColumn)) {
            return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
        } else {
            return "convert(" + StringUtils.toUnderScoreCase(orderByColumn) + " " + "using " + " " + Constants.GBK + ")" + " COLLATE gbk_chinese_ci  " + isAsc;
        }

        //return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(String isAsc) {
        this.isAsc = isAsc;
    }
}
