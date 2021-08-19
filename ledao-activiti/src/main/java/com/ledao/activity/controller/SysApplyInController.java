package com.ledao.activity.controller;

import java.util.Date;
import java.util.List;

import com.ledao.activity.dao.*;
import com.ledao.activity.service.*;
import com.ledao.common.utils.StringUtils;
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
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysUser;

/**
 * 档案入库申请Controller
 * 
 * @author lxz
 * @date 2021-08-04
 */
@Controller
@RequestMapping("/applyIn")
public class SysApplyInController extends BaseController
{
    private String prefix = "applyIn";

    @Autowired
    private ISysApplyInService sysApplyInService;

    
    @Autowired
    private ISysApplyWorkflowService sysApplyWorkflowService;

    @Autowired
    private ISysUserService ISysUserService;
    
    @Autowired
    private ISysDocumentFileService sysDocumentFileService;
    
    @Autowired
    private ISysFileDetailService sysFileDetailService;

    @Autowired
    private ISysApplyOutDetailService sysApplyOutDetailService;

//    @RequiresPermissions("applyIn:view")
    @GetMapping("/applyIn")
    public String applyIn()
    {
        return prefix + "/applyIn";
    }
    
    @GetMapping("/applyOut")
    public String applyOut(ModelMap modelMap) {
        return prefix + "/applyOut";
    }
    @GetMapping("/applyList")
    public String applyList(ModelMap modelMap) {
//        我的已办
        return prefix + "/applyList";
    }
    @GetMapping("/applyUnDoneList")
    public String applyUnDoneList(ModelMap modelMap) {
//        我的待办
        return prefix + "/applyUnDoneList";
    }

    @GetMapping("/applyListByMe")
    public String applyListByMe(ModelMap modelMap) {
//        我的申请
        return prefix + "/applyListByMe";
    }

    @GetMapping("/reject/{applyId}")
    public String reject(@PathVariable("applyId") String applyId,ModelMap modelMap) {
//        我的添加审批拒绝备注
        modelMap.put("applyId",applyId);
        return prefix + "/reject";
    }

    @GetMapping("/applyProcess/historyList/{applyId}")
    public String historyProcess(@PathVariable("applyId") String applyId,ModelMap modelMap) {
//        查看审批历史
        return "applyProcess/historyList";
    }

    /**
     * 查询档案入库申请列表
     */
//    @RequiresPermissions("applyIn:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysApplyIn sysApplyIn)
    {
        startPage();
        sysApplyIn.setApplyUser(ShiroUtils.getLoginName());
        List<SysApplyIn> list = sysApplyInService.selectSysApplyInList(sysApplyIn);
        return getDataTable(list);
    }

    /**
     * 我的已办
     * @param sysApplyIn
     * @return
     */
    @PostMapping("/listByMe")
    @ResponseBody
    public TableDataInfo listDownByMe(SysApplyIn sysApplyIn)
    {
//        sysApplyIn = new SysApplyIn();
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        sysApplyIn.setApplyUser(user.getLoginName());
        List<SysApplyIn> list = sysApplyInService.listDownByMe(sysApplyIn);
        return getDataTable(list);
    }

    /**
     * 我的申请
     * @param
     * @return
     */
    @PostMapping("/applyListByMe")
    @ResponseBody
    public TableDataInfo applyListByMe(SysApplyIn sysApplyIn)
    {
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        sysApplyIn.setApplyUser(user.getLoginName());
        List<SysApplyIn> list = sysApplyInService.selectSysApplyInList(sysApplyIn);
        return getDataTable(list);
    }

    /**
     * 我的待办
     * @param sysApplyIn
     * @return
     */
    @PostMapping("/listUnDownByMe")
    @ResponseBody
    public TableDataInfo listUnDownByMe(SysApplyIn sysApplyIn)
    {
//        sysApplyIn = new SysApplyIn();
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        sysApplyIn.setApplyUser(user.getLoginName());
        List<SysApplyIn> list = sysApplyInService.listUnDownByMe(sysApplyIn);
        return getDataTable(list);
    }

    /**
     * 导出档案入库申请列表
     */
//    @RequiresPermissions("applyIn:export")
    @Log(title = "档案入库申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysApplyIn sysApplyIn)
    {
        List<SysApplyIn> list = sysApplyInService.selectSysApplyInList(sysApplyIn);
        ExcelUtil<SysApplyIn> util = new ExcelUtil<SysApplyIn>(SysApplyIn.class);
        return util.exportExcel(list, "applyIn");
    }

    /**
     * 新增档案入库申请
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增档案入库申请
     */
    @GetMapping("/editOutList/{applyId}")
    public String editOutList(@PathVariable("applyId") Long applyId,ModelMap mmap)
    {
        mmap.put("applyId",applyId);
        return prefix + "/editOutList";
    }
    @GetMapping("/editOutUpdate/{outDetailId}")
    public String editOutUpdate(@PathVariable("outDetailId") Long outDetailId,ModelMap mmap)
    {
        SysApplyOutDetail sysApplyOutDetail = sysApplyOutDetailService.selectSysApplyOutDetailById(outDetailId);
        mmap.put("outDetailId",outDetailId);
        mmap.put("sysApplyOutDetail",sysApplyOutDetail);
        return prefix + "/editOutUpdate";
    }

    /**
     * 新增档案出库申请
     */
    @GetMapping("/addOut")
    public String addOut()
    {
        return prefix + "/addOut";
    }


    /**
     * 新增档案入库申请
     */
    @GetMapping("/editDocumentModal/{applyId}/{documentTypeVal}")
    public String editDocumentModal(@PathVariable("applyId") Long applyId, @PathVariable("documentTypeVal") String documentTypeVal,ModelMap mmap)
    {
        mmap.put("applyId",applyId);
        mmap.put("documentTypeVal",documentTypeVal);
        return prefix + "/editDocumentModal";
    }

    /**
     * 新增保存档案入库申请
     */
//    @RequiresPermissions("applyIn:add")
    @Log(title = "档案入库申请(保存0)", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysApplyIn sysApplyIn)
    {
        String userName = ShiroUtils.getLoginName();
        sysApplyIn.setApplyUser(userName);
        sysApplyIn.setApplyTime(new Date());
        sysApplyIn.setCreateBy(userName);
        sysApplyIn.setCreateTime(new Date());
        sysApplyIn.setApproveStatu("0");
        //            判断当有实际提交人时，获取实际提交人的直属领导
        if (StringUtils.isNotEmpty(sysApplyIn.getRealCreateBy())){
            SysUser realUser = ISysUserService.selectUserByLoginName(sysApplyIn.getRealCreateBy());
            if (realUser!=null && StringUtils.isNotEmpty(realUser.getLoginName())){}
            else{
                return AjaxResult.error("实际提交人不存在，请重新输入");
            }
        }
        return toAjax(sysApplyInService.insertSysApplyIn(sysApplyIn));
    }
    
    
    /**
     * 新增保存档案入库申请
     */
//    @RequiresPermissions("applyIn:addSysApplyIn")
    @Log(title = "档案入库申请发起", businessType = BusinessType.INSERT)
    @PostMapping("/addSysApplyIn")
    @ResponseBody
    public AjaxResult addSysApplyIn(SysApplyIn sysApplyIn)
    {
        sysApplyIn.setApproveStatu("0");
    	sysApplyInService.insertSysApplyIn(sysApplyIn);
        return toAjax(true);
    }

    /**
     * 修改档案入库申请
     */
    @GetMapping("/edit/{applyId}")
    public String edit(@PathVariable("applyId") Long applyId, ModelMap mmap)
    {
        SysApplyIn sysApplyIn = sysApplyInService.selectSysApplyInById(applyId);
        mmap.put("sysApplyIn", sysApplyIn);
        return prefix + "/edit";
    }

    /**
     * 修改档案入库申请
     */
    @GetMapping("/editOut/{applyId}")
    public String editOut(@PathVariable("applyId") Long applyId, ModelMap mmap)
    {
        SysApplyIn sysApplyIn = sysApplyInService.selectSysApplyInById(applyId);
        mmap.put("sysApplyIn", sysApplyIn);
        return prefix + "/editOut";
    }

    /**
     * 修改档案入库申请
     */
    @GetMapping("/applyBack/{applyId}")
    public String applyBack(@PathVariable("applyId") Long applyId, ModelMap mmap)
    {
        SysApplyIn sysApplyIn = sysApplyInService.selectSysApplyInById(applyId);
        mmap.put("sysApplyIn", sysApplyIn);
        return prefix + "/edit";
    }

    /**
     * 修改保存档案入库申请
     */
//    @RequiresPermissions("applyIn:edit")
    @Log(title = "档案入库申请", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysApplyIn sysApplyIn)
    {
        return toAjax(sysApplyInService.editSave(sysApplyIn));
    }

    /**
     * 档案状态修改，保存，提交审批，审批通过，审批拒绝
     */
    @Log(title = "档案状态修改", businessType = BusinessType.UPDATE)
    @PostMapping("/applyEditSave")
    @ResponseBody
    public AjaxResult applyEditSave(SysApplyIn sysApplyIn)
    {
        AjaxResult res = sysApplyInService.applyEditSave(sysApplyIn);
        return res;
    }
    
    @PostMapping("/selectSysApplyWorkflowList")
    @ResponseBody
    public TableDataInfo selectSysApplyWorkflowList(SysApplyIn sysApplyIn)
    {
        List<SysApplyWorkflow> list = sysApplyWorkflowService.selectSysApplyWorkflowList(sysApplyIn);
        return getDataTable(list);
    }

    /**
     * 删除档案入库申请
     */
//    @RequiresPermissions("applyIn:remove")
    @Log(title = "档案入库申请", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        AjaxResult res = sysApplyInService.deleteSysApplyInByIds(ids);
        return res;
    }
}
