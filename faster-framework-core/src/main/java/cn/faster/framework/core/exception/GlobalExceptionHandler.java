package cn.faster.framework.core.exception;

import cn.faster.framework.core.exception.model.BasicError;
import cn.faster.framework.core.exception.model.ErrorResponseEntity;
import cn.faster.framework.core.exception.model.ResultError;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * @author Created by zhangbowen on 2017/6/6.
 */
@ControllerAdvice
@ResponseBody
@Configuration
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Object handleException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        ResultError resultMsg = new ResultError(BasicError.VALIDATION_FAILED.getValue(), fieldError.getDefaultMessage());
        return new ErrorResponseEntity(resultMsg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public Object handleException(BindException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        ResultError resultMsg = new ResultError(BasicError.VALIDATION_FAILED.getValue(), fieldError.getField() + fieldError.getDefaultMessage());
        return new ErrorResponseEntity(resultMsg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenValidException.class)
    public Object handleException(TokenValidException exception) {
        return new ErrorResponseEntity(exception.getErrorCode(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SQLException.class)
    public Object handleException(SQLException exception) {
        exception.printStackTrace();
        return new ErrorResponseEntity(BasicError.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PersistenceException.class)
    public Object handleException(PersistenceException exception) {
        exception.printStackTrace();
        return new ErrorResponseEntity(BasicError.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
