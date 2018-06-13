package com.github.faster.framework.core.upload.error;

import com.github.faster.framework.core.exception.model.ErrorCode;
import com.github.faster.framework.core.exception.model.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangbowen
 */
@Getter
@AllArgsConstructor
public enum UploadError implements ErrorCode {
    SIGN_ERROR(0, "签名错误"),
    ;
    private int value;
    private String description;
}
