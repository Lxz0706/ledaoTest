package com.ledao.system.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ledao.common.config.Global;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.file.FileUploadUtils;
import com.ledao.common.utils.file.FileUtils;
import com.ledao.system.dao.SysBgczzck;
import com.ledao.system.dao.SysProject;
import com.ledao.system.dao.SysProjectZck;
import com.ledao.system.mapper.SysBgczzckMapper;
import com.ledao.system.mapper.SysProjectMapper;
import com.ledao.system.mapper.SysProjectZckMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysUnderlyingDataMapper;
import com.ledao.system.dao.SysUnderlyingData;
import com.ledao.system.service.ISysUnderlyingDataService;
import com.ledao.common.core.text.Convert;
import org.springframework.web.multipart.MultipartFile;

/**
 * 底层资料Service业务层处理
 * 
 * @author lxz
 * @date 2021-09-07
 */
@Service
public class SysUnderlyingDataServiceImpl implements ISysUnderlyingDataService 
{
    @Autowired
    private SysUnderlyingDataMapper sysUnderlyingDataMapper;

    @Autowired
    private SysProjectMapper sysProjectMapper;

    @Autowired
    private SysBgczzckMapper sysBgczzckMapper;

    /**
     * 查询底层资料
     * 
     * @param fileId 底层资料ID
     * @return 底层资料
     */
    @Override
    public SysUnderlyingData selectSysUnderlyingDataById(Long fileId)
    {
        return sysUnderlyingDataMapper.selectSysUnderlyingDataById(fileId);
    }

    /**
     * 查询底层资料列表
     * 
     * @param sysUnderlyingData 底层资料
     * @return 底层资料
     */
    @Override
    public List<SysUnderlyingData> selectSysUnderlyingDataList(SysUnderlyingData sysUnderlyingData)
    {
        return sysUnderlyingDataMapper.selectSysUnderlyingDataList(sysUnderlyingData);
    }

    /**
     * 新增底层资料
     *
     * @param sysUnderlyingData 底层资料
     * @return 结果
     */
    @Override
    public AjaxResult insertSysUnderlyingData(SysUnderlyingData sysUnderlyingData, MultipartFile[] files) {
        List<MultipartFile> muFs = new ArrayList<>();
        for (MultipartFile f : files) {
            muFs.add(f);
            SysUnderlyingData sysFile = new SysUnderlyingData();
            sysFile.setFileName(f.getOriginalFilename());
            sysFile.setProjectType(sysUnderlyingData.getProjectType());
            sysFile.setProjectId(sysUnderlyingData.getProjectId());
            List<SysUnderlyingData> fs = sysUnderlyingDataMapper.selectSysUnderlyingDataList(sysFile);
            if (fs!=null && fs.size()>0){
                return AjaxResult.error("存在相同文件，请修改后重新上传");
            }
        }

        for (MultipartFile file : muFs) {
            try {
                //获取各类型名称及其子集
                SysUnderlyingData sysFile = new SysUnderlyingData();
                String baseDir = "";
                String filePath = Global.getUploadPathUnderLying();

                String fileName = FileUploadUtils.upload(filePath, file, true);
                sysFile.setFileUrl(fileName);

                sysFile.setFileName(file.getResource().getFilename());
                sysFile.setFileSize((double) file.getSize());
                sysFile.setCreateBy(sysUnderlyingData.getCreateBy());
                sysFile.setCreateTime(DateUtils.getNowDate());
                sysFile.setCreateor(sysUnderlyingData.getCreateBy());
                sysFile.setFileType(FileUploadUtils.getExtension(file));
                sysFile.setProjectId(sysUnderlyingData.getProjectId());
                sysFile.setProjectType(sysUnderlyingData.getProjectType());

                sysUnderlyingDataMapper.insertSysUnderlyingData(sysFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return AjaxResult.success();
    }

    /**
     * 修改底层资料
     * 
     * @param sysUnderlyingData 底层资料
     * @return 结果
     */
    @Override
    public int updateSysUnderlyingData(SysUnderlyingData sysUnderlyingData)
    {
        sysUnderlyingData.setUpdateTime(DateUtils.getNowDate());
        return sysUnderlyingDataMapper.updateSysUnderlyingData(sysUnderlyingData);
    }

    /**
     * 删除底层资料对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysUnderlyingDataByIds(String ids)
    {
        for (String id : ids.split(",")) {
            SysUnderlyingData sysUnderly = sysUnderlyingDataMapper.selectSysUnderlyingDataById(Long.valueOf(id));
            if (sysUnderly!=null){
                String filePath = Global.getUploadPathUnderLying();
                if (StringUtils.isNotEmpty(sysUnderly.getFileUrl()) && sysUnderly.getFileUrl().contains("/profile/upload/underLying")){
                    String url = sysUnderly.getFileUrl().split("/profile/upload/underLying")[1];
                    FileUtils.deleteFile(filePath+url);
                }
            }
        }
        return sysUnderlyingDataMapper.deleteSysUnderlyingDataByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除底层资料信息
     * 
     * @param fileId 底层资料ID
     * @return 结果
     */
    @Override
    public int deleteSysUnderlyingDataById(Long fileId)
    {
        return sysUnderlyingDataMapper.deleteSysUnderlyingDataById(fileId);
    }

    @Override
    public List<SysUnderlyingData> selectSysUnderlyingDataByPid(Long projectId,Long projectType) {
        return sysUnderlyingDataMapper.selectSysUnderlyingDataByPid(projectId, projectType);
    }

    @Override
    public List<SysUnderlyingData> selectSysUnderlyingProDataList(SysUnderlyingData sysUnderlyingData) {
        List<SysUnderlyingData> sly = sysUnderlyingDataMapper.selectSysUnderlyingProDataList(sysUnderlyingData);
        for (SysUnderlyingData s: sly) {
            //投后
            if (0==s.getProjectType()){
                SysProject pro = sysProjectMapper.selectSysProjectById(s.getProjectId());
                s.setProName(pro!=null?pro.getProjectName():"");
            }else{
                SysBgczzck pro = sysBgczzckMapper.selectSysBgczzckById(s.getProjectId());
                s.setProName(pro!=null?pro.getProjectName():"");
            }
        }
        return sly;
    }

    @Override
    public List<SysUnderlyingData> selectSysUnderlyingNoLikeDataList(SysUnderlyingData sysUnderlyingData) {
        List<SysUnderlyingData> sly = sysUnderlyingDataMapper.selectSysUnderlyingNoLikeDataList(sysUnderlyingData);
        return sly;
    }

    /**
     * 投后项目
     * @param sysUnderlyingData
     * @return
     */
    @Override
    public List<SysUnderlyingData> selectSysUnderlyingNoLikeDetailDataList(SysUnderlyingData sysUnderlyingData) {
        List<SysUnderlyingData> sly = sysUnderlyingDataMapper.selectSysUnderlyingNoLikeDetailDataList(sysUnderlyingData);
        for (SysUnderlyingData s: sly) {
            //投后
            if (0==s.getProjectType()){
                SysProject pro = sysProjectMapper.selectSysProjectById(s.getProjectId());
                s.setProName(pro!=null?pro.getProjectName():"");
            }else{
                SysBgczzck pro = sysBgczzckMapper.selectSysBgczzckById(s.getProjectId());
                s.setProName(pro!=null?pro.getProjectName():"");
            }
        }
        return sly;
    }

    /**
     * 大型单体项目
     * @param sysUnderlyingData
     * @return
     */
    @Override
    public List<SysUnderlyingData> selectSysUnderlyingProList(SysUnderlyingData sysUnderlyingData) {
        List<SysUnderlyingData> sly = sysUnderlyingDataMapper.selectSysUnderlyingProList(sysUnderlyingData);
        return sly;
    }

    @Override
    public List<SysUnderlyingData> selectSysUnderlyingDebtList(SysUnderlyingData sysUnderlyingData) {
        List<SysUnderlyingData> sly = sysUnderlyingDataMapper.selectSysUnderlyingDebtList(sysUnderlyingData);
        return sly;
    }
}
