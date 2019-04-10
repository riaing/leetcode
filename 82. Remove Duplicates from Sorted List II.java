Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.

For example,
Given 1->2->3->3->4->4->5, return 1->2->5.
Given 1->1->1->2->3, return 2->3.

-----------------------------与remove dup1思路一样，当两个node重复时，讲dup1.next = du1.next.next, 移除第二个重复元素。不同之处在于，我们
用一个dummy来记录nodes的pre-------------------------------------------------------------------------------------------------
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

--------------直接除掉重复元素写法2 - 记录重复的值，将next的值与此值比较即可 -----------------------------------------------------
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
                int val = pointer.next.val;  //记录这个value， 直接remove掉之后所有等于这个值的node。 
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

---------------自己的写法，记录pre，再把pre直接连到重复元素之后的那个。那么就要注意[1,2,2]中第二个后面为null
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        // corner case
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode p1 = head;
        ListNode p2 = head.next;
        while (p1 != null && p2 != null) {
            if (p1.val == p2.val) {
                while (p2 != null && p1.val == p2.val) {
                p2 = p2.next;
                }
                pre.next = p2;
                p1 = p2;
                if (p2 == null) { // [1,2,2]
                    break;
                }
                p2 = p2.next;
            }
            else {
                p1 = p1.next;
                p2 = p2.next;
                pre = pre.next;
            }
        }
        return dummy.next;
    }
}
