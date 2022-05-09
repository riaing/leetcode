
----------------- 完全没collision，浪费空间的intuitive写法 ------------------------------------------
/*
用array - key是index。 计算：integer是4 bytes，题目给了key最大是10^6， 所以array length要是10^6+1, 每个slot大小为4 bytes。所以这个array总大小为 (10^6+1) * 4 = 4000 * 10^3 bytes = 4000 KB = 4MB -> 要那么大的memory
*/
class MyHashMap {
    int[] arr; 
    public MyHashMap() {
        this.arr =  new int[(int) Math.pow(10,6)+1]; 
        Arrays.fill(arr, -1);
    }
    
    public void put(int key, int value) {
        arr[key] = value;
    }
    
    public int get(int key) {
        return arr[key];
    }
    
    public void remove(int key) {
        arr[key] = -1;
    }
}

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */
 
 -------------------- hashtable的实现 -------------------------------
 /*
实现个hashtable： 
1. 一个arraylist，值是linkedlist， linkedlist存(key, value) pair 
2. 定义 module。 key % module = hashKey

arraylist就是hashtable，linkedlist就是同一个hashkey的bucket

时间：就是要iterate through the whole linkedlist，for all operations. 
怎么知道linkedlist size？ ideal情况key是evenly distributed，那么每个linklist存 N / modulo 个值. N - number of possible keys （题中是10^6), module - 自己定义的为2069 
所以时间是O(N/module) 

*/

class Pair {
    int key;
    int value;
    public Pair(int key, int value) {
        this.key = key;
        this.value = value; 
    }
}

class MyHashMap {
    List<LinkedList<Pair>> hashTable; 
    int modulo;  // in order to minimize the potential collisions, use a prime number as the base of modulo, e.g. 2069.
    
    public MyHashMap() {
        this.hashTable =  new ArrayList<LinkedList<Pair>>();
        this.modulo = 2069; 
        for (int i = 0; i < this.modulo; i++) {
            this.hashTable.add(new LinkedList<Pair>());
        }
    }
    
    public void put(int key, int value) {
        // 1. get hashKey 
        int hashKey = key % this.modulo;
        LinkedList<Pair> bucket = this.hashTable.get(hashKey);
        // 2.a if key exist, update
        boolean found = false; 
        for (Pair pair :bucket) {
            if (pair.key == key) {
                pair.value = value;
                found = true;
                break;
            }
        }
        // 2.b key not exist, insert
        if (!found) {
            bucket.add(new Pair(key, value));
        }
    }
    
    public int get(int key) {
        // 1. get hashKey 
        int hashKey = key % this.modulo;
        LinkedList<Pair> bucket = this.hashTable.get(hashKey);
        
         for (Pair pair :bucket) {
            if (pair.key == key) {
                return pair.value;
            }
         }
        return -1; 
    }
    
    public void remove(int key) {
         // 1. get hashKey 
        int hashKey = key % this.modulo;
        LinkedList<Pair> bucket = this.hashTable.get(hashKey);
        
        
         for (Pair pair :bucket) {
            if (pair.key == key) {
                bucket.remove(pair);
                break;
            }
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
