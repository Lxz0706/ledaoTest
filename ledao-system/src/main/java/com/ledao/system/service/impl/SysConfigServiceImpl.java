package com.ledao.system.service.impl;

import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;

import com.ledao.common.constant.WeChatConstants;
import com.ledao.common.utils.DateUtils;
import com.ledao.common.utils.qrCode.WxQrCode;
import com.ledao.system.dao.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ledao.common.constant.Constants;
import com.ledao.common.constant.UserConstants;
import com.ledao.common.core.redis.RedisCache;
import com.ledao.common.core.text.Convert;
import com.ledao.common.exception.ServiceException;
import com.ledao.common.utils.StringUtils;
import com.ledao.system.mapper.SysConfigMapper;
import com.ledao.system.service.ISysConfigService;

/**
 * 参数配置 服务层实现
 *
 * @author ruoyi
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService {
    @Autowired
    private SysConfigMapper configMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 项目启动时，初始化参数到缓存
     */
    @PostConstruct
    public void init() {
        loadingConfigCache();
    }

    /**
     * 查询参数配置信息
     *
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    @Override
    public SysConfig selectConfigById(Long configId) {
        SysConfig config = new SysConfig();
        config.setConfigId(configId);
        return configMapper.selectConfig(config);
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数key
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey) {
        String configValue = Convert.toStr(redisCache.getCacheObject(getCacheKey(configKey)));
        if (StringUtils.isNotEmpty(configValue)) {
            return configValue;
        }
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        SysConfig retConfig = configMapper.selectConfig(config);
        if (StringUtils.isNotNull(retConfig)) {
            redisCache.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
            return retConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    @Override
    public List<SysConfig> selectConfigList(SysConfig config) {
        return configMapper.selectConfigList(config);
    }

    /**
     * 新增参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int insertConfig(SysConfig config) {
        int row = configMapper.insertConfig(config);
        if (row > 0) {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * 修改参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int updateConfig(SysConfig config) {
        int row = configMapper.updateConfig(config);
        if (row > 0) {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
        return row;
    }

    /**
     * 批量删除参数配置对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public void deleteConfigByIds(String ids) {
        Long[] configIds = Convert.toLongArray(ids);
        for (Long configId : configIds) {
            SysConfig config = selectConfigById(configId);
            if (StringUtils.equals(UserConstants.YES, config.getConfigType())) {
                throw new ServiceException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
            }
            configMapper.deleteConfigById(configId);
            redisCache.deleteObject(getCacheKey(config.getConfigKey()));
        }
    }

    /**
     * 加载参数缓存数据
     */
    @Override
    public void loadingConfigCache() {
        List<SysConfig> configsList = configMapper.selectConfigList(new SysConfig());
        for (SysConfig config : configsList) {
            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
        }
    }

    /**
     * 清空参数缓存数据
     */
    @Override
    public void clearConfigCache() {
        Collection<String> keys = redisCache.keys(Constants.SYS_CONFIG_KEY + "*");
        redisCache.deleteObject(keys);
    }

    /**
     * 重置参数缓存数据
     */
    @Override
    public void resetConfigCache() {
        clearConfigCache();
        loadingConfigCache();
    }

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public String checkConfigKeyUnique(SysConfig config) {
        Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
        SysConfig info = configMapper.checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue()) {
            return UserConstants.CONFIG_KEY_NOT_UNIQUE;
        }
        return UserConstants.CONFIG_KEY_UNIQUE;
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return Constants.SYS_CONFIG_KEY + configKey;
    }

    @Override
    public String getWechatComAccessToken() {
        String accessToken = "";
        try {
            SysConfig config = new SysConfig();
            config.setConfigKey("weChatAccessToken");
            List<SysConfig> confs = configMapper.selectConfigList(config);
            boolean needSave = false;
            if (confs != null && confs.size() > 0) {
                if ((DateUtils.getNowDate().getTime() - confs.get(0).getCreateTime().getTime()) / 1000 / 60 < 90 && StringUtils.isNotEmpty(confs.get(0).getConfigValue())) {
                    accessToken = confs.get(0).getConfigValue();
                } else {
                    needSave = true;
                    for (SysConfig conf : confs) {
                        configMapper.deleteConfigByIds(new String[]{conf.getConfigId().toString()});
                    }
                }
            } else {
                needSave = true;
            }
            if (needSave) {
                accessToken = WxQrCode.getAccessToken(WeChatConstants.WXAPPIDCOM, WeChatConstants.WXSECRETCOM);
                config.setConfigValue(accessToken);
                configMapper.insertConfig(config);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    @Override
    public String getWechatAccessToken() {
        String accessToken = "";
        try {
            SysConfig config = new SysConfig();
            config.setConfigKey("weChatLittleAccessToken");
            List<SysConfig> confs = configMapper.selectConfigList(config);
            boolean needSave = false;
            if (confs != null && confs.size() > 0) {
                if (((DateUtils.getNowDate().getTime() - confs.get(0).getCreateTime().getTime()) / 1000 / 60 < 90) && StringUtils.isNotEmpty(confs.get(0).getConfigValue())) {
                    accessToken = confs.get(0).getConfigValue();
                } else {
                    needSave = true;
                    for (SysConfig conf : confs) {
                        configMapper.deleteConfigByIds(new String[]{conf.getConfigId().toString()});
                    }
                }
            } else {
                needSave = true;
            }
            if (needSave) {
                accessToken = WxQrCode.getAccessToken(WeChatConstants.WXAPPID, WeChatConstants.WXSECRET);
                config.setConfigValue(accessToken);
                configMapper.insertConfig(config);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    /***
     * 获取微信订阅号AccessToken
     * @return accessToken
     */
    @Override
    public String getSubscribeAccessToken() {
        String accessToken = "";
        try {
            SysConfig config = new SysConfig();
            config.setConfigKey("weSubscribeAccessToken");
            List<SysConfig> confs = configMapper.selectConfigList(config);
            boolean needSave = false;
            if (confs != null && confs.size() > 0) {
                if (((DateUtils.getNowDate().getTime() - confs.get(0).getCreateTime().getTime()) / 1000 / 60 < 90) && StringUtils.isNotEmpty(confs.get(0).getConfigValue())) {
                    accessToken = confs.get(0).getConfigValue();
                } else {
                    needSave = true;
                    for (SysConfig conf : confs) {
                        configMapper.deleteConfigByIds(new String[]{conf.getConfigId().toString()});
                    }
                }
            } else {
                needSave = true;
            }
            if (needSave) {
                accessToken = WxQrCode.getAccessToken(WeChatConstants.WXSUBSCRIBEAPPID, WeChatConstants.WXSUBSCRIBEAPPSECRET);
                config.setConfigValue(accessToken);
                configMapper.insertConfig(config);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    /***
     * 获取乐道熊猫小程序token
     * @return
     */
    @Override
    public String getLeDaoPanDaAccessToken() {
        String accessToken = "";
        try {
            SysConfig config = new SysConfig();
            config.setConfigKey("leDaoPanDaAccessToken");
            List<SysConfig> confs = configMapper.selectConfigList(config);
            boolean needSave = false;
            if (confs != null && confs.size() > 0) {
                if (((DateUtils.getNowDate().getTime() - confs.get(0).getCreateTime().getTime()) / 1000 / 60 < 60) && StringUtils.isNotEmpty(confs.get(0).getConfigValue())) {
                    accessToken = confs.get(0).getConfigValue();
                } else {
                    needSave = true;
                    for (SysConfig conf : confs) {
                        configMapper.deleteConfigByIds(new String[]{conf.getConfigId().toString()});
                    }
                }
            } else {
                needSave = true;
            }
            if (needSave) {
                accessToken = WxQrCode.getAccessToken(WeChatConstants.LDPANDAAPPID, WeChatConstants.LDPANDASECRET);
                config.setConfigValue(accessToken);
                System.out.println("获取的leDaoPanDaAccessToken：" + accessToken);
                configMapper.insertConfig(config);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

}
