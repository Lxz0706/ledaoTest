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
import com.ledao.system.dao.SysPcustomer;
import com.ledao.system.service.ISysPcustomerService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 项目客户Controller
 *
 * @author lxz
 * @date 2021-02-02
 */
@Controller
@RequestMapping("/system/pcustomer")
public class SysPcustomerController extends BaseController {
    private String prefix = "system/pcustomer";

    @Autowired
    private ISysPcustomerService sysPcustomerService;

    @RequiresPermissions("system:pcustomer:view")
    @GetMapping()
    public String pcustomer() {
        return prefix + "/pcustomer";
    }

    /**
     * 查询项目客户列表
     */
    @RequiresPermissions("system:pcustomer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysPcustomer sysPcustomer) {
        startPage();
        List<SysPcustomer> list = sysPcustomerService.selectSysPcustomerList(sysPcustomer);
        return getDataTable(list);
    }

    /**
     * 导出项目客户列表
     */
    @RequiresPermissions("system:pcustomer:export")
    @Log(title = "项目客户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysPcustomer sysPcustomer) {
        List<SysPcustomer> list = sysPcustomerService.selectSysPcustomerList(sysPcustomer);
        ExcelUtil<SysPcustomer> util = new ExcelUtil<SysPcustomer>(SysPcustomer.class);
        return util.exportExcel(list, "pcustomer");
    }

    /**
     * 新增项目客户
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存项目客户
     */
    @RequiresPermissions("system:pcustomer:add")
    @Log(title = "项目客户", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysPcustomer sysPcustomer) {
        return toAjax(sysPcustomerService.insertSysPcustomer(sysPcustomer));
    }

    /**
     * 修改项目客户
     */
    @GetMapping("/edit/{dealCustomerId}")
    public String edit(@PathVariable("dealCustomerId") Long dealCustomerId, ModelMap mmap) {
        SysPcustomer sysPcustomer = sysPcustomerService.selectSysPcustomerById(dealCustomerId);
        mmap.put("sysPcustomer", sysPcustomer);
        return prefix + "/edit";
    }

    /**
     * 修改保存项目客户
     */
    @RequiresPermissions("system:pcustomer:edit")
    @Log(title = "项目客户", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysPcustomer sysPcustomer) {
        return toAjax(sysPcustomerService.updateSysPcustomer(sysPcustomer));
    }

    /**
     * 删除项目客户
     */
    @RequiresPermissions("system:pcustomer:remove")
    @Log(title = "项目客户", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysPcustomerService.deleteSysPcustomerByIds(ids));
    }
}
