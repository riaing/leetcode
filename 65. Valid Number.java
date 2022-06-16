A valid number can be split up into these components (in order):

A decimal number or an integer.
(Optional) An 'e' or 'E', followed by an integer.
A decimal number can be split up into these components (in order):

(Optional) A sign character (either '+' or '-').
One of the following formats:
One or more digits, followed by a dot '.'.
One or more digits, followed by a dot '.', followed by one or more digits.
A dot '.', followed by one or more digits.
An integer can be split up into these components (in order):

(Optional) A sign character (either '+' or '-').
One or more digits.
For example, all the following are valid numbers: ["2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789"], while the following are not valid numbers: ["abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53"].

Given a string s, return true if s is a valid number.

 

Example 1:

Input: s = "0"
Output: true
Example 2:

Input: s = "e"
Output: false
Example 3:

Input: s = "."
Output: false
 

Constraints:

1 <= s.length <= 20
s consists of only English letters (both uppercase and lowercase), digits (0-9), plus '+', minus '-', or dot '.'.

--------------------------------------------- 分情况讨论 --------------------------------------------------
/*
根据数字，e/E, 符号，dot，分四种情况讨论来返回false。
需要三个boolean： seenDigit, seenE, seenDot.
返回seenDigit 

digits: must >= 1 (seenDigit)
 - set seenDigit = true 
 
sign: only at the very beginning, or right after E. : -63e+7
 - if not, return false  
 
E: 1. only one (seenE).  2. Must appear after decimal/digits (seenDigit = true) 3. 它之后必须接integer
  - if seenE || !seenDigit => return false 
  - set seenE = true. seenDigit = false (后面得是数字)
  
Dot: 1. 只能 <=1 decimal number, <= 1 dot. (seenDot). 2. Must appear Before E (seenE = false ) 
 - if seenDot || seenE => return false
 - set seenDot = true
 
Return seenDigit 


*/
class Solution {
    public boolean isNumber(String s) {
        boolean seenDigit = false;
        boolean seenE = false;
        boolean seenDot = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                 seenDigit = true;
             }
            else if (c == '+' || c == '-') {
                //只能在beginning或者e的后面
                if (i != 0 && (s.charAt(i-1) != 'e' && s.charAt(i-1) != 'E')) {
                    return false;
                }
            }
            else if (c == 'e' || c == 'E') {
                if (seenE || !seenDigit) {
                    return false;
                }
                seenE = true;
                seenDigit = false; // reset，之后必须得接digit
            }
            else if (c == '.') {
                if (seenDot || seenE) {
                    return false;
                }
                seenDot = true;
            }
            else {
                return false; 
            }
        }
        return seenDigit; 
    }
}
