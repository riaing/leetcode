Given an integer array data representing the data, return whether it is a valid UTF-8 encoding (i.e. it translates to a sequence of valid UTF-8 encoded characters).

A character in UTF8 can be from 1 to 4 bytes long, subjected to the following rules:

For a 1-byte character, the first bit is a 0, followed by its Unicode code.
For an n-bytes character, the first n bits are all one's, the n + 1 bit is 0, followed by n - 1 bytes with the most significant 2 bits being 10.
This is how the UTF-8 encoding would work:

     Number of Bytes   |        UTF-8 Octet Sequence
                       |              (binary)
   --------------------+-----------------------------------------
            1          |   0xxxxxxx
            2          |   110xxxxx 10xxxxxx
            3          |   1110xxxx 10xxxxxx 10xxxxxx
            4          |   11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
x denotes a bit in the binary form of a byte that may be either 0 or 1.

Note: The input is an array of integers. Only the least significant 8 bits of each integer is used to store the data. This means each integer represents only 1 byte of data.

 

Example 1:

Input: data = [197,130,1]
Output: true
Explanation: data represents the octet sequence: 11000101 10000010 00000001.
It is a valid utf-8 encoding for a 2-bytes character followed by a 1-byte character.
Example 2:

Input: data = [235,140,4]
Output: false
Explanation: data represented the octet sequence: 11101011 10001100 00000100.
The first 3 bits are all one's and the 4th bit is 0 means it is a 3-bytes character.
The next byte is a continuation byte which starts with 10 and that's correct.
But the second continuation byte does not start with 10, so it is invalid.
 

Constraints:

1 <= data.length <= 2 * 104
0 <= data[i] <= 255
Accepted
68,943
Submissions
175,405
Seen this question in a real interview before?

Yes

No
0 ~ 6 months6 months ~ 1 year1 year ~ 2 years

Google
|
3
All you have to do is follow the rules. For a given integer, obtain its binary representation in the string form and work with the rules given in the problem.
An integer can either represent the start of a UTF-8 character, or a part of an existing UTF-8 character. There are two separate rules for these two scenarios in the problem.
If an integer is a part of an existing UTF-8 character, simply check the 2 most significant bits of in the binary representation string. They should be 10. If the integer represents the start of a UTF-8 character, then the first few bits would be 1 followed by a 0. The number of initial bits (most significant) bits determines the length of the UTF-8 character.

Note: The array can contain multiple valid UTF-8 characters.
  
  
  ------------------------------ bit ------------
  /*
https://www.youtube.com/watch?v=0s4M9Y1ue5o&ab_channel=HuaHua

本题的input可能代表n个character，一个char最多为4个数字，最少为1个数字。
对于input，首先检查第一个数字判断出当前char共有几个数字, by check prefix. 
 - 再对这几个数字check 是否为10开头

如何查一个数的prefix？把他右位移n位只剩prefix。
本题number最大255.说明最多log2（255）= 8位数字
如果取前三位：number >> 5
取前两位：number >> 6
前一位：number >> 7 

O（N) 

*/
class Solution {
    public boolean validUtf8(int[] data) {
        int left = 0; // 标记当前数字
        for (int d : data) {      
          if (left == 0) { 
            if ((d >> 3) == 0b11110) left = 3;  // 当前char还剩三个要查
            else if ((d >> 4) == 0b1110) left = 2;
            else if ((d >> 5) == 0b110) left = 1;
            else if ((d >> 7) == 0b0) left = 0;
            else return false;
          } else {
            if ((d >> 6) != 0b10) return false;
            --left; // 减为0后开始下一个char的check 
          }
        }
        return left == 0; // 如果不等于0，说明char没被encode完整
    }
}
