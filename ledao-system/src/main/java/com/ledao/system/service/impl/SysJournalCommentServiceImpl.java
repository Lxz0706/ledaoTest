package com.ledao.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysJournalCommentMapper;
import com.ledao.system.dao.SysJournalComment;
import com.ledao.system.service.ISysJournalCommentService;
import com.ledao.common.core.text.Convert;

/**
 * 日志评论Service业务层处理
 * 
 * @author lxz
 * @date 2021-09-05
 */
@Service
public class SysJournalCommentServiceImpl implements ISysJournalCommentService 
{
    @Autowired
    private SysJournalCommentMapper sysJournalCommentMapper;

    /**
     * 查询日志评论
     * 
     * @param id 日志评论ID
     * @return 日志评论
     */
    @Override
    public SysJournalComment selectSysJournalCommentById(Long id)
    {
        return sysJournalCommentMapper.selectSysJournalCommentById(id);
    }

    /**
     * 查询日志评论列表
     * 
     * @param sysJournalComment 日志评论
     * @return 日志评论
     */
    @Override
    public List<SysJournalComment> selectSysJournalCommentList(SysJournalComment sysJournalComment)
    {
        return sysJournalCommentMapper.selectSysJournalCommentList(sysJournalComment);
    }

    /**
     * 新增日志评论
     * 
     * @param sysJournalComment 日志评论
     * @return 结果
     */
    @Override
    public int insertSysJournalComment(SysJournalComment sysJournalComment)
    {
        sysJournalComment.setCreateTime(DateUtils.getNowDate());
        return sysJournalCommentMapper.insertSysJournalComment(sysJournalComment);
    }

    /**
     * 修改日志评论
     * 
     * @param sysJournalComment 日志评论
     * @return 结果
     */
    @Override
    public int updateSysJournalComment(SysJournalComment sysJournalComment)
    {
        sysJournalComment.setUpdateTime(DateUtils.getNowDate());
        return sysJournalCommentMapper.updateSysJournalComment(sysJournalComment);
    }

    /**
     * 删除日志评论对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysJournalCommentByIds(String ids)
    {
        return sysJournalCommentMapper.deleteSysJournalCommentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除日志评论信息
     * 
     * @param id 日志评论ID
     * @return 结果
     */
    @Override
    public int deleteSysJournalCommentById(Long id)
    {
        return sysJournalCommentMapper.deleteSysJournalCommentById(id);
    }

    @Override
    public List<SysJournalComment> selectSysJournalCommentListNoParent(SysJournalComment sysJournalComment) {
        List<SysJournalComment> comTotals = new ArrayList<>();
        List<SysJournalComment> comments = sysJournalCommentMapper.selectSysJournalCommentListNoParent(sysJournalComment);
        for (int i=0;i< comments.size();i++) {
            comments.get(i).setNo(String.valueOf(i+1));
            comTotals.add(comments.get(i));
            SysJournalComment s = new SysJournalComment();
            s.setParentId(comments.get(i).getId().toString());
            List<SysJournalComment> details = sysJournalCommentMapper.selectSysJournalCommentList(s);
//            com.setDetails(details);
            for (SysJournalComment c: details) {
                c.setWorkDetail(c.getChatDetail());
                c.setNo(null);
                c.setCreateTime(c.getCreateTimeComment());
                c.setUserName(c.getUserName()+" 回复：");
                comTotals.add(c);
            }
        }
        return comTotals;
    }
}
