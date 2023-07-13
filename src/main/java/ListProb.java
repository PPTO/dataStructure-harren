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
        if (head == null || head.next == null)
            return head;
        ListNode node = reverseList3(head.next);
        head.next.next = head;
        head.next = null;
        return  node;
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
     * 空间复杂度：O(1), 时间复杂度：O(n)
     */
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode head1 = reverseList3(l1);
        ListNode head2 = reverseList3(l2);
        ListNode res = addTwoNumbers(head1, head2);
        ListNode listNode = reverseList3(res);
        return listNode;
    }

    /**
     * Leecode 234
     * 回文链表
     * 时间复杂度：o(n)，空间复杂度：o(1)
     * 技巧：通过快慢指针找链表中点
     */
    public boolean isPalindrome(ListNode head) {
        ListNode quick = head, slow = head, mid, tmp;
        while (quick != null && quick.next != null){
            quick = quick.next.next;
            slow = slow.next;
        }
        mid = quick == null ? slow : slow.next;
        ListNode node = reverseList3(mid);
        while (node != null){
            if (node.val == head.val){
                node = node.next;
                head = head.next;
            }
            else return false;
        }
        return true;
    }
    private ListNode reverseList3(ListNode head){
        if (head == null || head.next == null)
            return head;
        ListNode node = reverseList3(head.next);
        head.next.next = head;
        head.next = null;
        return  node;
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

    /**
     * Leecode 141
     * 环形链表
     * plan: 快慢指针
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null)
            return false;
        ListNode fast = head.next.next, slow = head.next;
        while (fast != slow){
            if (fast == null || fast.next == null)
                return false;
            fast = fast.next.next;
            slow = slow.next;
        }
        return true;
    }


    /**
     * Leecode 142
     * 环形链表 II ： 找到环形链表的第一个节点
     * 2(a + b) = a + 2b + c
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null)
            return null;
        ListNode fast = head.next.next, slow = head.next;
        while (fast != slow){
            if (fast == null || fast.next == null)
                return null;
            fast = fast.next.next;
            slow = slow.next;
        }
        slow = head;
        while (slow != fast){
            slow = slow.next;
            fast = fast.next;
        }
        return fast;
    }

    /**
     * Offer25 / Leecode 21 合并两个有序链表
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode();
        ListNode tmp = head;
        while ( l1 != null && l2 != null){
            if (l1.val <= l2.val){
                tmp.next = l1;
                l1 = l1.next;
            }
            else {
                tmp.next = l2;
                l2 = l2.next;
            }
            tmp = tmp.next;
        }
        tmp.next = l1 != null ? l1 : l2;
        return head.next;
    }

    /**
     * Offer25 / Leecode 21 合并两个有序链表（难！）
     * plan: 递归
     */
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null)
            return l1 == null ? l2 : l1;
        ListNode tmp;
        if (l1.val <= l2.val){
            tmp = l1;
            l1.next = mergeTwoLists2(l1.next, l2);
        }
        else {
            tmp = l2;
            l2.next = mergeTwoLists2(l1, l2.next);
        }
        return tmp;
    }

    /**
     * Leecode 23. 合并K个升序链表
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists ==null || lists.length == 0)
            return null;
        int length = lists.length;
        ListNode head = new ListNode();
        ListNode node = head;
        while (length != 0){
            ListNode tmp = new ListNode(Integer.MAX_VALUE);
            int num = -1;
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] != null && lists[i].val <tmp.val){
                    tmp = lists[i];
                    num = i;
                }
            }
            if (tmp.val == Integer.MAX_VALUE)
                break;
            node.next = tmp;
            node = node.next;
            if (tmp.next == null)
                length--;
            lists[num] = tmp.next;
        }
        return head.next;
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
        ListNode b = new ListNode(4);
        ListNode c = new ListNode(5);
        a.next = b;
        b.next = c;
        ListNode e = new ListNode(1);
        ListNode f = new ListNode(3);
        ListNode g = new ListNode(4);
        e.next = f;
        f.next = g;
        ListNode h = new ListNode(2);
        ListNode i = new ListNode(6);
        h.next = i;
        ListNode[] res=new ListNode[]{a,e,h};
        listProb.mergeKLists(res);

    }
}