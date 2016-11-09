Given an array of N elements and a number X compute the exact number of pairs (a,b) of distinct elements so that a+b <= X


X = 6
Array: 1 6 2 3
Answer is 3 -> (1,2) (1,3) (2,3)
1,2,3,6


public int count(int[] nums, int x){
	if ( nums == null || nums.length ==0){
		return 0; 
	}
	int result =0 ; 
	Arrays.sort(nums); //sort the array 
	int end = nums.length-1; 
for(int i = 0; i < nums.length; i ++ ) {  
	int start = i ; 
		while ( start < end ) {   
			int sum = nums[start] + nums[end] ; 
			if ( sum > x ) {
				end --; 
			} 
			else{ 
		       		result = result + (end - start);  
				break; 
			} 
		}	 
	}
return result; 	
}
