package com.ledao.system.service;

import java.util.List;
import com.ledao.system.dao.SysJournalComment;

/**
 * 日志评论Service接口
 * 
 * @author lxz
 * @date 2021-09-05
 */
public interface ISysJournalCommentService 
{
    /**
     * 查询日志评论
     * 
     * @param id 日志评论ID
     * @return 日志评论
     */
    public SysJournalComment selectSysJournalCommentById(Long id);

    /**
     * 查询日志评论列表
     * 
     * @param sysJournalComment 日志评论
     * @return 日志评论集合
     */
    public List<SysJournalComment> selectSysJournalCommentList(SysJournalComment sysJournalComment);

    /**
     * 新增日志评论
     * 
     * @param sysJournalComment 日志评论
     * @return 结果
     */
    public int insertSysJournalComment(SysJournalComment sysJournalComment);

    /**
     * 修改日志评论
     * 
     * @param sysJournalComment 日志评论
     * @return 结果
     */
    public int updateSysJournalComment(SysJournalComment sysJournalComment);

    /**
     * 批量删除日志评论
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysJournalCommentByIds(String ids);

    /**
     * 删除日志评论信息
     * 
     * @param id 日志评论ID
     * @return 结果
     */
    public int deleteSysJournalCommentById(Long id);

    List<SysJournalComment> selectSysJournalCommentListNoParent(SysJournalComment sysJournalComment);
}
