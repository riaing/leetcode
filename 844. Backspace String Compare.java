Given two strings s and t, return true if they are equal when both are typed into empty text editors. '#' means a backspace character.

Note that after backspacing an empty text, the text will continue empty.

 

Example 1:

Input: s = "ab#c", t = "ad#c"
Output: true
Explanation: Both s and t become "ac".
Example 2:

Input: s = "ab##", t = "c#d#"
Output: true
Explanation: Both s and t become "".
Example 3:

Input: s = "a#c", t = "b"
Output: false
Explanation: s becomes "c" while t becomes "b".
 

Constraints:

1 <= s.length, t.length <= 200
s and t only contain lowercase letters and '#' characters.

------------ 2 pointers on two strings ------------------------------------------------------------
/*
from end, pointer move to left. if meet letter, write done to new string 
if #,count++. if then meet letter, count--. until count == 1, 更新pointer到 pointer -1, 
*/
class Solution {
    public boolean backspaceCompare(String s, String t) {
        int sIndex = s.length() - 1; 
        int tIndex = t.length() - 1; 
        while (sIndex >= 0 || tIndex >= 0) {
            int curValidSIndex = getNextValidCharIndex(s, sIndex);
            int curValidTIndex = getNextValidCharIndex(t, tIndex); 

            if (curValidSIndex == -1 && curValidTIndex == -1) { // reached the end of both the strings
                return true; 
            }
            if (curValidSIndex == -1 || curValidTIndex == -1) { // reached the end of one of the strings 
                return false; 
            }
            if (s.charAt(curValidSIndex) == t.charAt(curValidTIndex)) {
                sIndex = curValidSIndex - 1;
                tIndex = curValidTIndex - 1; 
            }
            else {
                return false;
            }
            
        }
        return true; // reach the end of both index < 0, meaning find result 
        
    }
    
    
    
    private int getNextValidCharIndex(String s, int index) {
        int p = index; 
        int count = 0; 
        while(p >= 0) {
            if (s.charAt(p) != '#') {
                if (count == 0) { //遇到字母前之前没#，做对比
                    return p;
                }
                else { //遇到字母且之前碰到#，count-1 
                    count--;
                }
            }
            else { //遇到#count+1 
                count++;
            }
            p--;
        }
        return -1; 
    }
}

------------------------- 可作为单独一题： clean string from backspace -------------------------------------------------------

  // clean string 的方法1， 方法2是用个stack 正向扫。
    private String clean(String s) {
        int p = s.length() - 1; 
        int count = 0; 
        String s1 = "";
        while(p >= 0) {
            if (s.charAt(p) != '#') {
                if (count == 0) { //遇到字母前之前没#，做对比
                    s1 = s.charAt(p) + s1;
                }
                else { //遇到字母且之前碰到#，count-1 
                    count--;
                }
            }
            else { //遇到#count+1 
                count++;
            }
            
            
            p--;
            
        }
        System.out.println(s1);
        return s1; 
    }
