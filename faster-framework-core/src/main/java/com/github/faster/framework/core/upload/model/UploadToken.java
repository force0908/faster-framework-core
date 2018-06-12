package com.github.faster.framework.core.upload.model;

import lombok.Data;

/**
 * @author zhangbowen 2018/6/12 10:08
 */
@Data
public class UploadToken {
    private String sign;
    /**
     * 模式
     */
    private Integer mode;
}
