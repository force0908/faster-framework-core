package com.github.faster.framework.spring.boot.autoconfigure.sms;

/**
 * @author zhangbowen
 * @since 2018/8/27
 */
public class SmsProperties {
    //是否为调试环境
    protected boolean debug = false;
    //超时时间，默认15分钟
    protected long expire = 60 * 15;
}
