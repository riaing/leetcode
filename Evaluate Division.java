Equations are given in the format A / B = k, where A and B are variables represented as strings, and k is a real number (floating point number). Given some queries, return the answers. If the answer does not exist, return -1.0.

Example:
Given a / b = 2.0, b / c = 3.0. 
queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? . 
return [6.0, 0.5, -1.0, 1.0, -1.0 ].

The input is: vector<pair<string, string>> equations, vector<double>& values, vector<pair<string, string>> queries , where equations.size() == values.size(), and the values are positive. This represents the equations. Return vector<double>.

According to the example above:

equations = [ ["a", "b"], ["b", "c"] ],
values = [2.0, 3.0],
queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ]. 

public class Solution {
        Map<String, List<String>> pair = new HashMap<String, List<String>>();
        Map<String, Double> equationRes = new HashMap<String, Double>();
    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        if (equations == null || values == null || queries == null){
            double[] none = {};
            return none; 
        }
        
        //put infor in map 
        for(int i = 0; i < values.length ; i ++ ){
            String a = equations[i][0]; 
            String b = equations[i][1]; 
            
            if(!pair.containsKey(a)){
                pair.put( a, new ArrayList<String>());
            }
            if (! pair.containsKey(b)){
                    pair.put (b, new ArrayList<String>());
                }
            
            pair.get(a).add(b); //add a--b pair
            equationRes.put (a+"%"+b, values[i]);
            
            if(equationRes.get(a+"%"+b) != 0 ) { //add b--a pair
                equationRes.put(b+"%"+a, 1.0/values[i]);
                pair.get(b).add(a); 
            }
        }
        
        
        double[] result = new double[queries.length];
        for (int j =0; j<queries.length ; j ++){
            result[j] = dfs (new HashSet<String>(), queries[j][0], queries[j][1]); 
        }
        return result; 
        
    }
    
    private double dfs(HashSet<String> visited , String a, String b){
        double curRes = -1; 
        if (!pair.containsKey(a)){
            return curRes; 
        }
        visited.add(a); 
        List<String> list = pair.get(a); // get all node folowing a 
        
        for (String s : list){ //dfs nodeSet of a 
            if (s.equals(b)){
                curRes = equationRes.get(a+"%"+s);
                break; 
            }
            else if ( !visited.contains(s) ) {
                double tmp = dfs(visited, s , b );
                if( tmp != -1){
                    curRes=  equationRes.get(a+"%"+s) * tmp; 
                    break; 
                }
            }
        }
        //visited.remove(a); //special to this problem: here don't need backtracking. since if one path not work, then the total wont work.
        return curRes; 
        
    }
}
