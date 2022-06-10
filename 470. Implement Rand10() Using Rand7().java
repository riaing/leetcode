Given the API rand7() that generates a uniform random integer in the range [1, 7], write a function rand10() that generates a uniform random integer in the range [1, 10]. You can only call the API rand7(), and you shouldn't call any other API. Please do not use a language's built-in random API.

Each test case will have one internal argument n, the number of times that your implemented function rand10() will be called while testing. Note that this is not an argument passed to rand10().

 

Example 1:

Input: n = 1
Output: [2]
Example 2:

Input: n = 2
Output: [2,8]
Example 3:

Input: n = 3
Output: [3,8,10]
 

Constraints:

1 <= n <= 105
 

Follow up:

What is the expected value for the number of calls to rand7() function?
Could you minimize the number of calls to rand7()?


--------------------------------------------------------------------
/**
 * The rand7() API is already defined in the parent class SolBase.
 * public int rand7();
 * @return a random integer in the range 1 to 7
 */

/*
Since 49 is not a multiple of 10, we have to use rejection sampling. Our desired range is integers from 1 to 40, which we can return the answer immediately. If not (the integer falls between 41 to 49), we reject it and repeat the whole process again.
只有1-40是uniform的结果，41-49的概率会大一些

最少要call两次
*/
class Solution extends SolBase {
    public int rand10() {
        // // 这不是uniform的解法。得到12% 2 和2 %2 的结果是一样的。
        // int i = rand7() + rand7();
        // System.out.println(i);
        // return i % 10 + 1; 
        
        int val;
        do {
            int row = rand7();
            int col = rand7();
            val = (row - 1) * 7 + col;
        } while (val > 40);
        return val % 10 + 1;
    }
}
