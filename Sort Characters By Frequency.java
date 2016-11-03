Given a string, sort it in decreasing order based on the frequency of characters.

Example 1:

Input:
"tree"

Output:
"eert"

Explanation:
'e' appears twice while 'r' and 't' both appear once.
So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
Example 2:

Input:
"cccaaa"

Output:
"cccaaa"

Explanation:
Both 'c' and 'a' appear three times, so "aaaccc" is also a valid answer.
Note that "cacaca" is incorrect, as the same characters must be together.
Example 3:

Input:
"Aabb"

Output:
"bbAa"

Explanation:
"bbaA" is also a valid answer, but "Aabb" is incorrect.
Note that 'A' and 'a' are treated as two different characters.

//use HashMap to store char and frequency, use an array to sort the frequency, iterate the array, to print the corresponding char 
public class Solution {
    public String frequencySort(String s) {
           StringBuilder res = new StringBuilder();
        if(s == null || s.length() == 0 ){
            return res.toString(); 
        }
        Map<Character, Integer> map = new HashMap<Character, Integer>(); //key: char, value: it's frequency 
        for ( int i = 0; i < s.length(); i ++ ){
            if( map.containsKey(s.charAt(i))){
                map.put (s.charAt(i), map.get(s.charAt(i))+1); 
            } 
            else {
                map.put(s.charAt(i), 1);
            }
        }
        
        int[] count = new int[map.size()]; 
        int i =0; 
        for ( Character c : map.keySet()){ //add frequency to array and sort it. 
            count[i] = map.get(c);
            i ++; 
        }
        Arrays.sort(count); 
        
     
        for ( int j = count.length -1; j >= 0; j --){ // go through the array, from the highest frequency to lowest 
            for (Character c : map.keySet()){ //find corresponding character to that frequency 
                if (map.get(c) == count[j]){
                    for ( int n = 0; n < count[j]; n ++){ //print out the character due to frequency 
                        res = res.append(c);
                    }
                    map.remove(c); //remove the char after print, from map 
                    break;
                }
            }
        }
        return res.toString(); // cast back to string 
    }
}
