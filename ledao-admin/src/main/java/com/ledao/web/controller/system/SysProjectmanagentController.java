package com.ledao.web.controller.system;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.framework.web.dao.server.Sys;
import com.ledao.system.dao.*;
import com.ledao.system.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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

    @Autowired
    private ISysProjectTypeService sysProjectTypeService;

    @Autowired
    private ISysCoverChargeService sysCoverChargeService;

    @Autowired
    private ISysRecaptureService sysRecaptureService;

    @RequiresPermissions("system:projectmanagent:view")
    @GetMapping()
    public String projectmanagent() {
        return prefix + "/projectmanagent";
    }


    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("system:projectmanagent:list")
    @PostMapping("/lists")
    @ResponseBody
    public TableDataInfo lists() {
        startPage();
        SysProjectType sysProjectType = new SysProjectType();
        List<SysProjectType> list = sysProjectTypeService.selectSysProjectTypeList(sysProjectType);
        for (SysProjectType sysProjectType1 : list) {
            SysProjectmanagent sysProjectmanagent = new SysProjectmanagent();
            sysProjectmanagent.setProjectType(sysProjectType1.getProjectType());
            List<SysProjectmanagent> sysProjectmanagentList = sysProjectmanagentService.selectSysProjectmanagentList(sysProjectmanagent);
            for (SysProjectmanagent sysProjectManagent : sysProjectmanagentList) {
                //投资金额总和
                if (StringUtils.isNull(sysProjectManagent.getInvestmentAmount())) {
                    sysProjectManagent.setInvestmentAmount(new BigDecimal(0));
                }
                if (StringUtils.isNull(sysProjectType1.getInvestmentAmount())) {
                    sysProjectType1.setInvestmentAmount(new BigDecimal(0));
                }
                sysProjectType1.setInvestmentAmount(sysProjectType1.getInvestmentAmount().add(sysProjectManagent.getInvestmentAmount()));

                if ("自投类项目".equals(sysProjectManagent.getProjectType())) {
                    //已收金额总和
                    SysProjectysyf sysProjectysyf = new SysProjectysyf();
                    sysProjectysyf.setProjectManagementId(sysProjectManagent.getProjectManagementId());
                    List<SysProjectysyf> sysProjectysyfList = sysProjectysyfService.selectSysProjectysyfList(sysProjectysyf);
                    if (StringUtils.isNotEmpty(sysProjectysyfList)) {
                        for (SysProjectysyf sysprojectYsyf : sysProjectysyfList) {
                            if (sysProjectManagent.getAmountPaid() == null) {
                                sysProjectManagent.setAmountPaid(new BigDecimal(0));
                            }
                            if (sysprojectYsyf.getAmountPaid() == null) {
                                sysprojectYsyf.setAmountPaid(new BigDecimal(0));
                            }
                            sysProjectManagent.setAmountPaid(sysProjectManagent.getAmountPaid().add(sysprojectYsyf.getAmountPaid()));
                            sysProjectType1.setYzfje(sysProjectManagent.getAmountPaid());
                        }
                    }

                    //已回收金额
                    SysProjectRecovered sysProjectRecovered = new SysProjectRecovered();
                    sysProjectRecovered.setProjectManagementId(sysProjectManagent.getProjectManagementId());
                    List<SysProjectRecovered> sysProjectRecoveredList = sysProjectRecoveredService.selectSysProjectRecoveredList(sysProjectRecovered);
                    if (StringUtils.isNotEmpty(sysProjectRecoveredList)) {
                        for (SysProjectRecovered SysProjectRecovered1 : sysProjectRecoveredList) {
                            if (sysProjectManagent.getEntryAmount() == null) {
                                sysProjectManagent.setEntryAmount(new BigDecimal(0));
                            }
                            if (SysProjectRecovered1.getAmountRecovered() == null) {
                                SysProjectRecovered1.setAmountRecovered(new BigDecimal(0));
                            }
                            sysProjectManagent.setEntryAmount(sysProjectManagent.getEntryAmount().add(SysProjectRecovered1.getAmountRecovered()));
                            sysProjectType1.setYhsje(sysProjectManagent.getEntryAmount());
                        }
                    }
                } else {
                    //总已结算

                    SysCoverCharge sysCoverCharge = new SysCoverCharge();
                    sysCoverCharge.setProjectManagementId(sysProjectManagent.getProjectManagementId());
                    sysCoverCharge.setFundType("已结算服务费");
                    List<SysCoverCharge> sysCoverChargeList = sysCoverChargeService.selectSysCoverChargeList(sysCoverCharge);
                    for (SysCoverCharge sysCoverCharge1 : sysCoverChargeList) {
                        if (sysCoverCharge1.getAmountPaid() == null) {
                            sysCoverCharge1.setAmountPaid(new BigDecimal(0));
                        }

                        if (sysProjectmanagent.getAmountRecovered() == null) {
                            sysProjectmanagent.setAmountRecovered(new BigDecimal(0));
                        }
                        sysProjectmanagent.setAmountRecovered(sysProjectmanagent.getAmountRecovered().add(sysCoverCharge1.getAmountPaid()));
                        sysProjectType1.setZyjs(sysProjectmanagent.getAmountRecovered());
                    }
                    /*if (sysProjectManagent.getEntryAmount() == null) {
                        sysProjectManagent.setEntryAmount(new BigDecimal(0));
                    }
                    if (sysProjectType1.getZyjs() == null) {
                        sysProjectType1.setZyjs(new BigDecimal(0));
                    }*/
                    //sysProjectType1.setZyjs(sysProjectType1.getZyjs().add(sysProjectManagent.getEntryAmount()));


                    //总回现金额
                    List<SysRecapture> sysRecaptureList = sysRecaptureService.selectSysRecaptureByProjectId(sysProjectManagent.getProjectManagementId());
                    if (sysRecaptureList != null && !sysRecaptureList.isEmpty()) {
                        for (SysRecapture sysRecature : sysRecaptureList) {
                            if (sysRecature.getRecapture() == null) {
                                sysRecature.setRecapture(new BigDecimal(0));
                            }
                            if (sysProjectType1.getRecapture() == null) {
                                sysProjectType1.setRecapture(new BigDecimal(0));
                            }
                            sysProjectType1.setRecapture(sysProjectType1.getRecapture().add(sysRecature.getRecapture()));
                        }
                    }
                }

            }
        }
        return getDataTable(list);
    }

    @RequiresPermissions("system:projectmanagent:list")
    @GetMapping({"/selectSysProjectmanagentListByProjectType"})
    public String selectSysProjectmanagentListByProjectType(String projectType, ModelMap modelMap) {
        modelMap.put("projectType", projectType);
        return "system/projectmanagent/projectmanagentList";
    }

    @RequiresPermissions("system:projectmanagent:list")
    @GetMapping({"/selectSysProjectmanagentListByProjectTypes"})
    public String selectSysProjectmanagentListByProjectTypes(String projectType, ModelMap modelMap) {
        modelMap.put("projectType", projectType);
        return "system/projectmanagent/projectmanagentLists";
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

            //已结算服务费
            SysCoverCharge sysCoverCharge = new SysCoverCharge();
            sysCoverCharge.setProjectManagementId(sysProjectmanagent1.getProjectManagementId());
            sysCoverCharge.setFundType("已结算服务费");
            List<SysCoverCharge> sysCoverChargeList = sysCoverChargeService.selectSysCoverChargeList(sysCoverCharge);
            for (SysCoverCharge sysCoverCharge1 : sysCoverChargeList) {
                if (sysCoverCharge1.getAmountPaid() == null) {
                    sysCoverCharge1.setAmountPaid(new BigDecimal(0));
                }

                if (sysProjectmanagent1.getAmountRecovered() == null) {
                    sysProjectmanagent1.setAmountRecovered(new BigDecimal(0));
                }
                sysProjectmanagent1.setAmountRecovered(sysProjectmanagent1.getAmountRecovered().add(sysCoverCharge1.getAmountPaid()));
            }

            //待结算服务费
            SysCoverCharge sysCoverCharge1 = new SysCoverCharge();
            sysCoverCharge1.setProjectManagementId(sysProjectmanagent1.getProjectManagementId());
            sysCoverCharge1.setFundType("待结算服务费");
            sysCoverCharge1.setState("否");
            List<SysCoverCharge> sysCoverChargeList1 = sysCoverChargeService.selectSysCoverChargeList(sysCoverCharge1);
            for (SysCoverCharge sysCoverCharge2 : sysCoverChargeList1) {
                if (sysCoverCharge2.getAmountPaid() == null) {
                    sysCoverCharge2.setAmountPaid(new BigDecimal(0));
                }

                if (sysProjectmanagent1.getExpectedAmount() == null) {
                    sysProjectmanagent1.setExpectedAmount(new BigDecimal(0));
                }
                sysProjectmanagent1.setExpectedAmount(sysProjectmanagent1.getExpectedAmount().add(sysCoverCharge2.getAmountPaid()));
            }
            //回现金额
            List<SysRecapture> sysRecaptureList = sysRecaptureService.selectSysRecaptureByProjectId(sysProjectmanagent1.getProjectManagementId());
            if (sysRecaptureList != null && !sysRecaptureList.isEmpty()) {
                sysProjectmanagent1.setRecapture(sysRecaptureList.get(0).getRecapture());
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
     * 新增【请填写功能名称】列表
     */
    @GetMapping("/add")
    public String add(String projectType, ModelMap mmap) {
        mmap.put("projectType", projectType);
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
        String url = "";
        SysProjectmanagent sysProjectmanagent = sysProjectmanagentService.selectSysProjectmanagentById(id);
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
        if (StringUtils.isNotNull(sysProjectmanagent.getInvestmentAmount())) {
            sysProjectmanagent.setInvestmentAmounts(decimalFormat.format(sysProjectmanagent.getInvestmentAmount()));
        }

        if (StringUtils.isNotNull(sysProjectmanagent.getAmountRecovered())) {
            sysProjectmanagent.setAmountRecovereds(decimalFormat.format(sysProjectmanagent.getAmountRecovered()));
        }

        if (StringUtils.isNotNull(sysProjectmanagent.getAmountPaid())) {
            sysProjectmanagent.setAmountPaids(decimalFormat.format(sysProjectmanagent.getAmountPaid()));
        }

        if (StringUtils.isNotNull(sysProjectmanagent.getYjzfwf())) {
            sysProjectmanagent.setYjzfwfs(decimalFormat.format(sysProjectmanagent.getYjzfwf()));
        }

        if ("自投类项目".equals(sysProjectmanagent.getProjectType())) {
            url = "/detail";
        } else {
            url = "/detail1";
        }
        mmap.put("sysProjectManagent", sysProjectmanagent);
        return prefix + url;

    }

}
