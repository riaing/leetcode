Given a characters array tasks, representing the tasks a CPU needs to do, where each letter represents a different task. Tasks could be done in any order. Each task is done in one unit of time. For each unit of time, the CPU could complete either one task or just be idle.

However, there is a non-negative integer n that represents the cooldown period between two same tasks (the same letter in the array), that is that there must be at least n units of time between any two same tasks.

Return the least number of units of times that the CPU will take to finish all the given tasks.

 

Example 1:

Input: tasks = ["A","A","A","B","B","B"], n = 2
Output: 8
Explanation: 
A -> B -> idle -> A -> B -> idle -> A -> B
There is at least 2 units of time between any two same tasks.
Example 2:

Input: tasks = ["A","A","A","B","B","B"], n = 0
Output: 6
Explanation: On this case any permutation of size 6 would work since n = 0.
["A","A","A","B","B","B"]
["A","B","A","B","A","B"]
["B","B","B","A","A","A"]
...
And so on.
Example 3:

Input: tasks = ["A","A","A","A","A","A","B","C","D","E","F","G"], n = 2
Output: 16
Explanation: 
One possible solution is
A -> B -> C -> A -> D -> E -> A -> F -> G -> A -> idle -> idle -> A -> idle -> idle -> A
 

Constraints:

1 <= task.length <= 104
tasks[i] is upper-case English letter.
The integer n is in the range [0, 100].

---------------------------- heap -----------------------------------------------------------------
/*
跑满 N+1个job后，再跑下一轮

This problem follows the Top ‘K’ Elements pattern and is quite similar to Rearrange String K Distance Apart. We need to rearrange tasks such that same tasks are ‘K’ distance apart.

Following a similar approach, we will use a Max Heap to execute the highest frequency task first. After executing a task we decrease its frequency and put it in a waiting list. In each iteration, we will try to execute as many as k+1 tasks. For the next iteration, we will put all the waiting tasks back in the Max Heap. If, for any iteration, we are not able to execute k+1 tasks, the CPU has to remain idle for the remaining time in the next iteration.

T
Time complexity#
The time complexity of the above algorithm is O(N*logN)
 where ‘N’ is the number of tasks. Our while loop will iterate once for each occurrence of the task in the input (i.e. ‘N’) and in each iteration we will remove a task from the heap which will take O(logN)
 time. Hence the overall time complexity of our algorithm is O(N*logN)

Space complexity#
The space complexity will be O(N)
, as in the worst case, we need to store all the ‘N’ tasks in the HashMap.
*/
class Node {
    char task;
    int occurance;
    public Node(char task, int occurance) {
        this.task = task;
        this.occurance = occurance; 
    }
}

class Solution {
    public int leastInterval(char[] tasks, int n) {
         // Map to store the char - occurance 
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (char c : tasks) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        // put node into heap sorted by max occurance 
        PriorityQueue<Node> q = new PriorityQueue<Node>((a, b) -> b.occurance - a.occurance);
        for (Map.Entry<Character,Integer> entry : map.entrySet()) {
            q.offer(new Node(entry.getKey(), entry.getValue()));
        }
        
        int res = 0;
        while (q.size() != 0) {
            Queue<Node> waitlist = new LinkedList<Node>();
            for (int i = 1; i <= n+1; i++) { // 循环n+1次
                if (q.size() != 0) { //如果还有job能run
                    res++;
                    Node cur = q.poll();
                    cur.occurance = cur.occurance - 1;
                    if (cur.occurance != 0) {
                        waitlist.add(cur);
                    }
                }
                else if (waitlist.size() != 0) { // 说明本轮中需要idle，并且下一轮还有job要跑
                    res+= (n+1 -(i-1)); // 本轮还剩几个idle：总共的减去已经跑过的
                    break;
                }
            } 
            // dump waitlist back to heap 
            q.addAll(waitlist);
        }
        return res; 
    }
}
