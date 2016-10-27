Reverse a linked list from position m to n. Do it in-place and in one-pass.

For example:
Given 1->2->3->4->5->NULL, m = 2 and n = 4,

return 1->4->3->2->5->NULL.
Given m, n satisfy the following condition:
1 ≤ m ≤ n ≤ length of list.
和之前不同的是，这里的prev设置为n(4)后的一个数(5)，因为希望m(2)连到此数(5)。 同时，要把m前面的数(1)的next连到 n (4)
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if(head == null || head.next == null){
            return head; 
        }
        ListNode dum = new ListNode(0);
        dum.next = head;
        ListNode beforeM = dum; 
         
        ListNode end = dum; 
        for ( int i =1; i <= m-1; i ++ ){
            beforeM= beforeM.next; //find the element that before m   
        }
        ListNode front = beforeM.next;
        for (int i = 1; i <= n; i ++){
            end = end.next;
        }
        ListNode prev = end.next; 
        while(prev != end){
             ListNode n1 = front.next;
            front.next = prev;
            prev = front; 
            front = n1; 
        }
        beforeM.next = end; 
        
        return dum.next; 
    }
}
