package com.ledao.system.service;

import java.util.List;
import com.ledao.system.domain.SysProjectUncollectedMoney;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ledao
 * @date 2020-08-31
 */
public interface ISysProjectUncollectedMoneyService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SysProjectUncollectedMoney selectSysProjectUncollectedMoneyById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysProjectUncollectedMoney 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SysProjectUncollectedMoney> selectSysProjectUncollectedMoneyList(SysProjectUncollectedMoney sysProjectUncollectedMoney);

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysProjectUncollectedMoney 【请填写功能名称】
     * @return 结果
     */
    public int insertSysProjectUncollectedMoney(SysProjectUncollectedMoney sysProjectUncollectedMoney);

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysProjectUncollectedMoney 【请填写功能名称】
     * @return 结果
     */
    public int updateSysProjectUncollectedMoney(SysProjectUncollectedMoney sysProjectUncollectedMoney);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectUncollectedMoneyByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSysProjectUncollectedMoneyById(Long id);
}
