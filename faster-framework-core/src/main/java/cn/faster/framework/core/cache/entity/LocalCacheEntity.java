package cn.faster.framework.core.cache.entity;

/**
 * Created by zhangbowen on 2016/12/5.
 */
public class LocalCacheEntity {
    //对象
    private Object obj;
    //缓存时间
    private int exp;
    //存入时间
    private long saveTime;

    public long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
