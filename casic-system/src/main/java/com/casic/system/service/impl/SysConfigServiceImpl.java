package com.casic.system.service.impl;

import java.util.List;
import java.util.UUID;

import com.casic.common.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casic.common.constant.UserConstants;
import com.casic.common.support.Convert;
import com.casic.common.utils.StringUtils;
import com.casic.system.domain.SysConfig;
import com.casic.system.mapper.SysConfigMapper;
import com.casic.system.service.ISysConfigService;

/**
 * 参数配置 服务层实现
 *
 * @author yuzengwen
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService {
    @Autowired
    private SysConfigMapper configMapper;

    /**
     * 查询参数配置信息
     *
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    @Override
    public SysConfig selectConfigById(String configId) {
        SysConfig config = new SysConfig();
        config.setConfigId(configId);
        return configMapper.selectConfig(config);
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数名称
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey) {
        SysConfig config = new SysConfig();
        config.setConfigKey(configKey);
        SysConfig retConfig = configMapper.selectConfig(config);
        return StringUtils.isNotNull(retConfig) ? retConfig.getConfigValue() : "";
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
        if (config != null) {
            if ("".equals(config.getConfigId()) || config.getConfigId() == null) {
                String uuid = UuidUtils.getUUIDString();
                config.setConfigId(uuid);
            }
        }
        return configMapper.insertConfig(config);
    }

    /**
     * 修改参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int updateConfig(SysConfig config) {
        return configMapper.updateConfig(config);
    }

    /**
     * 批量删除参数配置对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteConfigByIds(String ids) {
        return configMapper.deleteConfigByIds(Convert.toStrArray(ids));
    }

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public String checkConfigKeyUnique(SysConfig config) {
        String configId = StringUtils.isNull(config.getConfigId()) ? "-1" : config.getConfigId();
        SysConfig info = configMapper.checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull(info) && !info.getConfigId().equals(configId)) {
            return UserConstants.CONFIG_KEY_NOT_UNIQUE;
        }
        return UserConstants.CONFIG_KEY_UNIQUE;
    }

    @Override
    public List<SysConfig> selectConfigListByKeys(String[] configKeys) {
        return configMapper.selectConfigListByKeys(configKeys);
    }

}
