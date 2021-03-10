package com.ledao.system.service;

import java.util.List;

import com.ledao.system.dao.SysDocument;

/**
 * 文件管理Service接口
 *
 * @author lxz
 * @date 2021-02-23
 */
public interface ISysDocumentService {
    /**
     * 查询文件管理
     *
     * @param fileId 文件管理ID
     * @return 文件管理
     */
    public SysDocument selectSysDocumentById(Long fileId);

    /**
     * 查询文件管理列表
     *
     * @param sysDocument 文件管理
     * @return 文件管理集合
     */
    public List<SysDocument> selectSysDocumentList(SysDocument sysDocument);

    /**
     * 新增文件管理
     *
     * @param sysDocument 文件管理
     * @return 结果
     */
    public int insertSysDocument(SysDocument sysDocument);

    /**
     * 修改文件管理
     *
     * @param sysDocument 文件管理
     * @return 结果
     */
    public int updateSysDocument(SysDocument sysDocument);

    /**
     * 批量删除文件管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysDocumentByIds(String ids);

    /**
     * 删除文件管理信息
     *
     * @param fileId 文件管理ID
     * @return 结果
     */
    public int deleteSysDocumentById(Long fileId);
}
