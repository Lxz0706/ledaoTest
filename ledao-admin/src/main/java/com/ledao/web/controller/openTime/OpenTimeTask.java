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
import com.ledao.system.dao.SysMonomerLaw;
import com.ledao.system.dao.SysNotice;
import com.ledao.system.dao.SysProject;
import com.ledao.system.dao.SysUser;
import com.ledao.system.service.ISysMonomerLawService;
import com.ledao.system.service.ISysNoticeService;
import com.ledao.system.service.ISysProjectService;
import com.ledao.system.service.ISysUserService;
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

    public void openTime() {
        //投后部小程序消息推送
        thProject();

        //投资部小程序消息推送
        tzProject();
    }

    public void thProject() {
        SysProject sysProject = new SysProject();
        List<SysProject> list = sysProjectService.selectSysProjectList(sysProject);
        System.out.println("投后部项目开庭时间个数：======="+list.size());
        for (SysProject sysProject1 : list) {
            //获取项目经理和其上级主管
            StringBuffer sb = new StringBuffer();
            StringBuffer nameSb = new StringBuffer();
            List<SysUser> userList = new ArrayList<>();
            if (StringUtils.isNotEmpty(sysProject1.getProjectManager())) {
                SysUser sysUser = sysUserService.selectUserByUserName(sysProject1.getProjectManager());
               if(StringUtils.isNotNull(sysUser)){
                   userList.add(sysUser);
                   sb.append(sysUser.getUserId());
                   nameSb.append(sysUser.getUserName());
                   if (StringUtils.isNotNull(sysUser.getDirectorId())) {
                       SysUser sysUser1 = sysUserService.selectUserById(sysUser.getDirectorId());
                       userList.add(sysUser1);
                       sb.append(",").append(sysUser1.getUserId());
                       nameSb.append(",").append(sysUser1.getUserName());
                   }
                }
            }

            //获取法务部主管
            SysUser sysUser = new SysUser();
            sysUser.setRoleKey("fkbjl");
            List<SysUser> sysUserList = sysUserService.selectUserByRoleKey(sysUser);
            for (SysUser sysUser1 : sysUserList) {
                sb.append(",").append(sysUser1.getUserId());
                nameSb.append(",").append(sysUser1.getUserName());
            }

            if (StringUtils.isNotEmpty(sysProject1.getOpenTime())) {
                //需要使用的JSON的parseArray方法，将jsonArray解析为object类型的数组
                JSONArray objects = JSON.parseArray(sysProject1.getOpenTime());
                for (int i = 0; i < objects.size(); i++) {
                    //通过数组下标取到object，使用强转转为JSONObject，之后进行操作
                    JSONObject object = JSON.parseObject(objects.get(i).toString());
                    int differentDay = 0;
                    if (DateUtils.differentDays(DateUtils.parseDate(object.getString("value")), new Date()) == 10
                            || DateUtils.differentDays(DateUtils.parseDate(object.getString("value")), new Date()) == 3) {
                        if (DateUtils.differentDays(DateUtils.parseDate(object.getString("value")), new Date()) == 10) {
                            differentDay = 10;
                        } else if (DateUtils.differentDays(DateUtils.parseDate(object.getString("value")), new Date()) == 3) {
                            differentDay = 3;
                        }
                        SysNotice sysNotice = new SysNotice();
                        sysNotice.setNoticeTitle(sysProject1.getProjectName() + "距离" + object.getString("type") + "开庭，还剩" + differentDay + "天");
                        sysNotice.setNoticeType("3");
                        sysNotice.setReceiverId(sb.toString());
                        sysNotice.setReceiver(nameSb.toString());
                        sysNotice.setShareDeptAndUser(nameSb.toString());
                        sysNotice.setCreateBy(sysProject1.getCreateBy());
                        sysNoticeService.insertNotice(sysNotice);

                        //小程序消息推送
                        System.out.println("-------------投后部开庭时间推送----------");
                        List<SysUser> us = new ArrayList<>();
                        us.addAll(sysUserList);
                        us.addAll(userList);
                        Map<String, String> parmStr = new HashMap<>();
                        parmStr.put("first", "您有一个法务工作提醒");
                        parmStr.put("word1", sysNotice.getNoticeTitle());
                        iSysApplyWorkflowService.sendTaskMsg(us, parmStr);
                    }
                }
            }
        }
    }

    public void tzProject() {
        SysMonomerLaw sysMonomerLaw = new SysMonomerLaw();
        List<SysMonomerLaw> sysMonomerLawList = sysMonomerLawService.selectSysMonomerLawList(sysMonomerLaw);
        System.out.println("大型单体项目开庭时间个数：======="+sysMonomerLawList.size());
        for (SysMonomerLaw sysMonomerLaw1 : sysMonomerLawList) {
            //获取项目经理和其上级主管
            StringBuffer sb = new StringBuffer();
            StringBuffer nameSb = new StringBuffer();
            List<SysUser> userList = new ArrayList<>();
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
                sb.append(",").append(sysUser1.getUserId());
                nameSb.append(",").append(sysUser1.getUserName());
            }

            if (StringUtils.isNotEmpty(sysMonomerLaw1.getOpenTime())) {
                //需要使用的JSON的parseArray方法，将jsonArray解析为object类型的数组
                JSONArray objects = JSON.parseArray(sysMonomerLaw1.getOpenTime());
                for (int i = 0; i < objects.size(); i++) {
                    //通过数组下标取到object，使用强转转为JSONObject，之后进行操作
                    JSONObject object = JSON.parseObject(objects.get(i).toString());
                    int differentDay = 0;
                    if (DateUtils.differentDays(DateUtils.parseDate(object.getString("value")), new Date()) == 10
                            || DateUtils.differentDays(DateUtils.parseDate(object.getString("value")), new Date()) == 3) {
                        if (DateUtils.differentDays(DateUtils.parseDate(object.getString("value")), new Date()) == 10) {
                            differentDay = 10;
                        } else if (DateUtils.differentDays(DateUtils.parseDate(object.getString("value")), new Date()) == 3) {
                            differentDay = 3;
                        }
                        SysNotice sysNotice = new SysNotice();
                        sysNotice.setNoticeTitle(sysMonomerLaw1.getProjectName() + "距离" + object.getString("type") + "开庭，还剩" + differentDay + "天");
                        sysNotice.setNoticeType("3");
                        sysNotice.setReceiverId(sb.toString());
                        sysNotice.setReceiver(nameSb.toString());
                        sysNotice.setShareDeptAndUser(nameSb.toString());
                        sysNotice.setCreateBy(sysMonomerLaw1.getCreateBy());
                        sysNoticeService.insertNotice(sysNotice);

                        //小程序消息推送
                        try {
                            System.out.println("-------------大型单体项目开庭时间推送----------");
                            List<SysUser> us = new ArrayList<>();
                            us.addAll(sysUserList);
                            //us.addAll(userList);
                            Map<String, String> parmStr = new HashMap<>();
                            parmStr.put("first", "您有一个法务工作提醒");
                            parmStr.put("word1", sysNotice.getNoticeTitle());
                            iSysApplyWorkflowService.sendTaskMsg(us, parmStr);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
