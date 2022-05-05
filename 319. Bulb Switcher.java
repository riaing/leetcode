There are n bulbs that are initially off. You first turn on all the bulbs, then you turn off every second bulb.

On the third round, you toggle every third bulb (turning on if it's off or turning off if it's on). For the ith round, you toggle every i bulb. For the nth round, you only toggle the last bulb.

Return the number of bulbs that are on after n rounds.

 

Example 1:


Input: n = 3
Output: 1
Explanation: At first, the three bulbs are [off, off, off].
After the first round, the three bulbs are [on, on, on].
After the second round, the three bulbs are [on, off, on].
After the third round, the three bulbs are [on, off, off]. 
So you should return 1 because there is only one bulb is on.
Example 2:

Input: n = 0
Output: 0
Example 3:

Input: n = 1
Output: 1
 

Constraints:

0 <= n <= 109

-----------------------------------------------
/*
BF: 按要求每次 turn on or off -> 超时

Math解法: https://www.youtube.com/watch?v=8sF5HIJxlXE&ab_channel=CatRacketCode-LeetCodeJavaScript 
1. 最后开着的灯泡肯定被toggle奇数次，因为第一轮全开亮
2. 对于灯泡i，会不会在第d轮被toggle，取决于 d % i == 0. eg: 第二个灯泡，在第2，4，6，8.。。轮被toggle
3. 所以对于一个灯泡i，被toggle的次数等于所有因子。 eg：灯泡12 = 1*12 = 2*6 = 3*4 ：说明在1，12，2，6，3，4 轮会被toggle。总共有6次toggle，所以最后会off
    - 注意因子要去重. eg 16 = 1*16 = 2*8 = 4*4 ->去重4 = 5次
    - 所以如果一个数可以被开平方 = 用奇数次toggle = 最后会on。
4. 题目变成求1-n中有多少个数能被开平方
 - 再接下来就是找规律了， 找几个例子走一走：n=1时，只有1能被开根号，答案是1
                                      n = 3是，只有1， 答案是1 
                                    n = 5时，1，4能被开根号，答案是 1+1= 2 
                                    n = 9时， 1，4，3能被开，答案是1+1+1 = 3 
                                    n = 10时，也是3、
                                    所以得出 floor squareroot n就是结果
*/
class Solution {
//     public int bulbSwitch(int n) {
//         if (n <= 1) {
//             return n;
//         }
        
//         int[] bulbs = new int[n]; // 0 - on 
//         for (int i = 2; i < n; i++) {
//             int time = 1; 
//             while (time * i - 1 < n) {
//                 bulbs[time*i-1] = 1 - bulbs[time*i-1]; // flip the value 
//                 time++;
//             }
//         }
//         // at nth round
//         bulbs[n-1] = 1 - bulbs[n-1]; 
//         // count how many 0s 
//         int on = 0;
//         for (int bulb : bulbs) {
//             if (bulb == 0) {
//                 on++;
//             }
//         }
//         return on; 
//     }
    
    public int bulbSwitch(int n) {
        return (int) Math.sqrt(n);
    }
}
