package algorithm;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringProb {

    /**
     * Leecode 678. 有效的括号字符串（难！）
     */
    public boolean checkValidString(String s) {
        // 括号匹配的问题可以用栈求解。

        return false;
    }

    /**
     * Offer 50. 第一个只出现一次的字符
     */
    public char firstUniqChar(String s) {
        if (s.equals(""))
            return ' ';
        HashSet<Character> set = new HashSet<>();
        HashSet<Character> repeatSet = new HashSet<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (set.contains(chars[i])){
                repeatSet.add(chars[i]);
            }
            set.add(chars[i]);
        }
        for (int i = 0; i < chars.length; i++) {
            if (!repeatSet.contains(chars[i])){
                return chars[i];
            }
        }
        return ' ';
    }


    /**
     * Leecode 128. 最长连续序列
     */
    public int longestConsecutive(int[] nums) {
        // hash 表
        return -1;
    }



    public static void main(String[] args) {
        StringProb sp = new StringProb();

        int[] nums ={1,2};
        sp.longestConsecutive(nums);
    }
}
