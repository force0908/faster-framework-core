package cn.faster.framework.core.upload.error;

import cn.faster.framework.core.exception.model.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhangbowen 2018/6/12 10:52
 */
@Getter
@AllArgsConstructor
public enum UploadError implements ErrorCode {
    SIGN_ERROR(0, "签名错误"),
    ;
    private int value;
    private String description;
}
