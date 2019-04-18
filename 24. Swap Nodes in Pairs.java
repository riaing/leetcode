Given a linked list, swap every two adjacent nodes and return its head.

You may not modify the values in the list’s nodes, only nodes itself may be changed.

 

Example:

Given 1->2->3->4, you should return the list as 2->1->4->3.

--------------------------------------------------------------------------------------------
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode swapPairs(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return head;
        }
        
        
       ListNode dummy = new ListNode(0);
        dummy.next = head.next; //头总会是第二个元素
        ListNode pre = dummy;
        
        
        while (head != null && head.next != null) {
            //1, pre指向head的下一个
            pre.next = head.next; 
            
            //2, head.next指回head
            ListNode tmp = head.next.next;
            head.next.next = head;  
            // 3， head指向第三个元素
            head.next = tmp;
            pre = head;
            // 4, 更新head， pre
            head = tmp; 
        }
        return dummy.next;
    }
}
