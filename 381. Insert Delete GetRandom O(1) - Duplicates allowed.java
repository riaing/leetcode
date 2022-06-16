RandomizedCollection is a data structure that contains a collection of numbers, possibly duplicates (i.e., a multiset). It should support inserting and removing specific elements and also removing a random element.

Implement the RandomizedCollection class:

RandomizedCollection() Initializes the empty RandomizedCollection object.
bool insert(int val) Inserts an item val into the multiset, even if the item is already present. Returns true if the item is not present, false otherwise.
bool remove(int val) Removes an item val from the multiset if present. Returns true if the item is present, false otherwise. Note that if val has multiple occurrences in the multiset, we only remove one of them.
int getRandom() Returns a random element from the current multiset of elements. The probability of each element being returned is linearly related to the number of same values the multiset contains.
You must implement the functions of the class such that each function works on average O(1) time complexity.

Note: The test cases are generated such that getRandom will only be called if there is at least one item in the RandomizedCollection.

 

Example 1:

Input
["RandomizedCollection", "insert", "insert", "insert", "getRandom", "remove", "getRandom"]
[[], [1], [1], [2], [], [1], []]
Output
[null, true, false, true, 2, true, 1]

Explanation
RandomizedCollection randomizedCollection = new RandomizedCollection();
randomizedCollection.insert(1);   // return true since the collection does not contain 1.
                                  // Inserts 1 into the collection.
randomizedCollection.insert(1);   // return false since the collection contains 1.
                                  // Inserts another 1 into the collection. Collection now contains [1,1].
randomizedCollection.insert(2);   // return true since the collection does not contain 2.
                                  // Inserts 2 into the collection. Collection now contains [1,1,2].
randomizedCollection.getRandom(); // getRandom should:
                                  // - return 1 with probability 2/3, or
                                  // - return 2 with probability 1/3.
randomizedCollection.remove(1);   // return true since the collection contains 1.
                                  // Removes 1 from the collection. Collection now contains [1,2].
randomizedCollection.getRandom(); // getRandom should return 1 or 2, both equally likely.
 

Constraints:

-231 <= val <= 231 - 1
At most 2 * 105 calls in total will be made to insert, remove, and getRandom.
There will be at least one element in the data structure when getRandom is called.


----------------------------------------Map + arraylist -------------
/*
array来放每个value，实现getRandom的O（1）
记录每个value 对应的index们 。Map<value, set of index>. 
删除时仍然是把valueswap到最后一位，再从array中删除
同时得更新最后一位的index ，所以需要index set来迅速找到
*/
class RandomizedCollection {
    Map<Integer, Set<Integer>> map; // val -> indexs...
    List<Integer> arr; 
    Random rand = new Random();

    public RandomizedCollection() {
        this.map = new HashMap<>();
        this.arr = new ArrayList<>(); 
    }
    
    public boolean insert(int val) {
        boolean res = false; 
        if (!map.containsKey(val) || map.get(val).size() == 0) {
            res = true;
            map.put(val, new HashSet<>());
        }
        arr.add(val);
        map.get(val).add(arr.size()-1);
        return res; 
    }
    
    public boolean remove(int val) {
        if (!map.containsKey(val) || map.get(val).size() == 0) {
            return false; 
        }
        // 1. 拿到val其中一个index, 从map中移出
        Set<Integer> indexs = map.get(val); 
        int toRemove = indexs.iterator().next();
        indexs.remove(toRemove);
        
        // 2. 与 value list的最后一位交换， 并remove最后一位 
        int lastVal = arr.get(arr.size()-1);
        int lastIndex = arr.size() - 1; 
        arr.set(toRemove, lastVal); 
        arr.remove(arr.size() - 1); 
        
        // 3. 现在原本list中最后一位的index变了，得update它在map中的值
        Set<Integer> lastValIndexes = map.get(lastVal);
        lastValIndexes.add(toRemove);
        lastValIndexes.remove(lastIndex); // 没有的话 hashSet.remove() return false 
        return true; 
    }
    
    public int getRandom() {
        int index = rand.nextInt(arr.size());
        return arr.get(index);
    }
}

/**
 * Your RandomizedCollection object will be instantiated and called as such:
 * RandomizedCollection obj = new RandomizedCollection();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
