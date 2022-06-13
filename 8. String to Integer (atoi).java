Implement the myAtoi(string s) function, which converts a string to a 32-bit signed integer (similar to C/C++'s atoi function).

The algorithm for myAtoi(string s) is as follows:

Read in and ignore any leading whitespace.
Check if the next character (if not already at the end of the string) is '-' or '+'. Read this character in if it is either. This determines if the final result is negative or positive respectively. Assume the result is positive if neither is present.
Read in next the characters until the next non-digit character or the end of the input is reached. The rest of the string is ignored.
Convert these digits into an integer (i.e. "123" -> 123, "0032" -> 32). If no digits were read, then the integer is 0. Change the sign as necessary (from step 2).
If the integer is out of the 32-bit signed integer range [-231, 231 - 1], then clamp the integer so that it remains in the range. Specifically, integers less than -231 should be clamped to -231, and integers greater than 231 - 1 should be clamped to 231 - 1.
Return the integer as the final result.
Note:

Only the space character ' ' is considered a whitespace character.
Do not ignore any characters other than the leading whitespace or the rest of the string after the digits.
 

Example 1:

Input: s = "42"
Output: 42
Explanation: The underlined characters are what is read in, the caret is the current reader position.
Step 1: "42" (no characters read because there is no leading whitespace)
         ^
Step 2: "42" (no characters read because there is neither a '-' nor '+')
         ^
Step 3: "42" ("42" is read in)
           ^
The parsed integer is 42.
Since 42 is in the range [-231, 231 - 1], the final result is 42.
Example 2:

Input: s = "   -42"
Output: -42
Explanation:
Step 1: "   -42" (leading whitespace is read and ignored)
            ^
Step 2: "   -42" ('-' is read, so the result should be negative)
             ^
Step 3: "   -42" ("42" is read in)
               ^
The parsed integer is -42.
Since -42 is in the range [-231, 231 - 1], the final result is -42.
Example 3:

Input: s = "4193 with words"
Output: 4193
Explanation:
Step 1: "4193 with words" (no characters read because there is no leading whitespace)
         ^
Step 2: "4193 with words" (no characters read because there is neither a '-' nor '+')
         ^
Step 3: "4193 with words" ("4193" is read in; reading stops because the next character is a non-digit)
             ^
The parsed integer is 4193.
Since 4193 is in the range [-231, 231 - 1], the final result is 4193.
 

Constraints:

0 <= s.length <= 200
s consists of English letters (lower-case and upper-case), digits (0-9), ' ', '+', '-', and '.'.

--------------------------------------------
/*
一些corner case
1. +-12: 多符号
2. abc12: 数字前有字母


重点是怎么handle overflow/underflow ！
*/
class Solution {
    public int myAtoi(String s) {
        // 1. 读取symbol和digit
        String newS = s.trim(); 
        int symbol = 1;
        String digit = "0";
        
        // 方法1：各种flag来保证顺序
//         boolean findDigit = false; 
//         boolean findSymbol = false; 
//         for (char c : newS.toCharArray()) {
//             if ((c == '-' || c == '+') && !findDigit) { // 第一个一定要是符号
//                 if (findSymbol) {
//                     break;
//                 }
//                 symbol = c == '-' ? -1 : 1; 
//                 findSymbol = true;
//             }
//             else if (!Character.isDigit(c) && !findDigit) { // 没遇到数字就有letter， invalid
//                 break; 
//             }
//             else if (!Character.isDigit(c) && findDigit) { // now after digits 
//                 break; 
//             }
//             else if (Character.isDigit(c)) {
//                 findDigit = true; 
//                 digit += c; 
//             }
//         }
        
        // 方法2： 因为char有严格的顺序：符号-> 数字->后面忽略。所以分段处理更简单
        int index = 0;
        if (index < newS.length() && (newS.charAt(index) == '+' || newS.charAt(index) == '-')) {
            symbol = newS.charAt(index) == '-' ? -1 : 1;
            index++;
        }
        // 找到符号后，接下来必须是数字
        while (index < newS.length() && Character.isDigit(newS.charAt(index))) {
             digit += newS.charAt(index); 
            index++;
        }
        
        

        // 2. handlow overflow, underflow 
        /*
        最大int = 2147483647， 最小int = -2147483648
        */
        int res = 0; 
        for (char c : digit.toCharArray()) {
            int curDigit = c - '0';
            if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && curDigit > Integer.MAX_VALUE % 10 )) { // 重点行！如果digit > 7 且res刚好= 214748364 
                return symbol == -1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            // 还能符合范围，更新
            res = res * 10 + curDigit; 
        }

        return res * symbol; 
    }
} 
