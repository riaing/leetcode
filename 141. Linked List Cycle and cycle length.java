Given a linked list, determine if it has a cycle in it.

To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.

 

Example 1:

Input: head = [3,2,0,-4], pos = 1
Output: true
Explanation: There is a cycle in the linked list, where tail connects to the second node.


Example 2:

Input: head = [1,2], pos = 0
Output: true
Explanation: There is a cycle in the linked list, where tail connects to the first node.


Example 3:

Input: head = [1], pos = -1
Output: false
Explanation: There is no cycle in the linked list.


 

Follow up:

Can you solve it using O(1) (i.e. constant) memory?

---------------------------------------------------------------------------------------------------------------
快慢指针，当指针相遇时，有cycle

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
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }
}

---------------- 2022,1.20 提高版 ------------------------------------------------------------
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
/*
1. find if has a cycle 
2. if cycle, find cycle length 
*/
public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode quick = head; 
        while (quick != null && quick.next != null) {
          slow = slow.next;
          quick = quick.next.next;
          if (slow == quick) {
              // 衍生：find cycle length 
              int len = findCycleLength(slow);
              System.out.println("cycle len: " + len);
              return true;
          }
        }
        return false;
    }
    
    // 衍生：find cycle length 
    // 解：当 slow 和 fast 相遇时，他们必定在 cycle 里。于是记录下相遇点，用另一个 poiter 往前走，直到再次碰到相遇点则可得 cycle length
    private int findCycleLength(ListNode meetPoint) {
        int len = 1;
        ListNode cur = meetPoint.next;
        while (cur != meetPoint) {
            len++;
            cur = cur.next;
        }
        return len;
    }
}
