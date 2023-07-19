import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 对于 “找前k个数” 这种不需要全排序的问题，统一可以用堆排序解决，其中JDK的堆排序实现为 priorityQueue
 */
public class SortProb {

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


//    public String minNumber(int[] nums) {
//        List<Integer> list = IntStream.of(nums).boxed()
//                .sorted((t1, t2) ->{
//                    String s1 = String.valueOf(t1);
//                    String s2 = String.valueOf(t2);
//                    int i = 0;
//                    if (s1.equals(s2)){
//                        return -1;
//                    }
//                    while (true){
//                        int s11 = s1.charAt(i % s1.length()) - 48;
//                        int s22 = s2.charAt(i % s2.length()) - 48;
//                        if (s11 > s22)
//                            return 1;
//                        else if (s11 < s22)
//                            return -1;
//                        else {
//                            i++;
//                        }
//                    }
//                })
//                .collect(Collectors.toList());
//        String s = "";
//        for (Integer integer : list) {
//            s += integer;
//        }
//        return s;
//    }

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


    public static void main(String[] args) {
        char a = '0';
        int i = a - 48;
        System.out.println(i);
        Integer i1 = 10;
        System.out.println(i1.toString());

        String s1 = "asd";
        String s2 = "asd";
        System.out.println(s1 == s2);

        SortProb sortProb = new SortProb();
        int[] ints = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        sortProb.findKthLargest(ints, 5);

        int[] ints1 = {3, 30, 34, 5, 9};
        sortProb.minNumber(ints1);
    }
}