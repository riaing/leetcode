You are given a doubly linked list which in addition to the next and previous pointers, it could have a child pointer, which may or may
not point to a separate doubly linked list. These child lists may have one or more children of their own, and so on, to produce a 
multilevel data structure, as shown in the example below.

Flatten the list so that all the nodes appear in a single-level, doubly linked list. You are given the head of the first level of the list.


Example:

Input:
 1---2---3---4---5---6--NULL
         |
         7---8---9---10--NULL
             |
             11--12--NULL

Output:
1-2-3-7-8-11-12-9-10-4-5-6-NULL
 


--------------------------------------题目一：flattern ------------------------------------------------------------------------
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

/*
1, no child, continue
2, has child, record nextTmp, then dfs
3, connect child and cur : put child.pre = cur; 
4, connect child's last and nextTmp 
5, set cur.child to null 
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
        //System.out.println(outputByLevel(head, 0, new ArrayList<List<Integer>>()));
        return head;
    }
    
    
    ---------------------------------------题目二：按level输出-----------------------------------------------------------
    /*
    Input:
     1---2---3---4---5---6--NULL
         |               |       
         7-8-9-10-NULL   14 (在第二层)
            |
            11--12--NULL
   
   output: [[1, 2, 3, 4, 5, 6], [7, 8, 9, 10, 14], [11, 12]]    
    */
    private List<List<Integer>> outputByLevel(Node head, int index, List<List<Integer>> res) {
        List<Integer> curRes;
        if (res.size() - 1 < index) {
             curRes = new ArrayList<Integer>();
            res.add(curRes);
        }
        else {
            curRes = res.get(index);
        }
      
        Node cur = head; 
        while (cur != null) {
             if (cur.val == 14) {
                    System.out.println(index);
                }
            if (cur.child != null) {
               
                outputByLevel(cur.child, index+1, res);
            }
            curRes.add(cur.val);
            cur = cur.next; 
        }
        return res;
    } 
}
