package com.ledao.web.controller.system;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysProject;
import com.ledao.system.dao.SysZck;
import com.ledao.system.service.ISysDictTypeService;
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
import com.ledao.system.dao.SysBgczzck;
import com.ledao.system.service.ISysBgczzckService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 重组并购项目信息库Controller
 *
 * @author lxz
 * @date 2020-06-16
 */
@Controller
@RequestMapping("/system/bgczzck")
public class SysBgczzckController extends BaseController {
    private String prefix = "system/bgczzck";

    @Autowired
    private ISysBgczzckService sysBgczzckService;

    @Autowired
    private ISysDictTypeService dictTypeService;

    @RequiresPermissions("system:bgczzck:view")
    @GetMapping()
    public String bgczzck() {
        return prefix + "/bgczzck";
    }

    /**
     * 查询重组并购项目信息库列表
     */
    @RequiresPermissions("system:bgczzck:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        startPage();
        List<SysBgczzck> list = sysBgczzckService.selectByProjectStatus();
        return getDataTable(list);
    }

    /**
     * 根据项目状态查询项目
     */
    @RequiresPermissions("system:bgczzck:list")
    @GetMapping({"/selectByProjectStatus/{projectStatus}"})
    public String selectZcbByAssetStatus(@PathVariable("projectStatus") String projectStatus, ModelMap modelMap) {
        modelMap.put("projectStatus", projectStatus);
        return "system/bgczzck/bgczzckList";
    }

    @RequiresPermissions("system:bgczzck:list")
    @PostMapping("/lists")
    @ResponseBody
    public TableDataInfo lists(SysBgczzck sysBgczzck) {
        startPage();
        List<SysBgczzck> list = sysBgczzckService.selectSysBgczzckList(sysBgczzck);
        return getDataTable(list);
    }

    /**
     * 导出重组并购项目信息库列表
     */
    @RequiresPermissions("system:bgczzck:export")
    @Log(title = "重组并购项目信息库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysBgczzck sysBgczzck) {
        String id = getRequest().getParameter("ids");
        String projectStatus = getRequest().getParameter("projectStatus");
        sysBgczzck.setProjectStatus(projectStatus);
        logger.info(sysBgczzck.getProjectStatus());
        List<SysBgczzck> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotNull(id)) {
            list = sysBgczzckService.selectByIds(id);
        } else {
            list = sysBgczzckService.selectSysBgczzckList(sysBgczzck);
        }

        ExcelUtil<SysBgczzck> util = new ExcelUtil<SysBgczzck>(SysBgczzck.class);
        return util.exportExcel(list, "大型单体项目");
    }

    /**
     * 新增重组并购项目信息库
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存重组并购项目信息库
     */
    @RequiresPermissions("system:bgczzck:add")
    @Log(title = "重组并购项目信息库", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysBgczzck sysBgczzck) {
        sysBgczzck.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysBgczzckService.insertSysBgczzck(sysBgczzck));
    }

    /**
     * 修改重组并购项目信息库
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysBgczzck sysBgczzck = sysBgczzckService.selectSysBgczzckById(id);
        mmap.put("sysBgczzck", sysBgczzck);
        return prefix + "/edit";
    }

    /**
     * 修改保存重组并购项目信息库
     */
    @RequiresPermissions("system:bgczzck:edit")
    @Log(title = "重组并购项目信息库", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysBgczzck sysBgczzck) {
        sysBgczzck.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysBgczzckService.updateSysBgczzck(sysBgczzck));
    }

    /**
     * 删除重组并购项目信息库
     */
    @RequiresPermissions("system:bgczzck:remove")
    @Log(title = "重组并购项目信息库", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        logger.info("ids:====" + ids);
        return toAjax(sysBgczzckService.deleteSysBgczzckByIds(ids));
    }

    /**
     * 查看详细
     */
    @RequiresPermissions("system:bgczzck:detail")
    @Log(title = "重组并购项目信息库", businessType = BusinessType.DETAIL)
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mmap) {
        SysBgczzck sysBgczzck = sysBgczzckService.selectSysBgczzckById(id);
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
        if (StringUtils.isNotNull(sysBgczzck.getAcquisitionCost())) {
            sysBgczzck.setAcquisitionCosts(decimalFormat.format(sysBgczzck.getAcquisitionCost()));
        }

        if (StringUtils.isNotNull(sysBgczzck.getContractPrincipal())) {
            sysBgczzck.setContractPrincipals(decimalFormat.format(sysBgczzck.getContractPrincipal()));
        }

        if (StringUtils.isNotNull(sysBgczzck.getPrincipalBalance())) {
            sysBgczzck.setPrincipalBalances(decimalFormat.format(sysBgczzck.getPrincipalBalance()));
        }

        if (StringUtils.isNotNull(sysBgczzck.getInterestBalance())) {
            sysBgczzck.setInterestBalances(decimalFormat.format(sysBgczzck.getInterestBalance()));
        }

        if (StringUtils.isNotNull(sysBgczzck.getPrincipalInterestBalance())) {
            sysBgczzck.setPrincipalInterestBalances(decimalFormat.format(sysBgczzck.getPrincipalInterestBalance()));
        }

        if (StringUtils.isNotNull(sysBgczzck.getLandUnitPrice())) {
            sysBgczzck.setLandUnitPrices(decimalFormat.format(sysBgczzck.getLandUnitPrice()));
        }

        if (StringUtils.isNotNull(sysBgczzck.getLandTotalPrice())) {
            sysBgczzck.setLandTotalPrices(decimalFormat.format(sysBgczzck.getLandTotalPrice()));
        }

        if (StringUtils.isNotNull(sysBgczzck.getTotalPrice())) {
            sysBgczzck.setTotalPrices(decimalFormat.format(sysBgczzck.getTotalPrice()));
        }

        if (StringUtils.isNotNull(sysBgczzck.getContractAmount()) && StringUtils.isNotEmpty(sysBgczzck.getContractAmount())) {
            sysBgczzck.setContractAmount(decimalFormat.format(new BigDecimal(sysBgczzck.getContractAmount())));
        }
        if (StringUtils.isNotNull(sysBgczzck.getBuildValuation()) && StringUtils.isNotEmpty(sysBgczzck.getBuildValuation())) {
            sysBgczzck.setBuildValuation(decimalFormat.format(new BigDecimal(sysBgczzck.getBuildValuation())));
        }
        if (StringUtils.isNotNull(sysBgczzck.getCashBackAmount()) && StringUtils.isNotEmpty(sysBgczzck.getCashBackAmount())) {
            sysBgczzck.setCashBackAmount(decimalFormat.format(new BigDecimal(sysBgczzck.getCashBackAmount())));
        }
        mmap.put("sysBgczzck", sysBgczzck);
        return prefix + "/detail";
    }

    @Log(title = "重组并购项目信息库", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:bgczzck:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysBgczzck> util = new ExcelUtil<SysBgczzck>(SysBgczzck.class);
        List<SysBgczzck> sysBgczzckList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = sysBgczzckService.importBgczzk(sysBgczzckList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("system:bgczzck:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<SysBgczzck> util = new ExcelUtil<SysBgczzck>(SysBgczzck.class);
        return util.importTemplateExcel("重组并购项目信息库");
    }

    @RequiresPermissions("system:bgczzck:list")
    @GetMapping({"/queryAll"})
    public String queryAll(ModelMap modelMap, SysBgczzck sysBgczzck) {
        modelMap.put("sysBgczzck", sysBgczzck);
        return "system/bgczzck/queryAll";
    }

    @RequiresPermissions("system:bgczzck:list")
    @PostMapping("/listes")
    @ResponseBody
    public TableDataInfo listes(SysBgczzck sysBgczzck) {
        startPage();
        List<SysBgczzck> list = sysBgczzckService.selectSysBgczzckList(sysBgczzck);
        return getDataTable(list);
    }

    /**
     * 客户选择器
     */
    @GetMapping("/listForTree")
    @ResponseBody
    public String listForTree(SysBgczzck sysBgczzck) {
        List<SysBgczzck> list = sysBgczzckService.selectSysBgczzckList(sysBgczzck);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("list", list);
        return jsonObject.toString();
    }
}
