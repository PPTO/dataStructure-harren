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
     * Leecode 82. 删除排序链表中的重复元素II（难）
     *
     * 1 -> 2 -> 2 -> 3 ==> 1 -> 3
     *
     * 解：删繁就简，关键就是在开头添加一个无用节点用来删除条件节点。
     */
    public ListNode deleteDuplicates2(ListNode head) {
        return null;
    }

    /**
     * Offer22倒数第K个节点
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

    //反转列表，方法二：递归法（难）
    public ListNode reverseList2(ListNode head) {
        return null;
    }

    //反转链表升级版 -> 反转链表の前 N 个节点，（n <= 链表长度），同样可以使用方法：递归法（难）
    public ListNode reverseListN(ListNode head, int n) {
        return null;
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

    }
}
