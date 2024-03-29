Create a timebased key-value store class TimeMap, that supports two operations.

1. set(string key, string value, int timestamp)

Stores the key and value, along with the given timestamp.
2. get(string key, int timestamp)

Returns a value such that set(key, value, timestamp_prev) was called previously, with timestamp_prev <= timestamp.
If there are multiple such values, it returns the one with the largest timestamp_prev.
If there are no values, it returns the empty string ("").
 

Example 1:

Input: inputs = ["TimeMap","set","get","get","set","get","get"], inputs = [[],["foo","bar",1],["foo",1],["foo",3],["foo","bar2",4],["foo",4],["foo",5]]
Output: [null,null,"bar","bar",null,"bar2","bar2"]
Explanation:   
TimeMap kv;   
kv.set("foo", "bar", 1); // store the key "foo" and value "bar" along with timestamp = 1   
kv.get("foo", 1);  // output "bar"   
kv.get("foo", 3); // output "bar" since there is no value corresponding to foo at timestamp 3 and timestamp 2, then the only value is at timestamp 1 ie "bar"   
kv.set("foo", "bar2", 4);   
kv.get("foo", 4); // output "bar2"   
kv.get("foo", 5); //output "bar2"   

Example 2:

Input: inputs = ["TimeMap","set","set","get","get","get","get","get"], inputs = [[],["love","high",10],["love","low",20],["love",5],["love",10],["love",15],["love",20],["love",25]]
Output: [null,null,null,"","high","high","low","low"]

----------------------------binary search + hashmap 九章模板 ---------------------------------------------------------------
//binary search，找到左边第一个小于target的数。 
// Time Complexity: O(1) for each set operation, and O(log N) for each get operation, where N is the number of entries in the TimeMap.
//Space Complexity: O(N). 
class TimeMap {
    class Node {
        String value;
        int timestamp;
        public Node(String value, int timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }
    }
    Map<String, List<Node>> map;
    
    /** Initialize your data structure here. */
    public TimeMap() {
        this.map = new HashMap<String, List<Node>>();    
    }
    
    public void set(String key, String value, int timestamp) {
        // because the timestamps for all TimeMap.set operations are strictly increasing, we are sure that the map's value list is sorted by timestamp
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<Node>());
        }
        map.get(key).add(new Node(value, timestamp));
    }
    
    public String get(String key, int timestamp) {
        if (!map.containsKey(key)) {
            return "";
        }
        // search in the value list to find the target timestamp or the first one left to the target timestamp, if cannot find, return ""
        return search(timestamp, map.get(key));
    }
    
    private String search(int target, List<Node> input) {
        int start = 0;
        int end = input.size()-1;
        while (start + 1 < end) {
            int mid = start + (end - start)/2;
            int midTimestamp = input.get(mid).timestamp;
            if (midTimestamp == target) {
                return input.get(mid).value;
            }
            else if (midTimestamp < target) {
                start = mid;
            }
            else {
                end = mid;
            }
        }

        // order matters! 
        if (input.get(end).timestamp <= target) {
            return input.get(end).value;
        }
        // now target must < end
        else if (input.get(start).timestamp <= target) {
            return input.get(start).value;
        }
        // now target must < start
        else {
            return "";
        }
    }
}

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */


---------------bineray自己的模板
//binary search，找到左边第一个小于target的数。 
// Time Complexity: O(1) for each set operation, and O(log N) for each get operation, where N is the number of entries in the TimeMap.
//Space Complexity: O(N). 
class TimeMap {
    class Node {
        String value;
        int timestamp;
        public Node(String value, int timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }
    }
    Map<String, List<Node>> map;
    
    /** Initialize your data structure here. */
    public TimeMap() {
        this.map = new HashMap<String, List<Node>>();    
    }
    
    public void set(String key, String value, int timestamp) {
        // because the timestamps for all TimeMap.set operations are strictly increasing, we are sure that the map's value list is sorted by timestamp
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<Node>());
        }
        map.get(key).add(new Node(value, timestamp));
    }
    
    public String get(String key, int timestamp) {
        if (!map.containsKey(key)) {
            return "";
        }
        // search in the value list to find the target timestamp or the first one left to the target timestamp, if cannot find, return ""
        return search(timestamp, map.get(key));
    }
    
    private String search(int target, List<Node> input) {
        int start = 0;
        int end = input.size()-1;
        while (start < end) {
            int mid = start + (end - start)/2+1;
            int midTimestamp = input.get(mid).timestamp;
            //start=mid时，考虑两个数的loop，解决方法是mid+1.
           if (midTimestamp <= target) {
                start = mid;
            }
            else {
                end = mid - 1;
            }
        }
        if (input.get(start).timestamp <= target) {
            return input.get(start).value;
        }
        else {
            return "";
        }
    }
}

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */
 
------------- 2022.6 using treeMap.floorKey(key) ------------------------------------
 https://leetcode.com/problems/time-based-key-value-store/solution/ 

/*
TreeMap 解法。 set比get慢
bs解法：get比set慢 
*/
class TimeMap {
    Map<String, TreeMap<Integer, String>> map;
    public TimeMap() {
        this.map = new HashMap<>();
        
    }
    
    public void set(String key, String value, int timestamp) { // lgn, n size of the map 
        if (!map.containsKey(key)) {
            map.put(key, new TreeMap<Integer, String>());
        }
        TreeMap<Integer, String> treeMap = map.get(key);
        treeMap.put(timestamp, value);
    }
    
    public String get(String key, int timestamp) { // O(1)
        if (!map.containsKey(key)) {
            return "";
        }

        // System.out.println(map.get(key).floorEntry(1).getValue());
        Map.Entry<Integer, String> entry = map.get(key).floorEntry(timestamp);
        return entry == null ? "" : entry.getValue(); 
    }
}

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */
