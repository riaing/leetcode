Given an array of non-negative integers arr, you are initially positioned at start index of the array. When you are at index i, you can jump to i + arr[i] or i - arr[i], check if you can reach to any index with value 0.

Notice that you can not jump outside of the array at any time.

 

Example 1:

Input: arr = [4,2,3,0,3,1,2], start = 5
Output: true
Explanation: 
All possible ways to reach at index 3 with value 0 are: 
index 5 -> index 4 -> index 1 -> index 3 
index 5 -> index 6 -> index 4 -> index 1 -> index 3 
Example 2:

Input: arr = [4,2,3,0,3,1,2], start = 0
Output: true 
Explanation: 
One possible way to reach at index 3 with value 0 is: 
index 0 -> index 4 -> index 1 -> index 3
Example 3:

Input: arr = [3,0,2,1,2], start = 2
Output: false
Explanation: There is no way to reach at index 1 with value 0.
 

Constraints:

1 <= arr.length <= 5 * 104
0 <= arr[i] < arr.length
0 <= start < arr.length

----------------------------4.3.2021 BFS O(n) -----------------------------------------------------------------------------------------
  class Solution {
    public boolean canReach(int[] arr, int start) {
        // queue to stroe index 
        Queue<Integer> indexQueue = new LinkedList<Integer>();
        Set<Integer> visited = new HashSet<Integer>();
        visited.add(start);
        indexQueue.offer(start); 
        while (!indexQueue.isEmpty()) {
            int curIndex = indexQueue.poll();
            if (arr[curIndex] == 0) {
                return true;
            }
            int nextIndex1 = curIndex + arr[curIndex];
            int nextIndex2 = curIndex - arr[curIndex];
            add(nextIndex1, arr, indexQueue, visited);
            add(nextIndex2, arr, indexQueue, visited);
        }
        return false;
    }
    
    private void add(int index, int[] arr,   Queue<Integer> indexQueue, Set<Integer> visited) {
        if (index >= 0 && index < arr.length && !visited.contains(index)) {
            indexQueue.offer(index); 
            visited.add(index);
        }
    }
}

------------------------ 4.3.2021 DFS O(n) ------------------------------------------------------------------------------------------------------
 
 class Solution {
    public boolean canReach(int[] arr, int start) {
        Set<Integer> visited = new HashSet<Integer>();
        return helper(arr, start, visited);
    }
    
    private boolean helper(int[] arr, int index, Set<Integer> visited) {
        if (index < 0 || index >= arr.length || visited.contains(index)) {
            return false;
        }
        if (arr[index] == 0) {
            return true;
        }
        visited.add(index);
            
        int nextIndex1 = index + arr[index];
        int nextIndex2 = index - arr[index];
        return helper(arr, nextIndex1, visited) || helper(arr, nextIndex2, visited);
    }
}
