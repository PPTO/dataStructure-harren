package algorithm.arrayProb;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArrayProb {

    /**
     * Offer 03. 数组中重复的数字（难！）
     */
    public int findRepeatNumber(int[] nums) {
        // 原地交换：空间复杂度 O(1)
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
     * Leecode 88. 合并两个有序数组
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 重点：从后往前
        int i = m-1, j = n-1, k = m + n -1;
        while (j >= 0){
            if (i < 0 || nums1[i] <= nums2[j])
                nums1[k--] = nums2[j--];
            else
                nums1[k--] = nums1[i--];
        }
    }

    /**
     * Leecode 1109. 航班预订统计
     */
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] ans = new int[n];
        for (int i = 0; i < bookings.length; i++) {
            for (int j = bookings[i][0] - 1; j < bookings[i][1]; j++) {
                ans[j] += bookings[i][2];
            }
        }
        return ans;
    }

    /**
     * Leecode 739. 每日温度
     */
    public int[] dailyTemperatures(int[] temperatures) {
        // 重点：存储下标
        int[] ans = new int[temperatures.length];
        Stack<int[]> stack = new Stack<>();
        for (int i = temperatures.length-1; i >=0; i--) {
            while (!stack.isEmpty() && temperatures[i] >= stack.peek()[0])
                stack.pop();
            ans[i] = stack.isEmpty() ? 0 : stack.peek()[1] - i;
            stack.add(new int[]{temperatures[i], i});
        }
        return ans;
    }

    /**
     * Leecode 554. 砖墙
     */
    public int leastBricks(List<List<Integer>> wall) {
        // 题目转化为找最多的缝隙
        HashMap<Integer, Integer> map = new HashMap<>();
        for (List<Integer> list : wall) {
            int j = 0;
            for (int i = 0; i < list.size() - 1; i++) {
                j += list.get(i);
                map.put(j, map.get(j) == null ? 1 : map.get(j) + 1);
            }
        }
        int length = wall.size();
        int ans = 0;
        for (Integer integer : map.keySet()) {
            ans = Math.max(ans, map.get(integer));
        }
        return length - ans;
    }

    /**
     * Leecode 560. 和为 K 的子数组
     */
    public int subarraySum(int[] nums, int k) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k)
                    ans++;
            }
        }
        return ans;
    }

    /**
     * Leecode 162. 寻找峰值
     */
    public int findPeakElement(int[] nums) {
        if (nums.length == 1)
            return 0;
        for (int i = 1; i < nums.length - 1; i++) {
            if (nums[i] > nums[i-1] && nums[i] > nums[i+1])
                return i;
        }
        if (nums[0] > nums[1])
            return 0;
        if (nums[nums.length-1] > nums[nums.length-2])
            return nums.length-1;
        return 0;
    }

    /**
     * Leecode 14. 最长公共前缀
     */
    public String longestCommonPrefix(String[] strs) {
        String s = "";
        int flag = 0;
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i >= strs[j].length() || strs[j].charAt(i) != c){
                    flag = 1;
                    break;
                }
            }
            if (flag == 1)
                break;
            else {
                s += c;
            }
        }
        return s;
    }

    /**
     * Leecode 152. 乘积最大子数组（难！）
     */
    public int maxProduct(int[] nums) {
        // 以当前下标结尾的最大值
        int imax = nums[0];
        // 以当前下标结尾的最小值
        int imin = nums[0];
        // 最终结果
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < 0){
                // 交换
                int tmp = imax;
                imax = imin;
                imin = tmp;
            }
            imax = Math.max(nums[i], imax * nums[i]);
            imin = Math.min(nums[i], imin * nums[i]);
            max = Math.max(max, imax);
        }
        return max;
    }

    /**
     * Leecode 283. 移动零（hard！！）
     */
    public void moveZeroes(int[] nums) {

    }




    public static void main(String[] args) {
        ArrayProb arrayProb = new ArrayProb();


        int[] gas = {1, 2, 3, 4, 5};
        int[] cost = {3, 4, 5, 1, 2};
        int i = arrayProb.canCompleteCircuit(gas, cost);


        int[] arr1 = {0,0,0,0,0};
        int[] arr2 = {1,2,3,4,5};
        arrayProb.merge(arr1,0,arr2,5);

        int[] ints = {2,3,-2,-4};
        arrayProb.maxProduct(ints);
    }
}