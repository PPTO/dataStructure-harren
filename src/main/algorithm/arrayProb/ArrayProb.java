package algorithm.arrayProb;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArrayProb {

    /**
     * Offer 03. 数组中重复的数字（难！）
     */
    //空间复杂度 O(n)
    public int findRepeatNumber(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i])){
                return nums[i];
            }
            else {
                set.add(nums[i]);
            }
        }
        return -1;
    }

    //原地交换：空间复杂度 O(1)
    public int findRepeatNumber1(int[] nums) {
        int i = 0;
        while (i < nums.length){
            if (nums[i] == i)
                i++;
            else if (nums[nums[i]] == nums[i])
                return nums[i];
            else {
                int tmp = nums[i];
                nums[i] = nums[tmp];
                nums[tmp] =  tmp;
            }
        }
        return -1;
    }


    /**
     * Offer 39. 数组中出现次数超过一半的数字 / Leecode 169. 多数元素
     */
    public int majorityElement(int[] nums) {
        // 排序，中间的数为超过一版的数
        List<Integer> collect = IntStream.of(nums).boxed().sorted().collect(Collectors.toList());
        return  collect.get(nums.length /2);
    }

    /**
     * Offer 39. 数组中出现次数超过一半的数字 / Leecode 169. 多数元素
     */
    public int majorityElement1(int[] nums) {
        // 摩尔投票，时间复杂度：O(n)
        int cur = -1;
        int vot = 0;
        for (int i = 0; i < nums.length; i++) {
            if (vot == 0){
                cur = nums[i];
                vot++ ;
            }
            else {
                vot = nums[i] == cur ? vot+1 : vot-1;
            }
        }
        return cur;
    }


    /**
     * Offer 53 - I. 在排序数组中查找数字 I
     * 遍历的时间复杂度：0(n)
     * 二分法时间复杂度：O(log_2 n)
     */
    public int search2(int[] nums, int target) {
        //二分法找最左目标值
        int h = 0, t = nums.length-1;
        while (h < t){
            int mid = (h + t) / 2;
            if (nums[mid] == target){
                t = mid;
            }
            else if (nums[mid] > target){
                t = mid -1;
            }
            else if (nums[mid] < target){
                h = mid +1;
            }
        }
        int res = 0;
        while (h < nums.length && nums[h] == target){
            res++;
            h++;
        }
        return res;
    }

    /**
     * 当你发现一道题可以用 遍历 来解决时， 可以尝试使用 二分法 来优化时间复杂度
     */

    /**
     * Offer 53 - II. 0～n-1中缺失的数字
     */
    public int missingNumber(int[] nums) {
        // 二分法找第一个（最左）未排序的位置
        int h = 0, t = nums.length-1;
        while (h < t){
            int mid = (h + t) / 2;
            if (nums[mid] != mid){
                t = mid;
            }
            else if (nums[mid] == mid){
                h = mid + 1;
            }
        }
        if (h == nums.length-1){
            return nums[h] == h ? h+1 : h;
        }
        return h;
    }

    /**
     *  Offer 57. 和为s的两个数字
     *  时间复杂度：O(n)
     */
    public int[] twoSum(int[] nums, int target) {
        // 左右两端的双指针
        int i = 0, j = nums.length-1;
        while (i != j){
            if (nums[i] + nums[j] < target){
                i++;
            }
            else if (nums[i] + nums[j] > target){
                j--;
            }
            else {
                break;
            }
        }
        int[] res = {nums[i], nums[j]};
        return res;
    }

    /**
     *  Offer 57 - II. 和为s的连续正数序列
     */
    public int[][] findContinuousSequence(int target) {
        //双指针，时间复杂度：o(n)
        ArrayList<int[]> lists = new ArrayList<>();
        int i = 1, j = 2, cur = 3, end = target/2 +1;
        while (i != j && j <= end){
            if (cur < target){
                j++;
                cur += j;
            }
            else if (cur > target){
                cur -=i;
                i++;
            }
            else if (cur == target){
                int[] list = new int[j - i + 1];
                for (int k = i; k <= j; k++) {
                    list[k-i] = k;
                }
                lists.add(list);
                cur -=i;
                i++;
            }
        }
        return lists.toArray(new int[lists.size()][]);
    }

    /**
     * Offer 59 - I. 滑动窗口的最大值（难）
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        ArrayList<Integer> res = new ArrayList<>();
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            while (!queue.isEmpty() && nums[i] > queue.peekLast()){
                queue.removeLast();
            }
            queue.addLast(nums[i]);
        }
        res.add(queue.get(0));
        for (int i = k; i < nums.length; i++) {
            //1. 先把前一个数删去
            if (!queue.isEmpty() && nums[i-k] == queue.peekFirst()){
                queue.removeFirst();
            }
            //2. 再去添加新的数
            while (!queue.isEmpty() && nums[i] > queue.peekLast()){
                queue.removeLast();
            }
            queue.addLast(nums[i]);
            res.add(queue.get(0));
        }
        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Offer 59 - II. 队列的最大值
     */
    class MaxQueue {

        ArrayList<Integer> list;
        //储存最大值
        LinkedList<Integer> queue;

        public MaxQueue() {
            list = new ArrayList<>();
            queue = new LinkedList<>();
        }

        public int max_value() {
            return queue.isEmpty() ? -1 : queue.peekFirst();
        }

        public void push_back(int value) {
            list.add(value);
            while (!queue.isEmpty() && queue.peekLast() < value){
                queue.removeLast();
            }
            queue.addLast(value);
        }

        public int pop_front() {
            if (list.isEmpty())
                return -1;
            /**
             * bug: integer 类型判断相等要用 equal ，不能用 “==”
             */
            int r = list.remove(0);
            if (r == queue.peekFirst()){
                queue.removeFirst();
            }
            return r;
        }
    }

    /**
     * Offer 61. 扑克牌中的顺子
     * 或者用： set数组判断数字的唯一性
     */
    public boolean isStraight(int[] nums) {
        //除0最大值
        int max = IntStream.of(nums).filter((t) -> !Objects.equals(t, 0)).max().getAsInt();
        //除0最小值
        int min = IntStream.of(nums).filter((t) -> !Objects.equals(t, 0)).min().getAsInt();

        long count1 = IntStream.of(nums).filter((t) -> !Objects.equals(t, 0)).distinct().count();

        long count2 = IntStream.of(nums).filter((t) -> !Objects.equals(t, 0)).count();

        return max - min < 5 && count2 == count1 ;
    }

    /**
     * Offer 31. 栈的压入、弹出序列
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        int i = 0;
        Stack<Integer> stack = new Stack<>();
        for (int j = 0; j < pushed.length; j++) {
            stack.add(pushed[j]);
            while (!stack.isEmpty() && i < popped.length && popped[i] == stack.peek()){
                i++;
                stack.pop();
            }
        }
        return stack.isEmpty() ? true : false;
    }

    /**
     * Offer 51. 数组中的逆序对
     */
    public int reversePairs(int[] nums) {
        // 归并排序
        return mergeSort(nums, 0, nums.length-1);
    }

    /**
     * 排序
     * @return 逆序对数
     */
    private int mergeSort(int[] nums, int h, int t){
        if (h >= t)
            return 0;
        int mid = (h + t)/2;
        int num = 0;
        int i = mergeSort(nums, h, mid);
        int j = mergeSort(nums, mid + 1, t);
        //优化! 不优化有一个示例会超时！
        if (nums[mid] <= nums[mid + 1])
            return i + j;
        int[] array = new int[nums.length];
        int m = h, n = mid + 1, o = h;
        while (m <= mid && n <= t){
            if (nums[n] < nums[m]){
                num += mid - m + 1;
                array[o++] = nums[n++];
            }
            else {
                array[o++] = nums[m++];
            }
        }
        while (m <= mid){
            array[o++] = nums[m++];
        }
        while (n <= t){
            array[o++] = nums[n++];
        }
        for (int k = h; k <=t; k++) {
            nums[k] = array[k];
        }
        return i + j + num;
    }

    /**
     * 归并法找出数组的最大 / 最小值
     */
    public int FindMin(int[] array){
        return findMin(array, 0, array.length - 1);
    }

    public int findMin(int[] array ,int low,int high){
        if (low == high)
            return array[low];
        int mid = (low + high) / 2;
        int min1 = findMin(array, low, mid);
        int min2 = findMin(array, mid + 1, high);
        return Math.min(min1, min2);
    }

    /**
     * Leecode 31. 下一个排列
     */
    public void nextPermutation(int[] nums) {
        int i ;
        for (i = nums.length-1; i >0; i--) {
            if (nums[i] > nums[i-1]){
                for (int j = nums.length-1; j >=i; j--) {
                    if (nums[j] > nums[i-1]){
                        int tmp = nums[j];
                        nums[j] = nums[i-1];
                        nums[i-1] = tmp;
                        break;
                    }
                }
                break;
            }
        }
        Arrays.sort(nums, i, nums.length);
    }


    /**
     * Leecode 134. 加油站（难！）
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        // 只要总油量大于等于总耗油量就肯定能跑完一圈
        int gasAdd = 0, costAdd = 0, cur = 0, begin = 0;
        for (int i = 0; i < gas.length; i++) {
            gasAdd += gas[i];
            costAdd += cost[i];
            cur += gas[i] - cost[i];
            if (cur < 0){
                cur = 0;
                begin = i+1;
            }
        }
        return gasAdd >= costAdd ? begin : -1;
    }

    /**
     * Leecode 15. 三数之和（难！）
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        // 排序 + 遍历 + 两数之和
        int[] array = IntStream.of(nums).sorted().toArray();
        for (int i = 0; i < array.length; i++) {
            int a = array[i];
            int l = i + 1, r = array.length - 1;
            while (l < r){
                if (array[l] + array[r] + a < 0)
                    l++;
                else if (array[l] + array[r] + a > 0)
                    r--;
                else {
                    list.add(Arrays.asList(a, array[l++], array[r--]));
                    //更新left 和 right
                    while (l < r && array[l] == array[l-1])
                        l ++;
                    while (l < r &&  array[r] == array[r +1])
                        r--;
                }
            }
            //找到下一个不相等的数
            while (i < array.length-1 && array[i] == array[i+1]){
                i++;
            }
        }
        return list;
    }




    /**
     * Leecode 752. 打开转盘锁
     */
    public int openLock(String[] deadends, String target) {
        // 必须用 bfs，不能用 dfs!!
        String cur = "0000";
        if (target.equals(cur))
            return 0;
//        List<String> dns_tmp = Arrays.asList(deadends);
//        ArrayList<String> dns = new ArrayList<>(dns_tmp);
        Set<String> dns = Stream.of(deadends).collect(Collectors.toSet());
        if (dns.contains(cur))
            return -1;
        dns.add(cur);
        ArrayList<String> queue = new ArrayList<>();
        queue.add(cur);
        int step = 0;
        while (!queue.isEmpty()){
            step++;
            /**
             * 巨错！
             * for (int i = 0; i < queue.size(); i++)
             */
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String s = queue.remove(0);
                for (int j = 0; j < 4; j++) {
                    String up = up(s, j);
                    if (up.equals(target)) {
                        return step;
                    }
                    if (!dns.contains(up)) {
                        dns.add(up);
                        queue.add(up);
                    }
                }
                for (int j = 0; j < 4; j++) {
                    String down = down(s, j);
                    if (down.equals(target)){
                        return step;
                    }
                    if (!dns.contains(down)){
                        dns.add(down);
                        queue.add(down);
                    }
                }
            }
        }
        return -1;
    }

    private String up(String s, int i){
        char[] chars = s.toCharArray();
        if (chars[i] == '9'){
            chars[i] = '0';
        }
        else {
            chars[i] +=1;
        }
        return String.valueOf(chars);
    }
    private String down(String s, int i){
        char[] chars = s.toCharArray();
        if (chars[i] == '0')
            chars[i] = '9';
        else
            chars[i] -= 1;
        return String.valueOf(chars);
    }



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
     * Leecode 200. 岛屿数量
     */
    public int numIslands(char[][] grid) {
        int[] X = {1, 0, -1, 0};
        int[] Y = {0, 1, 0, -1};
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1'){
                    numIs(grid, X, Y, i, j);
                    res ++;
                }

            }
        }
        return res;
    }
    private void numIs(char[][] grid, int[] X, int[] Y, int x, int y){
        //遍历所有连接的岛屿并将1 变为0
        if (grid[x][y] == '0')
            return;
        grid[x][y] = '0';
        for (int i = 0; i < 4; i++) {
            if (X[i] + x >=0 && X[i] + x < grid.length && Y[i] + y >=0 && Y[i] + y < grid[0].length)
                numIs(grid, X, Y, X[i]+x, Y[i]+y);
        }
    }


    /**
     * Leecode 463. 岛屿的周长
     */
    public int islandPerimeter(int[][] grid) {
        int[] X = {1, -1, 0, 0};
        int[] Y = {0, 0, 1, -1};
        int perimeter = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1){
                    for (int k = 0; k < 4; k++) {
                        if (X[k] + i < 0 || X[k] + i >= grid.length || Y[k] + j < 0 || Y[k] + j >= grid[0].length){
                            perimeter++;
                        }
                        else if (grid[X[k]+i][Y[k]+j] == 0){
                            perimeter++;
                        }
                    }
                }
            }
        }
        return perimeter;
    }

    /**
     * 使用递归记得用 返回值 记录结果
     */

    /**
     * Leecode 695. 岛屿的最大面积
     */
    public int maxAreaOfIsland(int[][] grid) {
        int[] X = {1, -1, 0, 0};
        int[] Y = {0, 0, 1, -1};
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1){
                    int tmp = mai(grid, X, Y, i, j);
                    max = max >= tmp ? max : tmp;
                }

            }
        }
        return max == Integer.MIN_VALUE ? 0 : max;
    }
    private int mai(int[][] grid, int[] X, int[] Y, int x, int y){
        if (grid[x][y] == 0){
            return 0;
        }
        int area = 0;
        if (grid[x][y] == 1){
            area++;
            grid[x][y] = 0;
        }
        for (int i = 0; i < 4; i++) {
            int x_new = X[i] + x;
            int y_new = Y[i] + y;
            if (x_new >=0 && x_new <grid.length && y_new >= 0 && y_new < grid[0].length){
                area += mai(grid,X, Y, x_new, y_new);
            }
        }
        return area;
    }

    /**
     * Leecode 135
     * 分发糖果
     */
    public int candy(int[] ratings) {
        // 类似于 接雨水，每个数字下标的值都需要相邻的的下标进行判定，因此思路即为：左遍历 + 右遍历
        int[] point = new int[ratings.length];
        point[0] = 1;
        // 右遍历
        for (int i = 1; i <point.length; i++) {
            point[i] = ratings[i] > ratings[i-1] ? point[i-1] + 1 : 1;
        }
        // 左遍历
        for (int i = point.length-2; i >= 0; i--) {
            point[i] = ratings[i] > ratings[i + 1] ? Math.max(point[i+1] + 1, point[i]) : point[i];
        }
        return IntStream.of(point).sum();
    }

    /**
     * Leecode 33. 搜索旋转排序数组
     */
    public int search3(int[] nums, int target) {
        int left = 0, right = nums.length-1;
        while (left <= right){
            int mid = (left + right)/2;
            if (nums[mid] == target){
                return mid;
            }
            // 左有序
            if (nums[mid] >= nums[left]){
                if (target < nums[mid] && target >= nums[left]){
                    right = mid -1;
                }
                else {
                    left = mid + 1;
                }
            }
           // 右有序
            if (nums[mid] < nums[right]){
                if (target > nums[mid] && target <= nums[right]){
                    left = mid + 1;
                }
                else {
                    right = mid -1;
                }
            }
        }
        return -1;
    }

    /**
     * Leecode 870. 优势洗牌 / 田忌赛马 （难！）
     */
    public int[] advantageCount(int[] nums1, int[] nums2) {
        // 贪心算法： 最小的对最小的，如果最小的对不上最小的，就对最大的
        // 难点：存储下标位置
        int length = nums1.length;
        Integer[] array1 = new Integer[length];
        Integer[] array2 = new Integer[length];
        for (int i = 0; i < length; i++) {
            array1[i] = i;
            array2[i] = i;
        }
        Arrays.sort(array1, (i, j) -> nums1[i] - nums1[j]);
        Arrays.sort(array2, (i, j) -> nums2[i] - nums2[j]);

        int[] ans = new int[length];
        int j = 0, k = length-1;
        for (int i = 0; i < length; i++) {
            int num = nums1[array1[i]];
            if (num > nums2[array2[j]]){
                ans[array2[j]] = num;
                j++;
            }
            else {
                // num <= nums2[array2[j]]
                ans[array2[k]] = num;
                k--;
            }
        }
        return ans;
    }

    /**
     * Leecode 1. 两数之和
     */
    public int[] twoSum1(int[] nums, int target) {
        // 排序 + 双指针
        int length = nums.length;
        Integer[] index = new Integer[length];
        for (int i = 0; i < length; i++) {
            index[i] = i;
        }
        Arrays.sort(index, (i, j) -> nums[i] - nums[j]);
        int[] ans = new int[2];
        int l = 0, r = length-1;
        while (l < r){
            if (nums[index[l]] + nums[index[r]] == target)
                return new int[]{index[l] , index[r]};
            else if (nums[index[l]] + nums[index[r]] > target)
                r--;
            else
                l++;
        }
        return null;
    }

    /**
     * Leecode 167. 两数之和 II - 输入有序数组
     */
    public int[] twoSum2(int[] numbers, int target) {
        //双指针
        int length = numbers.length;
        int l = 0, r = length-1;
        while (l < r){
            if (numbers[l] + numbers[r] == target)
                return new int[]{l + 1, r + 1};
            else if (numbers[l] + numbers[r] < target)
                l++;
            else
                r--;
        }
        return null;
    }

    /**
     * Leecode 88. 合并两个有序数组
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {


    }

    /**
     * 一个先递增再递减的数组，如何找到第K大的数。注.数字可以重复
     * [1，1，3，4，6，8，3，2，2，1]
     */
    public int findKthnum(int[] nums, int k){

        return -1;
    }







    /**
     * Leecode 75. 颜色分类
     */
    public void sortColors(int[] nums) {
        // 快排，也可以用双指针，时间复杂度 O(n)
        quicks(nums, 0, nums.length-1);
    }

    private void quicks(int[] nums, int left, int right){
        if (left >= right)
            return;
        int l = left, r = right;
        int tmp = nums[l];
        while (l < r){
            while (l < r && nums[r] >= tmp)
                r--;
            if (l < r)
                nums[l++] = nums[r];
            while (l < r && nums[l] < tmp)
                l++;
            if (l < r)
                nums[r--] = nums[l];
        }
        nums[l] = tmp;
        quicks(nums, left, l-1);
        quicks(nums, l + 1, right);
    }





    public static void main(String[] args) {
        ArrayProb arrayProb = new ArrayProb();

        int[][] x = {{1,1}};
        int i1 = arrayProb.islandPerimeter(x);
        System.out.println(i1);

        int[][] area = {{0,0,1,0,0,0,0,1,0,0,0,0,0},{0,0,0,0,0,0,0,1,1,1,0,0,0},{0,1,1,0,1,0,0,0,0,0,0,0,0},{0,1,0,0,1,1,0,0,1,0,1,0,0},{0,1,0,0,1,1,0,0,1,1,1,0,0},{0,0,0,0,0,0,0,0,0,0,1,0,0},{0,0,0,0,0,0,0,1,1,1,0,0,0},{0,0,0,0,0,0,0,1,1,0,0,0,0}};
        arrayProb.maxAreaOfIsland(area);

        int[] a = {-1,0,1,2,-1,-4};
        arrayProb.threeSum(a);

        int[] gas = {1, 2, 3, 4, 5};
        int[] cost = {3, 4, 5, 1, 2};
        int i = arrayProb.canCompleteCircuit(gas, cost);


        int[] arr1 = {2,7,11,15};
        int[] arr2 = {1,10,4,11};
        arrayProb.advantageCount(arr1, arr2);

    }
}