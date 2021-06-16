package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysDocument;

/**
 * 文件管理Mapper接口
 *
 * @author lxz
 * @date 2021-02-23
 */
public interface SysDocumentMapper {
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
     * 删除文件管理
     *
     * @param fileId 文件管理ID
     * @return 结果
     */
    public int deleteSysDocumentById(Long fileId);

    /**
     * 批量删除文件管理
     *
     * @param fileIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysDocumentByIds(String[] fileIds);


    /**
     * 根据文档类型中的子集分组
     *
     * @param sysDocument
     * @return
     */
    public List<SysDocument> listBySubsetType(SysDocument sysDocument);
}
