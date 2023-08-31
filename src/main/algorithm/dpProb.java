package algorithm;

import java.util.stream.IntStream;

public class dpProb {

    /**
     * Offer 10- I. 斐波那契数列
     */
    public int fib(int n) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i < n+1; i++) {
            dp[i] = (dp[i-1] + dp[i-2]) % 1000000007;
        }
        return dp[n] ;
    }

    /**
     * Offer 10- II. 青蛙跳台阶问题
     */
    public int numWays(int n) {
        if (n == 0 || n == 1)
            return 1;
        if (n == 2)
            return 2;
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 2; i < n; i++) {
            dp[i] = (dp[i-1] + dp[i-2]) % 1000000007;
        }
        return dp[n-1];
    }

    /**
     * 0-1 背包问题
     * 问题：一个可装载重量为 W 的背包和 N 个物品，每个物品有重量和价值两个属性。
     *      现在让你用这个背包装物品，最多能装的价值是多少？（注意. 每个物品只能装一个）
     *  求最大值（动态规划）
     *
     * @param w 背包最大重量
     * @param n 物品数量
     * @param wt 物品重量属性
     * @param val 物品价值属性
     * @return 背包能装入的最大值
     */
    public int knapsack(int w, int n, int[] wt, int[] val){
        //1.建立状态数组
        int[][] dp = new int[n + 1][w + 1];
        //2.确定 base case（因为默认数组初值为0，因此可认为自动填完base case）
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= w; j++) {
                //4.状态转移方程
                if (wt[i-1] > j){
                    dp[i][j] = dp[i-1][j];
                }
                else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j - wt[i-1]]+ wt[i-1]);
                }
            }
        }
        return dp[n][w];
    }

    /**
     * Leecode 42. 接雨水
     */
    public int trap(int[] height) {
        if(height.length == 0 || height.length == 1)
            return 0;
        // 1.找到该列左边的最高墙壁
        int[] left = new int[height.length];
        left[0] = 0;
        for (int i = 1; i < height.length; i++) {
            left[i] = Math.max(left[i-1], height[i-1]);
        }
        // 2.找到该列右边的最高墙壁
        int[] right = new int[height.length];
        right[height.length-1] = 0;
        for (int i = height.length-2; i >=0; i--) {
            right[i] = Math.max(right[i+1], height[i+1]);
        }
        //2. 找到每列最多能够存的雨水，并累加
        int num = 0;
        for (int i = 1; i <height.length-1; i++) {
            int tmp = Math.min(left[i], right[i]) - height[i];
            num += tmp >0 ? tmp : 0;
        }
        return num;
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
     * Offer 60. n个骰子的点数（难！）
     */
    public double[] dicesProbability(int n) {
        /**
         * 递归问题都可以用dp解决
         * 例子：3个色子，从min=3到max=6*6*6
         * 3个色子->3 = 2个色子->2 + 2个色子->1
         * 3个色子->4 = 2个色子->3 + 2个色子->2 + 2色子->1
         * 3个色子->5 = 2个色子->4 + 2个色子->3 + .....
         * 3个色子->6 = 2个色子->5 + 2个色子->4 + .....
         */
        int[][] dp = new int[n + 1][n * 6 + 1];
        int total = 0;
        //1. 初始化
        for (int i = 1; i <= n; i++) {
            dp[i][i] = 1;
        }
        for (int i = 1; i <=6; i++) {
            dp[1][i] = 1;
        }

        //2. 选择
        for (int i = 2; i <= n; i++) {
            for (int j = i+1; j <= i*6; j++) {
                for (int k = 1; k <= 6; k++) {
                    if (j - k >0){
                        dp[i][j] += dp[i-1][j-k];
                    }
                }
            }
        }
        double[] res = new double[n * 6 - n + 1];
        int tmp = 0;
        for (int i = 0; i < res.length; i++) {
            res[tmp++] = dp[n][n+i] / Math.pow(6,n);
        }
        return res;
    }

    /**
     * Offer 14- I. 剪绳子
     */
    public int cuttingRope(int n) {
        int[][] dp = new int[n + 1][n + 1];
        // 1. 初始化
        for (int i = 1; i <= n; i++) {
            dp[i][1] = i;
        }
        for (int i = 1; i <= n; i++) {
            dp[i][i] = 1;
        }
        // 2. 选择
        for (int i = 3; i <= n; i++) {
            for (int j = 2; j < i ; j++) {
                int max = Integer.MIN_VALUE;
                for (int k = 0; k < i-1; k++) {
                    int tmp = dp[k][j-1] * (i-k);
                    max = max >= tmp ? max : tmp;
                }
                dp[i][j] = max;
            }
        }
        int max = Integer.MIN_VALUE;
        for (int i = 2; i <= n; i++) {
            max = max >= dp[n][i] ? max : dp[n][i];
        }
        return max;
    }

    /**
     * 剪绳子 - 数学规律
     * 把绳子尽可能切为多个长度为 3 的片段
     * 若最后一段绳子长度为 2；则保留
     * 若最后一段绳子长度为 1；则应把一份 3+1 替换为 2+2
     */
    public int cuttingRope2(int n) {
        if (n == 2)
            return 1;
        if (n == 3)
            return 2;
        int i = n /3;
        int j = n %3;
        if (j == 2){
            return (int) Math.pow(3, i) * 2;
        }
        if (j == 1){
            return (int) Math.pow(3, i-1) * 2 * 2;
        }
        return (int) Math.pow(3, i);
    }

    /**
     * Offer 14- II. 剪绳子 II
     * 类似于 Offer 10
     */
    public int cuttingRope1(int n) {
        // 数学规律 + 循环求余
        if (n<=3)
            return n-1;
        long res = 1;
        // 从第一段线段开始验算，3的ret次方是否越界。
        // 注意是验算lineNums-1次。（否则当 j ==1 时 ，无法回溯到 2 * 2）
        for (int j = 1; j < n/3; j++) {
            res = res * 3 % 1000000007;
        }
        int j = n %3;
        if (j == 2){
            return (int) (res *3 *2 % 1000000007);
        }
        if (j == 1){
            return (int) (res *2 *2 % 1000000007);
        }
        return (int) (res *3 % 1000000007);
    }

    /**
     * Leecode 322. 零钱兑换（难！初始值的设置）
     */
    public int coinChange(int[] coins, int amount) {
        int[][] dp = new int[coins.length + 1][amount + 1];
        //1. 初始化：第一列, 第一行都为 0
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = amount+1;
        }
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 0;
        }
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                int coin = coins[i-1];
                if (coin > j){
                    dp[i][j] = dp[i-1][j];
                }
                if (coin <= j){
                    dp[i][j] = Math.min(dp[i-1][j] , 1 + dp[i][j-coin]);
                }
            }
        }
        return dp[dp.length-1][dp[0].length-1] == amount+1
                ? -1
                : dp[dp.length-1][dp[0].length-1];
    }

    /**
     * 0-1 背包问题就是 背包里的物品只有一个
     * 完全背包问题就是 背包里的物品数量无限
     * 区别就在于：
     *  dp[i][j] = dp[i-1][j] + dp[i][j-coin];  完全背包
     *  dp[i][j] = dp[i-1][j] + dp[i-1][j-coin]; 0-1背包
     */

    /**
     * Leecode 518 零钱兑换 II（完全背包问题）
     */
    public int change(int amount, int[] coins) {
        int[][] dp = new int[coins.length + 1][amount + 1];
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                int coin = coins[i - 1];
                if (coin > j){
                    dp[i][j] = dp[i-1][j];
                }
                else {
                    dp[i][j] = dp[i-1][j] + dp[i][j-coin];
                }
            }
        }
        return dp[dp.length-1][dp[0].length-1];
    }

    /**
     * Leecode 416. 分割等和子集（难！）
     */
    public boolean canPartition(int[] nums) {
        /**
         * 本题是一个 NP 问题，因此不能使用双指针（贪心）解决该问题。
         * 应聚焦于如何将该问题转换成背包问题
         */
        int sum = IntStream.of(nums).sum();
        if (sum % 2 != 0 || nums.length == 1)
            return false;
        int half = sum / 2;
        boolean[][] dp = new boolean[nums.length + 1][half + 1];
        //1. 初始化
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] =true;
        }
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                int num = nums[i-1];
                if (num > j)
                    dp[i][j] = dp[i-1][j];
                else {
                    dp[i][j] = dp[i-1][j] || dp[i-1][j-num];
                }
            }
        }
        return dp[dp.length-1][dp[0].length-1];
    }

    /**
     * Offer 42. 连续子数组的最大和 / Leecode 53. 最大子数组和 / 最大子序和/
     */
    public int maxSubArray(int[] nums) {
        // 这道题还不能用滑动窗口算法，因为数组中的数字可以是负数。
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i-1] + nums[i], nums[i]);
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            max = max >= dp[i] ? max : dp[i];
        }
        return max;
    }

    /**
     * Leecode 300. 最长递增子序列
     */
    public int lengthOfLIS(int[] nums) {
        // dp[i] 表示以 nums[i] 这个数结尾的最长递增子序列的长度。
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int res = 1;
        for (int i = 1; i < dp.length; i++) {
            int max = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]){
                    max = Math.max(max, dp[j] + 1);
                }
            }
            dp[i] = max;
            res = res > max ? res : max;
        }
        return res;
    }

    /**
     * Leecode 1143. 最长公共子序列
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (text1.charAt(i-1) == text2.charAt(j-1))
                    dp[i][j] = dp[i-1][j-1] + 1;
                else
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
            }
        }
        return dp[dp.length-1][dp[0].length-1];
    }

    /**
     * Leecode 516. 最长回文子序列（难！）
     */
    public int longestPalindromeSubseq(String s) {
        //  dp 数组的含义：在子串s[i..j]中，最长回文子序列的长度为dp[i][j]
        int[][] dp = new int[s.length()][s.length()];
        for (int i = 0; i < dp.length; i++) {
            dp[i][i] = 1;
        }
        for (int i = dp.length-1; i >= 0 ; i--) {
            for (int j = i+1; j < dp.length; j++) {
                if (s.charAt(i) == s.charAt(j))
                    dp[i][j] = dp[i+1][j-1] + 2;
                else {
                    dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
                }
            }
        }
        return dp[0][dp.length-1];
    }











    public static void main(String[] args) {
        dpProb dpProb = new dpProb();
        int[] ints = {1, 2, 5};
        boolean b = dpProb.canPartition(ints);
        dpProb.longestPalindromeSubseq("bbbab");

    }
}