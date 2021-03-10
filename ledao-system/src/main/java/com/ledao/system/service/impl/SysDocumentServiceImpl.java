package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysDocumentMapper;
import com.ledao.system.dao.SysDocument;
import com.ledao.system.service.ISysDocumentService;
import com.ledao.common.core.text.Convert;

/**
 * 文件管理Service业务层处理
 *
 * @author lxz
 * @date 2021-02-23
 */
@Service
public class SysDocumentServiceImpl implements ISysDocumentService {
    @Autowired
    private SysDocumentMapper sysDocumentMapper;

    /**
     * 查询文件管理
     *
     * @param fileId 文件管理ID
     * @return 文件管理
     */
    @Override
    public SysDocument selectSysDocumentById(Long fileId) {
        return sysDocumentMapper.selectSysDocumentById(fileId);
    }

    /**
     * 查询文件管理列表
     *
     * @param sysDocument 文件管理
     * @return 文件管理
     */
    @Override
    public List<SysDocument> selectSysDocumentList(SysDocument sysDocument) {
        return sysDocumentMapper.selectSysDocumentList(sysDocument);
    }

    /**
     * 新增文件管理
     *
     * @param sysDocument 文件管理
     * @return 结果
     */
    @Override
    public int insertSysDocument(SysDocument sysDocument) {
        sysDocument.setCreateTime(DateUtils.getNowDate());
        return sysDocumentMapper.insertSysDocument(sysDocument);
    }

    /**
     * 修改文件管理
     *
     * @param sysDocument 文件管理
     * @return 结果
     */
    @Override
    public int updateSysDocument(SysDocument sysDocument) {
        sysDocument.setUpdateTime(DateUtils.getNowDate());
        return sysDocumentMapper.updateSysDocument(sysDocument);
    }

    /**
     * 删除文件管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysDocumentByIds(String ids) {
        return sysDocumentMapper.deleteSysDocumentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除文件管理信息
     *
     * @param fileId 文件管理ID
     * @return 结果
     */
    @Override
    public int deleteSysDocumentById(Long fileId) {
        return sysDocumentMapper.deleteSysDocumentById(fileId);
    }
}
