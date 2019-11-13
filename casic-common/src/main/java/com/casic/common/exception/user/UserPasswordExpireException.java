package com.casic.common.exception.user;

/**
 * 用户密码超过七天未修改，已失效
 */
public class UserPasswordExpireException extends UserException {

    private static final long serialVersionUID = 1L;

    public UserPasswordExpireException() {
        super("user.password.expire", null);
    }
}
