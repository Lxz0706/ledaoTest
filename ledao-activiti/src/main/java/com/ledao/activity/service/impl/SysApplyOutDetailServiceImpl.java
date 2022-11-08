package com.ledao.activity.service.impl;

import java.util.Date;
import java.util.List;

import com.ledao.activity.dao.SysApplyIn;
import com.ledao.activity.dao.SysDocumentFile;
import com.ledao.activity.mapper.SysApplyInMapper;
import com.ledao.activity.mapper.SysDocumentFileMapper;
import com.ledao.common.core.dao.AjaxResult;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
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
public class SysApplyOutDetailServiceImpl implements ISysApplyOutDetailService {
    @Autowired
    private SysApplyOutDetailMapper sysApplyOutDetailMapper;

    @Autowired
    private SysApplyInMapper sysApplyInMapper;

    @Autowired
    private SysDocumentFileMapper sysDocumentFileMapper;

    /**
     * 查询档案出库详情记录
     *
     * @param outDetailId 档案出库详情记录ID
     * @return 档案出库详情记录
     */
    @Override
    public SysApplyOutDetail selectSysApplyOutDetailById(Long outDetailId) {
        return sysApplyOutDetailMapper.selectSysApplyOutDetailById(outDetailId);
    }

    /**
     * 查询档案出库详情记录列表
     *
     * @param sysApplyOutDetail 档案出库详情记录
     * @return 档案出库详情记录
     */
    @Override
    public List<SysApplyOutDetail> selectSysApplyOutDetailList(SysApplyOutDetail sysApplyOutDetail) {
        return sysApplyOutDetailMapper.selectSysApplyOutDetailList(sysApplyOutDetail);
    }

    /**
     * 新增档案出库详情记录
     *
     * @param sysApplyOutDetail 档案出库详情记录
     * @return 结果
     */
    @Override
    public int insertSysApplyOutDetail(SysApplyOutDetail sysApplyOutDetail) {
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
    public int updateSysApplyOutDetail(SysApplyOutDetail sysApplyOutDetail) {
        sysApplyOutDetail.setUpdateTime(DateUtils.getNowDate());
        sysApplyOutDetailMapper.updateSysApplyOutDetail(sysApplyOutDetail);

        /*SysApplyOutDetail d = sysApplyOutDetailMapper.selectSysApplyOutDetailById(sysApplyOutDetail.getOutDetailId());
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
//                sysApplyInMapper.updateSysApplyIn(app);
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
//                sysApplyInMapper.updateSysApplyIn(app);
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
//                sysApplyInMapper.updateSysApplyIn(app);
                for (SysApplyOutDetail a :apps){
                    a.setRealReturnTime(new Date());
                    sysApplyOutDetailMapper.updateSysApplyOutDetail(a);
                }
            }
        }
        if (flag){
            app.setReviser(ShiroUtils.getLoginName());
            app.setUpdateTime(DateUtils.getNowDate());
            sysApplyInMapper.updateSysApplyIn(app);
        }*/

        return 1;
    }

    /**
     * 删除档案出库详情记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysApplyOutDetailByIds(String ids) {
        String[] ds = Convert.toStrArray(ids);
        SysApplyOutDetail sd = sysApplyOutDetailMapper.selectSysApplyOutDetailById(Long.parseLong(ds[0]));
        updateSysApplyIn(sd.getApplyId());
        return sysApplyOutDetailMapper.deleteSysApplyOutDetailByIds(Convert.toStrArray(ids));
    }

    public int updateSysApplyIn(Long applyId) {
        SysApplyIn ap = sysApplyInMapper.selectSysApplyInById(applyId);
        ap.setReviser(ShiroUtils.getLoginName());
        ap.setUpdateTime(DateUtils.getNowDate());
        return sysApplyInMapper.updateSysApplyIn(ap);
    }

    /**
     * 删除档案出库详情记录信息
     *
     * @param outDetailId 档案出库详情记录ID
     * @return 结果
     */
    @Override
    public int deleteSysApplyOutDetailById(Long outDetailId) {
        return sysApplyOutDetailMapper.deleteSysApplyOutDetailById(outDetailId);
    }

    @Override
    public int saveApplyOutDetails(List<SysApplyOutDetail> sysApplyOutDetails, String applyId) {
        try {
            sysApplyOutDetailMapper.deleteSysApplyOutDetailByApplyId(applyId);
            for (SysApplyOutDetail d : sysApplyOutDetails) {
                sysApplyOutDetailMapper.insertSysApplyOutDetail(d);
            }
        } catch (Exception e) {
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
        String loginName = ShiroUtils.getLoginName();
        List<SysApplyOutDetail> outs = sysApplyOutDetailMapper.selectSysApplyOutDetailByDocumentIds(applyId, Convert.toStrArray(ids), loginName);
        if (outs != null && outs.size() > 0) {
            return AjaxResult.error("该档案已存在");
        }
        String[] idsArr = Convert.toStrArray(ids);
        for (int i = 0; i < idsArr.length; i++) {
            SysApplyOutDetail SysApplyOutDetail = new SysApplyOutDetail();
            SysApplyOutDetail.setDocumentId(Long.parseLong(idsArr[i]));
            SysApplyOutDetail.setApplyId(applyId);
            SysApplyOutDetail.setCreateBy(loginName);
            SysApplyOutDetail.setCreateBy(loginName);
            SysApplyOutDetail.setCreateTime(new Date());
            SysDocumentFile sysDocumentFile = sysDocumentFileMapper.selectSysDocumentFileById(Long.parseLong(idsArr[i]));
            if (StringUtils.isNull(sysDocumentFile.getCounts())) {
                SysApplyOutDetail.setCounts(Long.valueOf(0));
            } else {
                SysApplyOutDetail.setCounts(sysDocumentFile.getCounts());
            }
            sysApplyOutDetailMapper.insertSysApplyOutDetail(SysApplyOutDetail);
        }
        updateSysApplyIn(applyId);
        return AjaxResult.success();
    }

    /**
     * 批量修改借出文档类型
     *
     * @param sysApplyOutDetail
     * @return
     */
    @Override
    public int editOutFileDetailByIds(SysApplyOutDetail sysApplyOutDetail) {
        return sysApplyOutDetailMapper.editOutFileDetailByIds(sysApplyOutDetail);
    }
}
