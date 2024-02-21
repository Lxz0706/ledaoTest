package com.ledao.system.service.impl;

import com.ledao.common.core.text.Convert;
import com.ledao.common.exception.BusinessException;
import com.ledao.common.utils.StringUtils;
import com.ledao.system.dao.SysBuyer;
import com.ledao.system.mapper.SysBuyerMapper;
import com.ledao.system.service.ISysBuyerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 购房人基本信息Service业务层处理
 *
 * @author lxz
 * @date 2023-12-27
 */
@Service
public class SysBuyerServiceImpl implements ISysBuyerService {
    private static final Logger log = LoggerFactory.getLogger(SysBuyerServiceImpl.class);
    @Autowired
    private SysBuyerMapper sysBuyerMapper;

    /**
     * 查询购房人基本信息
     *
     * @param buyerId 购房人基本信息ID
     * @return 购房人基本信息
     */
    @Override
    public SysBuyer selectSysBuyerById(Long buyerId) {
        return sysBuyerMapper.selectSysBuyerById(buyerId);
    }

    /**
     * 查询购房人基本信息列表
     *
     * @param sysBuyer 购房人基本信息
     * @return 购房人基本信息
     */
    @Override
    public List<SysBuyer> selectSysBuyerList(SysBuyer sysBuyer) {
        return sysBuyerMapper.selectSysBuyerList(sysBuyer);
    }

    /**
     * 新增购房人基本信息
     *
     * @param sysBuyer 购房人基本信息
     * @return 结果
     */
    @Override
    public int insertSysBuyer(SysBuyer sysBuyer) {
        return sysBuyerMapper.insertSysBuyer(sysBuyer);
    }

    /**
     * 修改购房人基本信息
     *
     * @param sysBuyer 购房人基本信息
     * @return 结果
     */
    @Override
    public int updateSysBuyer(SysBuyer sysBuyer) {
        return sysBuyerMapper.updateSysBuyer(sysBuyer);
    }

    /**
     * 删除购房人基本信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysBuyerByIds(String ids) {
        return sysBuyerMapper.deleteSysBuyerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除购房人基本信息信息
     *
     * @param buyerId 购房人基本信息ID
     * @return 结果
     */
    @Override
    public int deleteSysBuyerById(Long buyerId) {
        return sysBuyerMapper.deleteSysBuyerById(buyerId);
    }

    /**
     * 导入用户数据
     *
     * @param sysBuyerList    用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importBuyer(List<SysBuyer> sysBuyerList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(sysBuyerList) || sysBuyerList.size() == 0) {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysBuyer sysBuyer : sysBuyerList) {
            try {
                sysBuyer.setCreateBy(operName);
                this.insertSysBuyer(sysBuyer);
                successNum++;
                successMsg.append("<br/>" + successNum + "、项目 " + sysBuyer.getBuyerName() + " 导入成功");
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、项目 " + sysBuyer.getBuyerName() + " 导入失败：";
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
