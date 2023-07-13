import java.util.*;

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
     * 非递归：栈 + 一个变量
     */
    public List<Integer> inorderTraversal2(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        ArrayList<Integer> res = new ArrayList<>();
        if (root == null)
            return res;
        TreeNode p = root;
        while ( p != null || !stack.isEmpty()){
            //将p以及p的左节点全部压栈
            while (p !=null){
                stack.add(p);
                p = p.left;
            }
            //每一轮出一个节点
            TreeNode temp = stack.pop();
            res.add(temp.val);
            p = temp.right;
        }
        return res;
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

    /**
     * Leecode 145
     * 二叉树的后序遍历 左右中
     * 非递归：两个栈
     */
    public List<Integer> postorderTraversal2(TreeNode root) {
        LinkedList<Integer> res = new LinkedList<>();
        if (root == null)
            return res;
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()){
            TreeNode node = stack.pop();
            if (node.left != null)
                stack.add(node.left);
            if (node.right != null)
                stack.add(node.right);
            res.addFirst(node.val);
        }
        return res;
    }

    /**
     * Leecode 102. 二叉树的层序遍历 / 剑指 Offer 32 - II. 从上到下打印二叉树 II
     * plan: 非递归
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        ArrayList<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        ArrayList<Integer> list;
        queue.add(root);
        int levelNum = 1;
        TreeNode tmp;
        while (!queue.isEmpty()){
             list = new ArrayList<>();
            while (levelNum -- >0){
                tmp = queue.removeFirst();
                list.add(tmp.val);
                if (tmp.left != null)
                    queue.addLast(tmp.left);
                if (tmp.right != null)
                    queue.addLast(tmp.right);
            }
            levelNum = queue.size();
            res.add(list);
        }
        return res;
    }

    /**
     * Leecode 102. 二叉树的层序遍历 / 剑指 Offer 32 - II. 从上到下打印二叉树 II (难！)
     * plan: 递归
     * 注意. 该递归方法仅适用于结果返回值为 List<List<>> 时。
     */
    public List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null)
            return res;
        levelOrder(root, res, 0);
        return res;
    }
    public void levelOrder(TreeNode root, List<List<Integer>> res, int level){
        if (res.size() == level)
            res.add(level,new ArrayList<>());
        res.get(level).add(root.val);
        if (root.left != null)
            levelOrder(root.left, res, level + 1);
        if (root.right != null)
            levelOrder(root.right, res, level + 1);
    }

    /**
     * Offer27
     * 二叉树的镜像
     */
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null)
            return root;
        TreeNode tmp = root.left;
        root.left = mirrorTree(root.right);
        root.right = mirrorTree(tmp);
        return root;
    }

    /**
     *Offer28
     * 对称的二叉树
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        return isSymmetric(root.left, root.right);
    }

    public boolean isSymmetric(TreeNode left, TreeNode right) {
        if (left == null && right == null)
            return true;
        if (left == null || right == null || left.val != right.val)
            return false;
        return isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);
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
