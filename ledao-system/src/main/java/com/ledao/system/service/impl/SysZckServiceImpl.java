package com.ledao.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ledao.common.exception.BusinessException;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.common.utils.security.Md5Utils;
import com.ledao.system.domain.SysUser;
import com.ledao.system.domain.SysZck;
import com.ledao.system.mapper.SysZckMapper;
import com.ledao.system.service.ISysZckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.common.core.text.Convert;

/**
 * 资产信息库Service业务层处理
 *
 * @author lxz
 * @date 2020-06-09
 */
@Service
public class SysZckServiceImpl implements ISysZckService {

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysZckMapper sysZckMapper;

    /**
     * 查询资产信息库
     *
     * @param id 资产信息库ID
     * @return 资产信息库
     */
    @Override
    public SysZck selectSysZckById(Long id) {
        return sysZckMapper.selectSysZckById(id);
    }

    /**
     * 查询资产信息库列表
     *
     * @param sysZck 资产信息库
     * @return 资产信息库
     */
    @Override
    public List<SysZck> selectSysZckList(SysZck sysZck) {
        return sysZckMapper.selectSysZckList(sysZck);
    }

    /**
     * 新增资产信息库
     *
     * @param sysZck 资产信息库
     * @return 结果
     */
    @Override
    public int insertSysZck(SysZck sysZck) {
        sysZck.setCreateTime(DateUtils.getNowDate());
        return sysZckMapper.insertSysZck(sysZck);
    }

    /**
     * 修改资产信息库
     *
     * @param sysZck 资产信息库
     * @return 结果
     */
    @Override
    public int updateSysZck(SysZck sysZck) {
        sysZck.setUpdateTime(DateUtils.getNowDate());
        return sysZckMapper.updateSysZck(sysZck);
    }

    /**
     * 删除资产信息库对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysZckByIds(String ids) {
        return sysZckMapper.deleteSysZckByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除资产信息库信息
     *
     * @param id 资产信息库ID
     * @return 结果
     */
    @Override
    public int deleteSysZckById(Long id) {
        return sysZckMapper.deleteSysZckById(id);
    }

    /**
     * 导入用户数据
     *
     * @param zckList         用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importZck(List<SysZck> zckList, Boolean isUpdateSupport, String operName, Long zcbId) {
        if (StringUtils.isNull(zckList) || zckList.size() == 0) {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        SysZck sysZck1 = new SysZck();
        for (SysZck zck : zckList) {
            try {
                if (StringUtils.isNotNull(zck.getBorrower())) {
                    sysZck1.setBorrower(zck.getBorrower());
                    List<SysZck> zckList2 = this.selectSysZckList(sysZck1);
                    if (zckList2.size() > 0) {
                        zck.setParentId(zckList2.get(0).getId());
                    }
                }
                zck.setCreateBy(operName);
                zck.setZcbId(zcbId);
                this.insertSysZck(zck);
                successNum++;
                successMsg.append("<br/>" + successNum + "、借款人名称 " + zck.getBorrower() + " 导入成功");
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、借款人名称 " + zck.getBorrower() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 根据资产包ID查询资产库信息
     *
     * @param zcbId
     * @return 结果
     */
    public List<SysZck> selectSysZckByZcbId(Long zcbId) {
        return sysZckMapper.selectSysZckByZcbId(zcbId);
    }

    /**
     * 根据借款人名称分
     *
     * @param sysZck
     * @return 结果
     */
    public List<SysZck> selectSysZck(SysZck sysZck) {
        return sysZckMapper.selectSysZck(sysZck);
    }

    /**
     * 查询出自己以及子集的数据
     *
     * @param sysZck
     * @return 结果
     */
    public List<SysZck> selectSysZckByParentId(SysZck sysZck) {
        return sysZckMapper.selectSysZckByParentId(sysZck);
    }

    /**
     * 根据zckId查询结果
     *
     * @param zckIds
     * @return 结果
     */
    public List<SysZck> selectSysZckByZckId(String zckIds) {
        return sysZckMapper.selectSysZckByZckId(Convert.toStrArray(zckIds));
    }

    /**
     * 查询所有数据
     *
     * @param sysZck
     * @return 结果
     */
    public List<SysZck> queryAll(SysZck sysZck) {
        return sysZckMapper.queryAll(sysZck);
    }
}
