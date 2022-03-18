Given a singly linked list L: L0→L1→…→Ln-1→Ln,
reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…

You may not modify the values in the list''s nodes, only nodes itself may be changed.

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
 ------------------------- 2022.3.17 ----------------------------------------------
     /**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public void reorderList(ListNode head) {
        // 1. find middle 
        ListNode mid = middleNode(head);
        ListNode midNext = mid.next;
        mid.next = null;
        // 2. reverse the second half 
        ListNode secondHead = reverse(midNext); 
        // 3. combine into one。方法一：用个sudo，简单明了 
        ListNode firstHead = head;
        ListNode sudo = new ListNode(0);
        ListNode cur = sudo;
        while (secondHead != null) { // secondHead.length = firstHead.length, or = firstHead.length - 1
            cur.next = firstHead;
            firstHead = firstHead.next; 
            cur = cur.next; 
            cur.next = secondHead;
            secondHead = secondHead.next;
            cur = cur.next; 
        }
        if (firstHead != null) { // 说明原linklist是奇数，cur1多一个
            cur.next = firstHead;
        }
        sudo.next = null; // 删掉多于的helper node
     
        // 3. 方法二
        // ListNode firstHead = head;
//      while (firstHead != null && secondHead != null) {
//           ListNode temp = firstHead.next;
//           firstHead.next = secondHead;
//           firstHead = temp;

//           temp = secondHead.next;
//           secondHead.next = firstHead;
//           secondHead = temp;
//         }
    }
    
    private ListNode middleNode(ListNode head) {
        ListNode slow = head; 
        ListNode fast = head.next; // 求第一个时，fast = head.next即可
        while(fast != null && fast.next != null) {
            slow = slow.next; 
            fast = fast.next.next; 
        }
        
        return slow; 
    }
    
    private ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode cur = head; 
        while (cur != null) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur; 
            cur = tmp;
        }
        return pre; 
    }
}
