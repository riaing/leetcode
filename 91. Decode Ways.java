/**
对于每个字符，1， 判断他自己（one digit）是否valid，是的话加上m[i-1]；2， 判断他和前一位一起是否valid，是的话加上m[i-2]
m[i] = m[i-1] if s.charAt(i) is valid + m[i-2] if s.substring(i-1,i+1) is valid; 
m[0] = s.charAt(0) valid ? 1 : 0;
m[1] =  s.charAt(1) valid ? m[0] : 0 +
        s.substring(0,2) valid ? 1 : 0
return m[length-1]       
Time: o(n)

*/
class Solution {
    public int numDecodings(String s) {
        if(s.equals("0") || s.length() == 0) return 0;
        if (s.length() == 1) {
            return s.charAt(0) == '0' ? 0 : 1;
        }
        
        int[] dp = new int[s.length()];
        dp[0] = s.charAt(0) != '0' ? 1 : 0; 
        if (s.charAt(1) != '0') {
            dp[1] = dp[0];
        }
        if (Integer.valueOf(s.substring(0,2)) >= 10 && Integer.valueOf(s.substring(0,2)) <= 26) {
            dp[1]++;
        }
       
        for(int i = 2; i < s.length(); i++){
            if(s.charAt(i) != '0') dp[i] += dp[i-1];
            int val = Integer.valueOf(s.substring(i-1,i+1));
            if(val >=10 && val <=26) dp[i] += dp[i-2];
        }
        return dp[s.length()-1];
    }
}
