思路：List<Set<String>> 类似于一个个bucket，index代表count，value就是有多少个key是这个count
max就是返回list中末尾的set中的一个，min的话，因为有可能第一个bucket为空，所以一直用一个var min来记录最小的bucket。
 Map<String, Integer> map是连接list的关键，value就是list的index。
 
class AllOne {
    Map<String, Integer> map; // key to count
    List<Set<String>> count; // count to corresponding set of keys 
    int min;
    /** Initialize your data structure here. */
    public AllOne() {
        map = new HashMap<String, Integer>();
        count = new ArrayList<Set<String>>();
        count.add(new HashSet<String>());
        min = 0;
    }
    
    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {
        if (!map.containsKey(key)) {
            map.put(key, 1);
            if (count.size() == 1) {
                count.add(new HashSet<String>());
            }
            count.get(1).add(key);
            min = 1;
        }
        else {
            // 更新count，从原来的count set中拿出来，放到下一个count set里
            int value = map.get(key);
            if (count.size() == value+1) {
                count.add(new HashSet<String>());
            }
            count.get(value).remove(key);
            count.get(value+1).add(key);
            map.put(key, value + 1);   
            
            if (value == min && count.get(value).isEmpty()) {
                min++;
             }
        }
       
    }
    
    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public  void dec(String key) {
        if (!map.containsKey(key)) {
            return;
        }
        int value = map.get(key);
        count.get(value).remove(key);
        if (value == 1) {
            map.remove(key);
            int cur = value;
            while (cur == min && cur < count.size() && count.get(cur).isEmpty()) {
                cur++;
                min = cur;
            } 
        }
        else {
            map.put(key, value-1);
            // also decrease from count list 
            count.get(value-1).add(key);
            
            if (value == min) {
                min--;
            }
        }
        
        // remove last index if it's empty
        if (value+1 == count.size() && count.get(value).isEmpty()) {
            count.remove(value);
        }
    
       
        //注意这里不能因为list对应的set是空就remove，因为list的index其实就是count，所以尽管当前index为空，后面可能还有值
        
    }
    
    /** Returns one of the keys with maximal value. */
    public String getMaxKey() {
        Set<String> maxValues = count.get(count.size()-1);
        if(maxValues.isEmpty()) {
            return "";
        }
        else {
            String res = "";
            for (String maxValue : maxValues) {
                res = maxValue;
                break;
            }
            return res;
        }
    }
    
    /** Returns one of the keys with Minimal value. */
    public String getMinKey() {
       
        if (min <= 0 || min >= count.size() || count.get(min).isEmpty()) {
            return "";
        }
         String res = "";
            for (String minValue :  count.get(min)) {
                res = minValue;
                break;
            }
        
        return res;
    }
}

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */
 
 -----------------------------------优化 用double Linked LIST ---------
 类似于LRU， 我们也可以创造出一个类{value，set(strings)}作为node，从而取消以上做法中map value与list index的关联：我们将map mapping到key ->node。
 这样做的好处是，list的index不再敏感，当set为empty, could directly delete that node 
 
 https://blog.csdn.net/qq_46105170/article/details/118673273 
 
 https://leetcode.com/problems/all-oone-data-structure/discuss/287496/My-All-O(1)-Java-Solution-(HashMap-%2B-Double-Linked-List) 


------------ 2022.4 treeMap ---------------------------------
/*
TreeMap易错点。
1、 因为treeMap如果sorted field有dup的话，会override
TreeMap<Node> sorted by Node.int;  if PUT Node(1, a), Node(1,B), Node(1,a) 会被override，尽管是不同的obj。-> 这时候应该按另一个field来sort以防止override

2，更改了Node的值后，应该拿出node再加回treeMap来trigger 红黑树rebalance
*/
class Node {
    String key;
    int count;
    public Node(String key, int count) {
        this.key = key;
        this.count = count;
    }
}

class AllOne {
    TreeMap<Node, String> treeMap;
    HashMap<String, Node> map;
    public AllOne() {
        Comparator<Node> comparator = new Comparator<Node>(){
            @Override 
            public int compare(Node a, Node b) {
                if (a.count == b.count) {
                    return a.key.compareTo(b.key);
                }
                return a.count - b.count;
            }
        };
        
        this.treeMap = new TreeMap<Node, String>(comparator);
        this.map = new HashMap<String, Node>();
    }
    
    public void inc(String key) {
        // 1. find the node from map
        if (map.containsKey(key)) {
            Node cur = map.get(key);
            // update treeMap.这里要拿出node再丢回去，来trigger红黑树的更新
            treeMap.remove(cur);
            cur.count++; 
            treeMap.put(cur, key);
        }
        else {
            Node cur = new Node(key, 1);
            map.put(key, cur);
            treeMap.put(cur, key);

        }
        // System.out.println("key " + key);
        // treeMap.keySet().forEach(o -> System.out.println(o.key + " " + o.count));
        // System.out.println("------- maxKey " + treeMap.lastKey().key + treeMap.lastKey().count);
        
    }
    
    public void dec(String key) {
        
        Node cur = map.get(key);
        treeMap.remove(cur); // 拿出来再重新加回去以trigger 红黑树
        cur.count--;
        treeMap.put(cur, key);
        if (cur.count == 0) {
            map.remove(key);
            treeMap.remove(cur);
        }
        // treeMap.keySet().forEach(o -> System.out.println(o.key + " " + o.count));
    }
    
    public String getMaxKey() {
        if (map.isEmpty()) {
            return "";
        }
        return treeMap.lastKey().key;
    }
    
    public String getMinKey() {
        if (map.isEmpty()) {
            return "";
        }
        return treeMap.firstKey().key;
    }
}

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */
