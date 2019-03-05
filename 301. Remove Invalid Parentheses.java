Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.

Note: The input string may contain letters other than the parentheses ( and ).

Example 1:

Input: "()())()"
Output: ["()()()", "(())()"]
Example 2:

Input: "(a)())()"
Output: ["(a)()()", "(a())()"]
Example 3:

Input: ")("
Output: [""]

---------------------------------------DFS---------------------------------------------------------------------
解法：先分别找到左右两边invalid括号的个数nleft，nright。
递归的思想是，对于每一个左右括号，我们都可以把它们delete。所以需要遍历整个string，找到所有能删掉nleft个左括号和nright个右括号的情况。
那么遍历整个string，我们先从删除右括号开始，如果当前char是右括号并且此时nright>0,我们删掉这个右括号，nright--，然后进行下一层递归。
递归终止的条件是当没有多余的左右括号并且当前string为valid时
注意1，在第i层，当我们删掉(或者)时，此时string中少了一个char，那么本该递归的下一层（i+1）就变成了i。所以递归时下一层的index还是i
注意2，对于重复的括号，我们只删掉第一个。 (() ->删掉第一个(，得到（）。如果递归到下一层，同样删掉(的话，还是得到（）
图示见 https://zxi.mytechroad.com/blog/searching/leetcode-301-remove-invalid-parentheses/ 
time: O(2^N) since in the worst case we will have only left parentheses in the expression and for every bracket we will
have two options i.e. whether to remove it or consider it. Considering that the expression has N parentheses, 
the time complexity will be O(2^N)
space: o(2^N)

class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> res = new ArrayList<String>();
        // find all invalid left and right paranthese
        int nleft = 0; 
        int nright = 0;
        for (char c: s.toCharArray()) {
            if (c == '(') {
                nleft++;
            }
            if( c == ')') {
                if (nleft == 0) {
                    nright ++;
                }
                else {
                    nleft--;
                }
            }
        }
        dfs(s, 0, res, nleft, nright);
        return res;
    }
    
    private void dfs(String s, int index, List<String> res, int nleft, int nright) {
        if (nleft == 0 && nright == 0) {
            if (validString(s)) {
                res.add(s);
            }
            return; 
        }
        for (int i = index; i < s.length(); i++) {
            // we only remove the first one if there are duplicates 
            if (i != 0 && s.charAt(i) == s.charAt(i-1)) {
                continue;
            }
            if (nright > 0 && s.charAt(i) == ')') {
                //remove it from string 
                String newString = s.substring(0,i) + s.substring(i+1); 
                // 注意这里循环的是i而不是i+1，因为remove了当前的char，string变短了。
                dfs(newString, i, res, nleft, nright-1);
            }
            if (nleft > 0 && s.charAt(i) == '(') {
                //remove it from string 
                String newString = s.substring(0,i) + s.substring(i+1); 
                dfs(newString, i, res, nleft-1, nright);
            }
        }
    } 
    
    private boolean validString(String s) {
        int cnt = 0;
        for (char c: s.toCharArray()) {
            if (c == '(') {
                cnt ++; 
            }
            if (c == ')') {
                cnt --;
            }
            // if in the middle ）> （，definitely wrong. eg: ")("
            if (cnt < 0) {
                return false;
            }
        }
        return cnt == 0;
    }
}

--------------------------BFS time O(N!), space 0(N!)-----------------------------------------------------------------------
 遍历string，删除左右括号。如果删除后的string不valid，加入queue中，如果valid，加入res中。注意这里用set存string是否出现过，出现的话跳过避免重复运算
   1           ()) ->依次删除左右括号
   n        ))  ()  (){其实这个已存在，这里写出以便visualize}
  n-1      )  ) ( ) ( ) 
  所以time是 1+n+n-1+...+1 = n! 
  space是n！（一般都是leave node 的个数）
                                 
class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> res = new ArrayList<String>();
        Queue<String> queue = new LinkedList<String>();
        queue.offer(s);
        Set<String> used = new HashSet<String>();
        // boolean to record if at the current level, have found valid string. 
        boolean findMinNum = false; 
        while (!queue.isEmpty()) {
            int curLevel = queue.size();
            for (int k = 0; k< curLevel; k++) {
                String cur = queue.poll();
                if (validString(cur)) {
                    res.add(cur);
                    used.add(cur);
                    findMinNum = true;
                }
                else {
                    for (int i = 0; i < cur.length(); i++) {
                        if (cur.charAt(i) == '(' || cur.charAt(i) == ')') {
                            String newString = cur.substring(0, i) + cur.substring(i+1);
                            if (!used.contains(newString)) {
                                queue.offer(newString);
                                used.add(newString);
                            }
                        }
                    }
                } 
            }
            if(findMinNum) {
                return res;
            }
        }
        return res;
    }
    
    private boolean validString(String s) {
        int cnt = 0;
        for (char c: s.toCharArray()) {
            if (c == '(') {
                cnt ++; 
            }
            if (c == ')') {
                cnt --;
            }
            // if in the middle ）> （，definitely wrong. eg: ")("
            if (cnt < 0) {
                return false;
            }
        }
        return cnt == 0;
    }
}
