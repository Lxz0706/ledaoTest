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
import com.ledao.system.dao.SysJournalComment;
import com.ledao.system.service.ISysJournalCommentService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 日志评论Controller
 * 
 * @author lxz
 * @date 2021-09-05
 */
@Controller
@RequestMapping("/system/comment")
public class SysJournalCommentController extends BaseController
{
    private String prefix = "system/comment";

    @Autowired
    private ISysJournalCommentService sysJournalCommentService;

    @RequiresPermissions("system:comment:view")
    @GetMapping()
    public String comment()
    {
        return prefix + "/comment";
    }

    /**
     * 查询日志评论列表
     */
    @RequiresPermissions("system:comment:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysJournalComment sysJournalComment)
    {
        startPage();
        List<SysJournalComment> list = sysJournalCommentService.selectSysJournalCommentList(sysJournalComment);
        return getDataTable(list);
    }

    /**
     * 导出日志评论列表
     */
    @RequiresPermissions("system:comment:export")
    @Log(title = "日志评论", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysJournalComment sysJournalComment)
    {
        List<SysJournalComment> list = sysJournalCommentService.selectSysJournalCommentList(sysJournalComment);
        ExcelUtil<SysJournalComment> util = new ExcelUtil<SysJournalComment>(SysJournalComment.class);
        return util.exportExcel(list, "comment");
    }

    /**
     * 新增日志评论
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存日志评论
     */
    @RequiresPermissions("system:comment:add")
    @Log(title = "日志评论", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysJournalComment sysJournalComment)
    {
        return toAjax(sysJournalCommentService.insertSysJournalComment(sysJournalComment));
    }

    /**
     * 修改日志评论
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SysJournalComment sysJournalComment = sysJournalCommentService.selectSysJournalCommentById(id);
        mmap.put("sysJournalComment", sysJournalComment);
        return prefix + "/edit";
    }

    /**
     * 修改保存日志评论
     */
    @RequiresPermissions("system:comment:edit")
    @Log(title = "日志评论", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysJournalComment sysJournalComment)
    {
        return toAjax(sysJournalCommentService.updateSysJournalComment(sysJournalComment));
    }

    /**
     * 删除日志评论
     */
    @RequiresPermissions("system:comment:remove")
    @Log(title = "日志评论", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysJournalCommentService.deleteSysJournalCommentByIds(ids));
    }
}
