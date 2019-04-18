---------------------------find middle O(nlgn)-----------------------------------------------------------
类似于 Convert Sorted array to Binary Search Tree,先找到中点（o（n））当做root，再递归中点左边（记得要在中点前断开）的list作为左子树，
右边作为右子树。时间是O（nlgn）
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public TreeNode sortedListToBST(ListNode head) {
         TreeNode root= null;
         //corner case 
         if (head == null){
            return root;
        }
       
        if (head.next == null){
            root = new TreeNode(head.val);
            return root; 
        }
        //find the mid of LinkedList
        ListNode slow = head;
        ListNode quick = head; 
        ListNode tmp = slow; 
        while(quick != null && quick.next != null){
            tmp =slow; 
            slow = slow.next; 
            quick = quick.next.next; 
        }
        tmp.next =null; //end of left list
        ListNode rightHead = slow.next; //create for right list 
        root = new TreeNode(slow.val); 
        root.left = sortedListToBST(head);
        root.right = sortedListToBST(rightHead); 
        
        return root ; 
        
    }
}

------------优化：like inorder traversal，记录当前node。先
public class Solution {
