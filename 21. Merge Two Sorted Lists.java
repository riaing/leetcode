Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.

Example:

Input: 1->2->4, 1->3->4
Output: 1->1->2->3->4->4


----------------------------------------------------------------------------------------------
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode mergeTwoLists(ListNode p1, ListNode p2) {

        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        while (p1 != null && p2 != null) {
            if (p1.val <= p2.val) {
                cur.next = p1;
                p1 = p1.next;
            }
            else {
                cur.next = p2;
                p2 = p2.next;
            }
            cur = cur.next;
        }
        // exactly one of l1 and l2 can be non-null at this point, so connect
        if (p1 != null) {
            cur.next = p1; 
        }
        if (p2 != null) {
            cur.next = p2;
        }
        return dummy.next;
    }
}
----------------------------------recursion 写法----------------------------------------------------------------------------
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

----------------------------Merge two list 解法----------------------------------------------------------------------------
    普通的两个list（非sort），一个接一个的merge，
    eg: 1->2, 4->3 会变成 1->4->2->3；  1->2-5, 4->3 会变成 1->4->2->3->5
        
         private ListNode mergeTwoList(ListNode p1, ListNode p2) {
        if (p1 == null) {
            return p2; 
        }
        
        if (p2 == null) {
            return p1;
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
        return head.next;
    }
