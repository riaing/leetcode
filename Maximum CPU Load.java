https://www.educative.io/courses/grokking-the-coding-interview/xVlyyv3rR93 
https://www.geeksforgeeks.org/maximum-cpu-load-from-the-given-list-of-jobs/ 

We are given a list of Jobs. Each job has a Start time, an End time, and a CPU load when it is running. Our goal is to find the maximum CPU load at any time if all the jobs are running on the same machine.

Example 1:

Jobs: [[1,4,3], [2,5,4], [7,9,6]]
Output: 7
Explanation: Since [1,4,3] and [2,5,4] overlap, their maximum CPU load (3+4=7) will be when both the 
jobs are running at the same time i.e., during the time interval (2,4).
Example 2:

Jobs: [[6,7,10], [2,4,11], [8,12,15]]
Output: 15
Explanation: None of the jobs overlap, therefore we will take the maximum load of any job which is 15.
Example 3:

Jobs: [[1,4,2], [2,4,1], [3,6,5]]
Output: 8
Explanation: Maximum CPU load will be 8 as all jobs overlap during the time interval [3,4]. 

---- priority queue + merge interval, 自己的写法， 比下稍微复杂 -------------------------------
Time 
The time complexity of the above algorithm is O(N*logN)
, where ‘N’ is the total number of jobs. This is due to the sorting that we did in the beginning. Also, while iterating the jobs, we might need to p
oll/offer jobs to the priority queue. Each of these operations can take O(logN)
. Overall our algorithm will take O(NlogN)

Space
O(N) for sorting and worst case store all jobs in queue 

import java.util.*;

class Job implements Comparable<Job>{
  int start;
  int end;
  int cpuLoad;

  public Job(int start, int end, int cpuLoad) {
    this.start = start;
    this.end = end;
    this.cpuLoad = cpuLoad;
  }

  @Override
  public int compareTo(Job b) {
    return this.start - b.start;
  }
};

class MaximumCPULoad {

  public static int findMaxCPULoad(List<Job> jobs) {
    // 1. sort array by start time 
    Collections.sort(jobs);
    // System.out.println(jobs.get(0).start);
    PriorityQueue<Job> q = new PriorityQueue<Job>((a, b) -> a.end - b.end); // q 存所有正在进行的job，sort by end time
    q.offer(jobs.get(0));
    int max =  jobs.get(0).cpuLoad;

    for (int i = 1; i < jobs.size(); i++) {
        // check if have overlap by comparing the heap top.end > curJob.start 
        Job curJob = jobs.get(i);
        if (q.size() != 0) {
            Job firstEndJob  = q.poll(); 
            if (firstEndJob.end > curJob.start) { // find an overlap 
                max = Math.max(max, firstEndJob.cpuLoad + curJob.cpuLoad); 
                if (firstEndJob.end <= curJob.end) {
                  firstEndJob.cpuLoad = firstEndJob.cpuLoad + curJob.cpuLoad;
                }
                q.offer(firstEndJob);
            }
            max = Math.max(max, curJob.cpuLoad);
            // put the current meeting into q for 1) if non overlap, curMeeting must end after firstEndMeeting, so put it into q. 2) if overlap, then 2 meetins must be in q, so also needs to put it into queue 
            q.offer(curJob);
        }
    }
    return max;
  }

  public static void main(String[] args) {
    List<Job> input = new ArrayList<Job>(Arrays.asList(new Job(1, 4, 3), new Job(2, 5, 4), new Job(7, 9, 6)));
    System.out.println("Maximum CPU load at any time: " + MaximumCPULoad.findMaxCPULoad(input));

    input = new ArrayList<Job>(Arrays.asList(new Job(6, 7, 10), new Job(2, 4, 11), new Job(8, 12, 15)));
    System.out.println("Maximum CPU load at any time: " + MaximumCPULoad.findMaxCPULoad(input));

    input = new ArrayList<Job>(Arrays.asList(new Job(1, 4, 2), new Job(2, 4, 1), new Job(3, 6, 5)));
    System.out.println("Maximum CPU load at any time: " + MaximumCPULoad.findMaxCPULoad(input));
  }
}

----------- 更清晰的做法，用一个var来代表当前的CPU -------------------------------------------------
import java.util.*;

class Job {
  int start;
  int end;
  int cpuLoad;

  public Job(int start, int end, int cpuLoad) {
    this.start = start;
    this.end = end;
    this.cpuLoad = cpuLoad;
  }
};

class MaximumCPULoad {

  public static int findMaxCPULoad(List<Job> jobs) {
    // sort the jobs by start time
    Collections.sort(jobs, (a, b) -> Integer.compare(a.start, b.start));

    int maxCPULoad = 0;
    int currentCPULoad = 0;
    PriorityQueue<Job> minHeap = new PriorityQueue<>(jobs.size(), (a, b) -> Integer.compare(a.end, b.end));
    for (Job job : jobs) {
      // remove all jobs that have ended
      while (!minHeap.isEmpty() && job.start > minHeap.peek().end)
        currentCPULoad -= minHeap.poll().cpuLoad;

      // add the current job into the minHeap
      minHeap.offer(job);
      currentCPULoad += job.cpuLoad;
      maxCPULoad = Math.max(maxCPULoad, currentCPULoad);
    }
    return maxCPULoad;
  }

  public static void main(String[] args) {
    List<Job> input = new ArrayList<Job>(Arrays.asList(new Job(1, 4, 3), new Job(2, 5, 4), new Job(7, 9, 6)));
    System.out.println("Maximum CPU load at any time: " + MaximumCPULoad.findMaxCPULoad(input));

    input = new ArrayList<Job>(Arrays.asList(new Job(6, 7, 10), new Job(2, 4, 11), new Job(8, 12, 15)));
    System.out.println("Maximum CPU load at any time: " + MaximumCPULoad.findMaxCPULoad(input));

    input = new ArrayList<Job>(Arrays.asList(new Job(1, 4, 2), new Job(2, 4, 1), new Job(3, 6, 5)));
    System.out.println("Maximum CPU load at any time: " + MaximumCPULoad.findMaxCPULoad(input));
  }
}
 
