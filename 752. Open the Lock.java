You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots: '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'. The wheels can rotate freely and wrap around: for example we can turn '9' to be '0', or '0' to be '9'. Each move consists of turning one wheel one slot.

The lock initially starts at '0000', a string representing the state of the 4 wheels.

You are given a list of deadends dead ends, meaning if the lock displays any of these codes, the wheels of the lock will stop turning and you will be unable to open it.

Given a target representing the value of the wheels that will unlock the lock, return the minimum total number of turns required to open the lock, or -1 if it is impossible.

Example 1:
Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
Output: 6
Explanation:
A sequence of valid moves would be "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202".
Note that a sequence like "0000" -> "0001" -> "0002" -> "0102" -> "0202" would be invalid,
because the wheels of the lock become stuck after the display becomes the dead end "0102".
Example 2:
Input: deadends = ["8888"], target = "0009"
Output: 1
Explanation:
We can turn the last wheel in reverse to move from "0000" -> "0009".
Example 3:
Input: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
Output: -1
Explanation:
We can't reach the target without getting stuck.
Example 4:
Input: deadends = ["0000"], target = "8888"
Output: -1
Note:
The length of deadends will be in the range [1, 500].
target will not be in the list deadends.
Every string in deadends and the string target will be a string of 4 digits from the 10,000 possibilities '0000' to '9999'.

----------------------------------------------BFS----------------------------------------------------------------------------
/**
time:  n-> N is the number of digits in the lock (4); A -> 0-9(10)
 each node, rotate() takes O (n^2), and there are A^n nodes. so time is 0(A^n * n^2) -> (10^4 * n^2)。如果算上copy deadends的话，就是O(A^n * n^2 + D) 
space: queue has O(A^n) nodes, and each node is a string of n, so the total is 
O(A^n *n)如果算上node上string的空间的话。或者general来说，空间为 O（A^n）个node大小也可。
**/

class Solution {
    public int openLock(String[] deadends, String target) {
        // O(D) ->D is the size of deadends.
        Set<String> dead = new HashSet<String>(Arrays.asList(deadends));
        // corner case 
        if (dead.contains("0000")) {
            return -1;
        }
        
        Set<String> seen = new HashSet<String>();
        seen.add("0000");
        
        Queue<String> queue = new LinkedList<String>();
        queue.offer("0000");
        int step = 0;
        while (!queue.isEmpty()) {
            step++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String cur = queue.poll();
                List<String> next = rotates(cur, seen, dead);
                for (String s : next) {
                    if (s.equals(target)) {
                        return step;
                    }
                    queue.offer(s);
                    seen.add(s);
                }
            }
        }
        return -1;
    }
    
    // 计算法：取进位后的个位数：0-> 9/1; 9 -> 8/0; 1->2/0; -> (x + 1/-1 + 10) %10: 增/减一位后加10取余数
    // find all possible rotates of a string, this rotate must not be shown in the previous level
    private List<String> rotates(String s, Set<String> seen, Set<String> dead) {
        List<String> res = new ArrayList<String>();
    
        List<Integer> cal = new ArrayList<Integer>();
        cal.add(1);
        cal.add(-1);
        // o(n)
        for (int i = 0; i < 4; i++) {
            //o(2)
            for (int n : cal) {
                int newPos = (s.charAt(i) - '0' + n + 10) % 10;
                //o(n) -> copy the string
                String newRotate = s.substring(0, i) + newPos + "" + s.substring(i+1);
                if (!seen.contains(newRotate) && !dead.contains(newRotate)) {
                    res.add(newRotate);
                }
            }
        }
        return res;
        // time: O(2n*n) = O(n^2)
    }
    
}
