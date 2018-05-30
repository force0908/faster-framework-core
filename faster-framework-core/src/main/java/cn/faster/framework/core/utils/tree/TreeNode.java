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

    default Integer getIsLeaf() {
        List<TreeNode> children = getChildren();
        boolean isLeaf = children == null || children.size() == 0;
        return isLeaf ? 1 : 0;
    }
}
