Design a HashMap without using any built-in hash table libraries.

To be specific, your design should include these functions:

put(key, value) : Insert a (key, value) pair into the HashMap. If the value already exists in the HashMap, update the value.
get(key): Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key.
remove(key) : Remove the mapping for the value key if this map contains the mapping for the key.
random(): return random key 

Example:

MyHashMap hashMap = new MyHashMap();
hashMap.put(1, 1);          
hashMap.put(2, 2);         
hashMap.get(1);            // returns 1
hashMap.get(3);            // returns -1 (not found)
hashMap.put(2, 1);          // update the existing value
hashMap.get(2);            // returns 1 
hashMap.remove(2);          // remove the mapping for 2
hashMap.get(2);            // returns -1 (not found) 

Note:

All keys and values will be in the range of [0, 1000000].
The number of operations will be in the range of [1, 10000].
Please do not use the built-in HashMap library.
------------------------------------硬核版，把map也implemented了---------------------------------------------------------------
class MyHashMap {
    Node[] listNodes;
    List<Integer> keys; // for random retrival 
    Map<Integer, Integer> indexMap;
    /** Initialize your data structure here. */
    public MyHashMap() {
        this.listNodes = new Node[10001];
        this.keys = new ArrayList<Integer>();
        indexMap = new HashMap<Integer, Integer>(); 
    }
    
    /** value will always be non-negative. */
    public void put(int key, int value) {
        int index = key % 10000;
        if (listNodes[index] == null) {
            Node root = new Node(-1, -1);
            listNodes[index] = root;
            root.next = new Node(key, value);
            keys.add(key);
            indexMap.put(key, keys.size()-1);
        }
        else {
            // whether this key exist 
            Node pre = findPre(listNodes[index], key);
            if (pre.next == null) {
                pre.next = new Node(key, value);
                keys.add(key);
                indexMap.put(key, keys.size()-1);
            }
            else {
                pre.next.val = value;
            }
        }
    }
    
    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {
        int index = key % 10000;
        if (listNodes[index] == null) {
            return -1;
        }
        else {
            Node pre = findPre(listNodes[index], key);
            if (pre.next == null) {
                return -1;
            }
            else {
                return pre.next.val;
            }
        } 
    }
    
    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        int index = key % 10000;
        if (listNodes[index] == null) {
            return;
        }
        else {
            Node pre = findPre(listNodes[index], key);
            if (pre.next == null) {
                return;
            }
            else {
                pre.next = pre.next.next;
                // also remove key from the list 
                if (keys.size() == 1) {
                    keys.clear();
                    indexMap.remove(key);
                }
                else {
                    // swap current key and last key 
                int keyIndex = indexMap.get(key);
                int lastKey = keys.get(keys.size()-1);
                keys.set(keyIndex, lastKey);
                keys.remove(keys.size()-1);
                indexMap.put(lastKey, keyIndex);
                indexMap.remove(key);
                }
         
            }
        } 
    }
    
    public int getRandom() {
        if (!keys.isEmpty()) {
            return -1; 
        }
        Random rand = new Random();
       
        int index = rand.nextInt(keys.size());
        return keys.get(index);
    }
    
    private Node findPre(Node root, int key) {
        Node pre = null;
        Node cur = root;
        while (cur != null && cur.key != key) {
            pre = cur;
            cur = cur.next;
        }
        return pre;
    }
    
    class Node {
        int key;
        int val;
        Node next;
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
}

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */

------------------------简化版（bolt onsite question）能直接用map，不需要再implement map --------------------------------------------
 import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Solution {
  Map<Integer, Integer> valueMap;
  List<Integer> keyList;
  // mapping from key -> index in list 
  Map<Integer, Integer> indexMap;
  
  public Solution() {
    this.valueMap = new HashMap<>();
    this.keyList = new ArrayList<Integer>();
    this.indexMap = new HashMap<>();
  }
  
  public int get(int key) {
    return valueMap.get(key);
  }
  
  public void put(int key, int value) {
    // add this to list 
    if (!valueMap.containsKey(key)) {
      keyList.add(key);
      indexMap.put(key, keyList.size()-1);
    }
    valueMap.put(key, value);  
  }
  
  public void delete(int key) {
    // 1, delte list 
    int index = indexMap.get(key);
    int lastValue = keyList.get(keyList.size()-1);
    keyList.set(index, lastValue);
    keyList.remove(keyList.size()-1); // check later for time 
    
    --------- 写法一：顺序不能乱 --------
        // 2, update index map
    indexMap.put(lastValue, index);
       // 3, remove index map 
    indexMap.remove(key); 

    -------写法二 ： first remove than update, must be careful when key is the last element -------------------
         // 2, remove index map 
    if (index == keyList.size()+1) {
        indexMap.remove(key);
    }
    else {
      indexMap.remove(key);
        // 3, update index map 
      indexMap.put(lastValue, index);
    }
    -------------------------
    // 4, update value map      
    valueMap.remove(key);
  }
  
  public int getRandom() {
    if (valueMap.isEmpty()) {
      return -1;
    }
    Random r = new Random();
    int randomIndex = r.nextInt(keyList.size()); 
    System.out.println("size " + keyList.size());
    return keyList.get(randomIndex);
  }
  
  
  public static void main(String[] args) {
    Solution s = new Solution();
    s.put(1,1);
    s.put(2,2);
    s.put(3,3);
    s.put(4,4);
    s.delete(3);
    System.out.println(s.getRandom());
  }
  
}
