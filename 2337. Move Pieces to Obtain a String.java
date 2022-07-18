You are given two strings start and target, both of length n. Each string consists only of the characters 'L', 'R', and '_' where:

The characters 'L' and 'R' represent pieces, where a piece 'L' can move to the left only if there is a blank space directly to its left, and a piece 'R' can move to the right only if there is a blank space directly to its right.
The character '_' represents a blank space that can be occupied by any of the 'L' or 'R' pieces.
Return true if it is possible to obtain the string target by moving the pieces of the string start any number of times. Otherwise, return false.

 

Example 1:

Input: start = "_L__R__R_", target = "L______RR"
Output: true
Explanation: We can obtain the string target from start by doing the following moves:
- Move the first piece one step to the left, start becomes equal to "L___R__R_".
- Move the last piece one step to the right, start becomes equal to "L___R___R".
- Move the second piece three steps to the right, start becomes equal to "L______RR".
Since it is possible to get the string target from start, we return true.
Example 2:

Input: start = "R_L_", target = "__LR"
Output: false
Explanation: The 'R' piece in the string start can move one step to the right to obtain "_RL_".
After that, no pieces can move anymore, so it is impossible to obtain the string target from start.
Example 3:

Input: start = "_R", target = "R_"
Output: false
Explanation: The piece in the string start can move only to the right, so it is impossible to obtain the string target from start.
 

Constraints:

n == start.length == target.length
1 <= n <= 105
start and target consist of the characters 'L', 'R', and '_'.


------------ 2 pointer --------------
/*
BF n^n 

2 Pointer: 
*/
class Solution {
    public boolean canChange(String start, String target) {
        int len = start.length(); 
        
        if(start.length() != target.length() ) return false;
        if(start.equals(target) ) return true;
        if(start.replace("_","").equals(target.replace("_","")) == false) return false; // 确保相同字母顺序 "--lr". "-lr"
        
        int i = 0; int j = 0;
        while (i < start.length() && j < target.length()) {
            while (i < start.length() && start.charAt(i) == '_') {
                i++;
            }
            while (j < target.length() && target.charAt(j) == '_') {
                j++;
            }
            if(i >= len && j >= len) return true; // 说明都走到底没letter了
            
            // now i. j point to letters             
            // now both at 'L', see if start can match target by some moves of L 
            if (start.charAt(i) == 'L') {
                if (i < j) { return false; }
                // now i >= j, if i==j, matches; i i > j, 肯定能移过去。所以这里肯定能match. 想象这是第一个字母
                i++;
                j++; 
              
            }
            else if (start.charAt(i) == 'R') {
                if (i > j) { return false; }
                // 同上 try some moves 
                i++;
                j++;
            }
        }
        return true; 
    }
 
    
    
    /*
        BF n^n 
    */
    private boolean helper(String start, String target, Map<String, Boolean> memo) {

        if (start.equals(target)) {
            return true;
        }
        if (memo.containsKey(start)) {
            return memo.get(start);
        }
        boolean res = false;
        for (int i = 0; i < start.length(); i++) {
            if (i != 0 && start.charAt(i-1) == '_' && start.charAt(i) == 'L') {
                String newStart = start.substring(0, i-1) + "L_" + start.substring(i+1);
                if (helper(newStart, target, memo)) {
                    res = true;
                    break;
                }
            }
            
            if (i + 1 < start.length() && start.charAt(i+1) == '_' && start.charAt(i) == 'R') {
                String newStart = start.substring(0, i) + "_R" + start.substring(i+2);
                if (helper(newStart, target, memo)) {
                    res = true;
                    break;
                }
            }
        }
        memo.put(start, res);
        return res;
    }
}
