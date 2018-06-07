package cn.faster.framework.core.utils.tree;

import java.util.List;

/**
 * @author zhangbowen 2018/5/30 14:38
 */
public interface TreeNode {
    Long getId();

    Long getParentId();

    List<TreeNode> getChildren();

    void setChildren(List<TreeNode> children);
}
