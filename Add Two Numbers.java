/**
 * Definition for singly-linked list.
 ou are given two linked lists representing two non-negative numbers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 0 -> 8

 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
         ListNode dum = new ListNode(0);
        if(l1 == null && l2 == null){
            return dum; 
        }
       
        ListNode point = dum; 
        int carry = 0; 
        while (l1 != null && l2!= null){
                int val = l1.val +l2.val+carry; 
                int remain = val%10;
                carry = val/10; 
                point.next = new ListNode(remain); 
                point = point.next; 
                l1 = l1.next;
                l2 = l2.next; 
        }
     
        while(l1 != null){  //if l1 has remaining 
            int val = l1.val+carry;
            int remain = val %10;
            carry = val/10;
            point.next = new ListNode(remain);
            point = point.next; 
            l1 = l1.next; 
        }
        while(l2 != null){ // if l2 has remaining 
            int val = l2.val+carry;
            int remain = val %10;
            carry = val/10;
            point.next = new ListNode(remain);
            point = point.next; 
            l2 = l2.next; 
        }
        
        if(carry != 0){ //check if there still have carry left 
            point.next = new ListNode(carry);
            return dum.next; 
        }
        
        return dum.next;  //if there is point left 
    }
}
