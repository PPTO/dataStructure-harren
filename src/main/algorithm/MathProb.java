package algorithm;

import java.util.*;

public class MathProb {
    Scanner in = new Scanner(System.in);
    /**
     * HJ7 取近似值：
     * 写出一个程序，接受一个正浮点数值，输出该数值的近似整数值。
     * 如果小数点后数值大于等于 0.5 ,向上取整；小于 0.5 ，则向下取整。
     * 数据范围：保证输入的数字在 32 位浮点数范围内
     */
    public void hj7(){
        double ans = in.nextDouble();
        System.out.print((int)(ans + 0.5));
    }

    /**
     * Offer 62. 圆圈中最后剩下的数字 / 约瑟夫环
     */
    public int lastRemaining(int n, int m) {
        // 用 linkedlist 超时
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++)
            list.add(i);
        int b = 0;
        while (list.size() > 1){
            b = (b + m - 1) % list.size();
            list.remove(b);
        }
        return list.get(0);
    }

    /**
     * Offer 65. 不用加减乘除做加法（难）
     */
    public int add(int a, int b) {
        /**
         * 在计算机系统中，数值一律用 补码 来表示和存储。
         * 补码的优势： 加法、减法可以统一处理（CPU只有加法器）。
         * 因此，本方法同时适用于正数和负数的加法 。
         */
        // debug
        int cur = a ^ b;
        int up =(a & b)<< 1;
        while (up != 0){
            int tmp = cur; //important
            cur ^= up;
            up = (tmp & up) << 1;
        }
        return cur;
    }

    /**
     * Offer 16. 数值的整数次方（难）
     *
     */
    public double myPow(double x, int n) {
        // 快速幂 + 递归
        return n >= 0 ? quickMul(x, n) : 1.0 / quickMul(x, -n);
    }
    // 原因是需要用 long 存 n，以满足 n = -2^31 的特殊情况
    public double quickMul(double x, long N) {
        if (N == 0) {
            return 1.0;
        }
        double y = quickMul(x, N / 2);
        return y * y  * (N % 2 == 0 ? 1 : x);
    }

    /**
     * Offer 17. 打印从1到最大的n位数
     */
    public int[] printNumbers(int n) {
        double num = Math.pow(10, n);
        int[] ans = new int[(int) num - 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = i+1;
        }
        return ans;
    }

    /**
     * Offer 41 找中位数（难！）
     */
    private class MedianFinder {
        // 大顶堆（存小数） + 小顶堆（存大数）
        PriorityQueue<Integer> big;
        PriorityQueue<Integer> small;
        public MedianFinder() {
            big = new PriorityQueue<>((o1, o2) -> o2 - o1);
            small = new PriorityQueue<>(((o1, o2) -> o1 - o2));
        }
        public void addNum(int num) {
            if ((big.size() + small.size()) %2  == 0){
                /**
                 * 由于设计奇数个时从小顶堆取值，
                 * 因此小顶堆一定要有值，要先放入大顶堆中，再把大顶堆中最大的数放入小顶堆中
                 */
                big.add(num);
                small.add(big.poll());
            }
            else if ((big.size() + small.size()) %2  == 1){
                small.add(num);
                big.add(small.poll());
            }
        }
        public double findMedian() {
            return (big.size() + small.size()) %2 == 1
                    ? small.peek()
                    // 因为是double型，所以要用2.0不能用2
                    : (small.peek() + big.peek()) / 2.0;
        }
    }

    /**
     * Offer 43. 1～n 整数中 1 出现的次数（难）
     */
    public int countDigitOne(int n) {
        int high = n / 10, cur = n % 10, low = 0, digit = 1;
        int ans = 0;
        // 找 n 的位数
        int length = String.valueOf(n).length();
        for (int i = 0; i < length; i++) {
            if (cur == 0){
                ans += high * digit;
            }
            else if (cur == 1){
                ans += high * digit + low + 1;
            }
            else {
                ans += (high + 1) * digit;
            }
            // update
            low += cur * digit;
            cur = high %10;
            high /= 10;
            digit *= 10;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(-3 % 2);
    }

}
