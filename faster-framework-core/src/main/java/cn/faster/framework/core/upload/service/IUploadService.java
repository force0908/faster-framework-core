package cn.faster.framework.core.upload.service;

import cn.faster.framework.core.upload.model.UploadRequest;
import cn.faster.framework.core.upload.model.UploadSuccess;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * @author zhangbowen 2018/6/12 10:07
 */
public abstract class IUploadService {
    protected static final DateTimeFormatter FILE_NAME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
    private String mode;

    protected IUploadService(String mode){
        this.mode = mode;
    }
    /**
     * 获取上传签名
     *
     * @return
     */
    protected abstract String sign(UploadRequest uploadRequest);

    /**
     * 上传文件
     *
     * @return
     */
    public UploadSuccess upload(MultipartFile uploadFile, UploadRequest uploadRequest) throws IOException {
        return new UploadSuccess();
    }

    /**
     * 预上传，获取上传所需参数
     * @param uploadRequest
     * @return
     */
    public UploadRequest preload(UploadRequest uploadRequest) {
        UploadRequest resultRequest = new UploadRequest();
        BeanUtils.copyProperties(uploadRequest, resultRequest);
        resultRequest.setMode(this.mode);
        resultRequest.setToken(sign(uploadRequest));
        return resultRequest;
    }
}
