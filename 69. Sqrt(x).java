Implement int sqrt(int x).

Compute and return the square root of x.


对于一个非负数n，它的平方根不会小于大于（n/2+1)。在[0, n/2+1]这个范围内可以进行二分搜索，求出n的平方根。


public class Solution {
    public int mySqrt(int x) {
        long start = 0;
        // 这里直接用x的一半，而不是x
        long end = x/2+1;
        
        while(start< end){
        //看到这种数字的题，特别是乘法，一定要考虑overflow的问题,所以用long
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

------------------- 2022.5 自己的模板 -------------------------------------------------
    
class Solution {
    public int mySqrt(int x) {
        if (x <= 1) {
            return x;
        }
        long start = 2;
        long end =  x/2; // 必须是long不然会超
        while (start <= end) {
            long mid = start + (end - start) / 2; 
            if (mid * mid == x) { 
                return (int) mid;
            }
            else if (mid * mid < x) {
                start = mid + 1;
            }
            else {
                end = mid - 1;
            }
        }
        return (int) end; 
    }
}
    
