package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.system.domain.SysJudicial;
import com.ledao.system.service.ISysJudicialService;
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
 * 司法Controller
 *
 * @author lxz
 * @date 2020-06-09
 */
@Controller
@RequestMapping("/system/judicial")
public class SysJudicialController extends BaseController {
    private String prefix = "system/judicial" ;

    @Autowired
    private ISysJudicialService sysJudicialService;

    @RequiresPermissions("system:judicial:view")
    @GetMapping()
    public String judicial() {
        return prefix + "/judicial" ;
    }

    /**
     * 查询司法列表
     */
    @RequiresPermissions("system:judicial:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysJudicial sysJudicial) {
        startPage();
        List<SysJudicial> list = sysJudicialService.selectSysJudicialList(sysJudicial);
        return getDataTable(list);
    }

    /**
     * 导出司法列表
     */
    @RequiresPermissions("system:judicial:export")
    @Log(title = "司法", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysJudicial sysJudicial) {
        List<SysJudicial> list = sysJudicialService.selectSysJudicialList(sysJudicial);
        ExcelUtil<SysJudicial> util = new ExcelUtil<SysJudicial>(SysJudicial.class);
        return util.exportExcel(list, "judicial");
    }

    /**
     * 新增司法
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add" ;
    }

    /**
     * 新增保存司法
     */
    @RequiresPermissions("system:judicial:add")
    @Log(title = "司法", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysJudicial sysJudicial) {
        return toAjax(sysJudicialService.insertSysJudicial(sysJudicial));
    }

    /**
     * 修改司法
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysJudicial sysJudicial = sysJudicialService.selectSysJudicialById(id);
        mmap.put("sysJudicial", sysJudicial);
        return prefix + "/edit" ;
    }

    /**
     * 修改保存司法
     */
    @RequiresPermissions("system:judicial:edit")
    @Log(title = "司法", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysJudicial sysJudicial) {
        return toAjax(sysJudicialService.updateSysJudicial(sysJudicial));
    }

    /**
     * 删除司法
     */
    @RequiresPermissions("system:judicial:remove")
    @Log(title = "司法", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysJudicialService.deleteSysJudicialByIds(ids));
    }
}
