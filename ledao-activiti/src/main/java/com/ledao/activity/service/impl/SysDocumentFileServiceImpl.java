package com.ledao.activity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.itextpdf.text.log.SysoLogger;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.file.FileUploadUtils;
import com.ledao.common.utils.file.FileUtils;
import com.ledao.framework.util.ShiroUtils;
import com.ledao.system.dao.SysUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ledao.activity.mapper.SysDocumentFileMapper;
import com.ledao.activity.mapper.SysFileDetailMapper;
import com.ledao.activity.dao.SysDocumentFile;
import com.ledao.activity.dao.SysFileDetail;
import com.ledao.activity.service.ISysDocumentFileService;
import com.ledao.common.config.Global;
import com.ledao.common.core.text.Convert;

/**
 * 档案Service业务层处理
 * 
 * @author yangwenxi
 * @date 2021-08-04
 */
@Service
@Transactional
public class SysDocumentFileServiceImpl implements ISysDocumentFileService {
	@Autowired
	private SysDocumentFileMapper sysDocumentFileMapper;
	@Autowired
	private SysFileDetailMapper sysFileDetailMapper;
	@Autowired
	private SysFileDetailMapper fileDetailMapper;

	/**
	 * 查询档案
	 * 
	 * @param documentId 档案ID
	 * @return 档案
	 */
	@Override
	public SysDocumentFile selectSysDocumentFileById(Long documentId) {
		return sysDocumentFileMapper.selectSysDocumentFileById(documentId);
	}

	/**
	 * 查询档案列表
	 * 
	 * @param sysDocumentFile 档案
	 * @return 档案
	 */
	@Override
	public List<SysDocumentFile> selectSysDocumentFileList(SysDocumentFile sysDocumentFile) {
		List<SysDocumentFile> documentFiles = sysDocumentFileMapper.selectSysDocumentFileList(sysDocumentFile);
		for (SysDocumentFile doc : documentFiles) {
			SysFileDetail detail = new SysFileDetail();
			detail.setDocumentFileId(doc.getDocumentId());
			List<SysFileDetail> details = fileDetailMapper.selectSysFileDetailList(detail);
			doc.setFileDetails(details);
		}
		return documentFiles;
	}

	/**
	 * 新增档案
	 * 
	 * @param sysDocumentFile 档案
	 * @return 结果
	 */
	@Override
	public int insertSysDocumentFile(SysDocumentFile sysDocumentFile, MultipartFile[] files) {
		sysDocumentFile.setCreateTime(DateUtils.getNowDate());
		sysDocumentFile.setCreateBy(ShiroUtils.getLoginName());
		sysDocumentFileMapper.insertSysDocumentFile(sysDocumentFile);
		SysUser currentUser = ShiroUtils.getSysUser();
		String loginUser = currentUser.getLoginName();
		for (MultipartFile file : files) {
			try {
				// 上传文件路径
				String filePath = Global.getUploadPath() + "/document";
				// 上传并返回新文件名称
				String fileName = FileUploadUtils.upload(filePath, file, true);
				String url = filePath + fileName;
				SysFileDetail fileDetail = new SysFileDetail();
				fileDetail.setCreateBy(loginUser);
				fileDetail.setFileName(file.getResource().getFilename());
				fileDetail.setFileUrl(fileName.split("/document")[1]);
				fileDetail.setCreateTime(new Date());
				fileDetail.setDocumentFileId(sysDocumentFile.getDocumentId());
				sysFileDetailMapper.insertSysFileDetail(fileDetail);

			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
		return 0;
	}

	/**
	 * 修改档案
	 * 
	 * @param sysDocumentFile 档案
	 * @return 结果
	 */
	@Override
	public int updateSysDocumentFile(SysDocumentFile sysDocumentFile) {
		sysDocumentFile.setUpdateTime(DateUtils.getNowDate());
		return sysDocumentFileMapper.updateSysDocumentFile(sysDocumentFile);
	}

	/**
	 * 删除档案对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteSysDocumentFileByIds(String ids) {
		String[] idArray = Convert.toStrArray(ids);
		for (String id: idArray) {
			SysFileDetail SysFileDetail = new SysFileDetail();
			SysFileDetail.setDocumentFileId(Long.parseLong(id));
			List<SysFileDetail> files = fileDetailMapper.selectSysFileDetailList(SysFileDetail);
			for (SysFileDetail f : files){
				System.out.println(Global.getUploadPath()+f.getFileUrl());
				FileUtils.deleteFile(Global.getUploadPath()+"/document"+f.getFileUrl());
				sysFileDetailMapper.deleteSysFileDetailById(f.getFileId());
			}
		}
		return sysDocumentFileMapper.deleteSysDocumentFileByIds(idArray);
	}

	/**
	 * 删除档案信息
	 * 
	 * @param documentId 档案ID
	 * @return 结果
	 */
	@Override
	public int deleteSysDocumentFileById(Long documentId) {
		return sysDocumentFileMapper.deleteSysDocumentFileById(documentId);
	}
}
