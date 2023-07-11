import org.graalvm.compiler.replacements.nodes.ReverseBytesNode;

import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ListProb {

    /**
     * Offer 06. 从尾到头打印链表
     */
    public int[] reversePrint(ListNode head){
        int num = 0;
        ListNode tmp = head;
        while (tmp != null){
            num ++;
            tmp = tmp.next;
        }
        tmp = head;
        int[] res = new int[num];
        for (int i = num; i >0 ; i--) {
           res[i-1] = tmp.val;
            tmp = tmp.next;
        }
        return res;
    }

    /**
     * Offer18删除链表的节点
     */
    public ListNode deleteNode(ListNode head, int val) {
        if (head.val == val)
            return head.next;
        ListNode tmp = head;
        while (tmp.next != null && tmp.next.val != val){
            tmp = tmp.next;
        }
        if (tmp.next != null)
            tmp.next = tmp.next.next;
        return head;
    }

    /**
     * Leecode 83. 删除排序链表中的重复元素
     * 1 -> 2 -> 2 -> 3 ==> 1 -> 2 -> 3
     */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null)
            return head;
        ListNode tmp = head;
        while (tmp != null){
            while (tmp.next != null && tmp.val == tmp.next.val){
                tmp.next = tmp.next.next;
            }
            tmp = tmp.next;
        }
        return head;
    }

    /**
     * Leecode 82. 删除排序链表中的重复元素II（难，ok）
     *
     * 1 -> 2 -> 2 -> 3 ==> 1 -> 3
     *
     * 解：删繁就简，关键就是在开头添加一个无用节点用来删除条件节点。
     */
    public ListNode deleteDuplicates2(ListNode head) {
        ListNode head_new = new ListNode(-1);
        head_new.next = head;
        ListNode top = head_new;
        ListNode tmp;
        int num = -1;
        while (top.next != null){
            num = top.next.val;
            if (top.next.next != null && top.next.val == top.next.next.val){
                tmp = top.next.next;
                while ( tmp != null && tmp.val == num){
                    tmp = tmp.next;
                }
                top.next = tmp;
            }
            else {
                top = top.next;
            }

        }
        return head_new.next;
    }

    /**
     * Offer22 链表中倒数第k个节点
     */
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode tmp = head;
        int num = 0;
        while (tmp != null){
            tmp = tmp.next;
            num ++;
        }
        tmp = head;
        for (int i = 0; i < num - k; i++) {
            tmp = tmp.next;
        }
        return tmp;
    }

    /**
     * Offer24 反转链表
     * 输入: 1->2->3->4->5->NULL
     * 输出: 5->4->3->2->1->NULL
     *
     * 方法一：头插法
     */
    public ListNode reverseList(ListNode head) {
        ListNode head_new = null;
        ListNode tmp1 = head, tmp2;
        while (tmp1 != null){
            tmp2 = tmp1.next;
            tmp1.next = head_new;
            head_new = tmp1;
            tmp1 = tmp2;
        }
        return head_new;
    }

    //反转列表，方法二：递归法（难，ok）
    public ListNode reverseList2(ListNode head) {
        if (head == null){
            return null;
        }
        if (head.next == null)
            return head;
        ListNode node = reverseList2(head.next);
        if (head.next != null)
            head.next.next = head;
        head.next = null;
        return node;
    }

    //反转链表升级版 -> 反转链表の前 N 个节点，（n <= 链表长度），同样可以使用方法：递归法（难，ok）
    ListNode last;
    public ListNode reverseListN(ListNode head, int n) {

        // 错
//        if (head == null)
//            return null;
//        if (head.next == null)
//            return head;
//        if (n == 0){
//            last = head;
//            return null;
//        }

        // 到需要反转的最后一个节点截止。
        if (n == 1){
            last = head.next;
            return head;
        }

        ListNode node = reverseListN(head.next, n - 1);
        head.next.next = head;
        head.next = last;
        return node;
    }

    /**
     *  Leecode92 反转链表 II
     *
     *  反转从位置 left 到位置 right 的链表节点
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == 1){
            return reverseListN(head, right);
        }
        ListNode listNode = reverseBetween(head.next, left - 1, right - 1);
        head.next = listNode;
        return head;
    }

    /**
     * Leecode 25. K 个一组翻转链表
     */
    public ListNode reverseKGroup(ListNode head, int k) {

        ListNode tmp = head;
        int num = k;
        while ( num-- >1 && tmp != null){
            tmp = tmp.next;
        }
        if (tmp != null){
            ListNode next_head = tmp.next;
            ListNode node = reverseListN(head, k);
            head.next = reverseKGroup(next_head, k);
            return node;
        }
        else {
            return  head;
        }
    }

    /**
     * Offer52 两个链表的第一个公共节点 / Leecode 160. 相交链表
     * 指针相遇法，
     * 时间复杂度：O(a + b)
     * 空间复杂夫：O(1)
     * 这里没有考虑到没有交点的情况
     */
    ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA, b = headB;
        while (a != b){
            a = a == null ? headA : a.next;
            b = b == null ? headB : b.next;
        }
        return b;
    }


    /**
     * Leecode 面试题02.05 / Leecode 2. 两数相加
     * 链表求和：个位排在链表首部
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode a = l1, b = l2;
        int c = 0;
        ListNode head = new ListNode();
        ListNode tmp = head;
        while (a != null || b != null){
            if (a !=null){
                c += a.val;
                a = a.next;
            }
            if (b != null){
                c += b.val;
                b = b.next;
            }
            tmp.next = new ListNode(c % 10);
            tmp = tmp.next;
            c = c / 10;
        }
        if (c != 0){
            tmp.next = new ListNode(c);
        }
        return head.next;
    }


    /**
     * 剑指 Offer II 025. 链表中的两数相加 / Leecode 445. 两数相加 II
     * 链表求和：个位排在链表尾部
     * 不要想着用long 来存储链表数字已经超过最大存储范围
     */
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        return null;
    }

    /**
     * Leecode 234
     * 回文链表
     */
    public boolean isPalindrome(ListNode head) {

        return true;
    }


    /**
     * Leecode 61 旋转链表
     *
     * 将链表每个节点向右移动 k 个位置。
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null)
            return head;
        ListNode tmp = head, tail = new ListNode();
        int num = 0;
        while (tmp != null){
            if (tmp.next == null)
                tail = tmp;
            num ++;
            tmp = tmp.next;
        }
        if (num - k == 0)
            return head;
        int node = num - (k % num);
        tmp = head;
        while (node-- > 1){
            tmp = tmp.next;
        }
        tail.next = head;
        head = tmp.next;
        tmp.next = null;
        return head;
    }



    public static class ListNode{
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static void main(String[] args) {
        ListProb listProb = new ListProb();
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2);
        ListNode c = new ListNode(3);
        ListNode d = new ListNode(4);
        ListNode e = new ListNode(5);
        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        listProb.reverseKGroup(a, 2);

    }
}