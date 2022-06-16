Convert a non-negative integer num to its English words representation.

 

Example 1:

Input: num = 123
Output: "One Hundred Twenty Three"
Example 2:

Input: num = 12345
Output: "Twelve Thousand Three Hundred Forty Five"
Example 3:

Input: num = 1234567
Output: "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
 

Constraints:

0 <= num <= 231 - 1

-------------------------------------------------
class Solution {
    Map<Integer, String> chunkMap = new HashMap<>();
    public String numberToWords(int num) {
         chunkMap.put(0 ,"");
         chunkMap.put(1 , "Thousand");
         chunkMap.put(2 , "Million");
         chunkMap.put(3 , "Billion");
        
        // corner case 
        if (num == 0) {
            return "Zero"; 
        }
        
        int chunk = 0;
        String res = "";
        while (num > 0) {
            int cur = num % 1000; // 每次取三位
            String curWord = helper(cur);
            if (!curWord.isEmpty()) { // 有可能是10(000)
                curWord = curWord + " " + chunkMap.get(chunk) + " ";
                res = curWord + res; 
            }
      
            chunk++; // 移动到往左边的下一个三位数
            num = num / 1000;
        }
        return res.trim();  
    }
    
    // take a at most 3 digits num and transfer to words.这时候没有leading zero。eg： 1，23，345 
    private String helper(int num) {
        if (num == 0) {
            return ""; 
        }
        
        if (num / 100 != 0) { //三位数
            String res = "";
            res = LessThan21(num / 100) + " Hundred";
            if (num % 100 != 0) {
                res = res + " " + helper(num % 100);
            }
            return res; 
        }
        // 两位数或者一位数
        // 1. < 21 
        if (num < 21) {
            return LessThan21(num);
        }
        else {
            String res = ten(num / 10);
            // 非整十
            if (num % 10 != 0) {
                res = res +  " " + LessThan21(num % 10); 
            }
            return res; 
        }
        

    }
    
    
    public String LessThan21(int num) {
        switch(num) {
          case 1: return "One";
          case 2: return "Two";
          case 3: return "Three";
          case 4: return "Four";
          case 5: return "Five";
          case 6: return "Six";
          case 7: return "Seven";
          case 8: return "Eight";
          case 9: return "Nine";      
          case 10: return "Ten";
          case 11: return "Eleven";
          case 12: return "Twelve";
          case 13: return "Thirteen";
          case 14: return "Fourteen";
          case 15: return "Fifteen";
          case 16: return "Sixteen";
          case 17: return "Seventeen";
          case 18: return "Eighteen";
          case 19: return "Nineteen";
          case 20: return "Twenty";      
        }
        return "";
    }
    
    public String ten(int num) {
        switch(num) {
          case 2: return "Twenty";
          case 3: return "Thirty";
          case 4: return "Forty";
          case 5: return "Fifty";
          case 6: return "Sixty";
          case 7: return "Seventy";
          case 8: return "Eighty";
          case 9: return "Ninety";
        }
        return "";
    }

}
