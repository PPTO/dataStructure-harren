package algorithm.arrayProb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class UnionProb {
    /**
     * 并查集：并查集是一种典型的数据结构，
     * 用于处理一些不相交集合的合并和查询问题。
     * 比如可以判断一个森林中有几个树，某个节点是否属于某棵树等。
     */

    /**
     * Leecode 547. 省份数量
     */
    public int findCircleNum(int[][] isConnected) {
        // 用一个一维数组存储城市的并查集
        int[] unions = new int[isConnected.length];
        for (int i = 0; i < unions.length; i++) {
            unions[i] = i;
        }
        for (int i = 1; i < isConnected.length; i++) {
            for (int j = 0; j < i; j++) {
                if (isConnected[i][j] == 1){
                    union(unions,i,j);
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < unions.length; i++) {
            if (unions[i] == i)
                ans++;
        }
        return ans;
    }

    /**
     * Leecode 684. 冗余连接
     */
    public int[] findRedundantConnection(int[][] edges) {
        // 构建一维并查集，并判断是否有环出现
        int[] unions = new int[edges.length+1];
        for (int i = 0; i < unions.length; i++) {
            unions[i] = i;
        }
        int[] ans = new int[2];
        for (int i = 0; i < edges.length; i++) {
            int[] tmp = edges[i];
            // 判断是否有环
            int p = root(unions, tmp[0]);
            int q = root(unions, tmp[1]);
            if (p == q){
                ans[0] = tmp[0];
                ans[1] = tmp[1];
            }
            else {
                union(unions, tmp[0], tmp[1]);
            }
        }
        return ans;
    }

    /**
     * Leecode 785. 判断二分图
     * 若无向图 G=(V,E)G=(V,E)G=(V,E) 的顶点集 V 可以分割为两个互不相交的子集，
     * 且图中每条边的两个顶点分别属于不同的子集，则称图 G 为一个二分图。
     */
    public boolean isBipartite(int[][] graph) {
        // 如果是二分图的话，那么图中每个顶点的所有邻接点都应该属于同一集合，且不与顶点处于同一集合。
        int[] unions = new int[graph.length];
        for (int i = 0; i < unions.length; i++) {
            unions[i] = i;
        }
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                int root = root(unions, i);
                int root1 = root(unions, graph[i][j]);
                if (root1 == root)
                    return false;
                union(unions, graph[i][0], graph[i][j]);
            }
        }
        return true;
    }

    /**
     * Leecode 1202. 交换字符串中的元素（难！）
     * 超时：超时原因是没有对并查集进行优化，懒得优化了，就这样吧
     */
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        int[] unions = new int[s.length()];
        for (int i = 0; i < unions.length; i++) {
            unions[i] = i;
        }
        for (List<Integer> pair : pairs) {
            union(unions, pair.get(0), pair.get(1));
        }
        HashMap<Integer, List<Character>> map = new HashMap<>();
        for (int i = 0; i < unions.length; i++) {
            int root = root(unions, unions[i]);
            if (!map.containsKey(root))
                map.put(root, new ArrayList<>());
            List<Character> list = map.get(root);
            list.add(s.charAt(i));
        }
        // 排序
        for (Integer i : map.keySet()) {
            List<Character> list = map.get(i);
            list.sort(((o1, o2) -> o1 - o2));
        }
        char[] ans = new char[s.length()];
        for (int i = 0; i < ans.length; i++) {
            int root = root(unions, i);
            List<Character> list = map.get(root);
            ans[i] = list.remove(0);
        }
        return String.valueOf(ans);
    }

    private int root(int[] unions, int i){
        while (unions[i] != i)
            i = unions[i];
        return i;
    }

    private void union(int[] unions, int i, int j){
        int p = root(unions, i);
        int q = root(unions, j);
        unions[p] = q;
    }

    public static void main(String[] args) {

    }
}
