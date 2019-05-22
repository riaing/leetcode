Suppose you are at a party with n people (labeled from 0 to n - 1) and among them, there may exist one celebrity. The definition of a celebrity is that all the other n - 1 people know him/her but he/she does not know any of them.

Now you want to find out who the celebrity is or verify that there is not one. The only thing you are allowed to do is to ask questions like: "Hi, A. Do you know B?" to get information of whether A knows B. You need to find out the celebrity (or verify there is not one) by asking as few questions as possible (in the asymptotic sense).

You are given a helper function bool knows(a, b) which tells you whether A knows B. Implement a function int findCelebrity(n). There will be exactly one celebrity if he/she is in the party. Return the celebrity's label if there is a celebrity in the party. If there is no celebrity, return -1.

 

Example 1:


Input: graph = [
  [1,1,0],
  [0,1,0],
  [1,1,1]
]
Output: 1
Explanation: There are three persons labeled with 0, 1 and 2. graph[i][j] = 1 means person i knows person j, otherwise graph[i][j] = 0 means person i does not know person j. The celebrity is the person labeled as 1 because both 0 and 2 know him but 1 does not know anybody.
Example 2:


Input: graph = [
  [1,0,1],
  [1,1,0],
  [0,1,1]
]
Output: -1
Explanation: There is no celebrity.
 

Note:

The directed graph is represented as an adjacency matrix, which is an n x n matrix where a[i][j] = 1 means person i knows person j while a[i][j] = 0 means the contrary.
Remember that you won't have direct access to the adjacency matrix.

--------------图:记录入度和出度-O（n^2）0------------------------------------------------------
/* The knows API is defined in the parent class Relation.
      boolean knows(int a, int b); */

/*
找到一个出度为零并且入度为n-1的人 。 */
public class Solution extends Relation {
    public int findCelebrity(int n) {
        Map<Integer, Integer> outdegree = new HashMap<Integer, Integer>();
        Map<Integer, Integer> indegree = new HashMap<Integer, Integer>();
        for (int i = 0; i < n; i++) {
            outdegree.put(i, 0);
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && this.knows(i, j)) {
                    // increase i's outdegree
                    outdegree.put(i, outdegree.get(i) + 1);
                    indegree.put(j, indegree.getOrDefault(j, 0) + 1);
                }
            }
        }
        //条件是说别人都认识他但他一个人都不认识，所以说只能有一个key的出度为0
        //如果有几个key的出度为0，说明这几个人一个都不认识并且也不是名人
        for (Integer key : outdegree.keySet()) {
            System.out.println("key " + key + "cnt " + indegree.get(key));
            if (outdegree.get(key) == 0 && indegree.containsKey(key) && indegree.get(key) == n-1) {
                return key;
            }
        }
        return -1;
        
    }
}

------------逻辑，对于不是名人的i，直接break掉 O（n^2）------------------------------------------------------------
我们其实可以不用一维数组来标记每个人的状态，我们对于不是名人的i，直接 break，继续检查下一个，但是由于我们没有标记后面的候选人的状态，
所以有可能会重复调用一些 knows 函数，所以下面这种方法虽然省了空间，但是调用 knows 函数的次数可能会比上面的方法次数要多

class Solution {
public:
    int findCelebrity(int n) {
        for (int i = 0, j = 0; i < n; ++i) {
            for (j = 0; j < n; ++j) {
                if (i != j && (knows(i, j) || !knows(j, i))) break;
            }
            if (j == n) return i;
        }
        return -1;
    }
};

-----------------------------------优化，O（n）--------------------------
/* 设定候选人 res 为0，原理是先遍历一遍，对于遍历到的人i，若候选人 res 认识i，则将候选人 res 设为i，完成一遍遍历后，我们来检测候选人 res 是否真正是名人，我们如果判断不是名人，则返回 -1，如果并没有冲突，返回 res，
*/

public class Solution extends Relation {
    public int findCelebrity(int n) {
       int res = 0; 
        for (int i = 0; i < n; i++) {
            if (knows(res, i)) {
                res = i;
            }
        }
        // 反向，看res是不是真名人
        for (int i = 0; i < n; i++) {
            if (res != i && (!knows(i, res) || knows(res, i))) {
                 return -1;
            }
           
        }
        return res; 
        
    }
}
