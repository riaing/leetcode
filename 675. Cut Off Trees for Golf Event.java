You are asked to cut off trees in a forest for a golf event. The forest is represented as a non-negative 2D map, in this map:

0 represents the obstacle can't be reached.
1 represents the ground can be walked through.
The place with number bigger than 1 represents a tree can be walked through, and this positive number represents the tree's height.
 

You are asked to cut off all the trees in this forest in the order of tree's height - always cut off the tree with lowest height first. And after cutting, the original place has the tree will become a grass (value 1).

You will start from the point (0, 0) and you should output the minimum steps you need to walk to cut off all the trees. If you can't cut off all the trees, output -1 in that situation.

You are guaranteed that no two trees have the same height and there is at least one tree needs to be cut off.

Example 1:

Input: 
[
 [1,2,3],
 [0,0,4],
 [7,6,5]
]
Output: 6
 

Example 2:

Input: 
[
 [1,2,3],
 [0,0,0],
 [7,6,5]
]
Output: -1
 

Example 3:

Input: 
[
 [2,3,4],
 [0,0,5],
 [8,7,6]
]
Output: 6
Explanation: You started from the point (0,0) and you can cut off the tree in (0,0) directly without walking.
 

Hint: size of the given matrix will not exceed 50x50.
----------------------BFS-----------------------------------------------------------------------------------
// 先找到所有树的node，sort by height（pre processing）。再对每两个height，通过bfs找到最短路径。把所有树node间的最短路径加起来。
//Time o(m*n)^2 preprocessing is m*n, bfs每个node m*n。分析：这里最多50*50， 所以时间最大50^4 = 6.25* 10^6。一一秒可以算10^9，所以这个复杂度还行
// space： O（m*n） max is m*n nodes 
class Solution {
    class Tree {
        int height; 
        int row; 
        int col;
        public Tree(int height, int row, int col) {
            this.height = height;
            this.row = row;
            this.col = col;
        }
    }
    public int cutOffTree(List<List<Integer>> forest) {
        List<Tree> trees = new ArrayList<Tree>();
        //find all the trees, sort by height 
        // O(m*n )
        for (int i = 0; i< forest.size(); i++) {
            for (int j = 0; j < forest.get(0).size(); j++) {
                if (forest.get(i).get(j) > 1) {
                    trees.add(new Tree(forest.get(i).get(j), i, j));
                }
            }
        }
        /** sol1: complete comparator **/
        // Comparator<Tree> x = new Comparator<Tree>() {
        //     @Override
        //     public int compare(Tree o1, Tree o2) {
        //         return o1.height-o2.height;
        //     } 
        // };
        // Collections.sort(trees, x); 
        /** sol2: convenient comparator **/
        Collections.sort(trees, (a, b) -> Integer.compare(a.height, b.height));
        
        // for tree in heigher order, find the shortest distance between two
        // trees respectively 
        int res = 0;
        // start from (0, 0)
        int sr = 0;
        int sc = 0;
       
        for (Tree tree : trees) {
            int distance = bfs(forest, sr, sc, tree.row, tree.col);
            if (distance < 0) {
                return -1;
            }
            res += distance;
            sr = tree.row;
            sc = tree.col;
        }
        return res;  
    }
    // O(m*n) m -> row; n-> col 
    private int bfs(List<List<Integer>> forest, int sr, int sc, int tr, int tc) {
        boolean[][] visited = new boolean[forest.size()][forest.get(0).size()];
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.offer(new int[]{sr, sc});
        visited[sr][sc] = true;
        int[] row = {1,-1,0,0};
        int[] col = {0,0,1,-1};
        
        int step = 0; 
        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cur = queue.poll();
                //因为有可能sr=tr sc=tc，我们把判断提到每次取出元素后，并在for后while前更新step。
                //这样做就是多了一步check起始(0,0)的情况。
                if (cur[0] == tr && cur[1] == tc) {
                    return step;
                }
                for (int j = 0; j < 4; j++) {
                    int newRow = cur[0] + row[j];
                    int newCol = cur[1] + col[j];
                    if (newRow >= 0 && newRow < forest.size() && newCol >= 0 && newCol < forest.get(0).size() && !visited[newRow][newCol] && forest.get(newRow).get(newCol) != 0) {
                        queue.offer(new int[]{newRow, newCol});
                        visited[newRow][newCol] = true;
                    }
                }
            }
            //for(int i = 0; i < size; i++)
           step++;
        }
        return -1;
    }
}
