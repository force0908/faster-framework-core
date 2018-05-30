package cn.faster.framework.core.exception.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by zhangbowen on 16-6-29.
 */
@SuppressWarnings("unchecked")
public class ErrorResponseEntity extends ResponseEntity {
    public ErrorResponseEntity(HttpStatus statusCode) {
        super(statusCode);
    }

    public ErrorResponseEntity(ErrorCode body, HttpStatus status) {
        super(new ResultError(body, status), status);
    }

    public ErrorResponseEntity(ResultError resultError, HttpStatus status) {
        super(resultError, status);
    }

    public static ErrorResponseEntity error(ErrorCode body, HttpStatus status) {
        return new ErrorResponseEntity(body, status);
    }
}
