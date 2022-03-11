package trees;

/**
 * You are given a perfect binary tree where all leaves are on the same level, and every parent has two children. The binary tree has the
 * following definition:
 *
 * struct Node {
 *   int val;
 *   Node *left;
 *   Node *right;
 *   Node *next;
 * }
 * Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
 *
 * Initially, all next pointers are set to NULL.
 *
 * Input: root = [1,2,3,4,5,6,7]
 * Output: [1,#,2,3,#,4,5,6,7,#]
 * Explanation: Given the above perfect binary tree (Figure A), your function should populate each next pointer to point to its next
 * right node, just like in Figure B. The serialized output is in level order as connected by the next pointers, with '#' signifying the
 * end of each level.
 */
public class PopulateRightNextPointers {

    /*
        Key is recursion already links the previous levels so when we reach next level access left and right
        and set their next pointers.
     */
    public Node connect(Node root) {
        recur(root);
        return root;
    }

    private void recur(Node root) {
        if(root==null)
            return;

        if(root.left!=null && root.right!=null)
            root.left.next = root.right;
        if(root.right!=null && root.next!=null)
            root.right.next = root.next.left;

        connect(root.left);
        connect(root.right);
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };
}