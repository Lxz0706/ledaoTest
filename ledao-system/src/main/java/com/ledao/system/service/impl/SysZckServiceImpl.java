package com.ledao.system.service.impl;

import java.util.List;

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
 * @author ledao
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
    public String importZck(List<SysZck> zckList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(zckList) || zckList.size() == 0) {
            throw new BusinessException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysZck zck : zckList) {
            try {
                zck.setCreateBy(operName);
                this.insertSysZck(zck);
                successNum++;
                successMsg.append("<br/>" + successNum + "、账号 " + zck.getAssetPackageName() + " 导入成功");
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + zck.getAssetPackageName() + " 导入失败：";
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
}