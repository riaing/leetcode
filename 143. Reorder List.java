Given a singly linked list L: L0→L1→…→Ln-1→Ln,
reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…

You may not modify the values in the list's nodes, only nodes itself may be changed.

Example 1:

Given 1->2->3->4, reorder it to 1->4->2->3.
Example 2:

Given 1->2->3->4->5, reorder it to 1->5->2->4->3.

--------------------------------------------------------------------------------------------------------

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        
     // 1, find the middle point, 根据1-2-3-4的例子，得出如果偶数时选前一个为middle，得从middle后断开list
        ListNode slow = head; 
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
    
        ListNode p2 = slow.next;
        //一定要在这里断掉
        slow.next = null;
        ListNode reveresdP2 = reverse(p2);
        mergeTwoList(head, reveresdP2);
    }
    
    private ListNode reverse(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode pre = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }
    
    private void mergeTwoList(ListNode p1, ListNode p2) {
        if (p1 == null) {
            return; 
        }
        
        if (p2 == null) {
            return;
        }
        ListNode head = new ListNode(0);
        ListNode cur = head;
        while (p1 != null && p2 != null) {
            cur.next = p1;
            cur = cur.next; 
            p1 = p1.next;
            
            cur.next = p2;
            cur = cur.next;
            p2 = p2.next;
        }
        if (p1 != null) {
            cur.next = p1;
        }
        
        else if (p2 != null) {
            cur.next = p2;
        }
        //return head.next;
    }
}
