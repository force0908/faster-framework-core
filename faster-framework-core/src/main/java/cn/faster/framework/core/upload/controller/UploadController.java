package cn.faster.framework.core.upload.controller;

import cn.faster.framework.core.exception.model.BasisErrorCode;
import cn.faster.framework.core.exception.model.ErrorResponseEntity;
import cn.faster.framework.core.upload.model.UploadRequest;
import cn.faster.framework.core.upload.service.IUploadService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author zhangbowen 2018/6/12 10:18
 */
@AllArgsConstructor
public abstract class UploadController {
    private IUploadService uploadService;


    /**
     * 预上传，返回上传所需参数
     *
     * @return
     */
    @GetMapping("/upload/preload")
    public ResponseEntity preload(UploadRequest uploadRequest) {
        return ResponseEntity.ok(uploadService.preload(uploadRequest));
    }

    /**
     * 上传文件
     *
     * @param uploadFile
     * @return
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
