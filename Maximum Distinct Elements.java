https://www.educative.io/courses/grokking-the-coding-interview/gx6oKY8PGYY 


Given an array of numbers and a number ‘K’, we need to remove ‘K’ numbers from the array such that we are left with maximum distinct numbers.

Example 1:

Input: [7, 3, 5, 8, 5, 3, 3], and K=2
Output: 3
Explanation: We can remove two occurrences of 3 to be left with 3 distinct numbers [7, 3, 8], we have to skip 5 because it is not distinct and appeared twice. 

Another solution could be to remove one instance of '5' and '3' each to be left with three distinct numbers [7, 5, 8], in this case, we have to skip 3 because it appeared twice.
Example 2:

Input: [3, 5, 12, 11, 12], and K=3
Output: 2
Explanation: We can remove one occurrence of 12, after which all numbers will become distinct. Then we can delete any two numbers which will leave us 2 distinct numbers in the result.
Example 3:

Input: [1, 2, 3, 3, 3, 3, 4, 4, 5, 5, 5], and K=2
Output: 3
Explanation: We can remove one occurrence of '4' to get three distinct numbers.

----------------------- min heap -------------
/*
min heap, 将 frequency 降到1，数 distinct num
nlgn n is the distinct numbers. this happens when all numbers are unique in the array 
one way optimization: don't push count=1's number in queue, when we know most number are unique 
*/
class Solution {
    public int findLeastNumOfUniqueInts(int[] nums, int k) {
        //1, map of num -> count 
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        // 2. min heap sort by nums.count 
        PriorityQueue<Integer> q = new PriorityQueue<Integer>((a, b) -> map.get(a) - map.get(b));
        for (int n : map.keySet()) {
            q.offer(n);
        }
        
        // 3. 从最小非1的堆顶开始，使每个 count 变为1，数 unique number
        int unique = 0;
        while (k > 0 && q.size() > 0) {
            int num = q.poll();
            if (map.get(num) == 1) {
                System.out.println(num);
                unique++;
            }
            else {
                k = k - (map.get(num) - 1);
                if (k >= 0) {
                    System.out.println(num);
                    unique++;
                }
            }
        }
        // 3.2 关键一步！如果 k 比较大时，还得移除 distinct number. eg: [3, 5, 12, 11, 12] k = 3 
        if (k > 0) {
            unique = unique - k;
        }
        return unique;
        
    }
} 
