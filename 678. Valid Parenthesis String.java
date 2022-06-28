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
  
  ----------------------- 2 stack O(n), O(n) ----------------------------
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

---------------- 从首尾扫两百遍  O(n), O(1) --------------
 There is another simple O(n) solution with O(1) space complexity, not very intuitive like the greedy approach, but it's nice to know about it. We can rephrase the problem by listing all the valid cases. There are 3 valid cases:

1- There are more open parenthesis but we have enough '*' so we can balance the parenthesis with ')'
2- There are more close parenthesis but we have enough '*' so we can balance the parenthesis with '('
3- There are as many '(' than ')' so all parenthesis are balanced, we can ignore the extra '*'

Algorithm: You can parse the String twice, once from left to right by replacing all '*' by '(' and once from right to left by replacing all '*' by ')'. For each of the 2 loops, if there's an iteration where you end up with a negative count (SUM['('] - SUM[')'] < 0) then you know the parenthesis were not balanced. You can return false. After these 2 checks (2 loops), you know the string is balanced because you've satisfied all the 3 valid cases mentioned above. Voila!

  public boolean checkValidString(String s) {  
    int leftBalance = 0;
    for (int i=0; i<s.length(); i++) {
      if ((s.charAt(i) == '(') || (s.charAt(i) == '*'))
        leftBalance++;        
      else
        leftBalance--;
      
      if (leftBalance<0) return false; // We know we have unbalanced parenthesis
    }
    
    // We can already check if parenthesis are valid
    if (leftBalance == 0) return true;
            
    int rightBalance = 0;
    for (int i=s.length()-1; i>=0; i--) {
      if ((s.charAt(i) == ')') || (s.charAt(i) == '*'))
        rightBalance++;
      else
        rightBalance--;
      
      if (rightBalance<0) return false;  // We know we have unbalanced parenthesis
    }
    
    // Here we know we have never been unbalanced parsing from left to right e.g. ')('
    // We've also already substituted '*' either by '(' or by ')'
    // So we only have 3 possible scenarios here:
    // 1. We had the same amount of '(' and ')'
    // 2. We had more '(' then ')' but enough '*' to substitute
    // 3. We had more ')' then '(' but enough '*' to substitute
    return true;
  }
