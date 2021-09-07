package com.ledao.web.controller.system;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.ledao.common.core.page.PageDao;
import com.ledao.common.core.page.TableSupport;
import com.ledao.common.utils.SortListUtil;
import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.*;
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
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 项目管理资产库Controller
 *
 * @author ledao
 * @date 2020-08-12
 */
@Controller
@RequestMapping("/system/projectZck")
public class SysProjectZckController extends BaseController {
    private String prefix = "system/projectZck";

    @Autowired
    private ISysProjectZckService sysProjectZckService;

    @Autowired
    private ISysProjectService sysProjectService;

    @Autowired
    private ISysRecaptureService sysRecaptureService;

    @Autowired
    private ISysProjectContractService sysProjectContractService;

    @Autowired
    private ISysDictDataService sysDictDataService;

    @RequiresPermissions("system:projectZck:view")
    @GetMapping()
    public String zck() {
        return prefix + "/projectZck";
    }

    @PostMapping("/listGroupByType")
    @ResponseBody
    public TableDataInfo list() {
        List<SysProjectZck> sysProjectZckList = sysProjectZckService.selectSysProjectZckByType();
        for (SysProjectZck sysProjectZck : sysProjectZckList) {
            List<SysProjectZck> sysProjectZckList2 = sysProjectZckService.selectProjectZckGroupByType(sysProjectZck.getProjectZckType());
            for (SysProjectZck sysProjectZck2 : sysProjectZckList2) {
                if ("处置中".equals(sysProjectZck2.getZckStatus())) {
                    sysProjectZck.setOngoingCount(sysProjectZck2.getZckStatusCount());
                } else if ("已退出".equals(sysProjectZck2.getZckStatus())) {
                    sysProjectZck.setQuitCount(sysProjectZck2.getZckStatusCount());
                }
            }

            if (StringUtils.isNull(sysProjectZck.getOngoingCount())) {
                sysProjectZck.setOngoingCount(0L);
            }
            if (StringUtils.isNull(sysProjectZck.getQuitCount())) {
                sysProjectZck.setQuitCount(0L);
            }

            //资产库中资产包总量
            sysProjectZck.setTotalZck(Long.valueOf(sysProjectZck.getQuitCount().intValue() + sysProjectZck.getOngoingCount().intValue()));


            //总户数
            SysProject sysProject2 = sysProjectService.selectCountByProjectZckType(sysProjectZck.getProjectZckType());
            sysProjectZck.setSyhs(sysProject2.getProjectCount());

            //计算本金余额总额
            for (String string : sysProjectZck.getProjectZckIds().split(",")) {
                SysProject sysProject = new SysProject();
                sysProject.setProjectZckId(Long.valueOf(string));
                List<SysProject> sysProjectList = sysProjectService.selectProject(sysProject);
                for (SysProject sysProject1 : sysProjectList) {
                    //总本金余额
                    SysProject sysProject3 = sysProjectService.selectTotalPrincipalBalanceByParentId(sysProject1.getProjectId());
                    if (StringUtils.isNotNull(sysProject3) && StringUtils.isNotNull(sysProject3.getTotalPrincipalBalance())) {
                        sysProject1.setTotalPrincipalBalance(sysProject3.getTotalPrincipalBalance());
                    }
                    //现金回现
                    SysRecapture sysRecapture = sysRecaptureService.selectTotalRecaptureByProjectId(sysProject1.getProjectId());
                    if (StringUtils.isNotNull(sysRecapture) && StringUtils.isNotNull(sysRecapture.getTotalRecapture())) {
                        sysProject1.setRecapture(sysRecapture.getTotalRecapture());
                    }

                    if (StringUtils.isNotNull(sysProject1.getRecapture()) && StringUtils.isNotNull(sysProject1.getTotalPrincipalBalance())) {
                        if (sysProject1.getRecapture().compareTo(sysProject1.getTotalPrincipalBalance()) > -1) {
                            //当回现>=总本金余额，本金余额=0，利息余额=利息-（回现-总本金余额）
                            //利息
                            if (sysProject1.getTotalInterest() == null) {
                                sysProject1.setTotalInterest(new BigDecimal(0));
                            }
                            if (sysProject1.getTotalInterestBalance() == null) {
                                sysProject1.setTotalInterestBalance(new BigDecimal(0));
                            }
                            sysProject1.setTotalInterestBalance(sysProject1.getTotalInterest().subtract(sysProject1.getRecapture().subtract(sysProject1.getTotalPrincipalBalance())));
                            sysProject1.setTotalPrincipalBalance(new BigDecimal(0));
                        }
                        if (sysProject1.getRecapture().compareTo(sysProject1.getTotalPrincipalBalance()) == -1) {
                            sysProject1.setTotalInterestBalance(sysProject1.getTotalInterest());
                            sysProject1.setTotalPrincipalBalance(sysProject1.getTotalPrincipalBalance().subtract(sysProject1.getRecapture()));
                        }
                    }

                    if (StringUtils.isNull(sysProjectZck.getCzhx())) {
                        sysProjectZck.setCzhx(new BigDecimal(0));
                    }
                    if (StringUtils.isNull(sysProject1.getRecapture())) {
                        sysProject1.setRecapture(new BigDecimal(0));
                    }
                    sysProjectZck.setCzhx(sysProjectZck.getCzhx().add(sysProject1.getRecapture()));
                    if (StringUtils.isNull(sysProjectZck.getBjye())) {
                        sysProjectZck.setBjye(new BigDecimal(0));
                    }
                    if (StringUtils.isNull(sysProject1.getTotalPrincipalBalance())) {
                        sysProject1.setTotalPrincipalBalance(new BigDecimal(0));
                    }
                    sysProjectZck.setBjye(sysProjectZck.getBjye().add(sysProject1.getTotalPrincipalBalance()));
                }
            }
        }
        return getDataTable(sysProjectZckList);
    }

    @GetMapping("/toProjectZckByType")
    public String toProjectZckByType(String projectZckType, ModelMap modelMap) {
        modelMap.put("projectZckType", projectZckType);
        return prefix + "/projectZckByType";
    }

    @PostMapping("/listByTypeHab")
    @ResponseBody
    public TableDataInfo listByTypeHab(SysProjectZck sysProjectZck) {
        startPage();
        List<SysProjectZck> list = sysProjectZckService.selectSysProjectZckAll(sysProjectZck);
        return getDataTable(list);
    }

    /**
     * 查询项目管理资产库列表
     */
    @RequiresPermissions("system:projectZck:list")
    @PostMapping("/listByType")
    @ResponseBody
    public TableDataInfo listByType(SysProjectZck sysProjectZck) {
        startPage();
        List<SysProjectZck> list = sysProjectZckService.selectSysProjectZckList(sysProjectZck);
        for (SysProjectZck sysProjectZck1 : list) {
            SysProject sysProject = new SysProject();
            sysProject.setProjectZckId(sysProjectZck1.getProjectZckId());
            List<SysProject> sysProjectList = sysProjectService.selectProject(sysProject);
            for (SysProject sysProject1 : sysProjectList) {
                //总本金余额
                //sysProject1.setTotalPrincipalBalance(total(sysProject1));
                SysProject sysProject3 = sysProjectService.selectTotalPrincipalBalanceByParentId(sysProject1.getProjectId());
                if (StringUtils.isNotNull(sysProject3) && StringUtils.isNotNull(sysProject3.getTotalPrincipalBalance())) {
                    sysProject1.setTotalPrincipalBalance(sysProject3.getTotalPrincipalBalance());
                }
                //现金回现
                SysRecapture sysRecapture = sysRecaptureService.selectTotalRecaptureByProjectId(sysProject1.getProjectId());
                if (StringUtils.isNotNull(sysRecapture) && StringUtils.isNotNull(sysRecapture.getTotalRecapture())) {
                    sysProject1.setRecapture(sysRecapture.getTotalRecapture());
                } else {
                    sysProject1.setRecapture(new BigDecimal(0));
                }

                if (StringUtils.isNotNull(sysProject1.getRecapture()) && StringUtils.isNotNull(sysProject1.getTotalPrincipalBalance())) {
                    if (sysProject1.getRecapture().compareTo(sysProject1.getTotalPrincipalBalance()) > -1) {
                        //当回现>=总本金余额，本金余额=0，利息余额=利息-（回现-总本金余额）
                        //利息
                        if (sysProject1.getTotalInterest() == null) {
                            sysProject1.setTotalInterest(new BigDecimal(0));
                        }
                        if (sysProject1.getTotalInterestBalance() == null) {
                            sysProject1.setTotalInterestBalance(new BigDecimal(0));
                        }
                        sysProject1.setTotalInterestBalance(sysProject1.getTotalInterest().subtract(sysProject1.getRecapture().subtract(sysProject1.getTotalPrincipalBalance())));
                        sysProject1.setTotalPrincipalBalance(new BigDecimal(0));
                    }
                    if (sysProject1.getRecapture().compareTo(sysProject1.getTotalPrincipalBalance()) == -1) {
                        sysProject1.setTotalInterestBalance(sysProject1.getTotalInterest());
                        sysProject1.setTotalPrincipalBalance(sysProject1.getTotalPrincipalBalance().subtract(sysProject1.getRecapture()));
                    }
                }

                if (StringUtils.isNull(sysProjectZck1.getCzhx())) {
                    sysProjectZck1.setCzhx(new BigDecimal(0));
                }
                if (StringUtils.isNull(sysProject1.getRecapture())) {
                    sysProject1.setRecapture(new BigDecimal(0));
                }
                sysProjectZck1.setCzhx(sysProjectZck1.getCzhx().add(sysProject1.getRecapture()));
                if (StringUtils.isNull(sysProjectZck1.getBjye())) {
                    sysProjectZck1.setBjye(new BigDecimal(0));
                }
                if (StringUtils.isNull(sysProject1.getTotalPrincipalBalance())) {
                    sysProject1.setTotalPrincipalBalance(new BigDecimal(0));
                }
                sysProjectZck1.setBjye(sysProjectZck1.getBjye().add(sysProject1.getTotalPrincipalBalance()));
            }
            sysProject.setDebtStatus("处置中");
            List<SysProject> sysProjectList1 = sysProjectService.selectProject(sysProject);
            sysProjectZck1.setSyhs(Long.valueOf(sysProjectList1.size()));
        }

        String field = "zckStatus,syhs,zckName";
        String sort = "asc,desc,desc";

        SortListUtil.sort(list, field.split(","), sort.split(","));
        return getDataTable(list);
    }

    //总的本金余额
    public BigDecimal total(SysProject sysProject1) {
        BigDecimal totalPrincipalBalance = new BigDecimal(0);
        List<SysProject> sysProjectsList = sysProjectService.selectSysProjectByParentId(sysProject1);
        for (SysProject sysProject2 : sysProjectsList) {
            SysProject sysProject3 = sysProjectService.selectSysProjectById(sysProject2.getProjectId());
            //本金余额相加
            if (totalPrincipalBalance == null) {
                totalPrincipalBalance = new BigDecimal(0);
            }
            if (sysProject3.getPrincipalBalance() == null) {
                sysProject3.setPrincipalBalance(new BigDecimal(0));
            }
            totalPrincipalBalance = totalPrincipalBalance.add(sysProject3.getPrincipalBalance());
        }
        return totalPrincipalBalance;
    }

    //总现金回现
    public BigDecimal totalRecapture(Long projectId) {
        BigDecimal recapture = new BigDecimal(0);
        List<SysRecapture> sysRecaptureList = sysRecaptureService.selectSysRecaptureByProjectId(projectId);
        for (SysRecapture sysRecapture : sysRecaptureList) {
            if (recapture == null) {
                recapture = new BigDecimal(0);
            }
            if (sysRecapture.getRecapture() == null) {
                sysRecapture.setRecapture(new BigDecimal(0));
            }
            recapture = recapture.add(sysRecapture.getRecapture());
        }
        return recapture;
    }

    /**
     * 根据资产库ID查询项目
     */
    @GetMapping("/projectList")
    public String projectList(String projectZckId, String fwProjectType, String projectZckType, String otherFlag, ModelMap modelMap) {
        String url = "";
        modelMap.put("projectZckId", projectZckId);
        modelMap.put("fwProjectType", fwProjectType);
        modelMap.put("projectZckType", projectZckType);
        modelMap.put("otherFlag", otherFlag);
        if (StringUtils.isNotEmpty(fwProjectType)) {
            if ("investmentProject".equals(fwProjectType)) {
                url = "system/project/projectList";
            } else {
                url = "system/project/investmentProject";
            }
        } else {
            url = "system/project/project";
        }
        return url;
    }

    /**
     * 导出项目管理资产库列表
     */
    @RequiresPermissions("system:projectZck:export")
    @Log(title = "项目管理资产库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysProjectZck sysProjectZck) {
        List<SysProjectZck> list = sysProjectZckService.selectSysProjectZckList(sysProjectZck);
        ExcelUtil<SysProjectZck> util = new ExcelUtil<SysProjectZck>(SysProjectZck.class);
        return util.exportExcel(list, "zck");
    }

    /**
     * 新增项目管理资产库
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存项目管理资产库
     */
    @RequiresPermissions("system:projectZck:add")
    @Log(title = "项目管理资产库", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysProjectZck sysProjectZck) {
        sysProjectZck.setCreateBy(ShiroUtils.getLoginName());
        sysProjectZck.setDelFlag("0");
        return toAjax(sysProjectZckService.insertSysProjectZck(sysProjectZck));
    }

    /**
     * 修改项目管理资产库
     */
    @GetMapping("/edit/{projectZckId}")
    public String edit(@PathVariable("projectZckId") Long projectZckId, ModelMap mmap) {
        SysProjectZck sysProjectZck = sysProjectZckService.selectSysProjectZckById(projectZckId);
        mmap.put("sysProjectZck", sysProjectZck);
        return prefix + "/edit";
    }

    /**
     * 修改保存项目管理资产库
     */
    @RequiresPermissions("system:projectZck:edit")
    @Log(title = "项目管理资产库", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProjectZck sysProjectZck) {
        sysProjectZck.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysProjectZckService.updateSysProjectZck(sysProjectZck));
    }

    /**
     * 删除项目管理资产库
     */
    @RequiresPermissions("system:projectZck:remove")
    @Log(title = "项目管理资产库", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysProjectZckService.deleteSysProjectZckByIds(ids));
    }
}
