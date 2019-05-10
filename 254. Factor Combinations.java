/*
https://www.cnblogs.com/grandyang/p/5332722.html
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
