/*You are the in-charge of the planning of the reconstruction of your city. As per the guidelines, you need to ensure that all the buildings in the city are of the same height, which is defined by the number of floors. You are aware of the current heights of all the buildings. To make all buildings of equal height, you can remove some of the floors from any of the buildings. Post removal, if there are no floors left in the building, then the plot will be used for community services (car parking, playing area, etc).

Given the number of floors in each building, devise an algorithm to make the building heights equal in such a way that the total number of floors removed from the city is minimized.
*/

/*For example:

Let there be 3 buildings in the city with 7, 3, and 5 floors respectively. We can remove 2 floors from the first building leaving it with 5 floors. We can then remove all the 3 floors from the second building leaving the plot for community services. After removing the floors, we will be left with two buildings each with 5 floors. The number of floors removed in this case is 2 + 3 = 5.

4 + 2 = 6.

7 , 5 => 1) 7 - 5;  2) 5,5, - 2  

3, 5, 7 => 

3, 5, 7, 9, 11, 13 
1) Keep everything: 0 + 2 + 4 + 6 + 8 + 10 = 30 

2) Only remove 3 (final # floors: 5): 3 + 0 + 2 + 4 + 6 + 8 = 23 

3) remove 3, 5 (final # floors: 7):  3 + 5 + 0 + 2 + 4 + 6 = 20

remove first i building: Sum  (height 0...i-1) + {i+1,....n} - time* h[i] 

n = height.length - 1
height.length - 1 - (i + 1) + 1 = height.length - 1 - i - 1 + 1 = height.length - 1 - i

[a,b] = b - a + 1
*/


思路：choose certain building as final height. buiding before it: all remove ; building after it: all cut the height 
/* Time: nlgn + n = nlgn 
Space: o(n) 
*/

public int minRemoval(int[] heights) {
    // 0. sort by ascending 
    Arrays.sort(heights, (a, b) -> a - b); // nlgn 
    
    // 1. preSum 
    int[] preSum = new int[heights + 1];  // o(n)
    preSum[0] = 0; 
    for (int i = 0; i < heights.length; i++) {
        preSum[i+1] = preSum[i] + heights[i]; 
    }
    
    int res = Integer.MAX_VAULE; 
    // 2. iterate every building 
    for (int i = 0; i < heights.length; i++) {
        
        int curHeight = heights[i];
        // remove 0...i-1th building 
        int curCost = preSum[i] + （preSum[preSum.length] - preSum[i+1] - curHeight * (heights.length - i-1)）; 
        res = Math.min(res, curCost);
    }
    
    // remove everything: presum[length] - won't be the optimal solution ! just notes 
    
    return res; 
}


// 3, 5,7;  5 + 7 - 3 *2 = 

preSum:    0, 3, 8, 15 
curHeight: 3
curCost (i=0): 0 + 15 - 3 - 3 * 2 = 12 - 6 = 6;  // keep : 2 + 4 = 6 
curCost (i=1): 3 + 15 - 8 - 5 * 1 = 5;  // remove first: 3 + 0 + 2 = 5 
curCost (i=2): 8 + 15 - 15 - 7 * 0 = 8;





