Implement int sqrt(int x).

Compute and return the square root of x.


对于一个非负数n，它的平方根不会小于大于（n/2+1）（谢谢@linzhi-cs提醒）。在[0, n/2+1]这个范围内可以进行二分搜索，求出n的平方根。

public class Solution {
    public int mySqrt(int x) {
        long start = 0;
        long end = x/2+1;
        
        while(start< end){
            long mid = start + (end -start)/2;
            if(mid*mid == x){
                return (int)mid;
            }
            else if(mid*mid<x){
                start =mid +1;
            }
            else{
                end  =mid -1;
            }
        }
        
        if(end *end <= x){//就是再进了loop一遍，考虑的corner case, end =start 其实
            return (int)end;
        }
        else{
            return (int)end-1;
        }
     
        
    }
}
