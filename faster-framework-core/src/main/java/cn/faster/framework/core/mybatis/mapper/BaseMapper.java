package cn.faster.framework.core.mybatis.mapper;

import cn.faster.framework.core.mybatis.provider.BaseProvider;
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
 * @author zhangbowen 2018/5/29 9:00
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
     * 根据实体中的属性值进行分页查询，查询条件使用等号
     *
     * @param record
     * @return
     */
    @SelectProvider(type = BaseProvider.class, method = "dynamicSQL")
    Page<T> selectPage(RowBounds pager, T record);

    /**
     * 根据Example条件进行分页查询
     *
     * @param example
     * @return
     */
    @SelectProvider(type = BaseProvider.class, method = "dynamicSQL")
    Page<T> selectPageByExample(RowBounds pager, Object example);
}
