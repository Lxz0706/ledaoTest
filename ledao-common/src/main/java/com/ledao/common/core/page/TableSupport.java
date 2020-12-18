package com.ledao.common.core.page;

import com.ledao.common.constant.Constants;
import com.ledao.common.utils.ServletUtils;

/**
 * 表格数据处理
 *
 * @author lxz
 */
public class TableSupport {
    /**
     * 封装分页对象
     */
    public static PageDao getPagedao() {
        PageDao pagedao = new PageDao();
        pagedao.setPageNum(ServletUtils.getParameterToInt(Constants.PAGE_NUM));
        pagedao.setPageSize(ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
        pagedao.setOrderByColumn(ServletUtils.getParameter(Constants.ORDER_BY_COLUMN));
        pagedao.setIsAsc(ServletUtils.getParameter(Constants.IS_ASC));
        return pagedao;
    }

    public static PageDao buildPageRequest() {
        return getPagedao();
    }
}
