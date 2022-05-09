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

-------------- 2022 3 hashMap + linkedHashSet 更简单--------------------------------------
  https://mp.weixin.qq.com/s/oXv03m1J8TwtHwMJEZ1ApQ
  /*
1. keyToValue Map -> 方便get时O（1）
2， keyToFreq Map -> 记录key的frequency，方便移出和增加
3. freqToListOfKeys Map -> 1. 加入的key必须保证时序 2. remove 某个key时要是O（1） -> LinkedHashSet(内部实现是double linkedlist)
4、 minFreq -> 每次put一个新key时，更新为1。 每次get时也要更新，

代码框架：
get时
1， increase frequency (key)

put时
1. 如果key exist
    改值
    increase frequency
2. 如果key不在
    b.如果到了capacity  
        removeMinFrequency
        put到 keyToValue， increase frequency到1 
    a. 如果还没到capacity
        put到 keyToValue， increase frequency到1 
3. 跟新 minFreq到1 
*/
class LFUCache {
    Map<Integer, Integer> keyToValue = new HashMap<Integer, Integer>();
    Map<Integer, Integer> keyToFreq = new HashMap<Integer, Integer>();
    Map<Integer, LinkedHashSet<Integer>> freqToKeys = new HashMap<Integer, LinkedHashSet<Integer>>();
    int minFreq = 0; 
    int capacity; 

    public LFUCache(int capacity) {
        this.capacity = capacity; 
    }
    
    public int get(int key) {
        if (keyToValue.containsKey(key)) {
            increaseFrequency(key);
            return keyToValue.get(key);
        }
        return -1; 
        
    }
    
    public void put(int key, int value) {
        if (capacity == 0) {
                return; 
        }
        
        if (keyToValue.containsKey(key)) {
            keyToValue.put(key, value);
            increaseFrequency(key); 
        }
        else {
     
            if (keyToValue.size() >= capacity) {
                removeMinFrequency();
            }
            // add key
            keyToValue.put(key, value);
            keyToFreq.put(key, 1); 
            freqToKeys.putIfAbsent(1, new LinkedHashSet<Integer>());
            freqToKeys.get(1).add(key);
            // update minFreq 
            minFreq = 1; 
        }
    }
    
    private void increaseFrequency(int key) { // update keyToFreq, freqToListOfKeys, minFreq
        // 从old freq中删掉
        int oldFreq = keyToFreq.get(key); 
        LinkedHashSet<Integer> keysWithOldFreq = freqToKeys.get(oldFreq); 
        keysWithOldFreq.remove(key);
        
        if (keysWithOldFreq.isEmpty()) { // 此时这个freq没有key了
            freqToKeys.remove(oldFreq); 
            if (minFreq == oldFreq) {
                minFreq++; 
            }
        }
        
        // 加到新的freq set中
        int newFreq = oldFreq + 1; 
        freqToKeys.putIfAbsent(newFreq, new LinkedHashSet<Integer>());
        freqToKeys.get(newFreq).add(key);
        
        // 更新keyToFreq table
        keyToFreq.put(key, newFreq); 
    }
    
    
    private void removeMinFrequency() {
        // 1. 找到对应的key
        LinkedHashSet<Integer> keysWithMinFreq  = freqToKeys.get(minFreq);
        int keyToRemove = keysWithMinFreq.iterator().next(); 
        // 2. remove from 3 maps. 
        keysWithMinFreq.remove(keyToRemove);
        if (keysWithMinFreq.isEmpty()) { // 此时这个freq没有key了
            freqToKeys.remove(minFreq); 
        }
        keyToValue.remove(keyToRemove);
        keyToFreq.remove(keyToRemove); 
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
