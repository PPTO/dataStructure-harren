package algorithm;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 数组问题包括：
 * 1. 排序问题
 * 2. 查找问题
 *  2.1 最大值最小化
 *  2.2 最小值最大化
 *
 * 注. 对于 “找前k个数” 这种不需要全排序的问题，统一可以用堆排序解决，其中JDK的堆排序实现为 priorityQueue
 */
public class ArrayProb {

    /**
     * Offer 21. 调整数组顺序使奇数位于偶数前面
     */
    public int[] exchange(int[] nums) {
        if (nums == null || nums.length == 0)
            return nums;
        int head = 0, tail = nums.length -1;
        int tmp = nums[head];
        while (head < tail){
            while (head < tail && nums[tail] %2 == 0){
                tail--;
            }
            if (head < tail){
                nums[head++] = nums[tail];
            }
            while (head < tail && nums[head] % 2 == 1){
                head++;
            }
            if (head < tail){
                nums[tail--] = nums[head];
            }
        }
        nums[head] = tmp;
        return nums;
    }

    /**
     *  Offer 40. 最小的k个数
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        return IntStream.of(arr).boxed()
                .sorted((t1, t2)-> t1 - t2)
                .limit(k)
                .mapToInt(t -> t.intValue())
                .toArray();
    }

    /**
     * Offer 45. 把数组排成最小的数（难！最骚的题）
     */
    public String minNumber(int[] nums) {
        quickSort(nums, 0, nums.length-1);
        String s = "";
        for (int num : nums) {
            s += num;
        }
        return s;
    }
    private void quickSort(int[] nums, int head, int tail){
        if (head >= tail)
            return;
        int h = head, t = tail, tmp = nums[head];
        while (h < t){
            while ( h < t
                    &&
                    ("" + nums[t] + tmp).compareTo("" + tmp + nums[t]) > 0
            ){
                t--;
            }
            if (h < t)
                nums[h++] = nums[t];
            while (h < t
                    &&
                    ("" + nums[h] + tmp).compareTo("" + tmp + nums[h]) <=0
            ){
                h++;
            }
            if (h < t)
                nums[t--] = nums[h];
        }
        nums[h] = tmp;
        quickSort(nums, head, h-1);
        quickSort(nums, h+1, tail);
    }

    /**
     * 215. 数组中的第K个最大元素
     * 要求：时间复杂度为 O(n)
     */
    public int findKthLargest(int[] nums, int k) {
        //解决：快速排序
        return IntStream.of(nums).boxed()
                .sorted((t1, t2) -> t2 - t1)
                .collect(Collectors.toList()).get(k-1);
    }

    /**
     * Offer 11. 旋转数组的最小数字
     */
    public int minArray(int[] numbers) {
        // 二分查找
        int head = 0, tail = numbers.length -1;
        while (head < tail){
            int mid = (head + tail) /2;
            if (numbers[mid] > numbers[tail]){
                head = mid +1;
            }
            else if (numbers[mid] < numbers[tail]){
                tail = mid;
            }
            else {
                tail--;
            }
        }
        return numbers[head];
    }


    /**
     * 二分查找的多种考查形式：
     * 1.查找是否存在目标值（最基础的类型）
     * 2.数组中存在重复值
     *  2.1查找目标值的左侧边界索引
     *  2.2查找目标值的右侧边界索引
     * 3.查找目标值在数组中的个数（2.1+2.2）
     */

    /**
     * Leecode 704. 二分查找
     * 类型一
     * （元素不重复）
     */
    public int search(int[] nums, int target) {
        int h = 0, t = nums.length-1;
        while (h <= t){
            int mid = (h+ t)/2;
            if (nums[mid] == target){
                return  mid;
            }
            if (nums[mid] > target){
                t = mid -1;
            }
            if (nums[mid] < target){
                h = mid +1;
            }
        }
        return -1;
    }

    /**
     * Leecode 34. 在排序数组中查找元素的第一个和最后一个位置
     * 类型三
     * 时间复杂度为 O(log n)
     */
    public int[] searchRange(int[] nums, int target) {
        int[] res = {-1, -1};
        if (nums.length == 0)
            return res;
        //1. 查找开始位置
        res[0] = searchLeftandRight1(nums, target, -1);
        //2. 查找结束位置
        res[1] = searchLeftandRight1(nums, target, 1);
        return res;
    }

    public int searchLeftandRight1(int[] nums, int target, int flag){
        int h = 0, t = nums.length-1;
        while (h <= t){
            int mid = (h+t)/2;
            if (nums[mid] > target)
                t = mid -1;
            if (nums[mid] < target)
                h = mid +1;
            if (nums[mid] == target){
                if (flag == -1){
                    if (mid-1 >=0 && nums[mid-1] == target)
                        t = mid-1;
                    else
                        return mid;
                }
                if (flag == 1){
                    if (mid+1 < nums.length && nums[mid+1] == target)
                        h = mid+1;
                    else
                       return mid;
                }
            }
        }
        return -1;
    }

    /**
     *  Offer 53 - I. 在排序数组中查找数字 I
     *  类型三
     */
    public int search1(int[] nums, int target) {
        //1. 查找开始位置
        int i = searchLeftandRight(nums, target, -1);
        //2. 查找结束位置
        int j = searchLeftandRight(nums, target, 1);
        return i == -1 ? 0 : j-i +1;
    }

    //该解法的时间复杂度大于 O(log_2n)
    public int searchLeftandRight(int[] nums, int target, int flag){
        int h = 0, t = nums.length-1;
        while (h <= t){
            int mid = (h+t)/2;
            if (nums[mid] > target){
                t = mid -1;
            }
            if (nums[mid] < target){
                h = mid +1;
            }
            if (nums[mid] == target){
                if (flag == -1){
                    while (mid-1 >= 0 && nums[mid-1] == target){
                        mid--;
                    }
                    return mid;
                }
                if (flag == 1){
                    while (mid+1 <= nums.length-1 && nums[mid+1] == target){
                        mid++;
                    }
                    return mid;
                }
            }
        }
        return -1;
    }

    /**
     * 二分法：
     * 若 while(low < high)，则可以跟 high = mid，在 while 后根据 low 进行 return
     * 若 while(low <= high)，则需要跟 high = mid -1，在 while 里 retuen。
     * low 必须是 low = mid +1;
     */

    /**
     * 对于最大值最小化、最小值最大化的问题，解题思路都为：先确定二分法的遍历范围，再二分法 + 枚举。
     * 这类问题完全可以用一种套路解决。
     */

    /**
     * Leecode 875. 爱吃香蕉的珂珂
     * 最大化最小值问题
     */
    public int minEatingSpeed(int[] piles, int h) {
        int tail = IntStream.of(piles).max().getAsInt();
        int head = 1;
        while (head < tail){
            int mid = (head + tail) / 2;
            if (canEatAll(piles, h, mid)){
                tail = mid;
            }
            else {
                head = mid +1;
            }
        }
        return head;
    }
    private boolean canEatAll(int[] piles, int h, int mid){
        for (int i = 0; i < piles.length; i++) {
            h -= piles[i] % mid == 0 ? piles[i] / mid : piles[i] / mid + 1;
        }
        return h < 0 ? false : true;
    }


    /**
     * Leecode 1011. 在 D 天内送达包裹的能力
     *最大化最小值
     */
    public int shipWithinDays1(int[] weights, int days) {
        int min = IntStream.of(weights).max().getAsInt();
        int max = IntStream.of(weights).boxed().collect(Collectors.summingInt(Integer::intValue));
        while (min < max){
            int mid = (min + max) / 2;
            if (canMinShip(weights, days, mid)){
                max = mid;
            }
            else {
                min = mid + 1;
            }
        }
        return min;
    }
    private boolean canMinShip(int[] weights, int days, int mid){
        int tmp = mid;
        for (int i = 0; i < weights.length; i++) {
            if (tmp >= weights[i]){
                tmp -= weights[i];
            }
            else {
                tmp = mid - weights[i];
                days--;
            }
        }
        days--;
        return days >= 0 ? true: false;
    }

    //该方法的判断条件有些复杂，有些时候如果发现判断条件过多，那么就说明应该换一个方案了。
    public int shipWithinDays(int[] weights, int days) {
        int[] max = {Integer.MIN_VALUE};
        IntStream.of(weights).boxed().forEach(t1 -> max[0] = t1 > max[0] ? t1 : max[0]);
        int[] count = {Integer.MAX_VALUE};
        IntStream.of(weights).boxed().forEach(t1 ->count[0] += t1);
        int l = max[0], h = count[0];
        while (l<h){
            int mid = (l+ h)/2, tmp = days, i = 0;
            while (tmp >0 && i <weights.length){
                if ( mid > weights[i]){
                    mid -= weights[i];
                    i++;
                }
                if (mid <= weights[i]){
                    tmp--;
                    i++;
                    mid = (l+ h)/2;
                }
            }
            if (tmp > 0){
                h = (l+ h)/2 -1;
            }
            if (tmp == 0 && i == weights.length){
                h = (l+ h)/2;
            }
            if (tmp == 0 && i < weights.length){
                l =  (l+ h)/2 +1;
            }
        }
        return l;
    }


    /**
     * Leecode. 410 分割数组的最大值
     * 最大值最小化问题
     */
    public int splitArray(int[] nums, int k) {
        int max = IntStream.of(nums).boxed().collect(Collectors.summingInt(Integer::intValue));
        int min = IntStream.of(nums).max().getAsInt();
        while (min < max){
            int mid = (min + max) / 2;
            if (canSplit(nums, k, mid)){
                max = mid;
            }
            else {
                min = mid + 1;
            }
        }
        return min;
    }
    private boolean canSplit(int[] nums, int k, int mid){
        int tmp = mid;
        for (int i = 0; i < nums.length; i++) {
            if (tmp >= nums[i]){
                tmp -= nums[i];
            }
            else {
                tmp = mid - nums[i];
                k--;
            }
        }
        k--;
        return k <0 ? false : true;
    }

    /**
     * Offer 03. 数组中重复的数字
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

    //空间复杂度 O(1)
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


















    public static void main(String[] args) {
        ArrayProb arrayProb = new ArrayProb();

        int[][] x = {{1,1}};
        int i1 = arrayProb.islandPerimeter(x);
        System.out.println(i1);

        int[][] area = {{0,0,1,0,0,0,0,1,0,0,0,0,0},{0,0,0,0,0,0,0,1,1,1,0,0,0},{0,1,1,0,1,0,0,0,0,0,0,0,0},{0,1,0,0,1,1,0,0,1,0,1,0,0},{0,1,0,0,1,1,0,0,1,1,1,0,0},{0,0,0,0,0,0,0,0,0,0,1,0,0},{0,0,0,0,0,0,0,1,1,1,0,0,0},{0,0,0,0,0,0,0,1,1,0,0,0,0}};
        arrayProb.maxAreaOfIsland(area);

    }
}