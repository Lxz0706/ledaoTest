package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysTagging;
import com.ledao.system.service.ISysTaggingService;
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
import com.ledao.system.dao.SysJudicial;
import com.ledao.system.service.ISysJudicialService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 司法拍卖项目Controller
 *
 * @author ledao
 * @date 2020-09-14
 */
@Controller
@RequestMapping("/system/judicial")
public class SysJudicialController extends BaseController {
    private String prefix = "system/judicial";

    @Autowired
    private ISysJudicialService sysJudicialService;

    @Autowired
    private ISysTaggingService sysTaggingService;

    @RequiresPermissions("system:judicial:view")
    @GetMapping()
    public String judicial() {
        return prefix + "/judicial";
    }

    /**
     * 查询司法拍卖项目列表
     */
    @RequiresPermissions("system:judicial:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysJudicial sysJudicial) {
        startPage();
        List<SysJudicial> list = sysJudicialService.selectSysJudicialList(sysJudicial);
        /*for (SysJudicial sysJudicial1 : list) {
            SysTagging sysTagging = sysTaggingService.selectSysTaggingByJudicialId(sysJudicial1.getId());
            if (StringUtils.isNotNull(sysTagging)) {
                sysJudicial1.setTagging("Y");
            } else {
                sysJudicial1.setTagging("N");
            }
        }*/
        return getDataTable(list);
    }

    /**
     * 导出司法拍卖项目列表
     */
    @RequiresPermissions("system:judicial:export")
    @Log(title = "司法拍卖项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysJudicial sysJudicial) {
        List<SysJudicial> list = sysJudicialService.selectSysJudicialList(sysJudicial);
        ExcelUtil<SysJudicial> util = new ExcelUtil<SysJudicial>(SysJudicial.class);
        return util.exportExcel(list, "judicial");
    }

    /**
     * 新增司法拍卖项目
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存司法拍卖项目
     */
    @RequiresPermissions("system:judicial:add")
    @Log(title = "司法拍卖项目", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysJudicial sysJudicial) {
        return toAjax(sysJudicialService.insertSysJudicial(sysJudicial));
    }

    /**
     * 修改司法拍卖项目
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysJudicial sysJudicial = sysJudicialService.selectSysJudicialById(id);
        mmap.put("sysJudicial", sysJudicial);
        return prefix + "/edit";
    }

    /**
     * 修改保存司法拍卖项目
     */
    @RequiresPermissions("system:judicial:edit")
    @Log(title = "司法拍卖项目", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysJudicial sysJudicial) {
        return toAjax(sysJudicialService.updateSysJudicial(sysJudicial));
    }

    /**
     * 删除司法拍卖项目
     */
    @RequiresPermissions("system:judicial:remove")
    @Log(title = "司法拍卖项目", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysJudicialService.deleteSysJudicialByIds(ids));
    }


    /**
     * 新增保存星标库
     */
    @RequiresPermissions("system:tagging:add")
    @Log(title = "星标库", businessType = BusinessType.INSERT)
    @PostMapping("/addTagging")
    @ResponseBody
    public AjaxResult addTagging(SysTagging sysTagging) {
        SysJudicial sysJudicial = new SysJudicial();
        sysJudicial.setId(sysTagging.getJudicialId());
        sysJudicial.setTagging("Y");
        sysJudicial.setUpdateBy(ShiroUtils.getLoginName());
        sysJudicialService.updateSysJudicial(sysJudicial);
        sysTagging.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysTaggingService.insertSysTagging(sysTagging));
    }

    /**
     * 删除星标库
     */
    @RequiresPermissions("system:tagging:remove")
    @Log(title = "星标库", businessType = BusinessType.DELETE)
    @PostMapping("/removeTagging")
    @ResponseBody
    public AjaxResult removeTagging(SysTagging sysTagging) {
        SysJudicial sysJudicial = new SysJudicial();
        sysJudicial.setId(sysTagging.getJudicialId());
        sysJudicial.setTagging("");
        sysJudicial.setUpdateBy(ShiroUtils.getLoginName());
        sysJudicialService.updateSysJudicial(sysJudicial);

        SysTagging sysTagging1 = sysTaggingService.selectSysTaggingByJudicialId(sysTagging.getJudicialId());
        return toAjax(sysTaggingService.deleteSysTaggingById(sysTagging1.getId()));
    }
}
