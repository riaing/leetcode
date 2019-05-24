----------如果每秒的hit很多：高阶法-用rotational array ----------------------------------------

https://www.cnblogs.com/grandyang/p/5605552.html 

/* 问：what's the qps of this event?
两个list,每次hit时不仅加入count，而且要从list开头查起，把多于五分钟的都丢掉。
所以这个维持大小的list就得O（n）
所以提出rotational array的解法
*/

class HitCounter {
    int[] times;
    int[] counts;
    /** Initialize your data structure here. */
    public HitCounter() {
        times = new int[300];
        counts = new int[300];
    }
    
    /** Record a hit.
        @param timestamp - The current timestamp (in seconds granularity). */
    
    public void hit(int timestamp) {
        int index = timestamp % 300;
        if (times[index] == timestamp) {
            counts[index]++;
        }
        // the previous event is over 5 min, reset to 1
        else {
            times[index] = timestamp;
            counts[index] = 1;
        }
    }
    
    /** Return the number of hits in the past 5 minutes.
        @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        // check the valid time in times, add corresponding count into sum 
        int hits = 0;
        for (int i = 0; i < times.length; i++) {
            if (timestamp - 300 < times[i]) {
                hits += counts[i];
            }
        }
        return hits;
        
    }
}

/**
 * Your HitCounter object will be instantiated and called as such:
 * HitCounter obj = new HitCounter();
 * obj.hit(timestamp);
 * int param_2 = obj.getHits(timestamp);
 */
 
 -----------------比较low的最开始的解法，用heap，维持五分钟大小的heap----------------------
 class HitCounter {
   PriorityQueue<Integer> q;
    /** Initialize your data structure here. */
    public HitCounter() {
        q = new PriorityQueue<Integer>();
    }
    
    /** Record a hit.
        @param timestamp - The current timestamp (in seconds granularity). */
    
    public void hit(int timestamp) {
        q.offer(timestamp);
    }
    
    /** Return the number of hits in the past 5 minutes.
        @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        // check the valid time in times, add corresponding count into sum 
        int hits = 0;
       while (!q.isEmpty() && timestamp - 300 >= q.peek()) {
           q.poll();
       }
        return q.size();
        
        
    }
}
