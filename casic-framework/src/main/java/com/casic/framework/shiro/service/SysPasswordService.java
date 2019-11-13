package com.casic.framework.shiro.service;

import com.casic.common.constant.Constants;
import com.casic.common.exception.user.UserPasswordNotMatchException;
import com.casic.common.exception.user.UserPasswordRetryLimitExceedException;
import com.casic.common.utils.MessageUtils;
import com.casic.framework.manager.AsyncManager;
import com.casic.framework.manager.factory.AsyncFactory;
import com.casic.system.domain.SysUser;
import com.casic.system.service.ISysUserService;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 登录密码方法
 *
 * @author yuzengwen
 */
@Component
public class SysPasswordService {
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private CacheManager cacheManager;

    private Cache<String, AtomicInteger> loginRecordCache;

    @Value(value = "${user.password.maxRetryCount}")
    private String maxRetryCount;

    @Autowired
    private ISysUserService userService;

    @PostConstruct
    public void init() {
        loginRecordCache = cacheManager.getCache("loginRecordCache");
    }

    public void validate(SysUser user, String password) {
        String loginName = user.getLoginName();

        AtomicInteger retryCount = loginRecordCache.get(loginName);

        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            loginRecordCache.put(loginName, retryCount);
        }
        //密码输入次数大于五次
        if (retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount).intValue()) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.exceed", retryCount)));
            //停用用户
            user.setStatus("1");
            userService.updateUserInfo(user);
            throw new UserPasswordRetryLimitExceedException(Integer.valueOf(maxRetryCount).intValue());
        }

        if (!matches(user, password)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.count", retryCount)));
            loginRecordCache.put(loginName, retryCount);
            throw new UserPasswordNotMatchException();
        } else {
            clearLoginRecordCache(loginName);
        }
    }

    public boolean matches(SysUser user, String newPassword) {

        String planPassword = user.getLoginName() + newPassword + user.getSalt();
        boolean matches = bCryptPasswordEncoder.matches(planPassword, user.getPassword());
        return matches;
        /*
        return user.getPassword().equals(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
        */
    }

    public void clearLoginRecordCache(String username) {
        loginRecordCache.remove(username);
    }

    public String encryptPassword(String username, String password, String salt) {
        //return new Md5Hash(username + password + salt).toHex().toString();
        return bCryptPasswordEncoder.encode(username + password + salt);
    }

}
