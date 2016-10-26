Given an array of strings, group anagrams together.

For example, given: ["eat", "tea", "tan", "ate", "nat", "bat"], 
Return:

[
  ["ate", "eat","tea"],
  ["nat","tan"],
  ["bat"]
]
时间 O(NKlogK) 空间 O(N)

思路
判断两个词是否是变形词，最简单的方法是将两个词按字母排序，看结果是否相同。这题中我们要将所有同为一个变形词词根的词归到一起，最快的方法则是用哈希表。所以这题就是结合哈希表和排序。我们将每个词排序后，根据这个键值，找到哈希表中相应的列表，并添加进去。为了满足题目字母顺序的要求，我们输出之前还要将每个列表按照内部的词排序一下。可以直接用Java的Collections.sort()这个API。

注意
将字符串排序的技巧是先将其转换成一个char数组，对数组排序后再转回字符串
public class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<List<String>>(); 
        if(strs == null || strs.length == 0){
            return result; 
        }
        char[][] split = new char[strs.length][]; 
        Map<String, List<String>> map = new HashMap<String, List<String>>(); 
       
        for (int i =0; i<strs.length;i ++){
	    	   split[i] = strs[i].toCharArray(); //先化为array
	    	   Arrays.sort(split[i]);  //sort array 
	    	   String key = new String(split[i]); //再化为 string，因为map中key不能为 char[] ？ 
	    	   
	    	   if (!map.containsKey(key)){
	    	       List<String> cur = new ArrayList<String>();
	    	       map.put(key, cur); //先加个空的
	    	   }
	    	   
	    	    map.get(key).add(strs[i]); //每次加入新数
 	    
	    	     //map.put(key, map.get(key).add(strs[i])); //why don't work? boolean cant convert to list<string> 
	    }
	    
	    for (String key : map.keySet()){
	     
	        result.add(map.get(key));
	    }
	    return result; 
	    
	    
	       
	      
        
    }
}
