package com.ledao.web.controller.system;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ledao.common.annotation.Log;
import com.ledao.common.enums.BusinessType;
import com.ledao.system.dao.SysMemorandum;
import com.ledao.system.service.ISysMemorandumService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 备忘录Controller
 *
 * @author lxz
 * @date 2021-03-04
 */
@Controller
@RequestMapping("/system/memorandum")
public class SysMemorandumController extends BaseController {
    private String prefix = "system/memorandum";

    @Autowired
    private ISysMemorandumService sysMemorandumService;

    @RequiresPermissions("system:memorandum:view")
    @GetMapping()
    public String memorandum() {
        return prefix + "/memorandum";
    }

    /**
     * 查询备忘录列表
     */
    @RequiresPermissions("system:memorandum:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysMemorandum sysMemorandum) {
        startPage();
        List<SysMemorandum> list = sysMemorandumService.selectSysMemorandumList(sysMemorandum);
        return getDataTable(list);
    }

    /**
     * 导出备忘录列表
     */
    @RequiresPermissions("system:memorandum:export")
    @Log(title = "备忘录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysMemorandum sysMemorandum) {
        List<SysMemorandum> list = sysMemorandumService.selectSysMemorandumList(sysMemorandum);
        ExcelUtil<SysMemorandum> util = new ExcelUtil<SysMemorandum>(SysMemorandum.class);
        return util.exportExcel(list, "memorandum");
    }

    /**
     * 新增备忘录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存备忘录
     */
    @RequiresPermissions("system:memorandum:add")
    @Log(title = "备忘录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysMemorandum sysMemorandum) {
        return toAjax(sysMemorandumService.insertSysMemorandum(sysMemorandum));
    }

    /**
     * 修改备忘录
     */
    @GetMapping("/edit/{memorandumId}")
    public String edit(@PathVariable("memorandumId") Long memorandumId, ModelMap mmap) {
        SysMemorandum sysMemorandum = sysMemorandumService.selectSysMemorandumById(memorandumId);
        mmap.put("sysMemorandum", sysMemorandum);
        return prefix + "/edit";
    }

    /**
     * 修改保存备忘录
     */
    @RequiresPermissions("system:memorandum:edit")
    @Log(title = "备忘录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysMemorandum sysMemorandum) {
        return toAjax(sysMemorandumService.updateSysMemorandum(sysMemorandum));
    }

    /**
     * 删除备忘录
     */
    @RequiresPermissions("system:memorandum:remove")
    @Log(title = "备忘录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysMemorandumService.deleteSysMemorandumByIds(ids));
    }

    /**
     * 主页功能区
     */
    @PostMapping("/selectForByMain")
    @ResponseBody
    public AjaxResult selectForMain(SysMemorandum sysMemorandum) {
        /*sysMemorandum.setStartTime(startTime);
        sysMemorandum.setEndTime(DateUtils.parseDate(endTime));*/
        List<SysMemorandum> sysMemorandumList = sysMemorandumService.selectSysMemorandumList(sysMemorandum);
        return AjaxResult.success(sysMemorandumList);
    }

    @PostMapping("/updatePlan/{memorandumId}/{startTime}/{endTime}")
    @ResponseBody
    public AjaxResult updatePlan(@PathVariable("memorandumId") Long memorandumId, @PathVariable("startTime") Date startTime, @PathVariable("endTime") Date endTime) {
        SysMemorandum sysMemorandum = sysMemorandumService.selectSysMemorandumById(memorandumId);
        sysMemorandum.setStartTime(startTime);
        if (StringUtils.isNotNull(endTime)) {
            sysMemorandum.setEndTime(endTime);
        }
        return toAjax(sysMemorandumService.updateSysMemorandum(sysMemorandum));
    }
}
