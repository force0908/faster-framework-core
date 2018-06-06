package cn.faster.framework.core.auth.admin;

import cn.faster.framework.core.auth.admin.cache.ShiroCacheManager;
import cn.faster.framework.core.exception.model.BasisErrorCode;
import cn.faster.framework.core.exception.model.ErrorResponseEntity;
import cn.faster.framework.core.exception.model.ResultError;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhangbowen 2018/5/28 17:28
 */
@Configuration
public class ShiroConfiguration {
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /**
     * 缓存
     *
     * @return
     */
    @Bean
    public CacheManager cacheManager() {
        return new ShiroCacheManager();
    }

    /**
     * 权限管理器
     *
     * @param authorizingRealm
     * @return
     */
    @Bean
    public SecurityManager securityManager(AuthorizingRealm authorizingRealm) {
        authorizingRealm.setCacheManager(cacheManager());
        authorizingRealm.setAuthorizationCachingEnabled(true);
        authorizingRealm.setAuthenticationCachingEnabled(true);
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authorizingRealm);
        securityManager.setCacheManager(cacheManager());
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 拦截器
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("jwt", new ShiroFilter());
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }


    /**
     * shiro 注解
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    @ControllerAdvice
    @Configuration
    public static class ShiroExceptionHandler {
        @ResponseBody
        @ExceptionHandler(value = AuthorizationException.class)
        public Object handleException(AuthorizationException exception) {
            ResultError resultMsg = new ResultError(BasisErrorCode.NOT_HAVE_PERMISSION.getValue(), BasisErrorCode.NOT_HAVE_PERMISSION.getDescription() + "：" + exception.getMessage());
            return ErrorResponseEntity.error(resultMsg, HttpStatus.BAD_REQUEST);
        }
    }
}
