The complement of an integer is the integer you get when you flip all the 0's to 1's and all the 1's to 0's in its binary representation.

For example, The integer 5 is "101" in binary and its complement is "010" which is the integer 2.
Given an integer n, return its complement.

 

Example 1:

Input: n = 5
Output: 2
Explanation: 5 is "101" in binary, with complement "010" in binary, which is 2 in base-10.
Example 2:

Input: n = 7
Output: 0
Explanation: 7 is "111" in binary, with complement "000" in binary, which is 0 in base-10.
Example 3:

Input: n = 10
Output: 5
Explanation: 10 is "1010" in binary, with complement "0101" in binary, which is 5 in base-10.
 

Constraints:

0 <= n < 109

-------------------------------------------bitwise XOR --------------------------------------
 
Solution #
Recall the following properties of XOR:

It will return 1 if we take XOR of two different bits i.e. 1^0 = 0^1 = 1.

It will return 0 if we take XOR of two same bits i.e. 0^0 = 1^1 = 0. In other words, XOR of two same numbers is 0.

It returns the same number if we XOR with 0.

From the above-mentioned first property, we can conclude that XOR of a number with its complement will result in a number that has all of its bits
set to 1. For example, the binary complement of “101” is “010”; and if we take XOR of these two numbers, we will get a number with all bits set to 1, 
i.e., 101 ^ 010 = 111

We can write this fact in the following equation:

number ^ complement = all_bits_set
Let’s add ‘number’ on both sides:

number ^ number ^ complement = number ^ all_bits_set
From the above-mentioned second property:

0 ^ complement = number ^ all_bits_set
From the above-mentioned third property:

complement = number ^ all_bits_set
We can use the above fact to find the complement of any number.

### How do we calculate ‘all_bits_set’? 
 One way to calculate all_bits_set will be to first count the bits required to store the given number. We can then use the fact that for a number 
 which is a complete power of ‘2’ i.e., it can be written as pow(2, n), if we subtract ‘1’ from such a number, we get a number which has ‘n’ least 
 significant bits set to ‘1’. For example, ‘4’ which is a complete power of ‘2’, and ‘3’ (which is one less than 4) has a binary representation of ‘11’ 
 i.e., it has ‘2’ least significant bits set to ‘1’. ->得到总共多少bit count后，2^bitCount - 1 就能得bitCount全是1的数
 
/*
Time complexity of this solution is O(b)O(b) where ‘b’ is the number of bits required to store the given number.
*/
class Solution {
    public int bitwiseComplement(int n) {
        //1. 找总共多少位
        int bitCount = 0;
        int num = n;
        
        if (n == 0) {
            bitCount = 1;
        }
        while (num > 0) {
            bitCount++;
            num = num >> 1; 
        }
        
        // 2. find all bit set to 1 
        // for a number which is a complete power of '2' i.e., it can be written as pow(2, n), if we
    // subtract '1' from such a number, we get a number which has 'n' least significant bits set to '1'.
    // For example, '4' which is a complete power of '2', and '3' (which is one less than 4) has a binary 
    // representation of '11' i.e., it has '2' least significant bits set to '1' 
        int allBitSet = (int) Math.pow(2, bitCount) - 1;
        System.out.println(allBitSet);
        
        
        //3. XOR 1111..1 和 n 去取得 complement
        return n ^ allBitSet;
    }
}
