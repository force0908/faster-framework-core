package com.github.faster.framework.core.captcha.service;

import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
import com.github.bingoohuang.patchca.service.Captcha;
import com.github.faster.framework.core.auth.JwtService;
import com.github.faster.framework.core.captcha.bean.CaptchaBean;
import com.github.faster.framework.core.utils.Utils;
import io.jsonwebtoken.Claims;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * @author zhangbowen
 */
public abstract class ICaptchaService {
    protected JwtService jwtService;
    protected ConfigurableCaptchaService configurableCaptchaService;

    public ICaptchaService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * 验证图形验证码是否有效
     * @param captcha 验证码
     * @param token token
     * @return true or false
     */
    public boolean validCaptcha(String captcha, String token) {
        Claims claims = jwtService.parseToken(token);
        return claims != null && captcha.equals(claims.getAudience());
    }

    /**
     * 生成图形验证码
     *
     * @return 图形验证码bean
     */
    public CaptchaBean generateCaptcha() {
        Captcha captcha = configurableCaptchaService.getCaptcha();
        String captchaBase64 = imgToBase64(captcha.getImage());
        String token = jwtService.createToken(captcha.getWord(), Utils.MINUTE);
        return new CaptchaBean(token, "data:image/png;base64," + captchaBase64);
    }

    /**
     * 图片转base64
     *
     * @param img 图片
     * @return base64
     */
    protected String imgToBase64(RenderedImage img) {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, "png", os);
            return Base64.getEncoder().encodeToString(os.toByteArray());
        } catch (final IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }
}
