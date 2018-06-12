package com.github.faster.framework.core.exception.model;

import com.github.faster.framework.core.web.context.WebContextFacade;
import org.springframework.http.HttpStatus;

/**
 * Created by zhangbowen on 2017/5/5.
 */
public class ResultError {
    private long timestamp;
    private int status;
    private String path;
    private int code;
    private String message;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private ResultError() {
        this.timestamp = System.currentTimeMillis();
        this.path = WebContextFacade.getRequestContext().getUri();
    }

    public ResultError(int code, String message) {
        this();
        this.code = code;
        this.message = message;
    }

    public ResultError(ErrorCode errorCode) {
        this();
        this.code = errorCode.getValue();
        this.message = errorCode.getDescription();
    }

    public ResultError(ErrorCode errorCode, HttpStatus httpStatus) {
        this();
        this.code = errorCode.getValue();
        this.message = errorCode.getDescription();
        this.status = httpStatus.value();
    }


}
