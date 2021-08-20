package com.ledao.activity.service.impl;

import java.util.Date;
import java.util.List;

import com.ledao.activity.dao.SysApplyIn;
import com.ledao.activity.mapper.SysApplyInMapper;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.DateUtils;
import com.ledao.framework.util.ShiroUtils;
import net.sf.jsqlparser.expression.LongValue;
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

    @Autowired
    private SysApplyInMapper sysApplyInMapper;

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
        sysApplyOutDetailMapper.updateSysApplyOutDetail(sysApplyOutDetail);

        SysApplyOutDetail d = sysApplyOutDetailMapper.selectSysApplyOutDetailById(sysApplyOutDetail.getOutDetailId());
        SysApplyIn app = sysApplyInMapper.selectSysApplyInById(d.getApplyId());

        boolean flag = true;
        if ("7".equals(app.getApproveStatu())){
            //已出库待确认
            SysApplyOutDetail de = new SysApplyOutDetail();
            de.setApplyId(d.getApplyId());
            List<SysApplyOutDetail> apps = sysApplyOutDetailMapper.selectSysApplyOutDetailList(de);
            for (SysApplyOutDetail a :apps){
                if ("0".equals(a.getIsReturn())){
                    if (!"0".equals(a.getIsReceive())){
                        flag = false;
                    }
                }
            }
            if (flag){
                app.setApproveStatu("8");
                sysApplyInMapper.updateSysApplyIn(app);
            }
        }else if("8".equals(app.getApproveStatu())){
            //已出库待确认
            SysApplyOutDetail de2 = new SysApplyOutDetail();
            de2.setApplyId(d.getApplyId());
            List<SysApplyOutDetail> apps = sysApplyOutDetailMapper.selectSysApplyOutDetailList(de2);
            for (SysApplyOutDetail a :apps){
                if ("0".equals(a.getIsReturn())){
                    if (!"0".equals(a.getIsReturned())){
                        flag = false;
                    }
                }
            }
            if (flag){
                app.setApproveStatu("9");
                sysApplyInMapper.updateSysApplyIn(app);
            }
        }else if("9".equals(app.getApproveStatu())){
            //已出库待确认
            SysApplyOutDetail de3 = new SysApplyOutDetail();
            de3.setApplyId(d.getApplyId());
            List<SysApplyOutDetail> apps = sysApplyOutDetailMapper.selectSysApplyOutDetailList(de3);
            for (SysApplyOutDetail a :apps){
                if ("0".equals(a.getIsReturn())){
                    if (!"0".equals(a.getIsReceived())){
                        flag = false;
                    }
                }
            }
            if (flag){
                app.setApproveStatu("3");
                sysApplyInMapper.updateSysApplyIn(app);
                for (SysApplyOutDetail a :apps){
                    a.setRealReturnTime(new Date());
                    sysApplyOutDetailMapper.updateSysApplyOutDetail(a);
                }
            }
        }

        return 1;
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

    @Override
    public AjaxResult addDocDetailIds(String ids, long applyId) {
        String[] idsArr = Convert.toStrArray(ids);
        for (int i = 0; i < idsArr.length; i++){
            SysApplyOutDetail SysApplyOutDetail = new SysApplyOutDetail();
            SysApplyOutDetail.setDocumentId(Long.parseLong(idsArr[i]));
            SysApplyOutDetail.setApplyId(applyId);
            SysApplyOutDetail.setCreateBy(ShiroUtils.getLoginName());
            SysApplyOutDetail.setCreateBy(ShiroUtils.getLoginName());
            SysApplyOutDetail.setCreateTime(new Date());
            sysApplyOutDetailMapper.insertSysApplyOutDetail(SysApplyOutDetail);
        }
        return AjaxResult.success();
    }
}
