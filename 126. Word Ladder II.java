Given two words (beginWord and endWord), and a dictionary’s word list, find all shortest transformation sequence(s) from beginWord to endWord, such that:

Only one letter can be changed at a time
Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
Note:

Return an empty list if there is no such transformation sequence.
All words have the same length.
All words contain only lowercase alphabetic characters.
You may assume no duplicates in the word list.
You may assume beginWord and endWord are non-empty and are not the same.
Example 1:

Input:
beginWord = "hit",
endWord = "cog",
wordList = ["hot","dot","dog","lot","log","cog"]

Output:
[
  ["hit","hot","dot","dog","cog"],
  ["hit","hot","lot","log","cog"]
]
Example 2:

Input:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log"]

Output: []

Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.


核心思想：先BFS消减branch，以便减少DFS时的时间
//先利用BFS，找出每个词能衍生出来的词（child）以及每个词离startword的最短步数。
//再DFS，找到最短步数的所有路径

-----------------------------DFS写法一，在一开始add word：注意要在当前层回溯-----------------------------
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        
        List<List<String>> results = new ArrayList<List<String>>();
        List<String> result = new ArrayList<String>();
        
        // Map to store key as a word, and value as the list of word this word could transformed to(it's child)
        Map<String, List<String>> child = new HashMap<String, List<String>>();
        // How many step this word from starting node. 
        Map<String, Integer> distance = new HashMap<String, Integer>(); 
        distance.put(beginWord, 1);
        
        Queue<String> queue = new LinkedList<String>();
        queue.offer(beginWord);
        
        bfs(new HashSet<String>(wordList), queue, child, distance);
        
        //result.add(beginWord);
        dfs(beginWord, endWord, results, result, child, distance);
        
        return results;
        
    }
    
    private void bfs(Set<String> set, Queue<String> queue, Map<String, List<String>> child, Map<String, Integer> distance) {
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String a = queue.poll();
                child.put(a, new ArrayList<String>());
                //O(26*k)
                List<String> nextCandidates = transformable(a, set);
               
                for (String next : nextCandidates) {
                  
                        child.get(a).add(next);
                        //如果这个值之前没出现，说明当前时最短
                        if (!distance.containsKey(next)) {
                            distance.put(next, distance.get(a)+1);
                            queue.offer(next);
                        }
                }
            }
        }
    }
    
    private void dfs(String beginWord, String endWord, List<List<String>> results, List<String> result, Map<String, List<String>> child, Map<String, Integer> distance) {  
        //在哪层加，就得在哪层remove。
        result.add(beginWord);
        if (beginWord.equals(endWord)) {
            results.add(new ArrayList<String>(result));
        }
        else{
            for (String s : child.get(beginWord)) {
                if (distance.get(s) == distance.get(beginWord)+1) {
                dfs(s, endWord, results, result, child, distance);
                }
            }
        }
        result.remove(result.size()-1);
    }
    
    //sol1 - This takes O(n*k)
    // private boolean transformable(String a, String b) {
    //     int differLetter = 0; 
    //     for (int i = 0; i < a.length(); i++) {
    //         if (a.charAt(i) != b.charAt(i)) {
    //             differLetter++;
    //         }
    //     }
    //     return differLetter == 1; 
    // }
    
    // sol2 - this takes O(26*k)
    private List<String> transformable(String a, Set<String> set) {

        List<String> res = new ArrayList<String>();
        for (int i = 0; i < a.length(); i++) {
            for (char j = 'a'; j <= 'z'; j++) {
                String next = a.substring(0, i) + j + a.substring(i+1);
          
                if (set.contains(next)) {
                   
                    res.add(next);
                }
            }
        }
         
        return res;
    }
}

---------------------DFS写法二：平时的写法，此时就预先把startword加上------------------------------------------
//先利用BFS，找出每个词能衍生出来的词（child）以及每个词离startword的最短步数。
//再dfs，找到最短步数的所有路径
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        
        List<List<String>> results = new ArrayList<List<String>>();
        List<String> result = new ArrayList<String>();
        
        // Map to store key as a word, and value as the list of word this word could transformed to(it's child)
        Map<String, List<String>> child = new HashMap<String, List<String>>();
        // How many step this word from starting node. 
        Map<String, Integer> distance = new HashMap<String, Integer>(); 
        distance.put(beginWord, 1);
        
        Queue<String> queue = new LinkedList<String>();
        queue.offer(beginWord);
        
        bfs(new HashSet<String>(wordList), queue, child, distance);
        //必须预先加上beginword
        result.add(beginWord);
        dfs(beginWord, endWord, results, result, child, distance);
        
        return results;
        
    }
    
    private void bfs(Set<String> set, Queue<String> queue, Map<String, List<String>> child, Map<String, Integer> distance) {
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String a = queue.poll();
                child.put(a, new ArrayList<String>());
                //O(26*k)
                List<String> nextCandidates = transformable(a, set);
               
                for (String next : nextCandidates) {
                  
                        child.get(a).add(next);
                        //如果这个值之前没出现，说明当前时最短
                        if (!distance.containsKey(next)) {
                            distance.put(next, distance.get(a)+1);
                            queue.offer(next);
                        }
                }
            }
        }
    }
    
    private void dfs(String beginWord, String endWord, List<List<String>> results, List<String> result, Map<String, List<String>> child, Map<String, Integer> distance) {  
  
        if (beginWord.equals(endWord)) {
            results.add(new ArrayList<String>(result));
            return;
        }
        else{
            for (String s : child.get(beginWord)) {
                if (distance.get(s) == distance.get(beginWord)+1) {
                result.add(s);
                dfs(s, endWord, results, result, child, distance);
                result.remove(result.size()-1);
                }
            }
        }
    }

    // this takes O(26*k)
    private List<String> transformable(String a, Set<String> set) {

        List<String> res = new ArrayList<String>();
        for (int i = 0; i < a.length(); i++) {
            for (char j = 'a'; j <= 'z'; j++) {
                String next = a.substring(0, i) + j + a.substring(i+1);
          
                if (set.contains(next)) {
                   
                    res.add(next);
                }
            }
        }
         
        return res;
    }
}

------------------------BFS优化------------------------------------------------------------------------------
BFS+DFS运用; 先用BFS剪枝，核心是DFS
BFS一般求最短路径，当问道求所有的最短路径时，我们用一个flag，当找到一个解时，flag设为true，并且遍历完当前层，出层后（进入到下一次while前），
查flag，为true即可return，出while loop"
  class Solution {
    List<List<String>> result = new ArrayList<>();
    Map<String, List<String>> nodeNeighbors = new HashMap<>();
    Map<String, Integer> distance = new HashMap<>();
    Set<String> dictionary;
    
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        dictionary = new HashSet<>(wordList);
        if(!dictionary.contains(endWord))
            return result;
        
        List<String> solution = new ArrayList<>();
        
        dictionary.add(beginWord);
        bfs(beginWord, endWord);
        dfs(beginWord, endWord, solution);
        
        return result;
    }
    
    public List<String> getNeighbors(String current) {
        List<String> neighbors = new ArrayList<>();
        char ch[] = current.toCharArray();
        for(char c = 'a'; c <= 'z'; c++) {
            for(int i = 0; i < ch.length; i++) {
                if(ch[i] == c)
                    continue;
                char old_char = ch[i];
                ch[i] = c;
                if(dictionary.contains(String.valueOf(ch)))
                    neighbors.add(String.valueOf(ch));
                ch[i] = old_char;
            }
        }
        
        return neighbors;
    }
    
    public void bfs(String start, String end) {
        for(String str : dictionary)
            nodeNeighbors.put(str, new ArrayList<String>());
        
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        distance.put(start, 0);
        
        while(!queue.isEmpty()) {
            int count = queue.size();
            boolean foundEnd = false;
            for(int i = 0; i < count; i++) {
                String current = queue.poll();
                int current_distance = distance.get(current);
                List<String> neighbors = getNeighbors(current);
                
                for(String neighbor : neighbors) {
                    nodeNeighbors.get(current).add(neighbor);
                    if(!distance.containsKey(neighbor)) {
                        distance.put(neighbor, current_distance + 1);
                        if(end.equals(neighbor))
                            foundEnd = true;
                        else
                            queue.offer(neighbor);
                    }
                }
            }
            
            if(foundEnd)
                break;
        }
    }
    
    public void dfs(String current, String end, List<String> solution) {
        solution.add(current);
        if(end.equals(current))
            result.add(new ArrayList<String>(solution));
        else {
            for(String neighbor : nodeNeighbors.get(current)) {
                if(distance.get(neighbor) == distance.get(current) + 1)
                    dfs(neighbor, end, solution);
            }
        }
        solution.remove(solution.size() - 1);
    }
}



