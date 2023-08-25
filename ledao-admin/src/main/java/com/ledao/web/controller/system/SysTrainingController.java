package com.ledao.web.controller.system;

import java.util.List;

import com.ledao.common.utils.Arith;
import com.ledao.common.utils.StringUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.framework.web.dao.server.Sys;
import com.ledao.system.service.ISysDocumentService;
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
import com.ledao.common.enums.BusinessType;
import com.ledao.system.dao.SysTraining;
import com.ledao.system.service.ISysTrainingService;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.common.core.page.TableDataInfo;

/**
 * 学习培训Controller
 *
 * @author lxz
 * @date 2023-07-13
 */
@Controller
@RequestMapping("/system/training")
public class SysTrainingController extends BaseController {
    private String prefix = "system/training";

    @Autowired
    private ISysTrainingService sysTrainingService;

    @Autowired
    private ISysDocumentService sysDocumentService;

    @Autowired
    private ISysUserService sysUserService;

    @RequiresPermissions("system:training:view")
    @GetMapping()
    public String training() {
        return prefix + "/training";
    }

    /**
     * 查询学习培训列表
     */
    @RequiresPermissions("system:training:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysTraining sysTraining) {
        startPage();
        List<SysTraining> list = sysTrainingService.selectSysTrainingList(sysTraining);
        for (SysTraining sysTraining1 : list) {
            sysTraining1.setDocumentName(sysDocumentService.selectSysDocumentById(sysTraining1.getDocumentId()).getFileName());
            sysTraining1.setCreator(sysUserService.selectUserByLoginName(sysTraining1.getCreateBy()).getUserName());
        }
        return getDataTable(list);
    }

    /**
     * 导出学习培训列表
     */
    @RequiresPermissions("system:training:export")
    @Log(title = "学习培训", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysTraining sysTraining) {
        List<SysTraining> list = sysTrainingService.selectSysTrainingList(sysTraining);
        ExcelUtil<SysTraining> util = new ExcelUtil<SysTraining>(SysTraining.class);
        return util.exportExcel(list, "training");
    }

    /**
     * 新增学习培训
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存学习培训
     */
    @RequiresPermissions("system:training:add")
    @Log(title = "学习培训", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysTraining sysTraining) {
        SysTraining sysTraining1 = new SysTraining();
        sysTraining1.setDocumentId(sysTraining.getDocumentId());
        sysTraining1.setCreateBy(sysTraining.getCreateBy());
        List<SysTraining> sysTrainingList = sysTrainingService.selectSysTrainingList(sysTraining1);
        sysTraining.setCreateBy(ShiroUtils.getLoginName());
        if (StringUtils.isNull(sysTraining.getPlayTime())) {
            sysTraining.setPlayTime(Long.valueOf(0));
        }
        if (StringUtils.isNull(sysTraining.getTotalTime())) {
            sysTraining.setTotalTime(Long.valueOf(0));
        }
        sysTraining.setLearnTime(Arith.getPercent(sysTraining.getPlayTime(), sysTraining.getTotalTime(), 2) + "%");
        sysTraining.setPlaybackProgress(Arith.getPercent(sysTraining.getPlayTime(), sysTraining.getTotalTime(), 2) + "%");
        if (StringUtils.isNull(sysTrainingList) || sysTrainingList.size() <= 0) {
            sysTrainingService.insertSysTraining(sysTraining);
        } else {
            for (SysTraining sysTraining2 : sysTrainingList) {
                sysTraining.setTrainId(sysTraining2.getTrainId());
            }
            sysTraining.setUpdateBy(sysTraining.getCreateBy());
            sysTrainingService.updateSysTraining(sysTraining);
        }
        return AjaxResult.success();
    }

    /**
     * 修改学习培训
     */
    @GetMapping("/edit/{trainId}")
    public String edit(@PathVariable("trainId") Long trainId, ModelMap mmap) {
        SysTraining sysTraining = sysTrainingService.selectSysTrainingById(trainId);
        mmap.put("sysTraining", sysTraining);
        return prefix + "/edit";
    }

    /**
     * 修改保存学习培训
     */
    @RequiresPermissions("system:training:edit")
    @Log(title = "学习培训", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysTraining sysTraining) {
        return toAjax(sysTrainingService.updateSysTraining(sysTraining));
    }

    /**
     * 删除学习培训
     */
    @RequiresPermissions("system:training:remove")
    @Log(title = "学习培训", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysTrainingService.deleteSysTrainingByIds(ids));
    }

    @PostMapping("/getCurrentTimeByDocumentId")
    @ResponseBody
    public AjaxResult getCurrentTimeByDocumentId(SysTraining sysTraining) {
        sysTraining.setCreateBy(ShiroUtils.getLoginName());
        List<SysTraining> sysTrainingList = sysTrainingService.selectSysTrainingList(sysTraining);
        return AjaxResult.success(sysTrainingList);
    }

    @GetMapping("/trainingList")
    public String trainingList(Long documentId, ModelMap modelMap) {
        modelMap.put("documentId", documentId);
        return prefix + "/training";
    }
}
