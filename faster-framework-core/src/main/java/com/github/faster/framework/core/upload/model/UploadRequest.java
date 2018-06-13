package com.github.faster.framework.core.upload.model;

import lombok.Data;

/**
 * @author zhangbowen
 */
@Data
public class UploadRequest {
    /**
     * 是否覆盖 0.否 1.是
     */
    private Integer isCover = 1;

    /**
     * 要存储的文件名称
     */
    private String fileName;

    /**
     * 上传token
     */
    private String token;

    /**
     * 模式
     */
    private String mode;


}
