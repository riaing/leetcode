Given an array arr of positive integers sorted in a strictly increasing order, and an integer k.

Return the kth positive integer that is missing from this array.

 

Example 1:

Input: arr = [2,3,4,7,11], k = 5
Output: 9
Explanation: The missing positive integers are [1,5,6,8,9,10,12,13,...]. The 5th missing positive integer is 9.
Example 2:

Input: arr = [1,2,3,4], k = 2
Output: 6
Explanation: The missing positive integers are [5,6,7,...]. The 2nd missing positive integer is 6.
 

Constraints:

1 <= arr.length <= 1000
1 <= arr[i] <= 1000
1 <= k <= 1000
arr[i] < arr[j] for 1 <= i < j <= arr.length
 

Follow up:

Could you solve this problem in less than O(n) complexity?
  
  ------------------------- bs ------------------------------------
  /*
arr[i] - (i+1) = 到 arr[i] 为止miss的个数
Time： o(lgN);
*/
class Solution {
    public int findKthPositive(int[] arr, int k) {
        int start = 0;
        int end = arr.length - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            int missingCnt = arr[mid] - (mid+1);
            System.out.println(mid + " " + missingCnt);
            if (missingCnt >= k) {
                end = mid;
            }
            else {
                start = mid + 1; 
            }
        }
        
        int missingCount = arr[start] - (start+1); 
        // 1. 特殊处理第一个
        if (start == 0) {
            if (missingCount >= k) {
                return k; 
            }
            else {
                return arr[start] + (k - missingCount);
            }
        } 
        
        // 2. 非第一个元素，永远保证missingCount <= k
        if (missingCount > k) {
            start--;
        }
        
        missingCount = arr[start] - (start+1); 
        if (missingCount == k) {
            return arr[start] - 1;
        }
        else if (missingCount < k) {
            return arr[start] + (k - missingCount);
        }
        return -1;  // never reach here 
    }
    
}
