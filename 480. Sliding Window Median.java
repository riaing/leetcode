The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle values.

For examples, if arr = [2,3,4], the median is 3.
For examples, if arr = [1,2,3,4], the median is (2 + 3) / 2 = 2.5.
You are given an integer array nums and an integer k. There is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.

Return the median array for each window in the original array. Answers within 10-5 of the actual value will be accepted.

 

Example 1:

Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [1.00000,-1.00000,-1.00000,3.00000,5.00000,6.00000]
Explanation: 
Window position                Median
---------------                -----
[1  3  -1] -3  5  3  6  7        1
 1 [3  -1  -3] 5  3  6  7       -1
 1  3 [-1  -3  5] 3  6  7       -1
 1  3  -1 [-3  5  3] 6  7        3
 1  3  -1  -3 [5  3  6] 7        5
 1  3  -1  -3  5 [3  6  7]       6
Example 2:

Input: nums = [1,2,3,4,2,3,1,4,2], k = 3
Output: [2.00000,3.00000,3.00000,3.00000,2.00000,3.00000,2.00000]
 

Constraints:

1 <= k <= nums.length <= 105
-231 <= nums[i] <= 231 - 1
---------------------------- 2 heap ----------------------------------------------------------------------

/*
每次 loop 中 remove certain element 是 O（k）， add/balance 是 log（k）。总共要找 n-k+1次 meadian。所以整体是 (n-k+1)* (lgk + k) -> O(nk)

Space: Ignoring the space needed for the output array, the space complexity will be O(K)O(K) because, at any time, we will be storing all the numbers within the sliding window. 
*/
class Solution {
    public double[] medianSlidingWindow(int[] nums, int k) {
        List<Double> results = new ArrayList<Double>();

        PriorityQueue<Integer> firstQ = new PriorityQueue<>((a,b) -> Double.compare(b,a)); // store the first part from big -> small 
        PriorityQueue<Integer> secondQ = new PriorityQueue<>(); //store the second part from small -> big 
                
        for (int n = 0; n < k-1; n++) { // 首先把 k-1个 num 加进去
            addNum(firstQ, secondQ, nums[n]);
        }
        
        for (int i = 0, j = k-1; j < nums.length; i++, j++) { 
            // add the last num in queue and get median
            addNum(firstQ, secondQ, nums[j]);
            if (k %2 == 0) {
                double med = firstQ.peek() / 2.0  + secondQ.peek() / 2.0;  // 注意 overflow
                results.add(med);
            }
            else {
                results.add(firstQ.peek()*1.0);
            }
            
            // remove the first num in window
            int numToRemove = nums[i];
            if (numToRemove > firstQ.peek()) {
                secondQ.remove(numToRemove); // 要先找，然后再 remove o(k)
            }
            else {
                firstQ.remove(numToRemove);
            }
            balanceQ(firstQ, secondQ);
        }
        
        double[] returnVal = new double[results.size()];
        for (int index = 0; index < results.size(); index++) {
            returnVal[index] = results.get(index);
        }
        return returnVal;
        
    }
    
    private void addNum(PriorityQueue<Integer> firstQ, PriorityQueue<Integer> secondQ, int num) { // o(lgk)
        if (firstQ.size() == 0 || num <= firstQ.peek()) { // firstQ == 0 就是第一次加数
            firstQ.offer(num);
        }
        else {
            secondQ.offer(num);
        }     
        balanceQ(firstQ, secondQ);
        return;
    }
    
    
    // maintain the Queues as firstQ's size - secondQ’s size <= 1
    private void balanceQ(PriorityQueue<Integer> firstQ, PriorityQueue<Integer> secondQ) { //o(lgk)
        while (firstQ.size() - secondQ.size() > 1) {
            secondQ.offer(firstQ.poll());
        }
        while (secondQ.size() - firstQ.size() > 0) {
            firstQ.offer(secondQ.poll());
        }
        return;
    }
    
}
