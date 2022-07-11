package com.ledao.web.controller.system;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.ledao.common.core.dao.entity.SysDept;
import com.ledao.common.core.dao.entity.SysDictData;
import com.ledao.common.core.dao.entity.SysRole;
import com.ledao.common.core.dao.entity.SysUser;
import com.ledao.common.utils.StringUtils;
import com.ledao.system.dao.*;
import com.ledao.system.service.ISysDeptService;
import com.ledao.system.service.ISysDictDataService;
import com.ledao.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.ledao.system.service.ISysNoticeService;

/**
 * 公告 信息操作处理
 *
 * @author lxz
 */
@Api("通知公告")
@Controller
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
    private String prefix = "system/notice";

    @Autowired
    private ISysNoticeService noticeService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysDictDataService sysDictDataService;

    @Autowired
    private ISysDeptService sysDeptService;

    @RequiresPermissions("system:notice:view")
    @GetMapping()
    public String notice() {
        return prefix + "/notice";
    }

    /**
     * 查询文件类型进行分类
     */
    @ApiOperation("获取公告类型列表")
    @RequiresPermissions("system:notice:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        List<SysDictData> sysDictDataList = sysDictDataService.selectDictDataByType("sys_notice_type");
        return getDataTable(sysDictDataList);
    }

    @ApiOperation("根据公告类型获取相关信息")
    @GetMapping("/toDocumentByType/{type}")
    public String toDocumentByType(@PathVariable("type") String type, ModelMap modelMap) {
        modelMap.put("type", type);
        return prefix + "/noticeByType";
    }

    /**
     * 查询公告列表
     */
    @ApiOperation("根据公告类型获取相关信息")
    @RequiresPermissions("system:notice:list")
    @PostMapping("/listByType")
    @ResponseBody
    public TableDataInfo listByType(SysNotice notice) {
        startPage();
        // 获取当前的用户
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey()) && !"zjl".equals(sysRole.getRoleKey())&& !"documentAdmin".equals(sysRole.getRoleKey())) {
                        notice.setCreateBy(ShiroUtils.getLoginName());
                        notice.setReceiver(ShiroUtils.getSysUser().getUserName());
                    }
                }
            }
        }

        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 新增公告
     */
    @ApiOperation("新增公告消息")
    @GetMapping(value = {"/add", "/add/{type}"})
    public String add(@PathVariable(value = "type", required = false) String type, ModelMap modelMap) {
        modelMap.put("noticeType", type);
        return prefix + "/add";
    }

    /**
     * 新增保存公告
     */
    @ApiOperation("新增公告消息")
    @RequiresPermissions("system:notice:add")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysNotice notice) {
        notice.setReadFlag("未读");
        notice.setCreateBy(ShiroUtils.getLoginName());
        StringBuffer userIds = new StringBuffer();
        StringBuffer userNames = new StringBuffer();
        StringBuffer deptIds = new StringBuffer();
        StringBuffer deptNames = new StringBuffer();

        if (StringUtils.isNotEmpty(notice.getShareDeptId()) && StringUtils.isNotEmpty(notice.getReceiverId())) {
            userIds.append(notice.getReceiverId());
            userNames.append(notice.getReceiver());
            for (String string : notice.getShareDeptId().split(",")) {
                SysUser sysUser = new SysUser();
                sysUser.setDeptId(Long.valueOf(string));
                SysUser currentUser = ShiroUtils.getSysUser();
                if (currentUser != null) {
                    if (!currentUser.isAdmin()) {
                        List<SysRole> getRoles = currentUser.getRoles();
                        for (SysRole sysRole : getRoles) {
                            if (!"SJXXB".equals(sysRole.getRoleKey())&& !"documentAdmin".equals(sysRole.getRoleKey())) {
                                if (StringUtils.equals("0", ShiroUtils.getSysUser().getFormalFlag())) {
                                    if (StringUtils.equals("0", ShiroUtils.getSysUser().getFormalFlag())) {
                                        sysUser.setFormalFlag("0");
                                    }
                                }
                            }
                        }
                    }
                }
                List<SysUser> sysUserList = sysUserService.selectUserListForDocument(sysUser);
                for (SysUser sysUser1 : sysUserList) {
                    if (!userIds.toString().contains(sysUser1.getUserId().toString())) {
                        userIds.append(sysUser1.getUserId()).append(",");
                        userNames.append(sysUser1.getUserName()).append(",");
                    }
                }
            }
            deptIds.append(notice.getShareDeptId());
            deptNames.append(notice.getShareDeptName());
        } else if (StringUtils.isNotEmpty(notice.getShareDeptId()) && StringUtils.isEmpty(notice.getReceiverId())) {
            for (String string : notice.getShareDeptId().split(",")) {
                SysUser sysUser = new SysUser();
                sysUser.setDeptId(Long.valueOf(string));
                SysUser currentUser = ShiroUtils.getSysUser();
                if (currentUser != null) {
                    if (!currentUser.isAdmin()) {
                        List<SysRole> getRoles = currentUser.getRoles();
                        for (SysRole sysRole : getRoles) {
                            if (!"SJXXB".equals(sysRole.getRoleKey())&& !"documentAdmin".equals(sysRole.getRoleKey())) {
                                if (StringUtils.equals("0", ShiroUtils.getSysUser().getFormalFlag())) {
                                    if (StringUtils.equals("0", ShiroUtils.getSysUser().getFormalFlag())) {
                                        sysUser.setFormalFlag("0");
                                    }
                                }
                            }
                        }
                    }
                }
                List<SysUser> sysUserList = sysUserService.selectUserListForDocument(sysUser);
                for (SysUser sysUser1 : sysUserList) {
                    userIds.append(sysUser1.getUserId()).append(",");
                    userNames.append(sysUser1.getUserName()).append(",");
                }
            }
            deptIds.append(notice.getShareDeptId());
            deptNames.append(notice.getShareDeptName());
        } else if (StringUtils.isEmpty(notice.getShareDeptId()) && StringUtils.isNotEmpty(notice.getReceiverId())) {
            userIds.append(notice.getReceiverId());
            userNames.append(notice.getReceiver());
        } else if (StringUtils.isEmpty(notice.getReceiverId()) && StringUtils.isEmpty(notice.getReceiverId())) {
            SysDept sysDept = new SysDept();
            sysDept.setStatus("0");
            List<SysDept> sysDeptList = sysDeptService.selectDeptList(sysDept);
            for (SysDept sysDept1 : sysDeptList) {
                deptIds.append(sysDept1.getDeptId()).append(",");
                deptNames.append(sysDept1.getDeptName()).append(",");
                SysUser sysUser = new SysUser();
                sysUser.setDeptId(sysDept1.getDeptId());
                SysUser currentUser = ShiroUtils.getSysUser();
                if (currentUser != null) {
                    if (!currentUser.isAdmin()) {
                        List<SysRole> getRoles = currentUser.getRoles();
                        for (SysRole sysRole : getRoles) {
                            if (!"SJXXB".equals(sysRole.getRoleKey())&& !"documentAdmin".equals(sysRole.getRoleKey())) {
                                if (StringUtils.equals("0", ShiroUtils.getSysUser().getFormalFlag())) {
                                    if (StringUtils.equals("0", ShiroUtils.getSysUser().getFormalFlag())) {
                                        sysUser.setFormalFlag("0");
                                    }
                                }
                            }
                        }
                    }
                }
                List<SysUser> sysUserList = sysUserService.selectUserList(sysUser);
                for (SysUser sysUser1 : sysUserList) {
                    userIds.append(sysUser1.getUserId()).append(",");
                    userNames.append(sysUser1.getUserName()).append(",");
                }
            }
        }
        notice.setReceiverId(userIds.toString());
        notice.setReceiver(userNames.toString());
        notice.setShareDeptId(deptIds.toString());
        notice.setShareDeptName(deptNames.toString());

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
        StringBuffer userIds = new StringBuffer();
        StringBuffer userNames = new StringBuffer();
        StringBuffer deptIds = new StringBuffer();
        StringBuffer deptNames = new StringBuffer();
        if (StringUtils.isNotEmpty(notice.getShareDeptId()) && StringUtils.isNotEmpty(notice.getReceiverId())) {
            userIds.append(notice.getReceiverId());
            userNames.append(notice.getReceiver());
            for (String string : notice.getShareDeptId().split(",")) {
                SysUser sysUser = new SysUser();
                sysUser.setDeptId(Long.valueOf(string));
                SysUser currentUser = ShiroUtils.getSysUser();
                if (currentUser != null) {
                    if (!currentUser.isAdmin()) {
                        List<SysRole> getRoles = currentUser.getRoles();
                        for (SysRole sysRole : getRoles) {
                            if (!"SJXXB".equals(sysRole.getRoleKey())&& !"documentAdmin".equals(sysRole.getRoleKey())) {
                                if (StringUtils.equals("0", ShiroUtils.getSysUser().getFormalFlag())) {
                                    sysUser.setFormalFlag("0");
                                }
                            }
                        }
                    }
                }
                List<SysUser> sysUserList = sysUserService.selectUserListForDocument(sysUser);
                for (SysUser sysUser1 : sysUserList) {
                    if (!userIds.toString().contains(sysUser1.getUserId().toString())) {
                        userIds.append(sysUser1.getUserId()).append(",");
                        userNames.append(sysUser1.getUserName()).append(",");
                    }
                }
            }
            deptIds.append(notice.getShareDeptId());
            deptNames.append(notice.getShareDeptName());
        } else if (StringUtils.isNotEmpty(notice.getShareDeptId()) && StringUtils.isEmpty(notice.getReceiverId())) {
            for (String string : notice.getShareDeptId().split(",")) {
                SysUser sysUser = new SysUser();
                sysUser.setDeptId(Long.valueOf(string));
                SysUser currentUser = ShiroUtils.getSysUser();
                if (currentUser != null) {
                    if (!currentUser.isAdmin()) {
                        List<SysRole> getRoles = currentUser.getRoles();
                        for (SysRole sysRole : getRoles) {
                            if (!"SJXXB".equals(sysRole.getRoleKey())&& !"documentAdmin".equals(sysRole.getRoleKey())) {
                                if (StringUtils.equals("0", ShiroUtils.getSysUser().getFormalFlag())) {
                                    sysUser.setFormalFlag("0");
                                }
                            }
                        }
                    }
                }
                List<SysUser> sysUserList = sysUserService.selectUserListForDocument(sysUser);
                for (SysUser sysUser1 : sysUserList) {
                    userIds.append(sysUser1.getUserId()).append(",");
                    userNames.append(sysUser1.getUserName()).append(",");
                }
            }
            deptIds.append(notice.getShareDeptId());
            deptNames.append(notice.getShareDeptName());
        } else if (StringUtils.isEmpty(notice.getShareDeptId()) && StringUtils.isNotEmpty(notice.getReceiverId())) {
            userIds.append(notice.getReceiverId());
            userNames.append(notice.getReceiver());
        } else if (StringUtils.isEmpty(notice.getReceiverId()) && StringUtils.isEmpty(notice.getReceiverId())) {
            SysDept sysDept = new SysDept();
            sysDept.setStatus("0");
            List<SysDept> sysDeptList = sysDeptService.selectDeptList(sysDept);
            for (SysDept sysDept1 : sysDeptList) {
                deptIds.append(sysDept1.getDeptId()).append(",");
                deptNames.append(sysDept1.getDeptName()).append(",");
                SysUser sysUser = new SysUser();
                sysUser.setDeptId(sysDept1.getDeptId());
                SysUser currentUser = ShiroUtils.getSysUser();
                if (currentUser != null) {
                    if (!currentUser.isAdmin()) {
                        List<SysRole> getRoles = currentUser.getRoles();
                        for (SysRole sysRole : getRoles) {
                            if (!"SJXXB".equals(sysRole.getRoleKey())&& !"documentAdmin".equals(sysRole.getRoleKey())) {
                                if (StringUtils.equals("0", ShiroUtils.getSysUser().getFormalFlag())) {
                                    sysUser.setFormalFlag("0");
                                }
                            }
                        }
                    }
                }
                List<SysUser> sysUserList = sysUserService.selectUserList(sysUser);
                for (SysUser sysUser1 : sysUserList) {
                    userIds.append(sysUser1.getUserId()).append(",");
                    userNames.append(sysUser1.getUserName()).append(",");
                }
            }
        }
        notice.setReceiverId(userIds.toString());
        notice.setReceiver(userNames.toString());
        notice.setShareDeptId(deptIds.toString());
        notice.setShareDeptName(deptNames.toString());
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
        //PageHelper.startPage(0, 10, false);
        SysNotice sysNotice = new SysNotice();
        sysNotice.setReadFlag("未读");
        // 获取当前的用户
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                //sysNotice.setCreateBy(ShiroUtils.getLoginName());
                sysNotice.setReceiver(ShiroUtils.getSysUser().getUserName());
            }
        }
        List<SysNotice> list = noticeService.selectNoticeLists(sysNotice);
        return AjaxResult.success(String.valueOf(list.size()));
        //return AjaxResult.success(list);
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
        if (StringUtils.isNotEmpty(sysNotice.getReceiver()) && StringUtils.isNotEmpty(sysNotice.getShareDeptName())) {
            sysNotice.setShareDeptAndUser(sysNotice.getShareDeptName() + "," + sysNotice.getReceiver());
        } else if (StringUtils.isEmpty(sysNotice.getShareDeptName())) {
            sysNotice.setShareDeptAndUser(sysNotice.getReceiver());
        } else if (StringUtils.isEmpty(sysNotice.getReceiver())) {
            sysNotice.setShareDeptAndUser(sysNotice.getShareDeptName());
        }
        mmap.put("notice", sysNotice);
        return prefix + "/detail";
    }

    /**
     * 系统提醒
     */
    @RequiresPermissions("system:notice:list")
    @PostMapping("/mainNoticeList")
    @ResponseBody
    public AjaxResult mainNoticeList() {
        PageHelper.startPage(0, 10);
        SysNotice sysNotice = new SysNotice();
        sysNotice.setNoticeType(getRequest().getParameter("type"));
        //sysNotice.setReadFlag("未读");
        // 获取当前的用户
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey()) && !"zjl".equals(sysRole.getRoleKey())&& !"documentAdmin".equals(sysRole.getRoleKey())) {
                        sysNotice.setCreateBy(ShiroUtils.getLoginName());
                        sysNotice.setReceiver(ShiroUtils.getSysUser().getUserName());
                    }
                }
            }
        }
        List<SysNotice> list = noticeService.selectNoticeList(sysNotice);
        return AjaxResult.success(list);
    }

    @GetMapping("/toListByType/{type}")
    public String toListByType(@PathVariable("type") String type, ModelMap modelMap) {
        modelMap.put("noticeType", type);
        return prefix + "/noticeByType";
    }

    @GetMapping("/toList")
    public String toList() {
        return prefix + "/notice";
    }
}
