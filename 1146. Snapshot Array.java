Implement a SnapshotArray that supports the following interface:

SnapshotArray(int length) initializes an array-like data structure with the given length.  Initially, each element equals 0.
void set(index, val) sets the element at the given index to be equal to val.
int snap() takes a snapshot of the array and returns the snap_id: the total number of times we called snap() minus 1.
int get(index, snap_id) returns the value at the given index, at the time we took the snapshot with the given snap_id
 

Example 1:

Input: ["SnapshotArray","set","snap","set","get"]
[[3],[0,5],[],[0,6],[0,0]]
Output: [null,null,0,null,5]
Explanation: 
SnapshotArray snapshotArr = new SnapshotArray(3); // set the length to be 3
snapshotArr.set(0,5);  // Set array[0] = 5
snapshotArr.snap();  // Take a snapshot, return snap_id = 0
snapshotArr.set(0,6);
snapshotArr.get(0,0);  // Get the value of array[0] with snap_id = 0, return 5
 

Constraints:

1 <= length <= 50000
At most 50000 calls will be made to set, snap, and get.
0 <= index < length
0 <= snap_id < (the total number of times we call snap())
0 <= val <= 10^9 

-------------------------- Design ------------------------------
/*
Tricky：get的实现。
思路1： Map存每个snapshot 对应的值。 每个index一个map
        这种set o(1), get o(n). 适用于set 多于 get
思路2： 用TreeMap存每个snapshot对应的值，get时可以找到左区间最考进snapshot id的值。
        这时 set o(lgn), get o (1), 应用于 get > set 
        https://leetcode.com/problems/snapshot-array/discuss/2108502/TreeMap%2BMap-JAVA 
*/
class SnapshotArray {
    int id;
    List<Map<Integer, Integer>> list; // map: snapShotId -> val
    public SnapshotArray(int length) {
        this.id = 0;
        //this.arr = new Map<Integer, Integer>[length]; // You can't have an array of a generic type. Use List instead.
        this.list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(new HashMap<>());
        }
    }
    
    public void set(int index, int val) {
        Map<Integer, Integer> curMap = list.get(index);
        curMap.put(id, val);
         // ist.forEach(o -> System.out.println(o));
    }
    
    public int snap() {
        // int curId = id;
        // id++;
        // return curId;   看一下更漂亮的写法： return id - 1 不会改变 id的值。
        id++;
        return id -1; 
    }
    
      // tricky：要找到有值的那一层。有可能call了好几遍空的snap(). 这里最差是O（n）
    public int get(int index, int snap_id) {
        Map<Integer, Integer> curMap = list.get(index);
       
        while (snap_id >= 0) {
            if (curMap.containsKey(snap_id)) {
                return curMap.get(snap_id);
            }
            snap_id--;
        }
       
        return 0;// 说明此值从没有被set过，default 0
    }
}

/**
 * Your SnapshotArray object will be instantiated and called as such:
 * SnapshotArray obj = new SnapshotArray(length);
 * obj.set(index,val);
 * int param_2 = obj.snap();
 * int param_3 = obj.get(index,snap_id);
 */
