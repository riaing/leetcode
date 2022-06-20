/*
ref: 
https://www.1point3acres.com/bbs/thread-864126-1-1.html 
https://www.1point3acres.com/bbs/thread-484962-1-1.html
答案： treeMap： 
https://www.cnblogs.com/apanda009/p/7945036.html 
https://github.com/shileiwill/destination/blob/master/Round1/src/company/linkedin/RetainBestCache.java 

题目：
写一个RetainBestCache的数据结构，给定了getRank函可以直接用来得到当前cache的rank，然后有capacity的限制，当达到capacity的时候要拿出当前所存lowest rank的cache，
跟新的cache比较，保留rank较高的那个


思路：
-HashMap keep k - v relation
-TreeMap or PriorityQueue  to keep the rank list
1. PQ remove(Object)是O（n）-> 但PQ只需要remove lowest rank，而不是特定element，所以这题用PQ可以
   TreeMap remove是 O（lgN）

follow up：
- What if we want to implment this in multi-threaded environment?
A: locking (persmistic v.s optimistic) 
-  How to consider thread safe？
A： 这个重点讨论不同的锁
- What if the rank will drift with time?
A： 1） PQ 删元素得O（n），这时候用TreeMap解法更好 
   2） DB和cache的sync方式：https://www.notion.so/prep-749ee4b3f0784b6984d6a16a71ae3a1e#dfd49fcb8df446908d375a5e8673f96b 
- LRU该如何实现? 

知识： 
1，thread 和process的区别
- Process means a program is in execution, thread means a segment of a process. 
- A Process is mostly isolated, whereas Threads share memory.
- Process does not share data, and Threads share data with each other.
- 一个process可以有几个thread，那就是multi threading。
比喻：
program = receipe = word文档找个app
->可以有多个process = 打开多个word文档
-> 一个process可以有几个thread = 一个在输入input，一个在做spell check， etc

2，什么是transaction
- A transaction is a way of representing a state change. Transactions ideally have four properties, commonly known as ACID 

3，mutex 和semaphore的区别。 
https://www.youtube.com/watch?v=8wcuLCvMmF8 

like traffic signals, 2 train want to go through same railroud.  - process needs to wait until the resources are free. use Acquire(), release() 
mutex:  lock base approach
- process should acquire lock on the Mutex object
semaphore:  signal machanism  
    - wait(), signal(), indicating the resource is acquring/releasing
*/


class Node<K,T extends Rankable> { // 重点1
    K key;
    T data;
    public Node(K key, T data) {
        this.key = key;
        this.data = data;
    }
}

public class RetainBestCache<K, T extends Rankable> { // RetainBestCache
    int entriesToRetain;
    HashMap<K, T> map = new HashMap<K,T>();
    DataSource<K,T> ds;
    PriorityQueue<Node<K, T>> pq;

    /* Constructor with a data source (assumed to be slow) and a cache size */
    public RetainBestCache(DataSource<K,T> ds, int entriesToRetain) { // 重点2
        //implement here
        Comparator<Node> com = new Comparator<Node>() {
            @Override
            public int compare(Node a, Node b) {
                return Long.compare(a.data.getRank(), b.data.getRank());
            }
        };
        this.ds = ds;
        this.entriesToRetain = entriesToRetain;
        this.pq = new PriorityQueue<>(com);
    }

    /* Gets some data. If possible, retrieves it from cache to be fast. If the data is not cached,
     * retrieves it from the data source. If the cache is full, attempt to cache the returned data,
     * evicting the T with lowest rank among the ones that it has available
     * If there is a tie, the cache may choose any T with lowest rank to evict.
     */
    public T get(K key) {
        // System.out.println("in get, key: " + key);
        //implement here
        if (map.containsKey(key)) {
            return map.get(key);
        }
        // 1. get data from data source
        T curData = ds.get(key);
        // 2. shrink cache is reach limit
        if (pq.size() >= entriesToRetain) {
            evit();
        }
        //3. put into cache and pq
        pq.offer(new Node(key, curData));
        map.put(key, curData);
        // System.out.println("now finish.return: " + curData.getRank());
        // map.forEach((k,v) -> System.out.println(k + ": " +v.getRank()));
        return curData;
    }

    private void evit() {
        Node minRankNode = pq.poll();
        map.remove(minRankNode.key);
    }

    public static void main(String[] args) {
        DataSource ds = new DataSourceImpl();
        RetainBestCache cache = new RetainBestCache(ds, 3);
        Rankable x1 = cache.get(1L);
        Rankable x2 = cache.get(3L);
        Rankable x3 = cache.get(4L);
        Rankable x4 = cache.get(2L); // should remove 1L
        System.out.println("res: " + x4.getRank());

    }
}
    
/*
* For reference, here are the Rankable and DataSource interfaces.
* You do not need to implement them, and should not make assumptions
* about their implementations.
*/
interface Rankable {
    /**
    * Returns the Rank of this object, using some algorithm and potentially
    * the internal state of the Rankable.
    */
    long getRank();
}
class RankableImpl implements Rankable{
    private Object key;

    public RankableImpl(Object key) {
        this.key = key;
    }
    @Override
    public long getRank() {
        return (long) this.key;
    }
}
interface DataSource<K, T extends Rankable> {
    T get(K key);
}
class DataSourceImpl implements DataSource{

    @Override
    public Rankable get(Object key) {
        return new RankableImpl(key);
    }
}
