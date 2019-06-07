/**
把string BAAE 转换成count+letter的形式 A1B2E1. 
用string来存data(a2b) instead of other data structure like map or list，不失为一种好方法
*/
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String s : strs) {
            String converted = convert(s);
            if (!map.containsKey(converted)) {
                map.put(converted, new ArrayList<String>());
            }
            map.get(converted).add(s);
        }
        List<List<String>> res = new ArrayList<List<String>>();
        for (String s : map.keySet()) {
            res.add(map.get(s));
        }
        return res;
    }
    
    // aab -> a2b1
    private String convert(String str) {
        int[] count = new int[26];
        for (char c : str.toCharArray()) {
            count[c -'a']++;
        }
        StringBuilder s = new StringBuilder();
        for (char i = 'a'; i <= 'z'; i++) {
            if (count[i - 'a'] != 0) {
                s.append(i);
                s.append(count[i-'a']);
            }
        }
        return s.toString();
    }
}
