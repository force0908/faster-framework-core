package com.github.faster.framework.core.smsCode.service;

import com.github.faster.framework.core.cache.context.CacheFacade;
import com.github.faster.framework.core.utils.Utils;
import org.springframework.util.StringUtils;

/**
 * @author zhangbowen
 * @since 2018/8/27
 */
public abstract class ISmsCodeService {
    //是否为调试环境
    protected boolean debug;
    //超时时间
    protected long expire;

    public ISmsCodeService(boolean debug, long expire) {
        this.debug = debug;
        this.expire = expire;
    }

    /**
     * 发送短信验证码
     *
     * @param phone 手机号
     * @return true/false
     */
    public boolean send(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        if (debug) {
            return true;
        }
        String code = generateCode();
        boolean success = this.send(phone, code);
        if (success) {
            CacheFacade.set(phone, code, expire);
        }
        return success;
    }


    /**
     * 验证短信验证码
     *
     * @param phone 手机号
     * @param code  短信验证码
     * @return true/false
     */
    public boolean valid(String phone, String code) {
        if (debug) {
            return true;
        }
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            return false;
        }
        String existCode = CacheFacade.get(phone);
        if (code.equals(existCode)) {
            return true;
        }
        return false;
    }

    /**
     * @return 短信验证码
     */
    protected String generateCode() {
        return Utils.generateSmsCode();
    }

    /**
     * 验证是否存在验证码
     *
     * @param phone 手机号
     * @param code  短信验证码
     * @return true or false
     */
    public boolean exist(String phone, String code) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            return false;
        }
        if (debug) {
            return true;
        }
        return code.equals(CacheFacade.get(phone));
    }

    /**
     * 通过手机号删除短信验证码
     *
     * @param phone 手机号
     */
    public void remove(String phone) {
        CacheFacade.delete(phone);
    }

    /**
     * 发送短信验证码
     * @param phone 手机号
     * @param code 验证码
     * @return true/false
     */
    protected abstract boolean send(String phone, String code);
}
