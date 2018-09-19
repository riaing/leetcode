Given a non-empty array of digits representing a non-negative integer, plus one to the integer.

The digits are stored such that the most significant digit is at the head of the list, and each element in the array contain a single digit.

You may assume the integer does not contain any leading zero, except the number 0 itself.

Example 1:

Input: [1,2,3]
Output: [1,2,4]
Explanation: The array represents the integer 123.
Example 2:

Input: [4,3,2,1]
Output: [4,3,2,2]
Explanation: The array represents the integer 4321.


--------------- 
对于每一位来说，只有等于9的时候才会进位，小于就的时候，数值加一然后返回本数组即可。

　　如果最后数值进位后恰好比原值多一位，则直接new一个数组，默认值为0，赋值最高位为1，返回即可。 
  
class Solution {
    public int[] plusOne(int[] digits) {
        if (digits == null || digits.length == 0) {
            return digits;
        }
   
        for (int i = digits.length -1; i >= 0;  i--){
          
            if (digits[i] == 9) {
                digits[i] = 0;
            }
            else {
                digits[i] = digits[i] + 1;
                //这里就需要直接return。
                return digits;
            }
        }
        //如果走到这里的话，说明前面没return，说明是每一位都是9的情况。这时候就new 一个array，把第一位变为1. ->100
        int[] x = new int[digits.length+1];
        x[0] = 1;
        return x; 
    }
}
