/*
可能存在cycle，可以自定义如果cycle存在如何返回

给出每个file的dependent files, 求valid compile file的顺序。
input：HashMap<String, List<String>> deps
example:
{
"file3": {"file2", "file1"},
"file2": {"file1"},
"file4": 
// cycle 
"file5": {file6}
"file6": {file5}
}  -> 1,4,2,3 + (cycle)5, 6 
*/
public class Main {
    public static void main(String[] args) {
        Map<String, List<String>> files = new HashMap<>();
        files.put("3", Arrays.asList("2", "1"));
        files.put("2", new ArrayList<>());
        files.get("2").add("1");
        files.put("4", new ArrayList<>());
        // cycle 部分
        files.put("5", new ArrayList<>());
        files.get("5").add("6");
        files.put("6", new ArrayList<>());
        files.get("6").add("5");
        System.out.println(buildOrder(files));
    }
    
    public static List<String> buildOrder(Map<String, List<String>> files) {
        // 1. build maps 
        Map<String, Integer> indegree = new HashMap<>();
        Map<String, Set<String>> children = new HashMap<>(); 
        for (String key : files.keySet()) {
            indegree.put(key, indegree.getOrDefault(key, 0) + files.get(key).size());
            children.putIfAbsent(key, new HashSet<>());
            // go through parent 
            for (String parent : files.get(key)) {
                indegree.putIfAbsent(parent, 0); 
                children.putIfAbsent(parent, new HashSet<>());
                children.get(parent).add(key);
            }
        }
        System.out.println("indegree " + indegree); 
        // 2. add into queue
        Queue<String> q = new LinkedList<>();
        for (String f : indegree.keySet()) {
            if (indegree.get(f) == 0) {
                q.offer(f);
            }
        }
        // 3. topological sort 
        List<String> res = new ArrayList<>();
        while(!q.isEmpty()) {
            String file = q.poll();
            res.add(file);
            // children's indegree -1, and add to queue 
            for (String c : children.get(file)) {
                indegree.put(c, indegree.get(c) - 1); 
                if (indegree.get(c) == 0) {
                    q.offer(c);
                }
            }
        }
        // 4. follow up - 有cyle：那就是indegree map里还有元素 indegree > 0. print 他们
        for (String key : indegree.keySet()) {
            if (indegree.get(key) > 0) {
                res.add(key);
            }
        }
        
        return res; 
    }
    
    
}
