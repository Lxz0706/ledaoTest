package com.ledao.activity.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ledao.activity.dao.SysWorkFlowVo;
import com.ledao.activity.service.IProcessService;
import com.ledao.common.utils.DateUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysUser;
import com.ledao.system.service.ISysUserService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ledao.common.annotation.Log;
import com.ledao.common.enums.BusinessType;
import com.ledao.activity.dao.SysWorkflow;
import com.ledao.activity.service.ISysWorkflowService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 档案管理出入库流程Controller
 *
 * @author lxz
 * @date 2021-05-26
 */
@Controller
@RequestMapping("/workflow")
public class SysWorkflowController extends BaseController {
    private String prefix = "workflow";

    @Autowired
    private ISysWorkflowService sysWorkflowService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IProcessService processService;

    @GetMapping()
    public String workflow(ModelMap modelMap) {
        modelMap.put("currentUser", ShiroUtils.getSysUser());
        return prefix + "/workflow";
    }

    @GetMapping("/warehouse")
    public String warehouse(ModelMap modelMap) {
        modelMap.put("currentUser", ShiroUtils.getSysUser());
        return prefix + "/warehouse";
    }

    @GetMapping("/delivery")
    public String delivery(ModelMap modelMap) {
        modelMap.put("currentUser", ShiroUtils.getSysUser());
        return prefix + "/delivery";
    }

    /**
     * 查询档案管理出入库流程列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysWorkFlowVo sysWorkFlowVo) {
        startPage();
        List<SysWorkFlowVo> list = sysWorkflowService.selectSysWorkflowList(sysWorkFlowVo);
        return getDataTable(list);
    }

    /**
     * 导出档案管理出入库流程列表
     */
    @Log(title = "档案管理出入库流程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysWorkFlowVo sysWorkFlowVo) {
        List<SysWorkFlowVo> list = sysWorkflowService.selectSysWorkflowList(sysWorkFlowVo);
        ExcelUtil<SysWorkFlowVo> util = new ExcelUtil<SysWorkFlowVo>(SysWorkFlowVo.class);
        return util.exportExcel(list, "workflow");
    }

    /**
     * 新增档案管理出入库流程
     */
    @GetMapping("/add/{workFlowType}")
    public String add(@PathVariable("workFlowType") String workFlowType, ModelMap modelMap) {
        modelMap.put("workFlowType", workFlowType);
        modelMap.put("createBy", ShiroUtils.getLoginName());
        modelMap.put("creator", ShiroUtils.getSysUser().getUserName());
        return prefix + "/add";
    }

    /**
     * 新增保存档案管理出入库流程
     */
    @Log(title = "档案管理出入库流程", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysWorkFlowVo sysWorkFlowVo) throws ParseException {
        SysUser sysUser = sysUserService.selectUserByLoginName(sysWorkFlowVo.getCreateBy());
        sysWorkFlowVo.setDeptId(sysUser.getDeptId());
        sysWorkFlowVo.setDeptName(sysUser.getDept().getDeptName());
        sysWorkFlowVo.setCreateUserName(sysWorkFlowVo.getCreator());
        sysWorkFlowVo.setReferee(ShiroUtils.getLoginName());


        //测试
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date four = null;
        Date now = DateUtils.parseDate(DateUtils.getDate());

        four = formatter2.parse(formatter.format(now) + " 06:01");

        return toAjax(sysWorkflowService.insertSysWorkflow(sysWorkFlowVo));
    }

    /**
     * 修改档案管理出入库流程
     */
    @GetMapping("/edit/{workFlowId}")
    public String edit(@PathVariable("workFlowId") Long workFlowId, ModelMap mmap) {
        SysWorkflow sysWorkflow = sysWorkflowService.selectSysWorkflowById(workFlowId);
        mmap.put("sysWorkflow", sysWorkflow);
        return prefix + "/edit";
    }

    /**
     * 修改保存档案管理出入库流程
     */
    @Log(title = "档案管理出入库流程", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysWorkFlowVo sysWorkFlowVo) {
        sysWorkFlowVo.setUpdateBy(ShiroUtils.getLoginName());
        sysWorkFlowVo.setReviser(ShiroUtils.getSysUser().getUserName());
        return toAjax(sysWorkflowService.updateSysWorkflow(sysWorkFlowVo));
    }

    /**
     * 删除档案管理出入库流程
     */
    @Log(title = "档案管理出入库流程", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysWorkflowService.deleteSysWorkflowByIds(ids));
    }

    @Log(title = "入库流程", businessType = BusinessType.UPDATE)
    @PostMapping("/submitApply")
    @ResponseBody
    public AjaxResult submitApply(Long id) {
        String key = "";
        SysWorkFlowVo sysWorkFlowVo = sysWorkflowService.selectSysWorkflowById(id);
        String applyUserId = sysWorkFlowVo.getCreateBy();
        sysWorkflowService.submitApply(sysWorkFlowVo, applyUserId, key, new HashMap<>());
        return success();
    }

    @GetMapping("/workflowTodo")
    public String todoView() {

        return prefix + "/workflowTodo";
    }

    /**
     * 我的待办列表
     *
     * @return
     */
    @PostMapping("/taskList")
    @ResponseBody
    public TableDataInfo taskList(SysWorkFlowVo sysWorkFlowVo) {
        //  sysWorkFlowVo.setWorkFlowType("document-rk");
        logger.info("流程类型：========" + sysWorkFlowVo.getWorkFlowType());
        List<SysWorkFlowVo> list = sysWorkflowService.findTodoTasks(sysWorkFlowVo, ShiroUtils.getLoginName());
        return getDataTable(list);
    }

    @GetMapping("/workflowDone")
    public String doneView() {
        return prefix + "/workflowDone";
    }

    /**
     * 我的已办列表
     *
     * @param sysWorkFlowVo
     * @return
     */
    @PostMapping("/taskDoneList")
    @ResponseBody
    public TableDataInfo taskDoneList(SysWorkFlowVo sysWorkFlowVo) {
        //bizLeave.setType("leave");
        List<SysWorkFlowVo> list = sysWorkflowService.findDoneTasks(sysWorkFlowVo, ShiroUtils.getLoginName());
        return getDataTable(list);
    }

    /**
     * 加载审批弹窗
     *
     * @param taskId
     * @param mmap
     * @return
     */
    @GetMapping("/showVerifyDialog/{taskId}")
    public String showVerifyDialog(@PathVariable("taskId") String taskId, ModelMap mmap) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        SysWorkFlowVo sysWorkFlowVo = sysWorkflowService.selectSysWorkflowById(new Long(processInstance.getBusinessKey()));
        mmap.put("sysWorkFlowVo", sysWorkFlowVo);
        mmap.put("taskId", taskId);
        String verifyName = task.getTaskDefinitionKey().substring(0, 1).toUpperCase() + task.getTaskDefinitionKey().substring(1);
        return prefix + "/task" + verifyName;
    }

    @GetMapping("/showFormDialog/{instanceId}")
    public String showFormDialog(@PathVariable("instanceId") String instanceId, ModelMap mmap) {
        String businessKey = processService.findBusinessKeyByInstanceId(instanceId);
        SysWorkFlowVo sysWorkFlowVo = sysWorkflowService.selectSysWorkflowById(new Long(businessKey));
        mmap.put("sysWorkFlowVo", sysWorkFlowVo);
        return prefix + "/view";
    }

    /**
     * 完成任务
     *
     * @return
     */
    @RequestMapping(value = "/complete/{taskId}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AjaxResult complete(@PathVariable("taskId") String taskId, @RequestParam(value = "saveEntity", required = false) String saveEntity,
                               @ModelAttribute("preloadWorkflow") SysWorkFlowVo sysWorkFlowVo, HttpServletRequest request) {
        boolean saveEntityBoolean = BooleanUtils.toBoolean(saveEntity);

        //获取对应的流程id
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String key = task.getProcessDefinitionId().substring(0, task.getProcessDefinitionId().indexOf(":"));
        processService.complete(taskId, sysWorkFlowVo.getInstanceId(), sysWorkFlowVo.getCreator() + "提交入库申请", sysWorkFlowVo.getRemarks(), key, new HashMap<String, Object>(), request);
        if (saveEntityBoolean) {
            sysWorkflowService.updateSysWorkflow(sysWorkFlowVo);
        }
        return success("任务已完成");
    }

    /**
     * 自动绑定页面字段
     */
    @ModelAttribute("preloadWorkflow")
    public SysWorkFlowVo getLeave(@RequestParam(value = "workFlowId", required = false) Long workFlowId, HttpSession session) {
        if (workFlowId != null) {
            SysWorkFlowVo sysWorkFlowVo = sysWorkflowService.selectSysWorkflowById(workFlowId);
            return sysWorkFlowVo;
        }
        return new SysWorkFlowVo();
    }
}
