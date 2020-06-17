package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.domain.SysJudicial;

/**
 * 司法Mapper接口
 *
 * @author lxz
 * @date 2020-06-09
 */
public interface SysJudicialMapper {
    /**
     * 查询司法
     *
     * @param id 司法ID
     * @return 司法
     */
    public SysJudicial selectSysJudicialById(Long id);

    /**
     * 查询司法列表
     *
     * @param sysJudicial 司法
     * @return 司法集合
     */
    public List<SysJudicial> selectSysJudicialList(SysJudicial sysJudicial);

    /**
     * 新增司法
     *
     * @param sysJudicial 司法
     * @return 结果
     */
    public int insertSysJudicial(SysJudicial sysJudicial);

    /**
     * 修改司法
     *
     * @param sysJudicial 司法
     * @return 结果
     */
    public int updateSysJudicial(SysJudicial sysJudicial);

    /**
     * 删除司法
     *
     * @param id 司法ID
     * @return 结果
     */
    public int deleteSysJudicialById(Long id);

    /**
     * 批量删除司法
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysJudicialByIds(String[] ids);
}
