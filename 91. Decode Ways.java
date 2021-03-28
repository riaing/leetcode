/**
对于每个字符，1， 判断他自己（one digit）是否valid，是的话加上m[i-1]；2， 判断他和前一位一起是否valid，是的话加上m[i-2]
m[i] = m[i-1] if s.charAt(i) is valid + m[i-2] if s.substring(i-1,i+1) is valid; 
m[0] = s.charAt(0) valid ? 1 : 0;
m[1] =  s.charAt(1) valid ? m[0] : 0 +
        s.substring(0,2) valid ? 1 : 0
return m[length-1]       
Time: o(n)

*/

---------------- 03.27、2021 ---------------------------
        class Solution {
    public int numDecodings(String s) {
        if (s.charAt(0) == '0') {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }
        
        int[] dp = new int[s.length()];
        // initialization 
        dp[0] = 1; 
        dp[1] = decodeTwoDigit(s.substring(0, 2)); //因为特殊处理了length为1的情况，所以这里不会exception
        
        for (int i = 2; i < s.length(); i++) {
            if (s.charAt(i) != '0') {
                dp[i] = dp[i-1];
            }
            int twoDigits = Integer.parseInt(s.substring(i-1, i+1));
            if (twoDigits > 9 && twoDigits < 27) {
                dp[i] = dp[i] + dp[i-2];
            }
        }
        return dp[s.length()-1];
        
    }
    
    private int decodeTwoDigit(String s) {
        int num = Integer.parseInt(s);  
        if (num == 10 || num == 20) {
            return 1;
        }
        // 30. 40....90
        if (s != "10" && s != "20" && s.charAt(1) == '0') {
            return 0; 
        }
     
        if (num >10 && num <27) {
            return 2; 
        }
        // 27 ... 99 except 20, 30,40,..90 
        return 1; 
    }
}
---------------------------------------------------------------------------------
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
--------------------------follow up: 返回所有的组合，用Map + memorization --------------------------------------------------------------
        class Solution {
` // 返回所有组合
    public int numDecodings(String s) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        map.put(0, new ArrayList<String>());
        if (s.charAt(0) != '0') {
              map.get(0).add(s.charAt(0) + "");
        }
        
        map.put(1, new ArrayList<String>());
        if (s.charAt(1) != '0') {
           map.get(1).add(s.charAt(1)+"");
        }
        map.get(1).add(s.substring(0,2));
      
        helper(String s....);
        return map.get(s.length()-1); 
        
    }
    private void helper(String s, int index, map) {
        if (index == s.length()) {
            num++;
        }
        for (int i = index; i < s.length(); i++) {
            List<String> cur = new ArrayList<String>();
            String oneDigit = s.substring(i, i+1);
            if (oneDifit.charAt(0) != '0') {
                cur.addAll(map.get(i-1));
                // add oneDigit in 
            }
            String twoDigit = s.substring(i-1, i+1);
            if (Integer.valueOf(twoDigit) >=10 <=26) {
                // get 
                map.get(i-2) 依次加上twodigit，再addAll到cur
            }
            map.put(i, cur);
            
        }
    }
}
