Reverse a linked list from position m to n. Do it in-place and in one-pass.

For example:
Given 1->2->3->4->5->NULL, m = 2 and n = 4,

return 1->4->3->2->5->NULL.
Given m, n satisfy the following condition:
1 ≤ m ≤ n ≤ length of list.
和之前不同的是，这里的prev设置为n(4)后的一个数(5)，因为希望m(2)连到此数(5)。 同时，要把m前面的数(1)的next连到 n (4)

  -------------------------------------------------------------------------
    /**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

// 1， 找到m 前面的点。2，reverse M-N, 3, 连接 pre m 和post n 
class Solution {
    
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null || m >= n) {
            return head;
        }
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        head = dummy;
        int j = 1;
        // 拿到m前面的一个点。因为用了head = dummy，所以此时的head就是m前面的点
        while (j < m) {
            // 
            if (head == null) { //corner case: 如果不够m个点
                return null;
            }
            head = head.next; 
           j++;
        }
        
        // 记录下preM和postN，翻转M-N的nodes，再把preM.next = n, m.next = postN拼接。
        ListNode preM = head;
        ListNode mNode = head.next; 
        //就移动这两个点，
        ListNode nNode = head.next;
        ListNode postN = mNode.next;
 
        for (int i = m; i < n; i++) { // n个node的话就reverse n-1次
            if (postN == null) { // corner case: 没有n个点
                return null;
            }
        // 正常reverse
            ListNode tmp = postN.next;
            postN.next = nNode;
            nNode = postN;
            postN = tmp;
        }
        // 拼接
        mNode.next = postN;
        preM.next = nNode;
        return dummy.next;
    }
}
