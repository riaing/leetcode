You are given a 0-indexed m x n binary matrix grid.

In one operation, you can choose any i and j that meet the following conditions:

0 <= i < m
0 <= j < n
grid[i][j] == 1
and change the values of all cells in row i and column j to zero.

Return the minimum number of operations needed to remove all 1's from grid.

 

Example 1:


Input: grid = [[1,1,1],[1,1,1],[0,1,0]]
Output: 2
Explanation:
In the first operation, change all cell values of row 1 and column 1 to zero.
In the second operation, change all cell values of row 0 and column 0 to zero.
Example 2:


Input: grid = [[0,1,0],[1,0,1],[0,1,0]]
Output: 2
Explanation:
In the first operation, change all cell values of row 1 and column 0 to zero.
In the second operation, change all cell values of row 2 and column 1 to zero.
Note that we cannot perform an operation using row 1 and column 1 because grid[1][1] != 1.
Example 3:


Input: grid = [[0,0],[0,0]]
Output: 0
Explanation:
There are no 1's to remove so return 0.
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 15
1 <= m * n <= 15
grid[i][j] is either 0 or 1.

----------------------- DFS 找最小 + bitmask 来记录matrix 状态----------------------------
/*
总：用的DFS思路，bitmask 就是通过bit来代表新的matrix。

DFS,对于每个1，可取可不取。取的话更新matrix，传个新的matrix，进行递归。
技巧1： 把matrix flatten成一维。对于 i,j -> i * n + j就是转换后的数。 恢复时对于k -> k / n = row, k %n = col
技巧2： 用32位数来代表新的matrix。 i,j位是1，就先算出i，j 变成1维的数k，然后state & 1 << k 就把这个数设成1 
当要把这个数变成0 时： 先取非，把位数变成0，然后再 & state： newState &= ~(1 << (k * n + j)); 

refer： http://leungyukshing.cn/archives/LeetCode%E8%A7%A3%E9%A2%98%E6%8A%A5%E5%91%8A%EF%BC%88519%EF%BC%89--%202174.%20Remove%20All%20Ones%20With%20Row%20and%20Column%20Flips%20II.html

*/
class Solution {
    int row;
    int col; 
    public int removeOnes(int[][] grid) {
        row = grid.length; 
        col = grid[0].length; 
        
        int lastNumInMatrx = grid.length * grid[0].length; 
        int[] memo = new int[1<<lastNumInMatrx]; 
        Arrays.fill(memo, Integer.MAX_VALUE); // 必须设为最大值
        int state = 0;
        // 把1记录到state里
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 1) {
                    int flatten = i * col + j;
                    state = state | (1 << flatten);  // 赋值
                }
            }
        }
        return dfs(state, memo);  // 传matrix进去
    }
    
    private int dfs(int state, int[] memo) {
        if (state == 0) { // base case, 如果matrix里全是0，说明不用flip
            return 0; 
        }
        if (memo[state] != Integer.MAX_VALUE) { 
            return memo[state]; 
        }
        
        // 对每一个可用的1进行dfs。这时当前matrix的状态之前可能已经找到解了，这里要取min
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int flatten = i * col + j; 
                if ((state & (1 << flatten)) > 0) {// 当前cell是1，说明可以进行dfs 
                    int newState = state; 
                    // 1. 把当前行和列的1全部变0 
                    // 先变行。循环列
                    for (int k = 0; k < col; k++) {
                        flatten = i * col + k; 
                        newState = newState & (~(1 << flatten)); // 先把这位的1 变成0， 再 & 就得0 
                    }
                    // 再变列
                    for (int k = 0; k < row; k++) {
                        flatten = k * col + j;
                        newState = newState & (~(1 << flatten)); 
                    }
                    // 2. 往下dfs
                    int curRes = 1 + dfs(newState, memo);
                    // 3. 当前matrix的状态之前可能已经找到解了，这里就要取min更新
                    memo[state] = Math.min(memo[state], curRes); 
                }
            }
        }
        return memo[state];
    }
}

----------------------------- BFS  空间要大些 ------------------
/*
总：用的DFS思路，bitmask 就是通过bit来代表新的matrix。

DFS,对于每个1，可取可不取。取的话更新matrix，传个新的matrix，进行递归。
技巧1： 把matrix flatten成一维。对于 i,j -> i * n + j就是转换后的数。 恢复时对于k -> k / n = row, k %n = col
技巧2： 用32位数来代表新的matrix。 i,j位是1，就先算出i，j 变成1维的数k，然后state & 1 << k 就把这个数设成1 
当要把这个数变成0 时： 先取非，把位数变成0，然后再 & state： newState &= ~(1 << (k * n + j)); 

refer： http://leungyukshing.cn/archives/LeetCode%E8%A7%A3%E9%A2%98%E6%8A%A5%E5%91%8A%EF%BC%88519%EF%BC%89--%202174.%20Remove%20All%20Ones%20With%20Row%20and%20Column%20Flips%20II.html

Time: {min(n,m)}! 比如行比列小，最多灭n行就行了，所以对于第一次有n个选择，第二次n-1个选择。。。阶乘
技巧总结： 
int[] memo = new int[1<<行*列]; 
位置压缩成1维： i * 列 + j 
1维变回行： k / 列
1维变回列： k % 列
把某个位置变为0：取非再and： & ~位置
*/


class Node {
    int pos; // 当前的position 
    int matrix;  // 当前状态下的matrix
    public Node(int pos, int matrix) {
        this.pos = pos;
        this.matrix = matrix; 
    }
}

class Solution {
    int col;
    int row; 
    public int removeOnes(int[][] grid) {
        this.col = grid[0].length;
        this.row = grid.length; 
        
        int initialMatrix = 0; // 存最开始的有1的matrix
        Queue<Node> q = new LinkedList<Node>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    int position = i * col + j; 
                    initialMatrix |= (1 << position);  // 把matrix这行设为1
                    q.offer(new Node(position, -1)); 
                }
            }
        }
        // corner case 
        if (initialMatrix == 0) {
            return 0; 
        }
        
        // 给q中的每个值加入initial matrix
        int size = q.size();
        for (int i = 0; i < size; i++) {
            Node n = q.poll();
            n.matrix = initialMatrix; 
            q.offer(n);
        }
        
        // BFS , 对于每一个点，把行列的1消掉后，把matrix剩下的1和当前matrix状态放进queue
        int steps = 0;
        while (!q.isEmpty()) {
            size = q.size(); 
            for (int k = 0; k < size; k++) {
                Node cur = q.poll();
                // // 1. 查当前matrix是否是最终状态 // 不需要这几行，因为q中必须是有1的matrix，所以刚进来时不可能是全0
                // if (cur.matrix == 0) {
                //     return steps;
                // }
                
                // 2. 将matrix当前行列变成0 + 判断
                changeToZero(cur);
                if (cur.matrix == 0) {
                    return ++steps; 
                }
                // 3. 把matrix剩下1的位置加入q
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        int nextPos = i * col + j;
                        if ((cur.matrix & (1 << nextPos)) > 0) {
                            q.offer(new Node(nextPos, cur.matrix));
                        }
                    }
                }
                
            }
            steps++;
        }
        return steps; 
    }
    
    // 更改matrix 状态 
    private void changeToZero(Node n) {
        int curRow = n.pos / col;
        int curCol = n.pos % col;
        int matrix = n.matrix;
        // 1. 先change 列
        for (int i = 0; i < row; i++) {
            int changePos = i * col + curCol; 
            matrix &=  ~(1 << changePos); 
        }
        // 2. change 行
        for (int j = 0; j < col; j++) {
            int changePos = curRow * col + j;
            matrix &= ~(1<< changePos);
        }
        n.matrix = matrix; 
    }
} 
