import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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





    public static void main(String[] args) {
        ArrayProb arrayProb = new ArrayProb();
//        arrayProb.searchRange(new int[]{5,7,7,8,8,10}, 8);

        ArrayList<Integer> arrayList = new ArrayList<>();
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        int[] ints = {1, 2, 3, 4};
        int[] ints1 = Arrays.copyOf(ints, ints.length);
        System.out.println(ints.equals(ints1));
    }
}