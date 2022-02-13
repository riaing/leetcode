Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.

Example:

Input:
[
  1->4->5,
  1->3->4,
  2->6
]
Output: 1->1->2->3->4->4->5->6

--------------compare first element by using Priority Queue --------------------------------------------------------------
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
/**
Pquque存链表首元素，每次pop出来后，加入pop元素后的下一个元素，直到queue为kong
Time： o(NlgK) quque的insert，pop都是O(lgK),对于所有元素都进行此操作，总共N个元素
Space：O（K）maintain max size为k的queue
*/
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode head = new ListNode(0);
        ListNode cur = head;
        if (lists == null || lists.length == 0) {
            return head.next;
        }
        
        // adding every first element into queue, maintain a max size k's queue 
        PriorityQueue<ListNode> q = new PriorityQueue<ListNode>((a, b) -> a.val - b.val);
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                q.offer(lists[i]);
            }
        }
        
        while (!q.isEmpty()) {
            ListNode smallest = q.poll();
            cur.next = smallest;
            cur = cur.next;
            if (smallest.next != null) {
                q.offer(smallest.next);
            }
        }
        return head.next;   
    }
}

-----------solution 5 提供了Merge with Divide And Conquer解法，值得一看 ----------------------------------
https://leetcode.com/problems/merge-k-sorted-lists/solution/ 

