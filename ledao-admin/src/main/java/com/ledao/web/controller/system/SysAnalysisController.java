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
import com.ledao.system.dao.SysAnalysis;
import com.ledao.system.service.ISysAnalysisService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 系统模块分析Controller
 *
 * @author lxz
 * @date 2021-04-06
 */
@Controller
@RequestMapping("/system/analysis")
public class SysAnalysisController extends BaseController {
    private String prefix = "system/analysis";

    @Autowired
    private ISysAnalysisService sysAnalysisService;

    @RequiresPermissions("system:analysis:view")
    @GetMapping()
    public String analysis() {
        return prefix + "/analysis";
    }

    /**
     * 查询系统模块分析列表
     */
    @RequiresPermissions("system:analysis:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysAnalysis sysAnalysis) {
        startPage();
        List<SysAnalysis> list = sysAnalysisService.selectSysAnalysisList(sysAnalysis);
        return getDataTable(list);
    }

    /**
     * 导出系统模块分析列表
     */
    @RequiresPermissions("system:analysis:export")
    @Log(title = "系统模块分析", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysAnalysis sysAnalysis) {
        List<SysAnalysis> list = sysAnalysisService.selectSysAnalysisList(sysAnalysis);
        ExcelUtil<SysAnalysis> util = new ExcelUtil<SysAnalysis>(SysAnalysis.class);
        return util.exportExcel(list, "analysis");
    }

    /**
     * 新增系统模块分析
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存系统模块分析
     */
    @RequiresPermissions("system:analysis:add")
    @Log(title = "系统模块分析", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysAnalysis sysAnalysis) {
        return toAjax(sysAnalysisService.insertSysAnalysis(sysAnalysis));
    }

    /**
     * 修改系统模块分析
     */
    @GetMapping("/edit/{analysisId}")
    public String edit(@PathVariable("analysisId") Long analysisId, ModelMap mmap) {
        SysAnalysis sysAnalysis = sysAnalysisService.selectSysAnalysisById(analysisId);
        mmap.put("sysAnalysis", sysAnalysis);
        return prefix + "/edit";
    }

    /**
     * 修改保存系统模块分析
     */
    @RequiresPermissions("system:analysis:edit")
    @Log(title = "系统模块分析", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysAnalysis sysAnalysis) {
        return toAjax(sysAnalysisService.updateSysAnalysis(sysAnalysis));
    }

    /**
     * 删除系统模块分析
     */
    @RequiresPermissions("system:analysis:remove")
    @Log(title = "系统模块分析", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysAnalysisService.deleteSysAnalysisByIds(ids));
    }
}
