package com.ledao.web.controller.system;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ledao.common.utils.StringUtils;
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
import com.ledao.system.domain.SysProject;
import com.ledao.system.service.ISysProjectService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.domain.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 投后部项目管理Controller
 *
 * @author ledao
 * @date 2020-08-06
 */
@Controller
@RequestMapping("/system/project")
public class SysProjectController extends BaseController {
    private String prefix = "system/project";

    @Autowired
    private ISysProjectService sysProjectService;

    @RequiresPermissions("system:project:view")
    @GetMapping()
    public String project() {
        return prefix + "/project";
    }

    /**
     * 查询投后部项目管理列表
     */
    @RequiresPermissions("system:project:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysProject sysProject) {
        startPage();
        List<SysProject> list = sysProjectService.selectProject(sysProject);
        StringBuffer sb = new StringBuffer();
        StringBuffer sb1 = new StringBuffer();
        for (SysProject sysProject1 : list) {
            sb.append(sysProject1.getProjectId()).append(",");
        }
        String projectIds = sb.deleteCharAt(sb.length() - 1).toString();
        SysProject sysProject1 = new SysProject();
        for (String string1 : projectIds.split(",")) {
            sysProject1.setProjectId(Long.parseLong(string1));
            List<SysProject> sysProjectsList = sysProjectService.selectSysProjectByParentId(sysProject1);
            for (SysProject sysProject2 : sysProjectsList) {
                sb1.append(sysProject2.getProjectId()).append(",");
            }
        }

        List<SysProject> projectList = sysProjectService.selectSysProjectByProjectId(sb1.deleteCharAt(sb1.length() - 1).toString());
        Map<Long, Long> map = new HashMap<>();
        for (SysProject sysProject2 : projectList) {
            map.put(sysProject2.getProjectId(), sysProject2.getProjectId());
        }
        for (SysProject sysproject : list) {
            for (SysProject sysProject2 : projectList) {
                if (map.get(sysProject2.getProjectId()).equals(sysproject.getProjectId())) {
                    StringBuffer sb2 = new StringBuffer();
                    StringBuffer sb3 = new StringBuffer();
                    if (sysproject.getTotalPrice() == null) {
                        sysproject.setTotalPrice(new BigDecimal(0));
                    }
                    if (sysProject2.getPrincipalBalance() == null) {
                        sysProject2.setPrincipalBalance(new BigDecimal(0));
                    }

                    if (sysproject.getTotalInterestBalance() == null) {
                        sysProject.setTotalInterestBalance(new BigDecimal(0));
                    }
                    if (sysProject2.getInterestBalance() == null) {
                        sysProject2.setInterestBalance(new BigDecimal(0));
                    }
                    //本金余额相加
                    sysproject.setTotalPrice(sysproject.getTotalPrice().add(sysProject2.getPrincipalBalance()));
                    //利息相加
                    sysproject.setTotalInterestBalance(sysProject.getTotalInterestBalance().add(sysProject2.getInterestBalance()));

                    sb2.append(sysProject2.getCollateral()).append(";");
                    sb3.append(sysProject2.getGuarantor()).append(";");

                    //抵押物拼接
                    sysproject.setCollateral(sb2.deleteCharAt(sb2.length() - 1).toString());
                    //保证人拼接
                    sysproject.setGuarantor(sb3.deleteCharAt(sb3.length() - 1).toString());
                }
            }
        }

        return getDataTable(list);
    }

    /**
     * 导出投后部项目管理列表
     */
    @RequiresPermissions("system:project:export")
    @Log(title = "投后部项目管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysProject sysProject) {
        List<SysProject> list = sysProjectService.selectSysProjectList(sysProject);
        ExcelUtil<SysProject> util = new ExcelUtil<SysProject>(SysProject.class);
        return util.exportExcel(list, "项目管理");
    }

    /**
     * 新增投后部项目管理
     */
    @GetMapping("/add/{projectZckId}")
    public String add(@PathVariable("projectZckId") String projectZckId, ModelMap mmap) {
        mmap.put("projectZckId", projectZckId);
        return prefix + "/add";
    }

    /**
     * 新增投后部项目管理
     */
    @GetMapping("/adds/{projectZckId}/{parentId}")
    public String adds(@PathVariable("projectZckId") String projectZckId, @PathVariable("parentId") String parentId, ModelMap mmap) {
        mmap.put("projectZckId", projectZckId);
        mmap.put("parentId", parentId);
        return prefix + "/add";
    }

    /**
     * 新增保存投后部项目管理
     */
    @RequiresPermissions("system:project:add")
    @Log(title = "投后部项目管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysProject sysProject) {
        List<SysProject> list = sysProjectService.selectSysProjectList(sysProject);
        if (StringUtils.isNotNull(list)) {
            for (SysProject sysproject : list) {
                sysProject.setParentId(sysproject.getProjectId());
            }
        }
        sysProject.setCreateBy(ShiroUtils.getLoginName());
        sysProject.setDelFlag("0");
        return toAjax(sysProjectService.insertSysProject(sysProject));
    }

    /**
     * 修改投后部项目管理
     */
    @GetMapping("/edit/{projectId}")
    public String edit(@PathVariable("projectId") Long projectId, ModelMap mmap) {
        SysProject sysProject = sysProjectService.selectSysProjectById(projectId);
        mmap.put("sysProject", sysProject);
        return prefix + "/edit";
    }

    /**
     * 修改保存投后部项目管理
     */
    @RequiresPermissions("system:project:edit")
    @Log(title = "投后部项目管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProject sysProject) {
        sysProject.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysProjectService.updateSysProject(sysProject));
    }

    /**
     * 删除投后部项目管理
     */
    @RequiresPermissions("system:project:remove")
    @Log(title = "投后部项目管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysProjectService.deleteSysProjectByIds(ids));
    }

    @RequiresPermissions("system:project:list")
    @GetMapping({"/projectList/{projectId}/{projectZckId}"})
    public String selectZcbByAssetStatus(@PathVariable("projectId") Long projectId, @PathVariable("projectZckId") Long projectZckId, ModelMap modelMap) {
        modelMap.put("projectId", projectId);
        modelMap.put("projectZckId", projectZckId);
        return "system/project/projectList";
    }

    /**
     * 根据父级ID查询
     *
     * @param sysProject
     * @return 结果
     */
    @RequiresPermissions("system:project:list")
    @PostMapping("/projectList")
    @ResponseBody
    public TableDataInfo projectList(SysProject sysProject) {
        startPage();
        StringBuffer sb = new StringBuffer();
        List<SysProject> sysProjectsList = sysProjectService.selectSysProjectByParentId(sysProject);
        for (SysProject sysProject1 : sysProjectsList) {
            sb.append(sysProject1.getProjectId()).append(",");
        }
        String projectIds = sb.deleteCharAt(sb.length() - 1).toString();
        List<SysProject> list = sysProjectService.selectSysProjectByProjectId(projectIds);
        return getDataTable(list);
    }

    /**
     * 查看详细
     */
    @RequiresPermissions("system:project:detail")
    @Log(title = "项目管理", businessType = BusinessType.DETAIL)
    @GetMapping("/detail/{id}/{ids}")
    public String detail(@PathVariable("id") Long id, @PathVariable("ids") Long ids, ModelMap mmap) {
        String url = "";
        mmap.put("sysProject", sysProjectService.selectSysProjectById(id));
        if (1 == ids) {
            url = "/detail1";
        } else if (2 == ids) {
            url = "/detail2";
        } else if (3 == ids) {
            url = "/detail3";
        } else if (4 == ids) {
            url = "/detail4";
        } else {
            url = "/detail";
        }
        return prefix + url;
    }
}
