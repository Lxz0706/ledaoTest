package com.ledao.web.controller.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ledao.common.annotation.Log;
import com.ledao.common.config.Global;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.core.page.TableDataInfo;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.bean.SheetExcelData;
import com.ledao.common.utils.file.FileUploadUtils;
import com.ledao.common.utils.poi.EasyExcelUtil;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysDepartment;
import com.ledao.system.dao.SysReshuffle;
import com.ledao.system.dao.SysStaff;
import com.ledao.system.service.ISysDepartmentService;
import com.ledao.system.service.ISysDictDataService;
import com.ledao.system.service.ISysReshuffleService;
import com.ledao.system.service.ISysStaffService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工信息Controller
 *
 * @author lxz
 * @date 2021-06-23
 */
@Controller
@RequestMapping("/system/staff")
public class SysStaffController extends BaseController {
    private String prefix = "system/staff";

    @Autowired
    private ISysStaffService sysStaffService;

    @Autowired
    private ISysDepartmentService sysDepartmentService;

    @Autowired
    private ISysDictDataService sysDictDataService;

    @Autowired
    private ISysReshuffleService sysReshuffleService;

    @RequiresPermissions("system:staff:view")
    @GetMapping()
    public String staff() {
        return prefix + "/staff";
    }

    /**
     * 查询员工信息列表
     */
    @RequiresPermissions("system:staff:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysStaff sysStaff) {
        startPage();
        List<SysStaff> list = sysStaffService.selectSysStaffList(sysStaff);
        for (SysStaff sysStaff1 : list) {
            if (StringUtils.isNotEmpty(sysStaff1.getContact1()) && StringUtils.isNotEmpty(sysStaff1.getContact2())) {
                sysStaff1.setContact1(sysStaff1.getContact1() + ";" + sysStaff1.getContact2());
            } else if (StringUtils.isNotEmpty(sysStaff1.getContact2()) && StringUtils.isEmpty(sysStaff1.getContact1())) {
                sysStaff1.setContact1(sysStaff1.getContact2());
            }
            if (StringUtils.isNotEmpty(sysStaff1.getResumeUrl())) {
                sysStaff1.setFileCount(Long.valueOf(sysStaff1.getResumeUrl().split(";").length));
            }
            if (StringUtils.isNotNull(sysStaff1.getDepartmentId())) {
                sysStaff1.setDepartmentName(sysDepartmentService.selectSysDepartmentById(sysStaff1.getDepartmentId()).getDepartmentName());
            }
        }
        return getDataTable(list);
    }

    /**
     * 新增员工信息
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存员工信息
     */
    @RequiresPermissions("system:staff:add")
    @Log(title = "员工信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysStaff sysStaff) throws IOException {
        sysStaff.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(sysStaffService.insertSysStaff(sysStaff));
    }

    /**
     * 修改员工信息
     */
    @GetMapping("/edit/{staffId}")
    public String edit(@PathVariable("staffId") Long staffId, ModelMap mmap) {
        SysStaff sysStaff = sysStaffService.selectSysStaffById(staffId);
        mmap.put("sysStaff", sysStaff);
        return prefix + "/edit";
    }

    /**
     * 修改保存员工信息
     */
    @RequiresPermissions("system:staff:edit")
    @Log(title = "员工信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysStaff sysStaff) {
        if ("1".equals(sysStaff.getStatus())) {
            SysDepartment sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentName("离职");
            List<SysDepartment> sysDepartmentList = sysDepartmentService.selectSysDepartmentList(sysDepartment);
            if (sysDepartmentList.size() > 0) {
                sysStaff.setDepartmentId(sysDepartmentList.get(0).getDepartmentId());
                sysStaff.setDepartmentName(sysDepartmentList.get(0).getDepartmentName());
            }
        }
        sysStaff.setUpdateBy(ShiroUtils.getLoginName());

        return toAjax(sysStaffService.updateSysStaff(sysStaff));
    }

    @PostMapping("/editResume")
    @ResponseBody
    public AjaxResult editResume(SysStaff sysStaff, @RequestParam("resume") MultipartFile resume) throws IOException {
        SysStaff sysStaff1 = sysStaffService.selectSysStaffById(sysStaff.getStaffId());
        String avatar = FileUploadUtils.upload(Global.getProfile() + "/staff", resume, false);
        if (StringUtils.isNotEmpty(sysStaff1.getResumeUrl())) {
            if (!sysStaff1.getResumeUrl().contains(resume.getOriginalFilename())) {
                sysStaff1.setResumeUrl(avatar + ";" + sysStaff1.getResumeUrl());
            }
        } else {
            sysStaff1.setResumeUrl(avatar);
        }
        sysStaffService.updateSysStaff(sysStaff1);
        return AjaxResult.success();
    }

    @PostMapping("/removeResume")
    @ResponseBody
    public AjaxResult removeResume(@RequestParam("staffId") Long staffId, @RequestParam("resumeUrls") String resumeUrls) {
        SysStaff sysStaff = sysStaffService.selectSysStaffById(staffId);
        for (String string : resumeUrls.split(",")) {
            if (StringUtils.isNotNull(string)) {
                StringBuffer sb = new StringBuffer();
                if (StringUtils.isNotEmpty(sysStaff.getResumeUrl())) {
                    for (String string1 : sysStaff.getResumeUrl().split(";")) {
                        if (!string.equals(StringUtils.substringAfterLast(string1, "/"))) {
                            sb.append(string1).append(";");
                        }
                    }
                }
                sysStaff.setResumeUrl(sb.toString());
                sysStaffService.updateSysStaff(sysStaff);
            }
        }
        return AjaxResult.success();
    }


    /**
     * 删除员工信息
     */
    @RequiresPermissions("system:staff:remove")
    @Log(title = "员工信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysStaffService.deleteSysStaffByIds(ids));
    }

    /**
     * 用户状态修改
     */
    @Log(title = "员工信息", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:staff:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysStaff sysStaff) {
        if ("1".equals(sysStaff.getStatus())) {
            SysDepartment sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentName("离职");
            List<SysDepartment> sysDepartmentList = sysDepartmentService.selectSysDepartmentList(sysDepartment);
            if (sysDepartmentList.size() > 0) {
                sysStaff.setDepartmentId(sysDepartmentList.get(0).getDepartmentId());
                sysStaff.setDepartmentName(sysDepartmentList.get(0).getDepartmentName());
            }
        }
        return toAjax(sysStaffService.changeStatus(sysStaff));
    }

    /**
     * 选择员工
     *
     * @param staffId
     * @param staffName
     * @param mmap
     * @return
     */
    @GetMapping("/selectTree")
    public String selectTree(String staffId, String staffName, ModelMap mmap) {
        mmap.put("staffId", staffId);
        mmap.put("staffName", staffName);
        return prefix + "/tree";
    }

    /**
     * 查看员工详情
     *
     * @param staffId
     * @param modelMap
     * @return
     */
    @GetMapping("/detail/{staffId}")
    public String detail(@PathVariable("staffId") Long staffId, ModelMap modelMap) {
        SysStaff sysStaff = sysStaffService.selectSysStaffById(staffId);
        if ("0".equals(sysStaff.getStatus())) {
            sysStaff.setStatus("正常");
        } else {
            sysStaff.setStatus("停用");
        }
        //计算年龄
        if (StringUtils.isNotNull(sysStaff.getBirthday())) {
            Period period = Period.between(LocalDate.parse(DateUtils.parseDateToStr("yyyy-MM-dd", sysStaff.getBirthday())), LocalDate.now());
            sysStaff.setAge(Long.valueOf(period.getYears()));
        }
        modelMap.put("sysStaff", sysStaff);
        return prefix + "/detail";
    }

    @PostMapping("/selectDepartment")
    @ResponseBody
    public AjaxResult selectDepartment() {
        SysDepartment sysDepartment = new SysDepartment();
        sysDepartment.setDepartmentName("离职");
        List<SysDepartment> sysDepartmentList = sysDepartmentService.selectSysDepartmentList(sysDepartment);
        SysDepartment sysDepartment1 = new SysDepartment();
        if (sysDepartmentList.size() > 0) {
            sysDepartment1.setDepartmentId(sysDepartmentList.get(0).getDepartmentId());
            sysDepartment1.setDepartmentName(sysDepartmentList.get(0).getDepartmentName());
        }
        return AjaxResult.success(sysDepartment1);
    }

    @GetMapping("/toUpload")
    public String toUpload(Long staffId, ModelMap modelMap) {
        modelMap.put("sysStaff", sysStaffService.selectSysStaffById(staffId));
        return prefix + "/uploadResume";
    }

    @PostMapping("/removeFile")
    @ResponseBody
    public AjaxResult removeImg(Long id, String fileName) {
        SysStaff sysStaff = sysStaffService.selectSysStaffById(id);
        logger.info(sysStaff.getResumeUrl());
        if (StringUtils.isNotNull(fileName)) {
            StringBuffer sb = new StringBuffer();
            if (StringUtils.isNotEmpty(sysStaff.getResumeUrl())) {
                for (String string1 : sysStaff.getResumeUrl().split(";")) {
                    if (!fileName.equals(StringUtils.substringAfterLast(string1, "/"))) {
                        sb.append(string1).append(";");
                    }
                }
            }
            sysStaff.setResumeUrl(sb.toString());
            sysStaffService.updateSysStaff(sysStaff);
        }
        return success();
    }

    @PostMapping("/selectStaffByAge")
    @ResponseBody
    public String selectStaffByAge(SysStaff sysStaff) {
        JSONArray jsonArray = new JSONArray();
        List<SysStaff> sysStaffList = sysStaffService.selectSysStaffList(sysStaff);
        Map<String, Integer> ageCounts = new HashMap<>();
        // 初始化各个年龄段的计数器
        ageCounts.put("20-29岁", 0);
        ageCounts.put("30-39岁", 0);
        ageCounts.put("40-49岁", 0);
        ageCounts.put("50岁以上", 0);
        for (SysStaff sysStaff1 : sysStaffList) {
            if (StringUtils.isNotNull(sysStaff1.getBirthday())) {
                Period period = Period.between(LocalDate.parse(DateUtils.parseDateToStr("yyyy-MM-dd", sysStaff1.getBirthday())), LocalDate.now());
                int age = period.getYears();
                if (age > 19 && age < 30) {
                    ageCounts.put("20-29岁", ageCounts.get("20-29岁") + 1);
                } else if (age > 29 && age < 40) {
                    ageCounts.put("30-39岁", ageCounts.get("30-39岁") + 1);
                } else if (age > 39 && age < 50) {
                    ageCounts.put("40-49岁", ageCounts.get("40-49岁") + 1);
                } else if (age > 49) {
                    ageCounts.put("50岁以上", ageCounts.get("50岁以上") + 1);
                }
            }
        }
        int total = 0;
        for (Map.Entry<String, Integer> entry : ageCounts.entrySet()) {
            total = total + entry.getValue();
        }

        for (Map.Entry<String, Integer> entry : ageCounts.entrySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", entry.getKey());
            jsonObject.put("value", entry.getValue());
            jsonObject.put("sold", new BigDecimal(entry.getValue()).divide(new BigDecimal(total), 1, BigDecimal.ROUND_HALF_UP));
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    /**
     * 根据学历分组
     *
     * @param sysStaff
     * @return
     */
    @PostMapping("/selectStaffByEducation")
    @ResponseBody
    public String selectStaffByEducation(SysStaff sysStaff) {
        JSONArray jsonArray = new JSONArray();
        Map<String, Integer> eduCounts = new HashMap<>();
        // 初始化各个年龄段的计数器
        eduCounts.put("硕士及以上", 0);
        eduCounts.put("本科", 0);
        eduCounts.put("大专及以下", 0);
        List<SysStaff> sysStaffList = sysStaffService.selectStaffByEducation(sysStaff);
        for (SysStaff sysStaff1 : sysStaffList) {
            if ("1".equals(sysStaff1.getEducation()) || "2".equals(sysStaff1.getEducation())) {
                eduCounts.put("硕士及以上", eduCounts.get("硕士及以上") + 1);
            } else if ("3".equals(sysStaff1.getEducation())) {
                eduCounts.put("本科", eduCounts.get("本科") + 1);
            } else if ("4".equals(sysStaff1.getEducation()) || "5".equals(sysStaff1.getEducation()) || "6".equals(sysStaff1.getEducation()) || "7".equals(sysStaff1.getEducation()) || "8".equals(sysStaff1.getEducation())) {
                eduCounts.put("大专及以下", eduCounts.get("大专及以下") + 1);
            }
        }
        for (Map.Entry<String, Integer> entry : eduCounts.entrySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", entry.getKey());
            jsonObject.put("value", entry.getValue());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    @PostMapping("/selectStaffBySex")
    @ResponseBody
    public String selectStaffBySex(SysStaff sysStaff) {
        JSONArray jsonArray = new JSONArray();
        List<SysStaff> sysStaffList = sysStaffService.selectStaffBySex(sysStaff);
        int total = 0;
        for (SysStaff sysStaff1 : sysStaffList) {
            total = total + new Long(sysStaff1.getSexCount()).intValue();
        }
        for (SysStaff sysStaff1 : sysStaffList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sex", sysStaff1.getSex());
            jsonObject.put("count", sysStaff1.getSexCount());
            jsonObject.put("sold", new BigDecimal(sysStaff1.getSexCount()).divide(new BigDecimal(total), 1, BigDecimal.ROUND_HALF_UP));
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }

    @PostMapping("/selectStaffBySecretaryLing")
    @ResponseBody
    public String selectStaffBySecretaryLing(SysStaff sysStaff) {
        JSONArray jsonArray = new JSONArray();
        List<SysStaff> sysStaffList = sysStaffService.selectStaffBySecretaryLing(sysStaff);
        for (SysStaff sysStaff1 : sysStaffList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", sysStaff1.getSecretaryLing() + "年");
            jsonObject.put("value", sysStaff1.getSecretaryLingCount());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }

    @Log(title = "员工基本信息导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:staff:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysStaff> util = new ExcelUtil<SysStaff>(SysStaff.class);
        List<SysStaff> sysStaffList = util.importEasyExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = sysStaffService.importStaff(sysStaffList, true, operName);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    @ResponseBody
    public static void importTemplate(HttpServletResponse response) throws Exception {
        EasyExcelUtil util = new EasyExcelUtil();
        SysStaff sysStaff = new SysStaff();
        sysStaff.setDepartmentName("测试");
        sysStaff.setStaffName("测试人员");
        sysStaff.setSex("男");
        List<SysStaff> sysStaffList = new ArrayList<>();
        sysStaffList.add(sysStaff);


        List<SheetExcelData> sheetExcelDataList = new ArrayList<>();
        SheetExcelData<SysStaff> sysApplyInSheetExcelData = new SheetExcelData<>();
        sysApplyInSheetExcelData.setSheetName("员工基本信息");
        sysApplyInSheetExcelData.settClass(SysStaff.class);
        sysApplyInSheetExcelData.setDataList(sysStaffList);
        sheetExcelDataList.add(sysApplyInSheetExcelData);

        util.exportSheetsExcel("员工信息", sheetExcelDataList, response);
    }

    /**
     * 导出员工信息列表
     */
    @RequiresPermissions("system:staff:export")
    @Log(title = "员工信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(SysStaff sysStaff, HttpServletResponse response) throws IOException {
        String id = getRequest().getParameter("ids");
        EasyExcelUtil util = new EasyExcelUtil();
        List<SysStaff> list = new ArrayList<>();
        List<SysReshuffle> sysReshuffleList = new ArrayList<>();
        List<SheetExcelData> sheetExcelDataList = new ArrayList<>();
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotNull(id)) {
            list = sysStaffService.selectByIds(id);
        } else {
            list = sysStaffService.selectSysStaffList(sysStaff);
        }

        for (SysStaff sysStaff1 : list) {
            sysStaff1.setDepartmentName(sysDepartmentService.selectSysDepartmentById(sysStaff1.getDepartmentId()).getDepartmentName());
            sysStaff1.setSex(sysDictDataService.selectDictLabel("sys_user_sex", sysStaff1.getSex()));
            sysStaff1.setPoliticalOutlook(sysDictDataService.selectDictLabel("sys_staff_politicalOutlook", sysStaff1.getPoliticalOutlook()));
            sysStaff1.setEducation(sysDictDataService.selectDictLabel("sys_staff_education", sysStaff1.getEducation()));
            sysStaff1.setMarriage(sysDictDataService.selectDictLabel("sys_staff_marriage", sysStaff1.getMarriage()));
            sysStaff1.setStatus(sysStaff1.getStatus().equals("0") ? "正常" : "停用");
            //计算年龄
            if (StringUtils.isNotNull(sysStaff1.getBirthday())) {
                Period period = Period.between(LocalDate.parse(DateUtils.parseDateToStr("yyyy-MM-dd", sysStaff1.getBirthday())), LocalDate.now());
                sysStaff1.setAge(Long.valueOf(period.getYears()));
            }
            List<SysReshuffle> sysReshuffleList1 = sysReshuffleService.selectReshuffleByStaffId(sysStaff1.getStaffId());
            for (SysReshuffle sysReshuffle : sysReshuffleList1) {
                SysStaff sysStaff2 = sysStaffService.selectSysStaffById(sysReshuffle.getStaffId());
                if (StringUtils.isNotNull(sysStaff2)) {
                    if (StringUtils.isNotEmpty(sysStaff2.getStaffName())) {
                        sysReshuffle.setStaffName(sysStaff2.getStaffName());
                    }
                }
                sysReshuffle.setTransferType(sysDictDataService.selectDictLabel("sys_staff_marriage", sysReshuffle.getTransferType()));
            }
            sysReshuffleList.addAll(sysReshuffleList1);
        }

        SheetExcelData<SysStaff> sysApplyInSheetExcelData = new SheetExcelData<>();
        sysApplyInSheetExcelData.setSheetName("员工基本信息");
        sysApplyInSheetExcelData.settClass(SysStaff.class);
        sysApplyInSheetExcelData.setDataList(list);
        sheetExcelDataList.add(sysApplyInSheetExcelData);

        SheetExcelData<SysReshuffle> sysDocumentFileSheetExcelData = new SheetExcelData<>();
        sysDocumentFileSheetExcelData.setSheetName("异动情况");
        sysDocumentFileSheetExcelData.settClass(SysReshuffle.class);
        sysDocumentFileSheetExcelData.setDataList(sysReshuffleList);
        sheetExcelDataList.add(sysDocumentFileSheetExcelData);

        util.exportSheetsExcel("员工信息", sheetExcelDataList, response);
    }
}
