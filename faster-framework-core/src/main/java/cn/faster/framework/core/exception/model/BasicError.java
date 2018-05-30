package cn.faster.framework.core.exception.model;

/**
 * Created with IntelliJ IDEA.
 *
 * @Autor: zhangbowen
 * @Time: 11:55
 * @Description:
 */
public enum BasicError implements ErrorCode {
    SERVER_ERROR(1000, "服务器正在维护"),
    VALIDATION_FAILED(1001, "参数异常"),
    TOKEN_INVALID(1002, "登录状态过期"),
    NOT_HAVE_PERMISSION(1003, "权限不足"),
    ;

    private int value;
    private String description;

    BasicError() {
    }

    BasicError(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
