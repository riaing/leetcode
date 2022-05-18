Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and put.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.

Follow up:
Could you do both operations in O(1) time complexity?

Example:

LRUCache cache = new LRUCache( 2 /* capacity */ );

cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // returns 1
cache.put(3, 3);    // evicts key 2
cache.get(2);       // returns -1 (not found)
cache.put(4, 4);    // evicts key 1
cache.get(1);       // returns -1 (not found)
cache.get(3);       // returns 3
cache.get(4);       // returns 4

思路：

LRU cache数据结构的核心就是当存储空间满了，而有新的item要插入时，要先丢弃最早更新的item。这里的数据结构需要符合以下条件：

1. 要能快速找到最早更新的item。这里需要将item以更新时间顺序排序。
可选的有：queue，heap，linked list

2. 要能快速访问指定item，并且访问以后要更新它的时间顺序。
对于更新时间顺序这个操作，queue和heap要做到就很困难了。所以这点最佳的是linked list。但linked list中查找指定item需要遍历，这里可以用一个hash table来记录key与节点之间的对应。并且由于要随时更新节点位置，doubly linked list更为适用。




class LRUCache {
    class Node {
        Node prev;
        Node next;
        int key;
        int val;
        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
    
    private Node head;
    private Node tail;
    private int capacity;
    private Map<Integer, Node> map; 
    
    public LRUCache(int capacity) {
        this.head = null;
        this.tail = null;
        this.capacity = capacity;
        this.map = new HashMap<Integer, Node>();
    }
    
    private void deleteHead() {
        if (head == null) {
            return;
        }
        map.remove(head.key);
        if (head == tail) {
            head = tail = null;
        }
        else {
            head = head.next;
            head.prev = null;
        }
    }
    
    private void moveToEnd(Node toMove) {
        // If key doesn't exsit or already at the end. 
        if (toMove == tail) {
            return;
        }
        else if (head == toMove) {
            head = head.next;
            head.prev = null;
        }
        else {
            toMove.prev.next = toMove.next;
            toMove.next.prev = toMove.prev;
        }
        // insert to end, update tail;
        // tail.next = toMove;
        // toMove.prev = tail;
        // toMove.next = null;
        // tail = toMove;
        insertToEnd(toMove);
    }
    
    private void insertToEnd(Node n) {
        if (head == null) {
            head = tail = n;
        }
        else {
            tail.next = n;
            n.prev = tail;
            n.next = null;
            tail = tail.next;
        }
    }
    
    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        moveToEnd(map.get(key));
        return map.get(key).val;
    }
    
    public void put(int key, int value) {
       // When the key has already exsits, update the value, then update its frequency by moving it to the end of list. 
        if (map.containsKey(key)) {
            map.get(key).val = value;
            moveToEnd(map.get(key));
            return;
        }
        if (map.size() == capacity) {
            deleteHead();
        }
        Node n = new Node(key, value);
        map.put(key, n);
        insertToEnd(n); 
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
 ------------------------------------5.7 被面后updatey一下思路，方法和上面一样------------------------------------
 1，use map to fatch data, linkedlist to remain the order, head is the LRU 
 2, 
for put: 
    1, when put the exsiting key(update an key) 
        update the map, find where the node at, extract it from the list, put it to end. 
    2, when put a new key 
        a, check capacity: if meet capacity -> remove head of list, remove map entry of the head node, so this means we need to have
        a relation between node and map so we can know which map entry correspond to the head node of list 
        b, add to map, add node to end of list 
for get:
if has the key in map, we need 
    1, update list: find where the node at, extract it from the list, put it to end. 
    2, get it from map, return
        
 to simplify:  
 get{ moveNodeToEnd } 
 put{
    if containsKey {
        moveNodeToEnd
    }else {
        if = capacity { removeHead;, remove entry in map}
        addToEnd;
    }
 }

---------------- 2022.5 自己写 ----------------
    class Node {
    Node pre;
    Node next;
    int key; 
    int val; 
    
    public Node(int key, int val) {
        this.key = key;
        this.val = val;
    }
}
class LRUList {
    Node sudo = new Node(-1, -1);
    Node tail = sudo; 
    
    public LRUList() {
        
    }
    
    public void add(Node n) {
        tail.next = n;
        n.pre = tail; 
        tail = tail.next; 
    }
    
    public Node removeAtFirst() {
        if (sudo == tail) {
            return null;
        }
        Node head = sudo.next;
        Node nextNode = sudo.next.next; 
        
        // if tail == head 
        if (tail == head) {
            tail = sudo;
        }
        else {
            sudo.next = nextNode;
            if (nextNode != null) {
                nextNode.pre = sudo;
            }
        }

        return head; 
    }
    
    public void remove(Node n) {
        if (n == tail) {
            tail = tail.pre;
            tail.next = null; 
        }
        
          // remove from middle 
        else {
            Node nPre = n.pre;
            Node nNext = n.next;
            nPre.next = nNext;
            nNext.pre = nPre; 
        } 
    }
}

class LRUCache {
    int capacity;;
    Map<Integer, Node> map; 
    LRUList lrulist;     
    public LRUCache(int capacity) {
        this.capacity = capacity; 
        this.map = new HashMap<Integer, Node>();
        this.lrulist = new LRUList();
    }
    
    public int get(int key) {
        if (map.containsKey(key)) {
            // extrac and put it into the end 
            Node curKeyNode = map.get(key);
            lrulist.remove(curKeyNode);
            lrulist.add(curKeyNode);
            return map.get(key).val;
        }
        return -1; 
    }
    
    public void put(int key, int value) {
        Node cur;
        // 1. 没有此key
        if (!map.containsKey(key)) {
            cur = new Node(key, value);
            if (map.size() >= capacity) {
                Node removedNode = lrulist.removeAtFirst();
                // update map 
                int keyToRemove = removedNode.key;
                map.remove(keyToRemove);
            }
              // add curNode
                lrulist.add(cur); 
                map.put(key, cur);
        }
        // 有此key。 更新
        else {
            map.get(key).val = value; 
            cur = map.get(key);
            lrulist.remove(cur);
            lrulist.add(cur); 
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
