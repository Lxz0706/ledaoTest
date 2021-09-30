package com.ledao.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.JudicialUpdataMapper;
import com.ledao.system.dao.JudicialUpdata;
import com.ledao.system.service.IJudicialUpdataService;
import com.ledao.common.core.text.Convert;

/**
 * 司法拍卖项目Service业务层处理
 * 
 * @author lxz
 * @date 2021-09-30
 */
@Service
public class JudicialUpdataServiceImpl implements IJudicialUpdataService 
{
    @Autowired
    private JudicialUpdataMapper judicialUpdataMapper;

    /**
     * 查询司法拍卖项目
     * 
     * @param id 司法拍卖项目ID
     * @return 司法拍卖项目
     */
    @Override
    public JudicialUpdata selectJudicialUpdataById(Long id)
    {
        return judicialUpdataMapper.selectJudicialUpdataById(id);
    }

    /**
     * 查询司法拍卖项目列表
     * 
     * @param judicialUpdata 司法拍卖项目
     * @return 司法拍卖项目
     */
    @Override
    public List<JudicialUpdata> selectJudicialUpdataList(JudicialUpdata judicialUpdata)
    {
        return judicialUpdataMapper.selectJudicialUpdataList(judicialUpdata);
    }

    /**
     * 新增司法拍卖项目
     * 
     * @param judicialUpdata 司法拍卖项目
     * @return 结果
     */
    @Override
    public int insertJudicialUpdata(JudicialUpdata judicialUpdata)
    {
        return judicialUpdataMapper.insertJudicialUpdata(judicialUpdata);
    }

    /**
     * 修改司法拍卖项目
     * 
     * @param judicialUpdata 司法拍卖项目
     * @return 结果
     */
    @Override
    public int updateJudicialUpdata(JudicialUpdata judicialUpdata)
    {
        return judicialUpdataMapper.updateJudicialUpdata(judicialUpdata);
    }

    /**
     * 删除司法拍卖项目对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteJudicialUpdataByIds(String ids)
    {
        return judicialUpdataMapper.deleteJudicialUpdataByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除司法拍卖项目信息
     * 
     * @param id 司法拍卖项目ID
     * @return 结果
     */
    @Override
    public int deleteJudicialUpdataById(Long id)
    {
        return judicialUpdataMapper.deleteJudicialUpdataById(id);
    }
}
