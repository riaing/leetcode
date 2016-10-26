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
