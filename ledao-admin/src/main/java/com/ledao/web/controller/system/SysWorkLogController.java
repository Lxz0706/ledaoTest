package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.system.domain.SysWorkLog;
import com.ledao.system.service.ISysWorkLogService;
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
import com.ledao.common.enums.BusinessType;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.domain.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 工作日志Controller
 *
 * @author lxz
 * @date 2020-06-09
 */
@Controller
@RequestMapping("/system/log")
public class SysWorkLogController extends BaseController {
    private String prefix = "system/log" ;

    @Autowired
    private ISysWorkLogService sysWorkLogService;

    @RequiresPermissions("system:log:view")
    @GetMapping()
    public String log() {
        return prefix + "/log" ;
    }

    /**
     * 查询工作日志列表
     */
    @RequiresPermissions("system:log:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysWorkLog sysWorkLog) {
        startPage();
        List<SysWorkLog> list = sysWorkLogService.selectSysWorkLogList(sysWorkLog);
        return getDataTable(list);
    }

    /**
     * 导出工作日志列表
     */
    @RequiresPermissions("system:log:export")
    @Log(title = "工作日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysWorkLog sysWorkLog) {
        List<SysWorkLog> list = sysWorkLogService.selectSysWorkLogList(sysWorkLog);
        ExcelUtil<SysWorkLog> util = new ExcelUtil<SysWorkLog>(SysWorkLog.class);
        return util.exportExcel(list, "log");
    }

    /**
     * 新增工作日志
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add" ;
    }

    /**
     * 新增保存工作日志
     */
    @RequiresPermissions("system:log:add")
    @Log(title = "工作日志", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysWorkLog sysWorkLog) {
        return toAjax(sysWorkLogService.insertSysWorkLog(sysWorkLog));
    }

    /**
     * 修改工作日志
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysWorkLog sysWorkLog = sysWorkLogService.selectSysWorkLogById(id);
        mmap.put("sysWorkLog", sysWorkLog);
        return prefix + "/edit" ;
    }

    /**
     * 修改保存工作日志
     */
    @RequiresPermissions("system:log:edit")
    @Log(title = "工作日志", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysWorkLog sysWorkLog) {
        return toAjax(sysWorkLogService.updateSysWorkLog(sysWorkLog));
    }

    /**
     * 删除工作日志
     */
    @RequiresPermissions("system:log:remove")
    @Log(title = "工作日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysWorkLogService.deleteSysWorkLogByIds(ids));
    }
}
