Suppose you have a random list of people standing in a queue. Each person is described by a pair of integers (h, k), where h is the height of the person and k is the number of people in front of this person who have a height greater than or equal to h. Write an algorithm to reconstruct the queue.

Note:
The number of people is less than 1,100.


Example

Input:
[[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]

Output:
[[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]

class Solution {
    public int[][] reconstructQueue(int[][] people) {
        Comparator<int[]> comparator = new Comparator<int[]>() {
            @Override 
            public int compare(int[] i1, int[] i2) {
                // If h is the same, sorted by k ascending 
                if (i1[0] == i2[0]) {
                    return i1[1] - i2[1];
                }
                // Sort by h from tall to short (descending)
                return i2[0] - i1[0];
            }
        };
        
        List<int[]> tmp = new ArrayList<int[]>();
        // Sort people by their height. 
        Arrays.sort(people, comparator);
        // Add people to the new list tmp to the position by their k value. 
        for (int i = 0; i < people.length; i++) {
            tmp.add(people[i][1], people[i]);
        }
        
        // Make the result as 2D array 
        int[][] res = new int[people.length][2];
        
        for (int j = 0; j < tmp.size(); j++) {
            res[j][0] = tmp.get(j)[0];
            res[j][1] = tmp.get(j)[1];
        }
        return res;  
    }
} 

-------------in place 解法见三： http://www.cnblogs.com/grandyang/p/5928417.html 
只不过没有使用额外空间，而是直接把位置不对的元素从原数组中删除，直接加入到正确的位置上

