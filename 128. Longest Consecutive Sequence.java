
Given an unsorted array of integers, find the length of the longest consecutive elements sequence.

Your algorithm should run in O(n) complexity.

Example:

Input: [100, 4, 200, 1, 3, 2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.

使用一个集合set存入所有的数字，然后遍历数组中的每个数字，如果其在集合中存在，那么将其移除，然后分别用两个变量pre和next算出其前一个数跟后一个数，
然后在集合中循环查找，如果pre在集合中，那么将pre移除集合，然后pre再自减1，直至pre不在集合之中，对next采用同样的方法，那么next-pre-1就是当前数字
的最长连续序列，更新res即可

class Solution {
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Set<Integer> set = new HashSet<Integer>();
        for (int i : nums) {
            set.add(i);
        }
        int res = 0; 
        for (int i : nums) {
            // If the element is not in the set, it must be moved by previous calculating. 这里保证了总时间的O（n）
            if (!set.contains(i)) {
                continue;
            }
            int currentStreak = 1;
            // remove visited的element是关键。
            set.remove(i);
            int pre = i - 1; 
            int next = i + 1; 
            while (set.contains(pre)) {
                set.remove(pre);
                pre--;
                currentStreak++;
            }
            while(set.contains(next)) {
                set.remove(next);
                next++; 
                currentStreak++;
            }
            // 这种解法不太好，比如很难想到这里为什么是next-pre-1. 所以我们直接用一个var currentStreak来表示当前的最长length。 
            // 用next - pre -1 的话就不需要currentStreak来记录了
            //res = Math.max(res, next - pre -1);
            res = Math.max(res, currentStreak );
        }
        return res; 
    }
}

------------------------- 2022. 6 -----------------------------
    
    /*
time: O(n). 每次找sequence只会从最小的开始，所以每个元素最多走一遍
*/
class Solution {
    public int longestConsecutive(int[] nums) {
        // 1. construct set 
        Set<Integer> set = new HashSet<Integer>(); 
        for (int i : nums) {
            set.add(i);
        }
        
        int max = 0; 
        for (int n : set) {
            if (set.contains(n-1)) { // 本题特殊：只从starting找。{1，2，3} 就只会从1开始，而且跳过2，3
                continue;
            }
            // 2. 从start起，找最长能走到哪
            int cur = n; 
            int curMax = 0; 
            while (set.contains(cur)) {
                curMax++;
                cur++; 
            }
            // 3. 跟新
            max = Math.max(max, curMax);
        }
        return max; 
        
    }
}
