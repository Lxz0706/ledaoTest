package com.ledao.activity.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ledao.common.config.Global;
import com.ledao.common.constant.Constants;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.file.FileUploadUtils;
import com.ledao.common.utils.file.FileUtils;
import com.ledao.framework.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.activity.mapper.SysFileDetailMapper;
import com.ledao.activity.dao.SysFileDetail;
import com.ledao.activity.service.ISysFileDetailService;
import com.ledao.common.core.text.Convert;
import org.springframework.web.multipart.MultipartFile;

/**
 * 档案详情Service业务层处理
 * 
 * @author lxz
 * @date 2021-08-04
 */
@Service
public class SysFileDetailServiceImpl implements ISysFileDetailService 
{
    @Autowired
    private SysFileDetailMapper sysFileDetailMapper;

    /**
     * 查询档案详情
     * 
     * @param fileId 档案详情ID
     * @return 档案详情
     */
    @Override
    public SysFileDetail selectSysFileDetailById(Long fileId)
    {
        return sysFileDetailMapper.selectSysFileDetailById(fileId);
    }

    /**
     * 查询档案详情列表
     * 
     * @param sysFileDetail 档案详情
     * @return 档案详情
     */
    @Override
    public List<SysFileDetail> selectSysFileDetailList(SysFileDetail sysFileDetail)
    {
        return sysFileDetailMapper.selectSysFileDetailList(sysFileDetail);
    }

    /**
     * 新增档案详情
     * 
     * @param sysFileDetail 档案详情
     * @return 结果
     */
    @Override
    public AjaxResult insertSysFileDetail(SysFileDetail sysFileDetail, MultipartFile[] files)
    {
        try {
            boolean flag = true;
            List<MultipartFile> muFs = new ArrayList<>();
            for (MultipartFile f : files) {
                muFs.add(f);
                SysFileDetail sysFile = new SysFileDetail();
                sysFile.setFileName(f.getResource().getFilename());
                List<SysFileDetail> fs = sysFileDetailMapper.selectSysFileDetailList(sysFile);
                if (fs!=null && fs.size()>0){
                    return AjaxResult.error("存在相同文件，请修改后重新上传");
                }
            }
            for (MultipartFile file : muFs) {
                // 上传文件路径
                String filePath = Global.getUploadPath();
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, file, true);
                String url = filePath + fileName;
                sysFileDetail.setFileUrl(fileName);
                sysFileDetail.setCreateBy(ShiroUtils.getLoginName());
                sysFileDetail.setFileName(file.getResource().getFilename());
                //            sysFileDetail.setFileUrl(fileName.split("/document")[1]);
                sysFileDetail.setCreateTime(new Date());
                sysFileDetail.setFileType(FileUploadUtils.getExtension(file));
                sysFileDetailMapper.insertSysFileDetail(sysFileDetail);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return AjaxResult.success();
    }

    /**
     * 修改档案详情
     * 
     * @param sysFileDetail 档案详情
     * @return 结果
     */
    @Override
    public int updateSysFileDetail(SysFileDetail sysFileDetail)
    {
        sysFileDetail.setUpdateTime(DateUtils.getNowDate());
        return sysFileDetailMapper.updateSysFileDetail(sysFileDetail);
    }

    /**
     * 删除档案详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysFileDetailByIds(String ids)
    {
        String[] idArray = Convert.toStrArray(ids);
        for (String id: idArray) {
            SysFileDetail SysFileDetail = new SysFileDetail();
            SysFileDetail.setFileId(Long.parseLong(id));
            SysFileDetail f = sysFileDetailMapper.selectSysFileDetailById(Long.parseLong(id));
            String url = f.getFileUrl().replace(Constants.RESOURCE_PREFIX,"");
            FileUtils.deleteFile(Global.getProfile()+url);
        }
        return sysFileDetailMapper.deleteSysFileDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除档案详情信息
     * 
     * @param fileId 档案详情ID
     * @return 结果
     */
    @Override
    public int deleteSysFileDetailById(Long fileId)
    {
        return sysFileDetailMapper.deleteSysFileDetailById(fileId);
    }
}
