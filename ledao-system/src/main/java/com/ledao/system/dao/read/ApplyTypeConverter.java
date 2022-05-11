package com.ledao.system.dao.read;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.ledao.common.core.text.Convert;

/**
 * @author lxz
 * @date 2022/4/28
 */
@SuppressWarnings("rawtypes")
public class ApplyTypeConverter implements Converter<String> {

    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String value = "0";
        String str = cellData.getStringValue();
        if ("入库申请".equals(str)) {
            value = "0";
        } else if ("出库申请".equals(str)) {
            value = "1";
        }
        return value;
    }

    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String str = "入库申请";
        if ("0".equals(str)) {
            str = "出库申请";
        }
        return new CellData(str);
    }
}
