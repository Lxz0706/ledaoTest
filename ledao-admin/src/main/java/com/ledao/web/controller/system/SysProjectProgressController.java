package com.ledao.web.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.ledao.common.utils.StringUtils;
import com.ledao.system.dao.SysProject;
import com.ledao.system.dao.SysProjectmanagent;
import com.ledao.system.service.ISysProjectService;
import com.ledao.system.service.ISysProjectZckService;
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
import com.ledao.system.dao.SysProjectProgress;
import com.ledao.system.service.ISysProjectProgressService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 *
 * @author ledao
 * @date 2020-08-26
 */
@Controller
@RequestMapping("/system/progress")
public class SysProjectProgressController extends BaseController {
    private String prefix = "system/progress";

    @Autowired
    private ISysProjectProgressService sysProjectProgressService;

    @Autowired
    private ISysProjectmanagentService sysProjectmanagentService;

    @Autowired
    private ISysProjectService sysProjectService;

    @Autowired
    private ISysProjectZckService sysProjectZckService;

    @RequiresPermissions("system:progress:view")
    @GetMapping()
    public String progress() {
        return prefix + "/progress";
    }

    @RequiresPermissions("system:projectmanagent:list")
    @GetMapping("/progressList/{projectManagementId}")
    public String selectProjectProgressByProjectId(@PathVariable("projectManagementId") String projectManagementId, ModelMap modelMap) {
        modelMap.put("projectManagementId", projectManagementId);
        return "system/progress/progress";
    }

    @RequiresPermissions("system:projectmanagent:list")
    @GetMapping("/progressLists/{projectManagementId}/{project}")
    public String selectProjectProgressByProjectIds(@PathVariable("projectManagementId") String projectManagementId, @PathVariable("project") String project, ModelMap modelMap) {
        modelMap.put("projectManagementId", projectManagementId);
        modelMap.put("project", project);
        if ("Y".equals(project)) {
            modelMap.put("projectZckId", sysProjectService.selectSysProjectById(Long.valueOf(projectManagementId)).getProjectZckId());
            modelMap.put("projectZckName", sysProjectZckService.selectSysProjectZckById(sysProjectService.selectSysProjectById(Long.valueOf(projectManagementId)).getProjectZckId()).getZckName());
        }else{
            modelMap.put("type", sysProjectmanagentService.selectSysProjectmanagentById(Long.valueOf(projectManagementId)).getProjectType());
        }
        return "system/progress/progress";
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("system:progress:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysProjectProgress sysProjectProgress) {
        startPage();
        List<SysProjectProgress> list = new ArrayList<>();
        SysProjectmanagent sysProjectmanagent = new SysProjectmanagent();
        SysProject sysProject = new SysProject();
        if (StringUtils.isNotNull(sysProjectProgress.getProjectManagementId())) {
            if (StringUtils.isNotNull(sysProjectProgress.getProject())) {
                if ("Y".equals(sysProjectProgress.getProject())) {
                    sysProject = sysProjectService.selectSysProjectById(sysProjectProgress.getProjectManagementId());
                    list = sysProjectProgressService.selectSysProjectProgressList(sysProjectProgress);
                    for (SysProjectProgress sysProjectProgress1 : list) {
                        if (StringUtils.isNotEmpty(sysProject.getProjectName())) {
                            sysProjectProgress1.setProjectManagementName(sysProject.getProjectName());
                        }
                    }
                } else {
                    sysProjectmanagent = sysProjectmanagentService.selectSysProjectmanagentById(sysProjectProgress.getProjectManagementId());
                    list = sysProjectProgressService.selectSysProjectProgressList(sysProjectProgress);
                    for (SysProjectProgress sysProjectProgress1 : list) {
                        if (StringUtils.isNotEmpty(sysProjectmanagent.getProjectManagementName())) {
                            sysProjectProgress1.setProjectManagementName(sysProjectmanagent.getProjectManagementName());
                        }
                    }
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("system:progress:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysProjectProgress sysProjectProgress) {
        List<SysProjectProgress> list = sysProjectProgressService.selectSysProjectProgressList(sysProjectProgress);
        ExcelUtil<SysProjectProgress> util = new ExcelUtil<SysProjectProgress>(SysProjectProgress.class);
        return util.exportExcel(list, "progress");
    }

    /*    *//**
     * 新增【请填写功能名称】
     *//*
    @GetMapping("/add/{projectManagementId}")
    public String add(@PathVariable("projectManagementId") String projectManagementId, ModelMap modelMap) {
        logger.info("111111111");
        modelMap.put("projectManagementId", projectManagementId);
        return prefix + "/add";
    }*/

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/adds/{projectManagementId}/{project}")
    public String adds(@PathVariable("projectManagementId") String projectManagementId, @PathVariable("project") String project, ModelMap modelMap) {
        modelMap.put("projectManagementId", projectManagementId);
        modelMap.put("project", project);
        return prefix + "/add";
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @RequiresPermissions("system:progress:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysProjectProgress sysProjectProgress) {
        return toAjax(sysProjectProgressService.insertSysProjectProgress(sysProjectProgress));
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{progressId}")
    public String edit(@PathVariable("progressId") Long progressId, ModelMap mmap) {
        SysProjectProgress sysProjectProgress = sysProjectProgressService.selectSysProjectProgressById(progressId);
        mmap.put("sysProjectProgress", sysProjectProgress);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @RequiresPermissions("system:progress:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProjectProgress sysProjectProgress) {
        return toAjax(sysProjectProgressService.updateSysProjectProgress(sysProjectProgress));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("system:progress:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysProjectProgressService.deleteSysProjectProgressByIds(ids));
    }
}
