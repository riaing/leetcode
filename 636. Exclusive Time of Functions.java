On a single threaded CPU, we execute some functions.  Each function has a unique id between 0 and N-1.

We store logs in timestamp order that describe when a function is entered or exited.

Each log is a string with this format: "{function_id}:{"start" | "end"}:{timestamp}".  For example, "0:start:3" means the function with id 0 started at the beginning of timestamp 3.  "1:end:2" means the function with id 1 ended at the end of timestamp 2.

A function's exclusive time is the number of units of time spent in this function.  Note that this does not include any recursive calls to child functions.

Return the exclusive time of each function, sorted by their function id.

 

Example 1:



Input:
n = 2
logs = ["0:start:0","1:start:2","1:end:5","0:end:6"]
Output: [3, 4]
Explanation:
Function 0 starts at the beginning of time 0, then it executes 2 units of time and reaches the end of time 1.
Now function 1 starts at the beginning of time 2, executes 4 units of time and ends at time 5.
Function 0 is running again at the beginning of time 6, and also ends at the end of time 6, thus executing for 1 unit of time. 
So function 0 spends 2 + 1 = 3 units of total time executing, and function 1 spends 4 units of total time executing.
 

Note:

1 <= n <= 100
Two functions won't start or end at the same time.
Functions will always log when they exit.
 
------------------------stack。这题不能用递归，比如0 start, 0 end, 1 start, 1 end时，不一定以0 start就以 0 end ------------------------------
class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] time = new int[n];
        int preTime = 0; 
        Deque<String[]> log = new LinkedList<String[]>();
        for (int i = 0; i < logs.size(); i++) {
            String[] curLog = logs.get(i).split(":");
            if (curLog[1].equals("start")) {
                // update the previous one 
                if (!log.isEmpty()) {
                    String[] pre = log.peek();
                    time[Integer.valueOf(pre[0])] += Integer.valueOf(curLog[2]) - Integer.valueOf(pre[2]);
                }
                log.push(curLog);
                // 更新 prevTime
                preTime = Integer.valueOf(curLog[2]);
            }
            else {
                log.pop();
                int curTime = Integer.valueOf(curLog[2]);
                // 存值
                time[Integer.valueOf(curLog[0])] += curTime + 1 - preTime;
                preTime = curTime + 1;
                // 这个程序end时，stack中的第一个程序自动start，所以更新他的时间
                if (!log.isEmpty()) {
                    log.peek()[2] = curTime + 1 + "";
                }
                
            }
        }
        return time;
    }
}
