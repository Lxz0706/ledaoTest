package com.ledao.web.controller.openTime;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.system.dao.SysNotice;
import com.ledao.system.dao.SysProject;
import com.ledao.system.dao.SysUser;
import com.ledao.system.service.ISysNoticeService;
import com.ledao.system.service.ISysProjectService;
import com.ledao.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void openTime() {
        SysProject sysProject = new SysProject();
        List<SysProject> list = sysProjectService.selectProject(sysProject);
        for (SysProject sysProject1 : list) {
            //获取项目经理和其上级主管
            StringBuffer sb = new StringBuffer();
            StringBuffer nameSb = new StringBuffer();
            if (StringUtils.isNotEmpty(sysProject1.getProjectManager())) {
                SysUser sysUser = sysUserService.selectUserByUserName(sysProject1.getProjectManager());
                sb.append(sysUser.getUserId());
                nameSb.append(sysUser.getUserName());
                if (StringUtils.isNotNull(sysUser.getDirectorId())) {
                    SysUser sysUser1 = sysUserService.selectUserById(sysUser.getDirectorId());
                    sb.append(",").append(sysUser1.getUserId());
                    nameSb.append(",").append(sysUser1.getUserName());
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
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String json = "[\"{\\\"type\\\":\\\"请问\\\",\\\"value\\\":\\\"2021-08-04\\\"}\",\"{\\\"type\\\":\\\"二审\\\",\\\"value\\\":\\\"2021-08-13\\\"}\",\"{\\\"type\\\":\\\"再审\\\",\\\"value\\\":\\\"2021-08-17\\\"}\"]";
        StringBuffer sb = new StringBuffer();
        StringBuffer sb1 = new StringBuffer();
        //需要使用的JSON的parseArray方法，将jsonArray解析为object类型的数组
        JSONArray objects = JSON.parseArray(json);
        for (int i = 0; i < objects.size(); i++) {
            System.out.print(objects.get(i));
            //通过数组下标取到object，使用强转转为JSONObject，之后进行操作
            JSONObject object = JSON.parseObject(objects.get(i).toString());
            sb.append(object.getString("type")).append(",");
            sb1.append(object.getString("value")).append(",");
        }
        //这个方法是去掉stringbuffer的最后一位的字符
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb + "=======" + sb1);
    }
}
