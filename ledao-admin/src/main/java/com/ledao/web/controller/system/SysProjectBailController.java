package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysProject;
import com.ledao.system.dao.SysProjectContract;
import com.ledao.system.service.ISysProjectService;
import com.ledao.system.service.ISysProjectZckService;
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
import com.ledao.system.dao.SysProjectBail;
import com.ledao.system.service.ISysProjectBailService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 投后部项目管理保证人Controller
 *
 * @author ledao
 * @date 2020-08-06
 */
@Controller
@RequestMapping("/system/bail")
public class SysProjectBailController extends BaseController {
    private String prefix = "system/bail";

    @Autowired
    private ISysProjectBailService sysProjectBailService;

    @Autowired
    private ISysProjectService sysProjectService;

    @Autowired
    private ISysProjectZckService sysProjectZckService;

    @RequiresPermissions("system:bail:view")
    @GetMapping()
    public String bail() {
        return prefix + "/bail";
    }

    /**
     * 查询投后部项目管理保证人列表
     */
    @RequiresPermissions("system:bail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysProjectBail sysProjectBail) {
        startPage();
        List<SysProjectBail> list = sysProjectBailService.selectSysProjectBailByProjectId(sysProjectBail.getProjectIds());
        for (SysProjectBail sysProjectBail1 : list) {
            SysProject sysProject = sysProjectService.selectSysProjectById(sysProjectBail1.getProjectId());
            sysProjectBail1.setProjectName(sysProject.getProjectName());
        }
        //List<SysProjectBail> list = sysProjectBailService.selectSysProjectBailList(sysProjectBail);
        return getDataTable(list);
    }

    /**
     * 根据项目ID查询保证人
     */
    @RequiresPermissions("system:project:list")
    @GetMapping("/bailList/{projectId}")
    public String selectProjectBailByProjectId(@PathVariable("projectId") String projectId, ModelMap modelMap) {
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
        modelMap.put("projectZckId", sysProjectService.selectSysProjectById(Long.valueOf(projectId)).getProjectZckId());
        modelMap.put("projectZckName", sysProjectZckService.selectSysProjectZckById(sysProjectService.selectSysProjectById(Long.valueOf(projectId)).getProjectZckId()).getZckName());
        modelMap.put("projectIds", sb1.deleteCharAt(sb1.length() - 1).toString());
        return "system/bail/bail";
    }

    /**
     * 导出投后部项目管理保证人列表
     */
    @RequiresPermissions("system:bail:export")
    @Log(title = "投后部项目管理保证人", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysProjectBail sysProjectBail) {
        List<SysProjectBail> list = sysProjectBailService.selectSysProjectBailList(sysProjectBail);
        ExcelUtil<SysProjectBail> util = new ExcelUtil<SysProjectBail>(SysProjectBail.class);
        return util.exportExcel(list, "bail");
    }

    /**
     * 新增投后部项目管理保证人
     */
    @GetMapping("/add/{projectId}")
    public String add(@PathVariable("projectId") String projectId, ModelMap modelMap) {
        modelMap.put("projectId", projectId);
        return prefix + "/add";
    }

    /**
     * 新增保存投后部项目管理保证人
     */
    @RequiresPermissions("system:bail:add")
    @Log(title = "投后部项目管理保证人", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysProjectBail sysProjectBail) {
        sysProjectBail.setCreateBy(ShiroUtils.getLoginName());
        sysProjectBail.setDelFlag("0");
        return toAjax(sysProjectBailService.insertSysProjectBail(sysProjectBail));
    }

    /**
     * 修改投后部项目管理保证人
     */
    @GetMapping("/edit/{bailId}")
    public String edit(@PathVariable("bailId") Long bailId, ModelMap mmap) {
        SysProjectBail sysProjectBail = sysProjectBailService.selectSysProjectBailById(bailId);
        mmap.put("sysProjectBail", sysProjectBail);
        return prefix + "/edit";
    }

    /**
     * 修改保存投后部项目管理保证人
     */
    @RequiresPermissions("system:bail:edit")
    @Log(title = "投后部项目管理保证人", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProjectBail sysProjectBail) {
        sysProjectBail.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysProjectBailService.updateSysProjectBail(sysProjectBail));
    }

    /**
     * 删除投后部项目管理保证人
     */
    @RequiresPermissions("system:bail:remove")
    @Log(title = "投后部项目管理保证人", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        for (String string1 : ids.split(",")) {
            SysProjectBail sysProjectBail = sysProjectBailService.selectSysProjectBailById(Long.valueOf(string1));
            sysProjectBailService.deleteSysProjectBailById(Long.valueOf(string1));
            List<SysProjectBail> sysProjectBailList = sysProjectBailService.selectProjectBailListByProjectId(sysProjectBail.getProjectId());
            StringBuffer sb = new StringBuffer();
            for (SysProjectBail sysProjectBail1 : sysProjectBailList) {
                if (StringUtils.isNotNull(sysProjectBail1.getBail())) {
                    sb.append(sysProjectBail1.getBail()).append(";");
                }
            }
            SysProject sysProject = new SysProject();
            sysProject.setProjectId(sysProjectBail.getProjectId());
            if (StringUtils.isNotNull(sb.toString()) && StringUtils.isNotEmpty(sb.toString())) {
                sysProject.setGuarantor(sb.deleteCharAt(sb.length() - 1).toString());
            }
            sysProject.setUpdateBy(ShiroUtils.getLoginName());
            sysProjectService.updateSysProject(sysProject);
        }
        return toAjax(sysProjectBailService.deleteSysProjectBailByIds(ids));
    }
}
