Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.

For example,
Given 1->2->3->3->4->4->5, return 1->2->5.
Given 1->1->1->2->3, return 2->3.

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null){
            return null;
        }
        ListNode dum = new ListNode(0);
        dum.next = head; 
        ListNode pointer = dum;
        while(pointer.next != null && pointer.next.next != null){
            if(pointer.next.val == pointer.next.next.val){
            while(pointer.next != null && pointer.next.next != null && pointer.next.val == pointer.next.next.val ){
                pointer.next = pointer.next.next; 
            }
            pointer.next = pointer.next.next; 
            }
            else{
            pointer = pointer.next;
            }
        }
        return dum.next; 
    }
}

// solution 2, 记录重复的值，将next的值与此值比较即可。 
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode dum = new ListNode(0);
        dum.next = head; 
        ListNode pointer = dum;
        
        while( pointer.next != null && pointer.next.next != null){
            if(pointer.next.val == pointer.next.next.val){
                int val = pointer.next.val;  //记录这个value， 直接remove掉所有重复的。 
                while(pointer.next != null && pointer.next.val == val ){
                
                    pointer.next = pointer.next.next; 
                }
            
            }
            else{ //必须要有else 

                pointer = pointer.next; 
            }
        }
        return dum.next; 
    }
}
