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
import com.ledao.system.dao.SysPccz;
import com.ledao.system.service.ISysPcczService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 破产网Controller
 *
 * @author lxz
 * @date 2022-10-18
 */
@Controller
@RequestMapping("/system/pccz")
public class SysPcczController extends BaseController {
    private String prefix = "system/pccz";

    @Autowired
    private ISysPcczService sysPcczService;

    @RequiresPermissions("system:pccz:view")
    @GetMapping()
    public String pccz() {
        return prefix + "/pccz";
    }

    /**
     * 查询破产网列表
     */
    @RequiresPermissions("system:pccz:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysPccz sysPccz) {
        startPage();
        List<SysPccz> list = sysPcczService.selectSysPcczList(sysPccz);
        return getDataTable(list);
    }

    /**
     * 导出破产网列表
     */
    @RequiresPermissions("system:pccz:export")
    @Log(title = "破产网", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysPccz sysPccz) {
        List<SysPccz> list = sysPcczService.selectSysPcczList(sysPccz);
        ExcelUtil<SysPccz> util = new ExcelUtil<SysPccz>(SysPccz.class);
        return util.exportExcel(list, "pccz");
    }

    /**
     * 新增破产网
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存破产网
     */
    @RequiresPermissions("system:pccz:add")
    @Log(title = "破产网", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysPccz sysPccz) {
        return toAjax(sysPcczService.insertSysPccz(sysPccz));
    }

    /**
     * 修改破产网
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysPccz sysPccz = sysPcczService.selectSysPcczById(id);
        mmap.put("sysPccz", sysPccz);
        return prefix + "/edit";
    }

    /**
     * 修改保存破产网
     */
    @RequiresPermissions("system:pccz:edit")
    @Log(title = "破产网", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysPccz sysPccz) {
        return toAjax(sysPcczService.updateSysPccz(sysPccz));
    }

    /**
     * 删除破产网
     */
    @RequiresPermissions("system:pccz:remove")
    @Log(title = "破产网", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysPcczService.deleteSysPcczByIds(ids));
    }
}
