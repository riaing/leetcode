这道题就是说给定一个x的值，小于x都放在大于等于x的前面，并且不改变链表之间node原始的相对位置。每次看这道题我老是绕晕，纠结为什么4在3的前面。。
其实还是得理解题意，4->3->5都是大于等3的数，而且这保持了他们原来的相对位置 。

---------------------------------------- 记住方法 ------------------------------------------------------------------
所以，这道题是不需要任何排序操作的，题解方法很巧妙。

new两个新链表，一个用来创建所有大于等于x的链表，一个用来创建所有小于x的链表。

遍历整个链表时，当当前node的val小于x时，接在小链表上，反之，接在大链表上。这样就保证了相对顺序没有改变，而仅仅对链表做了与x的比较判断。

最后，把小链表接在大链表上，别忘了把大链表的结尾赋成null。


/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode partition(ListNode head, int x) {
        if(head == null || head.next == null){
            return head; 
        }
        ListNode big = new ListNode(-1);
        ListNode bigHead = big;
        ListNode small = new ListNode(-1);
        ListNode smallHead = small;
        while(head != null){
            if(head.val < x){
                small.next = head;
                small =small.next;
            }
            else{
                big.next = head;
                big = big.next;
            }
            head = head.next; 
        }
        big.next = null;
        small.next = bigHead.next;
        return smallHead.next; 
    }
}

--------------------   数学归纳法  O（1）space  ------------------------------------------------------------------------------
  /**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode partition(ListNode head, int x) {
        if (head == null || head.next == null) {
            return head;
        }
        // ensure that list has at least two nodes.
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        //
        ListNode lastSmaller = head.val < x ? head : dummy;
        ListNode pre = head;
        ListNode cur = head.next;
        while (cur != null) {
            if (cur.val >= x) {
                pre = cur;
                cur = cur.next;
            }
            else {
                // to reserve the original order 
                if (pre.val < x) {
                    lastSmaller = cur;
                    pre = cur;
                    cur = cur.next;
                }
                else {
                pre.next = cur.next;
                ListNode tmp = lastSmaller.next;
                lastSmaller.next = cur;
                cur.next = tmp;
             
                // update 
                lastSmaller = cur;
                cur = pre.next; 
                }
            }  
        }
        return dummy.next;
    }
}
