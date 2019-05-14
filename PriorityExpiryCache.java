 ------------------------------------------ 5.13.19, Tesla 电面 -------------------------------
/*
Hi Yoko, I have implemented those three methods below! :) 
Some thoughts: 
at beginning, I thought to use priorityQueue that sorts nodes by their priority, so in evit(),
we can retrive those nodes with least priority, and according to their usage(from least to most recent), we remove them from our valueMap and priorityQueue. 

Cons: if, say, nodes in our heap are all have the same priority, by the solutions above, we need to iterate all nodes in heap and find the one that hase least usage, which cause to O(n)

so, I'm thinking about in a reverse way: 
we can create n lists according to priority, and put nodes in the same list if they have the same priority. 
for every incoming node, we add it into the end of list according to its priority, so the list's job is to ensure the order(least recent used at beginning and most recent used at end),
this is done in get() and set() methods

in our evict() method, first, we find the list in which nodes have the lowest priority, and we can remove the list's head by counts. This head node is guarantted to be the one that has the lowest priority and is least recent used. 

How can we find the list that has the lowest priority nodes? We can use a TreeMap<Priority, 
List<Node>>. and TreeMap.firstKey() returns us the list that has the lowest priority nodes. 
In this case, we reduce our time from O(n) -> O(lgn) for every need-to-remove node. 

Some improvement we could do:
As we discussed, in get method, we need to update the node's occurance by extracting it from the list, and move it to the list end. And single linked list will cause O(n) for finding the node's parent. 
In this case, we can use doublyLinkedList that reduces O(n) to O(1).

Additional approach to delete node that expires: 
Instead of going over all nodes, we can save a node's time to itself relation into a TreeMap<time, List<Node>> timeMap. Essentailly the timeMap provides up a soring function so that we don't need to traverse through all nodes. And of course, we need to update our valueMap and priorityMap when deleting expired node. 

Here it is! I enjoyed the time talking to you today. And hope I have the chance to discuss more fun problems with you in the future! 
*/
import java.io.*;
import java.util.*;
import com.google.common.base.Preconditions;

/*

Your task is to implement a PriorityExpiryCache cache with a max capacity.  Specifically implement
  the evictItems function and add data structures to the PriorityExpiryCache object as needed.
  You do NOT need to implement the get or set methods.

It should support these operations:
  Get: Get the value of the key if the key exists in the cache and is not expired.
  Set: Update or insert the value of the key with a priority value and expiretime.
    Set should never ever allow more items than maxItems to be in the cache.
    When evicting we need to evict the lowest priority item(s) which are least recently used.

Example:
p5 => priority 5
e10 => expires at 10 seconds since epoch

PriorityExpiryCache c = new PriorityExpiryCache(5);
c.set("A", 1, p5, e100)
c.set("B", 1, p15, e3)
c.set("C", 1, p5, e10)
c.set("D", 1, p1, e15)
c.set("E", 1, p5, e150)
c.get("C")

time.Now() = e0
c.setMaxItems(5)
c.keys() = ["A", "B", "C", "D", "E"]
// 5 keys, all 5 items are included

time.Now() = e5
c.setMaxItems(4)
c.keys() = ["A", "C", "D", "E"]
// "B" is removed because it is expired.  e3 < e5

c.setMaxItems(3)
c.keys() = ["A", "C", "E"]
// "D" is removed because it the lowest priority
// D's expire time is irellevant.

c.setMaxItems(2)
c.keys() = ["C", "E"]
// "A" is removed because it is least recently used."
// A's expire time is irellevant.

c.setMaxItems(1)
c.keys() = ["C"]
// "E" is removed because C is more recently used (due to the Get("C") event).

 
 */

public class PriorityExpiryCache<T> {
  private int maxItems;
  /* Group nodes by their priority. */
  TreeMap<Integer, List<Node>> priorityMap; 
  Map<String, Node> valueMap;
  /* Group nodes by their time added in. */
  TreeMap<Integer, List<Node>> timeMap;
  

  class Node {
    String key;
    T value;
    int priority;
    long time; 
    
    public Node(String key, T value, int priority, long time) {
      this.key = key;
      this.value = value; 
      this.priority = priority;
      this.time = time; 
    }
  }
  

  public PriorityExpiryCache(@NonNull int maxItems) {
    this.maxItems = maxItems;
    this.priorityMap = new TreeMap<Integer, List<Node>>(); 
    this.valueMap = new HashMap<String, Node>();
    this.timeMap = new TreeMap<Integer, List<Node>>();
    
  }
  
  public T get(String key) {
    Preconditions.checkArgument(valueMap.containsKey(key));
    
    Node nodeToMove = valueMap.get(key);
    // update the priorityMap 
    List<Node> updateList = priorityMap.get(valueMap.get(key).priority);
    // find the curNode and move it to the end of list 
    updateList.remove(noeToMove);
    updateList.add(noeToMove); 
    return nodeToMove.value;
  }
  
  public void set(String key, T value, int priority, long time) {
    Preconditions.checkArgument(maxItems < valueMap.size());
    Node newNode = new Node(key, value, priority, time);
    valueMap.add(key, newNode);
    if (!timeMap.containsKey(time)) {
      timeMap.put(time, new LinkedList<Node>());
    }
    timeMap.get(time).add(newNode);
    
    if (!priorityMap.containsKey(priority)) {
      priorityMap.put(priority, new LinkedList<Node>());
    }
    priorityMap.get(priority).add(newNode);
     
  }
  
  public void setMaxItems(int maxItems) {
    this.maxItems = maxItems;
    evict();
  }
  
  // evict() will evict count items from the cache to make room for new ones.
  public void evict() {
    long curTime = time.Now();
    
    // first, delete those nodes that expires 
    while (!timeMap.isEmpty() && timeMap.firstKey() < curTime) {
      List<Node> expiredNodes = timeMap.get(timeMap.firstKey());
      timeMap.remove(timeMap.firstKey());
      
      // remove expired nodes from both value map and priority map
      for (Node node : expiredNodes) {
        map.remove(node.key);
        priorityMap.get(node.priority).remove(node);
        if (priorityMap.get(node.priority).isEmpty()) {
          priorityMap.remove(node.priority);
      }
    }
     
  
   int difference = map.size() - maxItems; 
   if (difference <= 0) {
    return;
   }
   
  // we remove the nodes that has the lowest prioirty, and least recent use    
  List curList = priorityMap.get(priorityMap.firstKey())；
   while ( difference > 0) {
     Node curNodeToRemove = curList.get(0);
     // remove it from value map and priority map  
     valueMap.remove(curNodeToRemove.key);
     curList.remove(0);
     
     if (curList.size() == 0) {
      priorityMap.remove(priorityMap.firstKey());
      curList = priorityMap.get(priorityMap.firstKey())；
     }
     difference--;
   } 
  }
  
    
  
}
