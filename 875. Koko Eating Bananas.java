Koko loves to eat bananas.  There are N piles of bananas, the i-th pile has piles[i] bananas.  The guards have gone and 
will come back in H hours.

Koko can decide her bananas-per-hour eating speed of K.  Each hour, she chooses some pile of bananas, and eats K bananas
from that pile.  If the pile has less than K bananas, she eats all of them instead, and won’t eat any more bananas during 
this hour.

Koko likes to eat slowly, but still wants to finish eating all the bananas before the guards come back.

Return the minimum integer K such that she can eat all the bananas within H hours.

 

Example 1:

Input: piles = [3,6,7,11], H = 8
Output: 4
Example 2:

Input: piles = [30,11,23,4,20], H = 5
Output: 30
Example 3:

Input: piles = [30,11,23,4,20], H = 6
Output: 23
 

Note:

1 <= piles.length <= 10^4
piles.length <= H <= 10^9
1 <= piles[i] <= 10^9

---------------------------------------------------------------------------------------------------------------------
/**
bineray search, 关键是确定min和max，我们猜hour，min=1-总要吃；max的话，因为题目中说吃完一串后那个小时就不动了，所以max可以是piles中最大的数。

time:O(n*logW) W-> W is the maximum size of a pile.
space: O(1)
**/
class Solution {
    public int minEatingSpeed(int[] piles, int H) {
        int min = 1; // have to eat a banana 
        //find max of pile -- o(n)
        int max = 0;
        for (int num : piles) {
            max = Math.max(max, num);
        }
       
        
        while (min < max) {
            int mid = min + (max - min) / 2;
            // 如果当前hour是可以的，那么我们继续找他的bound
            if (hoursNeedAtK(piles, mid) <= H) {
                max = mid;
            }
            else{
                min = mid + 1;
            }
        }
        return min;  
    }
    
    // O(n)
    // 这里其实是求ceil(num/k),但因为Math.ceil(double),省得麻烦。所以也可以用 num - 1 / k + 1来表示ceil的值。具体证明可想两种情况，当num = nk（k的倍数), Math.ceiling(num/k) = n, num-1/k+1 = n; 当num = nk+m where 1 < m < k时(就是m是一个不满k的数)，同样发现 Math.ceiling(num/k) = n, num-1/k+1 = n;
    private int hoursNeedAtK(int[] piles, int K) {
        int cnt = 0;
        for (int num : piles) {
            cnt += num % K == 0 ? num / K : num / K + 1;
        }   
        return cnt;
    }
}
