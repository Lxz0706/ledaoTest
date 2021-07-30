package com.ledao.web.controller.system;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.ledao.common.core.page.PageDao;
import com.ledao.common.core.page.TableSupport;
import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.*;
import com.ledao.system.service.ISysProjectContractService;
import com.ledao.system.service.ISysProjectService;
import com.ledao.system.service.ISysRecaptureService;
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
import com.ledao.system.service.ISysProjectZckService;
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

    @RequiresPermissions("system:projectZck:view")
    @GetMapping()
    public String zck() {
        return prefix + "/projectZck";
    }

    /**
     * 查询项目管理资产库列表
     */
    @RequiresPermissions("system:projectZck:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysProjectZck sysProjectZck) {
        startPage();
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
        List<SysProjectZck> list = sysProjectZckService.selectSysProjectZckList(sysProjectZck);
        for (SysProjectZck sysProjectZck1 : list) {
            SysProject sysProject = new SysProject();
            sysProject.setProjectZckId(sysProjectZck1.getProjectZckId());
            List<SysProject> sysProjectList = sysProjectService.selectProject(sysProject);
            for (SysProject sysProject1 : sysProjectList) {
                //根据父级ID查询出子集
                List<SysProject> sysProjectsList = sysProjectService.selectSysProjectByParentId(sysProject1);
                for (SysProject sysProject2 : sysProjectsList) {
                    /*//合同本金
                    List<SysProjectContract> sysProjectContractList = sysProjectContractService.selectSysProjectContractByProjectId(sysProject2.getProjectId().toString());
                    for (SysProjectContract sysProjectContract : sysProjectContractList) {
                        if (sysProject1.getTotalPrice() == null) {
                            sysProject1.setTotalPrice(new BigDecimal(0));
                        }
                        if (sysProjectContract.getCapital() == null) {
                            sysProjectContract.setCapital(new BigDecimal(0));
                        }
                        sysProject1.setTotalPrice(sysProject1.getTotalPrice().add(sysProjectContract.getCapital()));
                    }*/
                    SysProject sysProject3 = sysProjectService.selectSysProjectById(sysProject2.getProjectId());
                    //本金余额相加
                    if (sysProject1.getTotalPrincipalBalance() == null) {
                        sysProject1.setTotalPrincipalBalance(new BigDecimal(0));
                    }
                    if (sysProject3.getPrincipalBalance() == null) {
                        sysProject3.setPrincipalBalance(new BigDecimal(0));
                    }
                    sysProject1.setTotalPrincipalBalance(sysProject1.getTotalPrincipalBalance().add(sysProject3.getPrincipalBalance()));
                }

                //现金回现
                List<SysRecapture> sysRecaptureList = sysRecaptureService.selectSysRecaptureByProjectId(sysProject1.getProjectId());
                for (SysRecapture sysRecapture : sysRecaptureList) {
                    if (sysProject1.getRecapture() == null) {
                        sysProject1.setRecapture(new BigDecimal(0));
                    }
                    if (sysRecapture.getRecapture() == null) {
                        sysRecapture.setRecapture(new BigDecimal(0));
                    }
                    sysProject1.setRecapture(sysProject1.getRecapture().add(sysRecapture.getRecapture()));
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

            if (StringUtils.isNotNull(sysProjectZck1.getBjye())) {
                sysProjectZck1.setBjyes(decimalFormat.format(sysProjectZck1.getBjye()));
            }
            if (StringUtils.isNotNull(sysProjectZck1.getCzhx())) {
                sysProjectZck1.setCzhxs(decimalFormat.format(sysProjectZck1.getCzhx()));
            }
            sysProject.setDebtStatus("处置中");
            List<SysProject> sysProjectList1 = sysProjectService.selectProject(sysProject);
            sysProjectZck1.setSyhs(Long.valueOf(sysProjectList1.size()));
        }
        return getDataTable(list);
    }

    /**
     * 根据资产库ID查询项目
     */
    @GetMapping("/projectList")
    public String projectList(String projectZckId, String fwProjectType, ModelMap modelMap) {
        String url = "";
        modelMap.put("projectZckId", projectZckId);
        modelMap.put("fwProjectType", fwProjectType);
        if (StringUtils.isNotEmpty(fwProjectType)) {
            if ("investmentProject".equals(fwProjectType)) {
                url = "system/project/projectZck";
            } else {
                modelMap.put("otherFlag", "N");
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
        logger.info("资产库名称：====" + sysProjectZck.getZckName());
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
