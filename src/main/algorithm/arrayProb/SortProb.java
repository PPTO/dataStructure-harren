package algorithm.arrayProb;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SortProb {

    /**
     *  注.
     *  对于 “找前k个数” 这种不需要全排序的问题，统一可以用堆排序解决，其中JDK的堆排序实现为 priorityQueue
     */

    /**
     * Offer 21. 调整数组顺序使奇数位于偶数前面
     */
    public int[] exchange(int[] nums) {
        // 单次快排
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
        // 排序题
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
     * Leecode 179. 最大数
     */
    public String largestNumber(int[] nums) {
        // 解法同上
        Integer[] array = IntStream.of(nums).boxed().toArray(Integer[]::new);
        Arrays.sort(array, (o1, o2)->{
            String s1 = o1 + "" + o2;
            String s2 = o2 + "" + o1;
            return s1.compareTo(s2) >=0 ? -1 : 1;
        });
        String s = "";
        for (int i = 0; i < array.length; i++) {
            s += array[i];
        }
        // 删掉前导0
        int i = 0;
        while (s.length() > 1 && s.charAt(i) == '0' && s.charAt(i + 1)== '0'){
            s = s.substring(1, s.length());
        }
        return s;
    }



    /**
     * Leecode 215. 数组中的第K个最大元素
     * 要求：时间复杂度为 O(n)
     */
    public int findKthLargest(int[] nums, int k) {
        //解决：快速排序
        return IntStream.of(nums).boxed()
                .sorted((t1, t2) -> t2 - t1)
                .collect(Collectors.toList()).get(k-1);
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
        SortProb sp = new SortProb();
        int[] ints = {10,2};
        String s = sp.largestNumber(ints);
        System.out.println(s);
    }




}
