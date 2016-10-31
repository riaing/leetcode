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
