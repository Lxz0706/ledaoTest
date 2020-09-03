package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.framework.util.ShiroUtils;
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
import com.ledao.system.domain.SysProjectRecovered;
import com.ledao.system.service.ISysProjectRecoveredService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.domain.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 已回收Controller
 *
 * @author ledao
 * @date 2020-09-03
 */
@Controller
@RequestMapping("/system/recovered")
public class SysProjectRecoveredController extends BaseController {
    private String prefix = "system/recovered";

    @Autowired
    private ISysProjectRecoveredService sysProjectRecoveredService;

    @RequiresPermissions("system:recovered:view")
    @GetMapping()
    public String recovered() {
        return prefix + "/recovered";
    }

    /**
     * 查询已回收列表
     */
    @RequiresPermissions("system:recovered:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysProjectRecovered sysProjectRecovered) {
        startPage();
        List<SysProjectRecovered> list = sysProjectRecoveredService.selectSysProjectRecoveredList(sysProjectRecovered);
        return getDataTable(list);
    }

    /**
     * 导出已回收列表
     */
    @RequiresPermissions("system:recovered:export")
    @Log(title = "已回收", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysProjectRecovered sysProjectRecovered) {
        List<SysProjectRecovered> list = sysProjectRecoveredService.selectSysProjectRecoveredList(sysProjectRecovered);
        ExcelUtil<SysProjectRecovered> util = new ExcelUtil<SysProjectRecovered>(SysProjectRecovered.class);
        return util.exportExcel(list, "recovered");
    }

    /**
     * 新增已回收
     */
    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") String id, ModelMap modelMap) {
        modelMap.put("projectManagementId", id);
        return prefix + "/add";
    }

    /**
     * 新增保存已回收
     */
    @RequiresPermissions("system:recovered:add")
    @Log(title = "已回收", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysProjectRecovered sysProjectRecovered) {
        sysProjectRecovered.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysProjectRecoveredService.insertSysProjectRecovered(sysProjectRecovered));
    }

    /**
     * 修改已回收
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysProjectRecovered sysProjectRecovered = sysProjectRecoveredService.selectSysProjectRecoveredById(id);
        mmap.put("sysProjectRecovered", sysProjectRecovered);
        return prefix + "/edit";
    }

    /**
     * 修改保存已回收
     */
    @RequiresPermissions("system:recovered:edit")
    @Log(title = "已回收", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProjectRecovered sysProjectRecovered) {
        sysProjectRecovered.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysProjectRecoveredService.updateSysProjectRecovered(sysProjectRecovered));
    }

    /**
     * 删除已回收
     */
    @RequiresPermissions("system:recovered:remove")
    @Log(title = "已回收", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysProjectRecoveredService.deleteSysProjectRecoveredByIds(ids));
    }

    @RequiresPermissions("system:projectmanagent:list")
    @GetMapping("/recoveredList/{projectManagementId}")
    public String selectRecoveredListByProjectId(@PathVariable("projectManagementId") String projectManagementId, ModelMap modelMap) {
        modelMap.put("projectManagementId", projectManagementId);
        return "system/recovered/recovered";
    }
}
