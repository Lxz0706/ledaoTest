package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.domain.SysProject;
import com.ledao.system.domain.SysProjectBail;
import com.ledao.system.service.ISysProjectService;
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
import com.ledao.system.domain.SysProjectMortgage;
import com.ledao.system.service.ISysProjectMortgageService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.domain.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 投后部项目管理抵押物Controller
 *
 * @author ledao
 * @date 2020-08-06
 */
@Controller
@RequestMapping("/system/mortgage")
public class SysProjectMortgageController extends BaseController {
    private String prefix = "system/mortgage";

    @Autowired
    private ISysProjectMortgageService sysProjectMortgageService;

    @Autowired
    private ISysProjectService sysProjectService;

    @RequiresPermissions("system:mortgage:view")
    @GetMapping()
    public String mortgage() {
        return prefix + "/mortgage";
    }

    /**
     * 查询投后部项目管理抵押物列表
     */
    @RequiresPermissions("system:mortgage:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysProjectMortgage sysProjectMortgage) {
        startPage();
        List<SysProjectMortgage> list = sysProjectMortgageService.selectSysProjectMortgageByProjectId(sysProjectMortgage.getProjectIds());
        for (SysProjectMortgage SysProjectMortgage1 : list) {
            SysProject sysProject = sysProjectService.selectSysProjectById(SysProjectMortgage1.getProjectId());
            SysProjectMortgage1.setProjectName(sysProject.getProjectName());
        }
        return getDataTable(list);
    }

    /**
     * 根据项目ID查询抵押物
     */
    @RequiresPermissions("system:project:list")
    @GetMapping("/mortgageList/{projectId}")
    public String selectProjectMortgageByProjectId(@PathVariable("projectId") String projectId, ModelMap modelMap) {
        StringBuffer sb = new StringBuffer();
        StringBuffer sb1 = new StringBuffer();
        SysProject sysProject = new SysProject();
        sysProject.setProjectId(Long.parseLong(projectId));
        List<SysProject> sysProjectsList = sysProjectService.selectSysProjectByParentId(sysProject);
        for (SysProject sysProject1 : sysProjectsList) {
            sb.append(sysProject1.getProjectId()).append(",");
        }
        String projectIds = sb.deleteCharAt(sb.length() - 1).toString();
        List<SysProject> list = sysProjectService.selectSysProjectByProjectId(projectIds);
        for (SysProject sysproject : list) {
            sb1.append(sysproject.getProjectId()).append(",");
        }
        modelMap.put("projectIds", sb1.deleteCharAt(sb1.length() - 1).toString());
        return "system/mortgage/mortgage";
    }

    /**
     * 导出投后部项目管理抵押物列表
     */
    @RequiresPermissions("system:mortgage:export")
    @Log(title = "投后部项目管理抵押物", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysProjectMortgage sysProjectMortgage) {
        List<SysProjectMortgage> list = sysProjectMortgageService.selectSysProjectMortgageList(sysProjectMortgage);
        ExcelUtil<SysProjectMortgage> util = new ExcelUtil<SysProjectMortgage>(SysProjectMortgage.class);
        return util.exportExcel(list, "mortgage");
    }

    /**
     * 新增投后部项目管理抵押物
     */
    @GetMapping("/add/{projectId}")
    public String add(@PathVariable("projectId") String projectId, ModelMap modelMap) {
        modelMap.put("projectId", projectId);
        return prefix + "/add";
    }

    /**
     * 新增保存投后部项目管理抵押物
     */
    @RequiresPermissions("system:mortgage:add")
    @Log(title = "投后部项目管理抵押物", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysProjectMortgage sysProjectMortgage) {
        sysProjectMortgage.setCreateBy(ShiroUtils.getLoginName());
        sysProjectMortgage.setDelFlag("0");
        return toAjax(sysProjectMortgageService.insertSysProjectMortgage(sysProjectMortgage));
    }

    /**
     * 修改投后部项目管理抵押物
     */
    @GetMapping("/edit/{mortgageId}")
    public String edit(@PathVariable("mortgageId") Long mortgageId, ModelMap mmap) {
        SysProjectMortgage sysProjectMortgage = sysProjectMortgageService.selectSysProjectMortgageById(mortgageId);
        mmap.put("sysProjectMortgage", sysProjectMortgage);
        return prefix + "/edit";
    }

    /**
     * 修改保存投后部项目管理抵押物
     */
    @RequiresPermissions("system:mortgage:edit")
    @Log(title = "投后部项目管理抵押物", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProjectMortgage sysProjectMortgage) {
        sysProjectMortgage.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysProjectMortgageService.updateSysProjectMortgage(sysProjectMortgage));
    }

    /**
     * 删除投后部项目管理抵押物
     */
    @RequiresPermissions("system:mortgage:remove")
    @Log(title = "投后部项目管理抵押物", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysProjectMortgageService.deleteSysProjectMortgageByIds(ids));
    }
}
