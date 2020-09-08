package com.ledao.web.controller.system;

import java.math.BigDecimal;
import java.util.List;

import com.ledao.common.utils.StringUtils;
import com.ledao.framework.web.domain.server.Sys;
import com.ledao.system.domain.*;
import com.ledao.system.service.*;
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
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.domain.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 *
 * @author ledao
 * @date 2020-08-26
 */
@Controller
@RequestMapping("/system/projectmanagent")
public class SysProjectmanagentController extends BaseController {
    private String prefix = "system/projectmanagent";

    @Autowired
    private ISysProjectmanagentService sysProjectmanagentService;

    @Autowired
    private ISysProjectProgressService sysProjectProgressService;

    @Autowired
    private ISysProjectysyfService sysProjectysyfService;

    @Autowired
    private ISysProjectRecoveredService sysProjectRecoveredService;

    @Autowired
    private ISysProjectTargetrecoverService sysProjectTargetrecoverService;

    @RequiresPermissions("system:projectmanagent:view")
    @GetMapping()
    public String projectmanagent() {
        return prefix + "/projectmanagent";
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("system:projectmanagent:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysProjectmanagent sysProjectmanagent) {
        startPage();
        List<SysProjectmanagent> list = sysProjectmanagentService.selectSysProjectmanagentList(sysProjectmanagent);
        for (SysProjectmanagent sysProjectmanagent1 : list) {
            //项目进度情况
            SysProjectProgress sysProjectProgress = new SysProjectProgress();
            sysProjectProgress.setProjectManagementId(sysProjectmanagent1.getProjectManagementId());
            List<SysProjectProgress> sysProjectProgressList = sysProjectProgressService.selectSysProjectProgressList(sysProjectProgress);
            if (StringUtils.isNotEmpty(sysProjectProgressList)) {
                if (StringUtils.isNotEmpty(sysProjectProgressList.get(0).getProgress())) {
                    sysProjectmanagent1.setProgress(sysProjectProgressList.get(0).getProgress());
                }
            }

            //已收金额总和
            SysProjectysyf sysProjectysyf = new SysProjectysyf();
            sysProjectysyf.setProjectManagementId(sysProjectmanagent1.getProjectManagementId());
            List<SysProjectysyf> sysProjectysyfList = sysProjectysyfService.selectSysProjectysyfList(sysProjectysyf);
            if (StringUtils.isNotEmpty(sysProjectysyfList)) {
                for (SysProjectysyf sysprojectYsyf : sysProjectysyfList) {
                    if (sysProjectmanagent1.getAmountPaid() == null) {
                        sysProjectmanagent1.setAmountPaid(new BigDecimal(0));
                    }
                    if (sysprojectYsyf.getAmountPaid() == null) {
                        sysprojectYsyf.setAmountPaid(new BigDecimal(0));
                    }
                    sysProjectmanagent1.setAmountPaid(sysProjectmanagent1.getAmountPaid().add(sysprojectYsyf.getAmountPaid()));
                }
            }

            //已回收金额
            SysProjectRecovered sysProjectRecovered = new SysProjectRecovered();
            sysProjectRecovered.setProjectManagementId(sysProjectmanagent1.getProjectManagementId());
            List<SysProjectRecovered> sysProjectRecoveredList = sysProjectRecoveredService.selectSysProjectRecoveredList(sysProjectRecovered);
            if (StringUtils.isNotEmpty(sysProjectRecoveredList)) {
                for (SysProjectRecovered SysProjectRecovered1 : sysProjectRecoveredList) {
                    if (sysProjectmanagent1.getEntryAmount() == null) {
                        sysProjectmanagent1.setEntryAmount(new BigDecimal(0));
                    }
                    if (SysProjectRecovered1.getAmountRecovered() == null) {
                        SysProjectRecovered1.setAmountRecovered(new BigDecimal(0));
                    }
                    sysProjectmanagent1.setEntryAmount(sysProjectmanagent1.getEntryAmount().add(SysProjectRecovered1.getAmountRecovered()));
                }
            }


            //目标回收金额
            SysProjectTargetrecover sysProjectTargetrecover = new SysProjectTargetrecover();
            sysProjectTargetrecover.setProjectManagementId(sysProjectmanagent1.getProjectManagementId());
            List<SysProjectTargetrecover> sysProjectTargetrecoverList = sysProjectTargetrecoverService.selectSysProjectTargetrecoverList(sysProjectTargetrecover);
            if (StringUtils.isNotEmpty(sysProjectTargetrecoverList)) {
                if (StringUtils.isNotNull(sysProjectTargetrecoverList.get(0))) {
                    if (StringUtils.isNotNull(sysProjectTargetrecoverList.get(0).getTargetRecoveryAmount())) {
                        sysProjectmanagent1.setTargetRecoveryAmount(sysProjectTargetrecoverList.get(0).getTargetRecoveryAmount());
                    }
                    if (StringUtils.isNotNull(sysProjectTargetrecoverList.get(0).getTargetRecoveryDate())) {
                        sysProjectmanagent1.setTargetRecoverDate(sysProjectTargetrecoverList.get(0).getTargetRecoveryDate());
                    }
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("system:projectmanagent:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysProjectmanagent sysProjectmanagent) {
        List<SysProjectmanagent> list = sysProjectmanagentService.selectSysProjectmanagentList(sysProjectmanagent);
        ExcelUtil<SysProjectmanagent> util = new ExcelUtil<SysProjectmanagent>(SysProjectmanagent.class);
        return util.exportExcel(list, "projectmanagent");
    }

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @RequiresPermissions("system:projectmanagent:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysProjectmanagent sysProjectmanagent) {
        return toAjax(sysProjectmanagentService.insertSysProjectmanagent(sysProjectmanagent));
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{projectManagementId}")
    public String edit(@PathVariable("projectManagementId") Long projectManagementId, ModelMap mmap) {
        SysProjectmanagent sysProjectmanagent = sysProjectmanagentService.selectSysProjectmanagentById(projectManagementId);
        mmap.put("sysProjectmanagent", sysProjectmanagent);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @RequiresPermissions("system:projectmanagent:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProjectmanagent sysProjectmanagent) {
        return toAjax(sysProjectmanagentService.updateSysProjectmanagent(sysProjectmanagent));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("system:projectmanagent:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        StringBuffer sb = new StringBuffer();
        for (String string1 : ids.split(",")) {
            SysProjectProgress sysProjectProgress = new SysProjectProgress();
            sysProjectProgress.setProjectManagementId(Long.parseLong(string1));
            List<SysProjectProgress> sysProjectProgressList = sysProjectProgressService.selectSysProjectProgressList(sysProjectProgress);
            for (SysProjectProgress sysProjectProgress1 : sysProjectProgressList) {
                sb.append(sysProjectProgress1.getProgressId()).append(",");
            }
        }
        if (StringUtils.isNotEmpty(sb.toString())) {
            sysProjectProgressService.deleteSysProjectProgressByIds(sb.deleteCharAt(sb.length() - 1).toString());
        }

        return toAjax(sysProjectmanagentService.deleteSysProjectmanagentByIds(ids));
    }

    /**
     * 查看详细
     */
    @RequiresPermissions("system:projectmanagent:detail")
    @Log(title = "项目管理", businessType = BusinessType.DETAIL)
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("sysProjectManagent", sysProjectmanagentService.selectSysProjectmanagentById(id));
        return prefix + "/detail";
    }
}
