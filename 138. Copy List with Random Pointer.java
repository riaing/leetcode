A linked list is given such that each node contains an additional random pointer which could point to any node 
in the list ornull.

Return a deep copy of the list.

 

Example 1:



Input:
{"$id":"1","next":{"$id":"2","next":null,"random":{"$ref":"2"},"val":2},"random":{"$ref":"2"},"val":1}

Explanation:
Node 1's value is 1, both of its next and random pointer points to Node 2.
Node 2's value is 2, its next pointer points to null and its random pointer points to itself.
 

Note:

You must return the copy of the given head as a reference to the cloned list.

----------------using hashmap O(N) space-------------------------------------------------------------------------------------

/*
// Definition for a Node.
class Node {
    public int val;
    public Node next;
    public Node random;

    public Node() {}

    public Node(int _val,Node _next,Node _random) {
        val = _val;
        next = _next;
        random = _random;
    }
};
*/

/**
用map存（old node，new node），再根据key的random来link new node的random
Time: O（N）make one pass over the original linked list.
Space： O(N) as we have a dictionary containing mapping from old list nodes to new list nodes. Since there are NN nodes, we have O(N) space complexity. 
*/
class Solution {
    public Node copyRandomList(Node head) {
        //HashMap 
        Map<Node, Node> map = new HashMap<Node, Node>();
        Node dummy = new Node(0, null, null);
        Node pre = dummy;
        while (head != null) {
            pre.next = new Node(head.val, null, null);
            map.put(head, pre.next);
            pre = pre.next;
            head = head.next;
        }
        for (Map.Entry<Node, Node> entry : map.entrySet()) {
            entry.getValue().random = map.get(entry.getKey().random);
        }
        return dummy.next;                                 
    }
}


---------------小trick，将copy的node连在Node后 ---------------------------------------------------------------------------------
 /*
// Definition for a Node.
class Node {
    public int val;
    public Node next;
    public Node random;

    public Node(int _val) {val = _val;}

    public Node(int _val,Node _next,Node _random) {
        val = _val;
        next = _next;
        random = _random;
    }
};
*/

/**
用next来代表映射关系，讲copy的node连在原node后。
Time: O（N）make one pass over the original linked list.
Space： O(1) 
*/
class Solution {
    public Node copyRandomList(Node head) {
        //原list每个node后面添加它的copy
        // insert copy between node and its next 
        Node cur = head;
        
        while (cur != null) {
            Node tmp = cur.next;
            cur.next = new Node(cur.val);
            cur.next.next = tmp;
            cur = cur.next.next;
        }
         
        // Link random 
        cur = head; 
        while (cur != null) {
            cur.next.random = cur.random == null ? null : cur.random.next;
            cur = cur.next.next;
        }
        
        // split into two list 
        cur = head;
        Node dummy = new Node(0);
        Node newListNode = dummy;
        while (cur != null) {
            newListNode.next = cur.next;
            newListNode = newListNode.next;
            // 删除掉copy的node
            cur.next = cur.next.next;
            cur = cur.next; 
        }
        return dummy.next;
    }
}
