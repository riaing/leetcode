class Solution {
    int N;
    public int snakesAndLadders(int[][] board) {
        N = board.length;
        
        int res = 0;
        Queue<Integer> q = new LinkedList<Integer>();
        q.offer(1);
        int cur = 0;
        Set<Integer> visited = new HashSet<Integer>();
        visited.add(1);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                cur = q.poll();
                if (cur == N*N) {
                    System.out.println(cur);
                    return res;
                }
               
                for (int square =  cur + 1; square <= Math.min(cur + 6, N*N); square++) {
                     int [] coordinate = getCoordinate(square);
                     int realSquare = board[coordinate[0]][coordinate[1]] == -1 ? square : board[coordinate[0]][coordinate[1]];
                 
                    if (!visited.contains(realSquare)) {
                         q.offer(realSquare);
                 
                        visited.add(realSquare);
                    }
                }
            }
            res++;
        }
        return -1;
    }
    
    
    // 计算
    private int[] getCoordinate(int num) {
        int quotient = (num-1) / N;
        int reminder = (num-1) % N;
        int row = N-1-quotient;
        int col = (row+1) % 2 == N % 2 ? reminder : N-1-reminder;  // 注意这里的计算
        
        int[] res = {row, col};
        return res;
    }
}
