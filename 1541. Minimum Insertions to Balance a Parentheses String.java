Given a parentheses string s containing only the characters '(' and ')'. A parentheses string is balanced if:

Any left parenthesis '(' must have a corresponding two consecutive right parenthesis '))'.
Left parenthesis '(' must go before the corresponding two consecutive right parenthesis '))'.
In other words, we treat '(' as an opening parenthesis and '))' as a closing parenthesis.

For example, "())", "())(())))" and "(())())))" are balanced, ")()", "()))" and "(()))" are not balanced.
You can insert the characters '(' and ')' at any position of the string to balance it if needed.

Return the minimum number of insertions needed to make s balanced.

 

Example 1:

Input: s = "(()))"
Output: 1
Explanation: The second '(' has two matching '))', but the first '(' has only ')' matching. We need to add one more ')' at the end of the string to be "(())))" which is balanced.
Example 2:

Input: s = "())"
Output: 0
Explanation: The string is already balanced.
Example 3:

Input: s = "))())("
Output: 3
Explanation: Add '(' to match the first '))', Add '))' to match the last '('.
 

Constraints:

1 <= s.length <= 105
s consists of '(' and ')' only.
  
  ------------------------------------ 2 variable ---------------------------------
  /*
https://labuladong.github.io/algo/4/31/129/ 。注意两个variable的定义


res
- 表示的是当前遍历过程中为了配平当前的状态，所需要插入左括号或是右括号的总次数，
- 左括号 需求,而是除了 左右括号对应添加的 need 以外人为为了保持规则，而添加的次数。
need
- 表示的是遍历结束后仍需要的右括号数量，
*/
class Solution {
    public int minInsertions(String s) {
        int rightNeed = 0;
        int res = 0;  
        int i = 0; 
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c == '(') {
                rightNeed += 2; 
                if (rightNeed % 2 == 1) { // 当遇到左括号时，若对右括号的需求量为奇数，需要插入 1 个右括号。因为一个左括号需要两个右括号嘛，右括号的需求必须是偶数，这一点也是本题的难点。 
                    res++; // 多加个右括号
                    rightNeed--; // 对右括号的需求减1 
                }
            }
            else {
                rightNeed--;
                if (rightNeed < 0) {   // 说明右括号太多了
                    res++; //插入一个左括号
                    rightNeed = 1; //  // 同时，对右括号的需求变为 1
                }
            }
            i++;
        }
        return res + rightNeed; 
    }
}
