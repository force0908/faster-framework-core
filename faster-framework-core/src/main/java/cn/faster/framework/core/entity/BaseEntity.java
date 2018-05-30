package cn.faster.framework.core.entity;

import cn.faster.framework.core.sequence.Sequence;
import cn.faster.framework.core.mybatis.model.PagerBean;
import cn.faster.framework.core.web.context.WebContextFacade;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhangbowen
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseEntity extends PagerBean implements Serializable {
    @Id
    private Long id;
    private Long createBy;
    private Long updateBy;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Integer sort;
    private String remark;
    private Integer deleted = 0;

    /**
     * 预插入方法
     */
    public void preInsert() {
        this.id = Sequence.next();
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
        Long userId = WebContextFacade.getRequestContext().getUserId();
        this.createBy = userId;
        this.updateBy = userId;
    }

    /**
     * 预更新方法
     */
    public void preUpdate() {
        this.updateDate = LocalDateTime.now();
        this.updateBy = WebContextFacade.getRequestContext().getUserId();
    }
}
