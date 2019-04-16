Given a linked list, remove the n-th node from the end of list and return its head.

Example:

Given linked list: 1->2->3->4->5, and n = 2.

After removing the second node from the end, the linked list becomes 1->2->3->5.
Note:

Given n will always be valid.

Follow up:

Could you do this in one pass?


---------------数学思想，fast指针先走n步----------------------------------------------------------------------------
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode slow = head; 
        ListNode fast = head;
        while (n > 0) {
            fast = fast.next;
            n--;
        }
        
        //注意corner case: if n == list.length, means remove first node 
        if (fast == null) {
            return head.next;
        }
        
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
       
        slow.next = slow.next.next;
        
        return head;
        
    }
}

---------------同样的思想，但因为头不确定. so using dummy node -----------------------------------------------------
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        //因为头不确定，所以用dummy
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode slow = dummy; 
        ListNode fast = dummy;
        while (n > 0) {
            fast = fast.next;
            n--;
        }
        
        //用了dummynode，不用再考虑这个corner case。
        //corner case: if n == list.length, means remove first node 
        // if (fast == null) {
        //     return head.next;
        // }
        
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
       
        slow.next = slow.next.next;
        
        return dummy.next;
        
    }
}
