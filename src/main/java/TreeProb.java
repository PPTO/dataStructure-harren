import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
     * Leecode 102. 二叉树的层序遍历 / 剑指 Offer 32 - II.
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
     * Leecode 102. 二叉树的层序遍历 / 剑指 Offer 32 - II.
     * plan: 递归
     * 注意. 该递归方法仅适用于结果返回值为 List<List<>> 时。
     */
    public List<List<Integer>> levelOrder1(TreeNode root) {
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
     * 剑指 Offer 32 - I. 从上到下打印二叉树
     */
    public int[] levelOrder2(TreeNode root) {
        if (root == null)
            return  new int[0];
        ArrayList<Integer> res = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        int level = 1;
        queue.add(root);
        while (!queue.isEmpty()){
            int newLevel = 0;
            for (int i = 0; i < level; i++) {
                TreeNode node = queue.remove();
                res.add(node.val);
                if (node.left != null){
                    queue.add(node.left);
                    newLevel++;
                }
                if (node.right != null){
                    queue.add(node.right);
                    newLevel++;
                }
            }
            level = newLevel;
        }
        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * 剑指 Offer 32 - III. 二叉树层序遍历（难!）
     * 左->右
     * 右->左
     * 左->右
     */
    public List<List<Integer>> levelOrder3(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null)
            return res;
        int flag = 0;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int level = 1;
        while (!queue.isEmpty()){
            ArrayList<Integer> list = new ArrayList<>();
            while (level-- >0){
                TreeNode node = queue.remove();
                list.add(node.val);
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
            level = queue.size();
            if (flag == 1)
                Collections.reverse(list);
            res.add(list);
            flag = (flag + 1) % 2;
        }
        return res;
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

    /**
     *Offer54 二叉搜索树的第k大节点
     */
    private int KthRes, k;
    public int kthLargest(TreeNode root, int k) {
        this.k = k;
        inorder(root);
        return KthRes;
    }
    private void inorder(TreeNode root){
        if (root.right != null)
            inorder(root.right);
        k--;
        if (k == 0){
            KthRes = root.val;
            return;
        }
        if (root.left != null){
            inorder(root.left);
        }
    }

    /**
     * Offer 55 - I. 二叉树的深度
     */
    public int maxDepth(TreeNode root) {
        int depth = 0;
        if (root == null) {
            return depth;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            int size = queue.size();
            while (size-- >0){
                TreeNode node = queue.remove();
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
            depth++;
        }
        return depth;
    }

    /**
     * Offer 55 - II. 平衡二叉树
     */
    private boolean balanceFlag = true;
    public boolean isBalanced(TreeNode root) {
        if (root == null)
            return true;
        judgeBalanced(root);
        return balanceFlag;
    }

    /**
     * 后序遍历
     */
    private int judgeBalanced(TreeNode root){
        int i = 0, j = 0;
        if (root.left != null){
            i = judgeBalanced(root.left);
        }
        if (root.right != null){
            j = judgeBalanced(root.right);
        }
        if (Math.abs(i - j) >1)
            balanceFlag = false;
        return Math.max(i, j) + 1;
    }

    /**
     *  Offer 68 - I. 二叉搜索树的最近公共祖先
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode low = p.val > q.val? q : p;
        TreeNode high= p.val > q.val? p : q;
        if (root.val >= low.val && root.val <= high.val)
            return root;
        TreeNode node;
        if (high.val < root.val) {
            node = lowestCommonAncestor(root.left, p, q);
        }
        else {
           // p.val > root.val
            node = lowestCommonAncestor(root.right, p, q);
        }
        return node;
    }

    /**
     * Offer 68 - II. 二叉树的最近公共祖先 / Leecode236 二叉树的最近公共祖先
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        //后序遍历
        if (root == null)
            return null;
        TreeNode n1 = null, n2 = null;
        if (root.left != null) {
            n1 = lowestCommonAncestor2(root.left, p, q);
        }
        if (root.right != null) {
            n2 = lowestCommonAncestor2(root.right, p, q);
        }
        if (root.val == p.val || root.val == q.val || n1 != null && n2 != null)
            return root;
        if (n1 != null || n2 != null){
            return n1 != null ? n1 : n2;
        }
        return null;
    }

    /**
     * Offer 26. 树的子结构
     */
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A == null || B == null)
            return false;
        if (A.val == B.val){
            boolean b = isSubStr(A, B);
            if (b == true)
                return true;
        }
        boolean b1 = isSubStructure(A.left, B);
        boolean b2 = isSubStructure(A.right, B);
        return b1 || b2;
    }
    private boolean isSubStr(TreeNode a, TreeNode b){
        if (b == null)
            return true;
        else if (a != null && b != null){
            if (a.val != b.val) {
                return false;
            }
            boolean b1 = isSubStr(a.left, b.left);
            boolean b2 = isSubStr(a.right, b.right);
            return b1 && b2;
        }
        else {
            //其中一个不为空
            return false;
        }
    }

    /**
     *  Offer 33. 二叉搜索树的后序遍历序列
     */
    public boolean verifyPostorder(int[] postorder) {
        return verifyPost(postorder, 0, postorder.length-1);
    }
    private boolean verifyPost(int[] postorder, int head, int tail){
        if ( head >= tail)
            return true;
        int length = postorder.length;
        int i = head, j = tail-1;
        while (i <= j && postorder[i] < postorder[tail])
            i++;
        int k = i;
        while (i < tail){
            if (postorder[i] > postorder[tail])
                i++;
            else
                return false;
        }
        boolean b = verifyPost(postorder, head, k - 1);
        boolean b1 = verifyPost(postorder, k, tail - 1);
        return b && b1;
    }

    /**
     * Offer 34. 二叉树中和为某一值的路径
     */
    private List<List<Integer>> lists = new ArrayList<>();
    private List<Integer> list = new ArrayList<>();
    public List<List<Integer>> pathSum(TreeNode root, int target) {
        if (root == null)
            return lists;
        pathSummary(root, target);
        return lists;
    }
    private void pathSummary(TreeNode root, int target){
        //前序遍历：中左右
        if (root.val == target && root.left == null && root.right == null){
            list.add(root.val);
            ArrayList<Integer> tmp = new ArrayList<>(list);
            lists.add(tmp);
            list.remove(list.size()-1);
            return;
        }
        list.add(root.val);
        if (root.left != null)
            pathSummary(root.left, target - root.val);
        if (root.right != null)
            pathSummary(root.right, target - root.val);
        // 还原
        list.remove(list.size()-1);
    }

    /**
     * Offer 36. 二叉搜索树与双向链表
     */
    ArrayList<Node> nodes = new ArrayList<>();
    public Node treeToDoublyList(Node root) {
        if (root == null)
            return null;
        treeToDouble(root);
        for (int i = 0; i < nodes.size(); i++) {
            Node tmp = nodes.get(i);
            tmp.left = nodes.get((i-1+nodes.size())%nodes.size());
            tmp.right = nodes.get((i+1)%nodes.size());
        }
        return nodes.get(0);
    }
    private void treeToDouble(Node root){
        // 中序遍历
        if (root == null)
            return;
        treeToDouble(root.left);
        nodes.add(root);
        treeToDouble(root.right);
    }



    private static class TreeNode{
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
    class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val,Node _left,Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }

    public static void main(String[] args) {
        TreeProb treeProb = new TreeProb();
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        TreeNode node5 = new TreeNode(4);
        treeProb.isSubStructure(node1, node5);
    }
}
