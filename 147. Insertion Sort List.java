Sort a linked list using insertion sort.


A graphical example of insertion sort. The partial sorted list (black) initially contains only the first element in the list.
With each iteration one element (red) is removed from the input data and inserted in-place into the sorted list
 

Algorithm of Insertion Sort:

Insertion sort iterates, consuming one input element each repetition, and growing a sorted output list.
At each iteration, insertion sort removes one element from the input data, finds the location it belongs within the sorted list, and inserts it there.
It repeats until no input elements remain.

Example 1:

Input: 4->2->1->3
Output: 1->2->3->4
Example 2:

Input: -1->5->3->4->0
Output: -1->0->3->4->5
----------------------------------------------------------------------------------------
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode insertionSortList(ListNode head) {
        ListNode dummy = new ListNode(0);
        // 这个dummy的作用是，把head开头的链表一个个的插入到dummy开头的链表里
        // 所以这里不需要dummy.next = head;
     
        // 遍历list，依次将每个元素插入到dummy为头的新list中
        while (head != null) {
            ListNode start = dummy;// 每次都从头开始与cur比较
            while (start.next != null && start.next.val <= head.val) {
                start = start.next;
            }
            ListNode next = head.next; //下一个将要处理的元素
            
           //这时候start.next > head, 所以我们把head插到 start和start.next之间.insert node的模板
            ListNode tmp = start.next;
            start.next = head;
            head.next = tmp;
            // 然后更新下一个要访问的元素
            head = next;
        }
     
        return dummy.next;
    }
}
