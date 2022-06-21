http://blog.csdn.net/lisonglisonglisong/article/details/45309651
 首先，默认大家都知道素数(质数)吧，就是对于一个正整数n，如果能且仅能被1和n整除，那么这个数n为素数~ps：0,1不是质数。

那么对于判断一个数是否为素数，起初的做法是我们从i=2开始，判断n%i！=0，然后i++一直到n-1，如果全部满足不能整除那么此n为素数。

大家都知道其实不用判断到n-1，只要到sqrt(n)就ok了，这是为什么呢，这里就不解释了- -(本人虽然菜鸟，但还是觉得好啰嗦)

      对于任何一个整数n可以通过以上方法来判断n是否为素数，若如果要你找出从1~n中所有的素数，那是不是对于每个n都要去判断一次呢？

假如n=1000000；那么这个算法的时间复杂度为o(n*sqrt(n))，这个数有多大我就不解释了。。。那是你的计算机跑几分钟都跑不完的。所以

我在这里介绍一种时间为o(n*lgn)的算法，它的名字叫做：埃拉托斯特尼筛法。

　　终于切入正题，其实这种筛选法特别好理解。首先我们把所有的数标记为素数（true），
  就是我们从2开始，找出所有2的倍数，把它标记为不是素数（false）(当然不是素数),再找到3，找出所有3的倍数，
  也标记为不是素数（false），再找到下一个标记为（true）的数k，再去找到所有k的倍数，标记为（false），
  依次类推...那么剩下来的数就都是素数了.如果你还不理解就看代码吧，写的很详细。(visit[i]用来判断i是素数还是合数)

如果一个数是另一个数的倍数，那这个数肯定不是素数。利用这个性质，我们可以建立一个素数数组，
从2开始将素数的倍数都标注为不是素数。第一轮将4、6、8等表为非素数，然后遍历到3，发现3没有被标记为非素数，
则将6、9、12等标记为非素数，一直到N为止，再数一遍素数数组中有多少素数。

// time =  O(NloglogN）~ O(N），  空间 O(N)

public class Solution {
    public int countPrimes(int n) {
        
        boolean[] Prime = new boolean[n];
        Arrays.fill(Prime, true); 
        for (int i =2; i *i  < n ; i ++ ){ O(logn)  //实际上如果 i * i 大于 n的话， 那么 i后面没被false的都是素数了, 
        //以121 = 11*11 为例， 把11的倍数设为F时，首先是11*2 =22. 然而22已经在对2的倍数处理时做过了。原因是 11*(2) < 11* (11), 
        //所以处理2的倍数时，2的11倍肯定会被处理到。  同理，处理13的倍数时，13*2 肯定被处理过了因为是2的13倍。 
            if(Prime[i]){
                       
                    for ( int j = 2 ; j *i < n ; j ++ ){ O(logN) //make it 2倍 4倍。。。都为non prime
                    
                   // for ( int j = i ; j *i < n ; j ++ ){  //从J = I 开始就可以了，因为J =2时肯定在2的倍数时处理过了
                        Prime[j *i] = false; 
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

---------------------- 2022 ----------------
 /*
0. 空间换时间，先给个大小为n的数组
1. 从2开始，判断是不是prime，然后把他的倍数全部mark off
     // to mark off. can start from n的n倍数，因为n-1倍数已经在cur = n-1时被处理了
2. 只用mark off 2- sqrtN, 因为大于sqrtN的都会被mark off了
3. 扫一遍array拿还是prime的值

Time: 
总共是o(n)
*/
class Solution {
    public int countPrimes(int n) {
        if (n <= 1) {
            return 0;
        }
        boolean[] nonPrime = new boolean[n];
        for (int i = 2; i < Math.sqrt(n); i++) { // O(sqrtN)
            if (!nonPrime[i]) { // 是个prime，把倍数都mark off 
                // to mark off. can start from n的n倍数，因为n-1倍数已经在cur = n-1时被处理了
                for (int k = i; k * i < n; k++) { // 找prime的倍数个数，大概是 loglogN 
                    nonPrime[k*i] = true;
                }
            }
        }
        // #3  找res
        int res = 0; 
        for (int i = 2; i < n; i++) {
            if (!nonPrime[i]) {
                res++;
            }
        }
        return res; 
    }
    
    
    
    
    
    // 本题不用但有意义的code
    private boolean isPrime(int n) { // O(lgn)
        int res = 0; 
        for (int i = 2; i < Math.sqrt(n); i++) {
            if (n % i == 0) {
                res += 2;
            }
            if (res >= 2) {
                return false;
            }
        }
        return true; 
    }
}
