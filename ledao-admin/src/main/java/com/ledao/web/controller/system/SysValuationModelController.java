package com.ledao.web.controller.system;

import com.ledao.common.annotation.Log;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.core.page.TableDataInfo;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.utils.GisUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysJudicial;
import com.ledao.system.dao.SysValuation;
import com.ledao.system.dao.SysValuationModel;
import com.ledao.system.service.ISysJudicialService;
import com.ledao.system.service.ISysValuationModelService;
import com.ledao.system.service.ISysValuationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lxz
 * @date 2022/8/16
 */
@Controller
@RequestMapping("/system/valuationModel")
public class SysValuationModelController extends BaseController {
    @Autowired
    private ISysValuationModelService sysValuationModelService;

    @Autowired
    private ISysJudicialService sysJudicialService;

    @Autowired
    private ISysValuationService sysValuationService;

    private String prefix = "system/valuationModel";

    @GetMapping()
    public String valuationmap() {
        return prefix + "/valuationModel";
    }

    @Log(title = "估值计算", businessType = BusinessType.OTHER)
    @GetMapping("/valuationModel")
    public String valuationModel(String itemId, String latLon, ModelMap modelMap) {
        SysJudicial sysJudicial1 = sysJudicialService.selectSysJudicialById(itemId);
        if (StringUtils.isNull(sysJudicial1.getItemConsultprice())) {
            modelMap.put("itemMarketPrice", sysJudicial1.getItemMarketprice());
        } else {
            modelMap.put("itemMarketPrice", sysJudicial1.getItemConsultprice());
        }
        modelMap.put("floorSpace", StringUtils.isNull(sysJudicial1.getFloorSpace()) ? Long.valueOf(0) : sysJudicial1.getFloorSpace());
        modelMap.put("itemCurrentprice", sysJudicial1.getItemCurrentprice());
        modelMap.put("itemType", sysJudicial1.getItemType());
        modelMap.put("itemStatus", sysJudicial1.getItemStatus());
        modelMap.put("latLon", latLon);
        modelMap.put("itemId", itemId);
        return prefix + "/valuationModel";
    }

    @GetMapping("/toList")
    public String toList(String itemId, ModelMap mmap) {
        mmap.put("itemId", itemId);
        return "system/valuation/valuation";
    }

    /**
     * 查询估值计算列表
     */
    //@RequiresPermissions("system:valuationModel:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysValuationModel sysValuationModel) {
        startPage();
        List<SysValuationModel> list = sysValuationModelService.selectSysValuationModelList(sysValuationModel);
        return getDataTable(list);
    }

    /**
     * 导出估值计算列表
     */
    @RequiresPermissions("system:valuationModel:export")
    @Log(title = "估值计算", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysValuationModel sysValuationModel) {
        List<SysValuationModel> list = sysValuationModelService.selectSysValuationModelList(sysValuationModel);
        ExcelUtil<SysValuationModel> util = new ExcelUtil<SysValuationModel>(SysValuationModel.class);
        return util.exportExcel(list, "valuationModel");
    }

    /**
     * 新增估值计算
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存估值计算
     */
    //@RequiresPermissions("system:valuationModel:add")
    @Log(title = "估值计算", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysValuationModel sysValuationModel) {
        //sysValuationModel.setCreateBy(ShiroUtils.getLoginName());
        sysValuationModelService.insertSysValuationModel(sysValuationModel);
        return AjaxResult.success(sysValuationModel.getValuationId());
    }

    /**
     * 修改估值计算
     */
    @GetMapping("/edit/{valuationId}")
    public String edit(@PathVariable("valuationId") Long valuationId, ModelMap mmap) {
        SysValuationModel sysValuationModel = sysValuationModelService.selectSysValuationModelById(valuationId);
        mmap.put("sysValuationModel", sysValuationModel);
        return prefix + "/edit";
    }

    /**
     * 修改保存估值计算
     */
    //@RequiresPermissions("system:valuationModel:edit")
    @Log(title = "估值计算", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysValuationModel sysValuationModel) {
        sysValuationModel.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysValuationModelService.updateSysValuationModel(sysValuationModel));
    }

    /**
     * 删除估值计算
     */
    //@RequiresPermissions("system:valuationModel:remove")
    @Log(title = "估值计算", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysValuationModelService.deleteSysValuationModelByIds(ids));
    }

    @PostMapping("/addMain")
    @ResponseBody
    public AjaxResult addMain(SysValuation sysValuation) {
        sysValuation.setCreateBy(ShiroUtils.getLoginName());
        sysValuationService.insertSysValuation(sysValuation);
        return AjaxResult.success(sysValuation.getValuationId());
    }
}
