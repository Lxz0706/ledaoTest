package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.common.annotation.RepeatSubmit;
import com.ledao.system.dao.wenShuDetail;
import com.ledao.system.service.IwenShuDetailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ledao.common.annotation.Log;
import com.ledao.common.enums.BusinessType;
import com.ledao.system.dao.wenShu;
import com.ledao.system.service.IwenShuService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 文书网Controller
 *
 * @author lxz
 * @date 2022-12-14
 */
@Controller
@RequestMapping("/system/wenShu")
public class wenShuController extends BaseController {
    private String prefix = "system/wenShu";

    @Autowired
    private IwenShuService wenShuService;

    //@RequiresPermissions("system:wenShu:view")
    @GetMapping()
    public String wenShu() {
        return prefix + "/wenShu";
    }

    /**
     * 查询文书网列表
     */
    //@RequiresPermissions("system:wenShu:list")
    @PostMapping("/list")
    @ResponseBody
    @RepeatSubmit
    public TableDataInfo list(wenShu wenShu) {
        startPage();
        List<wenShu> list = wenShuService.selectwenShuList(wenShu);
        return getDataTable(list);
    }

    /**
     * 导出文书网列表
     */
    @RequiresPermissions("system:wenShu:export")
    @Log(title = "文书网", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(wenShu wenShu) {
        List<wenShu> list = wenShuService.selectwenShuList(wenShu);
        ExcelUtil<wenShu> util = new ExcelUtil<wenShu>(wenShu.class);
        return util.exportExcel(list, "wenShu");
    }

    /**
     * 新增文书网
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存文书网
     */
    @RequiresPermissions("system:wenShu:add")
    @Log(title = "文书网", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(wenShu wenShu) {
        return toAjax(wenShuService.insertwenShu(wenShu));
    }

    /**
     * 修改文书网
     */
    @GetMapping("/edit/{courtId}")
    public String edit(@PathVariable("courtId") String courtId, ModelMap mmap) {
        wenShu wenShu = wenShuService.selectwenShuById(courtId);
        mmap.put("wenShu", wenShu);
        return prefix + "/edit";
    }

    /**
     * 修改保存文书网
     */
    @RequiresPermissions("system:wenShu:edit")
    @Log(title = "文书网", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(wenShu wenShu) {
        return toAjax(wenShuService.updatewenShu(wenShu));
    }

    /**
     * 删除文书网
     */
    @RequiresPermissions("system:wenShu:remove")
    @Log(title = "文书网", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(wenShuService.deletewenShuByIds(ids));
    }
}
