There is a rectangular brick wall in front of you with n rows of bricks. The ith row has some number of bricks each of the same height (i.e., one unit) but they can be of different widths. The total width of each row is the same.

Draw a vertical line from the top to the bottom and cross the least bricks. If your line goes through the edge of a brick, then the brick is not considered as crossed. You cannot draw a line just along one of the two vertical edges of the wall, in which case the line will obviously cross no bricks.

Given the 2D array wall that contains the information about the wall, return the minimum number of crossed bricks after drawing such a vertical line.

 

Example 1:


Input: wall = [[1,2,2,1],[3,1,2],[1,3,2],[2,4],[3,1,2],[1,3,1,1]]
Output: 2
Example 2:

Input: wall = [[1],[1],[1]]
Output: 3
 
  
  Constraints:

n == wall.length
1 <= n <= 104
1 <= wall[i].length <= 104
1 <= sum(wall[i].length) <= 2 * 104
sum(wall[i]) is the same for each row i.
1 <= wall[i][j] <= 231 - 1
  
  ------------------------- preSum -----------------
  /*
用prefix sum代表砖的位子，
*/
class Solution {
    public int leastBricks(List<List<Integer>> wall) {
        int edgeLen = 0; // total width
        // map. key- preSum, val- freq
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (List<Integer> w : wall) {
            int sum = 0;
            for (int i : w) {
                sum += i;
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
            edgeLen = sum; // 拿到一行有多长
        }
        
        // corner case, 每行只有一块砖
        if (map.size() == 1) {
            return map.get(edgeLen);
        }
        // find 位子which has most 砖,在这里砍伤害最小。edgeLen-得滤掉因为不能cut at edge
        int maxBricksEnds = 0; 
        for (int key : map.keySet()) {
            if (key == edgeLen) {
                continue;
            }
            maxBricksEnds = Math.max(maxBricksEnds, map.get(key));
         
        }
        int row = wall.size();
        return row - maxBricksEnds;
        
    }
}
