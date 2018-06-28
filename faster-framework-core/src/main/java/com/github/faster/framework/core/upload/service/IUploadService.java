package com.github.faster.framework.core.upload.service;

import com.github.faster.framework.core.upload.model.UploadRequest;
import com.github.faster.framework.core.upload.model.UploadSuccess;
import com.github.faster.framework.core.upload.model.UploadToken;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * @author zhangbowen
 */
public abstract class IUploadService {
    protected static final DateTimeFormatter FILE_NAME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");

    protected IUploadService() {
    }

    /**
     * 预上传，获取token
     *
     * @param uploadRequest 上传请求实体
     * @return 签名
     */
    public abstract UploadToken preload(UploadRequest uploadRequest);

    /**
     * 上传文件
     *
     * @param uploadFile    文件
     * @param uploadRequest 请求实体
     * @param token         签名字符串
     * @return 上传成功实体
     * @throws IOException ioexception
     */
    public UploadSuccess upload(MultipartFile uploadFile, UploadRequest uploadRequest, String token) throws IOException {
        return new UploadSuccess();
    }

    /**
     * 预览、下载上传文件
     *
     * @param fileName 文件名称
     * @return 字节流
     */
    public byte[] files(String fileName) {
        return new byte[]{};
    }
}
