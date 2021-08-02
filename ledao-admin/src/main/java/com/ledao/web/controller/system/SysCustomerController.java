package com.ledao.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.github.pagehelper.PageHelper;
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
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey())) {
                        sysCustomer.setCreateBy(ShiroUtils.getLoginName());
                        sysCustomer.setCreator(ShiroUtils.getSysUser().getUserName());
                        SysUser sysUser = sysUserService.selectUserByLoginName(sysCustomer.getCreateBy());
                        sysCustomer.setDeptId(sysUser.getDeptId());
                        sysCustomer.setDeptName(sysUser.getDept().getDeptName());
                    } else {
                        sysCustomer.setAgentId(ShiroUtils.getLoginName());
                        sysCustomer.setAgent(ShiroUtils.getSysUser().getUserName());
                        if (StringUtils.isNotEmpty(sysCustomer.getCreateBy())) {
                            SysUser sysUser = sysUserService.selectUserByLoginName(sysCustomer.getCreateBy());
                            sysCustomer.setDeptId(sysUser.getDeptId());
                            sysCustomer.setDeptName(sysUser.getDept().getDeptName());
                        } else {
                            sysCustomer.setCreateBy(ShiroUtils.getLoginName());
                            sysCustomer.setCreator(ShiroUtils.getSysUser().getUserName());
                            sysCustomer.setDeptId(ShiroUtils.getSysUser().getDeptId());
                            sysCustomer.setDeptName(ShiroUtils.getSysUser().getDept().getDeptName());
                        }
                    }
                }
            } else {
                sysCustomer.setAgentId(ShiroUtils.getLoginName());
                sysCustomer.setAgent(ShiroUtils.getSysUser().getUserName());
                if (StringUtils.isNotEmpty(sysCustomer.getCreateBy())) {
                    SysUser sysUser = sysUserService.selectUserByLoginName(sysCustomer.getCreateBy());
                    if (StringUtils.isNotNull(sysUser)) {
                        sysCustomer.setDeptId(sysUser.getDeptId());
                        sysCustomer.setDeptName(sysUser.getDept().getDeptName());
                    }
                } else {
                    sysCustomer.setCreateBy(ShiroUtils.getLoginName());
                    sysCustomer.setCreator(ShiroUtils.getSysUser().getUserName());
                }
            }
        }
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

    @GetMapping("/toDingDing")
    public String toDingDing() {
        return prefix + "/dingding";
    }

    //请假
    private static String LEAVE = "PROC-AA188D5E-88D3-49DE-B221-99ED8DFE012E";

    //报销
    private static String REIMBURSEMENT = "PROC-50C4FCAD-05FB-4D7D-91C0-FD643B5B9F34";

    //加班
    private static String WORK_OVERTIME = "PROC-7C65368A-3551-4531-B78E-53475B360C67";

    //采购
    private static String PURCHASE = "PROC-ED98ACBF-623A-4DB4-9C55-AD0EDFE22875";

    //备用金申请
    private static String PETTY_CASH = "PROC-05A3B964-2B36-488E-B2F2-2F4935EBE2F1";

    //用印
    private static String SEAL = "PROC-85B8CB94-CC3C-4929-8E84-96E1AA2EB9A2";

    //付款申请
    private static String PAYMENT = "PROC-EFYJFT8W-3YYZHDQUQ9RCC3DSIFWN3-8IOKTWNJ-9";

    //开票申请
    private static String BILLING = "PROC-JFYJETRV-XOYZBKBDNTT0Y18FJ6O62-4DGJUWNJ-H";

    //招待申请
    private static String ENTERTAIN = "PROC-FFYJAWGV-AAYZU8ZXPR8E807NKQ772-GZO3UWNJ-21";

    //档案入库申请
    private static String WAREHOUSING = "PROC-JFYJH47W-J0J1D7HW10XDJAXBYD1S2-UICYI3QJ-7";

    //档案出库申请
    private static String DELIVERY = "PROC-AKYJDMNV-48J1M7LC5NMT29PV4URY2-WKA0U4QJ-S";

    //法律文书出具（审核）
    private static String LEGAL_INSTRUMENT = "PROC-DF8388B5-BA66-4DD2-9AED-695152B7C136";

    //公司注册、变更、注销申请
    private static String COMPANY = "PROC-770E9DE8-F088-4263-A8D0-0D5603E13203";

    //特例审批
    private static String SPECIAL_CASE = "PROC-E434C381-DD55-4399-BAE0-D37868A12F00";

    //出差
    private static String BUSINESS_TRAVEL = "PROC-EB470DE8-BA8F-4417-9395-852033F000D3";

    //外出
    private static String GO_OUT = "PROC-1C0FB9E3-BCB5-4DB4-8FA8-3F17AF8FB3C6";

    //补卡
    private static String CARD_REPLACEMENT = "PROC-8D45554D-43EC-4533-BA98-A7D571FC34C1";

    @GetMapping("/getProcessCodeList")
    @ResponseBody
    public static AjaxResult getProcessCodeList(String company) throws ApiException {
        String accessToken = getAccessToken(company);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/process/listbyuserid");
        OapiProcessListbyuseridRequest req = new OapiProcessListbyuseridRequest();
        req.setOffset(0L);
        req.setSize(100L);
        OapiProcessListbyuseridResponse rsp = client.execute(req, accessToken);
        return AjaxResult.success(rsp.getResult().getProcessList());
    }

    /**
     * 根据流程code获取流程名称
     *
     * @param accessToken
     * @param processCode
     * @return
     * @throws ApiException
     */
    public static String getProcessNameByCode(String accessToken, String processCode) throws ApiException {
        String name = "";
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/process/listbyuserid");
        OapiProcessListbyuseridRequest req = new OapiProcessListbyuseridRequest();
        req.setOffset(0L);
        req.setSize(100L);
        OapiProcessListbyuseridResponse rsp = client.execute(req, accessToken);
        List<OapiProcessListbyuseridResponse.ProcessTopVo> list = rsp.getResult().getProcessList();
        for (OapiProcessListbyuseridResponse.ProcessTopVo processTopVo : list) {
            if (processCode.equals(processTopVo.getProcessCode())) {
                name = processTopVo.getName();
            }
        }
        return name;
    }

    public static List<String> getProcessList(String company, String process) throws ApiException {
        String accessToken = getAccessToken(company);
        DingTalkClient
                client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/listids");
        OapiProcessinstanceListidsRequest req = new OapiProcessinstanceListidsRequest();
        req.setProcessCode(process);
        req.setStartTime(1625443200000L);
        //req.setEndTime(1625450400000L);
        OapiProcessinstanceListidsResponse rsp = client.execute(req, accessToken);
        return rsp.getResult().getList();
    }

    public static List<Map<String, Object>> processList(String accessToken, List<OapiProcessinstanceGetResponse.OperationRecordsVo> operationRecords) {
        List<Map<String, Object>> newsList = new ArrayList<>();
        for (OapiProcessinstanceGetResponse.OperationRecordsVo operationRecordsVo : operationRecords) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            String userName = "";
            String result = "";
            String type = "";
            String remarks = "";
            if ("AGREE".equals(operationRecordsVo.getOperationResult())) {
                result = "同意";
            } else if ("REFUSE".equals(operationRecordsVo.getOperationResult())) {
                result = "拒绝";
            }
            JSONObject userInfo = getUserInfo(accessToken, operationRecordsVo.getUserid());
            //审批人名称
            if (StringUtils.isNotNull(userInfo.get("name"))) {
                userName = userInfo.get("name").toString();
            }

            if ("EXECUTE_TASK_NORMAL".equals(operationRecordsVo.getOperationType())) {
                type = "正常执行";
            } else if ("START_PROCESS_INSTANCE".equals(operationRecordsVo.getOperationType())) {
                type = "发起流程";
            } else if ("PROCESS_CC".equals(operationRecordsVo.getOperationType())) {
                type = "抄送人";
            }
            if (StringUtils.isNotEmpty(operationRecordsVo.getRemark())) {
                if (operationRecordsVo.getRemark().contains("[")) {
                    String str = subString(operationRecordsVo.getRemark(), "[", "]");
                    String str1 = operationRecordsVo.getRemark().substring(0, operationRecordsVo.getRemark().indexOf(")"));
                    String str2 = operationRecordsVo.getRemark().substring(str1.length() + 1, operationRecordsVo.getRemark().length());
                    remarks = str + str2;
                } else {
                    remarks = operationRecordsVo.getRemark();
                }
            }

            map1.put("userName", userName);
            map1.put("result", result);
            map1.put("type", type);
            map1.put("remarks", remarks);
            map1.put("date", DateUtils.parseDateToStr("MM-dd HH:mm", operationRecordsVo.getDate()));
            newsList.add(map1);
        }
        return newsList;
    }

    @GetMapping("/getActivity")
    @ResponseBody
    public AjaxResult getActivity(String company, String process) throws ApiException, IOException {
        Map<String, Object> map = new HashMap<>();
        String fileName = "";
        String templateName = "";
        String accessToken = getAccessToken(company);
        List<String> stringList = getProcessList(company, process);
        for (String string : stringList) {
            DingTalkClient client1 = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/get");
            OapiProcessinstanceGetRequest req1 = new OapiProcessinstanceGetRequest();
            req1.setProcessInstanceId(string);
            OapiProcessinstanceGetResponse rsp1 = client1.execute(req1, accessToken);
            List<OapiProcessinstanceGetResponse.FormComponentValueVo> stringList1 = rsp1.getProcessInstance().getFormComponentValues();

            /*for (OapiProcessinstanceGetResponse.FormComponentValueVo valueVo : stringList1) {
                if (StringUtils.isNotNull(valueVo.getValue()) && StringUtils.isNotEmpty(valueVo.getValue())) {
                    if (isJSON2(valueVo.getValue())) {
                        JSONArray jsonArray = JSONArray.parseArray(valueVo.getValue().trim());
                        if (StringUtils.isNotNull(jsonArray) && jsonArray.size() > 0) {
                            int size = jsonArray.size();
                            for (int i = 0; i < size; i++) {
                                String jsonObject = jsonArray.getString(i);
                                logger.info("数据：-----" + jsonObject);
                                if (StringUtils.isNotEmpty(jsonObject)) {
                                    if (isJSON2(jsonObject)) {
                                        if (JSONObject.parseObject(jsonObject) instanceof JSONObject) {
                                            logger.info("object:====" + JSONObject.parseObject(jsonObject).get("props"));
                                            *//*if (StringUtils.isNotNull(JSONObject.parseObject(jsonObject)) && StringUtils.isNotNull(JSONObject.parseObject(jsonObject).get("fileId"))) {
             *//**//*map.put("title", rsp1.getProcessInstance().getTitle());
                                                map.put("deptName", getDeptNameByDeptId(accessToken, Long.valueOf(rsp1.getProcessInstance().getOriginatorDeptId())));
                                                String url = getDowFileUrl(accessToken, string, JSONObject.parseObject(jsonObject).get("fileId").toString());
                                                String oldFile = StringUtils.subString(url, "/", true, "?", false);
                                                String oldFileName = oldFile.substring(0, oldFile.indexOf("."));
                                                String oldFileType = oldFile.substring(oldFile.substring(0, oldFile.indexOf(".")).length() + 1, oldFile.length());*//**//*
                                                logger.info("object:====" + JSONObject.parseObject(jsonObject));
                                            }*//*
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }*/


            List<Map<String, Object>> newsList = processList(accessToken, rsp1.getProcessInstance().getOperationRecords());
            List<OapiProcessinstanceGetResponse.OperationRecordsVo> operationRecordsVoList = rsp1.getProcessInstance().getOperationRecords();
            List<Map<String, Object>> fileList = new ArrayList<>();
            map.put("list", newsList);
            if (LEAVE.equals(process)) {
                for (OapiProcessinstanceGetResponse.FormComponentValueVo valueVo : stringList1) {
                    //设置文件名称
                    fileName = rsp1.getProcessInstance().getTitle() + "(" + DateUtils.dateTime(rsp1.getProcessInstance().getCreateTime()) + ")";
                    map.put("title", rsp1.getProcessInstance().getTitle());
                    map.put("deptName", getDeptNameByDeptId(accessToken, Long.valueOf(rsp1.getProcessInstance().getOriginatorDeptId())));
                    map.put("userName", valueVo.getName());
                    map.put("company", company);
                    map.put("createTime", "2021-07-05");
                    //原因
                    if ("TextareaField".equals(valueVo.getComponentType())) {
                        map.put("text", valueVo.getValue());
                    }
                    //职位代理人
                    if ("InnerContactField".equals(valueVo.getComponentType())) {
                        map.put("userNames", valueVo.getValue());
                    }
                    if ("DDHolidayField".equals(valueVo.getComponentType())) {
                        if (isJSON2(valueVo.getExtValue())) {
                            JSONArray jsonArray = JSONObject.parseObject(valueVo.getExtValue()).getJSONArray("detailList");
                            if (jsonArray.size() > 0) {
                                String startTime = DateUtils.timeToFormat(Long.valueOf(JSONObject.parseObject(jsonArray.getJSONObject(0).get("approveInfo").toString()).get("fromTime").toString()));
                                map.put("startTime", DateUtils.getTimeToAmOrPm(DateUtils.parseToDate(startTime), true));
                            }
                            BigDecimal dayCount = new BigDecimal(0);
                            for (int i = 0; i < jsonArray.size(); i++) {
                                dayCount = dayCount.add(new BigDecimal(JSONObject.parseObject(jsonArray.getJSONObject(i).get("approveInfo").toString()).get("durationInDay").toString()));
                                String endTime = DateUtils.timeToFormat(Long.valueOf(JSONObject.parseObject(jsonArray.getJSONObject(i).get("approveInfo").toString()).get("toTime").toString()));
                                map.put("endTime", DateUtils.getTimeToAmOrPm(DateUtils.parseToDate(endTime), true));
                            }
                            map.put("dayTime", dayCount);
                        }
                    }
                }
                templateName = "leave.ftl";
            } else if (CARD_REPLACEMENT.equals(process)) {
                for (OapiProcessinstanceGetResponse.FormComponentValueVo valueVo : stringList1) {
                    String time = "";
                    String text = "";
                    //设置文件名称
                    fileName = rsp1.getProcessInstance().getTitle() + "(" + DateUtils.dateTime(rsp1.getProcessInstance().getCreateTime()) + ")";
                    map.put("title", rsp1.getProcessInstance().getTitle());
                    map.put("deptName", getDeptNameByDeptId(accessToken, Long.valueOf(rsp1.getProcessInstance().getOriginatorDeptId())));
                    //原因
                    if ("TextareaField".equals(valueVo.getComponentType())) {
                        map.put("remarks", valueVo.getValue());
                    }
                    if (StringUtils.isNotEmpty(valueVo.getExtValue())) {
                        if (isJSON2(valueVo.getExtValue())) {
                            if (StringUtils.isNotNull(JSONObject.parseObject(valueVo.getExtValue()).getLong("timestamp"))) {
                                time = DateUtils.timeToFormat(JSONObject.parseObject(valueVo.getExtValue()).getLong("timestamp"));
                            }
                            text = JSONObject.parseObject(valueVo.getExtValue()).get("planText").toString();
                        }
                    }
                    map.put("text", text);
                    map.put("time", time);
                }
                templateName = "cardReplacement.ftl";
            } else if (PETTY_CASH.equals(process)) {
                for (OapiProcessinstanceGetResponse.FormComponentValueVo valueVo : stringList1) {
                    String money = "";
                    String amount = "";
                    String attachment = "";

                    //设置文件名称
                    fileName = rsp1.getProcessInstance().getTitle() + "(" + DateUtils.dateTime(rsp1.getProcessInstance().getCreateTime()) + ")";
                    map.put("title", rsp1.getProcessInstance().getTitle());
                    map.put("deptName", getDeptNameByDeptId(accessToken, Long.valueOf(rsp1.getProcessInstance().getOriginatorDeptId())));
                    //借款事由
                    if ("借款事由".equals(valueVo.getName())) {
                        map.put("text", valueVo.getValue());
                    }

                    //申请金额（元）
                    if ("MoneyField".equals(valueVo.getComponentType())) {
                        money = valueVo.getValue();
                        amount = JSONObject.parseObject(valueVo.getExtValue()).getString("upper");
                        map.put("money", money);
                        map.put("amount", amount);
                    }

                    //备注
                    if ("备注".equals(valueVo.getName())) {
                        map.put("remarks", valueVo.getValue());
                    }

                    //关联审批单
                    if ("DDAttachment".equals(valueVo.getComponentType())) {
                        JSONArray jsonArray = JSONObject.parseArray(valueVo.getValue());
                        for (int i = 0; i < jsonArray.size(); i++) {
                            attachment = jsonArray.getJSONObject(i).getString("fileName");
                            map.put("attachment", attachment);
                        }
                    }

                    //使用时间
                    if ("DDDateRangeField".equals(valueVo.getComponentType())) {
                        JSONArray jsonArray = JSONObject.parseArray(valueVo.getValue());
                        if (jsonArray.size() > 0) {
                            map.put("startTime", jsonArray.get(0));
                        }
                        if (jsonArray.size() > 1) {
                            map.put("endTime", jsonArray.get(1));
                        }
                    }
                    map.put("userName", getUserInfo(accessToken, rsp1.getProcessInstance().getOriginatorUserid()).get("name"));
                    map.put("departmentName", getDeptNameByDeptId(accessToken, Long.valueOf(rsp1.getProcessInstance().getOriginatorDeptId())));
                }
                templateName = "pettyCash.ftl";
            } else if (PAYMENT.equals(process)) {
                for (OapiProcessinstanceGetResponse.FormComponentValueVo valueVo : stringList1) {
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    String companyName = "";
                    String projectName = "";
                    String text = "";
                    String moneyCount = "";
                    String moneyType = "";
                    String banK = "";
                    String collectionName = "";
                    String collectionNumber = "";
                    String bankRemarks = "";
                    String relation = "";
                    String attachment = "";
                    //设置文件名称
                    fileName = rsp1.getProcessInstance().getTitle() + "(" + DateUtils.parseDateToStr("YYYYMMDDHHMMSS", rsp1.getProcessInstance().getCreateTime()) + ")";
                    map.put("title", rsp1.getProcessInstance().getTitle());
                    map.put("deptName", getDeptNameByDeptId(accessToken, Long.valueOf(rsp1.getProcessInstance().getOriginatorDeptId())));

                    //付款公司
                    if ("付款公司".equals(valueVo.getName())) {
                        map.put("companyName", valueVo.getValue());
                    }

                    //项目名称
                    if ("项目名称".equals(valueVo.getName())) {
                        projectName = valueVo.getValue();
                        map.put("projectName", projectName);
                    }

                    //付款事由
                    if ("付款事由".equals(valueVo.getName())) {
                        text = valueVo.getValue();
                        map.put("subjectMatter", text);
                    }

                    //付款总额
                    if ("付款总额".equals(valueVo.getName())) {
                        moneyCount = valueVo.getValue();
                        map.put("moneyCount", moneyCount);
                    }

                    //付款方式
                    if ("付款方式".equals(valueVo.getName())) {
                        moneyType = valueVo.getValue();
                        map.put("moneyType", moneyType);
                    }

                    //收款名称
                    if ("收款名称".equals(valueVo.getName())) {
                        collectionName = valueVo.getValue();
                        map.put("collectionName", collectionName);
                    }

                    //收款开户行
                    if ("收款开户行".equals(valueVo.getName())) {
                        banK = valueVo.getValue();
                        map.put("bankName", banK);
                    }


                    //收款账号
                    if ("收款账号".equals(valueVo.getName())) {
                        collectionNumber = valueVo.getValue();
                        map.put("collectionNumber", collectionNumber);
                    }

                    //银行打款备注
                    if ("银行打款备注".equals(valueVo.getName())) {
                        bankRemarks = valueVo.getValue();
                        map.put("bankRemarks", bankRemarks);
                    }

                    //关联审批单
                    if ("关联审批单".equals(valueVo.getName())) {
                        relation = valueVo.getValue();
                        map.put("relation", relation);
                    }

                    //关联审批单
                    if ("DDAttachment".equals(valueVo.getComponentType())) {
                        JSONArray jsonArray = JSONObject.parseArray(valueVo.getValue());
                        if (StringUtils.isNotNull(jsonArray)) {
                            for (int i = 0; i < jsonArray.size(); i++) {
                                logger.info("fileUrl:=====" + getDowFileUrl(accessToken, string, jsonArray.getJSONObject(i).getLong("fileId").toString()));
                                map1.put("fileUrl", getDowFileUrl(accessToken, string, jsonArray.getJSONObject(i).getLong("fileId").toString()));
                                map1.put("attachmentName", jsonArray.getJSONObject(i).getString("fileName"));
                                fileList.add(map1);
                            }
                        }
                    }
                    map.put("fileList", fileList);
                    templateName = "payment.ftl";
                }
            } else if (SEAL.equals(process)) {

            }
            WorldUtil.createWordToLocal(map, "/templates/down", templateName, "F:\\work\\" + company + "\\" + getProcessNameByCode(accessToken, process) + "\\" + DateUtils.datePath(), fileName + ".doc");
        }
        return AjaxResult.success();
    }

    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
        }
        if (strEndIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
        }
        /* 开始截取 */
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }

    public static Key getKey(String keySeed) {
        if (keySeed == null) {
            keySeed = System.getenv("AES_SYS_KEY");
        }
        if (keySeed == null) {
            keySeed = System.getProperty("AES_SYS_KEY");
        }
        if (keySeed == null || keySeed.trim().length() == 0) {
            keySeed = "abcd1234!@#$";// 默认种子
        }
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(keySeed.getBytes());
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(secureRandom);
            return generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<File> readFile(String fileDir) {
        List<File> fileList = new ArrayList<File>();
        File file = new File(fileDir);
        File[] files = file.listFiles();// 获取目录下的所有文件或文件夹
        if (files == null) {// 如果目录为空，直接退出
            return null;
        }

        // 遍历，目录下的所有文件
        for (File f : files) {
            if (f.isFile()) {
                fileList.add(f);
            } else if (f.isDirectory()) {
                readFile(f.getAbsolutePath());
            }
        }
        return fileList;
    }

    public static boolean isJSON2(String str) {
        boolean result = false;
        try {
            Object obj = JSON.parse(str);
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public String getDowFileUrl(String accessToken, String processInstanceId, String fileId) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/file/url/get");
        OapiProcessinstanceFileUrlGetRequest req = new OapiProcessinstanceFileUrlGetRequest();
        OapiProcessinstanceFileUrlGetRequest.GrantCspaceRequest obj1 = new OapiProcessinstanceFileUrlGetRequest.GrantCspaceRequest();
        obj1.setProcessInstanceId(processInstanceId);
        obj1.setFileId(fileId);
        req.setRequest(obj1);
        OapiProcessinstanceFileUrlGetResponse rsp = client.execute(req, accessToken);
        return rsp.getResult().getDownloadUri();
    }

    public static String getAccessToken(String company) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest req = new OapiGettokenRequest();
        if ("江苏乐道".equals(company)) {
            req.setAppkey("dingutkcyhqh66yeoiui");
            req.setAppsecret("CERkBL6C22tGvX7V2klfRziDTT49PDVWfusBLw4qNNaO3zK4iwXTU3ftej818DJF");
        } else if ("无锡苏金".equals(company)) {
            //无锡苏金
            req.setAppkey("ding6vrnzgcrjohdzhhn");
            req.setAppsecret("vrWGWkEsaoIcO3LJaPXO0LTdsOMouJK52_rwCF2lJgwL_-SuUd9bLEvbTO8bJUxL");
        } else if ("无锡乐道".equals(company)) {
            //无锡乐道
            req.setAppkey("dingxmsyt40ex9ymdgaq");
            req.setAppsecret("KlAZlbSknVLnIfR4Z_w89bbnPpBOhAEpf6fEm_ZhLFkma9QmJrIhUE4yvvYZ4njD");
        } else if ("无锡天孚".equals(company)) {
            //无锡天孚营销策划有限公司
            req.setAppkey("dingzwxdgv8woydoewzo");
            req.setAppsecret("EIQfYeaMKoaQDtgVAt4ITVo0DS2dhbalQNbZu_Srn24zWaNsVKJDePq2J6NzVP51");
        } else if ("江苏青泓".equals(company)) {
            //江苏青泓
            req.setAppkey("dingsbogfid0tdpqokgu");
            req.setAppsecret("hzYljfKYIvOnrAt22_7GlbLk4aBUk_9JQlPgVUmLWZVD0Oud8hSYa3oR0iZ_z-cc");
        } else if ("无锡乐道天香".equals(company)) {
            //无锡乐道天香
            req.setAppkey("ding4w0ssnlufxxpxq2g");
            req.setAppsecret("-xsS6jUaXu-_JLuEEP44XHwjGcv-w7w-B7N-qP-rHx01tkhstQ7GkvTocqYALyNb");
        }

        req.setHttpMethod("GET");
        OapiGettokenResponse rsp = client.execute(req);
        return rsp.getAccessToken();
    }

    @GetMapping("/getData")
    @ResponseBody
    public AjaxResult getData(HttpServletResponse response, String company) throws Exception {
        String accessToken = getAccessToken(company);
        Set<String> set = new HashSet<>();
        getUserIdsPage(accessToken, "3", 0L, set);
        List<String> list = new ArrayList<String>(set);

        /*List<String> userList = new ArrayList<>();
        List<OapiV2DepartmentListsubResponse.DeptBaseResponse> deptList = getDeptList(accessToken);
        if (StringUtils.isNotNull(deptList)) {
            for (OapiV2DepartmentListsubResponse.DeptBaseResponse deptBaseResponse : deptList) {
                userList.addAll(getUserIdListByDeptId(accessToken, deptBaseResponse.getDeptId()));
            }
        }*/

        //获取用户打卡记录
        long i = 0L;
        List<ExcelModel> excelModels = new ArrayList<>();
        while (true) {
            //获取userIds 指定日期内的打卡记录
            DingTalkClient client1 = new DefaultDingTalkClient("https://oapi.dingtalk.com/attendance/list");
            OapiAttendanceListRequest req1 = new OapiAttendanceListRequest();
            req1.setWorkDateFrom("2021-07-12 00:00:00");
            req1.setWorkDateTo("2021-07-16 00:00:00");
            //最大50个
            //req1.setUserIdList(userList);
            req1.setUserIdList(list);
            req1.setOffset(i);
            req1.setLimit(50L);
            OapiAttendanceListResponse rsp1 = client1.execute(req1, accessToken);
            List<OapiAttendanceListResponse.Recordresult> recordresults = rsp1.getRecordresult();
            if (recordresults.isEmpty()) {
                break;
            }
            //打卡结果封装
            for (OapiAttendanceListResponse.Recordresult recordresult : recordresults) {
                String userId = recordresult.getUserId();
                //根据id获取用户详情
                JSONObject userInfo = getUserInfo(accessToken, userId);
                String onDuty = recordresult.getCheckType().equals("OnDuty") ? "上班" : "下班";
                Date userCheckTime = recordresult.getUserCheckTime();
                LocalDateTime localDateTime = LocalDateTime.ofInstant(userCheckTime.toInstant(), ZoneId.systemDefault());
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String checkTime = localDateTime.format(dtf);
                //封装数据ExcelModel
                ExcelModel excelModel = new ExcelModel();
                excelModel.setName(userInfo.getString("name"));
                excelModel.setUserId(userId);
                excelModel.setCheckType(onDuty);
                //获取考勤组名称
                /*Long deptId = null;
                for (int j = 0; j < userInfo.getJSONArray("department").size(); j++) {
                    deptId = Long.valueOf(userInfo.getJSONArray("department").get(j).toString());
                }
                excelModel.setGroupId(getDeptNameByDeptId(accessToken, deptId));*/
                excelModel.setGroupId(getAttendance(accessToken, recordresult.getGroupId()));
                excelModel.setCheckTime(checkTime);
                if (!excelModels.contains(excelModel)) {
                    excelModels.add(excelModel);
                }
            }
            i += 50;
        }
        ExcelUtil<ExcelModel> util = new ExcelUtil<ExcelModel>(ExcelModel.class);
        return util.exportExcel(excelModels, "考勤记录");
    }

    /**
     * 根据部门id获取部门名称
     *
     * @param accessToken
     * @param deptId
     * @return
     * @throws ApiException
     */
    public static String getDeptNameByDeptId(String accessToken, Long deptId) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/get");
        OapiV2DepartmentGetRequest req = new OapiV2DepartmentGetRequest();
        req.setDeptId(deptId);
        OapiV2DepartmentGetResponse rsp = client.execute(req, accessToken);
        return rsp.getResult().getName();
    }


    /**
     * 获取部门列表
     *
     * @param accessToken
     * @return
     * @throws ApiException
     */
    public static List<OapiV2DepartmentListsubResponse.DeptBaseResponse> getDeptList(String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsub");
        OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
        OapiV2DepartmentListsubResponse rsp = client.execute(req, accessToken);
        List<OapiV2DepartmentListsubResponse.DeptBaseResponse> list = rsp.getResult();
        return list;
    }

    /**
     * 根据部门id查询人员列表
     *
     * @param accessToken
     * @param deptId
     * @return
     * @throws ApiException
     */
    public static List<String> getUserIdListByDeptId(String accessToken, Long deptId) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/user/listid");
        OapiUserListidRequest req = new OapiUserListidRequest();
        req.setDeptId(deptId);
        OapiUserListidResponse rsp = client.execute(req, accessToken);
        return rsp.getResult().getUseridList();
    }

    /**
     * 查询用户信息
     *
     * @param userId 用户id
     */
    public static JSONObject getUserInfo(String accessToken, String userId) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
            OapiUserGetRequest req = new OapiUserGetRequest();
            req.setUserid(userId);
            req.setHttpMethod("GET");
            OapiUserGetResponse rsp = client.execute(req, accessToken);
            return JSONObject.parseObject(rsp.getBody());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询考勤组名称
     *
     * @param id 考勤组id
     */
    public static String getAttendance(String accessToken, Long id) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/attendance/group/query");
        OapiAttendanceGroupQueryRequest req = new OapiAttendanceGroupQueryRequest();
        req.setOpUserId("024809384635332125");
        req.setGroupId(id);
        try {
            OapiAttendanceGroupQueryResponse rsp = client.execute(req, accessToken);
            return rsp.getResult().getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询全部在职用户Id
     *
     * @param status     在职员工子状态筛选，其他状态无效。2:试用期；3:正式；5:待离职；-1:无状态。
     * @param nextCursor 下一次调用需要传的分页值
     * @param set        调用这个方法的外部set集合 如 Set<String> set = new HashSet<>() ; 自动将全部id封装到这个set中
     */
    public static void getUserIdsPage(String accessToken, String status, Long nextCursor, Set<String> set) {
        JSONObject jsonObject = getUserIds(accessToken, status, nextCursor);
        List<String> dataList = (List<String>) jsonObject.get("dataList");
        set.addAll(dataList);
        Long cursor = jsonObject.getLong("cursor");
        if (cursor != null) {
            getUserIdsPage(accessToken, status, cursor, set);
        }
    }

    private static JSONObject getUserIds(String accessToken, String status, Long nextCursor) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/queryonjob");
            OapiSmartworkHrmEmployeeQueryonjobRequest req = new OapiSmartworkHrmEmployeeQueryonjobRequest();
            req.setStatusList(status);
            req.setOffset(nextCursor);
            req.setSize(50L);
            OapiSmartworkHrmEmployeeQueryonjobResponse rsp = client.execute(req, accessToken);
            List<String> dataList = rsp.getResult().getDataList();
            Long cursor = rsp.getResult().getNextCursor();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dataList", dataList);
            jsonObject.put("cursor", cursor);
            return jsonObject;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
