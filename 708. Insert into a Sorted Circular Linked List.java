Given a Circular Linked List node, which is sorted in ascending order, write a function to insert a value insertVal into the list such that it remains a sorted circular list. The given node can be a reference to any single node in the list and may not necessarily be the smallest value in the circular list.

If there are multiple suitable places for insertion, you may choose any place to insert the new value. After the insertion, the circular list should remain sorted.

If the list is empty (i.e., the given node is null), you should create a new single circular list and return the reference to that single node. Otherwise, you should return the originally given node.

 

Example 1:


 
Input: head = [3,4,1], insertVal = 2
Output: [3,4,1,2]
Explanation: In the figure above, there is a sorted circular list of three elements. You are given a reference to the node with value 3, and we need to insert 2 into the list. The new node should be inserted between node 1 and node 3. After the insertion, the list should look like this, and we should still return node 3.



Example 2:

Input: head = [], insertVal = 1
Output: [1]
Explanation: The list is empty (given head is null). We create a new single circular list and return the reference to that single node.
Example 3:

Input: head = [1], insertVal = 0
Output: [1,0]
 

Constraints:

The number of nodes in the list is in the range [0, 5 * 104].
-106 <= Node.val, insertVal <= 106


------------------------- pre,next 指针 ----------------
/*
// Definition for a Node.
class Node {
    public int val;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _next) {
        val = _val;
        next = _next;
    }
};
*/

/*
审题：
1. head 不代表最小的node
2. you should return the originally given node.


思路
1. 维护cur， pre
找到断口处，插头尾： if insert < 头&尾 插头，if insert > 头&尾插尾。 code一样
插中间： if insert > pre & insert < cur 
走了一圈没插的地方，则随便插到当前

O（N）

Test
1. 头，尾，空，一个node
2. [3,3,3], 0
*/
class Solution {
    public Node insert(Node head, int insertVal) {
        Node insertion = new Node(insertVal);
        // 没有node 
        if (head == null) {
            insertion.next = insertion; 
            return insertion;
        }
        // 只有一个node
        if (head.next == head) {
            head.next = insertion;
            insertion.next = head; 
            return head; 
        }
        
        Node pre = head;
        Node cur = head.next; 
        while (true) {
            // 1, normal case 
            if (pre.val <= insertVal && insertVal <= cur.val) { 
                insertTo(pre, cur, insertion);
                break;
            }
            
           // 2. 判断断口
            if (pre.val > cur.val) {
                if (insertVal <= cur.val || insertVal >= pre.val) {
                    insertTo(pre, cur, insertion);
                    break; 
                }
            }
            
            // 3. reached end of list without suitable insert position
            if (cur == head) { //走了一圈没找到解。【3，3，3】， 5
                insertTo(pre, cur, insertion);
                break;
            }
            pre = cur;
            cur = cur.next;
        }
        return head; 
    }
    
    private void insertTo(Node pre, Node cur, Node insertion) {
         pre.next = insertion;
        insertion.next = cur; 
    }
}
