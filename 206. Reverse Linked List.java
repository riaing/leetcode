
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
