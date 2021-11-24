package com.ledao.web.controller.openTime;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ledao.activity.service.ISysApplyWorkflowService;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.system.dao.*;
import com.ledao.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @ClassName openTimeTask
 * @Description TODO
 * @Author 87852
 * @Date 2021/8/6 14:33
 * @Version 1.0
 */
@Component("openTimeTask")
public class OpenTimeTask {
    @Autowired
    private ISysProjectService sysProjectService;

    @Autowired
    private ISysNoticeService sysNoticeService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysApplyWorkflowService iSysApplyWorkflowService;

    @Autowired
    private ISysMonomerLawService sysMonomerLawService;

    @Autowired
    private ISysBgczzckService sysBgczzckService;

    public void openTime() {
        //投后部小程序消息推送
        thProject();

        //投资部小程序消息推送
        tzProject();
    }

    public void thProject() {
        SysProject sysProject = new SysProject();
        List<SysProject> list = sysProjectService.selectSysProjectList(sysProject);
        for (SysProject sysProject1 : list) {
            StringBuffer sb = new StringBuffer();
            StringBuffer nameSb = new StringBuffer();
            List<SysUser> userList = new ArrayList<>();

            //获取法务部主管
            SysUser sysUser = new SysUser();
            sysUser.setRoleKey("fkbjl");
            List<SysUser> sysUserList = sysUserService.selectUserByRoleKey(sysUser);
            for (SysUser sysUser1 : sysUserList) {
                sb.append(sysUser1.getUserId());
                nameSb.append(sysUser1.getUserName());
            }

            //获取风控部普通员工
            SysUser sysUser1 = new SysUser();
            sysUser1.setRoleKey("fkbCommon");
            List<SysUser> fkbptList = sysUserService.selectUserByRoleKey(sysUser1);
            for (SysUser sysUser2 : fkbptList) {
                sb.append(",").append(sysUser2.getUserId());
                nameSb.append(",").append(sysUser2.getUserName());
            }

            //获取项目经理和其上级主管
            if (StringUtils.isNotEmpty(sysProject1.getProjectManagerId())) {
                for (String string : sysProject1.getProjectManagerId().split(",")) {
                    if (StringUtils.isNotEmpty(string)) {
                        SysUser sysUser2 = sysUserService.selectUserById(Long.valueOf(string));
                        userList.add(sysUser2);
                        sb.append(",").append(sysUser2.getUserId());
                        nameSb.append(",").append(sysUser2.getUserName());
                        if (StringUtils.isNotNull(sysUser2.getDirectorId())) {
                            SysUser sysUser3 = sysUserService.selectUserById(sysUser2.getDirectorId());
                            userList.add(sysUser3);
                            sb.append(",").append(sysUser3.getUserId());
                            nameSb.append(",").append(sysUser3.getUserName());
                        }
                    }
                }
            }

            if (StringUtils.isNotEmpty(sysProject1.getOpenTime())) {
                //需要使用的JSON的parseArray方法，将jsonArray解析为object类型的数组
                JSONArray objects = JSON.parseArray(sysProject1.getOpenTime());
                for (int i = 0; i < objects.size(); i++) {
                    //通过数组下标取到object，使用强转转为JSONObject，之后进行操作
                    JSONObject object = JSON.parseObject(objects.get(i).toString());
                    int differentDay = 0;
                    if (DateUtils.differentDays(new Date(), DateUtils.parseDate(object.getString("value"))) == 10
                            || DateUtils.differentDays(new Date(), DateUtils.parseDate(object.getString("value"))) == 3) {
                        if (DateUtils.differentDays(new Date(), DateUtils.parseDate(object.getString("value"))) == 10) {
                            differentDay = 10;
                        } else if (DateUtils.differentDays(new Date(), DateUtils.parseDate(object.getString("value"))) == 3) {
                            differentDay = 3;
                        }
                        SysNotice sysNotice = new SysNotice();
                        sysNotice.setNoticeTitle(sysProject1.getProjectName() + "距离" + object.getString("type") + "开庭，还剩" + differentDay + "天");
                        sysNotice.setNoticeType("3");
                        sysNotice.setReceiverId(StringUtils.removeSameString(sb.toString(), ","));
                        sysNotice.setReceiver(StringUtils.removeSameString(nameSb.toString(), ","));
                        sysNotice.setShareDeptAndUser(StringUtils.removeSameString(nameSb.toString(), ","));
                        sysNotice.setCreateBy(sysProject1.getCreateBy());
                        sysNoticeService.insertNotice(sysNotice);

                        //小程序消息推送
                        System.out.println("-------------投后部开庭时间推送----------");
                        List<SysUser> us = new ArrayList<>();
                        us.addAll(sysUserList);
                        us.addAll(userList);
                        us.addAll(fkbptList);
                        Map<String, String> parmStr = new HashMap<>();
                        parmStr.put("first", "您有一个法务工作提醒");
                        parmStr.put("word1", sysNotice.getNoticeTitle());
                        iSysApplyWorkflowService.sendTaskMsg(StringUtils.removeDuplicate(us), parmStr);
                    }
                }
            }
        }
    }

    public void tzProject() {
        SysMonomerLaw sysMonomerLaw = new SysMonomerLaw();
        List<SysMonomerLaw> sysMonomerLawList = sysMonomerLawService.selectSysMonomerLawList(sysMonomerLaw);
        for (SysMonomerLaw sysMonomerLaw1 : sysMonomerLawList) {
            //获取项目经理和其上级主管
            StringBuffer sb = new StringBuffer();
            StringBuffer nameSb = new StringBuffer();
            List<SysUser> userList = new ArrayList<>();
            SysBgczzck sysBgczzck = sysBgczzckService.selectSysBgczzckById(sysMonomerLaw1.getProjectId());
            //获取项目经理和其上级主管
            /*if (StringUtils.isNotEmpty(sysProject1.getProjectManager())) {
                SysUser sysUser = sysUserService.selectUserByUserName(sysProject1.getProjectManager());
                userList.add(sysUser);
                sb.append(sysUser.getUserId());
                nameSb.append(sysUser.getUserName());
                if (StringUtils.isNotNull(sysUser.getDirectorId())) {
                    SysUser sysUser1 = sysUserService.selectUserById(sysUser.getDirectorId());
                    userList.add(sysUser1);
                    sb.append(",").append(sysUser1.getUserId());
                    nameSb.append(",").append(sysUser1.getUserName());
                }
            }*/

            //获取法务部主管
            SysUser sysUser = new SysUser();
            sysUser.setRoleKey("fkbjl");
            List<SysUser> sysUserList = sysUserService.selectUserByRoleKey(sysUser);
            for (SysUser sysUser1 : sysUserList) {
                sb.append(sysUser1.getUserId()).append(",");
                nameSb.append(sysUser1.getUserName()).append(",");
            }


            //获取风控部普通员工
            SysUser sysUser1 = new SysUser();
            sysUser1.setRoleKey("fkbCommon");
            List<SysUser> fkbptList = sysUserService.selectUserByRoleKey(sysUser1);
            for (SysUser sysUser2 : fkbptList) {
                sb.append(sysUser2.getUserId()).append(",");
                nameSb.append(sysUser2.getUserName()).append(",");
            }

            //获取并购重组经理
            SysUser sysUser2 = new SysUser();
            sysUser2.setRoleKey("bgczManager");
            List<SysUser> bgczManagerList = sysUserService.selectUserByRoleKey(sysUser2);
            for (SysUser sysUser3 : bgczManagerList) {
                sb.append(sysUser3.getUserId()).append(",");
                nameSb.append(sysUser3.getUserName()).append(",");
            }

            if (StringUtils.isNotEmpty(sysMonomerLaw1.getOpenTime())) {
                //需要使用的JSON的parseArray方法，将jsonArray解析为object类型的数组
                JSONArray objects = JSON.parseArray(sysMonomerLaw1.getOpenTime());
                for (int i = 0; i < objects.size(); i++) {
                    //通过数组下标取到object，使用强转转为JSONObject，之后进行操作
                    JSONObject object = JSON.parseObject(objects.get(i).toString());
                    int differentDay = 0;
                    if (DateUtils.differentDays(new Date(), DateUtils.parseDate(object.getString("value"))) == 10
                            || DateUtils.differentDays(new Date(), DateUtils.parseDate(object.getString("value"))) == 3) {
                        if (DateUtils.differentDays(new Date(), DateUtils.parseDate(object.getString("value"))) == 10) {
                            differentDay = 10;
                        } else if (DateUtils.differentDays(new Date(), DateUtils.parseDate(object.getString("value"))) == 3) {
                            differentDay = 3;
                        }
                        SysNotice sysNotice = new SysNotice();
                        sysNotice.setNoticeTitle(sysBgczzck.getProjectName() + "距离" + object.getString("type") + "开庭，还剩" + differentDay + "天");
                        sysNotice.setNoticeType("3");
                        sysNotice.setReceiverId(StringUtils.removeSameString(sb.toString(), ","));
                        sysNotice.setReceiver(StringUtils.removeSameString(nameSb.toString(), ","));
                        sysNotice.setShareDeptAndUser(StringUtils.removeSameString(nameSb.toString(), ","));
                        sysNotice.setCreateBy(sysMonomerLaw1.getCreateBy());
                        sysNoticeService.insertNotice(sysNotice);

                        //小程序消息推送
                        try {
                            System.out.println("-------------大型单体项目开庭时间推送----------");
                            List<SysUser> us = new ArrayList<>();
                            us.addAll(sysUserList);
                            us.addAll(fkbptList);
                            us.addAll(bgczManagerList);
                            Map<String, String> parmStr = new HashMap<>();
                            parmStr.put("first", "您有一个法务工作提醒");
                            parmStr.put("word1", sysNotice.getNoticeTitle());
                            iSysApplyWorkflowService.sendTaskMsg(StringUtils.removeDuplicate(us), parmStr);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
