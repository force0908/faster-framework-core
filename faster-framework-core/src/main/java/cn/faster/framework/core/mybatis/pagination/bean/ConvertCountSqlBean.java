package cn.faster.framework.core.mybatis.pagination.bean;

/**
 * @author zhangbowen
 */
public class ConvertCountSqlBean {
    private String countSql;
    private int beforeFromPreParamNum;

    public ConvertCountSqlBean(String countSql, int beforeFromPreParamNum) {
        this.countSql = countSql;
        this.beforeFromPreParamNum = beforeFromPreParamNum;
    }

    public String getCountSql() {
        return countSql;
    }

    public void setCountSql(String countSql) {
        this.countSql = countSql;
    }

    public int getBeforeFromPreParamNum() {
        return beforeFromPreParamNum;
    }

    public void setBeforeFromPreParamNum(int beforeFromPreParamNum) {
        this.beforeFromPreParamNum = beforeFromPreParamNum;
    }
}
