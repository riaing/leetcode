
二分法递归
public class Solution {
    public double myPow(double x, int n) {
        if(n >= 0){
            return powPos(x, n);
        }
        else{
            return 1/(powPos(x, -1-n)*x); //avoid over flow at n = -2147483648
            //now n is pos and is 2147483647  
        }
    }
     private double powPos(double x, int n){   
        if(n ==0){
            return 1;
        }
        
        double result = powPos(x, n/2);  
        result = result* result;
        
        if(n%2 ==1){
            result =result*x;
        }
        return result; 
        
    }
}
