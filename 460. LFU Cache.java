Design and implement a data structure for Least Frequently Used (LFU) cache. It should support the following operations: get and put.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
put(key, value) - Set or insert the value if the key is not already present. When the cache reaches its capacity, it should 
invalidate the least frequently used item before inserting a new item. For the purpose of this problem, when there is a tie (i.e., 
two or more keys that have the same frequency), the least recently used key would be evicted.

Follow up:
Could you do both operations in O(1) time complexity?

Example:

LFUCache cache = new LFUCache( 2 /* capacity */ );

cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // returns 1
cache.put(3, 3);    // evicts key 2
cache.get(2);       // returns -1 (not found)
cache.get(3);       // returns 3.
cache.put(4, 4);    // evicts key 1.
cache.get(1);       // returns -1 (not found)
cache.get(3);       // returns 3
cache.get(4);       // returns 4

---------------------double linked list 加 hashmap-----------------------------------------------
http://shirleyisnotageek.blogspot.com/2016/12/lfu-cache.html

http://bookshadow.com/weblog/2016/11/22/leetcode-lfu-cache/ 
整体数据结构设计如下图所示：
head --- FreqNode1 ---- FreqNode2 ---- ... ---- FreqNodeN
              |               |                       |               
            first           first                   first             
              |               |                       |               
           KeyNodeA        KeyNodeE                KeyNodeG           
              |               |                       |               
           KeyNodeB        KeyNodeF                KeyNodeH           
              |               |                       |               
           KeyNodeC         last                   KeyNodeI           
              |                                       |      
           KeyNodeD                                 last
              |
            last
            
            
            
            
class LFUCache {
    Map<Integer, Integer> valueMap;
    Map<Integer, Node> nodeMap;
    Node head;
    int capacity;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.valueMap = new HashMap<Integer, Integer>();
        this.nodeMap = new HashMap<Integer, Node>();
        this.head = null;
    }
    
    public int get(int key) {
        if (!valueMap.containsKey(key)) {
            return -1;
        }
        increaseFreq(nodeMap.get(key), key);
       
        return valueMap.get(key);
    }
    
    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }
        // 1, check capacity 
        if (!valueMap.containsKey(key) && valueMap.size() == capacity) {
           
            // remove the LFU by LRU
            int toRemove = head.keys.poll();
             
            // remove from list 
            if (head.keys.isEmpty()) {
                remove(head);
            }
            valueMap.remove(toRemove);
            nodeMap.remove(toRemove);
        }
        
        // 2, key exist, change freq
       
        if (valueMap.containsKey(key)) {
            
            increaseFreq(nodeMap.get(key), key);
        }
        else {
            // if head == null
            if (head == null || head.freq != 1) {
                Node newNode = new Node(1);
                newNode.next = head;
                if (head != null) {
                    head.pre = newNode;
                }
                head = newNode;
            }
      
            head.keys.offer(key);
            nodeMap.put(key, head);
        }
        valueMap.put(key, value);
    }
    
    private void increaseFreq(Node node, int key) {
        //1, add to new freq bucket 
        if (node.next != null && node.next.freq == node.freq+1) {
            node.next.keys.offer(key);
            nodeMap.put(key, node.next);
        }
        else {
            // create new node在当前node后面
            Node newNode = insert(node, node.freq+1);
            newNode.keys.offer(key);
            nodeMap.put(key, newNode);
        }
        // 2, remove key from list
        node.keys.remove(key);
        // 3, if need to remove old keys' queue
        if (node.keys.isEmpty()) {
            remove(node);
        }
    }
    
    private Node insert(Node node, int freq) {
        Node newNode = new Node(freq);
        if (node.next == null) {
            node.next = newNode;
            newNode.pre = node;
        }
        else {
            Node tmp = node.next;
            node.next = newNode;
            newNode.pre = node;
            newNode.next = tmp;
            tmp.pre = newNode;
        }
        return newNode;
    }
    
    private void remove(Node node) {
        if (node == head) {
            head = node.next;
        }
        else if (node.next == null) {
            node.pre.next = null;
        }
        else {
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
    }
    
    class Node {
        Node next;
        Node pre;
        Queue<Integer> keys;
        int freq;
        public Node(int freq) {
            this.next = null;
            this.pre = null;
            this.keys = new LinkedList<Integer>();
            this.freq = freq;
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */            
