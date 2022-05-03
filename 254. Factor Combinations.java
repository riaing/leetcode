/*
https://www.cnblogs.com/grandyang/p/5332722.html

于题目中说明了1和n本身不能算其因子，那么可以从2开始遍历到n，如果当前的数i可以被n整除，说明i是n的一个因子，将其存入一位数组 out 中，然后递归调用 n/i，此时不从2开始遍历，
而是从i遍历到 n/i，停止的条件是当n等于1时，如果此时 out 中有因子，将这个组合存入结果 res 中

！！本题重点在于去重：#1.类似于subset题，要保证加入的每个数都大于等于前一个数：可以避免 223，232中232这个重复解 -> 需要记录最后加入的数
*/
class Solution {
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        helper(n, new ArrayList<Integer>(), res, 2);
        return res; 
        
        
    }
    private void helper(int n, List<Integer> cur, List<List<Integer>> res, int start) {
        if (n == 1 && cur.size() > 1) {
            res.add(new ArrayList<Integer>(cur));
            return;
        }
        for (int i = start; i <= n; i++) {
            if (n % i == 0) {
                cur.add(i);
                helper(n/i, cur, res, i);
                cur.remove(cur.size()-1);
            }
        }
    }
}

------------ 2022. D&C 解法， 并不比DFS好 -------------------------------------------------------------------------------
/*
本题重点在于去重：#1.类似于subset题，要保证加入的每个数都大于等于前一个数：可以避免 223，232中232这个重复解 -> 需要记录最后加入的数

D&C 做法：
#2 - 只用考虑除数为 2 - square root of n ： 12 = 3*4 = 4*3. 4*3是重复 
*/
class Solution {
    public List<List<Integer>> getFactors(int n) {
       return helper(n, 2);
    }
    
    private List<List<Integer>> helper(int n, int lastDivisor) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();

        for (int i = lastDivisor; i <= (int) Math.floor(Math.sqrt(n)); i++) { // #1,2 
            if (n % i == 0) {
                res.add(Arrays.asList(i, n / i));
                List<List<Integer>> remaining = helper(n / i, i); 
               
                for (List<Integer> list : remaining) {
                    List<Integer> cur = new ArrayList<Integer>();
                    cur.add(i);
                    cur.addAll(list);
              
                    res.add(cur);
                }
            }
        }
        return res; 
    }
}
