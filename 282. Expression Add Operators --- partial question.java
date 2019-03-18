Given a string, return all it's possible combination of operations(+,-,*)
input： "123"
output: ["1+2+3","1+2-3","1+2*3","1+23","1-2+3","1-2-3","1-2*3","1-23","1*2+3","1*2-3","1*2*3","1*23","12+3","12-3","12*3","123"]


---------------------- 每次减少string-------------------------------------------------------------------------------
class Solution {
    private String[] operator = {"+", "-", "*"};
    
    public List<String> addOperators(String num, int target) {
       
        List<String> tmp = new ArrayList<String>();
        dfs(num, tmp, "");
        return tmp;
    }
    
    private void dfs(String num, List<String> res, String cur) {
            
         if (num.length() == 0) {
             res.add(cur);
             return;
         }
        
        for (int i = 0; i<num.length(); i++) {
            String rec = num.substring(0, i+1);
            if (i == num.length() - 1) {            
                 dfs(num.substring(i+1), res, cur + rec);
            } else {
             for (int j = 0; j< operator.length; j++) {
                 dfs(num.substring(i+1), res, cur + rec + operator[j]);
             }
        }
        }
    }
}

---------------------------------每次增加index ------------------------------------------------------------
class Solution {
    private String[] operator = {"+", "-", "*"};
    
    public List<String> addOperators(String num, int target) {
       
        List<String> tmp = new ArrayList<String>();
        dfs(num, tmp, "", 0);
        return tmp;
    }
    
    private void dfs(String num, List<String> res, String cur, int index) {
            
         if (index == num.length()) {
             res.add(cur);
             return;
         }
        
        for (int i = index; i<num.length(); i++) {
            String rec = num.substring(index, i+1);
            if (i == num.length() - 1) {            
                 dfs(num, res, cur + rec, i+1);
            } else {
                for (int j = 0; j< operator.length; j++) {
                    dfs(num, res, cur + rec + operator[j], i+1);
                }
            }
        }
    }
}
