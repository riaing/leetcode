Sort a linked list in O(n log n) time using constant space complexity.

Example 1:

Input: 4->2->1->3
Output: 1->2->3->4
Example 2:

Input: -1->5->3->4->0
Output: -1->0->3->4->5
--------------------------merge sort写法， 找中点，合并两个sorted list------------------------------------------------------------------------------------------------
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

-------------------------quick sort 写法：找中点，partition，合并----------------------------------------------------------------------------------
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
        // 1, 找中点
        ListNode middle = findMiddle(head);
        // 2, 根据中点partition成两个不包括中点node的list，partition[0]为值小于等于中点的，[1]大于中点的.注意这里考虑了重复元素
        ListNode[] partition = partitionList(head, middle);
        ListNode small = sortList(partition[0]);
        ListNode large = sortList(partition[1]);
        
        //3， 合并。
        // a, 左边没值，直接中点接上右边。 
        // b, 右边每值，找到左边最后node，接上右边
        // c, 两边都有值，左边最后node->中点->右边
        if (small == null) {
            middle.next = large;
            return middle;
        }
 
        else{
            // find the last element of small 
             ListNode smallEnd = small;
            while (smallEnd.next != null) {
                smallEnd = smallEnd.next;
            }
            smallEnd.next = middle;
            if (large == null) {
                middle.next = null;
            }
            else {
                middle.next = large;
            }
           
            return small;
        }
    }
    
    private ListNode findMiddle(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    
    // 和普通partition时，quick sort的partition不包括本元素
    private ListNode[] partitionList(ListNode head, ListNode node) {
        ListNode dummySmall = new ListNode(0);
        ListNode small = dummySmall;
        ListNode dummyLarge = new ListNode(0);
        ListNode large = dummyLarge;
        while (head != null) {
            if (head.val <= node.val && head != node) { //重复值的node加进小的一边
                small.next = head;
                small = small.next;
            }
            else if (head.val > node.val) {
                large.next = head;
                large = large.next;
            }
            head = head.next;
        }
        // 必须要记得断掉！
        small.next = null;
        large.next = null;
        return new ListNode[]{dummySmall.next, dummyLarge.next};
    }
}
