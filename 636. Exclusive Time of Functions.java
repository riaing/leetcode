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

----------------------------- 2022 stack + 1 variable ------------------------------------------------------------------
 
 
 /*
https://www.youtube.com/watch?v=Oi68_8xkxI4 

stack存function Id, 遇到start时 push，遇到end时pop
1. 每次运行新fun时，实际是把pre func suspend了，所以要算一段pre func的运行时间
2. 用variable preTime来表示前一个程序的时间

Time O(n) n - # of logs 
Space O(n/2) - 每个程序的运行有两条log ：start end
*/
class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        Deque<Integer> stack = new LinkedList<Integer>(); // store function id
        int[] res = new int[n]; 
        int preTime = 0; 
        
        for (String s : logs) {
            // parse log 
            String[] log = s.split(":");
            int funId = Integer.parseInt(log[0]);
            int curTime = Integer.parseInt(log[2]);
            
            if (log[1].equals("start")) {
                // suspend previous function if any 
                if (!stack.isEmpty()) {
                    res[stack.peek()] += curTime - preTime; 
                }
                preTime = curTime; 
                stack.push(funId);
            }
            else {
                res[funId] += curTime - preTime + 1; 
                preTime = curTime + 1;
                stack.pop();
            }
        }
        return res; 
        
    }
}

------------------ follow up算inclusive时间。stack 记录jobId+状态改变时间 ---------
 //重点：注意时间的细节： end时加1 

/*
followup：
算inclusive time就得记录上个job状态改变的时间 
Stack<int[]> 0: jobId, 1: 状态改变的时间(改成start/end)
每次开新job时改变stack里lastJob的时间。
*/

class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] exclusiveTime = new int[n];
        int[] inclusiveTime = new int[n];
        
        Deque<int[]> stack = new LinkedList<>(); // 0; jobId, 1 - 状态改变的时间
        for (int i = 0; i < logs.size(); i++) {
            String[] cur = logs.get(i).split(":");
            int jobId = Integer.parseInt(cur[0]);
            int time = Integer.parseInt(cur[2]);
            if (cur[1].equals("start")) {
                 if(!stack.isEmpty()) {
                    // 计算上一个的时间
                    int[] lastJob = stack.pop();
                    int exeTime = time - lastJob[1];
                    exclusiveTime[lastJob[0]] += exeTime; 
                    // 更新last job 状态时间
                    lastJob[1] = time; 
                    stack.push(lastJob);
                }
                 // push自己
                stack.push(new int[]{jobId, time});
            }
            if (cur[1].equals("end")) { 
                // 算自己时间
                int[] self = stack.pop();
                int exeTime = time - self[1] + 1; // 加1
                exclusiveTime[jobId] += exeTime; 
                
                // 启动之前的job
                if (stack.peek() != null) {
                    int[] lastJob = stack.pop(); // 加1 
                    lastJob[1] = time + 1; 
                    stack.push(lastJob);
                    
                    // 算inclusive time
                    inclusiveTime[lastJob[0]] += exeTime; // 当前job的运行时间就是和上个job重叠的时间
                }
            }
        }
        System.out.println(Arrays.toString(inclusiveTime)); 
        return exclusiveTime; 
    }
}
