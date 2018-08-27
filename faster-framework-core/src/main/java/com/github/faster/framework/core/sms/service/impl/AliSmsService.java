package com.github.faster.framework.core.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.github.faster.framework.core.cache.context.CacheFacade;
import com.github.faster.framework.core.sms.service.ISmsService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangbowen
 * @since 2018/8/27
 */
public class AliSmsService extends ISmsService {
    private AliSmsInitModel aliSmsInitModel;
    //短信API产品名称（短信产品名固定，无需修改）
    private final String PRODUCT = "Dysmsapi";
    //短信API产品域名（接口地址固定，无需修改）
    private final String DOMAIN = "dysmsapi.aliyuncs.com";
    private final String END_POINT = "cn-hangzhou";
    private final String REGION_ID = "cn-hangzhou";
    //初始化ascClient,暂时不支持多region（请勿修改）
    private IClientProfile profile;

    public AliSmsService(boolean debug, long expire, AliSmsInitModel aliSmsInitModel) {
        super(debug, expire);
        this.aliSmsInitModel = aliSmsInitModel;
        DefaultProfile.getProfile(REGION_ID, aliSmsInitModel.getAccessKeyId(),
                aliSmsInitModel.getAccessKeySecret());
    }

    @Override
    protected boolean send(String phone, String code) {
        try {
            DefaultProfile.addEndpoint(END_POINT, REGION_ID, PRODUCT, DOMAIN);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            SendSmsRequest request = new SendSmsRequest();
            request.setMethod(MethodType.POST);
            request.setPhoneNumbers(phone);
            request.setSignName(aliSmsInitModel.getSignName());
            request.setTemplateCode(aliSmsInitModel.getTemplateCode());
            Map<String, Object> param = new HashMap<>();
            param.put("code", code);
            request.setTemplateParam(JSON.toJSONString(param));
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().
                    equals("OK")) {
                return true;
            }
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
