package algorithm;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StringProb {

    /**
     * Leecode 678. 有效的括号字符串（难！）
     * '*' 可以被视为单个右括号 ')' ，或单个左括号 '(' ，或一个空字符串。
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
     * Leecode 20. 有效的括号
     */
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{')
                stack.add(c);
            else if (!stack.isEmpty() && c == ')' && stack.peek() == '(' )
                stack.pop();
            else if (!stack.isEmpty() && c == ']' && stack.peek() == '[' )
                stack.pop();
            else if (!stack.isEmpty() && c == '}' && stack.peek() == '{' )
                stack.pop();
            else
                return false;
        }
        return stack.isEmpty() ? true : false;
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
     * Offer 48. 最长不含重复字符的子字符串 / Leecode 3. 无重复字符的最长子串
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
     * Leecode 344. 反转字符串
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
     * Leecode 5 最长回文子串
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
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> cur = new ArrayList<>();
        ria(s, 0, list, cur);
        return list;
    }

    private void ria(String s, int start, List<String> list, List<String> cur){
        if (start == s.length() && cur.size() == 4){
            String tmp = "";
            for (int i = 0; i < 4; i++) {
                tmp += cur.get(i) + (i==3 ? "" : ".");
            }
            list.add(tmp);
        }

        if (start < s.length() && cur.size() < 4){
            String tmp = "";
            for (int i = 0; i < 3; i++) {
                if (start + i == s.length())
                    return;
                char c = s.charAt(start + i);
                tmp += c;
                if (Integer.parseInt(tmp) > 255){
                    return;
                }
                cur.add(tmp);
                ria(s, start+i+1, list, cur);
                // 回溯
                cur.remove(cur.size()-1);
                if (tmp.equals("0")){
                    return;
                }
            }
        }
    }

    /**
     * Leecode 227. 基本计算器 II（难！）
     */
    public int calculate(String s) {
        // 模拟2个寄存器 + 1个栈
        String multi = "+";
        String digit = "";
        s = s+ "+0";
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)){
                digit += c;
            }
            else if (c != ' '){
                switch (multi){
                    case "+":
                        stack.add(Integer.parseInt(digit));
                        break;
                    case "-":
                        stack.add(-Integer.parseInt(digit));
                        break;
                    case "*":
                        Integer pop = stack.pop();
                        stack.add(pop * Integer.parseInt(digit));
                        break;
                    case "/":
                        Integer pop1 = stack.pop();
                        stack.add(pop1 / Integer.parseInt(digit));
                        break;
                }
                digit = "";
                multi = c + "";
            }
        }
        int ans = 0;
        for (Integer integer : stack) {
            ans += integer;
        }
        return ans;
    }

    /**
     * Leecode 394. 字符串解码 （难！）
     */
    public String decodeString(String s) {
        // 难点：模拟2个寄存器 + 2个栈空间
        String res = "";
        String multi = "";
        Stack<String> stack_res = new Stack<>();
        Stack<String> stack_multi = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)){
                multi += c;
            }
            else if (Character.isAlphabetic(c)){
                res += c;
            }
            else if (c == '['){
                stack_res.add(res);
                res = "";
                stack_multi.add(multi);
                multi = "";
            }
            else {
                // c == ']'
                int pop_multi = Integer.parseInt(stack_multi.pop());
                String tmp = res;
                res = stack_res.pop();
                for (int j = 0; j < pop_multi; j++) {
                   res += tmp;
                }
                multi = "";
            }
        }
        return res;
    }

    /**
     * Leecode 165. 比较版本号
     */
    public int compareVersion(String version1, String version2) {
        String[] split1 = version1.split("\\.");
        String[] split2 = version2.split("\\.");
        int j = split1.length-1;
        for (; j >=0; j--) {
            if (Integer.parseInt(split1[j]) != 0) {
                break;
            }
        }
        int k =split2.length-1;
        for(; k >=0; k--){
            if (Integer.parseInt(split2[k])!= 0){
                break;
            }
        }
        int m = 0, n = 0;
        while (m <= j && n <= k){
            int i1 = Integer.parseInt(split1[m]);
            int i2 = Integer.parseInt(split2[n]);
            if (i1 > i2){
                return 1;
            }
            else if (i1 < i2){
                return -1;
            }
            else {
                m++;
                n++;
            }
        }
        return j > k ? 1 : j < k ? -1 : 0;
    }

    /**
     * Leecode 43. 字符串相乘（难!）
     */
    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0"))
            return "0";
        // 模拟乘法
        String tmp = "";
        ArrayList<String> list = new ArrayList<>();
        for (int i = num2.length()-1; i >=0; i--) {
            char c = num2.charAt(i);
            String s = multi(num1, c);
            s += tmp;
            tmp += "0";
            list.add(s);
        }
        return multi_add(list);
    }
    private String multi(String num, char c){
        int tmp = 0;
        String s = "";
        for (int i = num.length()-1; i >=0; i--) {
            char c1 = num.charAt(i);
            int j = (c1 - '0') * (c - '0') + tmp;
            tmp = j /10;
            s = j % 10 + s;
        }
        s = (tmp == 0 ? "" : tmp) + s;
        return s;
    }
    private String multi_add(List<String> list){
        String s1 = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            String s2 = list.get(i);
            String ans = "";
            int tmp = 0;
            int j = s1.length()-1, k = s2.length()-1;
            while (j >=0 && k >=0){
                int m = s1.charAt(j) -'0' + s2.charAt(k) - '0' + tmp;
                ans = m % 10 + ans;
                tmp = m / 10;
                j--;
                k--;
            }
            if (k >=0){
                String substring = s2.substring(0, k + 1);
                String s3 = "";
                for (int l = substring.length()-1; l >=0; l--) {
                    int n = substring.charAt(l) - '0' + tmp;
                    ans = n %10 + ans;
                    tmp = n / 10;
                }
            }
            if (tmp != 0){
                ans = tmp + ans;
            }
            s1 = ans;
        }
        return s1;
    }


    public static void main(String[] args) {
        StringProb sp = new StringProb();


        sp.calculate("3*2+3");

    }
}














