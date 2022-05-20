Given an array of integers arr, return true if the number of occurrences of each value in the array is unique, or false otherwise.

 

Example 1:

Input: arr = [1,2,2,1,1,3]
Output: true
Explanation: The value 1 has 3 occurrences, 2 has 2 and 3 has 1. No two values have the same number of occurrences.
Example 2:

Input: arr = [1,2]
Output: false
Example 3:

Input: arr = [-3,0,1,-3,1,1,1,-3,10,0]
Output: true
 

Constraints:

1 <= arr.length <= 1000
-1000 <= arr[i] <= 1000

-------------------------- use more space to achieve time ------------------------------------------------

class Solution {
    public boolean uniqueOccurrences(int[] arr) {
        // 1. space trade time 
        Map<Integer, Integer> occurance = new HashMap<Integer, Integer>();
        for (int i : arr) {
            occurance.put(i, occurance.getOrDefault(i, 0) + 1);
        }
        Set<Integer> seen = new HashSet<Integer>();
        for (int k : occurance.keySet()) {
            if (seen.contains(occurance.get(k))) {
                return false; 
            }
            seen.add(occurance.get(k));
        }
        return true; 
    }
}

------------------------- 时间换空间。 o(1) space ----------------------
class Solution {
    public boolean uniqueOccurrences(int[] arr) {
        // 1. sort后，计算每个数字出现的次数，记在本arr上
        Arrays.sort(arr);
        int curCount = 1; 
        int index = 0; 
        for (int i = 0; i < arr.length; i++) {
            if (i+1 < arr.length) {
                if (arr[i] == arr[i+1]) {
                    curCount++;
                }
                else {
                    arr[index] = curCount; 
                    index++; 
                    curCount = 1;
                }
            }
            else {
                arr[index] = curCount; 
                index++;
            }
        }
        
        // 把本arr除了计数外别的bucket设为-1 
        for (int i = index; i < arr.length; i++) {
            arr[i] = -1; 
        }
        // skip -1, sort 后查是否有重复值即可
        Arrays.sort(arr);
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == -1) {
                continue; 
            }
            if (arr[i] == arr[i-1]) {
                return false; 
            }
        }
        
        return true; 
        
    }
}
