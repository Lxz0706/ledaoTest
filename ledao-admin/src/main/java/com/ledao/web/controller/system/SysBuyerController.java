package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysBgczzck;
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
import com.ledao.system.dao.SysBuyer;
import com.ledao.system.service.ISysBuyerService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 购房人基本信息Controller
 * 
 * @author lxz
 * @date 2023-12-27
 */
@Controller
@RequestMapping("/system/buyer")
public class SysBuyerController extends BaseController
{
    private String prefix = "system/buyer";

    @Autowired
    private ISysBuyerService sysBuyerService;

    @RequiresPermissions("system:buyer:view")
    @GetMapping()
    public String buyer()
    {
        return prefix + "/buyer";
    }

    /**
     * 查询购房人基本信息列表
     */
    @RequiresPermissions("system:buyer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysBuyer sysBuyer)
    {
        startPage();
        List<SysBuyer> list = sysBuyerService.selectSysBuyerList(sysBuyer);
        return getDataTable(list);
    }

    /**
     * 导出购房人基本信息列表
     */
    @RequiresPermissions("system:buyer:export")
    @Log(title = "购房人基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysBuyer sysBuyer)
    {
        List<SysBuyer> list = sysBuyerService.selectSysBuyerList(sysBuyer);
        ExcelUtil<SysBuyer> util = new ExcelUtil<SysBuyer>(SysBuyer.class);
        return util.exportExcel(list, "buyer");
    }

    /**
     * 新增购房人基本信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存购房人基本信息
     */
    @RequiresPermissions("system:buyer:add")
    @Log(title = "购房人基本信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysBuyer sysBuyer)
    {
        return toAjax(sysBuyerService.insertSysBuyer(sysBuyer));
    }

    /**
     * 修改购房人基本信息
     */
    @GetMapping("/edit/{buyerId}")
    public String edit(@PathVariable("buyerId") Long buyerId, ModelMap mmap)
    {
        SysBuyer sysBuyer = sysBuyerService.selectSysBuyerById(buyerId);
        mmap.put("sysBuyer", sysBuyer);
        return prefix + "/edit";
    }

    /**
     * 修改保存购房人基本信息
     */
    @RequiresPermissions("system:buyer:edit")
    @Log(title = "购房人基本信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysBuyer sysBuyer)
    {
        return toAjax(sysBuyerService.updateSysBuyer(sysBuyer));
    }

    /**
     * 删除购房人基本信息
     */
    @RequiresPermissions("system:buyer:remove")
    @Log(title = "购房人基本信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysBuyerService.deleteSysBuyerByIds(ids));
    }

    @Log(title = "重组并购项目信息库", businessType = BusinessType.IMPORT)
    //@RequiresPermissions("system:bgczzck:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysBuyer> util = new ExcelUtil<SysBuyer>(SysBuyer.class);
        List<SysBuyer> sysBgczzckList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = sysBuyerService.importBuyer(sysBgczzckList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    // @RequiresPermissions("system:bgczzck:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<SysBuyer> util = new ExcelUtil<SysBuyer>(SysBuyer.class);
        return util.importTemplateExcel("购房人信息");
    }
}
