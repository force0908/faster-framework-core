package com.github.faster.framework.core.entity;

import com.github.faster.framework.core.mybatis.model.BasePager;
import com.github.faster.framework.core.utils.sequence.Sequence;
import com.github.faster.framework.core.web.context.WebContextFacade;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhangbowen
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseEntity extends BasePager implements Serializable {
    @Id
    private Long id;
    private Long createBy;
    private Long updateBy;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Integer sort;
    private String remark;
    private Integer deleted;

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
        this.deleted = 0;
        if (this.sort == null) {
            this.sort = 0;
        }
    }

    /**
     * 预更新方法
     */
    public void preUpdate() {
        this.updateDate = LocalDateTime.now();
        this.updateBy = WebContextFacade.getRequestContext().getUserId();
    }
}
