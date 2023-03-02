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
import com.ledao.system.dao.wenShuDetail;
import com.ledao.system.service.IwenShuDetailService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 文书网数据Controller
 *
 * @author lxz
 * @date 2022-12-14
 */
@Controller
@RequestMapping("/system/detail")
public class wenShuDetailController extends BaseController {
    private String prefix = "system/detail";

    @Autowired
    private IwenShuDetailService wenShuDetailService;

    @RequiresPermissions("system:detail:view")
    @GetMapping()
    public String detail() {
        return prefix + "/detail";
    }

    /**
     * 查询文书网数据列表
     */
    @RequiresPermissions("system:detail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(wenShuDetail wenShuDetail) {
        startPage();
        List<wenShuDetail> list = wenShuDetailService.selectwenShuDetailList(wenShuDetail);
        for (wenShuDetail wenShuDetail1 : list) {

        }
        return getDataTable(list);
    }

    /**
     * 导出文书网数据列表
     */
    @RequiresPermissions("system:detail:export")
    @Log(title = "文书网数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(wenShuDetail wenShuDetail) {
        List<wenShuDetail> list = wenShuDetailService.selectwenShuDetailList(wenShuDetail);
        ExcelUtil<wenShuDetail> util = new ExcelUtil<wenShuDetail>(wenShuDetail.class);
        return util.exportExcel(list, "detail");
    }

    /**
     * 新增文书网数据
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存文书网数据
     */
    @RequiresPermissions("system:detail:add")
    @Log(title = "文书网数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(wenShuDetail wenShuDetail) {
        return toAjax(wenShuDetailService.insertwenShuDetail(wenShuDetail));
    }

    /**
     * 修改文书网数据
     */
    @GetMapping("/edit/{itemCourtId}")
    public String edit(@PathVariable("itemCourtId") String itemCourtId, ModelMap mmap) {
        wenShuDetail wenShuDetail = wenShuDetailService.selectwenShuDetailById(itemCourtId);
        mmap.put("wenShuDetail", wenShuDetail);
        return prefix + "/edit";
    }

    /**
     * 修改保存文书网数据
     */
    @RequiresPermissions("system:detail:edit")
    @Log(title = "文书网数据", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(wenShuDetail wenShuDetail) {
        return toAjax(wenShuDetailService.updatewenShuDetail(wenShuDetail));
    }

    /**
     * 删除文书网数据
     */
    @RequiresPermissions("system:detail:remove")
    @Log(title = "文书网数据", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(wenShuDetailService.deletewenShuDetailByIds(ids));
    }
}
