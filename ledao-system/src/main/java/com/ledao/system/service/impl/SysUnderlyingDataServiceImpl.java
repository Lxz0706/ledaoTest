package com.ledao.system.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ledao.common.config.Global;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.file.FileUploadUtils;
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
            SysUnderlyingData sysFile = new SysUnderlyingData();
            String fileName = file.getOriginalFilename();
            sysFile.setFileName(fileName);
            sysFile.setFileSize((double) file.getSize());
            sysFile.setCreateBy(sysUnderlyingData.getCreateBy());
            sysFile.setCreateTime(DateUtils.getNowDate());
            sysFile.setCreateor(sysUnderlyingData.getCreateBy());
            sysFile.setFileType(FileUploadUtils.getExtension(file));
            sysFile.setProjectId(sysUnderlyingData.getProjectId());
            sysFile.setProjectType(sysUnderlyingData.getProjectType());
            //获取各类型名称及其子集
            String baseDir = "";
            try {
                String avatar = FileUploadUtils.upload(Global.getProfile() + "/underlyingdata" + baseDir, file, false);
                sysFile.setFileUrl(avatar);
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
        return sysUnderlyingDataMapper.selectSysUnderlyingProDataList(sysUnderlyingData);
    }
}
