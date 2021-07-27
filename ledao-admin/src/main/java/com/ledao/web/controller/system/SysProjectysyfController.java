package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.service.ISysProjectmanagentService;
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
import com.ledao.system.dao.SysProjectysyf;
import com.ledao.system.service.ISysProjectysyfService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 流转已收已付Controller
 *
 * @author ledao
 * @date 2020-08-31
 */
@Controller
@RequestMapping("/system/projectysyf")
public class SysProjectysyfController extends BaseController {
    private String prefix = "system/projectysyf";

    @Autowired
    private ISysProjectysyfService sysProjectysyfService;

    @Autowired
    private ISysProjectmanagentService sysProjectmanagentService;

    @RequiresPermissions("system:projectysyf:view")
    @GetMapping()
    public String projectysyf() {
        return prefix + "/projectysyf";
    }

    /**
     * 查询流转已收已付列表
     */
    @RequiresPermissions("system:projectysyf:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysProjectysyf sysProjectysyf) {
        startPage();
        List<SysProjectysyf> list = sysProjectysyfService.selectSysProjectysyfList(sysProjectysyf);
        for (SysProjectysyf sysProjectysyf1 : list) {
            if (StringUtils.isNotEmpty(sysProjectysyf1.getImgUrl())) {
                sysProjectysyf1.setImgFlag(true);
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出流转已收已付列表
     */
    @RequiresPermissions("system:projectysyf:export")
    @Log(title = "流转已收已付", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysProjectysyf sysProjectysyf) {
        List<SysProjectysyf> list = sysProjectysyfService.selectSysProjectysyfList(sysProjectysyf);
        ExcelUtil<SysProjectysyf> util = new ExcelUtil<SysProjectysyf>(SysProjectysyf.class);
        return util.exportExcel(list, "projectysyf");
    }

    /**
     * 新增流转已收已付
     */
    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") String id, ModelMap modelMap) {
        modelMap.put("projectManagementId", id);
        return prefix + "/add";
    }

    /**
     * 新增保存流转已收已付
     */
    @RequiresPermissions("system:projectysyf:add")
    @Log(title = "流转已收已付", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysProjectysyf sysProjectysyf) {
        sysProjectysyf.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysProjectysyfService.insertSysProjectysyf(sysProjectysyf));
    }

    /**
     * 修改流转已收已付
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysProjectysyf sysProjectysyf = sysProjectysyfService.selectSysProjectysyfById(id);
        mmap.put("sysProjectysyf", sysProjectysyf);
        return prefix + "/edit";
    }

    /**
     * 修改保存流转已收已付
     */
    @RequiresPermissions("system:projectysyf:edit")
    @Log(title = "流转已收已付", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProjectysyf sysProjectysyf) {
        return toAjax(sysProjectysyfService.updateSysProjectysyf(sysProjectysyf));
    }

    /**
     * 删除流转已收已付
     */
    @RequiresPermissions("system:projectysyf:remove")
    @Log(title = "流转已收已付", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysProjectysyfService.deleteSysProjectysyfByIds(ids));
    }

    @RequiresPermissions("system:projectmanagent:list")
    @GetMapping("/projectysyfList/{projectManagementId}")
    public String selectProjectYsyfListByProjectId(@PathVariable("projectManagementId") String projectManagementId, ModelMap modelMap) {
        modelMap.put("projectManagementId", projectManagementId);
        modelMap.put("type", sysProjectmanagentService.selectSysProjectmanagentById(Long.valueOf(projectManagementId)).getProjectType());
        return "system/projectysyf/projectysyf";
    }

    /**
     * 修改头像
     */
    @GetMapping("/imgUrl/{id}")
    public String avatar(@PathVariable("id") String id, ModelMap mmap) {
        mmap.put("sysProjectysyf", sysProjectysyfService.selectSysProjectysyfById(Long.valueOf(id)));
        return prefix + "/imgUrl";
    }

    @PostMapping("/imgUrlList/{id}")
    @ResponseBody
    public AjaxResult imgUrlList(@PathVariable("id") String id) {
        SysProjectysyf sysProjectysyf = sysProjectysyfService.selectSysProjectysyfById(Long.valueOf(id));
        if (StringUtils.isNotEmpty(sysProjectysyf.getImgUrl())) {
            return AjaxResult.success(sysProjectysyf.getImgUrl().split(";"));
        } else {
            return AjaxResult.success();
        }
    }
}
