package cn.faster.framework.core.mybatis.model;

import cn.faster.framework.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

/**
 * @author Created by zhangbowen on 2016/1/25.
 */
@Data
public abstract class BasePager{
    //页数
    @Transient
    private Integer pageNum;
    //每页数据，默认10条
    @Transient
    private Integer pageSize = 10;
}
