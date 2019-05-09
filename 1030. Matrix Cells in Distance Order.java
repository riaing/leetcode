We are given a matrix with R rows and C columns has cells with integer coordinates (r, c), where 0 <= r < R and 0 <= c < C.

Additionally, we are given a cell in that matrix with coordinates (r0, c0).

Return the coordinates of all cells in the matrix, sorted by their distance from (r0, c0) from smallest distance to largest distance.  Here, the distance between two cells (r1, c1) and (r2, c2) is the Manhattan distance, |r1 - r2| + |c1 - c2|.  (You may return the answer in any order that satisfies this condition.)

 

Example 1:

Input: R = 1, C = 2, r0 = 0, c0 = 0
Output: [[0,0],[0,1]]
Explanation: The distances from (r0, c0) to other cells are: [0,1]
Example 2:

Input: R = 2, C = 2, r0 = 0, c0 = 1
Output: [[0,1],[0,0],[1,1],[1,0]]
Explanation: The distances from (r0, c0) to other cells are: [0,1,1,2]
The answer [[0,1],[1,1],[0,0],[1,0]] would also be accepted as correct.
Example 3:

Input: R = 2, C = 3, r0 = 1, c0 = 2
Output: [[1,2],[0,2],[1,1],[0,1],[1,0],[0,0]]
Explanation: The distances from (r0, c0) to other cells are: [0,1,1,2,2,3]
There are other answers that would also be accepted as correct, such as [[1,2],[1,1],[0,2],[1,0],[0,1],[0,0]].
 

Note:

1 <= R <= 100
1 <= C <= 100
0 <= r0 < R
0 <= c0 < C
--------------------1, treeMap, get等操作都是 lgn, 总时间  O(R*C lg(R+C))-----------------------------------------------------------------------
class Solution {
  public int[][] allCellsDistOrder(int R, int C, int r0, int c0) {
        Map<Integer, List<int[]>> map = new TreeMap<Integer, List<int[]>>();

        for (int i = 0; i < R; i++) {
          for (int j = 0; j < C; j++) {
            int distance = findDistance(i, j, r0, c0);
            int[] coordinate = {i,j};
            if (!map.containsKey(distance)) {  
              map.put(distance, new ArrayList<int[]>()); // put is lg(R+C)
            }
            map.get(distance).add(coordinate); // O(R*C lg(R+C))
          }
        }

        int[][] result = new int[R*C][2];
        int index = 0; 
        for (Integer key : map.keySet()) {
          List<int[]> values = map.get(key);
          for (int i = 0; i < values.size(); i++) {
            int[] cell = values.get(i);
            result[index][0] = cell[0];
            result[index][1] = cell[1];
            index++;
          }
        }

        return result; 
    }

    private int findDistance(int curRow, int curCol, int iniRow, int iniCol) {
      return Math.abs(curRow - iniRow) + Math.abs(curCol - iniCol);
    }
}

------------- bucket sort ------------------------------------
/**
根据提示，最大的distance为100-0+100-0=200，数字比较小，所以提示我们可以用bucket sort。
每个bucket的index为distance，value就是这个distance对应的pair coordinate。 
O(n)
*/
class Solution {
  public int[][] allCellsDistOrder(int R, int C, int r0, int c0) {
      List<int[]>[] bucket = new List[R+C];
      int[] a = new int[2];
      
        for (int i = 0; i < R; i++) {
          for (int j = 0; j < C; j++) {
            int distance = findDistance(i, j, r0, c0);
            int[] coordinate = {i,j};
            if (bucket[distance] == null) {
                bucket[distance] = new ArrayList<int[]>();
            }
            bucket[distance].add(coordinate);
          }
        }

        int[][] result = new int[R*C][2];
        int index = 0;
        for (int k = 0; k < bucket.length; k++) {
          if (bucket[k] == null) {
              continue;
          }
            
          for (int i = 0; i < bucket[k].size(); i++) {
            int[] cell =  bucket[k].get(i);
            result[index][0] = cell[0];
            result[index][1] = cell[1];
            index++;
          }
        }

        return result; 
    }

    private int findDistance(int curRow, int curCol, int iniRow, int iniCol) {
      return Math.abs(curRow - iniRow) + Math.abs(curCol - iniCol);
    }
}
