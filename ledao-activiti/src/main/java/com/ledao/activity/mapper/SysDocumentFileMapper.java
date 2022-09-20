package com.ledao.activity.mapper;

import java.util.List;

import com.ledao.activity.dao.SysDocumentFile;

/**
 * 档案Mapper接口
 *
 * @author lxz
 * @date 2021-08-04
 */
public interface SysDocumentFileMapper {
    /**
     * 查询档案
     *
     * @param documentId 档案ID
     * @return 档案
     */
    public SysDocumentFile selectSysDocumentFileById(Long documentId);

    /**
     * 查询档案列表
     *
     * @param sysDocumentFile 档案
     * @return 档案集合
     */
    public List<SysDocumentFile> selectSysDocumentFileList(SysDocumentFile sysDocumentFile);

    /**
     * 新增档案
     *
     * @param sysDocumentFile 档案
     * @return 结果
     */
    public int insertSysDocumentFile(SysDocumentFile sysDocumentFile);

    /**
     * 修改档案
     *
     * @param sysDocumentFile 档案
     * @return 结果
     */
    public int updateSysDocumentFile(SysDocumentFile sysDocumentFile);

    /**
     * 删除档案
     *
     * @param documentId 档案ID
     * @return 结果
     */
    public int deleteSysDocumentFileById(Long documentId);

    /**
     * 批量删除档案
     *
     * @param documentIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysDocumentFileByIds(String[] documentIds);

    /**
     * @param 查询详细的档案明细
     * @return 结果
     */
    List<SysDocumentFile> selectSysDocumentFileDetailList(SysDocumentFile sysDocumentFile);

    /**
     * @param 查询详细的档案明细
     * @return 结果
     */
    List<SysDocumentFile> selectSysDocumentFileTotalList(SysDocumentFile sysDocumentFile);

    List<SysDocumentFile> selectDocListByApplyInIds(String[] ids);
}
