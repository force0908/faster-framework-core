package com.github.faster.framework.core.utils.tree;

import java.util.List;

/**
 * @author zhangbowen
 */
public interface TreeNode {
    Long getId();

    Long getParentId();

    List<TreeNode> getChildren();

    void setChildren(List<TreeNode> children);
}
