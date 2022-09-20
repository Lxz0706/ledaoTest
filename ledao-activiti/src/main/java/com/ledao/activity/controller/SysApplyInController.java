package com.ledao.activity.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import com.ledao.activity.dao.*;
import com.ledao.activity.mapper.SysWorkflowProcessMapper;
import com.ledao.activity.service.*;
import com.ledao.common.core.text.Convert;
import com.ledao.common.json.JSONObject;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.WxImageTool;
import com.ledao.common.utils.ZipUtils;
import com.ledao.common.utils.file.FileUploadUtils;
import com.ledao.common.utils.file.FileUtils;
import com.ledao.common.utils.poi.EasyExcelUtil;
import com.ledao.common.utils.qrCode.WxQrCode;
import com.ledao.common.core.dao.entity.SysDictData;
import com.ledao.common.core.dao.entity.SysRole;
import com.ledao.common.utils.bean.SheetExcelData;
import com.ledao.system.mapper.SysRoleMapper;
import com.ledao.system.mapper.SysUserMapper;
import com.ledao.system.mapper.SysUserRoleMapper;
import com.ledao.system.service.ISysConfigService;
import com.ledao.system.service.ISysDictDataService;
import com.ledao.system.service.ISysRoleService;
import com.ledao.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.ledao.common.annotation.Log;
import com.ledao.common.core.controller.BaseController;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.core.page.TableDataInfo;
import com.ledao.common.enums.BusinessType;
import com.ledao.common.utils.poi.ExcelUtil;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.common.core.dao.entity.SysUser;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 档案入库申请Controller
 *
 * @author lxz
 * @date 2021-08-04
 */
@Controller
@RequestMapping("/applyIn")
public class SysApplyInController extends BaseController {
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

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysDictDataService sysDictDataService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private SysWorkflowProcessMapper sysWorkflowProcessMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    private List<Long> applyIds = new ArrayList<>();
    private String accessToken;

    @GetMapping("/applyIn")
    public String applyIn() {
        return prefix + "/applyIn";
    }


    //    @RequiresPermissions("applyIn:view")
    @GetMapping("/docApplyIn")
    public String docApplyIn() {
        return "docList/docApplyIn";
    }

    @GetMapping("/docEdit/{applyId}")
    public String docEditApplyIn(@PathVariable("applyId") Long applyId, ModelMap mmap) {
        SysApplyIn sysApplyIn = sysApplyInService.selectSysApplyInById(applyId);
        mmap.put(("appStatu"), sysApplyIn.getApproveStatu());
        mmap.put("sysApplyIn", sysApplyIn);
        return "docList/docEdit";
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

    @GetMapping("/inOutPage")
    public String applyListByMe(Long documentId, String documentType, ModelMap modelMap) {
//        我的申请
        modelMap.put("documentId", documentId);
        modelMap.put("documentType", documentType);
        return "docList/inOutPage";
    }

    @GetMapping("/importApplyInImport")
    public String importApplyInImport() {
        return "docList/docImport";
    }

    @Log(title = "历史数据迁移", businessType = BusinessType.INSERT)
    @PostMapping("/importApplyIn")
    @ResponseBody
    public AjaxResult importApplyIn(@RequestParam("file") MultipartFile file) {
        try {
            return sysApplyInService.importApplyIn(file);
        } catch (Exception e) {
            return AjaxResult.error("导入失败 " + e.getMessage(), e);
        }
    }

    @GetMapping("/reject/{applyId}/{applyType}/{approveStatu}")
    public String reject(@PathVariable("applyId") String applyId, @PathVariable("applyType") String applyType,
                         @PathVariable("approveStatu") String approveStatu, ModelMap modelMap) {
//        我的添加审批拒绝备注
        modelMap.put("applyId", applyId);
        modelMap.put("applyType", applyType);
        modelMap.put("approveStatu", approveStatu);
        return prefix + "/reject";
    }

    @GetMapping("/applyProcess/historyList/{applyId}")
    public String historyProcess(@PathVariable("applyId") String applyId, ModelMap modelMap) {
//        查看审批历史
        return "applyProcess/historyList";
    }

    @GetMapping("/processWorkDialog/{applyId}")
    public String processWorkDialog(@PathVariable("applyId") String applyId, ModelMap modelMap) {
//        查看审批历史
        modelMap.put("applyId", applyId);
        return "applyIn/processWorkDialog";
    }

    @GetMapping("/fileDetail/{documentId}")
    public String fileDetail(@PathVariable("documentId") Long documentId, ModelMap modelMap) {
//        查看档案附件
        SysFileDetail sysFileDetail = new SysFileDetail();
        sysFileDetail.setDocumentFileId(documentId);
        List<SysFileDetail> des = sysFileDetailService.selectSysFileDetailList(sysFileDetail);
        modelMap.put("sysFileDetail", sysFileDetail);
        return "fileDetail/detail";
    }

    @GetMapping("/documentFile")
    public String documentFile(String dataType, String documentType, String roleType, ModelMap mmap) {
        mmap.put("projectZckType", dataType);
        mmap.put("documentType", documentType);
        mmap.put("roleType", roleType);
        return "docList/docApplyInZcb";
    }

    /**
     * 查询档案入库申请列表
     */
//    @RequiresPermissions("applyIn:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysApplyIn sysApplyIn) {
        startPage();
        boolean isfileAdmin = false;
        SysUser u = ShiroUtils.getSysUser();
        for (SysRole r : u.getRoles()) {
            if ("documentAdmin".equals(r.getRoleKey())) {
                isfileAdmin = true;
                continue;
            }
        }
        if ("1".equals(sysApplyIn.getApplyType()) && isfileAdmin) {
        } else {
            sysApplyIn.setApplyUser(ShiroUtils.getLoginName());
        }
        List<SysApplyIn> list = sysApplyInService.selectSysApplyInListUser(sysApplyIn);
        return getDataTable(list);
    }


    @PostMapping("/listInOutDetail")
    @ResponseBody
    public TableDataInfo listInOutDetail(SysApplyIn sysApplyIn) {
        startPage();
        List<SysApplyIn> list = sysApplyInService.listInOutDetail(sysApplyIn);
        return getDataTable(list);
    }

    @PostMapping("/docList")
    @ResponseBody
    public TableDataInfo docList(SysApplyIn sysApplyIn) {
        startPage();
        List<SysApplyIn> list = sysApplyInService.selectSysApplyInListUser(sysApplyIn);
        return getDataTable(list);
    }

    @PostMapping("/docListDetail")
    @ResponseBody
    public TableDataInfo docListDetail(SysApplyIn sysApplyIn) {
        startPage();
        List<SysApplyIn> list = sysApplyInService.selectSysApplyInDocDetailList(sysApplyIn);
        return getDataTable(list);
    }


    @PostMapping("/docListDailyDetail")
    @ResponseBody
    public TableDataInfo docListDailyDetail(SysApplyIn sysApplyIn) {
        startPage();
        List<SysApplyIn> list = sysApplyInService.selectSysApplyInDailyDetailList(sysApplyIn);
        return getDataTable(list);
    }


    @PostMapping("/docListDobtDetailByPName")
    @ResponseBody
    public TableDataInfo docListDobtDetailByPName(SysApplyIn sysApplyIn) {
        startPage();
        List<SysApplyIn> list = sysApplyInService.docListDobtDetailByPName(sysApplyIn);
        return getDataTable(list);
    }

    @PostMapping("/docListDetailByPName")
    @ResponseBody
    public TableDataInfo docListDetailByPName(SysApplyIn sysApplyIn) {
        startPage();
        List<SysApplyIn> list = sysApplyInService.selectSysApplyInDocByPNameDetailList(sysApplyIn);
        return getDataTable(list);
    }

    @PostMapping("/docListDetailZcb")
    @ResponseBody
    public TableDataInfo docListDetailZcb(SysApplyIn sysApplyIn) {
        startPage();
        List<SysApplyIn> list = sysApplyInService.selectSysApplyInDocDetailZcbList(sysApplyIn);
        return getDataTable(list);
    }

    /**
     * 我的已办
     *
     * @param sysApplyIn
     * @return
     */
    @PostMapping("/listByMe")
    @ResponseBody
    public TableDataInfo listDownByMe(SysApplyIn sysApplyIn) {
//        sysApplyIn = new SysApplyIn();
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        sysApplyIn.setApplyUser(user.getLoginName());
        List<SysApplyIn> list = sysApplyInService.listDownByMe(sysApplyIn);
        return getDataTable(list);
    }

    /**
     * 我的申请
     *
     * @param
     * @return
     */
    @PostMapping("/applyListByMe")
    @ResponseBody
    public TableDataInfo applyListByMe(SysApplyIn sysApplyIn) {
        startPage();
        SysUser user = ShiroUtils.getSysUser();
        sysApplyIn.setApproveStatu("3");
        List<SysApplyIn> list = sysApplyInService.selectSysApplyInList(sysApplyIn);
        for (SysApplyIn sysApplyIn1 : list) {
            sysApplyIn1.setApplyUserName(userMapper.selectUserByLoginName(sysApplyIn1.getApplyUser()).getUserName());
        }
        return getDataTable(list);
    }

    /**
     * 我的待办
     *
     * @param sysApplyIn
     * @return
     */
    @PostMapping("/listUnDownByMe")
    @ResponseBody
    public TableDataInfo listUnDownByMe(SysApplyIn sysApplyIn) {
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
    public AjaxResult export(SysApplyIn sysApplyIn) {
        List<SysApplyIn> list = sysApplyInService.selectSysApplyInList(sysApplyIn);
        ExcelUtil<SysApplyIn> util = new ExcelUtil<SysApplyIn>(SysApplyIn.class);
        return util.exportExcel(list, "applyIn");
    }

    /**
     * 新增档案入库申请
     */
    @GetMapping("/add")
    public String add(ModelMap mmp) {
        mmp.put("sysDictData", sysDictDataService.selectDictDataByType("sys_company_name"));
        return prefix + "/add";
    }

    /**
     * 新增档案入库申请
     */
    @GetMapping("/editOutList/{applyId}")
    public String editOutList(@PathVariable("applyId") Long applyId, ModelMap mmap) {
        SysApplyIn sysApplyIn = sysApplyInService.selectSysApplyInById(applyId);
        mmap.put("applyId", applyId);
        mmap.put("sysApplyIn", sysApplyIn);
        return prefix + "/editOutList";
    }

    @GetMapping("/editOutUpdate/{outDetailId}")
    public String editOutUpdate(@PathVariable("outDetailId") Long outDetailId, ModelMap mmap) {
        SysApplyOutDetail sysApplyOutDetail = sysApplyOutDetailService.selectSysApplyOutDetailById(outDetailId);
        SysApplyIn in = sysApplyInService.selectSysApplyInById(sysApplyOutDetail.getApplyId());
        mmap.put("outDetailId", outDetailId);
        mmap.put("sysApplyOutDetail", sysApplyOutDetail);
        mmap.put("approveStatu", in.getApproveStatu());
        return prefix + "/editOutUpdate";
    }

    /**
     * 新增档案出库申请
     */
    @GetMapping("/addOut")
    public String addOut(ModelMap mmap) {
        String roleType = sysApplyInService.checkUserRole(ShiroUtils.getSysUser());
        mmap.put("roleType", roleType);
        mmap.put("sysDictData", sysDictDataService.selectDictDataByType("sys_company_name"));
        return prefix + "/addOut";
    }

    @PostMapping("/getRoleType")
    @ResponseBody
    public AjaxResult getRoleType(@RequestParam("applyUser") String applyUser) {
        String roleType = sysApplyInService.checkUserRole(ISysUserService.selectUserByLoginName(applyUser));
        if (roleType != null) {
            return AjaxResult.success(roleType);
        } else {
            return AjaxResult.error("无项目");
        }

    }


    /**
     * 新增档案入库申请
     */
    @GetMapping("/editDocumentModal/{applyId}/{documentTypeVal}/{applyType}")
    public String editDocumentModal(@PathVariable("applyId") Long applyId
            , @PathVariable("documentTypeVal") String documentTypeVal
            , @PathVariable("applyType") String applyType
            , ModelMap mmap) {
        mmap.put("applyId", applyId);
        mmap.put("documentTypeVal", documentTypeVal);
        mmap.put("applyType", applyType);
        return prefix + "/editDocumentModal";
    }

    /**
     * 我的待办
     */
    @GetMapping("/editDocumentModal/{applyId}/{documentTypeVal}/{applyType}/{applyTypeUnDone}")
    public String editDocumentModalUnDone(@PathVariable("applyId") Long applyId
            , @PathVariable("documentTypeVal") String documentTypeVal
            , @PathVariable("applyType") String applyType
            , @PathVariable("applyTypeUnDone") String applyTypeUnDone
            , ModelMap mmap) {
        mmap.put("applyId", applyId);
        mmap.put("documentTypeVal", documentTypeVal);
        mmap.put("applyType", applyType);
        mmap.put("applyTypeUnDone", applyTypeUnDone);
        return prefix + "/editDocumentModal";
    }

    @GetMapping("/editDocumentModal/{applyId}")
    public String editDocumentModalUnDoneAdd(@PathVariable("applyId") Long applyId, ModelMap mmap) {
        SysApplyIn sysApplyIn = sysApplyInService.selectSysApplyInById(applyId);
        mmap.put("documentType", sysApplyIn.getDocumentType());
        mmap.put("applyId", applyId);
        return prefix + "/editDocumentModal";
    }

    @GetMapping("/selectPro")
    public String selectPro(@RequestParam("roleType") String roleType, ModelMap mmap) {
        if ("inve".equals(roleType)) {
            return "dialogs/zcbQueryAll";
        } else if ("thb".equals(roleType)) {
            return "dialogs/projectZckByType";
        } else if ("bg".equals(roleType)) {
            return "dialogs/czbgQueryAll";
        } else {
            return null;
        }
    }

    @GetMapping("/selectDebtor")
    public String selectDebtor(@RequestParam("zcbId") String zcbId, @RequestParam("roleType") String roleType, ModelMap mmap) {
        mmap.put("proId", zcbId);
        if ("inve".equals(roleType)) {
            return "dialogs/zck";
        }
//        String roleType = sysApplyInService.checkUserRole();
        if ("thb".equals(roleType)) {
            return "dialogs/project";
        } else {
            return null;
        }
    }

    /**
     * 新增保存档案入库申请
     */
//    @RequiresPermissions("applyIn:add")
    @Log(title = "档案入库申请(保存0)", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysApplyIn sysApplyIn) {
        String userName = ShiroUtils.getLoginName();
        sysApplyIn.setCreateBy(userName);
        sysApplyIn.setCreateTime(new Date());
        sysApplyIn.setUpdateTime(new Date());
        sysApplyIn.setReviser(userName);
        sysApplyIn.setApproveStatu("0");
        //            判断当有实际提交人时，获取实际提交人的直属领导
        if (StringUtils.isNotEmpty(sysApplyIn.getRealCreateBy())) {
            SysUser realUser = ISysUserService.selectUserByLoginName(sysApplyIn.getRealCreateBy());
            if (realUser != null && StringUtils.isNotEmpty(realUser.getLoginName())) {
            } else {
                return AjaxResult.error("实际提交人不存在，请重新输入");
            }
        }
        sysApplyInService.insertSysApplyIn(sysApplyIn);
        Map resMpa = new HashMap();
        resMpa.put("applyId", sysApplyIn.getApplyId());
        return AjaxResult.success(resMpa);
    }


    /**
     * 新增保存档案入库申请
     */
//    @RequiresPermissions("applyIn:addSysApplyIn")
    @Log(title = "档案入库申请发起", businessType = BusinessType.INSERT)
    @PostMapping("/addSysApplyIn")
    @ResponseBody
    public AjaxResult addSysApplyIn(SysApplyIn sysApplyIn) {
        sysApplyIn.setApproveStatu("0");
        sysApplyInService.insertSysApplyIn(sysApplyIn);
        return toAjax(true);
    }

    /**
     * 修改档案入库申请
     */
    @GetMapping("/edit/{applyId}/{applyType}/{applyTypeUnDone}/{seOrEd}")
    public String edit(@PathVariable("applyId") Long applyId, @PathVariable("applyType") String applyType,
                       @PathVariable("applyTypeUnDone") String applyTypeUnDone, @PathVariable("seOrEd") String seOrEd, ModelMap mmap) {
        SysApplyIn sysApplyIn = sysApplyInService.selectSysApplyInById(applyId);
        mmap.put("sysApplyIn", sysApplyIn);
        mmap.put(("appStatu"), sysApplyIn.getApproveStatu());
        mmap.put("applyTypeUnDone", applyTypeUnDone);
        mmap.put("seOrEd", seOrEd);
        mmap.put("sysDictData", sysDictDataService.selectDictDataByType("sys_company_name"));
        if ("1".equals(applyType)) {
            String roleType = sysApplyInService.checkUserRole(ShiroUtils.getSysUser());
            mmap.put("roleType", roleType);
            return prefix + "/editOut";
        }
        return prefix + "/edit";
    }

    /**
     * 修改档案入库申请
     */
    @GetMapping("/edit/{applyId}")
    public String editApplyIn(@PathVariable("applyId") Long applyId, ModelMap mmap) {
        SysApplyIn sysApplyIn = sysApplyInService.selectSysApplyInById(applyId);
        mmap.put(("appStatu"), sysApplyIn.getApproveStatu());
        mmap.put("sysApplyIn", sysApplyIn);
        mmap.put("sysDictData", sysDictDataService.selectDictDataByType("sys_company_name"));
        return prefix + "/edit";
    }

    /**
     * 修改档案入库申请
     */
    @GetMapping("/editOut/{applyId}")
    public String editOut(@PathVariable("applyId") Long applyId, ModelMap mmap) {
        SysApplyIn sysApplyIn = sysApplyInService.selectSysApplyInById(applyId);
        mmap.put("sysApplyIn", sysApplyIn);
        mmap.put(("appStatu"), sysApplyIn.getApproveStatu());
        String roleType = sysApplyInService.checkUserRole(ISysUserService.selectUserByLoginName(sysApplyIn.getApplyUser()));
        mmap.put("roleType", roleType);
        return prefix + "/editOut";
    }

    /**
     * 修改档案入库申请
     */
    @GetMapping("/applyBack/{applyId}")
    public String applyBack(@PathVariable("applyId") Long applyId, ModelMap mmap) {
        SysApplyIn sysApplyIn = sysApplyInService.selectSysApplyInById(applyId);
        mmap.put("sysApplyIn", sysApplyIn);
        return prefix + "/edit";
    }


    /**
     * 接收二维码
     *
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/code")
    @ResponseBody
    public Object twoCode(Long documentId, HttpServletResponse response) throws Exception {
        JSONObject data = new JSONObject();
        String accessToken = null;
        try {
            JSONObject parmData = new JSONObject();
            parmData.put("scene", documentId);
            // 提交// 提交 提交使用
            parmData.put("url", "pages/lookInformation/index");


            String parm = parmData.toString();
            accessToken = configService.getWechatAccessToken();
            if (StringUtils.isEmpty(accessToken)) {
                throw new RuntimeException("获取accessToken失败");
            }
            data.put("twoCodeUrl", WxQrCode.getWxQrCode(accessToken, FileUploadUtils.getDefaultBaseDir(), StringUtils.replaceBlank(sysDocumentFileService.selectSysDocumentFileById(documentId).getFileName().trim()), parm, response));
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改保存档案入库申请
     */
//    @RequiresPermissions("applyIn:edit")
    @Log(title = "档案入库申请", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysApplyIn sysApplyIn) {
        return toAjax(sysApplyInService.editSave(sysApplyIn));
    }

    /**
     * 档案状态修改，保存，提交审批，审批通过，审批拒绝
     */
    @Log(title = "档案状态修改", businessType = BusinessType.UPDATE)
    @PostMapping("/applyEditSave")
    @ResponseBody
    public synchronized AjaxResult applyEditSave(SysApplyIn sysApplyIn, HttpServletRequest request) {
        AjaxResult res = new AjaxResult();
        try {
            res = sysApplyInService.applyEditSave(sysApplyIn, request);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            applyIds.remove(sysApplyIn.getApplyId());
        }
        return res;
    }

    @GetMapping("/documentTypeListOpen")
    public String documentTypeListOpen() {
        return "docList/documentTypeList";
    }

    @GetMapping("/documentTypeListBack")
    public String documentTypeListBack(String documentType, String roleType, ModelMap mmap) {
        mmap.put("roleType", roleType);
        if ("0".equals(documentType)) {
            mmap.put("documentType", documentType);
            if ("thb".equals(roleType)) {
                return "docList/documentDetailTypeList";
            }
            return "docList/docProTypeMu";
        } else {
            return "docList/documentTypeList";
        }
    }

    @GetMapping("/backDocumentTypeList")
    public String backDocumentTypeList() {
        return "docList/documentTypeList";
    }

    @GetMapping("/documentDetailTypeListOpen")
    public String documentDetailTypeListOpen(String docType, ModelMap mmap) {
        mmap.put("documentType", docType);
        if ("0".equals(docType)) {
            return "docList/docProTypeMu";
        } else {
            return "docList/docApplyInDaily";
        }
    }

    @GetMapping("/docProTypeMu")
    public String docProTypeMu(String roleType, ModelMap mmap) {
        mmap.put("roleType", roleType);
        mmap.put("documentType", "0");
        if ("thb".equals(roleType)) {
            return "docList/documentDetailTypeList";
        } else if ("bg".equals(roleType)) {
            //大型单体的项目类/资产包页面
            return "docList/docApplyInZcb";
        } else {
            //投资的项目类/资产包页面
            return "docList/docApplyInZcb";
        }
    }


    @GetMapping("/docApplyInZcbDetail")
    public String docApplyInZcbDetail(String roleType, String documentType, String projectName, String projectZckType, ModelMap mmap) {
        mmap.put("projectZckType", projectZckType);
        mmap.put("roleType", roleType);
        mmap.put("documentType", documentType);
        mmap.put("projectName", projectName);
        if ("bg".equals(roleType)) {
            return "docList/docApplyInZcbDebtDetail";
        } else {
            return "docList/docApplyInZcbDetail";
        }
//        return "docList/docApplyInZcbDebtDetail";
    }

    @GetMapping("/docApplyInZcbDebtDetail")
    public String docApplyInZcbDebtDetail(String roleType, String documentType, String projectName, String projectZckType, String debtorName, ModelMap mmap) {
        mmap.put("projectZckType", projectZckType);
        mmap.put("roleType", roleType);
        mmap.put("documentType", documentType);
        mmap.put("projectName", projectName);
        mmap.put("debtorName", debtorName);
        //投资详情展示页面
        return "docList/docApplyInZcbDebtDetail";
    }

    @GetMapping("/docApplyInDailyDetail")
    public String docApplyInDailyDetail(String roleType, String documentType, String companyName, ModelMap mmap) {
        mmap.put("roleType", roleType);
        mmap.put("documentType", documentType);
        mmap.put("companyName", companyName);
        return "docList/docApplyInDailyDetail";
    }


    @PostMapping("/documentTypeList")
    @ResponseBody
    public TableDataInfo documentTypeList() {
        startPage();
        List<SysDictData> sysDictDataList = sysDictDataService.selectDictDataByType("sys_document_busi_type");
        return getDataTable(sysDictDataList);
    }

    @PostMapping("/documentDetailTypeList/{docType}")
    @ResponseBody
    public TableDataInfo documentDetailTypeList(@PathVariable("docType") String docType) {
        List<SysDictData> sysDictDataList = new ArrayList<>();
//        项目类
        if ("0".equals(docType)) {
            startPage();
            sysDictDataList = sysDictDataService.selectDictDataByType("sys_project_type");
        }
        return getDataTable(sysDictDataList);
    }

    @PostMapping("/selectSysApplyWorkflowList")
    @ResponseBody
    public TableDataInfo selectSysApplyWorkflowList(SysApplyIn sysApplyIn) {
        List<SysApplyWorkflow> list = sysApplyWorkflowService.selectSysApplyWorkflowList(sysApplyIn);
        return getDataTable(list);
    }

    /**
     * 删除档案入库申请
     */
//    @RequiresPermissions("applyIn:remove")
    @Log(title = "档案入库申请", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        String loginName = ShiroUtils.getLoginName();
        String[] idsArr = Convert.toStrArray(ids);
        for (String id : idsArr) {
            SysApplyIn sysApplyIn = sysApplyInService.selectSysApplyInById(Long.parseLong(id));
            if (sysApplyIn == null || !loginName.equals(sysApplyIn.getApplyUser())) {
                return AjaxResult.error("不可操作");
            }
        }
        AjaxResult res = sysApplyInService.deleteSysApplyInByIds(ids);
        return res;
    }

    private List<String> getUsers(String roleKey) {
        List<String> users = new ArrayList<>();
        SysRole r = new SysRole();
        r.setRoleKey(roleKey);
        List<SysRole> ros = roleMapper.selectRoleList(r);
        if (ros != null && ros.size() > 0) {
            SysUser userRoles = new SysUser();
            userRoles.setRoleId(ros.get(0).getRoleId());
            List<SysUser> us = userMapper.selectAllocatedList(userRoles);
            for (SysUser u : us) {
                users.add(u.getLoginName());
            }
        }
        return users;
    }


    @PostMapping("/selectSysApplyWorkflowProcess")
    @ResponseBody
    public TableDataInfo selectSysApplyWorkflowProcess(SysWorkflowProcess sysWorkflowProcess) {
        List<SysWorkflowProcess> listSort = new ArrayList<>();
        int index = 0;
        List<SysWorkflowProcess> list = sysWorkflowProcessMapper.selectSysWorkflowProcessList(sysWorkflowProcess);

        List<String> jls = getUsers("flgw");
        for (int i = 0; i < list.size(); i++) {
            SysWorkflowProcess pro = list.get(i);
            if (StringUtils.isNotEmpty(pro.getApplyUserName())) {
                if (pro.getApplyUserName().length() > 1) {
                    pro.setNameEndStr(pro.getApplyUserName().substring(pro.getApplyUserName().length() - 2));
                } else {
                    pro.setNameEndStr(pro.getApplyUserName());
                }
            }

            if ("0".equals(pro.getApplyStatu()) && pro.getId() != list.get(0).getId()) {
                pro.setApplyUserName(pro.getApplyUserName() + "(已同意)");
            }
            if (StringUtils.isNotEmpty(pro.getShowLable())) {
                String showLable = sysDictDataService.selectDictLabel("apply_statu", pro.getShowLable());
                if (StringUtils.isNotEmpty(showLable)) {
                    pro.setShowLable(showLable);
                }
            }
            if (jls.contains(pro.getApplyLoginName()) && "fw".equals(pro.getRemark2())) {
                listSort.add(pro);
                if (index == 0) {
                    index = i;
                }
            }

        }

        //对不带时间类型的集合进行排序
        List<SysWorkflowProcess> pros = new ArrayList<>();
        List<SysWorkflowProcess> listSortHasDate = new ArrayList<>();
        for (int i = 0; i < listSort.size(); i++) {
            SysWorkflowProcess pro = listSort.get(i);
            if (pro.getApplyTime() == null) {
                pros.add(pro);
            } else {
                listSortHasDate.add(pro);
            }
        }

        for (SysWorkflowProcess p : pros) {
            listSortHasDate.add(p);
        }
        for (int i = 0; i < listSortHasDate.size(); i++) {
            list.set(index, listSortHasDate.get(i));
            index++;
        }

        return getDataTable(list);
    }

    @GetMapping("/download/resource")
    public void download(String ids, Long applyId, String debtorName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String title = debtorName;
        //附件类型
        List<SysDictData> documentFileTypeDict = sysDictDataService.selectDictDataByType("document_file_type");
        List<SysDictData> companyList = sysDictDataService.selectDictDataByType("sys_company_name");
        String accessToken = configService.getWechatAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            throw new RuntimeException("获取accessToken失败");
        }
        if (StringUtils.isEmpty(ids)) {
            SysDocumentFile sysDocumentFile = new SysDocumentFile();
            sysDocumentFile.setApplyId(applyId);
            List<SysDocumentFile> sysDocumentFileList = sysDocumentFileService.selectSysDocumentFileDetailList(sysDocumentFile);
            for (SysDocumentFile sysDocumentFile1 : sysDocumentFileList) {
                ids = sysDocumentFile1.getDocumentId() + "," + ids;
            }
        }
        for (String string : ids.split(",")) {
            if (StringUtils.isNotEmpty(string)) {
                SysApplyIn sysApplyIn = sysApplyInService.selectSysApplyInById(applyId);
                SysDocumentFile sysDocumentFile = sysDocumentFileService.selectSysDocumentFileById(Long.valueOf(string));
                String fileType = StringUtils.isNotNull(getValue(documentFileTypeDict, sysDocumentFile.getFileType())) ? getValue(documentFileTypeDict, sysDocumentFile.getFileType()) : "";
                String companyName = getValue(companyList, sysApplyInService.selectSysApplyInById(sysDocumentFile.getApplyId()).getCompanyName());
                title = StringUtils.isEmpty(debtorName) ? companyName : debtorName;
                String userName = userMapper.selectUserByLoginName(sysApplyIn.getApplyUser()).getUserName();
                String applyTime = DateUtils.parseDateToStr("yyyy-MM-dd", sysApplyIn.getApplyTime());
                Long counts = StringUtils.isNotNull(sysDocumentFile.getCounts()) ? sysDocumentFile.getCounts() : Long.valueOf(0);
                Long pages = StringUtils.isNotNull(sysDocumentFile.getPages()) ? sysDocumentFile.getPages() : Long.valueOf(0);
                createQrCodeImg(Long.valueOf(string), sysDocumentFile.getFileName().replace(" ", "").replace("（", "(").replace("）", ")"), title, title + ";" + applyTime + ";" + userName + ";" + fileType + ";" + counts + "份     " + pages + "页;", accessToken);
            }
        }
        downloadFile(title, request, response);
    }

    @GetMapping("/download/resources")
    public void downloadForAppId(String applyIds, SysApplyIn sysApplyIn, String debtorName, String roleType, HttpServletRequest request, HttpServletResponse response) throws Exception {
        StringBuffer applyInIdsSb = new StringBuffer();
        //附件类型
        List<SysDictData> documentFileTypeDict = sysDictDataService.selectDictDataByType("document_file_type");
        //档案类型

        List<SysDictData> companyList = sysDictDataService.selectDictDataByType("sys_company_name");
        if (StringUtils.isEmpty(applyIds) || "null".equals(applyIds) || "undefined".equals(applyIds)) {
            List<SysApplyIn> list = sysApplyInService.selectSysApplyInDocByPNameDetailList(sysApplyIn);
            for (SysApplyIn sysApplyIn1 : list) {
                applyInIdsSb.append(sysApplyIn1.getApplyId()).append(",");
            }
        } else {
            for (String string : applyIds.split(",")) {
                applyInIdsSb.append(string).append(",");
            }
        }
        createAndDow(applyInIdsSb.toString(), debtorName, documentFileTypeDict, companyList, request, response);
    }

    public void createAndDow(String applyInIds, String debtorName, List<SysDictData> documentFileTypeDict, List<SysDictData> companyList, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String title = debtorName;
        String accessToken = configService.getWechatAccessToken();
        List<SysDocumentFile> sysDocumentFileList = sysDocumentFileService.selectDocListByApplyInIds(applyInIds);
        for (SysDocumentFile sysDocumentFile1 : sysDocumentFileList) {
            SysApplyIn sysApplyIn1 = sysApplyInService.selectSysApplyInById(sysDocumentFile1.getApplyId());
            String fileType = StringUtils.isNotNull(getValue(documentFileTypeDict, sysDocumentFile1.getFileType())) ? getValue(documentFileTypeDict, sysDocumentFile1.getFileType()) : "";
            String companyName = getValue(companyList, sysDocumentFile1.getCompanyName());
            title = StringUtils.isEmpty(debtorName) ? companyName : debtorName;
            String userName = sysDocumentFile1.getCreatorName();
            String applyTime = DateUtils.parseDateToStr("yyyy-MM-dd", sysApplyIn1.getApplyTime());
            Long counts = StringUtils.isNotNull(sysDocumentFile1.getCounts()) ? sysDocumentFile1.getCounts() : Long.valueOf(0);
            Long pages = StringUtils.isNotNull(sysDocumentFile1.getPages()) ? sysDocumentFile1.getPages() : Long.valueOf(0);
            createQrCodeImg(sysDocumentFile1.getDocumentId(), sysDocumentFile1.getFileName().replace(" ", "").replace("（", "(").replace("）", ")"), title, title + ";" + applyTime + ";" + userName + ";" + fileType + ";" + counts + "份     " + pages + "页;", accessToken);
        }
        downloadFile(title, request, response);
    }

    public String getValue(List<SysDictData> list, String valueName) {
        for (SysDictData sysDictData : list) {
            if (sysDictData.getDictValue().equals(valueName)) {
                valueName = sysDictData.getDictLabel();
            }
        }
        return valueName;
    }

    public void createQrCodeImg(Long scene, String fileName, String debtorName, String text, String accessToken) throws Exception {
        if (StringUtils.isEmpty(accessToken)) {
            throw new RuntimeException("获取accessToken失败");
        }
        JSONObject paramData = new JSONObject();
        paramData.put("scene", scene);
        paramData.put("url", "pages/lookInformation/index");
        WxQrCode.downloadMiniCode(accessToken, FileUploadUtils.getDefaultBaseDir(), StringUtils.replaceBlank(fileName + "-" + scene), debtorName, paramData.toString());
        String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String savePath = FileUploadUtils.getDefaultBaseDir() + File.separator + "files" + File.separator + nowday + File.separator + debtorName + File.separator + (fileName + "-" + scene) + ".png";
        WxImageTool.generateCode(savePath, savePath, text, 290, 300);
    }

    public void downloadFile(String debtorName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // 数据库资源地址
        String downloadPath = FileUploadUtils.getDefaultBaseDir() + File.separator + "files" + File.separator + nowday + File.separator + debtorName;
        ZipUtils.toZip(downloadPath, new FileOutputStream(new File(downloadPath + ".zip")), true);
        // 下载名称
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, debtorName + ".zip"));
        FileUtils.writeBytes(downloadPath + ".zip", response.getOutputStream());
        FileUtils.delFolder(new File(downloadPath));
        File file = new File(downloadPath + ".zip");
        if (file.getName().endsWith(".zip")) {
            file.delete();
        }
    }


    public void editFileName(Long appId) {
        SysDocumentFile sysDocumentFile = new SysDocumentFile();
        sysDocumentFile.setApplyId(appId);
        List<SysDocumentFile> sysDocumentFileList = sysDocumentFileService.selectSysDocumentFileList(sysDocumentFile);
        for (SysDocumentFile sysDocumentFile1 : sysDocumentFileList) {
            sysDocumentFile1.setFileName(sysDocumentFile1.getFileName().trim().replace(" ", "").replace("/", "-"));
            sysDocumentFileService.updateSysDocumentFile(sysDocumentFile1);
        }
    }

    @GetMapping("/selectSysApplyInByApplyUserNameAndTime")
    public void selectSysApplyInByApplyUserNameAndTime(SysApplyIn applyIn, HttpServletResponse response) throws IOException {
        List<SysApplyIn> list = sysApplyInService.selectSysApplyInListUser(applyIn);
        for (SysApplyIn sysApplyIn : list) {
            sysApplyIn.setApplyTypeToString(sysDictDataService.selectDictLabel("sys_file_apply_type", sysApplyIn.getApplyType()));
            sysApplyIn.setDocumentTypeToString(sysDictDataService.selectDictLabel("sys_document_busi_type", sysApplyIn.getDocumentType()));
            sysApplyIn.setCompanyNameToString(sysDictDataService.selectDictLabel("sys_company_name", sysApplyIn.getCompanyName()));
            sysApplyIn.setApproveStatuToString(sysDictDataService.selectDictLabel("apply_statu", sysApplyIn.getApproveStatu()));
        }

        EasyExcelUtil util = new EasyExcelUtil();

        List<SheetExcelData> sheetExcelDataList = new ArrayList<>();
        SheetExcelData<SysApplyIn> sysApplyInSheetExcelData = new SheetExcelData<>();
        sysApplyInSheetExcelData.setSheetName("基本信息");
        sysApplyInSheetExcelData.settClass(SysApplyIn.class);
        sysApplyInSheetExcelData.setDataList(list);
        sheetExcelDataList.add(sysApplyInSheetExcelData);

        //获取文档信息
        SysDocumentFile sysDocumentFile = new SysDocumentFile();
        sysDocumentFile.setApplyUserName(applyIn.getApplyUserName());
        sysDocumentFile.getParams().put("beginApplyTime", applyIn.getParams().get("beginApplyTime"));
        sysDocumentFile.getParams().put("endApplyTime", applyIn.getParams().get("endApplyTime"));
        List<SysDocumentFile> sysDocumentFileList = sysDocumentFileService.selectSysDocumentFileList(sysDocumentFile);
        for (SysDocumentFile sysDocumentFile1 : sysDocumentFileList) {
            sysDocumentFile1.setFileGetTypeToString(sysDictDataService.selectDictLabel("document_in_type", sysDocumentFile1.getFileGetType()));
            sysDocumentFile1.setDocumentGetTypeToString(sysDictDataService.selectDictLabel("busi_document_type", sysDocumentFile1.getDocumentGetType()));
            sysDocumentFile1.setDocumentStatuToString(sysDictDataService.selectDictLabel("document_in_status", sysDocumentFile1.getDocumentStatu()));
            sysDocumentFile1.setDailyDocumentTypeToString(sysDictDataService.selectDictLabel("sys_document_busi_type", sysDocumentFile1.getDailyDocumentType()));
            sysDocumentFile1.setDocumentLevelToString(sysDictDataService.selectDictLabel("document_level", sysDocumentFile1.getDocumentLevel()));
            sysDocumentFile1.setCompanyNameToString(sysDictDataService.selectDictLabel("sys_company_name", sysDocumentFile1.getCompanyName()));
            sysDocumentFile1.setDocumentTypeVal(sysDictDataService.selectDictLabel("sys_document_busi_type", sysDocumentFile1.getDocumentType()));
        }
        SheetExcelData<SysDocumentFile> sysDocumentFileSheetExcelData = new SheetExcelData<>();
        sysDocumentFileSheetExcelData.setSheetName("档案信息");
        sysDocumentFileSheetExcelData.settClass(SysDocumentFile.class);
        sysDocumentFileSheetExcelData.setDataList(sysDocumentFileList);
        sheetExcelDataList.add(sysDocumentFileSheetExcelData);

        util.exportSheetsExcel("出入库信息", sheetExcelDataList, response);
    }

}


