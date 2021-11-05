package com.ledao.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.github.pagehelper.PageHelper;
import com.ledao.activity.service.ISysApplyInService;
import com.ledao.activity.service.ISysApplyWorkflowService;
import com.ledao.common.annotation.Log;
import com.ledao.common.constant.Constants;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.core.page.PageDao;
import com.ledao.common.core.page.TableDataInfo;
import com.ledao.common.core.page.TableSupport;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.ServletUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.freemarker.WorldUtil;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.utils.sql.SqlUtil;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.*;
import com.ledao.system.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.ledao.common.annotation.ExcelModel;
import com.taobao.api.ApiException;

import javax.crypto.KeyGenerator;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Key;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 客户库Controller
 *
 * @author lxz
 * @date 2020-11-18
 */
@Controller
@RequestMapping("/system/customer")
public class SysCustomerController<main> extends BaseController {
    private String prefix = "system/customer";

    @Autowired
    private ISysCustomerService sysCustomerService;

    @Autowired
    private ISysItemService sysItemService;

    @Autowired
    private ISysProjectService sysProjectService;

    @Autowired
    private ISysPcustomerService sysPcustomerService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysDictDataService sysDictDataService;

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private ISysApplyWorkflowService sysApplyWorkflowService;

    @RequiresPermissions("system:customer:view")
    @GetMapping()
    public String customer(String pageNumber, String pageSize, ModelMap modelMap) {
        modelMap.put("pageNumber", pageNumber);
        modelMap.put("pageSize", pageSize);
        return prefix + "/customer";
    }

    /**
     * 查询客户库列表
     */
    @RequiresPermissions("system:customer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysCustomer sysCustomer) {
        startPage();
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())
                            && !"investmentManager".equals(sysRole.getRoleKey()) && !"thbManager".equals(sysRole.getRoleKey())) {
                        /*if ("bgczCommon".equals(sysRole.getRoleKey()) || "bgczManager".equals(sysRole.getRoleKey()) || "investmentCommon".equals(sysRole.getRoleKey())
                                || "investmentManager2".equals(sysRole.getRoleKey()) || "investmentManager".equals(sysRole.getRoleKey())) {
                            String ids = "201,207,208,209";
                            sysCustomer.setDeptIds(ids.split(","));
                        } else {
                            sysCustomer.setShareUserId(ShiroUtils.getUserId().toString());
                            sysCustomer.setCreateBy(ShiroUtils.getLoginName());
                        }*/
                        sysCustomer.setCreateBy(ShiroUtils.getLoginName());
                        sysCustomer.setShareUserId(ShiroUtils.getUserId().toString());
                        sysCustomer.setDeptIds(ShiroUtils.getSysUser().getDeptId().toString().split(","));
                    }
                }
            }
        }
        List<SysCustomer> list = sysCustomerService.selectSysCustomerList(sysCustomer);
        for (SysCustomer sysCustomer1 : list) {
            StringBuffer sb = new StringBuffer();
            SysItem sysItem = new SysItem();
            SysUser currentUser1 = ShiroUtils.getSysUser();
            if (currentUser1 != null) {
                // 如果是超级管理员，则不过滤数据
                if (!currentUser1.isAdmin()) {
                    List<SysRole> getRoles = currentUser1.getRoles();
                    for (SysRole sysRole : getRoles) {

                        if ("SJXXB".equals(sysRole.getRoleKey()) || "seniorRoles".equals(sysRole.getRoleKey())) {
                            sysCustomer1.setIsAdmin("Y");
                        } else {
                            if ("thbManager".equals(sysRole.getRoleKey()) || "thbManager2".equals(sysRole.getRoleKey()) || "tzbzz".equals(sysRole.getRoleKey())
                                    || "investmentManager".equals(sysRole.getRoleKey()) || "bgczManager".equals(sysRole.getRoleKey()) || "thbzz".equals(sysRole.getRoleKey())) {
                                sysCustomer1.setIsAdmin("N");
                            }
                        }
                        /*if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                            if (!"bgczCommon".equals(sysRole.getRoleKey()) && !"bgczManager".equals(sysRole.getRoleKey()) && !"investmentCommon".equals(sysRole.getRoleKey())
                                    && !"investmentManager2".equals(sysRole.getRoleKey()) && !"investmentManager".equals(sysRole.getRoleKey())) {
                                //  sysItem.setCreateBy(ShiroUtils.getLoginName());
                                sysCustomer1.setIsAdmin("N");
                            } else {
                                sysCustomer1.setIsAdmin("N");
                            }
                        } else {
                            sysCustomer1.setIsAdmin("Y");
                        }*/
                    }
                } else {
                    sysCustomer1.setIsAdmin("Y");
                }
            }

            //将手机号中的，替换成/
            if (StringUtils.isNotEmpty(sysCustomer1.getContactNumber())) {
                sysCustomer1.setContactNumber(sysCustomer1.getContactNumber().replace(",", "/"));
            }

            if (StringUtils.isNotEmpty(sysCustomer1.getWeChatNumber())) {
                sysCustomer1.setWeChatNumber(sysCustomer1.getWeChatNumber().replace(",", "/"));
            }

            sysItem.setCustomerId(sysCustomer1.getCustomerId());
            List<SysItem> sysItemList = sysItemService.selectSysItemList(sysItem);
            for (SysItem sysItem1 : sysItemList) {
                if (StringUtils.isNotNull(sysItem1.getProjectName())) {
                    sb.append(sysItem1.getProjectName()).append(";");
                }
            }
            sysCustomer1.setProjectName(sb.toString());
        }
        return getDataTable(list);
    }

    /**
     * 导出客户库列表1113333(chenjie123321)
     */
    @RequiresPermissions("system:customer:export")
    @Log(title = "客户库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysCustomer sysCustomer) {
        List<SysCustomer> list = new ArrayList<>();
        PageHelper.orderBy("create_time desc");
        if (StringUtils.isNotEmpty(sysCustomer.getCustomerIds())) {
            SysCustomer sysCustomer1 = new SysCustomer();
            sysCustomer1.setCustomerIds(sysCustomer.getCustomerIds());
            list = sysCustomerService.selectSysCustomerList(sysCustomer1);
        } else {
            list = sysCustomerService.selectSysCustomerList(sysCustomer);
        }
        for (SysCustomer sysCustomer1 : list) {
            if (StringUtils.isEmpty(sysCustomer1.getWechatFlag())) {
                sysCustomer1.setWechatFlag("否");
            }
            StringBuffer sb = new StringBuffer();
            SysItem sysItem = new SysItem();
            sysItem.setCustomerId(sysCustomer1.getCustomerId());
            List<SysItem> sysItemList = sysItemService.selectSysItemList(sysItem);
            for (SysItem sysItem1 : sysItemList) {
                if (StringUtils.isNotEmpty(sysItem1.getProjectName())) {
                    sb.append(sysItem1.getProjectName()).append(";");
                }
            }
            sysCustomer1.setProjectName(sb.toString());
        }
        ExcelUtil<SysCustomer> util = new ExcelUtil<SysCustomer>(SysCustomer.class);
        return util.exportExcel(list, "客户详情");
    }

    /**
     * z
     * 新增客户库
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        boolean isAdmin = true;
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        isAdmin = false;
                        modelMap.put("isAdmin", isAdmin);
                    } else {
                        modelMap.put("isAdmin", isAdmin);
                    }
                }
            } else {
                modelMap.put("isAdmin", isAdmin);
            }
        }
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("sys_customer_label");
        modelMap.put("types", sysDictDataService.selectDictDataList(sysDictData));
        modelMap.put("createBy", ShiroUtils.getLoginName());
        modelMap.put("creator", ShiroUtils.getSysUser().getUserName());
        return prefix + "/add";
    }

    /**
     * 新增保存客户库
     */
    @RequiresPermissions("system:customer:add")
    @Log(title = "客户库", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysCustomer sysCustomer) {
        if (StringUtils.isNotEmpty(sysCustomer.getContactNumber())) {
            sysCustomer.setContactNumber(sysCustomer.getContactNumber().replace(",", "/"));
        }

        if (StringUtils.isNotEmpty(sysCustomer.getWeChatNumber())) {
            sysCustomer.setWeChatNumber(sysCustomer.getWeChatNumber().replace(",", "/"));
        }
        SysUser sysUser = sysUserService.selectUserByLoginName(sysCustomer.getCreateBy());
        sysCustomer.setDeptId(sysUser.getDeptId());
        sysCustomer.setDeptName(sysUser.getDept().getDeptName());

        sysCustomer.setAgentId(ShiroUtils.getLoginName());
        sysCustomer.setAgent(ShiroUtils.getSysUser().getUserName());
        if (StringUtils.isNull(sysCustomer.getDeptId()) && StringUtils.isEmpty(sysCustomer.getDeptName())) {
            sysCustomer.setDeptId(ShiroUtils.getSysUser().getDeptId());
            sysCustomer.setDeptName(ShiroUtils.getSysUser().getDept().getDeptName());
        }
        if (202 == sysCustomer.getDeptId()) {
            sysCustomer.setDeptType("投后部");
        } else if (201 == sysCustomer.getDeptId()) {
            sysCustomer.setDeptType("投资部");
        } else {
            sysCustomer.setDeptType(selectDeptTypeById(sysCustomer.getDeptId()));
        }

        //分享人推送
        Map<String, String> parmStr = new HashMap<>();
        List<SysUser> userList = new ArrayList<>();
        parmStr.put("first", "您有一个客户信息分享提醒");
        parmStr.put("word1", "客户姓名：" + sysCustomer.getContacts());
        parmStr.put("word2", "-");
        parmStr.put("word3", (sysUserService.selectUserByLoginName(sysCustomer.getCreateBy())).getUserName());
        parmStr.put("word4", "-");
        if (StringUtils.isNotEmpty(sysCustomer.getShareUserId())) {
            for (String string : sysCustomer.getShareUserId().split(",")) {
                if (StringUtils.isNotEmpty(string)) {
                    userList.add(sysUserService.selectUserById(Long.valueOf(string)));
                }
            }
        }
        sysApplyWorkflowService.sendTaskMsg(userList, parmStr);
        return toAjax(sysCustomerService.insertSysCustomer(sysCustomer));
    }

    /**
     * 修改客户库
     */
    @GetMapping("/edit/{customerId}")
    public String edit(@PathVariable("customerId") Long customerId, ModelMap mmap) {
        Boolean isAdmin = true;
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        isAdmin = false;
                        mmap.put("isAdmin", isAdmin);
                    } else {
                        mmap.put("isAdmin", isAdmin);
                    }
                }
            } else {
                mmap.put("isAdmin", isAdmin);
            }
        }
        SysCustomer sysCustomer = sysCustomerService.selectSysCustomerById(customerId);
        mmap.put("sysCustomer", sysCustomer);
        if (StringUtils.isNotEmpty(sysCustomer.getContactNumber())) {
            sysCustomer.setContactNumber(sysCustomer.getContactNumber().replace(",", "/"));
        }
        mmap.put("nums", sysCustomer.getContactNumber());

        if (StringUtils.isNotEmpty(sysCustomer.getWeChatNumber())) {
            sysCustomer.setWeChatNumber(sysCustomer.getWeChatNumber().replace(",", "/"));
        }
        mmap.put("weChatnums", sysCustomer.getWeChatNumber());


        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("sys_customer_label");
        Map<String, String> map = new HashMap<>();
        List<SysDictData> sysDictDataList = sysDictDataService.selectDictDataList(sysDictData);
        if (StringUtils.isNotEmpty(sysCustomer.getCustomerLable())) {
            for (String string1 : sysCustomer.getCustomerLable().split(",")) {
                map.put(string1, string1);
            }
        }
        for (SysDictData sysDictData1 : sysDictDataList) {
            if (StringUtils.isNotEmpty(map.get(sysDictData1.getDictValue()))) {
                if (map.get(sysDictData1.getDictValue()).equals(sysDictData1.getDictValue())) {
                    sysDictData1.setFlag(true);
                }
            }
        }
        mmap.put("types", sysDictDataList);
        return prefix + "/edit";
    }

    /**
     * 修改保存客户库
     */
    @RequiresPermissions("system:customer:edit")
    @Log(title = "客户库", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysCustomer sysCustomer) {
        if (StringUtils.isNotEmpty(sysCustomer.getContactNumber())) {
            sysCustomer.setContactNumber(sysCustomer.getContactNumber().replace(",", "/"));
        }
        if (StringUtils.isNotEmpty(sysCustomer.getWeChatNumber())) {
            sysCustomer.setWeChatNumber(sysCustomer.getWeChatNumber().replaceAll(",", "/"));
        }
        List<SysItem> sysItemList = sysItemService.selectItemByCustomerId(sysCustomer.getCustomerId());
        for (SysItem sysItem : sysItemList) {
            sysItem.setShareUserId(sysItem.getShareUserId() + "," + sysCustomer.getShareUserId());
            sysItem.setShareUserName(sysItem.getShareUserName() + "," + sysCustomer.getShareUserName());
            sysItemService.updateSysItem(sysItem);
        }
        SysPcustomer sysPcustomer = new SysPcustomer();
        sysPcustomer.setCustomerId(sysCustomer.getCustomerId().toString());
        List<SysPcustomer> sysPcustomerList = sysPcustomerService.selectSysPcustomerList(sysPcustomer);
        for (SysPcustomer sysPcustomer1 : sysPcustomerList) {
            sysPcustomer1.setShareUserId(sysPcustomer1.getShareUserId() + "," + sysCustomer.getShareUserId());
            sysPcustomer1.setShareUserName(sysPcustomer1.getShareUserName() + "," + sysCustomer.getShareUserName());
            sysPcustomerService.updateSysPcustomer(sysPcustomer1);
        }

        sysCustomer.setUpdateBy(ShiroUtils.getLoginName());
        sysCustomer.setReviser(ShiroUtils.getSysUser().getUserName());

        //分享推送
        Map<String, String> parmStr = new HashMap<>();
        List<SysUser> userList = new ArrayList<>();
        parmStr.put("first", "您有一个客户信息分享提醒");
        parmStr.put("word1", "客户姓名：" + sysCustomer.getContacts());
        parmStr.put("word2", "-");
        parmStr.put("word3", (sysUserService.selectUserByLoginName(sysCustomer.getCreateBy())).getUserName());
        parmStr.put("word4", "-");
        if (StringUtils.isNotEmpty(sysCustomer.getShareUserId())) {
            for (String string : sysCustomer.getShareUserId().split(",")) {
                if (StringUtils.isNotEmpty(string)) {
                    userList.add(sysUserService.selectUserById(Long.valueOf(string)));
                }
            }
        }
        sysApplyWorkflowService.sendTaskMsg(userList, parmStr);
        return toAjax(sysCustomerService.updateSysCustomer(sysCustomer));
    }

    /**
     * 删除客户库
     */
    @RequiresPermissions("system:customer:remove")
    @Log(title = "客户库", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        StringBuffer sb = new StringBuffer();
        StringBuffer sb1 = new StringBuffer();
        for (String string : ids.split(",")) {
            SysPcustomer sysPcustomer = new SysPcustomer();
            sysPcustomer.setCustomerId(string);
            List<SysPcustomer> sysPcustomerList = sysPcustomerService.selectSysPcustomerList(sysPcustomer);
            for (SysPcustomer sysPcustomer1 : sysPcustomerList) {
                sb.append(sysPcustomer1.getDealCustomerId()).append(",");
            }

            SysItem sysItem = new SysItem();
            sysItem.setCustomerId(Long.valueOf(string));
            List<SysItem> sysItemList = sysItemService.selectSysItemList(sysItem);
            for (SysItem sysItem1 : sysItemList) {
                sb1.append(sysItem1.getItemId()).append(",");
            }
        }
        sysPcustomerService.deleteSysPcustomerByIds(sb.toString());
        sysItemService.deleteSysItemByIds(sb1.toString());
        return toAjax(sysCustomerService.deleteSysCustomerByIds(ids));
    }

    public String repeatStr(String string1, String string2) {
        StringBuffer sb = new StringBuffer();
        for (String string : string2.split(",")) {
            if (!string.equals(string1)) {
                sb.append(string).append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 选择客户树
     */
    @GetMapping("/selectCustomerTree")
    public String selectCustomerTree(String customerIds, String customerNames, ModelMap mmap) {
        mmap.put("customerIds", customerIds);
        mmap.put("customerNames", customerNames);
        return prefix + "/tree";
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(SysCustomer sysCustomer) {
        StringBuffer tzbSb = new StringBuffer();
        List<SysDept> tzbList = sysDeptService.selectDeptByParentId(201L);
        for (SysDept sysDept : tzbList) {
            tzbSb.append(sysDept.getDeptId()).append(",");
        }
        if (tzbSb.toString().contains(ShiroUtils.getSysUser().getDeptId().toString())) {
            sysCustomer.setDeptIds(tzbSb.toString().split(","));
        }
        /*if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        sysCustomer.setDeptId(ShiroUtils.getSysUser().getDeptId());
                    }
                }
            }
        }*/
        //sysCustomer.setDeptId(ShiroUtils.getSysUser().getDeptId());
        return sysCustomerService.checkPhoneUnique(sysCustomer);
    }

    /**
     * 校验微信号码
     */
    @PostMapping("/checkWeChatNumberUnique")
    @ResponseBody
    public String checkWeChatNumberUnique(SysCustomer sysCustomer) {
        StringBuffer tzbSb = new StringBuffer();

        List<SysDept> tzbList = sysDeptService.selectDeptByParentId(201L);
        for (SysDept sysDept : tzbList) {
            tzbSb.append(sysDept.getDeptId()).append(",");
        }
        if (tzbSb.toString().contains(ShiroUtils.getSysUser().getDeptId().toString())) {
            sysCustomer.setDeptIds(tzbSb.toString().split(","));
        }
        String flag = sysCustomerService.checkWeChatNumberUnique(sysCustomer);
        return flag;
    }

    @RequiresPermissions("system:customer:list")
    @GetMapping({"/queryAll"})
    public String queryAll(ModelMap modelMap, SysCustomer sysCustomer) {
       /* StringBuffer sb = new StringBuffer();
        for (String string : sysCustomer.getCustomerLables()) {
            sb.append(string).append(",");
        }
        sysCustomer.setCustomerLable(sb.deleteCharAt(sb.length() - 1).toString());*/
        modelMap.put("sysCustomer", sysCustomer);
        return "system/customer/queryAll";
    }

    @RequiresPermissions("system:customer:list")
    @PostMapping("/queryAllList")
    @ResponseBody
    public TableDataInfo queryAllList(SysCustomer sysCustomer) {
        startPage();
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        if ("bgczCommon".equals(sysRole.getRoleKey()) || "bgczManager".equals(sysRole.getRoleKey()) || "investmentCommon".equals(sysRole.getRoleKey())
                                || "investmentManager2".equals(sysRole.getRoleKey()) || "investmentManager".equals(sysRole.getRoleKey())) {
                            String ids = "201,207,208,209";
                            sysCustomer.setDeptIds(ids.split(","));
                        } else {
                            sysCustomer.setCreateBy(ShiroUtils.getLoginName());
                        }
                    }
                }
            }
        }
        List<SysCustomer> list = sysCustomerService.queryAll(sysCustomer);
        for (SysCustomer sysCustomer1 : list) {
            StringBuffer sb = new StringBuffer();
            SysItem sysItem = new SysItem();
            SysUser currentUser1 = ShiroUtils.getSysUser();
            if (currentUser1 != null) {
                // 如果是超级管理员，则不过滤数据
                if (!currentUser1.isAdmin()) {
                    List<SysRole> getRoles = currentUser1.getRoles();
                    for (SysRole sysRole : getRoles) {
                        if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                            if (!"bgczCommon".equals(sysRole.getRoleKey()) && !"bgczManager".equals(sysRole.getRoleKey()) && !"investmentCommon".equals(sysRole.getRoleKey())
                                    && !"investmentManager2".equals(sysRole.getRoleKey()) && !"investmentManager".equals(sysRole.getRoleKey())) {
                                //  sysItem.setCreateBy(ShiroUtils.getLoginName());
                                sysCustomer1.setIsAdmin("N");
                            } else {
                                sysCustomer1.setIsAdmin("N");
                            }
                        } else {
                            sysCustomer1.setIsAdmin("Y");
                        }
                    }
                } else {
                    sysCustomer1.setIsAdmin("Y");
                }
            }
            sysItem.setCustomerId(sysCustomer1.getCustomerId());
            List<SysItem> sysItemList = sysItemService.selectSysItemList(sysItem);
            for (SysItem sysItem1 : sysItemList) {
                if (StringUtils.isNotNull(sysItem1.getProjectName())) {
                    sb.append(sysItem1.getProjectName()).append(";");
                }
            }
            sysCustomer1.setProjectName(sb.toString().trim());
        }
        return getDataTable(list);
    }

    /**
     * 选择人员
     */
    @GetMapping("/selectUser")
    public String selectUser(String loginName, String userName, Boolean singleSelect, ModelMap mmap) {
        mmap.put("loginName", loginName);
        mmap.put("userName", userName);
        mmap.put("singleSelect", singleSelect);
        return prefix + "/selectUser";
    }

    /**
     * 选择人员
     */
    @GetMapping("/selectShareUser")
    public String selectShareUser(String userId, String userName, ModelMap mmap) {
        mmap.put("userId", userId);
        mmap.put("userName", userName);
        mmap.put("list", sysUserService.selectUserByIds(userId));
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        if (!"bgczCommon".equals(sysRole.getRoleKey()) && !"bgczManager".equals(sysRole.getRoleKey()) && !"investmentCommon".equals(sysRole.getRoleKey())
                                && !"investmentManager2".equals(sysRole.getRoleKey()) && !"investmentManager".equals(sysRole.getRoleKey())) {
                            mmap.put("deptId", 202);
                        }
                    }
                }
            }
        }

        return prefix + "/selectShareUser";
    }

    /*
     * 详情信息
     * @Description:
     * @param: customerId
     * @param: mmap
     * @return java.lang.String
     * @author lxz
     * @date 2021/3/26 14:29
     */
    @GetMapping("/detail/{customerId}")
    public String detail(@PathVariable("customerId") Long customerId, ModelMap mmap) {
        Boolean isAdmin = true;
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey())) {
                        isAdmin = false;
                        mmap.put("isAdmin", isAdmin);
                    } else {
                        mmap.put("isAdmin", isAdmin);
                    }
                }
            } else {
                mmap.put("isAdmin", isAdmin);
            }
        }
        SysCustomer sysCustomer = sysCustomerService.selectSysCustomerById(customerId);
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("sys_customer_label");
        Map<String, String> map = new HashMap<>();
        List<SysDictData> sysDictDataList = sysDictDataService.selectDictDataList(sysDictData);
        if (StringUtils.isNotEmpty(sysCustomer.getCustomerLable())) {
            for (String string1 : sysCustomer.getCustomerLable().split(",")) {
                map.put(string1, string1);
            }
        }
        for (SysDictData sysDictData1 : sysDictDataList) {
            if (StringUtils.isNotEmpty(map.get(sysDictData1.getDictValue()))) {
                if (map.get(sysDictData1.getDictValue()).equals(sysDictData1.getDictValue())) {
                    sysDictData1.setFlag(true);
                }
            }
        }
        mmap.put("types", sysDictDataList);
        mmap.put("sysCustomer", sysCustomer);
        return prefix + "/detail";
    }

    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysCustomer sysCustomer) {
        sysCustomer.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysCustomerService.updateSysCustomer(sysCustomer));
    }

    /**
     * 客户库分析（地区）
     */
    @PostMapping("/selectCustomerByCity")
    @ResponseBody
    public String selectCustomerByCity(SysCustomer sysCustomer) {
        //PageHelper.getLocalPage().setCount(false);
        List<SysCustomer> sysCustomerList = sysCustomerService.selectSysCustomerByParam(sysCustomer, "city");
        JSONArray jsonArray = new JSONArray();
        for (SysCustomer sysCustomer1 : sysCustomerList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", sysCustomer1.getCity().substring(0, 2));
            jsonObject.put("value", sysCustomer1.getCityCount());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    /**
     * 客户库分析（客户标签）
     */
    @PostMapping("/selectSysCustomerByCustomerLable")
    @ResponseBody
    public String selectSysCustomerByCustomerLable(SysCustomer sysCustomer) {
        JSONArray jsonArray = new JSONArray();
        //PageHelper.getLocalPage().setCount(false);
        //查询所有数据
        List<SysCustomer> sysCustomerList = sysCustomerService.selectSysCustomerByParam(sysCustomer, "customerLable");

        for (SysCustomer sysCustomer1 : sysCustomerList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", sysCustomer1.getShareholder());
            jsonObject.put("value", sysCustomer1.getCustomerLableCount());
            jsonArray.add(jsonObject);
        }
        jsonArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getBigInteger("value")).reversed());
        return jsonArray.toJSONString();
    }

    /**
     * 客户库分析（部门）
     */
    @PostMapping("/selectSysCustomerByDept")
    @ResponseBody
    public String selectSysCustomerByDept(SysCustomer sysCustomer) {
        JSONArray jsonArray = new JSONArray();
        //PageHelper.getLocalPage().setCount(false);
        List<SysCustomer> sysCustomerList = sysCustomerService.selectSysCustomerByParam(sysCustomer, "deptType");
        for (SysCustomer sysCustomer1 : sysCustomerList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", sysCustomer1.getDeptType());
            jsonObject.put("value", sysCustomer1.getCustomerLableCount());
            jsonArray.add(jsonObject);
        }

        jsonArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getString("type")));

        return jsonArray.toJSONString();
    }

    public String selectDeptTypeById(Long deptId) {
        String deptType = "";
        StringBuffer tzbSb = new StringBuffer();
        StringBuffer thbSb = new StringBuffer();
        List<SysDept> tzbList = sysDeptService.selectDeptByParentId(201L);
        List<SysDept> thbList = sysDeptService.selectDeptByParentId(202L);

        for (SysDept sysDept : tzbList) {
            tzbSb.append(sysDept.getDeptId()).append(",");
        }

        for (SysDept sysDept : thbList) {
            thbSb.append(sysDept.getDeptId()).append(",");
        }
        if (tzbSb.toString().contains(deptId.toString())) {
            deptType = "投资部";
        } else if (thbSb.toString().contains(deptId.toString())) {
            deptType = "投后部";
        } else {
            deptType = "其他部门";
        }
        return deptType;
    }

    /**
     * 根据部门id查询部门下人员的数据
     */
    @PostMapping("/selectSysCustomerByDeptId")
    @ResponseBody
    public String selectSysCustomerByDeptId(SysCustomer sysCustomer) {
        JSONArray jsonArray = new JSONArray();
        // PageHelper.getLocalPage().setCount(false);
        List<SysCustomer> sysCustomerList = sysCustomerService.selectSysCustomerByParam(sysCustomer, "dept");

        for (SysCustomer sysCustomer1 : sysCustomerList) {
            sysCustomer1.setWechatFlag("是");
            sysCustomer1.setDeptName(sysCustomer1.getDeptName());
            List<SysCustomer> weChatList = sysCustomerService.selectSysCustomerList(sysCustomer1);
            List<SysCustomer> customerLableList = sysCustomerService.selectSysCustomerByParam(sysCustomer1, "customerLable");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", sysCustomer1.getDeptType());
            jsonObject.put("type", sysCustomer1.getDeptName());
            jsonObject.put("value", sysCustomer1.getCustomerLableCount());
            jsonObject.put("weChatNum", weChatList.size());
            Collections.sort(customerLableList, new Comparator<SysCustomer>() {
                @Override
                public int compare(SysCustomer o1, SysCustomer o2) {
                    // 按照学生的年龄进行降序排列
                    if (o1.getCustomerLableCount() > o2.getCustomerLableCount()) {
                        return -1;
                    }
                    if (o1.getCustomerLableCount().equals(o2.getCustomerLableCount())) {
                        return 0;
                    }
                    return 1;
                }
            });
            jsonObject.put("list", customerLableList);
            jsonArray.add(jsonObject);
        }
        jsonArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getString("name")));

        return jsonArray.toString();
    }

    @PostMapping("/delContactNumber")
    @ResponseBody
    public AjaxResult delContactNumber(SysCustomer sysCustomer) {
        StringBuffer sb = new StringBuffer();
        SysCustomer sysCustomer1 = sysCustomerService.selectSysCustomerById(sysCustomer.getCustomerId());
        sysCustomer1.setContactNumber(sysCustomer1.getContactNumber().replace(",", "/"));
        for (String string : sysCustomer1.getContactNumber().split("/")) {
            if (!string.equals(sysCustomer.getContactNumber())) {
                sb.append(string).append(",");
            }
        }
        if (StringUtils.isNotEmpty(sb.toString())) {
            sysCustomer1.setContactNumber(sb.deleteCharAt(sb.length() - 1).toString());
        }
        sysCustomerService.updateSysCustomer(sysCustomer1);
        return AjaxResult.success();
    }

    @PostMapping("/delWeChatNumber")
    @ResponseBody
    public AjaxResult delWeChatNumber(SysCustomer sysCustomer) {
        StringBuffer sb = new StringBuffer();
        SysCustomer sysCustomer1 = sysCustomerService.selectSysCustomerById(sysCustomer.getCustomerId());
        sysCustomer1.setWeChatNumber(sysCustomer1.getWeChatNumber().replace(",", "/"));
        for (String string : sysCustomer1.getWeChatNumber().split("/")) {
            if (!string.equals(sysCustomer.getWeChatNumber())) {
                sb.append(string).append(",");
            }
        }
        if (StringUtils.isNotEmpty(sb.toString())) {
            sysCustomer1.setWeChatNumber(sb.deleteCharAt(sb.length() - 1).toString());
        }
        sysCustomerService.updateSysCustomer(sysCustomer1);
        return AjaxResult.success();
    }

    @PostMapping("/selectUserById")
    @ResponseBody
    public AjaxResult selectUserById(SysUser sysUser) {
        return AjaxResult.success(sysUserService.selectUserById(sysUser.getUserId()));
    }
}
