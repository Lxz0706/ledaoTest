package com.ledao.web.controller.monitor;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ledao.system.dao.SysCustomer;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ledao.common.annotation.Log;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.core.page.TableDataInfo;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.system.dao.SysOperLog;
import com.ledao.system.service.ISysOperLogService;

/**
 * 操作日志记录
 *
 * @author lxz
 */
@Controller
@RequestMapping("/monitor/operlog")
public class SysOperlogController extends BaseController {
    private String prefix = "monitor/operlog";

    @Autowired
    private ISysOperLogService operLogService;

    @RequiresPermissions("monitor:operlog:view")
    @GetMapping()
    public String operlog() {
        return prefix + "/operlog";
    }

    @RequiresPermissions("monitor:operlog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysOperLog operLog) {
        startPage();
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        return getDataTable(list);
    }

    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @RequiresPermissions("monitor:operlog:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysOperLog operLog) {
        //List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        //ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
        //return util.exportExcel(list, "操作日志");
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
        return util.exportEasyExcel(list, "操作日志");
    }

    @RequiresPermissions("monitor:operlog:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(operLogService.deleteOperLogByIds(ids));
    }

    @RequiresPermissions("monitor:operlog:detail")
    @GetMapping("/detail/{operId}")
    public String detail(@PathVariable("operId") Long operId, ModelMap mmap) {
        mmap.put("operLog", operLogService.selectOperLogById(operId));
        return prefix + "/detail";
    }

    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @RequiresPermissions("monitor:operlog:remove")
    @PostMapping("/clean")
    @ResponseBody
    public AjaxResult clean() {
        operLogService.cleanOperLog();
        return success();
    }

    @GetMapping("/analysis")
    public String analysis() {
        return prefix + "/analysis";
    }

    @PostMapping("/selectOperLogByTitle")
    @ResponseBody
    public String selectOperLogByTitle(SysOperLog sysOperLog) {
        List<SysOperLog> sysOperLogList = operLogService.selectOperLogByTitle(sysOperLog);
        JSONArray jsonArray = new JSONArray();
        for (SysOperLog sysOperLog1 : sysOperLogList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", sysOperLog1.getTitle());
            jsonObject.put("value", sysOperLog1.getTotal());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    @PostMapping("/selectSysOperLogByOperTime")
    @ResponseBody
    public String selectSysOperLogByOperTime(SysOperLog sysOperLog) {
        List<SysOperLog> sysOperLogList = operLogService.selectSysOperLogByOperTime(sysOperLog);
        JSONArray jsonArray = new JSONArray();
        for (SysOperLog sysOperLog1 : sysOperLogList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", sysOperLog1.getHours());
            jsonObject.put("value", sysOperLog1.getTotal());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    @PostMapping("/selectOperLogByOperName")
    @ResponseBody
    public String selectOperLogByOperName(SysOperLog sysOperLog) {
        List<SysOperLog> sysOperLogList = operLogService.selectOperLogByOperName(sysOperLog);
        JSONArray jsonArray = new JSONArray();
        for (SysOperLog sysOperLog1 : sysOperLogList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", sysOperLog1.getUserName());
            jsonObject.put("value", sysOperLog1.getTotal());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

}
