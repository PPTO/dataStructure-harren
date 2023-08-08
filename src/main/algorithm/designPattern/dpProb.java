package algorithm.designPattern;

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
     * 0-1背包问题
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
     * Offer 42. 连续子数组的最大和
     */
    public int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i-1] + nums[i], nums[i]);
            max = max >= dp[i] ? max : dp[i];
        }
        return max;
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
     * Leecode 322. 零钱兑换
     */
    public int coinChange(int[] coins, int amount) {

        return -1;
    }







    public static void main(String[] args) {
        dpProb dpProb = new dpProb();
        dpProb.dicesProbability(2);
        dpProb.cuttingRope(4);

    }
}
