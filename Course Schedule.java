public class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        
       // if( numCourses == 0  || prerequisites == null || prerequisites.length == 0){
         //   return true; 
        //}

        Map<Integer, Integer> indegree = new HashMap<Integer, Integer>();
        
        for(int i = 0; i <numCourses; i ++){ //initialize the sender node 包括所有的node
            indegree.put(i, 0); 
        }
        
        for( int i = 0; i < prerequisites.length ; i ++){ //degree of the receiver node 
            if(!indegree.containsKey(prerequisites[i][1])){
                indegree.put(prerequisites[i][1],1);
            }
            else{
                indegree.put(prerequisites[i][1], indegree.get(prerequisites[i][1])+1);
            }
        }
        
        boolean[] ifVisit = new boolean[numCourses]; 
      
        for(int x = 0; x < numCourses; x ++ ){
             boolean hasLoop = true; //every time assume has loop 
            for(Integer j : indegree.keySet()){
           
                if(ifVisit[j] != true && indegree.get(j) ==0){
                    hasLoop = false; //if having node degree = 0, 
                    ifVisit[j] = true;  
                    countZero ++;
      
                    for(int i = 0; i < prerequisites.length ; i ++){
                        if(prerequisites[i][0] == j){
                            indegree.put(prerequisites[i][1],indegree.get(prerequisites[i][1])-1);
                        }
                    }
                    break; 
                }
            }

            if(hasLoop == true ){ //if can't find degree=0 this time
                return false;
            }
        }
        return true; 
        
        
    }
}

--------------- 2022.2.16 ------------------------
    class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer> res = new ArrayList<Integer>();
        // 1. initialize node map and in-degree map 
        Map<Integer, List<Integer>> nodes = new HashMap<Integer,List<Integer>>();
        Map<Integer, Integer> inDegrees = new HashMap<Integer, Integer>();
        for (int i = 0; i < numCourses; i++) {
            nodes.put(i, new ArrayList<Integer>());
            inDegrees.put(i, 0);
        }
        
         // 2, build  
        for (int i = 0; i < prerequisites.length; i++) {
            int parent = prerequisites[i][0];
            int child = prerequisites[i][1];
            nodes.get(parent).add(child);
            inDegrees.put(child, inDegrees.get(child) + 1);
        }
        
        // 3. find sources (in-degree = 0)
        Queue<Integer> sources = new LinkedList<Integer>();
        for (Map.Entry<Integer, Integer> entry : inDegrees.entrySet()) {
            if (entry.getValue() == 0) {
                sources.offer(entry.getKey());
            }
        }
        
        //4. sort 
        while (sources.size() > 0) {
            int curSource = sources.poll();
            //1. add to list 
            res.add(curSource);
            //2. decrese children nodes's indegree 
            for (int child : nodes.get(curSource)) {
                inDegrees.put(child, inDegrees.get(child)-1);
                // 3. add next 0 degree node to queue 
                if (inDegrees.get(child) == 0) {
                    sources.offer(child);
                }
            }
        }
        // 5. detect cycle 
        // way 1:
        if (res.size() != numCourses){ // 或者查 indegree map 还有没有谁的 in degree > 0 
            return false;
        }
        // way 2: 
        for (int key : inDegrees.keySet()) {
            if (inDegrees.get(key) != 0) {
                return false; 
            }
        }
        return true; 
    }
}
