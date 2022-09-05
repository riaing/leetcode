Design a Leaderboard class, which has 3 functions:

addScore(playerId, score): Update the leaderboard by adding score to the given player's score. If there is no player with such id in the leaderboard, add him to the leaderboard with the given score.
top(K): Return the score sum of the top K players.
reset(playerId): Reset the score of the player with the given id to 0 (in other words erase it from the leaderboard). It is guaranteed that the player was added to the leaderboard before calling this function.
Initially, the leaderboard is empty.

 

Example 1:

Input: 
["Leaderboard","addScore","addScore","addScore","addScore","addScore","top","reset","reset","addScore","top"]
[[],[1,73],[2,56],[3,39],[4,51],[5,4],[1],[1],[2],[2,51],[3]]
Output: 
[null,null,null,null,null,null,73,null,null,null,141]

Explanation: 
Leaderboard leaderboard = new Leaderboard ();
leaderboard.addScore(1,73);   // leaderboard = [[1,73]];
leaderboard.addScore(2,56);   // leaderboard = [[1,73],[2,56]];
leaderboard.addScore(3,39);   // leaderboard = [[1,73],[2,56],[3,39]];
leaderboard.addScore(4,51);   // leaderboard = [[1,73],[2,56],[3,39],[4,51]];
leaderboard.addScore(5,4);    // leaderboard = [[1,73],[2,56],[3,39],[4,51],[5,4]];
leaderboard.top(1);           // returns 73;
leaderboard.reset(1);         // leaderboard = [[2,56],[3,39],[4,51],[5,4]];
leaderboard.reset(2);         // leaderboard = [[3,39],[4,51],[5,4]];
leaderboard.addScore(2,51);   // leaderboard = [[2,51],[3,39],[4,51],[5,4]];
leaderboard.top(3);           // returns 141 = 51 + 51 + 39;
 

Constraints:

1 <= playerId, K <= 10000
It's guaranteed that K is less than or equal to the current number of players.
1 <= score <= 100
There will be at most 1000 function calls.
  
  
  --------- TreeMap --------
  /*
Heap做法：
在每次top()时，建一个新的min heap size = k。iterate over Map把每个player加到heap里，pop出堆顶（最小）元素。
Time： top： O(nlogK) - 每个元素都要进heap
       其他：O（1）

如果要求其他操作时间小：用heap
如果要求top小：用treeMap

*/
class Leaderboard {
    Map<Integer, Integer> playerMap;
    TreeMap<Integer, Set<Integer>> scoreMap; // 提高：只记录count，不care 具体player的分数

    public Leaderboard() {
        this.playerMap = new HashMap<>();
        this.scoreMap = new TreeMap<>();
    }
    
    public void addScore(int playerId, int score) { // O(lgN)
        int oldScore = 0; 
        if (playerMap.containsKey(playerId)) {
            oldScore = playerMap.get(playerId);
            removeFromScoreMap(playerId, oldScore);
        }
        int newScore = oldScore + score;
        scoreMap.putIfAbsent(newScore, new HashSet<>());
        scoreMap.get(newScore).add(playerId);
        // update playermap 
        playerMap.put(playerId, newScore);
        
    }
    
    public int top(int K) { // O(KlgN). 如scoreMap只记录count，可以做到O（N） 
        Set<Integer> keySet = scoreMap.descendingKeySet();
        int res = 0; 
        for (Integer score: keySet) {
            if (K > 0) {
                int count = Math.min(K, scoreMap.get(score).size());
                res += score * count;
                K -= count;
            }
        }
        return res; 
    }
    
    public void reset(int playerId) {  // O(lgN)
        int oldScore = playerMap.get(playerId);
        removeFromScoreMap(playerId, oldScore);
        playerMap.remove(playerId); // 注意：playerMap和scoreMap必须同删同加entry，来保证一个player在两个map中consistent
    }
    
    private void removeFromScoreMap(int playerId, int score) {
        scoreMap.get(score).remove(playerId);
        if (scoreMap.get(score).isEmpty()) {
            scoreMap.remove(score);
        }
    }
}

/**
 * Your Leaderboard object will be instantiated and called as such:
 * Leaderboard obj = new Leaderboard();
 * obj.addScore(playerId,score);
 * int param_2 = obj.top(K);
 * obj.reset(playerId);
 */
