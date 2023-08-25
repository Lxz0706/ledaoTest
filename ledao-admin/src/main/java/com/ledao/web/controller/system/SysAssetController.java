package com.ledao.web.controller.system;

import com.ledao.common.annotation.Log;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.core.page.TableDataInfo;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.system.dao.SysAsset;
import com.ledao.system.service.ISysAssetService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资产管理Controller
 *
 * @author lxz
 * @date 2023-06-16
 */
@Controller
@RequestMapping("/system/asset")
public class SysAssetController extends BaseController {
    private String prefix = "system/asset";

    @Autowired
    private ISysAssetService sysAssetService;

    @RequiresPermissions("system:asset:view")
    @GetMapping()
    public String asset() {
        return prefix + "/asset";
    }

    /**
     * 查询资产管理列表
     */
    @RequiresPermissions("system:asset:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysAsset sysAsset) {
        startPage();
        List<SysAsset> list = sysAssetService.selectSysAssetList(sysAsset);
        return getDataTable(list);
    }

    /**
     * 导出资产管理列表
     */
    @RequiresPermissions("system:asset:export")
    @Log(title = "资产管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysAsset sysAsset) {
        List<SysAsset> list = sysAssetService.selectSysAssetList(sysAsset);
        ExcelUtil<SysAsset> util = new ExcelUtil<SysAsset>(SysAsset.class);
        return util.exportExcel(list, "asset");
    }

    /**
     * 新增资产管理
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存资产管理
     */
    @RequiresPermissions("system:asset:add")
    @Log(title = "资产管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysAsset sysAsset) {
        return toAjax(sysAssetService.insertSysAsset(sysAsset));
    }

    /**
     * 修改资产管理
     */
    @GetMapping("/edit/{assetId}")
    public String edit(@PathVariable("assetId") Long assetId, ModelMap mmap) {
        SysAsset sysAsset = sysAssetService.selectSysAssetById(assetId);
        mmap.put("sysAsset", sysAsset);
        return prefix + "/edit";
    }

    /**
     * 修改保存资产管理
     */
    @RequiresPermissions("system:asset:edit")
    @Log(title = "资产管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysAsset sysAsset) {
        return toAjax(sysAssetService.updateSysAsset(sysAsset));
    }

    /**
     * 删除资产管理
     */
    @RequiresPermissions("system:asset:remove")
    @Log(title = "资产管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysAssetService.deleteSysAssetByIds(ids));
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("sysAsset", sysAssetService.selectSysAssetById(id));
        return prefix + "/detail";
    }

    @GetMapping("/queryAll")
    public String queryAll(ModelMap modelMap, Long assetId) {
        modelMap.put("assetId", assetId);
        return prefix + "/queryAll";
    }
}
