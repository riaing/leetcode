Given an integer n, you must transform it into 0 using the following operations any number of times:

Change the rightmost (0th) bit in the binary representation of n.
Change the ith bit in the binary representation of n if the (i-1)th bit is set to 1 and the (i-2)th through 0th bits are set to 0.
Return the minimum number of operations to transform n into 0.

 

Example 1:

Input: n = 3
Output: 2
Explanation: The binary representation of 3 is "11".
"11" -> "01" with the 2nd operation since the 0th bit is 1.
"01" -> "00" with the 1st operation.
Example 2:

Input: n = 6
Output: 4
Explanation: The binary representation of 6 is "110".
"110" -> "010" with the 2nd operation since the 1st bit is 1 and 0th through 0th bits are 0.
"010" -> "011" with the 1st operation.
"011" -> "001" with the 2nd operation since the 0th bit is 1.
"001" -> "000" with the 1st operation.
 

Constraints:

0 <= n <= 109

---------------------------------- DFS + memo ------------------
/*

ref ： https://www.youtube.com/watch?v=8MdutrMAwY4 

101011 = 
    1(10000)通过helper把01011变成10000 => 
    0(10000) 通过rule2，就是一步操作=> 
    0(00000) 这一步是通过minimumOneBitOperations（10000）

minimumOneBitOperations(1xxxxx/101011) = 
    helper(xxxxx -> 10000) + 
    1(把头的1转成0) + 
    minimumOneBitOperations(10000 -> 0000)

1. helper(xxxxx) -> operation to coverxt xxxxx 转成10000
  1. 如果开头是1：1xxxx -> 把xxxx变成0000 : 
                = minimumOneBitOperations(xxxx)
  2. 如果开头不是1： 0xxxx -> 10000: 就是要变成 0(1000)。之后才能 -> 1(1000) -> 1(0000)。 把xxxx变成1000 就是helper本身
                = helper(xxxx) + 1 + minimumOneBitOperations(1000)
  
边界：处理长度为1的字符串

Time: 
因为把二进制 10010 -> 00000 , 每位有2个选择，总共k位 -> 2^k 
*/
class Solution {
    Map<String, Integer> helperMap = new HashMap<>(); // memo for helper function 
    Map<String, Integer> dfsMap = new HashMap<>();
    public int minimumOneBitOperations(int n) {
        // 1. 转换成2进制字符串
        String base2 = Integer.toString(Integer.parseInt(n+"", 10), 2);
        // System.out.println("base2 " + base2); 
        return dfs(base2); 
    }
    
    // 把xxx变成000
    private int dfs(String n) {
          // 1. 边界
        if (n.isEmpty()) {
            return 0;
        }
         if (n.equals("0")) {
             // helperMap.put(n, 0);
             return 0; 
         }
        if (n.equals("1")) {
            // helperMap.put(n, 1);
            return 1;
        }
        // 2 corner case and memo 
        if (dfsMap.containsKey(n)) { 
            return dfsMap.get(n);
        }
        
        if (n.charAt(0) == '0') { //'011' 其实就是 '11'
            int res = dfs(n.substring(1)); // 用memo千万记得各种corner case都要放到map里
            dfsMap.put(n,res);
            return res; 
        }
        
        // 3 正式拆解
        // a, build 10000 string
        String p = build1xx(n.length()-1);
        // b. 根据公式  
        int res =  helper(n.substring(1)) + 1 + dfs(p);
        dfsMap.put(n,res);
        return res; 
    }
    
    
    
    
    // operation次数，把 xxx 变成100
    private int helper(String n) {
        // 1. 边界
        if (n.isEmpty()) {
            return 0;
        }
         if (n.equals("0")) { // 变成 1
             // helperMap.put(n, 1);
             return 1; 
         }
        if (n.equals("1")) { // 直接return
            // helperMap.put(n, 0);
            return 0;
        }
        
        if (helperMap.containsKey(n)) {
            return helperMap.get(n);
        }
        //2. 根据规则判断
        int res = 0;
        char firstChar = n.charAt(0);
        if (firstChar == '1') { // 1xx -> 把xx变成00 :
            res = dfs(n.substring(1)); 
        }
        else { // 0xxx  helper(xxx) + 1 + minimumOneBitOperations(100)
            String p = build1xx(n.length()-1);
            res = helper(n.substring(1)) + 1 + dfs(p);    
        }
        helperMap.put(n, res);
        return res; 
    }
    
    // build 100 if len = 3
    private String build1xx(int len) {
        String p = "";
        for (int i = 0; i < len; i++) {
            if (i == 0) {
                p += '1';
            }
            else {
                p += '0';
            }
        }
        return p; 
    }
}
