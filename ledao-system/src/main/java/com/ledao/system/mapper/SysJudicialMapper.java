package com.ledao.system.mapper;

import java.util.List;

import com.ledao.system.dao.SysJudicial;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 司法拍卖项目Mapper接口
 *
 * @author ledao
 * @date 2020-09-14
 */
public interface SysJudicialMapper {
    /**
     * 查询司法拍卖项目
     *
     * @param id 司法拍卖项目ID
     * @return 司法拍卖项目
     */
    public SysJudicial selectSysJudicialById(Long id);

    /**
     * 查询司法拍卖项目列表
     *
     * @param sysJudicial 司法拍卖项目
     * @return 司法拍卖项目集合
     */
    public List<SysJudicial> selectSysJudicialList(SysJudicial sysJudicial);

    /**
     * 新增司法拍卖项目
     *
     * @param sysJudicial 司法拍卖项目
     * @return 结果
     */
    public int insertSysJudicial(SysJudicial sysJudicial);

    /**
     * 修改司法拍卖项目
     *
     * @param sysJudicial 司法拍卖项目
     * @return 结果
     */
    public int updateSysJudicial(SysJudicial sysJudicial);

    /**
     * 删除司法拍卖项目
     *
     * @param id 司法拍卖项目ID
     * @return 结果
     */
    public int deleteSysJudicialById(Long id);

    /**
     * 批量删除司法拍卖项目
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysJudicialByIds(String[] ids);

    /*
     * 查询总量
     * @param sysJudicial
     * @return 结果
     *
     * */

    public List<SysJudicial> selectListTotal(SysJudicial sysJudicial);

}
