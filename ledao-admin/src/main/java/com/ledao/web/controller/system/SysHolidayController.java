package com.ledao.web.controller.system;

import java.util.List;

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
import com.ledao.system.dao.SysHoliday;
import com.ledao.system.service.ISysHolidayService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 节假日Controller
 *
 * @author lxz
 * @date 2022-11-01
 */
@Controller
@RequestMapping("/system/holiday")
public class SysHolidayController extends BaseController {
    private String prefix = "system/holiday";

    @Autowired
    private ISysHolidayService sysHolidayService;

    @RequiresPermissions("system:holiday:view")
    @GetMapping()
    public String holiday() {
        return prefix + "/holiday";
    }

    /**
     * 查询节假日列表
     */
    @RequiresPermissions("system:holiday:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysHoliday sysHoliday) {
        startPage();
        List<SysHoliday> list = sysHolidayService.selectSysHolidayList(sysHoliday);
        return getDataTable(list);
    }

    /**
     * 导出节假日列表
     */
    @RequiresPermissions("system:holiday:export")
    @Log(title = "节假日", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysHoliday sysHoliday) {
        List<SysHoliday> list = sysHolidayService.selectSysHolidayList(sysHoliday);
        ExcelUtil<SysHoliday> util = new ExcelUtil<SysHoliday>(SysHoliday.class);
        return util.exportExcel(list, "holiday");
    }

    /**
     * 新增节假日
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存节假日
     */
    @RequiresPermissions("system:holiday:add")
    @Log(title = "节假日", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysHoliday sysHoliday) {
        return toAjax(sysHolidayService.insertSysHoliday(sysHoliday));
    }

    /**
     * 修改节假日
     */
    @GetMapping("/edit/{holiday}")
    public String edit(@PathVariable("holiday") String holiday, ModelMap mmap) {
        SysHoliday sysHoliday = sysHolidayService.selectSysHolidayById(holiday);
        mmap.put("sysHoliday", sysHoliday);
        return prefix + "/edit";
    }

    /**
     * 修改保存节假日
     */
    @RequiresPermissions("system:holiday:edit")
    @Log(title = "节假日", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysHoliday sysHoliday) {
        return toAjax(sysHolidayService.updateSysHoliday(sysHoliday));
    }

    /**
     * 删除节假日
     */
    @RequiresPermissions("system:holiday:remove")
    @Log(title = "节假日", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysHolidayService.deleteSysHolidayByIds(ids));
    }
}
