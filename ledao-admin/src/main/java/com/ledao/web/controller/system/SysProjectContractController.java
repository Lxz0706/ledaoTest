package com.ledao.web.controller.system;

import java.math.BigDecimal;
import java.util.List;

import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysProject;
import com.ledao.system.dao.SysProjectMortgage;
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
import com.ledao.system.dao.SysProjectContract;
import com.ledao.system.service.ISysProjectContractService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 投后部项目管理合同本金Controller
 *
 * @author ledao
 * @date 2020-08-06
 */
@Controller
@RequestMapping("/system/contract")
public class SysProjectContractController extends BaseController {
    private String prefix = "system/contract";

    @Autowired
    private ISysProjectContractService sysProjectContractService;

    @Autowired
    private ISysProjectService sysProjectService;

    @Autowired
    private ISysProjectZckService sysProjectZckService;


    @RequiresPermissions("system:contract:list")
    @GetMapping({"/contractList/{projectId}"})
    public String selectProjectContractByProjectId(@PathVariable("projectId") String projectId, ModelMap modelMap) {
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
        return "system/contract/contract";
    }

    @RequiresPermissions("system:contract:view")
    @GetMapping()
    public String contract() {
        return prefix + "/contract";
    }

    /**
     * 查询投后部项目管理合同本金列表
     */
    @RequiresPermissions("system:contract:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysProjectContract sysProjectContract) {
        startPage();
        List<SysProjectContract> list = sysProjectContractService.selectSysProjectContractByProjectId(sysProjectContract.getProjectIds());
        for (SysProjectContract sysprojectContract : list) {
            SysProject sysProject = sysProjectService.selectSysProjectById(sysprojectContract.getProjectId());
            sysprojectContract.setProjectName(sysProject.getProjectName());
        }
        return getDataTable(list);
    }

    /**
     * 导出投后部项目管理合同本金列表
     */
    @RequiresPermissions("system:contract:export")
    @Log(title = "投后部项目管理合同本金", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysProjectContract sysProjectContract) {
        List<SysProjectContract> list = sysProjectContractService.selectSysProjectContractList(sysProjectContract);
        ExcelUtil<SysProjectContract> util = new ExcelUtil<SysProjectContract>(SysProjectContract.class);
        return util.exportExcel(list, "contract");
    }

    /**
     * 新增投后部项目管理合同本金
     */
    @GetMapping("/add/{projectId}")
    public String add(@PathVariable("projectId") String projectId, ModelMap modelMap) {
        modelMap.put("projectId", projectId);
        return prefix + "/add";
    }

    /**
     * 新增保存投后部项目管理合同本金
     */
    @RequiresPermissions("system:contract:add")
    @Log(title = "投后部项目管理合同本金", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysProjectContract sysProjectContract) {
        sysProjectContract.setCreateBy(ShiroUtils.getLoginName());
        sysProjectContract.setDelFlag("0");
        return toAjax(sysProjectContractService.insertSysProjectContract(sysProjectContract));
    }

    /**
     * 修改投后部项目管理合同本金
     */
    @GetMapping("/edit/{contractId}")
    public String edit(@PathVariable("contractId") Long contractId, ModelMap mmap) {
        SysProjectContract sysProjectContract = sysProjectContractService.selectSysProjectContractById(contractId);
        mmap.put("sysProjectContract", sysProjectContract);
        return prefix + "/edit";
    }

    /**
     * 修改保存投后部项目管理合同本金
     */
    @RequiresPermissions("system:contract:edit")
    @Log(title = "投后部项目管理合同本金", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProjectContract sysProjectContract) {
        sysProjectContract.setUpdateBy(ShiroUtils.getLoginName());
        SysProject sysProject = sysProjectService.selectSysProjectById(sysProjectContract.getProjectId());
        sysProject.setInterestBalance(sysProjectContract.getTotalInterest());
        sysProjectService.updateSysProject(sysProject);
        return toAjax(sysProjectContractService.updateSysProjectContract(sysProjectContract));
    }

    /**
     * 删除投后部项目管理合同本金
     */
    @RequiresPermissions("system:contract:remove")
    @Log(title = "投后部项目管理合同本金", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        for (String string1 : ids.split(",")) {
            SysProjectContract sysProjectContract = sysProjectContractService.selectSysProjectContractById(Long.valueOf(string1));
            if (StringUtils.isNotNull(sysProjectContract.getProjectId())) {
                SysProject sysProject = sysProjectService.selectSysProjectById(sysProjectContract.getProjectId());
                sysProject.setContractPrincipal(new BigDecimal(0));
                sysProject.setUpdateBy(ShiroUtils.getLoginName());
                sysProjectService.updateSysProject(sysProject);
            }
        }
        return toAjax(sysProjectContractService.deleteSysProjectContractByIds(ids));
    }
}
