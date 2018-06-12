package com.github.faster.framework.core.upload.service.local;

import com.github.faster.framework.core.exception.TokenValidException;
import com.github.faster.framework.core.upload.error.UploadError;
import com.github.faster.framework.core.upload.model.UploadRequest;
import com.github.faster.framework.core.upload.model.UploadSuccess;
import com.github.faster.framework.core.upload.service.IUploadService;
import com.github.faster.framework.core.utils.Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author zhangbowen 2018/6/12 11:45
 */
public class LocalUploadService extends IUploadService {
    private String fileDir;
    private String urlPrefix;
    private String secretKey;

    public LocalUploadService(String mode, String fileDir, String urlPrefix, String secretKey) {
        super(mode);
        this.fileDir = fileDir;
        this.urlPrefix = urlPrefix;
        this.secretKey = secretKey;
    }

    @Override
    public String sign(UploadRequest uploadRequest) {
        return Utils.signWithSort(uploadRequest, secretKey);
    }

    @Override
    public UploadSuccess upload(MultipartFile multipartFile, UploadRequest uploadRequest) throws IOException {
        //验证签名
        boolean success = Utils.signWithSort(uploadRequest, secretKey).equals(uploadRequest.getToken());
        if (!success) {
            throw new TokenValidException(UploadError.SIGN_ERROR);
        }
        String fileName = StringUtils.isEmpty(uploadRequest.getFileName()) ? LocalDateTime.now().format(FILE_NAME_FORMATTER) : uploadRequest.getFileName();
        //如果不覆盖,在文件后增加时间后缀
        if (uploadRequest.getIsCover() == 0) {
            fileName = fileName.concat(LocalDateTime.now().format(FILE_NAME_FORMATTER));
        }
        File file = new File(fileDir, fileName);
        if (!file.getParentFile().exists()) {
            boolean mkSuccess = file.getParentFile().mkdirs();
            if (!mkSuccess) {
                return new UploadSuccess();
            }
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(multipartFile.getBytes());
        }
        UploadSuccess uploadSuccess = new UploadSuccess();
        uploadSuccess.setUrl(urlPrefix + fileName);
        return uploadSuccess;
    }
}
