package com.ledao.web.controller.system;

import java.util.List;
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
import com.ledao.system.dao.JudicialUpdata;
import com.ledao.system.service.IJudicialUpdataService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 司法拍卖项目Controller
 * 
 * @author lxz
 * @date 2021-09-30
 */
@Controller
@RequestMapping("/system/judicialUpdata")
public class JudicialUpdataController extends BaseController
{
    private String prefix = "system/judicialUpdata";

    @Autowired
    private IJudicialUpdataService judicialUpdataService;

    @RequiresPermissions("system:judicialUpdata:view")
    @GetMapping()
    public String judicialUpdata()
    {
        return prefix + "/judicialUpdata";
    }

    /**
     * 查询司法拍卖项目列表
     */
    @RequiresPermissions("system:judicialUpdata:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(JudicialUpdata judicialUpdata)
    {
        startPage();
        List<JudicialUpdata> list = judicialUpdataService.selectJudicialUpdataList(judicialUpdata);
        return getDataTable(list);
    }

    /**
     * 导出司法拍卖项目列表
     */
    @RequiresPermissions("system:judicialUpdata:export")
    @Log(title = "司法拍卖项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(JudicialUpdata judicialUpdata)
    {
        List<JudicialUpdata> list = judicialUpdataService.selectJudicialUpdataList(judicialUpdata);
        ExcelUtil<JudicialUpdata> util = new ExcelUtil<JudicialUpdata>(JudicialUpdata.class);
        return util.exportExcel(list, "judicialUpdata");
    }

    /**
     * 新增司法拍卖项目
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存司法拍卖项目
     */
    @RequiresPermissions("system:judicialUpdata:add")
    @Log(title = "司法拍卖项目", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(JudicialUpdata judicialUpdata)
    {
        return toAjax(judicialUpdataService.insertJudicialUpdata(judicialUpdata));
    }

    /**
     * 修改司法拍卖项目
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        JudicialUpdata judicialUpdata = judicialUpdataService.selectJudicialUpdataById(id);
        mmap.put("judicialUpdata", judicialUpdata);
        return prefix + "/edit";
    }

    /**
     * 修改保存司法拍卖项目
     */
    @RequiresPermissions("system:judicialUpdata:edit")
    @Log(title = "司法拍卖项目", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(JudicialUpdata judicialUpdata)
    {
        return toAjax(judicialUpdataService.updateJudicialUpdata(judicialUpdata));
    }

    /**
     * 删除司法拍卖项目
     */
    @RequiresPermissions("system:judicialUpdata:remove")
    @Log(title = "司法拍卖项目", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(judicialUpdataService.deleteJudicialUpdataByIds(ids));
    }
}
