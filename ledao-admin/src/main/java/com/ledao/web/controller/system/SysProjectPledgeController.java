package com.ledao.web.controller.system;

import java.math.BigDecimal;
import java.util.List;

import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.*;
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
import com.ledao.system.service.ISysProjectPledgeService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 投后项目质押物Controller
 *
 * @author lxz
 * @date 2020-10-28
 */
@Controller
@RequestMapping("/system/pledge")
public class SysProjectPledgeController extends BaseController {
    private String prefix = "system/pledge";

    @Autowired
    private ISysProjectPledgeService sysProjectPledgeService;

    @Autowired
    private ISysProjectService sysProjectService;

    @RequiresPermissions("system:pledge:view")
    @GetMapping()
    public String pledge() {
        return prefix + "/pledge";
    }

    /**
     * 查询投后项目质押物列表
     */
    @RequiresPermissions("system:pledge:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysProjectPledge sysProjectPledge) {
        startPage();
        List<SysProjectPledge> list = sysProjectPledgeService.selectPledgeByProjectId(sysProjectPledge.getProjectIds());
        for (SysProjectPledge sysProjectPledge1 : list) {
            SysProject sysProject = sysProjectService.selectSysProjectById(sysProjectPledge1.getProjectId());
            sysProjectPledge1.setProjectName(sysProject.getProjectName());
        }
        return getDataTable(list);
    }

    /**
     * 根据项目ID查询质押物
     */
    @RequiresPermissions("system:pledge:list")
    @GetMapping("/pledgeList/{projectId}")
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
        return "system/pledge/pledge";
    }

    /**
     * 导出投后项目质押物列表
     */
    @RequiresPermissions("system:pledge:export")
    @Log(title = "投后项目质押物", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysProjectPledge sysProjectPledge) {
        List<SysProjectPledge> list = sysProjectPledgeService.selectSysProjectPledgeList(sysProjectPledge);
        ExcelUtil<SysProjectPledge> util = new ExcelUtil<SysProjectPledge>(SysProjectPledge.class);
        return util.exportExcel(list, "pledge");
    }

    /**
     * 新增投后项目质押物
     */
    @GetMapping("/add/{projectId}")
    public String add(@PathVariable("projectId") String projectId, ModelMap modelMap) {
        modelMap.put("projectId", projectId);
        return prefix + "/add";
    }

    /**
     * 新增保存投后项目质押物
     */
    @RequiresPermissions("system:pledge:add")
    @Log(title = "投后项目质押物", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysProjectPledge sysProjectPledge) {
        return toAjax(sysProjectPledgeService.insertSysProjectPledge(sysProjectPledge));
    }

    /**
     * 修改投后项目质押物
     */
    @GetMapping("/edit/{pledgeId}")
    public String edit(@PathVariable("pledgeId") Long pledgeId, ModelMap mmap) {
        SysProjectPledge sysProjectPledge = sysProjectPledgeService.selectSysProjectPledgeById(pledgeId);
        mmap.put("sysProjectPledge", sysProjectPledge);
        return prefix + "/edit";
    }

    /**
     * 修改保存投后项目质押物
     */
    @RequiresPermissions("system:pledge:edit")
    @Log(title = "投后项目质押物", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProjectPledge sysProjectPledge) {
        return toAjax(sysProjectPledgeService.updateSysProjectPledge(sysProjectPledge));
    }

    /**
     * 删除投后项目质押物
     */
    @RequiresPermissions("system:pledge:remove")
    @Log(title = "投后项目质押物", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        for (String string1 : ids.split(",")) {
            SysProjectPledge sysProjectPledge = sysProjectPledgeService.selectSysProjectPledgeById(Long.valueOf(string1));
            sysProjectPledgeService.deleteSysProjectPledgeById(Long.valueOf(string1));
            List<SysProjectPledge> sysProjectPledgeList = sysProjectPledgeService.selectSysPledgeByProjectId(sysProjectPledge.getProjectId());
            StringBuffer sb = new StringBuffer();
            for (SysProjectPledge sysProjectPledge1 : sysProjectPledgeList) {
                if (StringUtils.isNotNull(sysProjectPledge1.getPledgor())) {
                    sb.append(sysProjectPledge1.getPledgor()).append(";");
                }
            }
            SysProject sysProject = new SysProject();
            sysProject.setProjectId(sysProjectPledge.getProjectId());
            if (StringUtils.isNotNull(sb.toString()) && StringUtils.isNotEmpty(sb.toString())) {
                sysProject.setPledge(sb.deleteCharAt(sb.length() - 1).toString());
            }
            sysProject.setUpdateBy(ShiroUtils.getLoginName());
            sysProjectService.updateSysProject(sysProject);
        }
        return toAjax(sysProjectPledgeService.deleteSysProjectPledgeByIds(ids));
    }
}
