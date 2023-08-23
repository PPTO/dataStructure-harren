package algorithm.arrayProb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntervalProb {

    /**
     * Leecode 1288. 删除被覆盖区间
     */
    public int removeCoveredIntervals(int[][] intervals) {
        // 排序题
        Arrays.sort(intervals, (o1, o2) -> o1[0] == o2[0] ? o2[1] - o1[1] : o1[0] - o2[0]);
        int r = intervals[0][1], l = intervals[0][0];
        int size = intervals.length;
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][1] <= r)
                size--;
            else {
                r = intervals[i][1];
            }
        }
        return size;
    }

    /**
     * Leecode 56. 合并区间
     */
    public int[][] merge(int[][] intervals) {
        // 同上，排序题
        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);
        List<int[]> list = new ArrayList<>();
        int l = intervals[0][0], r = intervals[0][1];
        for (int i = 1; i <intervals.length; i++) {
            if (intervals[i][0] <= r){
                r = Math.max(r, intervals[i][1]);
            }
            else {
                // 更新
                list.add(new int[]{l, r});
                l = intervals[i][0];
                r = intervals[i][1];
            }
        }
        list.add(new int[]{l, r});
        return list.toArray(new int[list.size()][]);
    }

    /**
     * Leecode 986. 区间列表的交集
     */
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {

        return null;
    }

    /**
     * Leecode 452. 用最少数量的箭引爆气球
     */
    public int findMinArrowShots(int[][] points) {
        // 排序

        return -1;
    }
}
