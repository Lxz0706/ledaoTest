package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
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
                        sysItem.setCreateBy(ShiroUtils.getLoginName());
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
        modelMap.put("role", role);
        modelMap.put("customerId", customerId);
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
        SysUser currentUser = ShiroUtils.getSysUser();
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.isAdmin()) {
                List<SysRole> getRoles = currentUser.getRoles();
                if (StringUtils.isNotNull(sysItem.getProjectName()) && StringUtils.isNotNull(sysItem.getProjectId())) {
                    for (SysRole sysRole : getRoles) {
                        if ("investmentManager2".equals(sysRole.getRoleKey()) || "investmentManager".equals(sysRole.getRoleKey())) {
                            SysZck sysZck = sysZckService.selectSysZckById(sysItem.getProjectId());
                            if (StringUtils.isNotNull(sysZck)) {
                                if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                    sysZck.setCustomerId(sysItem.getCustomerId());
                                }
                                if (StringUtils.isNotNull(sysCustomer.getCustomerName())) {
                                    sysZck.setCustomer(sysCustomer.getCustomerName());
                                }
                            }
                            sysZckService.updateSysZck(sysZck);
                        } else if ("bgczCommon".equals(sysRole.getRoleKey()) || "bgczManager".equals(sysRole.getRoleKey())) {
                            SysBgczzck sysBgczzck = sysBgczzckService.selectSysBgczzckById(sysItem.getProjectId());
                            if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                sysBgczzck.setCustomerId(sysItem.getCustomerId());
                            }
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
                                        sysProject.setAssetSupplierId(sysItem.getCustomerId() + "," + sysProject.getAssetSupplierId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getAssetSupplierName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setAssetSupplierName(sysCustomer.getContacts());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setAssetSupplierName(sysCustomer.getContacts() + "," + sysProject.getAssetSupplierName());
                                    }
                                }
                            } else if ("资金供应方".equals(sysItem.getCustomerLable())) {
                                if (StringUtils.isNull(sysProject.getFundingProviderId())) {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setFundingProviderId(sysItem.getCustomerId().toString());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setFundingProviderId(sysItem.getCustomerId() + "," + sysProject.getFundingProviderId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getFundingProviderName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setFundingProviderName(sysCustomer.getContacts());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setFundingProviderName(sysCustomer.getContacts() + "," + sysProject.getFundingProviderName());
                                    }
                                }
                            } else if ("律师".equals(sysItem.getCustomerLable())) {
                                if (StringUtils.isNull(sysProject.getLawyerId())) {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setLawyerId(sysItem.getCustomerId().toString());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setLawyerId(sysItem.getCustomerId() + "," + sysProject.getLawyerId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getLawyerName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setLawyerName(sysCustomer.getContacts());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setLawyerName(sysCustomer.getContacts() + "," + sysProject.getLawyerName());
                                    }
                                }
                            } else if ("中介方".equals(sysItem.getCustomerLable())) {
                                if (StringUtils.isNull(sysProject.getIntermediaryId())) {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setIntermediaryId(sysItem.getCustomerId().toString());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setIntermediaryId(sysItem.getCustomerId() + "," + sysProject.getIntermediaryId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getIntermediaryName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setIntermediaryName(sysCustomer.getContacts());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setIntermediaryName(sysCustomer.getContacts() + "," + sysProject.getIntermediaryName());
                                    }
                                }
                            } else if ("债权意向客户".equals(sysItem.getCustomerLable())) {
                                if (StringUtils.isNull(sysProject.getZqyxCustomerId())) {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setZqyxCustomerId(sysItem.getCustomerId().toString());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setZqyxCustomerId(sysItem.getCustomerId() + "," + sysProject.getZqyxCustomerId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getZqyxCustomerName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setZqyxCustomerName(sysCustomer.getContacts());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setZqyxCustomerName(sysCustomer.getContacts() + "," + sysProject.getZqyxCustomerName());
                                    }
                                }
                            } else if ("债权成交客户".equals(sysItem.getCustomerLable())) {
                                if (StringUtils.isNull(sysProject.getZqcjCustomerId())) {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setZqcjCustomerId(sysItem.getCustomerId().toString());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setZqcjCustomerId(sysItem.getCustomerId() + "," + sysProject.getZqcjCustomerId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getZqcjCustomerName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setZqcjCustomerName(sysCustomer.getContacts());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setZqcjCustomerName(sysCustomer.getContacts() + "," + sysProject.getZqcjCustomerName());
                                    }
                                }
                            } else if ("物权意向客户".equals(sysItem.getCustomerLable())) {
                                if (StringUtils.isNull(sysProject.getWqyxCustomerId())) {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setWqyxCustomerId(sysItem.getCustomerId().toString());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setWqyxCustomerId(sysItem.getCustomerId() + "," + sysProject.getWqyxCustomerId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getWqyxCustomerName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setWqyxCustomerName(sysCustomer.getContacts());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setWqyxCustomerName(sysCustomer.getContacts() + "," + sysProject.getWqyxCustomerName());
                                    }
                                }
                            } else if ("物权成交客户".equals(sysItem.getCustomerLable())) {
                                if (StringUtils.isNull(sysProject.getWqcjCustomerId())) {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setWqcjCustomerId(sysItem.getCustomerId().toString());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setWqcjCustomerId(sysItem.getCustomerId() + "," + sysProject.getWqcjCustomerId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getWqcjCustomerName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setWqcjCustomerName(sysCustomer.getContacts());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setWqcjCustomerName(sysCustomer.getContacts() + "," + sysProject.getWqcjCustomerName());
                                    }
                                }
                            } else if ("其他".equals(sysItem.getCustomerLable())) {
                                if (StringUtils.isNull(sysProject.getOtherId())) {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setOtherId(sysItem.getCustomerId().toString());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysItem.getCustomerId())) {
                                        sysProject.setOtherId(sysItem.getCustomerId() + "," + sysProject.getOtherId());
                                    }
                                }
                                if (StringUtils.isNull(sysProject.getOtherName())) {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setOtherName(sysCustomer.getContacts());
                                    }
                                } else {
                                    if (StringUtils.isNotNull(sysCustomer.getContacts())) {
                                        sysProject.setOtherName(sysCustomer.getContacts() + "," + sysProject.getOtherName());
                                    }
                                }
                            }
                            sysProjectService.updateSysProject(sysProject);
                        }
                    }
                }
            }
        }
        sysItem.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysItemService.insertSysItem(sysItem));
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
        int row = 0;
        for (String string1 : ids.split(",")) {
            SysItem sysItem = sysItemService.selectSysItemById(Long.valueOf(string1));
            row = sysItemService.deleteSysItemById(Long.valueOf(string1));
            SysItem sysItem1 = new SysItem();
            sysItem1.setCustomerLable(sysItem.getCustomerLable());
            sysItem1.setProjectId(sysItem.getProjectId());
            List<SysItem> sysItemList = sysItemService.selectSysItemList(sysItem1);
            SysProject sysProject = sysProjectService.selectSysProjectById(sysItem1.getProjectId());
            if (sysItemList.size() > 0) {
                for (SysItem sysItem2 : sysItemList) {
                    StringBuffer sb = new StringBuffer();
                    StringBuffer sb1 = new StringBuffer();
                    SysCustomer sysCustomer = sysCustomerService.selectSysCustomerById(sysItem2.getCustomerId());
                    sb.append(sysItem2.getCustomerId()).append(",");
                    sb1.append(sysCustomer.getContacts()).append(",");
                    if ("资产供应方".equals(sysItem2.getCustomerLable())) {
                        sysProject.setAssetSupplierId(sb.toString());
                        sysProject.setAssetSupplierName(sb1.toString());
                    } else if ("资金供应方".equals(sysItem2.getCustomerLable())) {
                        sysProject.setFundingProviderId(sb.toString());
                        sysProject.setFundingProviderName(sb1.toString());
                    } else if ("律师".equals(sysItem2.getCustomerLable())) {
                        sysProject.setLawyerId(sb.toString());
                        sysProject.setLawyerName(sb1.toString());
                    } else if ("中介方".equals(sysItem2.getCustomerLable())) {
                        sysProject.setIntermediaryId(sb.toString());
                        sysProject.setIntermediaryName(sb1.toString());
                    } else if ("债权意向客户".equals(sysItem2.getCustomerLable())) {
                        sysProject.setZqyxCustomerId(sb.toString());
                        sysProject.setZqyxCustomerName(sb1.toString());
                    } else if ("债权成交客户".equals(sysItem2.getCustomerLable())) {
                        sysProject.setZqcjCustomerId(sb.toString());
                        sysProject.setZqcjCustomerName(sb1.toString());
                    } else if ("物权意向客户".equals(sysItem2.getCustomerLable())) {
                        sysProject.setWqyxCustomerId(sb.toString());
                        sysProject.setWqyxCustomerName(sb1.toString());
                    } else if ("物权成交客户".equals(sysItem2.getCustomerLable())) {
                        sysProject.setWqcjCustomerId(sb.toString());
                        sysProject.setWqcjCustomerName(sb1.toString());
                    } else if ("其他".equals(sysItem2.getCustomerLable())) {
                        sysProject.setOtherId(sb.toString());
                        sysProject.setOtherName(sb1.toString());
                    }
                    sysProjectService.updateSysProject(sysProject);
                }
            }else{
                if ("资产供应方".equals(sysItem1.getCustomerLable())) {
                    sysProject.setAssetSupplierId("");
                    sysProject.setAssetSupplierName("");
                } else if ("资金供应方".equals(sysItem.getCustomerLable())) {
                    sysProject.setFundingProviderId("");
                    sysProject.setFundingProviderName("");
                } else if ("律师".equals(sysItem.getCustomerLable())) {
                    sysProject.setLawyerId("");
                    sysProject.setLawyerName("");
                } else if ("中介方".equals(sysItem.getCustomerLable())) {
                    sysProject.setIntermediaryId("");
                    sysProject.setIntermediaryName("");
                } else if ("债权意向客户".equals(sysItem.getCustomerLable())) {
                    sysProject.setZqyxCustomerId("");
                    sysProject.setZqyxCustomerName("");
                } else if ("债权成交客户".equals(sysItem.getCustomerLable())) {
                    sysProject.setZqcjCustomerId("");
                    sysProject.setZqcjCustomerName("");
                } else if ("物权意向客户".equals(sysItem.getCustomerLable())) {
                    sysProject.setWqyxCustomerId("");
                    sysProject.setWqyxCustomerName("");
                } else if ("物权成交客户".equals(sysItem.getCustomerLable())) {
                    sysProject.setWqcjCustomerId("");
                    sysProject.setWqcjCustomerName("");
                } else if ("其他".equals(sysItem.getCustomerLable())) {
                    sysProject.setOtherId("");
                    sysProject.setOtherName("");
                }
                sysProjectService.updateSysProject(sysProject);
            }

        }
        return toAjax(row);
    }

    @RequiresPermissions("system:item:list")
    @GetMapping("/itemList/{customerId}")
    public String itemList(@PathVariable("customerId") String customerId, ModelMap modelMap) {
        modelMap.put("customerId", customerId);
        return "system/item/item";
    }
}
