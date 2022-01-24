Write an algorithm to determine if a number is "happy".

A happy number is a number defined by the following process: Starting with any positive integer, replace the number by the sum of the squares of its digits, and repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1. Those numbers for which this process ends in 1 are happy numbers.

Example: 19 is a happy number

12 + 92 = 82
82 + 22 = 68
62 + 82 = 100
12 + 02 + 02 = 1


class Solution {
    public boolean isHappy(int n) {
        // Use set to record if there is a loop
        Set<Integer> containsNum = new HashSet<Integer>(); 
        while (n != 1) {
            if (containsNum.contains(n)) {
            return false;
            }
            containsNum.add(n);
            n = getNum(n);
        }
        return true; 
    }
    private int getNum(int n) {
        int sum = 0;
        // 从个位数算起，直到最高位（n == 0）
        while (n != 0) {
            // First get the number on the digit 
            sum = sum + (n%10) * (n%10);
            n = n / 10; 
        }
        return sum;
    }
}

------------------ 2022.1.24: fast slow pointers -----------------------------
    /*
求 cycle 得题可转换成 fast slow pointer。
快指针每次计算两次，慢指针计算一次。当两指针数相同时说明 meet in loop。当任意指针数为1时说明 end。
time：假设链表长 N，o(N)
*/
class Solution {
    public boolean isHappy(int n) {
        int slow = n;
        int quick = n;
        
        while (quick != 1) {
            int quickNext = calculate(quick);
            int quickNextNext = calculate(quickNext);
           
            if (quickNext == 1 || quickNextNext == 1) { // 快指针总是先计算，这里用快指针作为终止条件
                return true;
            }
            
            quick = quickNextNext;
            slow = calculate(slow);
            if (quick == slow) {
                return false;
            }
        }
        return true;
        
    }
    
    //一：先转成 string
    private int calculate(int n) {
        String nString = String.valueOf(n); 
        int result = 0; 
        for (int i = 0; i < nString.length(); i++) {
            int curPositionVal =  Character.getNumericValue(nString.charAt(i));
            result += curPositionVal*curPositionVal;
        }
        return result; 
    }
    
    // 二：更好的找数字位数的方法
    private static int calculate2(int num) {
        int sum = 0, digit;
        while (num > 0) {
          digit = num % 10; // 从个位找起
          sum += digit * digit;
          num /= 10; //
        }
        return sum;
  }
}
    
