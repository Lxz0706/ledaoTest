package com.ledao.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysJournalMapper;
import com.ledao.system.dao.SysJournal;
import com.ledao.system.service.ISysJournalService;
import com.ledao.common.core.text.Convert;

/**
 * 日志Service业务层处理
 * 
 * @author lxz
 * @date 2021-09-05
 */
@Service
public class SysJournalServiceImpl implements ISysJournalService 
{
    @Autowired
    private SysJournalMapper sysJournalMapper;

    @Autowired
    private SysUserMapper userMapper;

    /**
     * 查询日志
     * 
     * @param id 日志ID
     * @return 日志
     */
    @Override
    public SysJournal selectSysJournalById(Long id)
    {
        return sysJournalMapper.selectSysJournalById(id);
    }

    /**
     * 查询日志列表
     * 
     * @param sysJournal 日志
     * @return 日志
     */
    @Override
    public List<SysJournal> selectSysJournalList(SysJournal sysJournal)
    {
        List<SysJournal> sj = sysJournalMapper.selectSysJournalList(sysJournal);
        for (SysJournal s:sj) {
            List<String> userNames = new ArrayList<>();
            if (StringUtils.isNotEmpty(s.getShared())){
                String[] shares = s.getShared().split(",");
                for (String userId:shares) {
                    userNames.add(userMapper.selectUserById(Long.valueOf(userId)).getUserName());
                }
            }
            s.setShared(String.join(",",userNames));
        }
        return sj;
    }

    /**
     * 新增日志
     * 
     * @param sysJournal 日志
     * @return 结果
     */
    @Override
    public int insertSysJournal(SysJournal sysJournal)
    {
        //sysJournal.setCreateTime(DateUtils.getNowDate());
        return sysJournalMapper.insertSysJournal(sysJournal);
    }

    /**
     * 修改日志
     * 
     * @param sysJournal 日志
     * @return 结果
     */
    @Override
    public int updateSysJournal(SysJournal sysJournal)
    {
        sysJournal.setUpdateTime(DateUtils.getNowDate());
        return sysJournalMapper.updateSysJournal(sysJournal);
    }

    /**
     * 删除日志对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysJournalByIds(String ids)
    {
        return sysJournalMapper.deleteSysJournalByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除日志信息
     * 
     * @param id 日志ID
     * @return 结果
     */
    @Override
    public int deleteSysJournalById(Long id)
    {
        return sysJournalMapper.deleteSysJournalById(id);
    }
}
