package com.github.faster.framework.core.upload.model;

import lombok.Data;

/**
 * @author zhangbowen
 */
@Data
public class UploadToken {
    private String sign;
    /**
     * 模式
     */
    private Integer mode;
}
