package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.common.utils.StringUtils;
import com.ledao.system.dao.*;
import com.ledao.system.service.ISysBgczzckService;
import com.ledao.system.service.ISysProjectService;
import com.ledao.system.service.ISysZckService;
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
import com.ledao.system.service.ISysMonomerLawService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;
import oshi.util.StringUtil;

/**
 * 项目法律信息Controller
 *
 * @author lxz
 * @date 2021-08-23
 */
@Controller
@RequestMapping("/system/monomerLaw")
public class SysMonomerLawController extends BaseController {
    private String prefix = "system/monomerLaw";

    @Autowired
    private ISysMonomerLawService sysMonomerLawService;

    @Autowired
    private ISysZckService sysZckService;

    @Autowired
    private ISysBgczzckService sysBgczzckService;

    @Autowired
    private ISysProjectService sysProjectService;

    @GetMapping("/toMonomerLaw")
    public String toMonomerLaw(Long projectId, String projectType, String projectStatus, ModelMap modelMap) {
        logger.info("123123");
        modelMap.put("projectId", projectId);
        modelMap.put("projectType", projectType);
        modelMap.put("projectStatus", projectStatus);
        return prefix + "/monomerLaw";
    }

    @RequiresPermissions("system:monomerLaw:view")
    @GetMapping()
    public String monomerLaw() {
        return prefix + "/monomerLaw";
    }

    /**
     * 查询项目法律信息列表
     */
    @RequiresPermissions("system:monomerLaw:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysMonomerLaw sysMonomerLaw) {
        startPage();
        List<SysMonomerLaw> list = sysMonomerLawService.selectSysMonomerLawList(sysMonomerLaw);
        for (SysMonomerLaw sysMonomerLaw1 : list) {
            String projectName = "";
            if (StringUtils.isNotNull(sysMonomerLaw1.getProjectId()) && StringUtils.isNotNull(sysMonomerLaw1.getProjectType())) {
                if ("1".equals(sysMonomerLaw1.getProjectType())) {
                    SysZck sysZck = sysZckService.selectSysZckById(sysMonomerLaw1.getProjectId());
                    if (StringUtils.isNotNull(sysZck) && StringUtils.isNotEmpty(sysZck.getProjectName())) {
                        projectName = sysZck.getProjectName();
                    }
                } else if ("2".equals(sysMonomerLaw1.getProjectType())) {
                    SysBgczzck sysBgczzck = sysBgczzckService.selectSysBgczzckById(sysMonomerLaw1.getProjectId());
                    if (StringUtils.isNotNull(sysBgczzck) && StringUtils.isNotEmpty(sysBgczzck.getProjectName())) {
                        projectName = sysBgczzck.getProjectName();
                    }
                } else if ("3".equals(sysMonomerLaw1.getProjectType())) {
                    SysProject sysProject = sysProjectService.selectSysProjectById(sysMonomerLaw1.getProjectId());
                    if (StringUtils.isNotNull(sysProject) && StringUtils.isNotEmpty(sysProject.getProjectName())) {
                        projectName = sysProject.getProjectName();
                    }
                }
            }
            sysMonomerLaw1.setProjectName(projectName);
        }
        return getDataTable(list);
    }

    /**
     * 导出项目法律信息列表
     */
    @RequiresPermissions("system:monomerLaw:export")
    @Log(title = "项目法律信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysMonomerLaw sysMonomerLaw) {
        List<SysMonomerLaw> list = sysMonomerLawService.selectSysMonomerLawList(sysMonomerLaw);
        ExcelUtil<SysMonomerLaw> util = new ExcelUtil<SysMonomerLaw>(SysMonomerLaw.class);
        return util.exportExcel(list, "monomerLaw");
    }

    /**
     * 新增项目法律信息
     */
    @GetMapping("/add")
    public String add(Long projectId, String projectType, ModelMap modelMap) {
        modelMap.put("projectId", projectId);
        modelMap.put("projectType", projectType);
        return prefix + "/add";
    }

    /**
     * 新增保存项目法律信息
     */
    @RequiresPermissions("system:monomerLaw:add")
    @Log(title = "项目法律信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysMonomerLaw sysMonomerLaw) {
        return toAjax(sysMonomerLawService.insertSysMonomerLaw(sysMonomerLaw));
    }

    /**
     * 修改项目法律信息
     */
    @GetMapping("/edit/{monomerLawId}")
    public String edit(@PathVariable("monomerLawId") Long monomerLawId, ModelMap mmap) {
        SysMonomerLaw sysMonomerLaw = sysMonomerLawService.selectSysMonomerLawById(monomerLawId);
        mmap.put("sysMonomerLaw", sysMonomerLaw);
        return prefix + "/edit";
    }

    /**
     * 修改保存项目法律信息
     */
    @RequiresPermissions("system:monomerLaw:edit")
    @Log(title = "项目法律信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysMonomerLaw sysMonomerLaw) {
        return toAjax(sysMonomerLawService.updateSysMonomerLaw(sysMonomerLaw));
    }

    /**
     * 删除项目法律信息
     */
    @RequiresPermissions("system:monomerLaw:remove")
    @Log(title = "项目法律信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysMonomerLawService.deleteSysMonomerLawByIds(ids));
    }

    @GetMapping("/detail")
    public String detail(Long monomerLawId, Long detailType, ModelMap modelMap) {
        String url = "";
        SysMonomerLaw sysMonomerLaw = sysMonomerLawService.selectSysMonomerLawById(monomerLawId);
        String projectName = "";
        if (StringUtils.isNotNull(sysMonomerLaw.getProjectId()) && StringUtils.isNotNull(sysMonomerLaw.getProjectType())) {
            if ("1".equals(sysMonomerLaw.getProjectType())) {
                SysZck sysZck = sysZckService.selectSysZckById(sysMonomerLaw.getProjectId());
                if (StringUtils.isNotNull(sysZck) && StringUtils.isNotEmpty(sysZck.getProjectName())) {
                    projectName = sysZck.getProjectName();
                }
            } else if ("2".equals(sysMonomerLaw.getProjectType())) {
                SysBgczzck sysBgczzck = sysBgczzckService.selectSysBgczzckById(sysMonomerLaw.getProjectId());
                if (StringUtils.isNotNull(sysBgczzck)) {
                    if (StringUtils.isNotEmpty(sysBgczzck.getProjectName())) {
                        projectName = sysBgczzck.getProjectName();
                    }
                    if (StringUtils.isNotEmpty(sysBgczzck.getProjectStatus())) {
                        sysMonomerLaw.setProjectStatus(sysBgczzck.getProjectStatus());
                    }
                }
            } else if ("3".equals(sysMonomerLaw.getProjectType())) {
                SysProject sysProject = sysProjectService.selectSysProjectById(sysMonomerLaw.getProjectId());
                if (StringUtils.isNotNull(sysProject) && StringUtils.isNotEmpty(sysProject.getProjectName())) {
                    projectName = sysProject.getProjectName();
                }
            }
        }
        sysMonomerLaw.setProjectName(projectName);
        modelMap.put("sysMonomerLaw", sysMonomerLaw);
        if (1 == detailType) {
            url = "/detail1";
        } else if (2 == detailType) {
            url = "/detail2";
        }
        return prefix + url;
    }
}
