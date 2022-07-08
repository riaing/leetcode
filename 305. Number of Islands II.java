You are given an empty 2D binary grid grid of size m x n. The grid represents a map where 0's represent water and 1's represent land. Initially, all the cells of grid are water cells (i.e., all the cells are 0's).

We may perform an add land operation which turns the water at position into a land. You are given an array positions where positions[i] = [ri, ci] is the position (ri, ci) at which we should operate the ith operation.

Return an array of integers answer where answer[i] is the number of islands after turning the cell (ri, ci) into a land.

An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

 

Example 1:


Input: m = 3, n = 3, positions = [[0,0],[0,1],[1,2],[2,1]]
Output: [1,1,2,3]
Explanation:
Initially, the 2d grid is filled with water.
- Operation #1: addLand(0, 0) turns the water at grid[0][0] into a land. We have 1 island.
- Operation #2: addLand(0, 1) turns the water at grid[0][1] into a land. We still have 1 island.
- Operation #3: addLand(1, 2) turns the water at grid[1][2] into a land. We have 2 islands.
- Operation #4: addLand(2, 1) turns the water at grid[2][1] into a land. We have 3 islands.
Example 2:

Input: m = 1, n = 1, positions = [[0,0]]
Output: [1]
 

Constraints:

1 <= m, n, positions.length <= 104
1 <= m * n <= 104
positions[i].length == 2
0 <= ri < m
0 <= ci < n
 

Follow up: Could you solve it in time complexity O(k log(mn)), where k == positions.length?


--------------------- Union Find ------------------
/*二维坐标 (x,y) 可以转换成 x * n + y 这个数（m 是棋盘的行数，n 是棋盘的列数），敲黑板，这是将二维坐标映射到一维的常用技巧。

UF： count与模板的设定不一样
时间：build UF：O（mn），遍历position：k * O(1) ->find, union 都是O（1） =》 O（mn+k）

follow up的 O(k log(mn))是因为没通过 path compression 的UF中，union/find 为lgmn。
*/


class UF {
    private int count;
    private int[] parent; 
    public UF(int n) { // O(N)
        this.count = 0; // 本题一开始没岛
        this.parent = new int[n];
        Arrays.fill(parent, -1); // 给个dummy parent
    }
    
    public void union(int p, int q) { 
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) {
            return;
        }
        parent[pRoot] = qRoot;
        count--;
    }
    
    public int find(int x) { // O(1), 最差O（lgN）
        if (x != parent[x]) {
            parent[x] = find(parent[x]); // path compression
        }
        return parent[x]; 
    }
    public int count() {
        return count; 
    }
    
    // for this problem
    public void setParent(int x) {
        parent[x] = x;
        count++; 
    }
    
    public boolean isIsland(int x) {
        return parent[x] != -1;
    }
    

}

class Solution {
    UF uf;
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        this.uf = new UF(m*n); 
        
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {
            int[] cur = positions[i];
            int curPos = cur[0] * n + cur[1];
            // 0. corner case: input有重复值，直接return结果
            if (uf.isIsland(curPos)) {
                res.add(uf.count());
                continue;
            }
            // 1. 给当前set parent为自己
            uf.setParent(curPos);
            // 2. 找四个neibor看能否union
            int[] row = {1, -1, 0,0};
            int[] col = {0, 0, 1, -1};
            for (int k = 0; k < row.length; k++) {
                int[] neibor = {cur[0] + row[k], cur[1] + col[k]};
                int neiborPos = neibor[0] * n + neibor[1];
                if (neibor[0] >= 0 && neibor[0] < m && neibor[1] >= 0 && neibor[1] < n && uf.isIsland(neiborPos)) {
                    // 把当前和neibor union
                    uf.union(curPos, neiborPos);
                }
            }
            // 3. 更新岛数量
            res.add(uf.count());
        }
        return res; 
    }
    
}
