package trees;

/**
 * Recursively traverse down the root. When target is less than root, go left;
 * when target is greater than root, go right.
 */
public class ClosestBinarySearchTreeValue {
    int goal;
    double min = Double.MAX_VALUE;

    public int closestValue(TreeNode root, double target) {
        helper(root, target);
        return goal;
    }

    public void helper(TreeNode root, double target){
        if(root==null)
            return;

        if(Math.abs(root.val - target) < min){
            min = Math.abs(root.val-target);
            goal = root.val;
        }

        if(target < root.val){
            helper(root.left, target);
        }else{
            helper(root.right, target);
        }
    }
}
