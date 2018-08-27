package com.github.faster.framework.core.sms.controller;

import com.github.faster.framework.core.sms.service.ISmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author zhangbowen
 */
public abstract class AbstractSmsController {
    @Autowired
    protected ISmsService smsService;


    /**
     * 发送短信验证码
     *
     * @param phone 验证码
     * @return ResponseEntity
     */
    @PostMapping("/send/{phone}")
    public ResponseEntity send(@PathVariable String phone) {
        smsService.send(phone);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
