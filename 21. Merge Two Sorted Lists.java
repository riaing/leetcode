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
