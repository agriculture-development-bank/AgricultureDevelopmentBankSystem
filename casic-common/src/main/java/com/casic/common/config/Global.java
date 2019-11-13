package com.casic.common.config;

import com.casic.common.utils.StringUtils;
import com.casic.common.utils.YamlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置类
 *
 * @author yuzengwen
 */
public class Global {
    private static final Logger log = LoggerFactory.getLogger(Global.class);

    private static String[] NAME = {"application.yml", "application-druid.yml"};

    /**
     * 当前对象实例
     */
    private static Global global = null;

    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = new HashMap<String, String>();

    private Global() {
    }

    /**
     * 静态工厂方法 获取当前对象实例 多线程安全单例模式(使用双重同步锁)
     */

    public static synchronized Global getInstance() {
        if (global == null) {
            synchronized (Global.class) {
                if (global == null)
                    global = new Global();
            }
        }
        return global;
    }

    /**
     * 获取配置
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            Map<?, ?> yamlMap1 = null;
            Map<?, ?> yamlMap2 = null;
            try {
                yamlMap1 = YamlUtil.loadYaml(NAME[0]);
                yamlMap2 = YamlUtil.loadYaml(NAME[1]);
                value = String.valueOf(YamlUtil.getProperty(yamlMap1, key));
                if (value == null || "null".equals(value.toLowerCase())) {
                    value = String.valueOf(YamlUtil.getProperty(yamlMap2, key));
                }
                if (value == null || "null".equals(value.toLowerCase())) {
                    map.put(key, StringUtils.EMPTY);
                } else {
                    map.put(key, value);
                }
            } catch (FileNotFoundException e) {
                log.error("获取全局配置异常 {}", key);
            }
        }
        return value;
    }

    /**
     * 获取项目名称
     */
    public static String getName() {
        return StringUtils.nvl(getConfig("casic.name"), "Casic706");
    }

    /**
     * 获取项目版本
     */
    public static String getVersion() {
        return StringUtils.nvl(getConfig("casic.version"), "3.0.0");
    }

    /**
     * 获取版权年份
     */
    public static String getCopyrightYear() {
        return StringUtils.nvl(getConfig("casic.copyrightYear"), "2018");
    }

    /**
     * 获取ip地址开关
     */
    public static Boolean isAddressEnabled() {
        return Boolean.valueOf(getConfig("casic.addressEnabled"));
    }

    /**
     * 获取文件上传路径
     */
    public static String getProfile() {
        return getConfig("casic.profile");
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getConfig("casic.profile") + "avatar/";
    }

    /**
     * 获取下载上传路径
     */
    public static String getDownloadPath() {
        return getConfig("casic.profile") + "download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        return getConfig("casic.profile") + "upload/";
    }

    /**
     * 获取作者
     */
    public static String getAuthor() {
        return StringUtils.nvl(getConfig("gen.author"), "casic706");
    }

    /**
     * 生成包路径
     */
    public static String getPackageName() {
        return StringUtils.nvl(getConfig("gen.packageName"), "com.casic.project.module");
    }

    /**
     * 是否自动去除表前缀
     */
    public static String getAutoRemovePre() {
        return StringUtils.nvl(getConfig("gen.autoRemovePre"), "true");
    }

    /**
     * 表前缀(类名不会包含表前缀)
     */
    public static String getTablePrefix() {
        return StringUtils.nvl(getConfig("gen.tablePrefix"), "sys_");
    }
}
