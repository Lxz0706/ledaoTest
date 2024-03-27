package com.ledao.common.utils.poi;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.exception.BusinessException;
import com.ledao.common.utils.bean.SheetExcelData;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

/**
 * @author lxz
 * @date 2022/4/28
 */
public class EasyExcelUtil {
    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);


    /**
     * 导出多个sheet的excel文件
     *
     * @param fileName
     * @param sheetExcelDataList
     * @return
     */
    public void exportSheetsExcel(String fileName, List<SheetExcelData> sheetExcelDataList, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(outputStream).autoCloseStream(false).build();
        try {
            String filename = encodingFilename(fileName);
            setResponse(fileName, response);
            for (int i = 0, length = sheetExcelDataList.size(); i < length; i++) {
                WriteSheet writeSheet = EasyExcel.writerSheet(i, sheetExcelDataList.get(i).getSheetName())
                        .head(sheetExcelDataList.get(i).gettClass())
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
                excelWriter.write(sheetExcelDataList.get(i).getDataList(), writeSheet);

//                //表头样式
//                WriteCellStyle headWriteCellStyle = new WriteCellStyle();
//                //设置表头居中对齐
//                headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
//                //内容样式
//                WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
//                //设置内容靠左对齐
//                contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
//                HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
//                EasyExcel.write(getOutputStream(fileName, response), clazz).excelType(ExcelTypeEnum.XLSX).sheet(sheetExcelDataList.get(i).getSheetName()).registerWriteHandler(horizontalCellStyleStrategy).doWrite(sheetExcelDataList.get(i));

            }
            //return AjaxResult.success(filename);
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
            throw new BusinessException("导出Excel失败，请联系网站管理员！");
        } finally {
            // 刷新流，不加这句话，下载文件损坏打不开
            outputStream.flush();
            if (excelWriter != null) {
                // 千万别忘记finish关闭流
                excelWriter.finish();
            }
        }
    }

    /**
     * 设置导出信息
     *
     * @param fileName
     * @param response
     * @throws UnsupportedEncodingException
     */
    private static void setResponse(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        // 重置response
        response.reset();
        // 这里URLEncoder.encode可以防止中文乱码
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + encodingFilename(fileName));
    }


    /**
     * 编码文件名
     */
    public static String encodingFilename(String filename) {
        filename = UUID.randomUUID().toString() + "_" + filename + ".xlsx";
        return filename;
    }

    /**
     * 导出
     *
     * @param response
     * @param data
     * @param fileName
     * @param sheetName
     * @param clazz
     * @return
     * @throws Exception
     */
    public static AjaxResult writeExcel(HttpServletResponse response, List<? extends Object> data, String fileName, String sheetName, Class clazz) throws Exception {
        //表头样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //内容样式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置内容靠左对齐
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        EasyExcel.write(getOutputStream(fileName, response), clazz).excelType(ExcelTypeEnum.XLSX).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy).doWrite(data);
        return AjaxResult.success();
    }

    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws Exception {
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        return response.getOutputStream();
    }


}

