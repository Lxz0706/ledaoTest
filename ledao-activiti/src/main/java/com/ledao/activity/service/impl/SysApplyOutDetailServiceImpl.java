package com.ledao.activity.service.impl;

import java.util.List;
import com.ledao.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.activity.mapper.SysApplyOutDetailMapper;
import com.ledao.activity.dao.SysApplyOutDetail;
import com.ledao.activity.service.ISysApplyOutDetailService;
import com.ledao.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 档案出库详情记录Service业务层处理
 * 
 * @author lxz
 * @date 2021-08-10
 */
@Service
@Transactional
public class SysApplyOutDetailServiceImpl implements ISysApplyOutDetailService 
{
    @Autowired
    private SysApplyOutDetailMapper sysApplyOutDetailMapper;

    /**
     * 查询档案出库详情记录
     * 
     * @param outDetailId 档案出库详情记录ID
     * @return 档案出库详情记录
     */
    @Override
    public SysApplyOutDetail selectSysApplyOutDetailById(Long outDetailId)
    {
        return sysApplyOutDetailMapper.selectSysApplyOutDetailById(outDetailId);
    }

    /**
     * 查询档案出库详情记录列表
     * 
     * @param sysApplyOutDetail 档案出库详情记录
     * @return 档案出库详情记录
     */
    @Override
    public List<SysApplyOutDetail> selectSysApplyOutDetailList(SysApplyOutDetail sysApplyOutDetail)
    {
        return sysApplyOutDetailMapper.selectSysApplyOutDetailList(sysApplyOutDetail);
    }

    /**
     * 新增档案出库详情记录
     * 
     * @param sysApplyOutDetail 档案出库详情记录
     * @return 结果
     */
    @Override
    public int insertSysApplyOutDetail(SysApplyOutDetail sysApplyOutDetail)
    {
        sysApplyOutDetail.setCreateTime(DateUtils.getNowDate());
        return sysApplyOutDetailMapper.insertSysApplyOutDetail(sysApplyOutDetail);
    }

    /**
     * 修改档案出库详情记录
     * 
     * @param sysApplyOutDetail 档案出库详情记录
     * @return 结果
     */
    @Override
    public int updateSysApplyOutDetail(SysApplyOutDetail sysApplyOutDetail)
    {
        sysApplyOutDetail.setUpdateTime(DateUtils.getNowDate());
        return sysApplyOutDetailMapper.updateSysApplyOutDetail(sysApplyOutDetail);
    }

    /**
     * 删除档案出库详情记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysApplyOutDetailByIds(String ids)
    {
        return sysApplyOutDetailMapper.deleteSysApplyOutDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除档案出库详情记录信息
     * 
     * @param outDetailId 档案出库详情记录ID
     * @return 结果
     */
    @Override
    public int deleteSysApplyOutDetailById(Long outDetailId)
    {
        return sysApplyOutDetailMapper.deleteSysApplyOutDetailById(outDetailId);
    }

    @Override
    public int saveApplyOutDetails(List<SysApplyOutDetail> sysApplyOutDetails, String applyId) {
        try {
            sysApplyOutDetailMapper.deleteSysApplyOutDetailByApplyId(applyId);
            for (SysApplyOutDetail d : sysApplyOutDetails){
                sysApplyOutDetailMapper.insertSysApplyOutDetail(d);
            }
        }catch (Exception e){
            throw new RuntimeException();
        }
        return 0;
    }

    @Override
    public List<SysApplyOutDetail> listDocumentAndDetail(SysApplyOutDetail sysApplyOutDetail) {
        return sysApplyOutDetailMapper.listDocumentAndDetail(sysApplyOutDetail);
    }
}
