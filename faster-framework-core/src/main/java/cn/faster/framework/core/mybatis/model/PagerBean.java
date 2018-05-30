package cn.faster.framework.core.mybatis.model;

import javax.persistence.Transient;

/**
 * @author Created by zhangbowen on 2016/1/25.
 */
public abstract class PagerBean {
    //页数
    @Transient
    private Integer pageNum = 1;
    //每页数据，默认10条
    @Transient
    private Integer pageSize = 10;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
