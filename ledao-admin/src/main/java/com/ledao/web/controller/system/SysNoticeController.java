package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.common.utils.StringUtils;
import com.ledao.system.dao.SysUser;
import com.ledao.system.service.ISysUserService;
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
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.core.page.TableDataInfo;
import com.ledao.common.enums.BusinessType;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysNotice;
import com.ledao.system.service.ISysNoticeService;

/**
 * 公告 信息操作处理
 *
 * @author lxz
 */
@Controller
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
    private String prefix = "system/notice";

    @Autowired
    private ISysNoticeService noticeService;

    @Autowired
    private ISysUserService sysUserService;

    @RequiresPermissions("system:notice:view")
    @GetMapping()
    public String notice() {
        return prefix + "/notice";
    }

    /**
     * 查询公告列表
     */
    @RequiresPermissions("system:notice:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysNotice notice) {
        startPage();
        // 获取当前的用户
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                notice.setCreateBy(ShiroUtils.getLoginName());
                notice.setReceiver(ShiroUtils.getSysUser().getUserName());
            }
        }

        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 新增公告
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存公告
     */
    @RequiresPermissions("system:notice:add")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysNotice notice) {
        notice.setReadFlag("未读");
        notice.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改公告
     */
    @GetMapping("/edit/{noticeId}")
    public String edit(@PathVariable("noticeId") Long noticeId, ModelMap mmap) {
        mmap.put("notice", noticeService.selectNoticeById(noticeId));
        return prefix + "/edit";
    }

    /**
     * 修改保存公告
     */
    @RequiresPermissions("system:notice:edit")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysNotice notice) {
        notice.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(noticeService.updateNotice(notice));
    }

    /**
     * 删除公告
     */
    @RequiresPermissions("system:notice:remove")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(noticeService.deleteNoticeByIds(ids));
    }

    @GetMapping("/authUser")
    public String authUser(ModelMap modelMap) {
        SysNotice sysNotice = new SysNotice();
        List<SysNotice> sysNoticeList = noticeService.selectNoticeList(sysNotice);
        modelMap.put("sysNoticeList", sysNoticeList);
        return prefix + "/authUser";
    }

    /**
     * 系统提醒
     */
    @RequiresPermissions("system:notice:list")
    @PostMapping("/noticeList")
    @ResponseBody
    public AjaxResult noticeList() {
        SysNotice sysNotice = new SysNotice();
        sysNotice.setReadFlag("未读");
        // 获取当前的用户
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                logger.info("不是管理员！！！！！！！");
                sysNotice.setCreateBy(ShiroUtils.getLoginName());
                sysNotice.setReceiver(ShiroUtils.getSysUser().getUserName());
            }
        }
        List<SysNotice> list = noticeService.selectNoticeList(sysNotice);
        return AjaxResult.success(String.valueOf(list.size()));
    }

    //将信息设置为已读
    @RequiresPermissions("system:notice:list")
    @PostMapping("/read")
    @ResponseBody
    public AjaxResult read(String ids) {
        return toAjax(noticeService.readNoticeByIds(ids));
    }

    /**
     * 通知公告详细
     */
    @RequiresPermissions("system:notice:list")
    @GetMapping("/detail/{noticeId}")
    public String detail(@PathVariable("noticeId") Long noticeId, ModelMap mmap) {
        SysNotice sysNotice = noticeService.selectNoticeById(noticeId);
        if (StringUtils.isNotNull(sysNotice.getNoticeContent())) {
            sysNotice.setNoticeContent(StringUtils.inputDataFilter(sysNotice.getNoticeContent()));
        }
        mmap.put("notice", sysNotice);
        return prefix + "/detail";
    }
}
