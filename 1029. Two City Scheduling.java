-------------------------------数学分析--------------
/*
coding: 如果每人都去cost最小的话，算出多少人去A/B, 以及都去最小城市的cost。接下来移动多的人数直到
去两个城市的人数相同：如果算出去A的比较多，则把去A的人中距离最小的算出来
*/
class Solution {
    public int twoCitySchedCost(int[][] costs) {
        // Sort by abs diff of A and B 
        Comparator<int[]> comparator = new Comparator<int[]>() {
            @Override 
            public int compare(int[] a, int[] b) {
                return Integer.compare(Math.abs(a[0] - a[1]), Math.abs(b[0] - b[1]));
            }
        };
        Arrays.sort(costs, comparator);
        
        // count the number of putting everyone into the smallers cost city 
        int numOfA = 0; 
        int numOfB = 0;
        int res = 0; 
        for (int i = 0; i < costs.length; i++) {
            if (costs[i][0] == costs[i][1]) {
                res+= costs[i][1];
            }
            else if (costs[i][0] > costs[i][1]) {
                numOfB++;
                res += costs[i][1];
            }
            else {
                numOfA++;
                res += costs[i][0];
            }
              
        }
        return adjustCost(Math.abs(numOfA - numOfB) / 2, costs, res, numOfA - numOfB > 0);
    }
    
    private int adjustCost(int numToTransfer, int[][] costs, int res, boolean AmoreThanB) {
            for (int i = 0; i < costs.length; i++) {
                if (numToTransfer == 0) {
                    break;
                }
                
                boolean move = AmoreThanB ? costs[i][0] < costs[i][1] : costs[i][0] > costs[i][1];                
                if (move) {
                    res += Math.abs(costs[i][0] - costs[i][1]);
                    numToTransfer--;
                   
                }
               
            }
        return res; 
        
    }
}
--------------------------------dp ------------------------------------
f[i][j]: i+j个人中有i个人去A，j个人去B城市的min cost
class Solution {
    public int twoCitySchedCost(int[][] costs) {
        int N = costs.length / 2;
        int[][] f = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            f[i][0] = f[i - 1][0] + costs[i - 1][0];
        }
        for (int j = 1; j <= N; j++) {
            f[0][j] = f[0][j - 1] + costs[j - 1][1];
        }
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                f[i][j] = Math.min(f[i - 1][j] + costs[i + j - 1][0], f[i][j - 1] + costs[i + j - 1][1]);
            }
        }
        return f[N][N];
    }
}
