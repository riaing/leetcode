Given two integer arrays nums1 and nums2, return an array of their intersection. Each element in the result must be unique and you may return the result in any order.

 

Example 1:

Input: nums1 = [1,2,2,1], nums2 = [2,2]
Output: [2]
Example 2:

Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
Output: [9,4]
Explanation: [4,9] is also accepted.
 

Constraints:

1 <= nums1.length, nums2.length <= 1000
0 <= nums1[i], nums2[i] <= 1000

------------------------ 3 解法 ------------------------------
/*
HashSet解法： Time n; Space: n
Two pointer解法： Time：nlgn， space：1. 两者sort，two pointer指向两头，数字小的那边++
binary search解法：sort其中一个，iterate另一个array，通过bs在sort中找值 Time: nlgn, space: 1 
*/
 
 ----------------------------- HashSet ----------------------------
 class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> s1 = new HashSet<Integer>();
        for (int i : nums1) {
            s1.add(i);
        }
        Set<Integer> inter = new HashSet<Integer>();
        for (int j : nums2) {
            if (s1.contains(j)) {
                inter.add(j);
            }
        }
        int[] res = new int[inter.size()];
        int i = 0;
        for (int n : inter) {
            res[i] = n;
            i++;
        }
        return res;
    }
}

---------------------------- 2 pointer -------------------------------
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2); 
        int i = 0;
        int j = 0;
        HashSet<Integer> res = new HashSet<>();
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                res.add(nums1[i]);
                i++;
                j++;
            }
            else if (nums1[i] < nums2[j]) {
                i++;
            }
            else {
                j++;
            }
        }
        int index = 0; 
        int[] output = new int[res.size()];
        for (int num : res) {
            output[index++] = num;
        }
        return output; 
        
    }
}

--------------------------- binary search ------------------------------
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        HashSet<Integer> res = new HashSet<>();
        // iterate num2,去1中找值
        for (int i = 0; i < nums2.length; i++) {
            int cur = nums2[i];
            // 在1中找cur
            if (exist(nums1, cur)) {
                res.add(cur);
            }
        }
        
        int index = 0; 
        int[] output = new int[res.size()];
        for (int num : res) {
            output[index++] = num;
        }
        return output; 
    }
    
    // binary search if k is in arr
    private boolean exist(int[] arr, int k) {
        int start = 0;
        int end = arr.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (arr[mid] == k) {
                return true;
            }
            else if (arr[mid] < k) {
                start = mid + 1;
            }
            else {
                end = mid - 1; 
            }
        }
        return false; 
    }
}
