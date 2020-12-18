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
import com.ledao.system.dao.SysRecapture;
import com.ledao.system.service.ISysRecaptureService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 投后项目现金回现Controller
 *
 * @author lxz
 * @date 2020-11-23
 */
@Controller
@RequestMapping("/system/recapture")
public class SysRecaptureController extends BaseController {
    private String prefix = "system/recapture";

    @Autowired
    private ISysRecaptureService sysRecaptureService;

    @RequiresPermissions("system:recapture:view")
    @GetMapping()
    public String recapture() {
        return prefix + "/recapture";
    }

    /**
     * 查询投后项目现金回现列表
     */
    @RequiresPermissions("system:recapture:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysRecapture sysRecapture) {
        startPage();
        List<SysRecapture> list = sysRecaptureService.selectSysRecaptureList(sysRecapture);
        return getDataTable(list);
    }

    /**
     * 根据项目id查询列表
     * */
    @RequiresPermissions("system:recapture:list")
    @GetMapping("/recaptureList/{projectId}")
    public String select(@PathVariable("projectId") String projectId, ModelMap modelMap) {
        modelMap.put("projectId", projectId);
        return "system/recapture/recapture";
    }

    /**
     * 导出投后项目现金回现列表
     */
    @RequiresPermissions("system:recapture:export")
    @Log(title = "投后项目现金回现", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysRecapture sysRecapture) {
        List<SysRecapture> list = sysRecaptureService.selectSysRecaptureList(sysRecapture);
        ExcelUtil<SysRecapture> util = new ExcelUtil<SysRecapture>(SysRecapture.class);
        return util.exportExcel(list, "recapture");
    }

    /**
     * 新增投后项目现金回现
     */
    @GetMapping("/add/{projectId}")
    public String add(@PathVariable("projectId") String projectId, ModelMap modelMap) {
        modelMap.put("projectId", projectId);
        return prefix + "/add";
    }

    /**
     * 新增保存投后项目现金回现
     */
    @RequiresPermissions("system:recapture:add")
    @Log(title = "投后项目现金回现", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysRecapture sysRecapture) {
        return toAjax(sysRecaptureService.insertSysRecapture(sysRecapture));
    }

    /**
     * 修改投后项目现金回现
     */
    @GetMapping("/edit/{recaptureId}")
    public String edit(@PathVariable("recaptureId") Long recaptureId, ModelMap mmap) {
        SysRecapture sysRecapture = sysRecaptureService.selectSysRecaptureById(recaptureId);
        mmap.put("sysRecapture", sysRecapture);
        return prefix + "/edit";
    }

    /**
     * 修改保存投后项目现金回现
     */
    @RequiresPermissions("system:recapture:edit")
    @Log(title = "投后项目现金回现", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysRecapture sysRecapture) {
        return toAjax(sysRecaptureService.updateSysRecapture(sysRecapture));
    }

    /**
     * 删除投后项目现金回现
     */
    @RequiresPermissions("system:recapture:remove")
    @Log(title = "投后项目现金回现", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysRecaptureService.deleteSysRecaptureByIds(ids));
    }
}
