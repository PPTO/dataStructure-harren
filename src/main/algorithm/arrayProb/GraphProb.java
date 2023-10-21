package algorithm.arrayProb;

public class GraphProb {

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
    /**
     * 使用递归记得用 返回值 记录结果
     */
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

    /**
     * Leecode 48. 旋转图像
     */
    public void rotate(int[][] matrix) {
        // matrix[i][j] → matrix[j][n−1−i]
        int n = matrix.length;
        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n-i-1; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[n-1-j][i];
                matrix[n-1-j][i] = matrix[n-1-i][n-1-j];
                matrix[n-1-i][n-1-j] = matrix[j][n-1-i];
                matrix[j][n-1-i] = tmp;
            }
        }
    }

    /**
     * Leecode 240. 搜索二维矩阵 II
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        // 贪心算法，将矩阵逆时针旋转 45° ，并将其转化为图形式，发现其类似于 二叉搜索树
        int left = 0, right = matrix[0].length-1;
        while (left < matrix.length && left >= 0 && right >= 0 && right < matrix[0].length){
            if (target == matrix[left][right])
                return true;
            else if (target < matrix[left][right])
                right--;
            else if (target > matrix[left][right])
                left++;
        }
        return false;
    }





    public static void main(String[] args) {
        GraphProb gp = new GraphProb();
    }
}
