public class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
      
         if( prerequisites == null || prerequisites.length == 0){ //if no prerequisite 
            int[] list = new int[numCourses];
            for( int i = 0; i < numCourses; i ++){
                list[i] =i; 
            }
            return list; 
        }

        Map<Integer, Integer> indegree = new HashMap<Integer, Integer>();
        
        for(int i = 0; i <numCourses; i ++){ //initialize the sender node 
            indegree.put(i, 0); 
        }
        
        for( int i = 0; i < prerequisites.length ; i ++){ //degree of the receiver node 
            if(!indegree.containsKey(prerequisites[i][0])){
                indegree.put(prerequisites[i][0],1);
            }
            else{
                indegree.put(prerequisites[i][0], indegree.get(prerequisites[i][0])+1);
            }
        }
        int[] list = new int[numCourses]; 
        boolean[] ifVisit = new boolean[numCourses]; 
        //int countZero = 0; 
        for(int x = 0; x < numCourses; x ++ ){
             boolean hasLoop = true; //every time assume has loop 
  
            for(Integer j : indegree.keySet()){
                if(ifVisit[j] != true && indegree.get(j) ==0){
                    list[x] = j; 
                    hasLoop = false; //if having node degree = 0, 
                    ifVisit[j] = true;  
                  
                    for(int i = 0; i < prerequisites.length ; i ++){
                        if(prerequisites[i][1] == j){
                            indegree.put(prerequisites[i][0],indegree.get(prerequisites[i][0])-1);
                        }
                    }
                    break; 
                }
            }
            if(hasLoop == true ){ //if can't find degree=0 this time
                int[] empty = {};
                return empty;
            }
        



        }
        return list; //if # of node >= numCourses
        
    }
}
