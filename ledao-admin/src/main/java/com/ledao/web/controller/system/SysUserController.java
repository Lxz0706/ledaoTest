package com.ledao.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.http.CommonUtil;
import com.ledao.system.dao.*;
import com.ledao.system.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.ledao.common.annotation.Log;
import com.ledao.common.constant.UserConstants;
import com.ledao.common.constant.WeChatConstants;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.core.page.TableDataInfo;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.framework.shiro.service.SysPasswordService;
import com.ledao.framework.util.ShiroUtils;

/**
 * 用户信息
 *
 * @author lxz
 */
@Controller
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
    private String prefix = "system/user";

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysCustomerService sysCustomerService;

    @Autowired
    private ISysDocumentService sysDocumentService;

    @Autowired
    private ISysNoticeService sysNoticeService;

    @RequiresPermissions("system:user:view")
    @GetMapping()
    public String user() {
        return prefix + "/user";
    }

    //    @RequiresPermissions("system:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysUser user) {
        startPage();
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey())) {
                        user.setFormalFlag("0");
                    }
                }
            }
        }
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysUser user) {
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey())) {
                        user.setFormalFlag("0");
                    }
                }
            }
        }
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.exportExcel(list, "用户数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:user:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("system:user:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.importTemplateExcel("用户数据");
    }

    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("roles", roleService.selectRoleAll());
        mmap.put("posts", postService.selectPostAll());
        return prefix + "/add";
    }

    /**
     * 新增保存用户
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysUser user) {
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(user.getLoginName()))) {
            return error("新增用户'" + user.getLoginName() + "'失败，登录账号已存在");
        } else if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return error("新增用户'" + user.getLoginName() + "'失败，手机号码已存在");
        } else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return error("新增用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
        }
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setCreateBy(ShiroUtils.getLoginName());
        userService.insertUser(user);

        SysCustomer sysCustomer = new SysCustomer();
        sysCustomer.setCreateBy(user.getLoginName());
        List<SysCustomer> sysCustomerList = sysCustomerService.queryAll(sysCustomer);
        for (SysCustomer sysCustomer1 : sysCustomerList) {
            sysCustomer1.setUpdateBy(sysCustomer1.getCreateBy());
            sysCustomer1.setDeptId(user.getDeptId());
            sysCustomer1.setDeptName(user.getDept().getDeptName());
            sysCustomerService.updateSysCustomer(sysCustomer1);
        }

        //文档管理
        SysDocument sysDocument = new SysDocument();
        List<SysDocument> sysDocumentList = sysDocumentService.selectSysDocumentList(sysDocument);
        for (SysDocument sysDocument1 : sysDocumentList) {
            //如果这个人的上级部门在分享部门中，则也会分享
            SysDept sysDept = deptService.selectDeptById(user.getDeptId());
            if (StringUtils.isNotEmpty(sysDocument1.getShareDeptId())) {
                if (!sysDocument1.getShareDeptId().contains(sysDept.getParentId().toString())) {
                    if (sysDocument1.getShareDeptId().contains(user.getDeptId().toString())) {
                        SysDocument sysDocument2 = new SysDocument();
                        sysDocument2.setFileId(sysDocument1.getFileId());
                        sysDocument2.setShareUserId(sysDocument1.getShareUserId() + "," + user.getUserId());
                        sysDocument2.setShareUserName(sysDocument1.getShareUserName() + "," + user.getUserName());
                        sysDocumentService.updateSysDocument(sysDocument2);
                    }
                } else {
                    SysDocument sysDocument2 = new SysDocument();
                    sysDocument2.setFileId(sysDocument1.getFileId());
                    sysDocument2.setShareUserId(sysDocument1.getShareUserId() + "," + user.getUserId());
                    sysDocument2.setShareUserName(sysDocument1.getShareUserName() + "," + user.getUserName());
                    sysDocumentService.updateSysDocument(sysDocument2);
                }
            }
        }

        //通知公告
        SysNotice sysNotice = new SysNotice();
        List<SysNotice> sysNoticeList = sysNoticeService.selectNoticeList(sysNotice);
        for (SysNotice sysNotice1 : sysNoticeList) {
            //如果这个人的上级部门在分享部门中，则也会分享
            SysDept sysDept = deptService.selectDeptById(user.getDeptId());
            if (StringUtils.isNotEmpty(sysNotice1.getShareDeptId())) {
                if (!sysNotice1.getShareDeptId().contains(sysDept.getParentId().toString())) {
                    if (sysNotice1.getShareDeptId().contains(user.getDeptId().toString())) {
                        SysNotice sysNotice2 = new SysNotice();
                        sysNotice2.setNoticeId(sysNotice1.getNoticeId());
                        sysNotice2.setReceiverId(sysNotice2.getReceiverId() + "," + user.getUserId());
                        sysNotice2.setReceiver(sysNotice2.getReceiver() + "," + user.getUserName());
                        sysNoticeService.updateNotice(sysNotice2);
                    }
                } else {
                    SysNotice sysNotice2 = new SysNotice();
                    sysNotice2.setNoticeId(sysNotice1.getNoticeId());
                    sysNotice2.setReceiverId(sysNotice2.getReceiverId() + "," + user.getUserId());
                    sysNotice2.setReceiver(sysNotice2.getReceiver() + "," + user.getUserName());
                    sysNoticeService.updateNotice(sysNotice2);
                }
            }

            /*            if (StringUtils.isNotEmpty(sysNotice1.getShareDeptId())) {
                for (String string : sysNotice1.getShareDeptId().split(",")) {
                    if (StringUtils.isNotEmpty(string)) {
                        List<SysDept> deptList = deptService.selectDeptByParentId(Long.valueOf(string));
                        if (deptList.size() > 0) {
                            for (SysDept sysDept : deptList) {
                                if (StringUtils.isNotNull(sysDept) && StringUtils.isNotNull(sysDept.getDeptId())) {
                                    if (sysDept.getDeptId().equals(user.getDeptId().toString())) {
                                        SysNotice sysNotice2 = new SysNotice();
                                        sysNotice2.setNoticeId(sysNotice1.getNoticeId());
                                        sysNotice2.setReceiverId(sysNotice2.getReceiverId() + "," + user.getUserId());
                                        sysNotice2.setReceiver(sysNotice2.getReceiver() + "," + user.getUserName());
                                        sysNoticeService.updateNotice(sysNotice2);
                                    }
                                }
                            }
                        } else {
                            SysDept sysDept = deptService.selectDeptById(user.getDeptId());
                            if (string.equals(sysDept.getParentId().toString())) {
                                SysNotice sysNotice2 = new SysNotice();
                                sysNotice2.setNoticeId(sysNotice1.getNoticeId());
                                sysNotice2.setReceiverId(sysNotice2.getReceiverId() + "," + user.getUserId());
                                sysNotice2.setReceiver(sysNotice2.getReceiver() + "," + user.getUserName());
                                sysNoticeService.updateNotice(sysNotice2);
                            }
                        }
                    }
                }
            }*/
        }

        return toAjax(Integer.parseInt(String.valueOf(user.getUserId())));
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        mmap.put("roles", roleService.selectRolesByUserId(userId));
        mmap.put("posts", postService.selectPostsByUserId(userId));
        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysUser user) {
        userService.checkUserAllowed(user);
        if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return error("修改用户'" + user.getLoginName() + "'失败，手机号码已存在");
        } else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return error("修改用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
        }
        SysCustomer sysCustomer = new SysCustomer();
        sysCustomer.setCreateBy(user.getLoginName());
        List<SysCustomer> sysCustomerList = sysCustomerService.queryAll(sysCustomer);
        for (SysCustomer sysCustomer1 : sysCustomerList) {
            sysCustomer1.setUpdateBy(sysCustomer1.getCreateBy());
            sysCustomer1.setDeptId(user.getDeptId());
            sysCustomer1.setDeptName(user.getDept().getDeptName());
            sysCustomerService.updateSysCustomer(sysCustomer1);
        }

        //文档管理
        SysDocument sysDocument = new SysDocument();
        List<SysDocument> sysDocumentList = sysDocumentService.selectSysDocumentList(sysDocument);
        for (SysDocument sysDocument1 : sysDocumentList) {
            //如果这个人的上级部门在分享部门中，则也会分享
            SysDept sysDept = deptService.selectDeptById(user.getDeptId());
            if (StringUtils.isNotEmpty(sysDocument1.getShareDeptId())) {
                if (!sysDocument1.getShareDeptId().contains(sysDept.getParentId().toString())) {
                    if (sysDocument1.getShareDeptId().contains(user.getDeptId().toString())) {
                        SysDocument sysDocument2 = new SysDocument();
                        sysDocument2.setFileId(sysDocument1.getFileId());
                        sysDocument2.setShareUserId(sysDocument1.getShareUserId() + "," + user.getUserId());
                        sysDocument2.setShareUserName(sysDocument1.getShareUserName() + "," + user.getUserName());
                        sysDocumentService.updateSysDocument(sysDocument2);
                    }
                } else {
                    SysDocument sysDocument2 = new SysDocument();
                    sysDocument2.setFileId(sysDocument1.getFileId());
                    sysDocument2.setShareUserId(sysDocument1.getShareUserId() + "," + user.getUserId());
                    sysDocument2.setShareUserName(sysDocument1.getShareUserName() + "," + user.getUserName());
                    sysDocumentService.updateSysDocument(sysDocument2);
                }
            }
        }

        //通知公告
        SysNotice sysNotice = new SysNotice();
        List<SysNotice> sysNoticeList = sysNoticeService.selectNoticeList(sysNotice);
        for (SysNotice sysNotice1 : sysNoticeList) {
            //如果这个人的上级部门在分享部门中，则也会分享
            SysDept sysDept = deptService.selectDeptById(user.getDeptId());
            if (StringUtils.isNotEmpty(sysNotice1.getShareDeptId())) {
                if (!sysNotice1.getShareDeptId().contains(sysDept.getParentId().toString())) {
                    if (sysNotice1.getShareDeptId().contains(user.getDeptId().toString())) {
                        SysNotice sysNotice2 = new SysNotice();
                        sysNotice2.setNoticeId(sysNotice1.getNoticeId());
                        sysNotice2.setReceiverId(sysNotice2.getReceiverId() + "," + user.getUserId());
                        sysNotice2.setReceiver(sysNotice2.getReceiver() + "," + user.getUserName());
                        sysNoticeService.updateNotice(sysNotice2);
                    }
                } else {
                    SysNotice sysNotice2 = new SysNotice();
                    sysNotice2.setNoticeId(sysNotice1.getNoticeId());
                    sysNotice2.setReceiverId(sysNotice2.getReceiverId() + "," + user.getUserId());
                    sysNotice2.setReceiver(sysNotice2.getReceiver() + "," + user.getUserName());
                    sysNoticeService.updateNotice(sysNotice2);
                }
            }
        }
        user.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(userService.updateUser(user));
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwdSave(SysUser user) {
        userService.checkUserAllowed(user);
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        if (userService.resetUserPwd(user) > 0) {
            if (ShiroUtils.getUserId().longValue() == user.getUserId().longValue()) {
                ShiroUtils.setSysUser(userService.selectUserById(user.getUserId()));
            }
            return success();
        }
        return error();
    }

    /**
     * 进入授权角色页
     */
    @GetMapping("/authRole/{userId}")
    public String authRole(@PathVariable("userId") Long userId, ModelMap mmap) {
        SysUser user = userService.selectUserById(userId);
        // 获取用户所属的角色列表
        List<SysUserRole> userRoles = userService.selectUserRoleByUserId(userId);
        mmap.put("user", user);
        mmap.put("userRoles", userRoles);
        return prefix + "/authRole";
    }

    /**
     * 用户授权角色
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PostMapping("/authRole/insertAuthRole")
    @ResponseBody
    public AjaxResult insertAuthRole(Long userId, Long[] roleIds) {
        userService.insertUserAuth(userId, roleIds);
        return success();
    }

    @RequiresPermissions("system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(userService.deleteUserByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(SysUser user) {
        return userService.checkLoginNameUnique(user.getLoginName());
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(SysUser user) {
        return userService.checkPhoneUnique(user);
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(SysUser user) {
        return userService.checkEmailUnique(user);
    }

    /**
     * 微信获取openid
     *
     * @return
     */
    @PostMapping("/getOpenid")
    @ResponseBody
    public AjaxResult getOpenid(String jsCode) {
        JSONObject jsonResult;
        try {
//			String requestUrl="https://api.weixin.qq.com/sns/jscode2session?appid="+Global.getConfig("wxAppid")+"&secret="+Global.getConfig("wxSecret")+"&js_code="+jsCode+"&grant_type="+Global.getConfig("wxGrant_type");  
            String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + WeChatConstants.WXAPPID + "&secret=" + WeChatConstants.WXSECRET + "&js_code=" + jsCode + "&grant_type=" + WeChatConstants.WXGRANT_TYPE;
            jsonResult = CommonUtil.httpsRequest(requestUrl, "GET", null);
            System.out.println("返回的jsonResult" + jsonResult);
            if (jsonResult != null) {
                System.out.println(jsonResult.toString());
                String openid = jsonResult.getString("openid");
                return AjaxResult.success(openid);
            } else {
                return AjaxResult.error("openid获取失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("openid获取失败");
        }
    }

    /**
     * 用户状态修改
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:user:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysUser user) {
        userService.checkUserAllowed(user);
        return toAjax(userService.changeStatus(user));
    }

    /**
     * 选择人员树
     */
    @GetMapping("/selectUserTree")
    public String selectUserTree(String selectedUserIds, String selectedUserNames, String selectedDeptIds, String
            selectedDeptNames,
                                 Boolean multiSelectFlag, ModelMap mmap, Boolean deptId, Boolean checkFlag) {
        mmap.put("dept", deptService.selectDeptById((long) 100));
        mmap.put("selectedUserIds", selectedUserIds);
        mmap.put("selectedUserNames", selectedUserNames);
        mmap.put("selectedDeptIds", selectedDeptIds);
        mmap.put("selectedDeptNames", selectedDeptNames);
        mmap.put("multiSelectFlag", multiSelectFlag);
        mmap.put("checkFlag", checkFlag);
        if (StringUtils.isNotNull(deptId)) {
            if (deptId == true) {
                mmap.put("deptId", 201);
                mmap.put("excludeId", 201);
            } else if (deptId == false) {
                mmap.put("deptId", 202);
                mmap.put("excludeId", 202);
            }
        }

        return prefix + "/userTree";
    }


    @GetMapping("/listForTree")
    @ResponseBody
    public String listForTree(SysUser user) {
        user.setStatus("0");
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey())) {
                        user.setFormalFlag("0");
                    }
                }
            }
        }
        List<SysUser> list = userService.selectUserList(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("userList", list);
        return jsonObject.toString();
    }

    @PostMapping("/selectUserByIds")
    @ResponseBody
    public AjaxResult selectUserByIds(String ids) {
        Map<String, Object> map = new HashMap<>();
        for (String string : ids.split(",")) {
            SysUser sysUser = userService.selectUserById(Long.valueOf(string));
            map.put("user", sysUser.getUserName());
        }
        return AjaxResult.success(map);
    }
}