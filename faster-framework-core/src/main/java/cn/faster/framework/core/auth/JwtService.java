package cn.faster.framework.core.auth;

import cn.faster.framework.core.cache.context.CacheFacade;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * @author zhangbowen
 */
@Data
public class JwtService {
    private String base64Security;
    private boolean multipartTerminal;

    /**
     * 解密，使用项目配置秘钥
     *
     * @param token 要解密的token
     * @return Claims
     */
    public Claims parseToken(String token) {
        return parseToken(token, this.base64Security);
    }

    /**
     * 解密
     *
     * @param token          要解密的token
     * @param base64Security 秘钥
     * @return Claims
     */
    private Claims parseToken(String token, String base64Security) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                .parseClaimsJws(token).getBody();
    }

    /**
     * 生成token,使用项目配置秘钥，存入缓存
     *
     * @param audience  接收者
     * @param expSecond 过期时间(秒)
     * @return String
     */
    public String createToken(Object audience, long expSecond) {
        String token = createToken(audience, expSecond, this.base64Security);
        //如果缓存非本地模式，并且不允许多端登录，设置token
        if (!CacheFacade.local && !multipartTerminal) {
            CacheFacade.set(audience.toString(), token, expSecond);
        }
        return token;
    }

    /**
     * 生成token
     *
     * @param audience       接收者
     * @param expSecond      过期时间(秒)
     * @param base64Security 秘钥
     * @return String
     */
    private String createToken(Object audience, long expSecond, String base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setAudience(audience.toString())
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        if (expSecond > 0) {
            long expMillis = nowMillis + expSecond * 1000;
            Date exp = new Date(expMillis);
            builder = builder.setExpiration(exp).setNotBefore(now);
        }
        //生成Token
        return builder.compact();
    }
}
