Sort a linked list in O(n log n) time using constant space complexity.

Example 1:

Input: 4->2->1->3
Output: 1->2->3->4
Example 2:

Input: -1->5->3->4->0
Output: -1->0->3->4->5
-----------merge sort写法------------------------------------------------------------------------------------------------
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // divide to two sublists,考虑只有两个元素时，如果必须分为两半，那么只能将middle定为前一个元素，然后再middle.next这里切开。[1,2,3] -> [1,2],[3]; [1,2] -> [1],[2]
        ListNode slow = head;
        ListNode fast = head.next; // middle要为前一个元素
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode secondPart = slow.next;
        slow.next = null;
        
        ListNode part1 = sortList(head);
        ListNode part2 = sortList(secondPart);
        
       return mergeTwoSortedList(part1, part2);
    }
    
    private ListNode mergeTwoSortedList(ListNode part1, ListNode part2) {
        if (part1 == null) {
            return part2;
        }
        if (part2 == null) {
            return part1;
        }
        
        ListNode head; 
        if (part1.val <= part2.val) {
            head = part1;
            head.next = mergeTwoSortedList(part1.next, part2); 
        }
        else {
            head = part2;
            head.next = mergeTwoSortedList(part1, part2.next);
        }
        return head;
    }
}
