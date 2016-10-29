I: 1
V: 5
X: 10
L: 50
C: 100
D: 500
M: 1000

字母可以重复，但不超过三次，当需要超过三次时，用与下一位的组合表示：
I: 1, II: 2, III: 3, IV: 4
C: 100, CC: 200, CCC: 300, CD: 400

s = 3978
3978/1000 = 3: MMM
978>(1000-100), 998/900 = 1: CM
78<(100-10), 78/50 = 1 :L
28<(50-10), 28/10 = XX
8<(100-1), 8/5 = 1: V
3<(5-1), 3/1 = 3: III
ret = MMMCMLXXVIII

所以可以将单个罗马字符扩展成组合形式，来避免需要额外处理类似IX这种特殊情况。
I: 1
IV: 4
V: 5
IX: 9
X: 10
XL: 40
L: 50
XC: 90
C: 100
CD: 400
D: 500
CM: 900
M: 1000

public class Solution {
    public String intToRoman(int num) {
        String[] dict = {"I", "IV", "V","IX", "X","XL","L","XC","C","CD","D","CM","M"}; //add some additional 
        int[] digit = {1,4,5,9,10,40,50,90,100,400,500,900,1000};
        String result ="";
        for( int i =12 ; i>=0; i--){ //反向，从最大的开始。 
            if(num >= digit[i]){
                int count = num/digit[i];
                num = num % digit[i];
                while(count >0){
                    result = result + dict[i];
                    count --; 
                }
            }
        }
        return result; 
    }
}
