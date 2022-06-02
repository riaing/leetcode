You are given two string arrays username and website and an integer array timestamp. All the given arrays are of the same length and the tuple [username[i], website[i], timestamp[i]] indicates that the user username[i] visited the website website[i] at time timestamp[i].

A pattern is a list of three websites (not necessarily distinct).

For example, ["home", "away", "love"], ["leetcode", "love", "leetcode"], and ["luffy", "luffy", "luffy"] are all patterns.
The score of a pattern is the number of users that visited all the websites in the pattern in the same order they appeared in the pattern.

For example, if the pattern is ["home", "away", "love"], the score is the number of users x such that x visited "home" then visited "away" and visited "love" after that.
Similarly, if the pattern is ["leetcode", "love", "leetcode"], the score is the number of users x such that x visited "leetcode" then visited "love" and visited "leetcode" one more time after that.
Also, if the pattern is ["luffy", "luffy", "luffy"], the score is the number of users x such that x visited "luffy" three different times at different timestamps.
Return the pattern with the largest score. If there is more than one pattern with the same largest score, return the lexicographically smallest such pattern.

 

Example 1:

Input: username = ["joe","joe","joe","james","james","james","james","mary","mary","mary"], timestamp = [1,2,3,4,5,6,7,8,9,10], website = ["home","about","career","home","cart","maps","home","home","about","career"]
Output: ["home","about","career"]
Explanation: The tuples in this example are:
["joe","home",1],["joe","about",2],["joe","career",3],["james","home",4],["james","cart",5],["james","maps",6],["james","home",7],["mary","home",8],["mary","about",9], and ["mary","career",10].
The pattern ("home", "about", "career") has score 2 (joe and mary).
The pattern ("home", "cart", "maps") has score 1 (james).
The pattern ("home", "cart", "home") has score 1 (james).
The pattern ("home", "maps", "home") has score 1 (james).
The pattern ("cart", "maps", "home") has score 1 (james).
The pattern ("home", "home", "home") has score 0 (no user visited home 3 times).
Example 2:

Input: username = ["ua","ua","ua","ub","ub","ub"], timestamp = [1,2,3,4,5,6], website = ["a","b","a","a","b","c"]
Output: ["a","b","a"]
 

Constraints:

3 <= username.length <= 50
1 <= username[i].length <= 10
timestamp.length == username.length
1 <= timestamp[i] <= 109
website.length == username.length
1 <= website[i].length <= 10
username[i] and website[i] consist of lowercase English letters.
It is guaranteed that there is at least one user who visited at least three websites.
All the tuples [username[i], timestamp[i], website[i]] are unique.

----------------------------------coding题，理解题意 -----------------------------------
class Info {
    String user;
    int time;
    String web;
    
    public Info(String user, int time, String web) {
        this.user = user;
        this.time = time;
        this.web = web;
    }
}
class Solution {
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        // 1. sort by time 
        List<Info> input = new ArrayList<Info>();
        for (int i = 0; i < username.length; i++) {
            input.add(new Info(username[i], timestamp[i], website[i])); 
        }
        Collections.sort(input, (a, b) -> Integer.compare(a.time, b.time));  // sort by increasing time 
        // 2. get user's website 
        Map<String, List<String>> userWebsites = new HashMap<>();
        for (Info info : input) {
            String usr = info.user;
            userWebsites.putIfAbsent(usr, new ArrayList<>());
            userWebsites.get(usr).add(info.web);
        }
        System.out.println(userWebsites); 
        
        int maxCount = 0;
        String maxPattern = ""; 
        // 3. for each user, find it's pattern combination 
        Map<String, Integer> patternCount = new HashMap<>();
        for (String user : userWebsites.keySet()) {
            Set<String> ptns = getPattern(userWebsites.get(user)); 
            // add into count map 
            for (String ptn : ptns) {
                patternCount.put(ptn, patternCount.getOrDefault(ptn, 0) + 1); 
                // find the max 
                if (maxCount < patternCount.get(ptn) || (maxCount == patternCount.get(ptn) && ptn.compareTo(maxPattern) < 0)) { // maxPattern < cur by lexicographically
                    maxCount = patternCount.get(ptn);
                    maxPattern = ptn;
                }
            }
        } 
        // 4. split max pattern 
        return Arrays.stream(maxPattern.split(",")).collect(Collectors.toList());
        
    }
    
    private Set<String> getPattern(List<String> input) { // must be set to remove dup pattern! 因为题目问的是每个pattern总共有多少个distinct user访问。 如果问每pattern的所有user的总访问量，这里才用List
        Set<String> output = new HashSet<String>(); 
        for (int i = 0; i < input.size() - 2; i++) {
            for (int j = i+1; j < input.size() -1; j++) {
                for (int k = j+1; k < input.size(); k++) {
                    String cur = input.get(i) + "," + input.get(j) + "," + input.get(k);
                    output.add(cur);
                }
            }
        }
        return output; 
    }
}
