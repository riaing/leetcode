Given a sequence originalSeq and an array of sequences, write a method to find if originalSeq can be uniquely reconstructed from the array of sequences.

Unique reconstruction means that we need to find if originalSeq is the only sequence such that all sequences in the array are subsequences of it.

Example 1:

Input: originalSeq: [1, 2, 3, 4], seqs: [[1, 2], [2, 3], [3, 4]]
Output: true
Explanation: The sequences [1, 2], [2, 3], and [3, 4] can uniquely reconstruct   
[1, 2, 3, 4], in other words, all the given sequences uniquely define the order of numbers 
in the 'originalSeq'. 
Example 2:

Input: originalSeq: [1, 2, 3, 4], seqs: [[1, 2], [2, 3], [2, 4]]
Output: false
Explanation: The sequences [1, 2], [2, 3], and [2, 4] cannot uniquely reconstruct 
[1, 2, 3, 4]. There are two possible sequences we can construct from the given sequences:
1) [1, 2, 3, 4]
2) [1, 2, 4, 3]
Example 3:

Input: originalSeq: [3, 1, 4, 2, 5], seqs: [[3, 1, 5], [1, 4, 2, 5]]
Output: true
Explanation: The sequences [3, 1, 5] and [1, 4, 2, 5] can uniquely reconstruct 
[3, 1, 4, 2, 5].

---------------------- topo sort ----------------------------------------------------------------------
/*
1. 最后的list的length = nums
2. topo sort时，没有两个node同时indegree = 0

Solution #
Since each sequence in the given array defines the ordering of some numbers, we need to combine all these ordering rules to find two things:

Is it possible to construct the originalSeq from all these rules?
Are these ordering rules not sufficient enough to define the unique ordering of all the numbers in the originalSeq? In other words, can these rules result in more than one sequence?
Take Example-1:

originalSeq: [1, 2, 3, 4], seqs:[[1, 2], [2, 3], [3, 4]]
The first sequence tells us that ‘1’ comes before ‘2’; the second sequence tells us that ‘2’ comes before ‘3’; the third sequence tells us that ‘3’ comes before ‘4’. Combining all these sequences will result in a unique sequence: [1, 2, 3, 4].

The above explanation tells us that we are actually asked to find the topological ordering of all the numbers and also to verify that there is only one topological ordering of the numbers possible from the given array of the sequences.

This makes the current problem similar to Tasks Scheduling Order with two differences:

We need to build the graph of the numbers by comparing each pair of numbers in the given array of sequences.
We must perform the topological sort for the graph to determine two things:
Can the topological ordering construct the originalSeq?
That there is only one topological ordering of the numbers possible. This can be confirmed if we do not have more than one source at any time while finding the topological ordering of numbers.

Time complexity#
In step ‘d’, each number can become a source only once and each edge (a rule) will be accessed and removed once. Therefore, the time complexity of the above algorithm will be O(V+E)
O(V+E)
, where ‘V’ is the count of distinct numbers and ‘E’ is the total number of the rules. Since, at most, each pair of numbers can give us one rule, we can conclude that the upper bound for the rules is O(N)
O(N)
 where ‘N’ is the count of numbers in all sequences. So, we can say that the time complexity of our algorithm is O(V+N)
O(V+N)
.

Space complexity#
The space complexity will be O(V+N)
O(V+N)
, since we are storing all of the rules for each number in an adjacency list.
*/
class Solution {
    public boolean sequenceReconstruction(int[] nums, List<List<Integer>> sequences) {
        // corner case 
        if (nums == null || nums.length == 0 || sequences == null || sequences.size() == 0) {
            return false; 
        }
        // 0. construct map 
        Map<Integer, List<Integer>> edges = new HashMap<Integer, List<Integer>>();
        Map<Integer, Integer> indegrees = new HashMap<Integer, Integer>();
        for (int i = 0; i < sequences.size(); i++) {
            List<Integer> cur = sequences.get(i);
            // every pair 
            for (int j = 0; j < cur.size(); j++) {
                edges.putIfAbsent(cur.get(j), new ArrayList<Integer>());
                indegrees.putIfAbsent(cur.get(j), 0);
            }
        }
        
        // 1. build map - 和前一步分开做！避免[1]出错
        for (int i = 0; i < sequences.size(); i++) {
            List<Integer> cur = sequences.get(i);
            // every pair 
            for (int j = 1; j < cur.size(); j++) {
                int first = cur.get(j-1);
                int second = cur.get(j);
                edges.get(first).add(second); 
                indegrees.put(second, indegrees.get(second) + 1);
            }
        }
        
        // 2. construct queue 
        Queue<Integer> q = new LinkedList<Integer>();
        for (int key : indegrees.keySet()) {
            if (indegrees.get(key) == 0) {
                q.offer(key);
            }
        }
        
        // 判断0；如果nums含更多的元素，return false
        if (nums.length != edges.size()) {
            return false; 
        }
        
        List<Integer> ordered = new ArrayList<Integer>();
        // 3. construct the topological list 
        while (q.size() != 0) {
            // 判断1： 不能有多种可能性的组合
            if (q.size() > 1) { // 如果只有一种组合，那么每次queue的size肯定为1
                return false; 
            }
            int cur = q.poll();
            ordered.add(cur);
            // reduce it's neibors indegree and add into queue 
            for (int neibor : edges.get(cur)) {
                indegrees.put(neibor, indegrees.get(neibor) - 1);
                if (indegrees.get(neibor) == 0) {
                    q.offer(neibor);
                }
            }
        }
        // 判断2: 只能有一种解
        if (ordered.size() != nums.length) {
            return false;
        }
        // 判断3： 顺序必须要和nums一样
        for (int i = 0; i < ordered.size(); i++) {
            if (ordered.get(i) != nums[i]) {
                return false; 
            }
        }
        return true; 
    }
}
