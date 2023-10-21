package algorithm.arrayProb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class DoublePointProb {

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
     * Leecode 15. 三数之和（难！）
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        // 排序 + 遍历 + 两数之和（双指针）
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
     * Leecode 1. 两数之和
     */
    public int[] twoSum1(int[] nums, int target) {
        // 排序 + 双指针, 或使用 map
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
     * 一个先递增再递减的数组，如何找到第 K 小的数。注.数字可以重复
     * [1，1，3，4，6，8，3，2，2，1]
     */
    public int findKthnum(int[] nums, int k){
        int l = 0, r = nums.length-1;
        while (l <= r){
            int min = Math.min(nums[l], nums[r]);
            if (--k == 0)
                return min;
            while (nums[l] == min)
                l++;
            while (nums[r] == min)
                r--;
        }
        return -1;
    }

    /**
     * Leecode 1004. 最大连续 1 的个数 III
     */
    public int longestOnes(int[] nums, int k) {
        return -1;
    }

    /**
     * Leecode 11. 盛最多水的容器
     */
    public int maxArea(int[] height) {
        // 双指针
        int i = 0, j = height.length-1;
        int ans = 0;
        while (i < j){
            int tmp = Math.min(height[i], height[j]) * (j-i);
            ans = Math.max(ans, tmp);
            if (height[i] >= height[j])
                j--;
            else
                i++;
        }
        return ans;
    }

    /**
     * Leecode 209. 长度最小的子数组
     */
    public int minSubArrayLen(int target, int[] nums) {
        //区间[left, right]
        int left = 0, right = 0;
        int cur = nums[0];
        int min = Integer.MAX_VALUE;
        while (right < nums.length){
            while (cur < target && ++right < nums.length) {
                cur += nums[right];
            }
            while (left <= right && cur >= target){
                min = Math.min(min, right - left+1);
                cur -= nums[left];
                left++;
            }
            if (left > right)
                right = left;
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    public static void main(String[] args) {
        DoublePointProb dpp = new DoublePointProb();
        int[] ints = {2,3,1,2,4,3};
        dpp.minSubArrayLen(7,ints);
    }


}
