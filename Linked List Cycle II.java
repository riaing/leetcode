Given a linked list, return the node where the cycle begins. If there is no cycle, return null.

Note: Do not modify the linked list.

Follow up:
Can you solve it without using extra space?

/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode detectCycle(ListNode head) {

    //cosider coner case [1,2](two nodes), fast == slow is at 2, when enter 2nd while, since
    //fast, slow always differ 1, will have endless loop. 
    //here put slow and fast both at head, just one more iteration, but could put fast == slow at 1. 
    
    ListNode slow = head;
    ListNode fast = head;

    do { 
        if (fast == null || fast.next == null) {
            return null;    //遇到null了，说明不存在环
        }
        slow = slow.next;
        fast = fast.next.next;
    }while(fast != slow);
          //第一次相遇在Z点

    slow = head;    //slow从头开始走，
    while (slow != fast) {    //二者相遇在Y点，则退出
        slow = slow.next;
        fast = fast.next;
    }
    return slow;

        
    }
}
