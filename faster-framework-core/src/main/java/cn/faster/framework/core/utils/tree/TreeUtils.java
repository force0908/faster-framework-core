package cn.faster.framework.core.utils.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangbowen 2018/5/30 14:36
 */
public class TreeUtils {

    public static <T extends TreeNode> List<TreeNode> convertToTree(List<T> list) {
        List<TreeNode> result = new ArrayList<>();
        Map<Long, TreeNode> firstMap = new HashMap<>();
        if (list == null || list.size() == 0) {
            return result;
        }
        for (TreeNode treeNode : list) {
            //获取父级是否存在缓存map中
            TreeNode parent = firstMap.get(treeNode.getParentId());
            if (treeNode.getParentId() == 0L && parent == null) {
                //说明为1级节点
                treeNode.setChildren(new ArrayList<>());
                result.add(treeNode);
            } else if (treeNode.getParentId() != 0L && parent != null) {
                List<TreeNode> children = parent.getChildren();
                if (children == null) {
                    children = new ArrayList<>();
                    parent.setChildren(children);
                }
                children.add(treeNode);
            }
            firstMap.put(treeNode.getId(), treeNode);
        }
        return result;
    }
}
