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

------------优化：like inorder traversal，记录当前node。先处理左边，再root，再右边。 O（n）-------------------------------
不同于找中点的思路，这里更像是inorder traversal思路。首先有一个隐形的pointer来记录当前处理到的node。从头开始，先处理size/2个node，形成左子树，
然后pointer移到下一个node，作为root，然后处理root.next 开头的长度为size - 1 - size/2的list，作为右子树。这样的话每个node只访问了一遍，
时间是o(n), 空间是O（lgn）stack space。

public class Solution {
    private ListNode current;

    private int getListLength(ListNode head) {
        int size = 0;

        while (head != null) {
            size++;
            head = head.next;
        }

        return size;
    }

    public TreeNode sortedListToBST(ListNode head) {
        int size;

        current = head;
        size = getListLength(head);

        return sortedListToBSTHelper(size);
    }

    public TreeNode sortedListToBSTHelper(int size) {
        if (size <= 0) {
            return null;
        }

        TreeNode left = sortedListToBSTHelper(size / 2);
        TreeNode root = new TreeNode(current.val);
        current = current.next;
        TreeNode right = sortedListToBSTHelper(size - 1 - size / 2);

        root.left = left;
        root.right = right;

        return root;
    }
}
-------------------同样思路优化，但不用global var，定义一个class来存treeNode和下一个要访问的listNode----------------------------------
  思路同上，但用class来记录下一个需要访问的listNode，可以想像有一个隐形的pointer被这个class携带。time,space同上
  
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
/**
从头开始，先处理size/2个node，形成左子树，然后pointer移到下一个node，作为root，然后处理root.next 开头的长度为size - 1 - size/2的list，
作为右子树。这样的话每个node只访问了一遍，时间是o(n), 空间是O（lgn）stack space。
*/
class Solution {
    
    class Node {
        TreeNode _root;
        ListNode _nextHead; 
        
        Node(TreeNode root, ListNode nextHead) {
            _root = root;
            _nextHead = nextHead; 
        }
    }
    
    public TreeNode sortedListToBST(ListNode head) {
        // find the size of the list 
        int size = 0; 
        ListNode cur = head; 
        while (cur != null) {
            size ++;
            cur = cur.next;
        }
        Node root = helper(head, size);
        return root._root;
    }
    
    private Node helper(ListNode head, int size) {
        if (size <= 0) {
            return new Node(null, head);
        }
        
         if (size == 1) {
            TreeNode leave = new TreeNode(head.val);
            return new Node(leave, head.next);
        }
        
        Node leftNode = helper(head, size / 2);
        Node rightNode = helper(leftNode._nextHead.next, size - 1 - size / 2); 
      
        TreeNode treeRoot = new TreeNode(leftNode._nextHead.val);
       
        treeRoot.left = leftNode._root;
        treeRoot.right = rightNode._root;
        
        Node root = new Node(treeRoot, rightNode._nextHead);
        return root;
    }
}
  
  
