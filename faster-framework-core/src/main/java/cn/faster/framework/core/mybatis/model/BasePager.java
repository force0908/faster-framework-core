package cn.faster.framework.core.mybatis.model;

import com.github.pagehelper.PageRowBounds;
import lombok.Data;

import javax.persistence.Transient;

/**
 * @author Created by zhangbowen on 2016/1/25.
 */
@Data
public class BasePager {
    //页数
    @Transient
    private Integer pageNum = 1;
    //每页数据，默认10条
    @Transient
    private Integer pageSize = 10;

    public PageRowBounds rowBounds() {
        int offset = (pageNum - 1) * pageSize;
        return new PageRowBounds(offset, pageSize);
    }
}
