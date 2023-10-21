package algorithm;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
     * 剑指 Offer 32 - III. 二叉树层序遍历
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
     *Offer28 对称的二叉树 / Leecode 101. 对称二叉树
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
     * Offer 55 - I. 二叉树的深度 / Leecode 104. 二叉树的最大深度
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
     * Offer 55 - II. 平衡二叉树 / Leecode 110. 平衡二叉树
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

    /**
     * Leecode 111. 二叉树的最小深度
     */
    public int minDepth(TreeNode root) {
        if (root == null)
            return 0;
        int i = minDepth(root.left);
        int j = minDepth(root.right);
        if (i == 0 || j == 0)
            return j == 0 ? i + 1 : j + 1;
        else {
            return i > j ? j + 1 : i + 1;
        }
    }

    /**
     * Offer 37. 序列化二叉树（难！）/ Leecode 297
     */
    public String serialize(TreeNode root) {
        // 层序遍历、思路：怎么序列回来的，怎么序列回去。（前序遍历也可实现）
        if (root == null)
            return "[]";
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        String s = "[";
        while (!queue.isEmpty()){
            TreeNode node = queue.removeFirst();
            if (node == null){
                s += "null,";
            }
            else {
                queue.add(
                        node.left != null ? node.left : null
                );
                queue.add(
                        node.right != null ? node.right : null
                );
                s += (node.val + ",");
            }
        }
        return s.substring(0, s.length()-1) + "]";
    }

    public TreeNode deserialize(String data) {
        if (data.equals("[]"))
            return null;
        LinkedList<TreeNode> queue = new LinkedList<>();
        String[] strings = data.substring(1, data.length() - 1).split(",");
        TreeNode root = new TreeNode(Integer.parseInt(strings[0]));
        queue.add(root);
        int p = 1;
        while (!queue.isEmpty()){
            TreeNode node = queue.removeFirst();
            TreeNode left = null;
            if (!strings[p].equals("null")) {
                left = new TreeNode(Integer.parseInt(strings[p]));
                queue.add(left);
            }
            node.left = left;
            p++;
            TreeNode right = null;
            if (!strings[p].equals("null")) {
                right = new TreeNode(Integer.parseInt(strings[p]));
                queue.add(right);
            }
            node.right = right;
            p++;
        }
        return root;
    }

    /**
     * Leecode 226. 翻转二叉树
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null)
            return null;
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.left = invertTree(right);
        root.right = invertTree(left);
        return root;
    }

    /**
     * Leecode 114. 二叉树展开为链表
     */
    public void flatten(TreeNode root) {
        if (root == null)
            return;
        ArrayList<TreeNode> list = new ArrayList<>();
        po(root, list);
        for (int i = 0; i < list.size(); i++) {
            TreeNode node = list.get(i);
            node.left = null;
            if (i != list.size()-1)
                node.right = list.get(i+1);
        }
    }
    private void po(TreeNode root, ArrayList<TreeNode> list){
        if (root == null)
            return;
        list.add(root);
        po(root.left, list);
        po(root.right, list);
    }

    /**
     * Leecode 116. 填充每个节点的下一个右侧节点指针
     */
    public Node connect(Node root) {
        // 层次遍历，挺无聊的题
        if (root == null)
            return null;
        LinkedList<Node> list = new LinkedList<>();
        list.add(root);
        while (!list.isEmpty()){
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Node node = list.removeFirst();
                if (node.left !=null)
                    list.add(node.left);
                if (node.right != null)
                    list.add(node.right);
                if (i != size -1){
                    node.next = list.get(0);
                }
            }
        }
        return root;
    }

    /**
     * Leecode 654. 最大二叉树
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return cmb(nums,0, nums.length-1);
    }

    private TreeNode  cmb(int[] nums, int left, int right){
        if (left > right)
            return null;
        int max = left;
        for (int i = left + 1; i <= right; i++) {
            max = nums[max] >= nums[i] ? max : i;
        }
        TreeNode node = new TreeNode(nums[max]);
        node.left = cmb(nums, left, max-1);
        node.right = cmb(nums, max+1, right);
        return node;
    }

    /**
     * Leecode 105. 从前序与中序遍历序列构造二叉树
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        /**
         * 中左右
         * 左中右
         */
        if (preorder.length == 0 || inorder.length == 0)
            return null;
        return bt(preorder, inorder, 0, preorder.length - 1, 0, inorder.length-1);
    }
    private TreeNode bt(int[] preorder, int[] inorder, int pl, int pr, int il, int ir){
        if ( pl > pr || il > ir)
            return null;
        TreeNode node = new TreeNode(preorder[pl]);
        int i = 0;
        for (i = il; i < ir; i++) {
            if (inorder[i] == preorder[pl])
                break;
        }
        node.left = bt(preorder, inorder, pl+1, pl + (i - il), il, i - 1);
        node.right = bt(preorder, inorder, pl + i- il + 1, pr, i + 1, ir);
        return node;
    }


    /**
     * Leecode 106. 从中序与后序遍历序列构造二叉树
     */
    public TreeNode buildTree1(int[] inorder, int[] postorder) {
        /**
         * 左中右
         * 左右中
         */
        if (inorder.length == 0 || postorder.length == 0)
            return null;
        return bt1(inorder, postorder, 0, inorder.length - 1, 0, postorder.length-1);
    }
    private TreeNode bt1(int[] inorder, int[] postorder, int il, int ir, int pl, int pr){
        if (il > ir || pl > pr)
            return null;
        int p= postorder[pr];
        TreeNode node = new TreeNode(p);
        int i = -1;
        for (i = il; i <= ir; i++) {
            if (inorder[i] == p)
                break;
        }
        node.left = bt1(inorder, postorder, il, i-1, pl, pl + (i-il) -1);
        node.right = bt1(inorder, postorder, i + 1, ir, pl + (i - il), pr-1);
        return node;
    }

    /**
     * Leecode 652. 寻找重复的子树
     */
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        // （难点）遍历方式：只能用后序遍历
        ArrayList<TreeNode> list = new ArrayList<>();
        if (root == null)
            return list;
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        preo(root, list, map);
        return list;
    }
    private String preo(TreeNode node, List<TreeNode> list, ConcurrentMap<String, Integer> map){
        if (node == null)
            return "";
        String left = preo(node.left, list, map);
        String right = preo(node.right, list, map);
        String s = left + "," + right + "," + node.val;
        if (map.containsKey(s) && map.get(s) == 1) {
            list.add(node);
        }
        map.put(
                s, (map.containsKey(s) ? map.get(s): 0) + 1
        );
        return s;
    }

    /**
     * 使用递归方法进行遍历，需要创建新的方法进行遍历
     * 使用非递归方法进行遍历，可在原方法内进行
     */

    /**
     * Leecode 230. 二叉搜索树中第K小的元素
     */
    private int res_kths;
    private int k_kths;
    public int kthSmallest(TreeNode root, int k) {
        k_kths = k;
        kths(root);
        return res_kths;
    }
    private void kths(TreeNode root){
        if (root == null)
            return;
        kths(root.left);
        k_kths--;
        if (k_kths == 0)
            res_kths = root.val;
        kths(root.right);
    }

    /**
     * Leecode 538. 把二叉搜索树转换为累加树 / Leecode 1038
     */
    private int sum_cbst;
    public TreeNode convertBST(TreeNode root) {
        // 中序遍历
        cbst(root);
        return root;
    }
    private void cbst(TreeNode node){
        if (node == null)
            return;
        cbst(node.right);
        node.val = node.val + sum_cbst;
        sum_cbst = node.val;
        cbst(node.left);
    }

    // 无需 全局变量 版本（比较牛逼，建议多看看）
    public TreeNode convertBST01(TreeNode root) {
        dfs05(root, 0);
        return root;
    }
    private int dfs05(TreeNode root, int parentVal) {
        if (root == null)
            return parentVal;
        root.val += dfs05(root.right, parentVal);
        return dfs05(root.left, root.val);
    }

    /**
     * Leecode 98. 验证二叉搜索树 / Leecode 98. 验证二叉搜索树
     */
    private boolean isvbst = true;
    public boolean isValidBST(TreeNode root) {
        // 由于需要通过左子树 和 右子树 判断 当前节点，因此选择后序遍历（但其实也有先序遍历和中序遍历的解决方案，先序遍历更简单）
        isvbst(root);
        return isvbst;
    }
    //返回以 root 为根节点的二叉树的最大值和最小值
    public int[] isvbst(TreeNode root) {
        if (root.left == null && root.right == null)
            return new int[]{root.val, root.val};
        int[] left = null, right = null;
        if (root.left != null)
            left = isvbst(root.left);
        if (root.right != null)
            right = isvbst(root.right);
        if (left != null && left[1] >= root.val || right != null && right[0] <= root.val)
            isvbst = false;
        return new int[]{
                left != null ? left[0] : root.val,
                right != null ? right[1] : root.val};
    }

    /**
     * Leecode 700. 二叉搜索树中的搜索
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null)
            return null;
        if (root.val == val)
            return root;
        if (root.val < val)
            return searchBST(root.right, val);
        if (root.val > val)
            return searchBST(root.left, val);
        return null;
    }

    /**
     * Leecode 701. 二叉搜索树中的插入操作
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);
        ibst(root, val);
        return root;
    }
    private void ibst(TreeNode root, int val){
        if (root.val > val){
            if (root.left == null)
                root.left = new TreeNode(val);
            else
                ibst(root.left, val);
        }
        if (root.val < val){
            if (root.right == null)
                root.right = new TreeNode(val);
            else
                ibst(root.right, val);
        }
    }

    /**
     * Leecode 450. 删除二叉搜索树中的节点
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null)
            return null;
        if (root.val == key){
            if (root.right == null)
                return root.left;
            else {
                TreeNode tmp = root.right;
                while (tmp.left != null){
                    tmp = tmp.left;
                }
                tmp.left = root.left;
                return root.right;
            }
        }
        if (root.val < key){
            root.right = deleteNode(root.right, key);
        }
        if (root.val > key){
            root.left = deleteNode(root.left, key);
        }
        return root;
    }

    /**
     * Leecode 222. 完全二叉树的节点个数
     */
    public int countNodes(TreeNode root) {
        return -1;
    }

    /**
     * Leecode 199
     * 二叉树的右视图
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        ArrayList<Integer> res = new ArrayList<>();
        if (root == null)
            return res;
        list.add(root);
        while (!list.isEmpty()){
            int size = list.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = list.remove(0);
                if (node.left != null)
                    list.add(node.left);
                if (node.right != null)
                    list.add(node.right);
                if (i == size-1)
                    res.add(node.val);
            }
        }
        return res;
    }

    /**
     * 求二叉树根节点到叶子结点的路径和的最小值
     */
    public int minPath(TreeNode root){
        if (root == null)
            return 0;
        int left = minPath(root.left);
        int right = minPath(root.right);
        return 1 + Math.min(left, right);
    }

    /**
     * Leecode 124. 二叉树中的最大路径和
     */
    private int maxps = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        mps(root);
        return maxps;
    }
    // 返回以 root 为根节点的最大单边路径和
    private int mps(TreeNode root){
        if (root == null)
            return 0;
        int left = mps(root.left);
        int right = mps(root.right);
        int sum = root.val + (left < 0 ? 0 : left) + (right < 0 ? 0 : right);
        maxps = maxps > sum ? maxps : sum;
        int res =  root.val + Math.max((left < 0 ? 0 : left) , (right < 0 ? 0 : right));
        return res;
    }

    /**
     * Leecode 112. 路径总和
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null)
            return false;
        //判断是否为叶子节点
        if (root.left == null && root.right == null)
            return targetSum - root.val == 0;
        //否则不是叶子节点，进行递归
        return hasPathSum(root.left , targetSum-root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    /**
     * Leecode 113. 路径总和 II
     */
    public List<List<Integer>> pathSum1(TreeNode root, int targetSum) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null)
            return res;
        List<Integer> list = new ArrayList<>();
        ps1(root, targetSum, list, res);
        return res;
    }
    private void ps1(TreeNode root, int target, List<Integer> list, List<List<Integer>> res){
        if (root.left == null && root.right == null){
            if (root.val == target){
                list.add(root.val);
                res.add( new ArrayList<>(list));
                // 回溯
                list.remove(list.size()-1);
            }
            return;
        }
        list.add(root.val);
        if (root.left != null)
            ps1(root.left, target - root.val, list, res);
        if (root.right != null)
            ps1(root.right, target - root.val, list, res);
        // 回溯
        list.remove(list.size()-1);
    }


    /**
     * Leecode 129. 求根节点到叶节点数字之和
     */
    public int sumNumbers(TreeNode root) {
        // 前序遍历
        return sn(root, 0);
    }
    // 以 root 为根节点返回的数字之和
    private int sn(TreeNode root, int num){
        if (root.left == null && root.right == null){
            return num * 10 + root.val;
        }
        num = num * 10 + root.val;
        int left = 0, right = 0;
        if (root.left != null)
            left = sn(root.left, num);
        if (root.right != null)
            right = sn(root.right, num);
        return left + right;
    }

    /**
     * Leecode 543. 二叉树的直径
     */

    private int max_dbt = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        dbt(root);
        return max_dbt;
    }
    private int dbt(TreeNode root){
        // 以 root 为中间节点半边的长度
        if (root == null)
            return 0;
        int left = dbt(root.left);
        int right = dbt(root.right);
        int max = left + right;
        max_dbt = max_dbt > max ? max_dbt : max;
        return Math.max(left + 1, right + 1);
    }

    /**
     * Leecode 662. 二叉树最大宽度
     */
    public int widthOfBinaryTree(TreeNode root) {
        // 编号 + 层序遍历
        if (root == null)
            return 0;
        LinkedList<TreeNode> list = new LinkedList<>();
        HashMap<TreeNode, Integer> map = new HashMap<>();
        list.addLast(root);
        map.put(root,1);
        int max = 0;
        while (!list.isEmpty()){
            int left = 0, right = 0;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = list.removeFirst();
                Integer num = map.remove(node);
                if (left == 0){
                    left = num;
                    right = num;
                }
                else
                    right = num;
                if (node.left != null){
                    list.addLast(node.left);
                    map.put(node.left, num * 2);
                }
                if (node.right != null){
                    list.addLast(node.right);
                    map.put(node.right, num * 2 + 1);
                }
            }
            max = right - left + 1 > max ? right - left +1: max;
        }
        return max;
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

    public static void main(String[] args) {
        TreeProb treeProb = new TreeProb();
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
//        TreeNode node3 = new TreeNode(3);
//        TreeNode node4 = new TreeNode(0);
//        TreeNode node5 = new TreeNode(0);
//        TreeNode node6 = new TreeNode(0);
//        TreeNode node7 = new TreeNode(0);
//        TreeNode node8 = new TreeNode(0);
//        TreeNode node9 = new TreeNode(0);
        node1.left = node2;
//        node1.right = node3;
//        node2.left = node4;
//        node3.right = node5;
//        node4.left = node6;
//        node4.right = node7;
//        node5.left = node8;
//        node5.right = node9;
        treeProb.diameterOfBinaryTree(node1);
    }
}
