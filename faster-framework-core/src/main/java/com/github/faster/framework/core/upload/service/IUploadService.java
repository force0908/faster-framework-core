package com.github.faster.framework.core.upload.service;

import com.github.faster.framework.core.upload.model.UploadRequest;
import com.github.faster.framework.core.upload.model.UploadSuccess;
import com.github.faster.framework.core.upload.model.UploadRequest;
import com.github.faster.framework.core.upload.model.UploadSuccess;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * @author zhangbowen
 */
public abstract class IUploadService {
    protected static final DateTimeFormatter FILE_NAME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
    private String mode;

    protected IUploadService(String mode){
        this.mode = mode;
    }
    /**
     * 获取签名
     * @param uploadRequest 上传请求实体
     * @return 签名
     */
    protected abstract String sign(UploadRequest uploadRequest);

    /**
     * 上传文件
     *
     * @param uploadFile 文件
     * @param uploadRequest 请求实体
     * @return 上传成功实体
     * @throws IOException ioexception
     */
    public UploadSuccess upload(MultipartFile uploadFile, UploadRequest uploadRequest) throws IOException {
        return new UploadSuccess();
    }

    /**
     * 预上传，获取上传所需参数
     * @param uploadRequest 上传请求实体
     * @return 返回携带签名的上传实体
     */
    public UploadRequest preload(UploadRequest uploadRequest) {
        UploadRequest resultRequest = new UploadRequest();
        BeanUtils.copyProperties(uploadRequest, resultRequest);
        resultRequest.setMode(this.mode);
        resultRequest.setToken(sign(uploadRequest));
        return resultRequest;
    }
}
