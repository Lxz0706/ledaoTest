package com.ledao.system.mapper;

import java.util.List;
import com.ledao.system.dao.JudicialUpdata;

/**
 * 司法拍卖项目Mapper接口
 * 
 * @author lxz
 * @date 2021-09-30
 */
public interface JudicialUpdataMapper 
{
    /**
     * 查询司法拍卖项目
     * 
     * @param id 司法拍卖项目ID
     * @return 司法拍卖项目
     */
    public JudicialUpdata selectJudicialUpdataById(Long id);

    /**
     * 查询司法拍卖项目列表
     * 
     * @param judicialUpdata 司法拍卖项目
     * @return 司法拍卖项目集合
     */
    public List<JudicialUpdata> selectJudicialUpdataList(JudicialUpdata judicialUpdata);

    /**
     * 新增司法拍卖项目
     * 
     * @param judicialUpdata 司法拍卖项目
     * @return 结果
     */
    public int insertJudicialUpdata(JudicialUpdata judicialUpdata);

    /**
     * 修改司法拍卖项目
     * 
     * @param judicialUpdata 司法拍卖项目
     * @return 结果
     */
    public int updateJudicialUpdata(JudicialUpdata judicialUpdata);

    /**
     * 删除司法拍卖项目
     * 
     * @param id 司法拍卖项目ID
     * @return 结果
     */
    public int deleteJudicialUpdataById(Long id);

    /**
     * 批量删除司法拍卖项目
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteJudicialUpdataByIds(String[] ids);
}
