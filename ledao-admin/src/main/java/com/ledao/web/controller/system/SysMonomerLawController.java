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
import com.ledao.system.dao.SysMonomerLaw;
import com.ledao.system.service.ISysMonomerLawService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 项目法律信息Controller
 *
 * @author lxz
 * @date 2021-08-23
 */
@Controller
@RequestMapping("/system/monomerLaw")
public class SysMonomerLawController extends BaseController {
    private String prefix = "system/monomerLaw";

    @Autowired
    private ISysMonomerLawService sysMonomerLawService;

    @GetMapping("/toMonomerLaw")
    public String toMonomerLaw(Long projectId, String projectType, ModelMap modelMap) {
        logger.info("进来了！！！！=========" + projectId);
        modelMap.put("projectId", projectId);
        modelMap.put("projectType", projectType);
        return prefix + "/monomerLaw";
    }

    @RequiresPermissions("system:monomerLaw:view")
    @GetMapping()
    public String monomerLaw() {
        return prefix + "/monomerLaw";
    }

    /**
     * 查询项目法律信息列表
     */
    @RequiresPermissions("system:monomerLaw:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysMonomerLaw sysMonomerLaw) {
        startPage();
        List<SysMonomerLaw> list = sysMonomerLawService.selectSysMonomerLawList(sysMonomerLaw);
        return getDataTable(list);
    }

    /**
     * 导出项目法律信息列表
     */
    @RequiresPermissions("system:monomerLaw:export")
    @Log(title = "项目法律信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysMonomerLaw sysMonomerLaw) {
        List<SysMonomerLaw> list = sysMonomerLawService.selectSysMonomerLawList(sysMonomerLaw);
        ExcelUtil<SysMonomerLaw> util = new ExcelUtil<SysMonomerLaw>(SysMonomerLaw.class);
        return util.exportExcel(list, "monomerLaw");
    }

    /**
     * 新增项目法律信息
     */
    @GetMapping("/add")
    public String add(Long projectId, String projectType, ModelMap modelMap) {
        modelMap.put("projectId", projectId);
        modelMap.put("projectType", projectType);
        return prefix + "/add";
    }

    /**
     * 新增保存项目法律信息
     */
    @RequiresPermissions("system:monomerLaw:add")
    @Log(title = "项目法律信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysMonomerLaw sysMonomerLaw) {
        return toAjax(sysMonomerLawService.insertSysMonomerLaw(sysMonomerLaw));
    }

    /**
     * 修改项目法律信息
     */
    @GetMapping("/edit/{monomerLawId}")
    public String edit(@PathVariable("monomerLawId") Long monomerLawId, ModelMap mmap) {
        SysMonomerLaw sysMonomerLaw = sysMonomerLawService.selectSysMonomerLawById(monomerLawId);
        mmap.put("sysMonomerLaw", sysMonomerLaw);
        return prefix + "/edit";
    }

    /**
     * 修改保存项目法律信息
     */
    @RequiresPermissions("system:monomerLaw:edit")
    @Log(title = "项目法律信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysMonomerLaw sysMonomerLaw) {
        return toAjax(sysMonomerLawService.updateSysMonomerLaw(sysMonomerLaw));
    }

    /**
     * 删除项目法律信息
     */
    @RequiresPermissions("system:monomerLaw:remove")
    @Log(title = "项目法律信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysMonomerLawService.deleteSysMonomerLawByIds(ids));
    }
}
