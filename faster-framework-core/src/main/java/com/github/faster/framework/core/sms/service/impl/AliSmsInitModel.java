package com.github.faster.framework.core.sms.service.impl;

import lombok.Data;

/**
 * @author zhangbowen
 * @since 2018/8/27
 */
@Data
public class AliSmsInitModel {
    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private String templateCode;
}
