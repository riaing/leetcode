/*
// Definition for a Node.
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;

    public Node() {}

    public Node(int _val,Node _prev,Node _next,Node _child) {
        val = _val;
        prev = _prev;
        next = _next;
        child = _child;
    }
};
*/
class Solution {
    public Node flatten(Node head) {
        if (head == null) {
            return head;
        }
        Node cur = head; 
        while (cur != null) {
            if (cur.child == null) {
                cur = cur.next;
                continue;
            }
            Node tmpNext = cur.next;
            cur.next = flatten(cur.child);
         
            cur.child.prev = cur;
            cur.child = null;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = tmpNext;
            if (tmpNext != null) {
                 tmpNext.prev = cur;
                 cur = tmpNext;
            }
           
        }
        return head;
    }
}
