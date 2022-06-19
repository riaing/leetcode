Design a data structure that supports all following operations in average O(1) time.

insert(val): Inserts an item val to the set if not already present.
remove(val): Removes an item val from the set if present.
getRandom: Returns a random element from current set of elements. Each element must have the same probability of being returned.
Example:

// Init an empty set.
RandomizedSet randomSet = new RandomizedSet();

// Inserts 1 to the set. Returns true as 1 was inserted successfully.
randomSet.insert(1);

// Returns false as 2 does not exist in the set.
randomSet.remove(2);

// Inserts 2 to the set, returns true. Set now contains [1,2].
randomSet.insert(2);

// getRandom should return either 1 or 2 randomly.
randomSet.getRandom();

// Removes 1 from the set, returns true. Set now contains [2].
randomSet.remove(1);

// 2 was already in the set, so return false.
randomSet.insert(2);

// Since 2 is the only number in the set, getRandom always return 2.
randomSet.getRandom();

----------------------------------------------------------------------------------------------
/**
Map & list
remove时，swap要remove的value和list的最后一个，再removelist的最后一个，就能保证update list的操作为o(1)


Map：value - index  
List : value in order

insert {
 if map contains key -> false 
 插到列表最后
}

delete {
    if map NOT key -> false 
    1、 swap 当前和list 最后元素
    2、 remove list最后
    3、 更新map index
}


*/
class RandomizedSet {
    List<Integer> list; 
    Map<Integer, Integer> map; // mapping from value to its index in the list 
    /** Initialize your data structure here. */
    public RandomizedSet() {
        this.list = new ArrayList<Integer>();
        this.map = new HashMap<Integer, Integer>();
    }
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (map.containsKey(val)) {
            return false;
        }
        list.add(val);
        map.put(val, list.size()-1);
        return true;
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (!map.containsKey(val)) {
            return false;
        }
        int valIndex = map.get(val);
        map.remove(val);
       
        // corner case: 只有一个元素或者要remove的是最后元素时
        if (valIndex == list.size()-1) {
            list.remove(list.size()-1); 
        }
        else {
        // update the list by swap cur value and the last value; 
        // we make sure list's size decreases by 1 for every remove() and it's always a full list
        int tmp = list.get(list.size()-1);
        list.set(valIndex, tmp);
        list.remove(list.size()-1);
        // then update map 
       
        map.put(tmp, valIndex); 
        }
        
        return true;
    }
    
    /** Get a random element from the set. */
    public int getRandom() {
        if (map.isEmpty()) {
            
        }
        Random rand = new Random();
       
        int index = rand.nextInt(list.size());
        return list.get(index);
        
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
