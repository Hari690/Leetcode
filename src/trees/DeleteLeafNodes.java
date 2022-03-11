package trees;

/**
 * Given a binary tree root and an integer target, delete all the leaf nodes with value target.
 * Note that once you delete a leaf node with value target,
 * if its parent node becomes a leaf node and has the value target,
 * it should also be deleted (you need to continue doing that until you cannot).
 * Input: root = [1,2,3,2,null,2,4], target = 2
 * Output: [1,null,3,null,4]
 * Explanation: Leaf nodes in green with value (target = 2) are removed (Picture in left).
 * After removing, new nodes become leaf nodes with value (target = 2) (Picture in center).
 * Input: root = [1,3,3,3,2], target = 3
 * Output: [1,3,null,null,2]
 * Input: root = [1,2,null,2,null,2], target = 2
 * Output: [1]
 * Explanation: Leaf nodes in green with value (target = 2) are removed at each step.
 */
public class DeleteLeafNodes {
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        if(root==null)
            return null;

        if(root.left==null && root.right==null && root.val==target) {
            return null;
        }

        TreeNode left = removeLeafNodes(root.left, target);
        TreeNode right = removeLeafNodes(root.right, target);

        if(left==null && right==null && root.val==target)
            return null;

        root.left=left;
        root.right=right;
        return root;
    }
}
