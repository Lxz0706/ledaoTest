package com.ledao.web.controller.system;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
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

    @Autowired
    private ISysProjectBailService sysProjectBailService;

    @Autowired
    private ISysProjectMortgageService sysProjectMortgageService;

    @Autowired
    private ISysProjectContractService sysProjectContractService;

    @Autowired
    private ISysProjectPledgeService sysProjectPledgeService;

    @Autowired
    private ISysRecaptureService sysRecaptureService;

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
        for (SysProject sysProject1 : list) {
            //根据父级ID查询出子集
            /*SysProject sysProject2 = new SysProject();
            sysProject2.setProjectId(sysProject1.getProjectId());*/
            List<SysProject> sysProjectsList = sysProjectService.selectSysProjectByParentId(sysProject1);
            for (SysProject sysProject2 : sysProjectsList) {
                //合同本金
                List<SysProjectContract> sysProjectContractList = sysProjectContractService.selectSysProjectContractByProjectId(sysProject2.getProjectId().toString());
                for (SysProjectContract sysProjectContract : sysProjectContractList) {
                    if (sysProject1.getTotalPrice() == null) {
                        sysProject1.setTotalPrice(new BigDecimal(0));
                    }
                    if (sysProjectContract.getCapital() == null) {
                        sysProjectContract.setCapital(new BigDecimal(0));
                    }
                    sysProject1.setTotalPrice(sysProject1.getTotalPrice().add(sysProjectContract.getCapital()));

                    //利息相加
                    if (sysProjectContract.getTotalInterest() == null) {
                        sysProjectContract.setTotalInterest(new BigDecimal(0));
                    }
                    if (sysProject1.getTotalInterest() == null) {
                        sysProject1.setTotalInterest(new BigDecimal(0));
                    }
                    sysProject1.setTotalInterest(sysProject1.getTotalInterest().add(sysProjectContract.getTotalInterest()));
                }
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
                //当回现≥本金，本金余额=0，利息余额=利息-（回现-合同本金）
                if (sysProject1.getRecapture().compareTo(sysProject1.getTotalPrice()) > -1) {
                    sysProject1.setTotalPrincipalBalance(new BigDecimal(0));
                    //利息
                    if (sysProject1.getTotalInterest() == null) {
                        sysProject1.setTotalInterest(new BigDecimal(0));
                    }
                    if (sysProject1.getTotalInterestBalance() == null) {
                        sysProject1.setTotalInterestBalance(new BigDecimal(0));
                    }
                    sysProject1.setTotalInterestBalance(sysProject1.getTotalInterest().subtract(sysProject1.getRecapture().subtract(sysProject1.getTotalPrice())));
                } else if (sysProject1.getRecapture().compareTo(sysProject1.getTotalPrice()) == -1) {
                    //当回现＜本金，合同本金-回现=本金余额
                    sysProject1.setTotalPrincipalBalance(sysProject1.getTotalPrice().subtract(sysProject1.getRecapture()));
                    sysProject1.setTotalInterestBalance(sysProject1.getTotalInterest());
                }
            }

            if (sysProject1.getTotalInterestBalance() == null) {
                sysProject1.setTotalInterestBalance(sysProject1.getTotalInterest());
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
        SysProject sysProject1 = new SysProject();
        if (StringUtils.isNotNull(sysProject.getProjectName())) {
            sysProject1.setProjectName(sysProject.getProjectName());
        }
        List<SysProject> list = sysProjectService.selectSysProjectList(sysProject1);
        Map<String, Long> map = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        for (SysProject sysProject2 : list) {
            map.put(sysProject2.getProjectName(), sysProject2.getProjectId());
            map1.put(sysProject2.getProjectName(), sysProject2.getProjectName());
        }
        if (StringUtils.isNotNull(map1.get(sysProject.getProjectName()))) {
            if (map1.get(sysProject.getProjectName()).equals(sysProject.getProjectName())) {
                sysProject.setParentId(map.get(sysProject.getProjectName()));
            }
        }
       /* if (StringUtils.isNotNull(list)) {
            for (SysProject sysproject : list) {
                sysProject.setParentId(sysproject.getProjectId());
            }
        }*/
        sysProject.setCreateBy(ShiroUtils.getLoginName());
        sysProject.setDelFlag("0");
        sysProjectService.insertSysProject(sysProject);
        //保证人新增
        if (StringUtils.isNotNull(sysProject.getGuarantor())) {
            for (String string1 : sysProject.getGuarantor().split(";")) {
                SysProjectBail sysProjectBail = new SysProjectBail();
                sysProjectBail.setProjectId(sysProject.getProjectId());
                sysProjectBail.setBail(string1);
                sysProjectBail.setCreateBy(ShiroUtils.getLoginName());
                sysProjectBailService.insertSysProjectBail(sysProjectBail);
            }
        }

        //抵押物新增
        if (StringUtils.isNotNull(sysProject.getCollateral())) {
            for (String string1 : sysProject.getCollateral().split(";")) {
                SysProjectMortgage sysProjectMortgage = new SysProjectMortgage();
                sysProjectMortgage.setMortgagor(string1);
                sysProjectMortgage.setProjectId(sysProject.getProjectId());
                sysProjectMortgage.setCreateBy(ShiroUtils.getLoginName());
                sysProjectMortgageService.insertSysProjectMortgage(sysProjectMortgage);
            }
        }

        //质押物新增
        if (StringUtils.isNotNull(sysProject.getPledge())) {
            for (String string1 : sysProject.getPledge().split(";")) {
                SysProjectPledge sysProjectPledge = new SysProjectPledge();
                sysProjectPledge.setPledgor(string1);
                sysProjectPledge.setProjectId(sysProject.getProjectId());
                sysProjectPledge.setCreateBy(ShiroUtils.getLoginName());
                sysProjectPledgeService.insertSysProjectPledge(sysProjectPledge);
            }
        }

        //合同本金
        if (StringUtils.isNotNull(sysProject.getContractPrincipal())) {
            SysProjectContract sysProjectContract = new SysProjectContract();
            sysProjectContract.setCapital(sysProject.getContractPrincipal());
            sysProjectContract.setProjectId(sysProject.getProjectId());
            sysProjectContract.setCreateBy(ShiroUtils.getLoginName());
            sysProjectContractService.insertSysProjectContract(sysProjectContract);
        }

        return toAjax(Integer.parseInt(String.valueOf(sysProject.getProjectId())));
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

        //修改保证人
        List<SysProjectBail> sysProjectBailList = sysProjectBailService.selectSysProjectBailByProjectId(sysProject.getProjectId().toString());
        if (sysProjectBailList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (SysProjectBail sysProjectBail : sysProjectBailList) {
                sb.append(sysProjectBail.getBailId()).append(",");
            }
            sysProjectBailService.deleteSysProjectBailByIds(sb.toString());
            for (String string1 : sysProject.getGuarantor().split(";")) {
                SysProjectBail sysProjectBail = new SysProjectBail();
                sysProjectBail.setProjectId(sysProject.getProjectId());
                sysProjectBail.setBail(string1);
                sysProjectBail.setCreateBy(ShiroUtils.getLoginName());
                sysProjectBailService.insertSysProjectBail(sysProjectBail);
            }
        } else {
            for (String string1 : sysProject.getGuarantor().split(";")) {
                SysProjectBail sysProjectBail = new SysProjectBail();
                sysProjectBail.setProjectId(sysProject.getProjectId());
                sysProjectBail.setBail(string1);
                sysProjectBail.setCreateBy(ShiroUtils.getLoginName());
                sysProjectBailService.insertSysProjectBail(sysProjectBail);
            }
        }


        //修改抵押物
        List<SysProjectMortgage> sysProjectMortgageList = sysProjectMortgageService.selectSysProjectMortgageByProjectId(sysProject.getProjectId().toString());
        if (sysProjectMortgageList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (SysProjectMortgage sysProjectMortgage : sysProjectMortgageList) {
                sb.append(sysProjectMortgage.getMortgageId()).append(",");
            }
            sysProjectMortgageService.deleteSysProjectMortgageByIds(sb.toString());
            for (String string1 : sysProject.getCollateral().split(";")) {
                SysProjectMortgage sysProjectMortgage = new SysProjectMortgage();
                sysProjectMortgage.setMortgagor(string1);
                sysProjectMortgage.setProjectId(sysProject.getProjectId());
                sysProjectMortgage.setCreateBy(ShiroUtils.getLoginName());
                sysProjectMortgageService.insertSysProjectMortgage(sysProjectMortgage);
            }
        } else {
            for (String string1 : sysProject.getCollateral().split(";")) {
                SysProjectMortgage sysProjectMortgage = new SysProjectMortgage();
                sysProjectMortgage.setMortgagor(string1);
                sysProjectMortgage.setProjectId(sysProject.getProjectId());
                sysProjectMortgage.setCreateBy(ShiroUtils.getLoginName());
                sysProjectMortgageService.insertSysProjectMortgage(sysProjectMortgage);
            }
        }

        //修改质押物
        List<SysProjectPledge> sysProjectPledgeList = sysProjectPledgeService.selectPledgeByProjectId(sysProject.getProjectId().toString());
        if (sysProjectMortgageList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (SysProjectPledge sysProjectPledge : sysProjectPledgeList) {
                sb.append(sysProjectPledge.getPledgeId()).append(",");
            }
            sysProjectPledgeService.deleteSysProjectPledgeByIds(sb.toString());
            for (String string1 : sysProject.getPledge().split(";")) {
                SysProjectPledge sysProjectPledge = new SysProjectPledge();
                sysProjectPledge.setPledgor(string1);
                sysProjectPledge.setProjectId(sysProject.getProjectId());
                sysProjectPledge.setCreateBy(ShiroUtils.getLoginName());
                sysProjectPledgeService.insertSysProjectPledge(sysProjectPledge);
            }
        } else {
            for (String string1 : sysProject.getPledge().split(";")) {
                SysProjectPledge sysProjectPledge = new SysProjectPledge();
                sysProjectPledge.setPledgor(string1);
                sysProjectPledge.setProjectId(sysProject.getProjectId());
                sysProjectPledge.setCreateBy(ShiroUtils.getLoginName());
                sysProjectPledgeService.insertSysProjectPledge(sysProjectPledge);
            }
        }

        //修改合同本金
        SysProjectContract sysProjectContract = sysProjectContractService.selectProjectContractByProjectId(sysProject.getProjectId());
        if (StringUtils.isNotNull(sysProjectContract)) {
            sysProjectContract.setCapital(sysProject.getContractPrincipal());
            sysProjectContract.setUpdateBy(ShiroUtils.getLoginName());
            sysProjectContractService.updateSysProjectContract(sysProjectContract);
        } else {
            sysProjectContract = new SysProjectContract();
            sysProjectContract.setCapital(sysProject.getContractPrincipal());
            sysProjectContract.setProjectId(sysProject.getProjectId());
            sysProjectContract.setCreateBy(ShiroUtils.getLoginName());
            sysProjectContractService.insertSysProjectContract(sysProjectContract);
        }

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
        StringBuffer sb = new StringBuffer();
        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        StringBuffer sb3 = new StringBuffer();
        StringBuffer sb4 = new StringBuffer();

        for (String string : ids.split(",")) {
            SysProject sysProject = new SysProject();
            sysProject.setProjectId(Long.valueOf(string));
            List<SysProject> list = sysProjectService.selectSysProjectByParentId(sysProject);
            for (SysProject sysProject1 : list) {
                sb.append(sysProject1.getProjectId()).append(",");
            }
        }

        String id = sb.deleteCharAt(sb.length() - 1).toString();


        //删除保证人
        List<SysProjectBail> sysProjectBailList = sysProjectBailService.selectSysProjectBailByProjectId(id);
        if (sysProjectBailList.size() > 0) {
            for (SysProjectBail sysProjectBail : sysProjectBailList) {
                sb1.append(sysProjectBail.getBailId()).append(",");
            }
            sysProjectBailService.deleteSysProjectBailByIds(sb1.deleteCharAt(sb1.length() - 1).toString());
        }

        //删除抵押物
        List<SysProjectMortgage> sysProjectMortgageList = sysProjectMortgageService.selectSysProjectMortgageByProjectId(id);
        if (sysProjectMortgageList.size() > 0) {
            for (SysProjectMortgage sysProjectMortgage : sysProjectMortgageList) {
                sb2.append(sysProjectMortgage.getMortgageId()).append(",");
            }
            sysProjectMortgageService.deleteSysProjectMortgageByIds(sb2.deleteCharAt(sb2.length() - 1).toString());
        }

        //删除质押物
        List<SysProjectPledge> sysProjectPledgeList = sysProjectPledgeService.selectPledgeByProjectId(id);
        if (sysProjectPledgeList.size() > 0) {
            for (SysProjectPledge sysProjectPledge : sysProjectPledgeList) {
                sb3.append(sysProjectPledge.getPledgeId()).append(",");
            }
            sysProjectPledgeService.deleteSysProjectPledgeByIds(sb3.deleteCharAt(sb3.length() - 1).toString());
        }

        //删除合同本金
        List<SysProjectContract> sysProjectContractList = sysProjectContractService.selectSysProjectContractByProjectId(id);
        if (sysProjectContractList.size() > 0) {
            for (SysProjectContract sysProjectContract : sysProjectContractList) {
                sb4.append(sysProjectContract.getContractId()).append(",");
            }
            sysProjectContractService.deleteSysProjectContractByIds(sb4.deleteCharAt(sb4.length() - 1).toString());
        }


        return toAjax(sysProjectService.deleteSysProjectByIds(id));
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
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
        SysProject sysProject = sysProjectService.selectSysProjectById(id);
        if (StringUtils.isNotNull(sysProject.getContractPrincipal())) {
            sysProject.setContractPrincipals(decimalFormat.format(sysProject.getContractPrincipal()));
        }

        if (StringUtils.isNotNull(sysProject.getPrincipalBalance())) {
            sysProject.setPrincipalBalances(decimalFormat.format(sysProject.getPrincipalBalance()));
        }

        if (StringUtils.isNotNull(sysProject.getInterestBalance())) {
            sysProject.setInterestBalances(decimalFormat.format(sysProject.getInterestBalance()));
        }

        if (StringUtils.isNotNull(sysProject.getPrincipalInterestBalance())) {
            sysProject.setPrincipalInterestBalances(decimalFormat.format(sysProject.getPrincipalInterestBalance()));
        }

        mmap.put("sysProject", sysProject);
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

    /**
     * 查询所有
     */
    @RequiresPermissions("system:project:list")
    @GetMapping({"/queryAll"})
    public String queryAll(ModelMap modelMap, SysProject sysProject) {
        modelMap.put("sysProject", sysProject);
        return "system/project/queryAll";
    }

    @RequiresPermissions("system:project:export")
    @Log(title = "项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export1")
    @ResponseBody
    public AjaxResult export1(SysProject sysProject) {
        String projectName = getRequest().getParameter("projectName");
        String assetStatus = getRequest().getParameter("assetStatus");
        List<SysProject> sysProjectList = new ArrayList<>();
        if (StringUtils.isNull(getRequest().getParameter("ids"))) {
            sysProject.setProjectName(projectName);
            //sysProject.setAssetStatus(assetStatus);
            sysProjectList = sysProjectService.selectSysProjectList(sysProject);
        } else {
            sysProjectList = sysProjectService.selectSysProjectByProjectId(getRequest().getParameter("ids"));
        }

        ExcelUtil<SysProject> util = new ExcelUtil<SysProject>(SysProject.class);
        return util.exportExcel(sysProjectList, "项目");
    }

    @RequiresPermissions("system:project:list")
    @PostMapping("/lists")
    @ResponseBody
    public TableDataInfo lists(SysProject sysProject) {
        startPage();
        List<SysProject> list = sysProjectService.selectSysProjectList(sysProject);
        StringBuffer sb = new StringBuffer();
        StringBuffer sb1 = new StringBuffer();
        if (list.size() > 0) {
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
                    }
                }
            }
        }
        return getDataTable(list);
    }


    /**
     * 选择项目树
     */
    @GetMapping("/selectProjectTree")
    public String selectCustomerTree(String selectedProjectIds, String selectedProjectNames, Boolean multiSelectFlag, ModelMap mmap) {
        mmap.put("selectedProjectIds", selectedProjectIds);
        mmap.put("selectedProjectNames", selectedProjectNames);
        mmap.put("multiSelectFlag", multiSelectFlag);
        List<SysRole> sysRoleList = ShiroUtils.getSysUser().getRoles();
        for (SysRole sysRole : sysRoleList) {
            if ("thbManager".equals(sysRole.getRoleKey()) || "thbCommon".equals(sysRole.getRoleKey())) {
                mmap.put("role", "thb");
            } else if ("bgczManager".equals(sysRole.getRoleKey()) || "bgczCommon".equals(sysRole.getRoleKey())) {
                mmap.put("role", "tzb");
            } else if ("investmentManager".equals(sysRole.getRoleKey()) || "investmentCommon".equals(sysRole.getRoleKey())) {
                mmap.put("role", "bgcz");
            }
        }
        return prefix + "/tree";
    }

    /**
     * 客户选择器
     */
    @GetMapping("/listForTree")
    @ResponseBody
    public String listForTree(SysProject sysProject) {
        List<SysProject> sysProjectList = sysProjectService.selectSysProjectList(sysProject);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("sysProjectList", sysProjectList);
        return jsonObject.toString();
    }
}
