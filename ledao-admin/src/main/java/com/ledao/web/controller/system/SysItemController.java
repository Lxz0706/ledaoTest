package com.ledao.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.framework.web.dao.server.Sys;
import com.ledao.system.dao.*;
import com.ledao.system.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ledao.common.annotation.Log;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 项目选择Controller
 *
 * @author lxz
 * @date 2020-12-02
 */
@Controller
@RequestMapping("/system/item")
public class SysItemController extends BaseController {
    private String prefix = "system/item";

    @Autowired
    private ISysItemService sysItemService;

    @Autowired
    private ISysProjectService sysProjectService;

    @Autowired
    private ISysBgczzckService sysBgczzckService;

    @Autowired
    private ISysZckService sysZckService;

    @Autowired
    private ISysCustomerService sysCustomerService;

    @Autowired
    private ISysPcustomerService sysPcustomerService;

    @Autowired
    private ISysUserService sysUserService;

    @RequiresPermissions("system:item:view")
    @GetMapping()
    public String item() {
        return prefix + "/item";
    }

    /**
     * 查询项目选择列表
     */
    @RequiresPermissions("system:item:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysItem sysItem) {
        startPage();
        // 获取当前的用户
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey())) {
                        if (!"bgczCommon".equals(sysRole.getRoleKey()) && !"bgczManager".equals(sysRole.getRoleKey()) && !"investmentCommon".equals(sysRole.getRoleKey())
                                && !"investmentManager2".equals(sysRole.getRoleKey()) && !"investmentManager".equals(sysRole.getRoleKey())
                                && !"thbManager".equals(sysRole.getRoleKey()) && !"thbManager2".equals(sysRole.getRoleKey())
                                && !"thbzz".equals(sysRole.getRoleKey()) && !"tzbzz".equals(sysRole.getRoleKey())) {
                            sysItem.setShareUserId(ShiroUtils.getUserId().toString());
                            sysItem.setCreateBy(ShiroUtils.getLoginName());
                        }
                    }
                }
            }
        }
        List<SysItem> list = sysItemService.selectSysItemList(sysItem);
        return getDataTable(list);
    }

    /**
     * 导出项目选择列表
     */
    @RequiresPermissions("system:item:export")
    @Log(title = "项目选择", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysItem sysItem) {
        List<SysItem> list = sysItemService.selectSysItemList(sysItem);
        ExcelUtil<SysItem> util = new ExcelUtil<SysItem>(SysItem.class);
        return util.exportExcel(list, "item");
    }

    /**
     * 新增项目选择
     */
    @GetMapping("/add/{customerId}")
    public String add(@PathVariable("customerId") String customerId, ModelMap modelMap) {
        String role = "";
        SysUser currentUser = ShiroUtils.getSysUser();
        SysCustomer sysCustomer = sysCustomerService.selectSysCustomerById(Long.valueOf(customerId));
        SysUser sysUser = sysUserService.selectUserByLoginName(sysCustomer.getCreateBy());
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if (!"SJXXB".equals(sysRole.getRoleKey()) && !"seniorRoles".equals(sysRole.getRoleKey()) && !"admin".equals(sysRole.getRoleKey())) {
                        if ("thbManager".equals(sysRole.getRoleKey()) || "thbCommon".equals(sysRole.getRoleKey())
                                || "thbManager2".equals(sysRole.getRoleKey()) || "thbzz".equals(sysRole.getRoleKey())) {
                            if (ShiroUtils.getLoginName().equals(sysCustomer.getCreateBy())) {
                                role = "thb";
                            }
                        } else if ("bgczCommon".equals(sysRole.getRoleKey()) || "bgczManager".equals(sysRole.getRoleKey())) {
                            if (ShiroUtils.getLoginName().equals(sysCustomer.getCreateBy())) {
                                role = "bgcz";
                            }
                        } else if ("investmentCommon".equals(sysRole.getRoleKey()) || "investmentManager2".equals(sysRole.getRoleKey())
                                || "investmentManager".equals(sysRole.getRoleKey()) || "tzbzz".equals(sysRole.getRoleKey())) {
                            if (ShiroUtils.getLoginName().equals(sysCustomer.getCreateBy())) {
                                role = "tzb";
                            }
                        }
                    } else {
                        modelMap.put("createBy", sysCustomer.getCreateBy());
                        if (StringUtils.isNotEmpty(sysUser.getRoles())) {
                            for (SysRole sysRole1 : sysUser.getRoles()) {
                                if ("SJXXB".equals(sysRole1.getRoleKey()) || "seniorRoles".equals(sysRole1.getRoleKey()) || "admin".equals(sysRole1.getRoleKey())) {
                                    role = "thb";
                                } else if ("thbManager".equals(sysRole1.getRoleKey()) || "thbCommon".equals(sysRole1.getRoleKey())
                                        || "thbManager2".equals(sysRole1.getRoleKey()) || "thbzz".equals(sysRole1.getRoleKey())) {
                                    role = "thb";
                                } else if ("bgczCommon".equals(sysRole1.getRoleKey()) || "bgczManager".equals(sysRole1.getRoleKey())) {
                                    role = "bgcz";
                                } else if ("investmentCommon".equals(sysRole1.getRoleKey()) || "investmentManager2".equals(sysRole1.getRoleKey())
                                        || "investmentManager".equals(sysRole1.getRoleKey()) || "tzbzz".equals(sysRole1.getRoleKey())) {
                                    role = "tzb";
                                }
                            }
                        } else {
                            role = "thb";
                        }

                    }
                }
            } else {
                modelMap.put("createBy", sysCustomer.getCreateBy());
                if (StringUtils.isNotEmpty(sysUser.getRoles())) {
                    for (SysRole sysRole1 : sysUser.getRoles()) {
                        if ("SJXXB".equals(sysRole1.getRoleKey()) || "seniorRoles".equals(sysRole1.getRoleKey()) || "admin".equals(sysRole1.getRoleKey())) {
                            role = "thb";
                        } else if ("thbManager".equals(sysRole1.getRoleKey()) || "thbCommon".equals(sysRole1.getRoleKey())
                                || "thbManager2".equals(sysRole1.getRoleKey()) || "thbzz".equals(sysRole1.getRoleKey())) {
                            role = "thb";
                        } else if ("bgczCommon".equals(sysRole1.getRoleKey()) || "bgczManager".equals(sysRole1.getRoleKey())) {
                            role = "bgcz";
                        } else if ("investmentCommon".equals(sysRole1.getRoleKey()) || "investmentManager2".equals(sysRole1.getRoleKey())
                                || "investmentManager".equals(sysRole1.getRoleKey()) || "tzbzz".equals(sysRole1.getRoleKey())) {
                            role = "tzb";
                        }
                    }
                } else {
                    role = "thb";
                }

            }
        }
        modelMap.put("role", role);
        modelMap.put("customerId", customerId);
        //客户标签
        if (StringUtils.isNotEmpty(sysCustomerService.selectSysCustomerById(Long.valueOf(customerId)).getCustomerLable())) {
            modelMap.put("type", sysCustomerService.selectSysCustomerById(Long.valueOf(customerId)).getCustomerLable().split(","));
        }

        return prefix + "/add";
    }

    /**
     * 新增保存项目选择
     */
    @RequiresPermissions("system:item:add")
    @Log(title = "项目选择", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysItem sysItem) {
        SysCustomer sysCustomer = sysCustomerService.selectSysCustomerById(sysItem.getCustomerId());
        if (StringUtils.isEmpty(sysItem.getCreateBy())) {
            sysItem.setCreateBy(ShiroUtils.getLoginName());
        }
        SysUser sysUser = sysUserService.selectUserByLoginName(sysCustomer.getCreateBy());
        sysItem.setShareUserId(sysCustomer.getShareUserId() + "," + sysUser.getUserId());
        sysItem.setShareUserName(sysCustomer.getShareUserName() + "," + sysCustomer.getCreator());
        int row = sysItemService.insertSysItem(sysItem);
        SysUser currentUser = sysUserService.selectUserByLoginName(sysItem.getCreateBy());
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                if (StringUtils.isNotNull(sysItem.getProjectName()) && StringUtils.isNotNull(sysItem.getProjectId())) {
                    for (SysRole sysRole : getRoles) {
                        if ("investmentManager2".equals(sysRole.getRoleKey()) || "investmentManager".equals(sysRole.getRoleKey())
                                || "investmentCommon".equals(sysRole.getRoleKey()) || "tzbzz".equals(sysRole.getRoleKey())) {
                            for (String string : sysItem.getProjectId().split(",")) {
                                SysPcustomer sysPcustomer = new SysPcustomer();
                                sysPcustomer.setCustomerId(sysItem.getCustomerId().toString());
                                sysPcustomer.setCustomerName(sysCustomer.getContacts());
                                sysPcustomer.setCustomerLable(sysItem.getCustomerLable());
                                sysPcustomer.setItemId(sysItem.getItemId());
                                sysPcustomer.setDeptType("tzb");
                                sysPcustomer.setProjectId(Long.valueOf(string));
                                sysPcustomer.setCreateBy(sysItem.getCreateBy());
                                sysPcustomer.setShareUserId(sysItem.getShareUserId());
                                sysPcustomer.setShareUserName(sysItem.getShareUserName());
                                sysPcustomerService.insertSysPcustomer(sysPcustomer);
                            }
                        } else if ("bgczCommon".equals(sysRole.getRoleKey()) || "bgczManager".equals(sysRole.getRoleKey())) {
                            for (String string : sysItem.getProjectId().split(",")) {
                                SysPcustomer sysPcustomer = new SysPcustomer();
                                sysPcustomer.setCustomerId(sysItem.getCustomerId().toString());
                                sysPcustomer.setCustomerName(sysCustomer.getContacts());
                                sysPcustomer.setCustomerLable(sysItem.getCustomerLable());
                                sysPcustomer.setItemId(sysItem.getItemId());
                                sysPcustomer.setDeptType("bgcz");
                                sysPcustomer.setProjectId(Long.valueOf(string));
                                sysPcustomer.setCreateBy(sysItem.getCreateBy());
                                sysPcustomer.setShareUserId(sysItem.getShareUserId());
                                sysPcustomer.setShareUserName(sysItem.getShareUserName());
                                sysPcustomerService.insertSysPcustomer(sysPcustomer);
                            }
                        } else if ("thbManager".equals(sysRole.getRoleKey()) || "thbCommon".equals(sysRole.getRoleKey())
                                || "SJXXB".equals(sysRole.getRoleKey()) || "thbManager2".equals(sysRole.getRoleKey())
                                || "thbzz".equals(sysRole.getRoleKey())) {
                            for (String string : sysItem.getProjectId().split(",")) {
                                SysPcustomer sysPcustomer = new SysPcustomer();
                                sysPcustomer.setCustomerId(sysItem.getCustomerId().toString());
                                sysPcustomer.setCustomerName(sysCustomer.getContacts());
                                sysPcustomer.setCustomerLable(sysItem.getCustomerLable());
                                sysPcustomer.setItemId(sysItem.getItemId());
                                sysPcustomer.setDeptType("thb");
                                sysPcustomer.setProjectId(Long.valueOf(string));
                                sysPcustomer.setCreateBy(sysItem.getCreateBy());
                                sysPcustomer.setShareUserId(sysItem.getShareUserId());
                                sysPcustomer.setShareUserName(sysItem.getShareUserName());
                                sysPcustomerService.insertSysPcustomer(sysPcustomer);
                            }
                        }
                    }
                }
            }
        }
        return toAjax(row);
    }

    /**
     * 修改项目选择
     */
    @GetMapping("/edit/{itemId}")
    public String edit(@PathVariable("itemId") Long itemId, ModelMap mmap) {
        String role = "";
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                for (SysRole sysRole : getRoles) {
                    if ("thbManager".equals(sysRole.getRoleKey()) || "thbCommon".equals(sysRole.getRoleKey())) {
                        role = "thb";
                    } else if ("bgczCommon".equals(sysRole.getRoleKey()) || "bgczManager".equals(sysRole.getRoleKey())) {
                        role = "bgcz";
                    } else if ("investmentCommon".equals(sysRole.getRoleKey()) || "investmentManager2".equals(sysRole.getRoleKey()) || "investmentManager".equals(sysRole.getRoleKey())) {
                        role = "tzb";
                    }
                }
            }
        }
        mmap.put("role", role);
        SysItem sysItem = sysItemService.selectSysItemById(itemId);
        mmap.put("sysItem", sysItem);
        return prefix + "/edit";
    }

    /**
     * 修改保存项目选择
     */
    @RequiresPermissions("system:item:edit")
    @Log(title = "项目选择", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysItem sysItem) {
        /*SysCustomer sysCustomer = sysCustomerService.selectSysCustomerById(sysItem.getCustomerId());
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                if (StringUtils.isNotNull(sysItem.getProjectName()) && StringUtils.isNotNull(sysItem.getProjectId())) {
                    for (SysRole sysRole : getRoles) {
                        if ("investmentManager2".equals(sysRole.getRoleKey()) || "investmentManager".equals(sysRole.getRoleKey())) {
                            SysZck sysZck = sysZckService.selectSysZckById(sysItem.getProjectId());
                            sysZck.setCustomerId(sysItem.getCustomerId());
                            if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                sysZck.setCustomer(sysCustomer.getCustomerName());
                            }
                            sysZckService.updateSysZck(sysZck);
                        } else if ("bgczCommon".equals(sysRole.getRoleKey()) || "bgczManager".equals(sysRole.getRoleKey())) {
                            SysBgczzck sysBgczzck = sysBgczzckService.selectSysBgczzckById(sysItem.getProjectId());
                            sysBgczzck.setCustomerId(sysItem.getCustomerId());
                            if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                sysBgczzck.setCustomer(sysCustomer.getCustomerName());
                            }
                            sysBgczzckService.updateSysBgczzck(sysBgczzck);
                        } else if ("thbManager".equals(sysRole.getRoleKey()) || "thbCommon".equals(sysRole.getRoleKey()) || "investmentManager".equals(sysRole.getRoleKey())) {
                            SysProject sysProject = sysProjectService.selectSysProjectById(sysItem.getProjectId());
                            if ("资产供应方".equals(sysItem.getCustomerLable())) {
                                if (StringUtils.isNull(sysProject.getAssetSupplierId())) {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setAssetSupplierId(sysItem.getCustomerId().toString());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setAssetSupplierId(sysProject.getAssetSupplierId() + "," + sysItem.getCustomerId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getAssetSupplierName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setAssetSupplierName(sysCustomer.getCustomerName());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setAssetSupplierName(sysProject.getAssetSupplierId() + "," + sysCustomer.getCustomerName());
                                    }
                                }
                            } else if ("资金供应方".equals(sysItem.getCustomerLable())) {
                                if (StringUtils.isNull(sysProject.getFundingProviderId())) {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setFundingProviderId(sysItem.getCustomerId().toString());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setFundingProviderId(sysProject.getFundingProviderId() + "," + sysItem.getCustomerId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getFundingProviderName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setFundingProviderName(sysCustomer.getCustomerName());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setFundingProviderName(sysProject.getFundingProviderName() + "," + sysCustomer.getCustomerName());
                                    }
                                }
                            } else if ("律师".equals(sysItem.getCustomerLable())) {
                                if (StringUtils.isNull(sysProject.getLawyerId())) {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setLawyerId(sysItem.getCustomerId().toString());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setLawyerId(sysProject.getLawyerId() + "," + sysItem.getCustomerId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getLawyerName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setLawyerName(sysCustomer.getCustomerName());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setLawyerName(sysProject.getLawyerName() + "," + sysCustomer.getCustomerName());
                                    }
                                }
                            } else if ("中介方".equals(sysItem.getCustomerLable())) {
                                if (StringUtils.isNull(sysProject.getIntermediaryId())) {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setIntermediaryId(sysItem.getCustomerId().toString());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setIntermediaryId(sysProject.getIntermediaryId() + "," + sysItem.getCustomerId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getIntermediaryName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setIntermediaryName(sysCustomer.getCustomerName());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setIntermediaryName(sysProject.getIntermediaryName() + "," + sysCustomer.getCustomerName());
                                    }
                                }
                            } else if ("债权意向客户".equals(sysItem.getCustomerLable())) {
                                if (StringUtils.isNull(sysProject.getZqyxCustomerId())) {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setZqyxCustomerId(sysItem.getCustomerId().toString());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setZqyxCustomerId(sysProject.getZqyxCustomerId() + "," + sysItem.getCustomerId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getZqyxCustomerName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setZqyxCustomerName(sysCustomer.getCustomerName());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setZqyxCustomerName(sysProject.getZqyxCustomerName() + "," + sysCustomer.getCustomerName());
                                    }
                                }
                            } else if ("债权成交客户".equals(sysItem.getCustomerLable())) {
                                if (StringUtils.isNull(sysProject.getZqcjCustomerId())) {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setZqcjCustomerId(sysItem.getCustomerId().toString());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setZqcjCustomerId(sysProject.getZqcjCustomerId() + "," + sysItem.getCustomerId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getZqcjCustomerName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setZqcjCustomerName(sysCustomer.getCustomerName());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setZqcjCustomerName(sysProject.getZqcjCustomerName() + "," + sysCustomer.getCustomerName());
                                    }
                                }
                            } else if ("物权意向客户".equals(sysItem.getCustomerLable())) {
                                if (StringUtils.isNull(sysProject.getWqyxCustomerId())) {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setWqyxCustomerId(sysItem.getCustomerId().toString());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setWqyxCustomerId(sysProject.getWqyxCustomerId() + "," + sysItem.getCustomerId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getWqyxCustomerName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setWqyxCustomerName(sysCustomer.getCustomerName());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setWqyxCustomerName(sysProject.getWqyxCustomerName() + "," + sysCustomer.getCustomerName());
                                    }
                                }
                            } else if ("物权成交客户".equals(sysItem.getCustomerLable())) {
                                if (StringUtils.isNull(sysProject.getWqcjCustomerId())) {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setWqcjCustomerId(sysItem.getCustomerId().toString());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setWqcjCustomerId(sysProject.getWqcjCustomerId() + "," + sysItem.getCustomerId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getWqcjCustomerName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setWqcjCustomerName(sysCustomer.getCustomerName());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setWqcjCustomerName(sysProject.getWqcjCustomerName() + "," + sysCustomer.getCustomerName());
                                    }
                                }
                            } else if ("其他".equals(sysItem.getCustomerLable())) {
                                if (StringUtils.isNull(sysProject.getOtherId())) {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setOtherId(sysItem.getCustomerId().toString());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setOtherId(sysProject.getOtherId() + "," + sysItem.getCustomerId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getOtherName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setOtherName(sysCustomer.getCustomerName());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                        sysProject.setOtherName(sysProject.getOtherName() + "," + sysCustomer.getCustomerName());
                                    }
                                }
                            }
                            sysProjectService.updateSysProject(sysProject);
                        }
                    }
                }
            }
        }*/
        sysItem.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysItemService.updateSysItem(sysItem));
    }

    /**
     * 删除项目选择
     */
    @RequiresPermissions("system:item:remove")
    @Log(title = "项目选择", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String string : ids.split(",")) {
            SysPcustomer sysPcustomer = new SysPcustomer();
            sysPcustomer.setItemId(Long.valueOf(string));
            List<SysPcustomer> sysPcustomerList = sysPcustomerService.selectSysPcustomerList(sysPcustomer);
            for (SysPcustomer sysPcustomer1 : sysPcustomerList) {
                stringBuffer.append(sysPcustomer1.getDealCustomerId()).append(",");
            }
        }
        sysPcustomerService.deleteSysPcustomerByIds(stringBuffer.toString());
        return toAjax(sysItemService.deleteSysItemByIds(ids));
    }

    @RequiresPermissions("system:item:list")
    @GetMapping("/itemList")
    public String itemList(String customerId, String pageNumber, String pageSize, ModelMap modelMap) {
        modelMap.put("customerId", customerId);
        modelMap.put("pageNumber", pageNumber);
        modelMap.put("pageSize", pageSize);
        return "system/item/item";
    }

    @PostMapping("/updates")
    @ResponseBody
    public void updates() {
        Map<Long, Long> pMap = new HashMap<>();
        SysItem sysItem = new SysItem();
        List<SysItem> sysItemList = sysItemService.selectSysItemList(sysItem);
        for (SysItem sysItem1 : sysItemList) {
            SysPcustomer sysPcustomer = new SysPcustomer();
            sysPcustomer.setItemId(sysItem1.getItemId());
            List<SysPcustomer> list = sysPcustomerService.selectSysPcustomerList(sysPcustomer);
            if (list.size() <= 0) {
                for (String string : sysItem1.getProjectId().split(",")) {
                    if (StringUtils.isNotNull(sysItem1.getItemId())) {
                        String name = sysCustomerService.selectSysCustomerById(sysItem1.getItemId()).getContacts();
                        if (StringUtils.isNotEmpty(string) && StringUtils.isNotEmpty(name)) {
                            SysPcustomer sysPcustomer1 = new SysPcustomer();
                            sysPcustomer1.setCustomerId(sysItem1.getCustomerId().toString());
                            sysPcustomer1.setCustomerName(name);
                            sysPcustomer1.setCustomerLable(sysItem1.getCustomerLable());
                            sysPcustomer1.setItemId(sysItem1.getItemId());
                            sysPcustomer1.setDeptType("thb");
                            sysPcustomer1.setProjectId(Long.valueOf(string));
                            sysPcustomer1.setCreateBy(sysItem1.getCreateBy());
                            sysPcustomer1.setShareUserId(sysItem1.getShareUserId());
                            sysPcustomer1.setShareUserName(sysItem1.getShareUserName());
                            sysPcustomerService.insertSysPcustomer(sysPcustomer1);
                        }
                    }

                }
            }
        }
    }
}
