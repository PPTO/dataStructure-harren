package algorithm.arrayProb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DfsProb {
    /**
     * dfs 要搭配 回溯
     */

    /**
     * Leecode 46. 全排列
     */
    public List<List<Integer>> permute(int[] nums) {
        // 经典 dfs
        List<List<Integer>> lists = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();
        List<Integer> list = new ArrayList<>();
        per(nums, lists, list, set);
        return lists;
    }
    private void per(int[] nums, List<List<Integer>> lists, List<Integer> list, HashSet<Integer> set){
        if (list.size() == nums.length){
            lists.add(new ArrayList<Integer>(list));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            int tmp = nums[i];
            if (!set.contains(tmp)){
                set.add(tmp);
                list.add(tmp);
                // 递归
                per(nums,lists,list,set);
                //回溯
                set.remove(tmp);
                list.remove(list.size()-1);
            }
        }
    }

    /**
     * Leecode 78. 子集
     * 输入：nums = [1,2,3]
     * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<Integer> list = new ArrayList<>();
        List<List<Integer>> lists = new ArrayList<>();
        subset(nums,list,lists,0);
        return lists;
    }
    private void subset(int[] nums, List<Integer> list, List<List<Integer>> lists,int j){
        lists.add(new ArrayList<>(list));
        for (int i = j; i < nums.length; i++) {
            int num = nums[i];
            list.add(num);
            subset(nums, list, lists,i+1);
            //回溯
            list.remove(list.size()-1);
        }
    }

    /**
     * Leecode 77. 组合
     * 输入：n = 4, k = 2
     * 输出：
     * [
     *   [2,4],
     *   [3,4],
     *   [2,3],
     *   [1,2],
     *   [1,3],
     *   [1,4],
     * ]
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        com(lists, list, n, k);
        return lists;
    }

    private void com(List<List<Integer>> lists, List<Integer> list, int n, int k){
        if (k == 0){
            lists.add(new ArrayList<>(list));
            return;
        }
        for (int i = n; i >0; i--) {
            list.add(i);
            com(lists, list, i-1, k-1);
            list.remove(list.size()-1);
        }
    }

    /**
     * Leecode 22. 括号生成 （难！）
     */
    public List<String> generateParenthesis(int n) {
        // 问题转换成：现在有 2n 个位置，每个号组合中，有多少个是合法的？
        ArrayList<String> list = new ArrayList<>();
        String s = "";
        gp(n, n, list, s);
        return list;
    }

    /**
     * @param n 当前 ‘（’ 的数量
     * @param m 当前 ‘）’ 的数量
     */
    private void gp(int n, int m, List<String> list, String s){
        if (n == 0 && m == 0 && !list.contains(s)){
            list.add(s);
            return;
        }
        if (n < 0 || m < 0){
            return;
        }
        if ( m >= n ){
            s += "(";
            gp(n-1, m, list, s);
            // 回溯
            s = s.substring(0, s.length() - 1);
            s += ")";
            gp(n, m-1, list, s);
            // 回溯
            s = s.substring(0, s.length()-1);
        }
    }

    /**
     * Leecode 39. 组合总和（难！）
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        cs(candidates, target, 0, list, ans);
        return ans;
    }


    private void cs(int[] candidates, int target, int start, List<Integer> list, List<List<Integer>> ans){
        if (target == 0){
            ans.add(new ArrayList<>(list));
            return;
        }
        if (target < 0)
            return;
        for (int i = start; i < candidates.length; i++) {
            target -= candidates[i];
            list.add(candidates[i]);
            /**
             * Important! 为保证结果的唯一性，start 从 i 开始遍历
             */
            cs(candidates, target, i, list, ans);
            // 回溯
            target += candidates[i];
            list.remove(list.size()-1);
        }
    }
}
