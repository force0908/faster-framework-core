package cn.faster.framework.core.mybatis.pagination.dialect;

import cn.faster.framework.core.mybatis.pagination.bean.ConvertCountSqlBean;
import com.github.pagehelper.Dialect;
import com.github.pagehelper.Page;
import com.github.pagehelper.dialect.AbstractHelperDialect;
import com.github.pagehelper.page.PageAutoDialect;
import com.github.pagehelper.page.PageParams;
import com.github.pagehelper.parser.CountSqlParser;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.pagehelper.page.PageMethod.clearPage;

public class CustomPagerHelper implements Dialect {
    private PageParams pageParams;
    private PageAutoDialect autoDialect;
    private static final Pattern SUB_QUERY_PATTERN = Pattern.compile("[(]+[^,]*");
    private static final Pattern FROM_PATTERN = Pattern.compile("\\sfrom\\s");
    //处理SQL
    protected CountSqlParser countSqlParser = new CountSqlParser();

    @Override
    public boolean skip(MappedStatement ms, Object parameterObject, RowBounds rowBounds) {
        Page page = pageParams.getPage(parameterObject, rowBounds);
        if (page == null) {
            return true;
        } else {
            autoDialect.initDelegateDialect(ms);
            return false;
        }
    }

    @Override
    public boolean beforeCount(MappedStatement ms, Object parameterObject, RowBounds rowBounds) {
        return autoDialect.getDelegate().beforeCount(ms, parameterObject, rowBounds);
    }

    @Override
    public String getCountSql(MappedStatement ms, BoundSql boundSql, Object parameterObject, RowBounds rowBounds, CacheKey countKey) {
        return null;
    }


    public ConvertCountSqlBean getCountSqlCovert(MappedStatement ms, BoundSql boundSql, Object parameterObject, RowBounds rowBounds, CacheKey countKey) {
        String sql = boundSql.getSql();
        return getSmartCountSql(sql);
    }

    private ConvertCountSqlBean getSmartCountSql(String sql) {
        //解析SQL
        Statement stmt;
        //特殊sql不需要去掉order by时，使用注释前缀
        if (sql.contains(CountSqlParser.KEEP_ORDERBY)) {
            return generateCountSql(sql);
        }
        try {
            stmt = CCJSqlParserUtil.parse(sql);
        } catch (Throwable e) {
            //无法解析的用一般方法返回count语句
            return generateCountSql(sql);
        }
        Select select = (Select) stmt;
        SelectBody selectBody = select.getSelectBody();
        try {
            //处理body-去order by
            countSqlParser.processSelectBody(selectBody);
        } catch (Exception e) {
            //当 sql 包含 group by 时，不去除 order by
            return generateCountSql(sql);
        }
        //处理with-去order by
        countSqlParser.processWithItemsList(select.getWithItemsList());
        //处理为count查询
//        countSqlParser.sqlToCount(select, name);
        return generateCountSql(select.toString());
    }

    @Override
    public boolean afterCount(long count, Object parameterObject, RowBounds rowBounds) {
        return autoDialect.getDelegate().afterCount(count, parameterObject, rowBounds);
    }

    @Override
    public Object processParameterObject(MappedStatement ms, Object parameterObject, BoundSql boundSql, CacheKey pageKey) {
        return autoDialect.getDelegate().processParameterObject(ms, parameterObject, boundSql, pageKey);
    }

    @Override
    public boolean beforePage(MappedStatement ms, Object parameterObject, RowBounds rowBounds) {
        return autoDialect.getDelegate().beforePage(ms, parameterObject, rowBounds);
    }

    @Override
    public String getPageSql(MappedStatement ms, BoundSql boundSql, Object parameterObject, RowBounds rowBounds, CacheKey pageKey) {
        return autoDialect.getDelegate().getPageSql(ms, boundSql, parameterObject, rowBounds, pageKey);
    }

    public String getPageSql(String sql, Page page, RowBounds rowBounds, CacheKey pageKey) {
        return autoDialect.getDelegate().getPageSql(sql, page, pageKey);
    }

    @Override
    public Object afterPage(List pageList, Object parameterObject, RowBounds rowBounds) {
        //这个方法即使不分页也会被执行，所以要判断 null
        AbstractHelperDialect delegate = autoDialect.getDelegate();
        if (delegate != null) {
            return delegate.afterPage(pageList, parameterObject, rowBounds);
        }
        return pageList;
    }

    @Override
    public void afterAll() {
        //这个方法即使不分页也会被执行，所以要判断 null
        AbstractHelperDialect delegate = autoDialect.getDelegate();
        if (delegate != null) {
            delegate.afterAll();
            autoDialect.clearDelegate();
        }
        clearPage();
    }

    @Override
    public void setProperties(Properties properties) {
        pageParams = new PageParams();
        autoDialect = new PageAutoDialect();
        pageParams.setProperties(properties);
        autoDialect.setProperties(properties);
    }


    /**
     * 生成获得总数的语句
     *
     * @param sql
     * @return
     */
    private ConvertCountSqlBean generateCountSql(String sql) {
        //去除order by
        String convertSql = sql.replaceAll("(?i)order\\s+by\\s+[\\w\\s,._-]+[^)]$", "");
        //获取from的index，拼接出select count
        int fromIndex = findFromIndex(convertSql);
        if (fromIndex == -1) {
            return new ConvertCountSqlBean(convertSql, 0);
        }
        int beforeFromPreParamNum = 0;
        String beforeFromStr = convertSql.substring(0, fromIndex);
        if (beforeFromStr.contains("?")) {
            beforeFromPreParamNum = beforeFromStr.split("\\?").length - 1;
        }
        String fromSql = convertSql.substring(fromIndex);
        if (fromSql.toLowerCase().contains("group by")) {
            convertSql = "select count(1) from (select 1 " + fromSql + " )_countSql";
        } else {
            convertSql = "select count(0) " + fromSql;
        }
        return new ConvertCountSqlBean(convertSql, beforeFromPreParamNum);
    }

    private int findFromIndex(String source) {
        boolean findFlag;
        int fromIndex = -1, lastEndIndex = 0;
        //替换掉子查询
        Matcher matcher = SUB_QUERY_PATTERN.matcher(source);
        do {
            if (fromIndex > 0) {
                break;
            }
            findFlag = matcher.find();
            //如果找到了子查询语句，获取子查询之后的index
            if (findFlag) {
                //获取不是子查询的语句
                String noSubQuery = source.substring(lastEndIndex, matcher.start()).toLowerCase();
                //判断其中是否存在from
                Matcher noSubQueryFromMatcher = FROM_PATTERN.matcher(noSubQuery);
                if (noSubQueryFromMatcher.find()) {
                    fromIndex = lastEndIndex + noSubQueryFromMatcher.start();
                }
                lastEndIndex = matcher.end();
            } else {//循环到最后必然没有子查询
                String noSubQuery = source.substring(lastEndIndex).toLowerCase();
                //判断其中是否存在from
                Matcher noSubQueryFromMatcher = FROM_PATTERN.matcher(noSubQuery);
                if (noSubQueryFromMatcher.find()) {
                    fromIndex = lastEndIndex + noSubQueryFromMatcher.start();
                }
            }
        } while (findFlag);
        return fromIndex;
    }
}
