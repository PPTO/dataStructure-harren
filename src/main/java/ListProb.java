import java.time.temporal.Temporal;
import java.util.ArrayList;
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
     * Leecode 25. K 个一组翻转链表（难！）
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
