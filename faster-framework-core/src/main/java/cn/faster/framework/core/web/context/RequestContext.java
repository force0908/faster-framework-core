package cn.faster.framework.core.web.context;

import lombok.Data;

/**
 * @author zhangbowen
 */
@Data
public class RequestContext {
    private String ip;
    private String uri;
    private Long userId;
}
