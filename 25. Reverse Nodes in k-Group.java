iven the head of a linked list, reverse the nodes of the list k at a time, and return the modified list.

k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.

You may not alter the values in the list's nodes, only nodes themselves may be changed.

 

Example 1:


Input: head = [1,2,3,4,5], k = 2
Output: [2,1,4,3,5]
Example 2:


Input: head = [1,2,3,4,5], k = 3
Output: [3,2,1,4,5]
 

Constraints:

The number of nodes in the list is n.
1 <= k <= n <= 5000
0 <= Node.val <= 1000
  
  ------------------------
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
    public ListNode reverseKGroup(ListNode head, int k) {
        // 1. 判断能 reverse 几次
        int len = 0;
        ListNode cur = head;
        while (cur != null) {
            len++;
            cur = cur.next;
        }
        int times = len / k; 
        
        ListNode sudo = new ListNode(0);
        sudo.next = head; 
        // 2. 维持两 node： left 和 left-1
        ListNode preLeft = sudo;
        ListNode left = head; 
        for (int time = 0; time < times; time++) {
            // 3 根据 range swap
            int start = k * time + 1; 
            int end = start + k - 1; 
            ListNode[] swapped = swapByRange(left, start, end);
            // 4 link swapped 后的头，尾
            preLeft.next = swapped[0];
            left.next = swapped[1];
            // 更新两Node
            preLeft = left;
            left = left.next;
        }
        return sudo.next;  
    }
    
    private ListNode[] swapByRange(ListNode head, int left, int right) {
        ListNode pre = null;
        ListNode cur = head; 
        
        for (int i = 0; i < right - left + 1; i++) {
            ListNode tmp  = cur.next;
            cur.next = pre;
            pre = cur; 
            cur = tmp; 
            
        }
        return new ListNode[]{pre, cur};
    }
} 
