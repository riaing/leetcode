There are two sorted arrays nums1 and nums2 of size m and n respectively.

Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

You may assume nums1 and nums2 cannot be both empty.

Example 1:

nums1 = [1, 3]
nums2 = [2]

The median is 2.0
Example 2:

nums1 = [1, 2]
nums2 = [3, 4]

The median is (2 + 3)/2 = 2.5
----------------------------------------------------------------
https://blog.csdn.net/yutianzuijin/article/details/11499917 
最后从medianof two sorted arrays中看到了一种非常好的方法。原文用英文进行解释，在此我们将其翻译成汉语。该方法的核心是将原问题转变成一个寻找第k小数的问题（假设两个原序列升序排列），这样中位数实际上是第(m+n)/2小的数。所以只要解决了第k小数的问题，原问题也得以解决。
首先假设数组A和B的元素个数都大于k/2，我们比较A[k/2-1]和B[k/2-1]两个元素，这两个元素分别表示A的第k/2小的元素和B的第k/2小的元素。这两个元素比较共有三种情况：>、<和=。如果A[k/2-1]<B[k/2-1]，这表示A[0]到A[k/2-1]的元素都在A和B合并之后的前k小的元素中。换句话说，A[k/2-1]不可能大于两数组合并之后的第k小值，所以我们可以将其抛弃。
证明也很简单，可以采用反证法。假设A[k/2-1]大于合并之后的第k小值，我们不妨假定其为第（k+1）小值。由于A[k/2-1]小于B[k/2-1]，所以B[k/2-1]至少是第（k+2）小值。但实际上，在A中至多存在k/2-1个元素小于A[k/2-1]，B中也至多存在k/2-1个元素小于A[k/2-1]，所以小于A[k/2-1]的元素个数至多有k/2+
 k/2-2，小于k，这与A[k/2-1]是第（k+1）的数矛盾。
当A[k/2-1]>B[k/2-1]时存在类似的结论。
当A[k/2-1]=B[k/2-1]时，我们已经找到了第k小的数，也即这个相等的元素，我们将其记为m。由于在A和B中分别有k/2-1个元素小于m，所以m即是第k小的数。(这里可能有人会有疑问，如果k为奇数，则m不是中位数。这里是进行了理想化考虑，在实际代码中略有不同，是先求k/2，然后利用k-k/2获得另一个数。)
通过上面的分析，我们即可以采用递归的方式实现寻找第k小的数。此外我们还需要考虑几个边界条件：

如果A或者B为空，则直接返回B[k-1]或者A[k-1]；如果k为1，我们只需要返回A[0]和B[0]中的较小值；如果A[k/2-1]=B[k/2-1]，返回其中一个；

本文来自 yutianzuijin 的CSDN 博客 ，全文地址请点击：https://blog.csdn.net/yutianzuijin/article/details/11499917?utm_source=copy 

-------------------------------------------------------------------------------------------------
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        
        int nums1Length = nums1.length;
        int nums2Length = nums2.length;
        
        // Odd number 
        if ((nums1Length + nums2Length) % 2 != 0 ) {
           return findKth(nums1, nums1Length, nums2, nums2Length, (nums1Length + nums2Length) / 2 + 1);
        }
        else {
            return ((findKth(nums1, nums1Length, nums2, nums2Length, (nums1Length + nums2Length) / 2)
                + findKth(nums1, nums1Length, nums2, nums2Length, (nums1Length + nums2Length) / 2 + 1)) / 2.0);
        }
    }
    
    // Find the Kth element of the two arrays. 
    private int findKth(int[] nums1, int length1, int[] nums2, int length2, int k) {
        // make sure that array 1 is shorter than array 2.
        if (length1 > length2) {
            return findKth(nums2, length2, nums1, length1, k);
        }
        // If there is an array is empty, then return the medium of the another array. 
        else if (length1 == 0) {
            return nums2[k-1];
        }
        // If trying to find the first element, return the smaller one. eg case : [2], [3] 
        else if (k == 1) {
            return Math.min(nums1[k-1], nums2[k-1]);
        }
        // See the k/2's element, and compare it in the two array 
        //这里用min的原因是，如果某个array的长度无法为总array的median值（及两array长度差别很大的情况 eg:  [1], [2,3,4,5,6], 
        // 如果只是 int half1 = k / 2; half1此时会为3， outofindex了第一个array ）。 
        int half1 = Math.min(length1, k / 2); 
        int half2 = Math.min (length2, k / 2);
        if (nums1[half1 -1 ] < nums2[half2 - 1]) {
            // This means that the first half1 element in nums1 won't be the Kth element, so we can discard them. 
            return findKth(Arrays.copyOfRange(nums1, half1, length1), length1 - half1, nums2, length2, k - half1); 
        }
        // If nums[half1 -1] > nums2[half2 - 1], we discard array2's k/2 part. 
        // if nums[half1 -1] == nums2[half2 - 1], then discard either array's part, so here we discard array2, 
        else {
            return findKth(nums1, length1, Arrays.copyOfRange(nums2, half2, length2), length2 - half2, k - half2);
        }
        
    }
}

思考：如解答中说的一样，当nums[half1 -1] == nums2[half2 - 1]时，其实返回任意 nums[half -1]就可以，具体为什么没想清楚，只是从例子证实。所以我觉得
用 "如果相等就扔掉任意一半的思路反而更好理解". 
 [3，], [3,4]; k = 2, 第一次recursion中half1 = 1， half2 = 1.并且median确实是3
