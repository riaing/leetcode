All DNA is composed of a series of nucleotides abbreviated as A, C, G, and T, for example: "ACGAATTCCG". When studying DNA, it is sometimes useful to identify repeated sequences within the DNA.

Write a function to find all the 10-letter-long sequences (substrings) that occur more than once in a DNA molecule.

For example,

Given s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT",

Return:
["AAAAACCCCC", "CCCCCAAAAA"].

public class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> result = new ArrayList<String>(); 
        if ( s == null || s.length() < 10){
            return result; 
        }
        Map<String, Integer> map = new HashMap<String, Integer>();
        
        String newStr = s.substring(0,10); //exclusive the end 
        map.put(newStr, 1); 
        
        for ( int i = 10; i < s.length(); i ++){
            newStr = newStr.substring(1) + s.charAt(i); //string can concatenate using this way 
            if(map.containsKey(newStr)){
                map.put(newStr, map.get(newStr) +1); 
            }
            else{
                map.put(newStr, 1); 
            }
        }
        
        for (String key : map.keySet()){
            if(map.get(key) >=2){
                result.add(key);
            } 
        }
        return result; 
        
        
    }
}

--------------- 2022 ----------------------------------
    
    /*
N - length of string, L - required length, here is 10 
BF:  
Time complexity : O((N-L)*L), N-L次substring，每次substring是O（L）
space: O(N-L)*L : 总共build那么多个string

可以做到O（N-L），要把string化为0100111然后hash
*/
class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        int start = 0;
        Set<String> seen = new HashSet<String>();
        Set<String> res = new HashSet<String>();
        for (int end = 0; end < s.length(); end++) {
            while (s.substring(start, end+1).length() > 10) {
                start++;
            }
            String cur = s.substring(start, end+1);
            if (cur.length() == 10) {
                if (seen.contains(cur)) {
                    res.add(cur);
                }
                else{
                    seen.add(cur);
                }
            }
        }
        return new ArrayList<String>(res); 
    }
}
    
