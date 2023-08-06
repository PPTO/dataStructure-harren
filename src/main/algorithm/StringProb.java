package algorithm;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StringProb {

    /**
     * Leecode 678. 有效的括号字符串（难！）
     */
    public boolean checkValidString(String s) {
        // 括号匹配的问题可以用栈求解。
        //存储左括号的下标
        Stack<Integer> left = new Stack<>();
        // 存储星号的下标
        Stack<Integer> star = new Stack<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(')
                left.add(i);
            if (chars[i] == '*')
                star.add(i);
            if (chars[i] == ')'){
                if (!left.isEmpty()){
                    left.pop();
                }
                else if (!star.isEmpty()){
                    star.pop();
                }
                else
                    return false;
            }
        }
        while (!left.isEmpty()){
            Integer l = left.pop();
            if (!star.isEmpty()){
                Integer st = star.pop();
                if (st < l)
                    return false;
            }
            else {
                return false;
            }
        }
        return true;
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
        Set<Integer> set = IntStream.of(nums).boxed().collect(Collectors.toSet());
        int res = 0;
        for (Integer i : set) {
            if (!set.contains(i - 1)){
                int num = 0;
                int tmp = i;
                while (set.contains(tmp++)){
                    num++;
                }
                res = res >= num ? res : num;
            }
        }
        return res;
    }

    /**
     * Offer 05. 替换空格
     */
    public String replaceSpace(String s) {
        char[] charArray = s.toCharArray();
        String string = "";
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == ' '){
                string += "%20";
            }
            else {
                string += charArray[i];
            }
        }
        return string;
    }

    /**
     * Offer 58 - I. 翻转单词顺序 / Leecode 151. 反转字符串中的单词
     */
    public String reverseWords(String s) {
        String[] split = s.trim().split("\\s+");
        String string = "";
        for (int i = split.length-1; i >0; i--) {
            string += split[i] + " ";
        }
        string += split[0];
        return string;
    }

    /**
     * Offer 58 - II. 左旋转字符串
     */
    public String reverseLeftWords(String s, int n) {
        String s1 = s.substring(0, n);
        String s2 = s.substring(n, s.length());
        return s2 + s1;
    }

    /**
     * Offer 48. 最长不含重复字符的子字符串
     */
    public int lengthOfLongestSubstring(String s) {
        // 滑动窗口 + set
        int left = 0, right = 0, max = 0;
        HashSet<Character> set = new HashSet<>();

        while (right < s.length()){
            char c = s.charAt(right);
            if (!set.contains(c)){
                set.add(c);
                right++;
                max = max >= right - left ? max : right - left;
            }
            else {
                while (left < right){
                    char c1 = s.charAt(left);
                    set.remove(c1);
                    left++;
                    if (c1 == c){
                        break;
                    }
                }
            }
        }
        return max;
    }

    /**
     * Leecode 415. 字符串相加
     */
    public String addStrings(String num1, String num2) {
        int i = num1.length()-1, j = num2.length()-1;
        String s = "";
        int up = 0;
        while (i >=0 || j >= 0){
            if (i >= 0){
                int i1 = Integer.parseInt(String.valueOf(num1.charAt(i)));
                up += i1;
                i--;
            }
            if (j >= 0){
                int i1 = Integer.parseInt(String.valueOf(num2.charAt(j)));
                up += i1;
                j--;
            }
            s = up % 10 + s;
            up /= 10;
        }
        if (up != 0)
            s = up + s;
        return s;
    }

    /**
     * 344. 反转字符串
     */
    public void reverseString(char[] s) {
        // 左右指针
        int i = 0, j = s.length-1;
        while ( i < j){
            char tmp = s[i];
            s[i] = s[j];
            s[j] = tmp;
            i++;
            j--;
        }
    }

    /**
     * Leecode 5 最长回文子串（难！）
     * 区别于最长回文子序列
     */
    public String longestPalindrome(String s) {
        /**
         * 使用双指针解决，
         * 核心思想：从中间开始向两边扩散来判断回文串
         * 注意要同时考虑回文串长度是奇数和偶数两种情况
         */
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            //奇数情况
            String s1 = palindrome(s, i, i);
            //偶数情况
            String s2 = palindrome(s, i, i + 1);
            res = res.length() >s1.length() ? res : s1;
            res = res.length() >s2.length() ? res : s2;
        }
        return res;
    }
    public String palindrome (String s, int l, int r){
        while (l>=0 && r<s.length() && s.charAt(l) == s.charAt(r)){
            l--;
            r++;
        }

        return s.substring(l+1, r);
    }

    /**
     * Leecode 93. 复原 IP 地址（难！）
     */
    public List<String> restoreIpAddresses(String s) {
        // dfs

        return null;
    }

    public static void main(String[] args) {
        StringProb sp = new StringProb();
        sp.addStrings("11", "123");
        String string = "123.12.33.";
        sp.restoreIpAddresses("25525511135");
    }
}
