Design a hit counter which counts the number of hits received in the past 5 minutes.

Each function accepts a timestamp parameter (in seconds granularity) and you may assume that calls are being made to the system in chronological order (ie, the timestamp is monotonically increasing). You may assume that the earliest timestamp starts at 1.

It is possible that several hits arrive roughly at the same time.

Example:

HitCounter counter = new HitCounter();

// hit at timestamp 1.
counter.hit(1);

// hit at timestamp 2.
counter.hit(2);

// hit at timestamp 3.
counter.hit(3);

// get hits at timestamp 4, should return 3.
counter.getHits(4);

// hit at timestamp 300.
counter.hit(300);

// get hits at timestamp 300, should return 4.
counter.getHits(300);

// get hits at timestamp 301, should return 3.
counter.getHits(301); 
Follow up:
What if the number of hits per second could be very large? Does your design scale?


这个题不要用heap了，本身就是有序的
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
 
 -----------------比较low的最开始的解法，还是两个list，每次hit时，把五分钟之前的event删去。这样的话可能要删太多遍----------------------
/* 问：what's the qps of this event?
两个list,每次hit时不仅加入count，而且要从list开头查起，把多于五分钟的都丢掉。
所以这个维持大小的list就得O（n）
所以提出rotational array的解法
*/

class HitCounter {
    LinkedList<Integer> times;
    LinkedList<Integer> counts;
    /** Initialize your data structure here. */
    public HitCounter() {
        times = new LinkedList<Integer>();
        counts = new LinkedList<Integer>();
    }
    
    /** Record a hit.
        @param timestamp - The current timestamp (in seconds granularity). */
    
    public void hit(int timestamp) {
       if (!times.isEmpty() && times.getLast() == timestamp) {
           counts.set(counts.size()-1 , counts.getLast() + 1);
       }
        else {
            counts.add(1);
            times.add(timestamp);
        }
        // remove invalid time 
        while(!times.isEmpty() && timestamp - 300 >= times.get(0)) { // 删掉5分钟前的event
            times.remove(0);
            counts.remove(0);
        }
    }
    
    /** Return the number of hits in the past 5 minutes.
        @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        // check the valid time in times, add corresponding count into sum 
        int hits = 0;
        for (int i = 0; i < times.size(); i++) {
            if (timestamp - 300 < times.get(i)) {
                hits += counts.get(i);
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
