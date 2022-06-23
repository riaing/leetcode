Given an input string s, reverse the order of the words.

A word is defined as a sequence of non-space characters. The words in s will be separated by at least one space.

Return a string of the words in reverse order concatenated by a single space.

Note that s may contain leading or trailing spaces or multiple spaces between two words. The returned string should only have a single space separating the words. Do not include any extra spaces.

 

Example 1:

Input: s = "the sky is blue"
Output: "blue is sky the"
Example 2:

Input: s = "  hello world  "
Output: "world hello"
Explanation: Your reversed string should not contain leading or trailing spaces.
Example 3:

Input: s = "a good   example"
Output: "example good a"
Explanation: You need to reduce multiple spaces between two words to a single space in the reversed string.
 

Constraints:

1 <= s.length <= 104
s contains English letters (upper-case and lower-case), digits, and spaces ' '.
There is at least one word in s.
 

Follow-up: If the string data type is mutable in your language, can you solve it in-place with O(1) extra space?

 ----------- 如果是char array的in place reverse -----------------
 /*
java 做不到O（1） space，必须得split成array.做法
1. trim（） -> 遍历string，删除中间多于空格
2. 遍历char array, 找到每个word，翻转
3. 翻转整个char array

Time: O(n), space O(n)

*/
class Solution {
    public String reverseWords(String s) {
        // 1. 除掉多于space变成char array
        s = s.trim(); // remove 首尾space
        String tmp = "";
        // 1. 除去中间多于space
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ' && s.charAt(i+1) == ' ') {
                continue;
            }
            tmp += c; 
        }
        char[] input = tmp.toCharArray();
        
        // 2. in place 翻转。先转每个词，再整个string转
        int i = 0;
        while (i < input.length) {
            if (input[i] == ' ') {
                i++;
                continue; 
            }
            // find the word's end index 
            int end = i;
            while (end < input.length && input[end] != ' ') {
                end++;
            }
            // now end pointer points to the space after the word 
            reverse(input, i, end - 1); 
            i = end; 
        }
        // 转整个string 
        reverse(input, 0, input.length-1); 
        // System.out.println(input);
        return new String(input); 
    }
    
    private void reverse(char[] input, int start, int end) {
        while (start < end) {
            char tmp = input[start];
            input[start] = input[end];
            input[end] = tmp;
            start++;
            end--;
        }
    }
}

--------------------- CODING -------------------
/*
java 做不到O（1） space，必须得split成array
Time: O(n), space O(n)
*/
class Solution {
    public String reverseWords(String s) {
        // 1. delete leading/trailing spaces 
        String s2 = s.trim(); // " ", "" 都可以trim
        if (s2.isEmpty()) {
            return s2; 
        }
        List<String> toReverse = Arrays.asList(s2.split("\\s+"));
        Collections.reverse(toReverse);
        return String.join(" ", toReverse);
    }
}

-------------- 从尾部遍历来reverse  --------------
 class Solution {
    public String reverseWords(String s) {
        String[] words = s.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            sb.append(words[i]).append(" ");
        }
        return sb.toString().trim();
    }
}
