package com.github.faster.framework.core.mybatis.mapper;

import com.github.faster.framework.core.mybatis.provider.BaseProvider;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Marker;

/**
 * @author zhangbowen
 */
@RegisterMapper
public interface BaseMapper<T>
        extends tk.mybatis.mapper.common.BaseMapper<T>,
        ExampleMapper<T>,
        InsertListMapper<T>,
        IdListMapper<T, Long>,
        IdsMapper<T>,
        Marker {
    /**
     * 分页查询
     *
     * @param pager  分页请求
     * @param record 实体
     * @return 分页结果
     */
    @SelectProvider(type = BaseProvider.class, method = "dynamicSQL")
    Page<T> selectPage(RowBounds pager, T record);

    /**
     * 根据Example条件进行分页查询
     *
     * @param pager   分页请求
     * @param example 条件
     * @return 分页实体
     */
    @SelectProvider(type = BaseProvider.class, method = "dynamicSQL")
    Page<T> selectPageByExample(RowBounds pager, Object example);
}
