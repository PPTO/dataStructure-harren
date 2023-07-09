import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class TreeProb {

    /**
     * Leecode 144
     * 二叉树的前序遍历：中左右
     * 递归
     */
    List<Integer> res = new ArrayList<>();
    public List<Integer> preorderTraversal(TreeNode root) {
        if (root == null)
            return res;
        res.add(root.val);
        preorderTraversal(root.left);
        preorderTraversal(root.right);
        return res;
    }

    /**
     * Leecode 144
     * 二叉树的前序遍历：中左右
     * 非递归
     */
    public List<Integer> preorderTraversal2(TreeNode root) {
        TreeNode tmp;
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> res = new ArrayList<>();
        if (root == null)
            return res;
        stack.add(root);
        while (!stack.isEmpty()){
            tmp = stack.pop();
            if (Objects.nonNull(tmp.right))
                stack.add(tmp.right);
            if (Objects.nonNull(tmp.left))
                stack.add(tmp.left);
            res.add(tmp.val);
        }
        return res;
    }

    /**
     * Leecode 94
     * 二叉树的中序遍历 左中右
     * 递归
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null){
            return res;
        }
        inorderTraversal(root.left);
        res.add(root.val);
        inorderTraversal(root.right);
        return res;
    }

    /**
     * Leecode 94
     * 二叉树的中序遍历 左中右
     * 非递归
     */
    public List<Integer> inorderTraversal2(TreeNode root) {

    }

    /**
     * Leecode 145
     * 二叉树的后序遍历 左右中
     * 递归
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        if (root == null)
            return res;
        postorderTraversal(root.left);
        postorderTraversal(root.right);
        res.add(root.val);
        return res;
    }

    class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(){}
        TreeNode(int val){
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right){
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    public static void main(String[] args) {
        TreeProb treeProb = new TreeProb();
    }
}
