package com.ledao.system.service.impl;

import java.util.List;

import com.ledao.common.exception.BusinessException;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.StringUtils;
import com.ledao.system.domain.SysZck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.system.mapper.SysBgczzckMapper;
import com.ledao.system.domain.SysBgczzck;
import com.ledao.system.service.ISysBgczzckService;
import com.ledao.common.core.text.Convert;

/**
 * 重组并购项目信息库Service业务层处理
 *
 * @author lxz
 * @date 2020-06-16
 */
@Service
public class SysBgczzckServiceImpl implements ISysBgczzckService {

    private static final Logger log = LoggerFactory.getLogger(SysBgczzckServiceImpl.class);
    @Autowired
    private SysBgczzckMapper sysBgczzckMapper;

    /**
     * 查询重组并购项目信息库
     *
     * @param id 重组并购项目信息库ID
     * @return 重组并购项目信息库
     */
    @Override
    public SysBgczzck selectSysBgczzckById(Long id) {
        return sysBgczzckMapper.selectSysBgczzckById(id);
    }

    /**
     * 查询重组并购项目信息库列表
     *
     * @param sysBgczzck 重组并购项目信息库
     * @return 重组并购项目信息库
     */
    @Override
    public List<SysBgczzck> selectSysBgczzckList(SysBgczzck sysBgczzck) {
        return sysBgczzckMapper.selectSysBgczzckList(sysBgczzck);
    }

    /**
     * 新增重组并购项目信息库
     *
     * @param sysBgczzck 重组并购项目信息库
     * @return 结果
     */
    @Override
    public int insertSysBgczzck(SysBgczzck sysBgczzck) {
        return sysBgczzckMapper.insertSysBgczzck(sysBgczzck);
    }

    /**
     * 修改重组并购项目信息库
     *
     * @param sysBgczzck 重组并购项目信息库
     * @return 结果
     */
    @Override
    public int updateSysBgczzck(SysBgczzck sysBgczzck) {
        sysBgczzck.setUpdateTime(DateUtils.getNowDate());
        return sysBgczzckMapper.updateSysBgczzck(sysBgczzck);
    }

    /**
     * 删除重组并购项目信息库对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysBgczzckByIds(String ids) {
        return sysBgczzckMapper.deleteSysBgczzckByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除重组并购项目信息库信息
     *
     * @param id 重组并购项目信息库ID
     * @return 结果
     */
    @Override
    public int deleteSysBgczzckById(Long id) {
        return sysBgczzckMapper.deleteSysBgczzckById(id);
    }

    /**
     * 导入用户数据
     *
     * @param sysBgczzckList  用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importBgczzk(List<SysBgczzck> sysBgczzckList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(sysBgczzckList) || sysBgczzckList.size() == 0) {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysBgczzck sysBgczzck : sysBgczzckList) {
            try {
                sysBgczzck.setCreateBy(operName);
                this.insertSysBgczzck(sysBgczzck);
                successNum++;
                successMsg.append("<br/>" + successNum + "、项目 " + sysBgczzck.getProjectName() + " 导入成功");
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、项目 " + sysBgczzck.getProjectName() + " 导入失败：";
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
     * 根据项目状态查询项目
     * */
    public List<SysBgczzck> selectByProjectStatus(){
        return sysBgczzckMapper.selectByProjectStatus();
    }
}
