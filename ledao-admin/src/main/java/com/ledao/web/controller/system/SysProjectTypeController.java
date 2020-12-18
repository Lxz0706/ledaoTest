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
import com.ledao.system.dao.SysProjectType;
import com.ledao.system.service.ISysProjectTypeService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 *
 * @author lxz
 * @date 2020-12-15
 */
@Controller
@RequestMapping("/system/type")
public class SysProjectTypeController extends BaseController {
    private String prefix = "system/type";

    @Autowired
    private ISysProjectTypeService sysProjectTypeService;

    @RequiresPermissions("system:type:view")
    @GetMapping()
    public String type() {
        return prefix + "/type";
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("system:type:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysProjectType sysProjectType) {
        startPage();
        List<SysProjectType> list = sysProjectTypeService.selectSysProjectTypeList(sysProjectType);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("system:type:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysProjectType sysProjectType) {
        List<SysProjectType> list = sysProjectTypeService.selectSysProjectTypeList(sysProjectType);
        ExcelUtil<SysProjectType> util = new ExcelUtil<SysProjectType>(SysProjectType.class);
        return util.exportExcel(list, "type");
    }

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @RequiresPermissions("system:type:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysProjectType sysProjectType) {
        return toAjax(sysProjectTypeService.insertSysProjectType(sysProjectType));
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{projectType}")
    public String edit(@PathVariable("projectType") String projectType, ModelMap mmap) {
        SysProjectType sysProjectType = sysProjectTypeService.selectSysProjectTypeById(projectType);
        mmap.put("sysProjectType", sysProjectType);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @RequiresPermissions("system:type:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProjectType sysProjectType) {
        return toAjax(sysProjectTypeService.updateSysProjectType(sysProjectType));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("system:type:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysProjectTypeService.deleteSysProjectTypeByIds(ids));
    }
}
