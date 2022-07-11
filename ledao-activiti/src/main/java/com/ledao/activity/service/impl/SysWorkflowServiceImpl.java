package com.ledao.activity.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.ledao.activity.dao.SysWorkFlowVo;
import com.ledao.activity.service.IProcessService;
import com.ledao.common.core.page.PageDao;
import com.ledao.common.core.page.TableSupport;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.core.dao.entity.SysUser;
import com.ledao.system.mapper.SysUserMapper;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.activity.mapper.SysWorkflowMapper;
import com.ledao.activity.service.ISysWorkflowService;
import com.ledao.common.core.text.Convert;
import org.springframework.util.CollectionUtils;

/**
 * 档案管理出入库流程Service业务层处理
 *
 * @author lxz
 * @date 2021-05-26
 */
@Service
public class SysWorkflowServiceImpl implements ISysWorkflowService {
    @Autowired
    private SysWorkflowMapper sysWorkflowMapper;


    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private IProcessService processService;

    /**
     * 查询档案管理出入库流程
     *
     * @param workFlowId 档案管理出入库流程ID
     * @return 档案管理出入库流程
     */
    @Override
    public SysWorkFlowVo selectSysWorkflowById(Long workFlowId) {
        SysWorkFlowVo sysWorkFlowVo = sysWorkflowMapper.selectSysWorkflowById(workFlowId);
        SysUser sysUser = userMapper.selectUserByLoginName(sysWorkFlowVo.getApplyUser());
        if (sysUser != null) {
            sysWorkFlowVo.setApplyUserName(sysUser.getUserName());
        }
        return sysWorkFlowVo;
    }

    /**
     * 查询档案管理出入库流程列表
     *
     * @param sysWorkFlowVo 档案管理出入库流程
     * @return 档案管理出入库流程
     */
    @Override
    public List<SysWorkFlowVo> selectSysWorkflowList(SysWorkFlowVo sysWorkFlowVo) {
        PageDao pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();

        // PageHelper 仅对第一个 List 分页
        Page<SysWorkFlowVo> list = (Page<SysWorkFlowVo>) sysWorkflowMapper.selectSysWorkflowList(sysWorkFlowVo);
        Page<SysWorkFlowVo> returnList = new Page<>();
        for (SysWorkFlowVo leave : list) {
            SysUser sysUser = userMapper.selectUserByLoginName(leave.getCreateBy());
            if (sysUser != null) {
                leave.setCreateUserName(sysUser.getUserName());
            }
            SysUser sysUser2 = userMapper.selectUserByLoginName(leave.getApplyUser());
            if (sysUser2 != null) {
                leave.setApplyUserName(sysUser2.getUserName());
            }
            // 当前环节
            if (StringUtils.isNotBlank(leave.getInstanceId())) {
                List<Task> taskList = taskService.createTaskQuery()
                        .processInstanceId(leave.getInstanceId())
//                        .singleResult();
                        .list();    // 例如请假会签，会同时拥有多个任务
                if (!CollectionUtils.isEmpty(taskList)) {
                    TaskEntityImpl task = (TaskEntityImpl) taskList.get(0);
                    leave.setTaskId(task.getId());
                    if (task.getSuspensionState() == 2) {
                        leave.setTaskName("已挂起");
                        leave.setSuspendState("2");
                    } else {
                        leave.setTaskName(task.getName());
                        leave.setSuspendState("1");
                    }
                } else {
                    // 已办结或者已撤销
                    leave.setTaskName("已结束");
                }
            } else {
                leave.setTaskName("未启动");
            }
            returnList.add(leave);
        }
        returnList.setTotal(CollectionUtils.isEmpty(list) ? 0 : list.getTotal());
        returnList.setPageNum(pageNum);
        returnList.setPageSize(pageSize);
        return returnList;
    }

    /**
     * 新增档案管理出入库流程
     *
     * @param sysWorkFlowVo 档案管理出入库流程
     * @return 结果
     */
    @Override
    public int insertSysWorkflow(SysWorkFlowVo sysWorkFlowVo) {
        sysWorkFlowVo.setCreateTime(DateUtils.getNowDate());
        return sysWorkflowMapper.insertSysWorkflow(sysWorkFlowVo);
    }

    /**
     * 修改档案管理出入库流程
     *
     * @param sysWorkFlowVo 档案管理出入库流程
     * @return 结果
     */
    @Override
    public int updateSysWorkflow(SysWorkFlowVo sysWorkFlowVo) {
        sysWorkFlowVo.setUpdateTime(DateUtils.getNowDate());
        return sysWorkflowMapper.updateSysWorkflow(sysWorkFlowVo);
    }

    /**
     * 删除档案管理出入库流程对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysWorkflowByIds(String ids) {
        return sysWorkflowMapper.deleteSysWorkflowByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除档案管理出入库流程信息
     *
     * @param workFlowId 档案管理出入库流程ID
     * @return 结果
     */
    @Override
    public int deleteSysWorkflowById(Long workFlowId) {
        return sysWorkflowMapper.deleteSysWorkflowById(workFlowId);
    }


    /**
     * 启动流程
     *
     * @param entity
     * @param applyUserId
     * @return
     */
    @Override
    public ProcessInstance submitApply(SysWorkFlowVo entity, String applyUserId, String key, Map<String, Object> variables) {
        entity.setApplyUser(applyUserId);
        entity.setApplyTime(DateUtils.getNowDate());
        entity.setUpdateBy(applyUserId);
        sysWorkflowMapper.updateSysWorkflow(entity);
        // 实体类 ID，作为流程的业务 key
        String businessKey = entity.getWorkFlowId().toString();
        SysUser sysUser = userMapper.selectUserByLoginName(applyUserId);
        String itemName = "";
        if (StringUtils.isNotNull(sysUser)) {
            if ("warehouse".equals(entity.getWorkFlowType())) {
                //根据提交人查询是否存在直接主管
                if (StringUtils.isNotEmpty(sysUser.getDirector()) && StringUtils.isNotEmpty(sysUser.getDirectorId().toString())) {
                    key = "document_rk_zg";
                    SysUser sysUser1 = userMapper.selectUserById(sysUser.getDirectorId());
                    //动态设置审批人员
                    variables.put("userId", sysUser1.getLoginName());
                } else {
                    key = "document_rk_zgNo";
                }
                itemName = entity.getCreator() + "提交的入库申请";
            } else {
                itemName = entity.getCreator() + "提交的出库申请";

                List<String> userList = new ArrayList<>();
                userList.add("yangxu");
                userList.add("yangxudong");
                variables.put("assigneeList", userList);

                //判断是否存在直接主管
                if (StringUtils.isNotEmpty(sysUser.getDirector()) && StringUtils.isNotEmpty(sysUser.getDirectorId().toString())) {
                    SysUser sysUser1 = userMapper.selectUserById(sysUser.getDirectorId());
                    //判断是否存在二级主管
                    if (StringUtils.isNotEmpty(sysUser1.getDirector()) && StringUtils.isNotEmpty(sysUser1.getDirectorId().toString())) {
                        //判断是否存在三级主管
                        SysUser sysUser2 = userMapper.selectUserById(sysUser1.getDirectorId());
                        if (StringUtils.isNotEmpty(sysUser2.getDirectorId().toString()) && StringUtils.isNotEmpty(sysUser2.getDirector())) {
                            SysUser sysUser3 = userMapper.selectUserById(sysUser2.getDirectorId());
                            variables.put("zgUser", sysUser1.getLoginName());
                            variables.put("ejzgUser", sysUser2.getLoginName());
                            variables.put("sjzgUser", sysUser3.getLoginName());
                            if ("否".equals(entity.getDocumentRevertFlag())) {
                                key = "document_ck_sjzgNo";
                            } else {
                                key = "document_ck_sjzg";
                            }
                        } else {

                            variables.put("zgUser", sysUser.getLoginName());
                            variables.put("ejzgUser", sysUser1.getLoginName());

                            if ("否".equals(entity.getDocumentRevertFlag())) {
                                key = "document_ck_ejzgNo";
                            } else {
                                key = "document_ck_ejzg";
                            }
                        }
                    } else {
                        variables.put("zgUser", sysUser1.getLoginName());
                        if ("N".equals(entity.getDocumentRevertFlag())) {
                            key = "document_zjzgNo";
                        } else {
                            key = "document_zjzg";
                        }
                    }
                } else {
                    if ("否".equals(entity.getDocumentRevertFlag())) {
                        key = "document_ghNo";
                    } else {
                        key = "document_gh";
                    }
                }
            }
        }

        ProcessInstance processInstance = processService.submitApply(applyUserId, businessKey, itemName, entity.getRemarks(), key, variables);

        String processInstanceId = processInstance.getId();
        entity.setInstanceId(processInstanceId); // 建立双向关系
        sysWorkflowMapper.updateSysWorkflow(entity);

        return processInstance;
    }

    /**
     * 查询待办任务
     */
    @Override
    public Page<SysWorkFlowVo> findTodoTasks(SysWorkFlowVo sysWorkFlowVo, String userId) {
        // 手动分页
        PageDao pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        Page<SysWorkFlowVo> list = new Page<>();

        List<SysWorkFlowVo> results = new ArrayList<>();
        List<Task> tasks = processService.findTodoTasks(userId, sysWorkFlowVo.getWorkFlowType());
        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            TaskEntityImpl taskImpl = (TaskEntityImpl) task;
            String processInstanceId = taskImpl.getProcessInstanceId();
            // 条件过滤 1
            if (StringUtils.isNotBlank(sysWorkFlowVo.getInstanceId()) && !sysWorkFlowVo.getInstanceId().equals(processInstanceId)) {
                continue;
            }
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String businessKey = processInstance.getBusinessKey();
            SysWorkFlowVo sysWorkFlowVo1 = sysWorkflowMapper.selectSysWorkflowById(new Long(businessKey));
            // 条件过滤 2
            if (StringUtils.isNotBlank(sysWorkFlowVo.getWorkFlowType()) && !sysWorkFlowVo.getWorkFlowType().equals(sysWorkFlowVo1.getWorkFlowType())) {
                continue;
            }
            sysWorkFlowVo1.setTaskId(taskImpl.getId());
            if (taskImpl.getSuspensionState() == 2) {
                sysWorkFlowVo1.setTaskName("已挂起");
            } else {
                sysWorkFlowVo1.setTaskName(taskImpl.getName());
            }
            SysUser sysUser = userMapper.selectUserByLoginName(sysWorkFlowVo1.getApplyUser());
            sysWorkFlowVo1.setApplyUserName(sysUser.getUserName());
            results.add(sysWorkFlowVo1);
        }

        List<SysWorkFlowVo> tempList;
        if (pageNum != null && pageSize != null) {
            int maxRow = (pageNum - 1) * pageSize + pageSize > results.size() ? results.size() : (pageNum - 1) * pageSize + pageSize;
            tempList = results.subList((pageNum - 1) * pageSize, maxRow);
            list.setTotal(results.size());
            list.setPageNum(pageNum);
            list.setPageSize(pageSize);
        } else {
            tempList = results;
        }

        list.addAll(tempList);

        return list;
    }

    /**
     * 查询已办列表
     *
     * @param sysWorkFlowVo
     * @param userId
     * @return
     */
    @Override
    public Page<SysWorkFlowVo> findDoneTasks(SysWorkFlowVo sysWorkFlowVo, String userId) {
        // 手动分页
        PageDao pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        Page<SysWorkFlowVo> list = new Page<>();

        List<SysWorkFlowVo> results = new ArrayList<>();
        List<HistoricTaskInstance> hisList = processService.findDoneTasks(userId, sysWorkFlowVo.getWorkFlowType());
        // 根据流程的业务ID查询实体并关联
        for (HistoricTaskInstance instance : hisList) {
            String processInstanceId = instance.getProcessInstanceId();
            // 条件过滤 1
            if (StringUtils.isNotBlank(sysWorkFlowVo.getInstanceId()) && !sysWorkFlowVo.getInstanceId().equals(processInstanceId)) {
                continue;
            }
            HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String businessKey = processInstance.getBusinessKey();
            SysWorkFlowVo sysWorkFlowVo1 = sysWorkflowMapper.selectSysWorkflowById(new Long(businessKey));
            SysWorkFlowVo newWorkflow = new SysWorkFlowVo();
            if (StringUtils.isNotNull(sysWorkFlowVo1) && StringUtils.isNotNull(newWorkflow)) {
                BeanUtils.copyProperties(sysWorkFlowVo1, newWorkflow);
                // 条件过滤 2
                if (StringUtils.isNotBlank(sysWorkFlowVo.getWorkFlowType()) && !sysWorkFlowVo.getWorkFlowType().equals(sysWorkFlowVo1.getWorkFlowType())) {
                    continue;
                }
                newWorkflow.setTaskId(instance.getId());
                newWorkflow.setTaskName(instance.getName());
                newWorkflow.setDoneTime(instance.getEndTime());
                SysUser sysUser = userMapper.selectUserByLoginName(sysWorkFlowVo1.getApplyUser());
                newWorkflow.setApplyUserName(sysUser.getUserName());
                results.add(newWorkflow);
            }
        }

        List<SysWorkFlowVo> tempList;
        if (pageNum != null && pageSize != null) {
            int maxRow = (pageNum - 1) * pageSize + pageSize > results.size() ? results.size() : (pageNum - 1) * pageSize + pageSize;
            tempList = results.subList((pageNum - 1) * pageSize, maxRow);
            list.setTotal(results.size());
            list.setPageNum(pageNum);
            list.setPageSize(pageSize);
        } else {
            tempList = results;
        }

        list.addAll(tempList);

        return list;
    }
}
