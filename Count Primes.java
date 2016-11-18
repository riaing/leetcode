http://blog.csdn.net/lisonglisonglisong/article/details/45309651
如果一个数是另一个数的倍数，那这个数肯定不是素数。利用这个性质，我们可以建立一个素数数组，
从2开始将素数的倍数都标注为不是素数。第一轮将4、6、8等表为非素数，然后遍历到3，发现3没有被标记为非素数，
则将6、9、12等标记为非素数，一直到N为止，再数一遍素数数组中有多少素数。

// time = N(logN) ? 

public class Solution {
    public int countPrimes(int n) {
        
        boolean[] Prime = new boolean[n];
        Arrays.fill(Prime, true); 
        for (int i =2; i < n ; i ++ ){ O(N) 
            if(Prime[i]){
                if ( i * i < n ){ //实际上如果 i * i 大于 n的话， 那么 i后面没被false的都是素数了
                       //make it 2倍 4倍。。。都为non prime
                    for ( int j = i *2 ; j < n ; j=j + i){ O(logN) 
                        Prime[j] = false; 
                    }
                }
             
            }
        }
        int count = 0; 
        for ( int x = 2; x < n; x ++  ){
            if ( Prime[x]){
                count ++ ; 
            }
        }
        return count; 
    }
}
