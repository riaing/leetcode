
-------------------------------数学归纳法--------------------------------------------------------------
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode cur = head.next;
        ListNode prev = head;
        while(cur != null) {
            prev.next = cur.next;
            cur.next = head;
            head = cur;
            cur = prev.next;
        }
        return head;
    }
}

------------------常规解法-----------------------------------------------------------------------------------
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
       ListNode pre = null;
       ListNode cur = head; 
       
        while(cur != null) {
           ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur; 
            cur = tmp; 
        }
        return pre;
    }
}
--------- 模板： reverse in a certain range ----------------

    这是 reverse in certain range 的写法。重点是想清楚要 reverse 多少次
    private ListNode reverseForCertainRange(ListNode head, int left, int right) {
        ListNode pre = null;
       ListNode cur = head; 
       for (int i = 1; i <= right - left + 1 && cur != null; i++) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur; 
            cur = tmp;
       }
        return pre;
    }  

