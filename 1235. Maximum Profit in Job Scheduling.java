
/*
需要sort by end time. sort takes time o(NLgN) space o(3*N)
方法1： 
DP[i] 如果做i个任务的最大profit
     dp[i] = profit[i]
           dp[j] + profit[i], j.end time < i.start time -> 所以要sort by end time来拿到所有在i之前结束的job -> n 
O(n^2)           

方法2，需要sort by end time
DP[i] 截止到i找个的max profit
    不取： dp[i-1]
    取： dp[j] + profit[i] . j的end time < i的start time。j是end time最靠近i start time的interval，所以要sort by end time. -> 可以用binary search找这个j -> lgn 

O(nlgn)    
*/

----------- 方法1. dp[i] 为以i结尾，超时 -------------------------------
public class Job {
    int start;
    int end;
    int profit;
    
    Job(int start, int end, int profit) {
        this.start = start;
        this.end = end;
        this.profit = profit;
    }
}


class Solution {
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        //Sort by end time.
        List<Job> jobs = new ArrayList<Job>();
        for (int i = 0; i < startTime.length; i++) {
            jobs.add(new Job(startTime[i], endTime[i], profit[i]));
        }
        Collections.sort(jobs, (a, b) -> Integer.compare(a.end, b.end));
        
        int[] dp = new int[jobs.size()];
        dp[0] = jobs.get(0).profit; 
        int max = 0;
        for (int i = 1; i < jobs.size(); i++) {
            dp[i] = jobs.get(i).profit;
            for (int j = i-1; j >= 0; j--) {
                if (jobs.get(i).start >= jobs.get(j).end) {
                    dp[i] = Math.max(dp[i], jobs.get(i).profit + dp[j]);
                }
            }
            max = Math.max(max, dp[i]);
            // System.out.println("i " + i + " res " + dp[i]);
            
        }
        return max; 
    }
}

----------- 方法二，dp[i] 为前i， 没用binary search ------------------------------------
public class Job {
    int start;
    int end;
    int profit;
    
    Job(int start, int end, int profit) {
        this.start = start;
        this.end = end;
        this.profit = profit;
    }
}

class Solution {
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        //Sort by end time.
        List<Job> jobs = new ArrayList<Job>();
        for (int i = 0; i < startTime.length; i++) {
            jobs.add(new Job(startTime[i], endTime[i], profit[i]));
        }
        Collections.sort(jobs, (a, b) -> Integer.compare(a.end, b.end));
        
        
        int[] dp = new int[jobs.size()];
        dp[0] = jobs.get(0).profit; 
        for (int i = 1; i < jobs.size(); i++) {
            // 情况1： 取自己+ previous dp[j] 
            dp[i] = jobs.get(i).profit; // 取自己
            // 如果取的话，从i-1到0中找end time最接近的那个dp[j] -> 可以优化成binary search
            for (int j = i-1; j >= 0; j--) {
                if (jobs.get(i).start >= jobs.get(j).end) {
                    dp[i] = Math.max(dp[i], jobs.get(i).profit + dp[j]);
                    break; // 因为sort by end time, 所以找到第一个就是最靠近的那个
                }
            }
            // 情况2， 不取自己，则和如果不取对比
            dp[i] = Math.max(dp[i], dp[i-1]);            
        }
        return dp[jobs.size()-1]; 
    }
}

--------------------- 方法二，dp[i] 为前i， + binary search -----------------------------------------------------------------

/*
需要sort by end time. sort takes time o(NLgN) space o(3*N)
方法1： 
DP[i] 如果做i个任务的最大profit
     dp[i] = profit[i]
           min(dp[j]) + profit[i], j.end time < i.start time -> 所以要sort by end time来拿到所有在i之前结束的job -> n 
O(n^2) -> 超时            

方法2，需要sort by end time
DP[i] 截止到i找个的max profit
    不取： dp[i-1]
    取： dp[j] + profit[i] . j的end time < i的start time。j是end time最靠近i start time的interval，所以要sort by end time. -> 可以用binary search找这个j -> lgn 

O(nlgn)    
*/

public class Job {
    int start;
    int end;
    int profit;
    
    Job(int start, int end, int profit) {
        this.start = start;
        this.end = end;
        this.profit = profit;
    }
}


class Solution {
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        //Sort by end time.
        List<Job> jobs = new ArrayList<Job>();
        for (int i = 0; i < startTime.length; i++) {
            jobs.add(new Job(startTime[i], endTime[i], profit[i]));
        }
        Collections.sort(jobs, (a, b) -> Integer.compare(a.end, b.end));
         
        // jobs.forEach(o -> System.out.println("start " + o.start + ".end " + o.end + ". profit " + o.profit));
        int[] dp = new int[jobs.size()];
        dp[0] = jobs.get(0).profit; 
        for (int i = 1; i < jobs.size(); i++) {
            // 情况1： 取自己+ previous dp[j] 
            dp[i] = jobs.get(i).profit; // 取自己
            // 如果取的话，从i-1到0中找end time最接近的那个dp[j] -> 可以优化成binary search
            
            // 1.a. binary search to find j that closest to i, j's end <= start  
            int j = binarySearch(jobs, i, jobs.get(i).start); 
            if (j != -1) { // binary 求出来可能-1 or length
                dp[i] = Math.max(dp[i], jobs.get(i).profit + dp[j]);
            }
            //1.b. 不用binary search
            // for (int j = i-1; j >= 0; j--) {
            //     if (jobs.get(i).start >= jobs.get(j).end) {
            //         // System.out.println("i " + i + " j " + j);
            //         dp[i] = Math.max(dp[i], jobs.get(i).profit + dp[j]);
            //         break; // 因为sort by end time, 所以找到第一个就是最靠近的那个
            //     }
            // }
            
            // 情况2， 不取自己，则和如果不取对比
            dp[i] = Math.max(dp[i], dp[i-1]);            
        }
        return dp[jobs.size()-1]; 
    }
    
    // binary search on the closest job whose end time <= target's starting time(k). 
    // 方法1：先找 index > k, 再index--
    private int binarySearch2(List<Job> jobs, int len, int k) {
        // 1. find first element > k 
        int left = 0;
        int right = len - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2; 
            if (jobs.get(mid).end <= k) {
                left = mid + 1; 
            }
            else {
                 right = mid - 1; 
            }
        }
        
        if (left > 0 && jobs.get(left-1).end == k) {
            left--;
            return left; // find the element that == k 
        }
        // 2. now find the first element > k, left - 1 should be the closest element, check if it's valid 
        if (left - 1 >= 0 && left - 1 < len) {
            return left -1;
        }
        return -1; // cannot find a valid result  
    }
    
    // binary search on the closest job whose end time <= target's starting time(k). 
    // 方法二：找 index <= k, 有dup的话找的是最左边那个，再 while向右
    private int binarySearch(List<Job> jobs, int len, int k) {
        int left = 0;
        int right = len - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2; 
            if (jobs.get(mid).end >= k) {
                 right = mid - 1; 
            }
            else {
                left = mid + 1; 
            }
        }

        if (right < len - 1 && jobs.get(right+1).end == k) {
            right++;
        }
        
        while (right >= 0 && right < len - 1 && jobs.get(right+1).end == jobs.get(right).end) {
            right++; 
        }
        return right; // -1, or 最靠近target的valid值
    }
}


