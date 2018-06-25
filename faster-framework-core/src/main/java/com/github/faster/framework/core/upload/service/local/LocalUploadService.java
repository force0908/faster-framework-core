package com.github.faster.framework.core.upload.service.local;

import com.github.faster.framework.core.exception.TokenValidException;
import com.github.faster.framework.core.upload.error.UploadError;
import com.github.faster.framework.core.upload.model.UploadRequest;
import com.github.faster.framework.core.upload.model.UploadSuccess;
import com.github.faster.framework.core.upload.model.UploadToken;
import com.github.faster.framework.core.upload.service.IUploadService;
import com.github.faster.framework.core.utils.Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * @author zhangbowen
 */
public class LocalUploadService extends IUploadService {
    private String fileDir;
    private String urlPrefix;
    private String secretKey;

    public LocalUploadService(String fileDir, String urlPrefix, String secretKey) {
        this.fileDir = fileDir;
        this.urlPrefix = urlPrefix;
        this.secretKey = secretKey;
    }

    @Override
    public UploadToken preload(UploadRequest uploadRequest) {
        long timestamp = System.currentTimeMillis();
        uploadRequest.setTimestamp(timestamp);
        return new UploadToken(Utils.signWithSort(uploadRequest, secretKey), timestamp);
    }

    @Override
    public UploadSuccess upload(MultipartFile multipartFile, UploadRequest uploadRequest, String token) throws IOException {
        //如果上传token超出30分钟，则认为token失效，需重新获取。
        if (System.currentTimeMillis() - uploadRequest.getTimestamp() > 1000 * 60 * 30) {
            throw new TokenValidException(UploadError.SIGN_TIME_OUT);
        }
        //验证签名
        boolean success = Utils.signWithSort(uploadRequest, secretKey).equals(token);
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
        uploadSuccess.setUrl(urlPrefix + "/files/" + fileName);
        return uploadSuccess;
    }

    @Override
    public byte[] files(String fileName) {
        try {
            return Files.readAllBytes(Paths.get(fileDir, fileName));
        } catch (IOException e) {
            return new byte[]{};
        }
    }
}
