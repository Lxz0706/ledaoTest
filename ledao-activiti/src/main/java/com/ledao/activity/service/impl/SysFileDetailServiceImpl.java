package com.ledao.activity.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ledao.activity.dao.SysApplyIn;
import com.ledao.activity.dao.SysDocumentFile;
import com.ledao.activity.mapper.SysApplyInMapper;
import com.ledao.activity.mapper.SysDocumentFileMapper;
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
    @Autowired
    private SysDocumentFileMapper sysDocumentFileMapper;
    @Autowired
    private SysApplyInMapper sysApplyInMapper;

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
        List<SysFileDetail> fis =sysFileDetailMapper.selectSysFileDetailList(sysFileDetail);
        for (SysFileDetail f: fis ) {
            if (StringUtils.isNotEmpty(f.getFileName()) ){
                f.setFileName(f.getFileName().substring(f.getFileName().lastIndexOf("/")+1));
            }
        }
        return fis;
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
                sysFile.setDocumentFileId(sysFileDetail.getDocumentFileId());
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
            //更新用户信息
            SysDocumentFile ds = sysDocumentFileMapper.selectSysDocumentFileById(sysFileDetail.getDocumentFileId());
            SysApplyIn ap = sysApplyInMapper.selectSysApplyInById(ds.getApplyId());
            updateApplyIn(ap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return AjaxResult.success();
    }

    public int updateApplyIn(SysApplyIn ap){
        ap.setReviser(ShiroUtils.getLoginName());
        ap.setUpdateTime(DateUtils.getNowDate());
        return sysApplyInMapper.updateSysApplyIn(ap);
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
        long doId = 0L;
        for (String id: idArray) {
            SysFileDetail SysFileDetail = new SysFileDetail();
            SysFileDetail.setFileId(Long.parseLong(id));
            SysFileDetail f = sysFileDetailMapper.selectSysFileDetailById(Long.parseLong(id));
            String url = f.getFileUrl().replace(Constants.RESOURCE_PREFIX,"");
            doId = f.getDocumentFileId();
            FileUtils.deleteFile(Global.getProfile()+url);
        }
        SysDocumentFile ds = sysDocumentFileMapper.selectSysDocumentFileById(doId);
        SysApplyIn ap = sysApplyInMapper.selectSysApplyInById(ds.getApplyId());
        updateApplyIn(ap);
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
        SysFileDetail f = sysFileDetailMapper.selectSysFileDetailById(fileId);
        SysDocumentFile ds = sysDocumentFileMapper.selectSysDocumentFileById(f.getDocumentFileId());
        SysApplyIn ap = sysApplyInMapper.selectSysApplyInById(ds.getApplyId());
        updateApplyIn(ap);
        return sysFileDetailMapper.deleteSysFileDetailById(fileId);
    }
}
