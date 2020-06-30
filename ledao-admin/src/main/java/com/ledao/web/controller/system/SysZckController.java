package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.domain.SysUser;
import com.ledao.system.domain.SysZck;
import com.ledao.system.service.ISysZckService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ledao.common.annotation.Log;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.domain.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 资产信息库Controller
 *
 * @author lxz
 * @date 2020-06-09
 */
@Controller
@RequestMapping("/system/zcb/zck")
public class SysZckController extends BaseController {
    private String prefix = "system/zcb/zck";

    @Autowired
    private ISysZckService sysZckService;

    @RequiresPermissions("system:zcb:view")
    @GetMapping()
    public String zck() {
        return prefix + "/zck";
    }

    /**
     * 查询资产信息库列表
     */
    @RequiresPermissions("system:zcb:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysZck sysZck) {
        startPage();
        List<SysZck> list = sysZckService.selectSysZckList(sysZck);
        return getDataTable(list);
    }

    /**
     * 查询资产信息库列表
     */
    @RequiresPermissions("system:zcb:list")
    @GetMapping("/lists")
    @ResponseBody
    public TableDataInfo lists(SysZck sysZck) {
        logger.info("进来了！！！！！！！！！！！！");
        startPage();
        List<SysZck> list = sysZckService.selectSysZckList(sysZck);
        return getDataTable(list);
    }

    /**
     * 导出资产信息库列表
     */
    @RequiresPermissions("system:zcb:export")
    @Log(title = "资产信息库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysZck sysZck) {
        List<SysZck> list = sysZckService.selectSysZckList(sysZck);
        ExcelUtil<SysZck> util = new ExcelUtil<SysZck>(SysZck.class);
        return util.exportExcel(list, "zck");
    }

    /**
     * 新增资产信息库
     */
    @GetMapping("/add/{zcbId}")
    public String add(@PathVariable("zcbId") Long zcbId, ModelMap mmap) {
        mmap.put("zcbId", zcbId);
        return prefix + "/add";
    }

    /**
     * 新增保存资产信息库
     */
    @RequiresPermissions("system:zcb:add")
    @Log(title = "资产信息库", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysZck sysZck) {
        sysZck.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysZckService.insertSysZck(sysZck));
    }

    /**
     * 修改资产信息库
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysZck sysZck = sysZckService.selectSysZckById(id);
        mmap.put("sysZck", sysZck);
        return prefix + "/edit";
    }

    /**
     * 修改保存资产信息库
     */
    @RequiresPermissions("system:zcb:edit")
    @Log(title = "资产信息库", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysZck sysZck) {
        sysZck.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysZckService.updateSysZck(sysZck));
    }

    /**
     * 删除资产信息库
     */
    @RequiresPermissions("system:zcb:remove")
    @Log(title = "资产信息库", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysZckService.deleteSysZckByIds(ids));
    }

    /**
     * 查看详细
     */
    @RequiresPermissions("system:zcb:detail")
    @Log(title = "资产信息库", businessType = BusinessType.DETAIL)
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("sysZck", sysZckService.selectSysZckById(id));
        return prefix + "/detail";
    }

    @Log(title = "资产信息库", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:zcb:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport, Long zcbId) throws Exception {
        ExcelUtil<SysZck> util = new ExcelUtil<SysZck>(SysZck.class);
        List<SysZck> sysZckList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = sysZckService.importZck(sysZckList, updateSupport, operName, zcbId);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("system:zcb:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<SysZck> util = new ExcelUtil<SysZck>(SysZck.class);
        return util.importTemplateExcel("资产库");
    }
}
