package com.github.faster.framework.core.smsCode.controller;

import com.github.faster.framework.core.exception.model.BasisErrorCode;
import com.github.faster.framework.core.exception.model.ErrorResponseEntity;
import com.github.faster.framework.core.smsCode.service.ISmsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author zhangbowen
 */
public abstract class AbstractSmsCodeController {
    @Autowired
    protected ISmsCodeService smsCodeService;


    /**
     * 发送短信验证码
     *
     * @param phone 验证码
     * @return ResponseEntity
     */
    @PostMapping("/send/{phone}")
    public ResponseEntity send(@PathVariable String phone) {
        boolean success = smsCodeService.send(phone);
        if (success) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return ErrorResponseEntity.error(BasisErrorCode.SMS_SEND_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
