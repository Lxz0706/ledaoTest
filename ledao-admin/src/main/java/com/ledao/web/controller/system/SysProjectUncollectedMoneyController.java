package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.domain.SysProjectysyf;
import com.ledao.system.service.ISysProjectysyfService;
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
import com.ledao.system.domain.SysProjectUncollectedMoney;
import com.ledao.system.service.ISysProjectUncollectedMoneyService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.domain.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 *
 * @author ledao
 * @date 2020-08-31
 */
@Controller
@RequestMapping("/system/money")
public class SysProjectUncollectedMoneyController extends BaseController {
    private String prefix = "system/money";

    @Autowired
    private ISysProjectUncollectedMoneyService sysProjectUncollectedMoneyService;

    @Autowired
    private ISysProjectysyfService sysProjectysyfService;

    @RequiresPermissions("system:money:view")
    @GetMapping()
    public String money() {
        return prefix + "/money";
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("system:money:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysProjectUncollectedMoney sysProjectUncollectedMoney) {
        startPage();
        List<SysProjectUncollectedMoney> list = sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyList(sysProjectUncollectedMoney);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("system:money:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysProjectUncollectedMoney sysProjectUncollectedMoney) {
        List<SysProjectUncollectedMoney> list = sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyList(sysProjectUncollectedMoney);
        ExcelUtil<SysProjectUncollectedMoney> util = new ExcelUtil<SysProjectUncollectedMoney>(SysProjectUncollectedMoney.class);
        return util.exportExcel(list, "money");
    }

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") String id, ModelMap modelMap) {
        modelMap.put("projectManagementId", id);
        return prefix + "/add";
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @RequiresPermissions("system:money:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysProjectUncollectedMoney sysProjectUncollectedMoney) {
        SysProjectysyf sysProjectysyf = new SysProjectysyf();
        SysProjectUncollectedMoney sysProjectUncollectedMoney1 = new SysProjectUncollectedMoney();
        if (StringUtils.isNotEmpty(sysProjectUncollectedMoney.getFundType())) {
            if (StringUtils.isNotEmpty(sysProjectUncollectedMoney.getState())) {
                if ("是".equals(sysProjectUncollectedMoney.getState())) {
                    if ("应（未）付金额".equals(sysProjectUncollectedMoney.getState())) {
                        sysProjectysyf.setFundType("已付金额");
                        sysProjectysyf.setPaidDate(sysProjectUncollectedMoney.getTime());
                        sysProjectysyf.setAmountPaid(sysProjectUncollectedMoney.getAmountMoney());
                        sysProjectysyf.setCreateBy(ShiroUtils.getLoginName());
                        sysProjectysyfService.insertSysProjectysyf(sysProjectysyf);
                    } else if ("应（未）收金额".equals(sysProjectUncollectedMoney.getState())) {
                        sysProjectysyf.setFundType("已收金额");
                        sysProjectysyf.setPaidDate(sysProjectUncollectedMoney.getTime());
                        sysProjectysyf.setAmountPaid(sysProjectUncollectedMoney.getAmountMoney());
                        sysProjectysyf.setCreateBy(ShiroUtils.getLoginName());
                        sysProjectysyfService.insertSysProjectysyf(sysProjectysyf);
                    } else if ("未收服务费金额".equals(sysProjectUncollectedMoney.getState())) {
                        sysProjectUncollectedMoney1.setFundType("已收服务费金额");
                        sysProjectUncollectedMoney1.setAmountMoney(sysProjectUncollectedMoney.getAmountMoney());
                        sysProjectUncollectedMoney1.setTime(sysProjectUncollectedMoney.getTime());
                        sysProjectUncollectedMoney1.setState("是");
                        sysProjectUncollectedMoney1.setCreateBy(ShiroUtils.getLoginName());
                        sysProjectUncollectedMoneyService.insertSysProjectUncollectedMoney(sysProjectUncollectedMoney1);
                    }
                }
            }
        }
        sysProjectUncollectedMoney.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysProjectUncollectedMoneyService.insertSysProjectUncollectedMoney(sysProjectUncollectedMoney));
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        SysProjectUncollectedMoney sysProjectUncollectedMoney = sysProjectUncollectedMoneyService.selectSysProjectUncollectedMoneyById(id);
        mmap.put("sysProjectUncollectedMoney", sysProjectUncollectedMoney);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @RequiresPermissions("system:money:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysProjectUncollectedMoney sysProjectUncollectedMoney) {
        SysProjectysyf sysProjectysyf = new SysProjectysyf();
        SysProjectUncollectedMoney sysProjectUncollectedMoney1 = new SysProjectUncollectedMoney();
        logger.info("sysProjectUncollectedMoney.getFundType():==============="+sysProjectUncollectedMoney.getFundType());
        if (StringUtils.isNotEmpty(sysProjectUncollectedMoney.getFundType())) {
            logger.info("sysProjectUncollectedMoney.getFundType():----------------------"+sysProjectUncollectedMoney.getFundType());
            if (StringUtils.isNotEmpty(sysProjectUncollectedMoney.getState())) {
                logger.info("sysProjectUncollectedMoney.getState():==============="+sysProjectUncollectedMoney.getState());
                if ("是".equals(sysProjectUncollectedMoney.getState())) {
                    if ("应（未）付金额".equals(sysProjectUncollectedMoney.getFundType())) {
                        logger.info("捡来了！！！！！！！！！！");
                        sysProjectysyf.setFundType("已付金额");
                        sysProjectysyf.setProjectManagementId(sysProjectUncollectedMoney.getProjectManagementId());
                        sysProjectysyf.setPaidDate(sysProjectUncollectedMoney.getTime());
                        sysProjectysyf.setAmountPaid(sysProjectUncollectedMoney.getAmountMoney());
                        sysProjectysyf.setCreateBy(ShiroUtils.getLoginName());
                        sysProjectysyfService.insertSysProjectysyf(sysProjectysyf);
                    } else if ("应（未）收金额".equals(sysProjectUncollectedMoney.getFundType())) {
                        sysProjectysyf.setFundType("已收金额");
                        sysProjectysyf.setProjectManagementId(sysProjectUncollectedMoney.getProjectManagementId());
                        sysProjectysyf.setPaidDate(sysProjectUncollectedMoney.getTime());
                        sysProjectysyf.setAmountPaid(sysProjectUncollectedMoney.getAmountMoney());
                        sysProjectysyf.setCreateBy(ShiroUtils.getLoginName());
                        sysProjectysyfService.insertSysProjectysyf(sysProjectysyf);
                    } else if ("未收服务费金额".equals(sysProjectUncollectedMoney.getFundType())) {
                        sysProjectUncollectedMoney1.setFundType("已收服务费金额");
                        sysProjectUncollectedMoney1.setProjectManagementId(sysProjectUncollectedMoney.getProjectManagementId());
                        sysProjectUncollectedMoney1.setAmountMoney(sysProjectUncollectedMoney.getAmountMoney());
                        sysProjectUncollectedMoney1.setTime(sysProjectUncollectedMoney.getTime());
                        sysProjectUncollectedMoney1.setState("是");
                        sysProjectUncollectedMoney1.setCreateBy(ShiroUtils.getLoginName());
                        sysProjectUncollectedMoneyService.insertSysProjectUncollectedMoney(sysProjectUncollectedMoney1);
                    }
                }
            }
        }
        sysProjectUncollectedMoney.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(sysProjectUncollectedMoneyService.updateSysProjectUncollectedMoney(sysProjectUncollectedMoney));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("system:money:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysProjectUncollectedMoneyService.deleteSysProjectUncollectedMoneyByIds(ids));
    }

    @RequiresPermissions("system:projectmanagent:list")
    @GetMapping("/moneyList/{projectManagementId}")
    public String selectProjectProgressByProjectId(@PathVariable("projectManagementId") String projectManagementId, ModelMap modelMap) {
        modelMap.put("projectManagementId", projectManagementId);
        return "system/money/money";
    }
}
