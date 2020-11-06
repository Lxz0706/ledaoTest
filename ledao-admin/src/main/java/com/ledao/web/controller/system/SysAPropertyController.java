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
import com.ledao.system.dao.SysAProperty;
import com.ledao.system.service.ISysAPropertyService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 *
 * @author lxz
 * @date 2020-10-20
 */
@Controller
@RequestMapping("/system/property")
public class SysAPropertyController extends BaseController {
    private String prefix = "system/property";

    @Autowired
    private ISysAPropertyService sysAPropertyService;

    @RequiresPermissions("system:property:view")
    @GetMapping()
    public String property() {
        return prefix + "/property";
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("system:property:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysAProperty sysAProperty) {
        startPage();
        List<SysAProperty> list = sysAPropertyService.selectSysAPropertyList(sysAProperty);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("system:property:export")
    @Log(title = "【请填写功能名称】" , businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysAProperty sysAProperty) {
        List<SysAProperty> list = sysAPropertyService.selectSysAPropertyList(sysAProperty);
        ExcelUtil<SysAProperty> util = new ExcelUtil<SysAProperty>(SysAProperty.class);
        return util.exportExcel(list, "property");
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
    @RequiresPermissions("system:property:add")
    @Log(title = "【请填写功能名称】" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysAProperty sysAProperty) {
        return toAjax(sysAPropertyService.insertSysAProperty(sysAProperty));
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysAProperty sysAProperty = sysAPropertyService.selectSysAPropertyById(id);
        mmap.put("sysAProperty" , sysAProperty);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @RequiresPermissions("system:property:edit")
    @Log(title = "【请填写功能名称】" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysAProperty sysAProperty) {
        return toAjax(sysAPropertyService.updateSysAProperty(sysAProperty));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("system:property:remove")
    @Log(title = "【请填写功能名称】" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysAPropertyService.deleteSysAPropertyByIds(ids));
    }
}
