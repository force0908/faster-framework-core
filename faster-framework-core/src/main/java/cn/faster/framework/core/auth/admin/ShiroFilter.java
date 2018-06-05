package cn.faster.framework.core.auth.admin;

import cn.faster.framework.core.exception.model.BasisErrorCode;
import cn.faster.framework.core.exception.model.ResultError;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhangbowen 2018/5/29 13:45
 */
public class ShiroFilter extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwtToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        return new AuthenticationToken() {
            @Override
            public Object getPrincipal() {
                return jwtToken;
            }

            @Override
            public Object getCredentials() {
                return jwtToken;
            }
        };
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return executeLogin(servletRequest, servletResponse);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setContentType("application/json;charset=utf-8");
        try (PrintWriter printWriter = httpResponse.getWriter()) {
            printWriter.write(JSON.toJSONString(new ResultError(BasisErrorCode.TOKEN_INVALID, HttpStatus.UNAUTHORIZED)));
            printWriter.flush();
        } catch (IOException ignore) {
        }
        return false;
    }
}
