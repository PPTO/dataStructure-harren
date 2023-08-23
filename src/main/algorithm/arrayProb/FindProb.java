package algorithm.arrayProb;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FindProb {
    /**
     * 二分查找的多种考查形式：
     * 1.查找是否存在目标值（最基础的类型）
     * 2.数组中存在重复值
     *  2.1查找目标值的左侧边界索引
     *  2.2查找目标值的右侧边界索引
     * 3.查找目标值在数组中的个数（2.1+2.2）
     * 4. 最大值最小化
     *  对于最大值最小化、最小值最大化的问题，解题思路都为：先确定二分法的遍历范围，再二分法 + 数组枚举。
     *  这类问题完全可以用一种套路解决。
     *
     *
     * Important!
     * 二分法：
     * 若 while(low < high)，则可以跟 high = mid，在 while 后根据 low 进行 return
     * 若 while(low <= high)，则需要跟 low = mid -1，（或）在 while 里 return。
     * low 必须是 low = mid +1;
     *
     * Important!
     * 当你发现一道题可以用 遍历 来解决时， 可以尝试使用 二分法 来优化时间复杂度
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
        res[0] = searchLeftandRight(nums, target, -1);
        //2. 查找结束位置
        res[1] = searchLeftandRight(nums, target, 1);
        return res;
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

    public int searchLeftandRight(int[] nums, int target, int flag){
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
     * Offer 11. 旋转数组的最小数字 （难！）
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
     * Leecode 875. 爱吃香蕉的珂珂
     */
    public int minEatingSpeed(int[] piles, int h) {
        // 最大化最小值问题
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
     */
    public int shipWithinDays1(int[] weights, int days) {
        // 最大化最小值
        int min = IntStream.of(weights).max().getAsInt();
        int max = IntStream.of(weights).sum();
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

    //错误示范：判断条件复杂，有些时候如果发现判断条件过多，那么就说明应该换一个方案了。
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
     * Leecode. 410 分割数组的最大值（难）
     */
    public int splitArray(int[] nums, int k) {
        // 最大值最小化问题
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
     * Offer 53 - II. 0～n-1中缺失的数字 （难！）
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

}
