You are given a string s that consists of lower case English letters and brackets.

Reverse the strings in each pair of matching parentheses, starting from the innermost one.

Your result should not contain any brackets.

 

Example 1:

Input: s = "(abcd)"
Output: "dcba"
Example 2:

Input: s = "(u(love)i)"
Output: "iloveu"
Explanation: The substring "love" is reversed first, then the whole string is reversed.
Example 3:

Input: s = "(ed(et(oc))el)"
Output: "leetcode"
Explanation: First, we reverse the substring "oc", then "etco", and finally, the whole string.
 

Constraints:

1 <= s.length <= 2000
s only contains lower case English characters and parentheses.
It is guaranteed that all parentheses are balanced.


---------------- stack 做法 -------------------
/*
stack做法
*/
class Solution {
    public String reverseParentheses(String s) {
        int index = 0; 
        StringBuilder b = new StringBuilder(); 
        while (index < s.length()) {
            if (s.charAt(index) != '(' && s.charAt(index) != ')') {
                b.append(s.charAt(index));
                index++;
            }
            else if (s.charAt(index) == '(') {
                // find the largest 括号to reverse 
                int cnt = 0;
                int rightIndex = -1;
                int leftIndex = 0;
                for (int i = index; i < s.length(); i++) {
                    if (s.charAt(i) == '(') {
                        if (rightIndex == -1) {
                            rightIndex = i;
                        }
                        cnt++;
                    }
                    else if (s.charAt(i) == ')') {
                        if (cnt == 1) {
                            leftIndex = i;
                            break;
                        }
                        else {
                            cnt--;
                        }
                    }
                }
                String reverted = revert(s.substring(rightIndex + 1, leftIndex)); // 传的时候不带最外层括号
                b.append(reverted);
                index = leftIndex+1;
            }
        }
        return b.toString();
        
       
    }
    
    private String revert(String s) {
         // stack - 碰到( 和字母放进queue，碰到 ）拿出来 放到list，知道碰到（；再把list放回stack
        Deque<Character> stack = new LinkedList<Character>(); 
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ')') {
                stack.push(c);
            }
            else {
                // grab out until meet first (
                List<Character> tmp = new LinkedList<Character>();
                while (stack.peek() != '(') {
                    tmp.add(stack.pop());
                }
                stack.pop(); // 拿出（
                for (char inTmp : tmp) {
                    stack.push(inTmp);
                }
            }
        }
        // construct the string 
        StringBuilder b = new StringBuilder();
        while (stack.size() != 0) {
            b.append(stack.pop());
        }
        return b.toString();
    }
    
}
