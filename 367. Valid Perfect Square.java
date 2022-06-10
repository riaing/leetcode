Given a positive integer num, write a function which returns True if num is a perfect square else False.

Follow up: Do not use any built-in library function such as sqrt.

 

Example 1:

Input: num = 16
Output: true
Example 2:

Input: num = 14
Output: false
 

Constraints:

1 <= num <= 2^31 - 1

----------------------就是sqrt的bs -----------------------------------------------------

class Solution {
    public boolean isPerfectSquare(int num) {
        if (num <= 1) {
            return true; 
        }
        long end = num / 2;
        long start = 0;
        
         // find first k which k^2 > x 
        while (start <= end) {
            long mid = start + (end - start) / 2; 
            // System.out.println("mid " + mid + " : " + mid*mid);
            if (mid * mid == num) {
                return true;
            }
            else if (mid*mid > num) { //注意是long，不让mid*mid会超
                end = mid - 1; 
            }
            else {
                start = mid + 1;
            }
        }
        
        return false;
    }
}
