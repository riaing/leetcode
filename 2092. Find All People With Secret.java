You are given an integer n indicating there are n people numbered from 0 to n - 1. You are also given a 0-indexed 2D integer array meetings where meetings[i] = [xi, yi, timei] indicates that person xi and person yi have a meeting at timei. A person may attend multiple meetings at the same time. Finally, you are given an integer firstPerson.

Person 0 has a secret and initially shares the secret with a person firstPerson at time 0. This secret is then shared every time a meeting takes place with a person that has the secret. More formally, for every meeting, if a person xi has the secret at timei, then they will share the secret with person yi, and vice versa.

The secrets are shared instantaneously. That is, a person may receive the secret and share it with people in other meetings within the same time frame.

Return a list of all the people that have the secret after all the meetings have taken place. You may return the answer in any order.

 

Example 1:

Input: n = 6, meetings = [[1,2,5],[2,3,8],[1,5,10]], firstPerson = 1
Output: [0,1,2,3,5]
Explanation:
At time 0, person 0 shares the secret with person 1.
At time 5, person 1 shares the secret with person 2.
At time 8, person 2 shares the secret with person 3.
At time 10, person 1 shares the secret with person 5.​​​​
Thus, people 0, 1, 2, 3, and 5 know the secret after all the meetings.
Example 2:

Input: n = 4, meetings = [[3,1,3],[1,2,2],[0,3,3]], firstPerson = 3
Output: [0,1,3]
Explanation:
At time 0, person 0 shares the secret with person 3.
At time 2, neither person 1 nor person 2 know the secret.
At time 3, person 3 shares the secret with person 0 and person 1.
Thus, people 0, 1, and 3 know the secret after all the meetings.
Example 3:

Input: n = 5, meetings = [[3,4,2],[1,2,1],[2,3,1]], firstPerson = 1
Output: [0,1,2,3,4]
Explanation:
At time 0, person 0 shares the secret with person 1.
At time 1, person 1 shares the secret with person 2, and person 2 shares the secret with person 3.
Note that person 2 can share the secret at the same time as receiving it.
At time 2, person 3 shares the secret with person 4.
Thus, people 0, 1, 2, 3, and 4 know the secret after all the meetings.
 

Constraints:

2 <= n <= 105
1 <= meetings.length <= 105
meetings[i].length == 3
0 <= xi, yi <= n - 1
xi != yi
1 <= timei <= 105
1 <= firstPerson <= n - 1


------------------------------ UF ---------------
/*
DFS 染色
1. sort by time， 每个time 建graph 
2. 遍历time， 找到firstpersson所在的第一个时间group，把相连的全染色，存set里
3。 依次扫时间group，把里面的全染色

https://www.youtube.com/watch?v=-RZ1nKH4dbk 
UF
1. 按时间排序，每个时间内的union起来
特殊点
2. 最开始parent = self，要union的parent = 0；
3. 每个时间点union完后，如果parent不是0的，要分开   
4. 因为定义了知道secrete的parent为0，union时选小的parent
Time：排序nlgn，UF大约O（1），要扫所有meeting O（N）



*/

class UF {
    int[] parent;
    
    public UF(int n) {
        this.parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }
    public int find(int x) {
        if (parent[x] != x) {
             parent[x] = find(parent[x]);
        }
        return parent[x]; 
    }
    public void union(int x, int y) {
        int parentX = find(x);
        int parentY = find(y);
        if (parentX == parentY) {
            return; 
        }
        if (parentX < parentY) {
            parent[parentY] = parentX; 
        }
        else {
            parent[parentX] = parentY; 
        }
    }
}


class Solution {
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        Arrays.sort(meetings, (a, b) -> (a[2] - b[2]));
        
        UF uf = new UF(n);
        Set<Integer> res = new HashSet<>(); 
        res.add(0); res.add(firstPerson); 
        // 定义 知道秘密的parent = 0 
        uf.parent[0] = 0;
        uf.parent[firstPerson] = 0; 
        int i = 0;
        
        while (i < meetings.length) {
            int j = i; // find meetings at the same time 
            Set<Integer> peopleInSameTime = new HashSet<>();
            while (j < meetings.length && meetings[j][2] == meetings[i][2]) {
                // 1. 记下这个时间段的所有人
                int[] curMeeting = meetings[j];
                peopleInSameTime.add(curMeeting[0]);
                peopleInSameTime.add(curMeeting[1]); 
                // 2. union people in same time's meeting
                uf.union(curMeeting[0], curMeeting[1]);
                j++; 
            }
            // 3. 把这时间不知道秘密的人复原break union； 把知道秘密的加到res
            for(int p : peopleInSameTime) {
                if (uf.find(p) == 0) {
                    res.add(p);
                }
                else {
                    uf.parent[p] = p;
                }
            }
            i = j; // 从下一个时间段开始
        }
        List<Integer> returnRes = new ArrayList<>(res);
        return returnRes;
    }
}
