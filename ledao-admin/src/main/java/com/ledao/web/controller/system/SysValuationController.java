package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysValuationModel;
import com.ledao.system.service.ISysUserService;
import com.ledao.system.service.ISysValuationModelService;
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
import com.ledao.system.dao.SysValuation;
import com.ledao.system.service.ISysValuationService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 估值计算主Controller
 *
 * @author lxz
 * @date 2022-11-11
 */
@Controller
@RequestMapping("/system/valuation")
public class SysValuationController extends BaseController {
    private String prefix = "system/valuation";

    @Autowired
    private ISysValuationService sysValuationService;

    @Autowired
    private ISysValuationModelService sysValuationModelService;

    @Autowired
    private ISysUserService userService;

    //@RequiresPermissions("system:valuation:view")
    @GetMapping("/toList")
    public String valuation(String itemId, ModelMap mmap) {
        mmap.put("itemId", itemId);
        return prefix + "/valuation";
    }

    /**
     * 查询估值计算主列表
     */
    //@RequiresPermissions("system:valuation:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysValuation sysValuation) {
        startPage();
        sysValuation.setCreateBy(ShiroUtils.getLoginName());
        List<SysValuation> list = sysValuationService.selectSysValuationList(sysValuation);
        for (SysValuation sysValuation1 : list) {
            if (StringUtils.isNotEmpty(sysValuation1.getCreateBy())) {
                sysValuation1.setCreator(userService.selectUserByLoginName(sysValuation1.getCreateBy()).getUserName());
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出估值计算主列表
     */
    @RequiresPermissions("system:valuation:export")
    @Log(title = "估值计算主", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysValuation sysValuation) {
        List<SysValuation> list = sysValuationService.selectSysValuationList(sysValuation);
        ExcelUtil<SysValuation> util = new ExcelUtil<SysValuation>(SysValuation.class);
        return util.exportExcel(list, "valuation");
    }

    /**
     * 新增估值计算主
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存估值计算主
     */
    @RequiresPermissions("system:valuation:add")
    @Log(title = "估值计算主", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysValuation sysValuation) {
        return toAjax(sysValuationService.insertSysValuation(sysValuation));
    }

    /**
     * 修改估值计算主
     */
    @GetMapping("/edit/{valuationId}")
    public String edit(@PathVariable("valuationId") Long valuationId, ModelMap mmap) {
        SysValuation sysValuation = sysValuationService.selectSysValuationById(valuationId);
        mmap.put("sysValuation", sysValuation);
        return prefix + "/edit";
    }

    /**
     * 修改保存估值计算主
     */
    @RequiresPermissions("system:valuation:edit")
    @Log(title = "估值计算主", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysValuation sysValuation) {
        return toAjax(sysValuationService.updateSysValuation(sysValuation));
    }

    /**
     * 删除估值计算主
     */
    @RequiresPermissions("system:valuation:remove")
    @Log(title = "估值计算主", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysValuationService.deleteSysValuationByIds(ids));
    }

//    @GetMapping("/detail")
//    public String detail(Long valuationId, ModelMap modelMap) {
//        SysValuationModel sysValuationModel = new SysValuationModel();
//        sysValuationModel.setMainId(valuationId);
//        List<SysValuationModel> list = sysValuationModelService.selectSysValuationModelList(sysValuationModel);
//        modelMap.put("list", list);
//        return prefix + "/detail";
//    }

    @GetMapping("/toDetail/{mainId}")
    public String toDetail(@PathVariable("mainId") Long mainId, ModelMap modelMap) {
        modelMap.put("mainId", mainId);
        SysValuationModel sysValuationModel = new SysValuationModel();
        sysValuationModel.setMainId(mainId);
        List<SysValuationModel> list = sysValuationModelService.selectSysValuationModelList(sysValuationModel);
        modelMap.put("list", list);
        return "system/valuationModel/list";
    }

    @GetMapping("/detail/{mainId}")
    public String detail(@PathVariable("mainId") Long mainId, ModelMap modelMap) {
        modelMap.put("mainId", mainId);
        return "system/valuationModel/list";
    }

    @PostMapping("/detail2")
    @ResponseBody
    public AjaxResult detail2(SysValuation sysValuation) {
        SysValuationModel sysValuationModel = new SysValuationModel();
        sysValuationModel.setMainId(sysValuation.getValuationId());
        List<SysValuationModel> list = sysValuationModelService.selectSysValuationModelList(sysValuationModel);
        return AjaxResult.success(list);
    }
}
