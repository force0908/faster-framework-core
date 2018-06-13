package com.github.faster.framework.core.upload.controller;

import com.github.faster.framework.core.exception.model.BasisErrorCode;
import com.github.faster.framework.core.exception.model.ErrorResponseEntity;
import com.github.faster.framework.core.upload.model.UploadRequest;
import com.github.faster.framework.core.upload.service.IUploadService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author zhangbowen
 */
public abstract class AbstractUploadController {
    @Autowired
    private IUploadService uploadService;


    /**
     * 预上传，返回上传所需参数
     * @param uploadRequest 上传请求
     * @return httpResponse
     */
    @GetMapping("/upload/preload")
    public ResponseEntity preload(UploadRequest uploadRequest) {
        return ResponseEntity.ok(uploadService.preload(uploadRequest));
    }

    /**
     * 上传文件
     *
     * @param uploadFile 文件
     * @param uploadRequest 上传请求
     * @return httpResponse
     */
    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile uploadFile, UploadRequest uploadRequest) {
        try {
            return ResponseEntity.ok(uploadService.upload(uploadFile, uploadRequest));
        } catch (IOException e) {
            return ErrorResponseEntity.error(BasisErrorCode.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
