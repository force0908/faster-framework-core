package com.github.faster.framework.core.web.inteceptor;

import com.github.faster.framework.core.utils.NetworkUtil;
import com.github.faster.framework.core.web.context.RequestContext;
import com.github.faster.framework.core.web.context.WebContextFacade;
import com.github.faster.framework.core.utils.NetworkUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author  zhangbowen
 */
public class ContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        setRequestContext(request);
        return true;
    }

    private void setRequestContext(HttpServletRequest request) {
        RequestContext requestContext = WebContextFacade.getRequestContext();
        requestContext.setIp(NetworkUtil.getIp(request));
        requestContext.setUri(request.getRequestURI());
        WebContextFacade.setRequestContext(requestContext);
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) {
        WebContextFacade.removeRequestContext();
    }
}
