Given a string s containing only three types of characters: '(', ')' and '*', return true if s is valid.

The following rules define a valid string:

Any left parenthesis '(' must have a corresponding right parenthesis ')'.
Any right parenthesis ')' must have a corresponding left parenthesis '('.
Left parenthesis '(' must go before the corresponding right parenthesis ')'.
'*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string "".
 

Example 1:

Input: s = "()"
Output: true
Example 2:

Input: s = "(*)"
Output: true
Example 3:

Input: s = "(*))"
Output: true
 

Constraints:

1 <= s.length <= 100
s[i] is '(', ')' or '*'.
  
  ----------------------- 2 stack ----------------------------
  /*
"*(*(()*" - true
***((( - false 
"(((*))" - true

思路：paramthesis的衍生，因为有了*，用途
1. 当碰到)时，先配对左括号，没有左括号就用星，星也没了直接false
2. 当最后有多于的(括号时，如果其右边有多于的*，则配对

所以需要两个stack，记星和左括号的index。遍历string时处理右括号，遍历完后试着配对多于的左括号
*/
class Solution {
    public boolean checkValidString(String s) {
        int valid = 0;
        int star = 0;
        Deque<Integer> paramStack = new LinkedList<Integer>(); // ( index 
        Deque<Integer> starStack = new LinkedList<Integer>(); // * index 
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                paramStack.push(i); 
            }
            else if (c == ')') {
                if (paramStack.isEmpty()) {
                    if (!starStack.isEmpty()) {
                        starStack.pop(); // 用了一个*
                    }
                    else {
                        return false;
                    }
                }
                else {
                    paramStack.pop();
                }
              
            }
            else {
                starStack.push(i);
            }
        }
        // 处理多于（ 
        while (!paramStack.isEmpty() && !starStack.isEmpty()) {
            if (starStack.peek() > paramStack.peek()) { // 配对
                paramStack.pop();
                starStack.pop(); 
            }
            else {
                return false; 
            }
        }
        return paramStack.isEmpty();
    }
}
