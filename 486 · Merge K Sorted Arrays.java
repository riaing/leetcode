Description
Given k sorted integer arrays, merge them into one sorted array.

Contact me on wechat to get Amazon、Google requent Interview questions . (wechat id : jiuzhang15)


Example
Example 1:

Input: 
  [
    [1, 3, 5, 7],
    [2, 4, 6],
    [0, 8, 9, 10, 11]
  ]
Output: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
Example 2:

Input:
  [
    [1,2,3],
    [1,2]
  ]
Output: [1,1,2,2,3]
Challenge
Do it in O(N log k).

N is the total number of integers.
k is the number of arrays.

-------------------------------- heap ---------------------------------------------
class Pair {
    int row;
    int col;

    public Pair (int row, int col) {
        this.row = row;
        this.col = col;
    }
}

public class Solution {
    /**
     * @param arrays: k sorted integer arrays
     * @return: a sorted array
     */
    public int[] mergekSortedArrays(int[][] arrays) {
        List<Integer> res = new ArrayList<>();
        PriorityQueue<Pair> q = new PriorityQueue<>((a, b) -> Integer.compare(arrays[a.row][a.col], arrays[b.row][b.col]));
        // 1. put first element 
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i].length != 0) {
                q.offer(new Pair(i, 0));
            }
        }
        // 2. go through q and add next 
        while (!q.isEmpty()) {
            Pair cur = q.poll();
            res.add(arrays[cur.row][cur.col]);
            // if has next, add into q 
            if (cur.col < arrays[cur.row].length - 1) {
                q.offer(new Pair(cur.row, cur.col + 1)); 
            }
        }
        int[] myArray = new int[res.size()];
        for (int i = 0; i < myArray.length; i++) {
            myArray[i] = res.get(i);
        }
        return myArray; 
    }
}
