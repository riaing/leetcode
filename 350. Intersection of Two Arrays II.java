Given two integer arrays nums1 and nums2, return an array of their intersection. Each element in the result must appear as many times as it shows in both arrays and you may return the result in any order.

 

Example 1:

Input: nums1 = [1,2,2,1], nums2 = [2,2]
Output: [2,2]
Example 2:

Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
Output: [4,9]
Explanation: [9,4] is also accepted.
 

Constraints:

1 <= nums1.length, nums2.length <= 1000
0 <= nums1[i], nums2[i] <= 1000
 

Follow up:

What if the given array is already sorted? How would you optimize your algorithm?
What if nums1's size is small compared to nums2's size? Which algorithm is better?
What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?

----------------- 2 pointer ---------------
/*
What if the given array is already sorted? How would you optimize your algorithm? 
- 2 pointer
What if nums1's size is small compared to nums2's size? Which algorithm is better?
- 把短的放hashMap，扫长的 -> 比sort好
What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?
- 假设1能fit memory：2 pointer和hashMap在这里都行。 2 pointer在一种情况更好：nums1 =【1，10】， nums2=【1。。。。1million】。 2pointer可以及早结束，while map解法要把2读完
- 如果1,2都不能fit。可以用external的方法将两array sort，然后一段段读
*/     
class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2); 
        List<Integer> res = new ArrayList<>(); 
        int i = 0; 
        int j = 0;
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
        

        int[] output = res.stream().mapToInt(Integer::intValue).toArray(); 
        return output;
    
    }
}
