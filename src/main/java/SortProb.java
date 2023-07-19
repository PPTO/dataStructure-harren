import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * (?)对于不需要全排序的问题，统一可以用堆排序解决，其中JDK的堆排序实现为 priorityQueue
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
     * Offer 45. 把数组排成最小的数（难！）
     */
    public String minNumber(int[] nums) {
        List<Integer> list = IntStream.of(nums).boxed()
                .sorted((t1, t2) ->{
                    String s1 = String.valueOf(t1);
                    String s2 = String.valueOf(t2);
                    int i = 0;
                    if (s1.equals(s2)){
                        return -1;
                    }
                    while (true){
                        int s11 = s1.charAt(i % s1.length()) - 48;
                        int s22 = s2.charAt(i % s2.length()) - 48;
                        if (s11 > s22)
                            return 1;
                        else if (s11 < s22)
                            return -1;
                        else {
                            i++;
                        }
                    }
                })
                .collect(Collectors.toList());
        String s = "";
        for (Integer integer : list) {
            s += integer;
        }
        return s;
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
    }
}