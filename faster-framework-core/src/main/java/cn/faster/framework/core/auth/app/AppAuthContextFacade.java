package cn.faster.framework.core.auth.app;

import cn.faster.framework.core.web.context.WebContextFacade;

/**
 * @author zhangbowen
 */
public class AppAuthContextFacade extends WebContextFacade {
    private static ThreadLocal<AppAuthContext> requestContextThreadLocal = new ThreadLocal<>();

    private static AppAuthContext getAppAuthContext() {
        AppAuthContext appAuthContext = requestContextThreadLocal.get();
        return appAuthContext == null ? new AppAuthContext() : appAuthContext;
    }

    public static void setAppAuthContext(AppAuthContext appAuthContext) {
        requestContextThreadLocal.set(appAuthContext);
    }

    public static void removeAppAuthContext() {
        requestContextThreadLocal.remove();
    }
}
