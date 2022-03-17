Given the head of a Singly LinkedList, write a method to check if the LinkedList is a palindrome or not.

Your algorithm should use constant space and the input LinkedList should be in the original form once the algorithm is finished. The algorithm should have O(N)
O(N)
 time complexity where ‘N’ is the number of nodes in the LinkedList.

Example 1:

Input: 2 -> 4 -> 6 -> 4 -> 2 -> null
Output: true
Example 2:

Input: 2 -> 4 -> 6 -> 4 -> 2 -> 2 -> null
Output: false
  
  ----------------------- LinkedList 基础的应用 ------------------------------------------
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

/*
1. We can use the Fast & Slow pointers method similar to Middle of the LinkedList to find the middle node of the LinkedList.
2. Once we have the middle of the LinkedList, we will reverse the second half.
3. Then, we will compare the first half with the reversed second half to see if the LinkedList represents a palindrome.
4. Finally, we will reverse the second half of the LinkedList again to revert and bring the LinkedList back to its original form.

*/
class Solution {
    public boolean isPalindrome(ListNode head) {
        // 1. find middle of the list 
        ListNode mid = middleNode(head); 
        // 2. reverse the second half 
        ListNode secondHead = mid.next;
        mid.next = null; 
        ListNode secondHalfReverse = reverse(secondHead); // second half's length = first half - 1; // or first half length
        // 3. compare 
        ListNode cur1 = head;
        ListNode cur2 = secondHalfReverse; 

        while (cur2 != null) {
            if (cur1.val != cur2.val) {
                // reverse back and return 
                mid.next = reverse(secondHalfReverse); 
                return false;
            }
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        // 4. reverse back the second half 
        mid.next = reverse(secondHalfReverse); 
        return true;
    }
    
    private ListNode middleNode(ListNode head) {
        ListNode slow = head; 
        ListNode fast = head.next; // 求第一个时，fast = head.next即可
        while(fast != null && fast.next != null) {
            slow = slow.next; 
            fast = fast.next.next; 
        }
        
        return slow; 
    }
    
    private ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode cur = head; 
        while (cur != null) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur; 
            cur = tmp;
        }
        return pre; 
    }
}
