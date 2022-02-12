Problem Statement#
Given ‘N’ ropes with different lengths, we need to connect these ropes into one big rope with minimum cost. The cost of connecting two ropes is equal to the sum of their lengths.

Example 1:

Input: [1, 3, 11, 5]
Output: 33
Explanation: First connect 1+3(=4), then 4+5(=9), and then 9+11(=20). So the total cost is 33 (4+9+20)
Example 2:

Input: [3, 4, 5, 6]
Output: 36
Explanation: First connect 3+4(=7), then 5+6(=11), 7+11(=18). Total cost is 36 (7+11+18)
Example 3:

Input: [1, 3, 11, 5, 2]
Output: 42
Explanation: First connect 1+2(=3), then 3+3(=6), 6+5(=11), 11+11(=22). Total cost is 42 (3+6+11+22) 

--------------- heap ----------------
Solution#
In this problem, following a greedy approach to connect the smallest ropes first will ensure the lowest cost. We can use a Min Heap to find the smallest ropes
following a similar approach as discussed in Kth Smallest Number. Once we connect two ropes, we need to insert the resultant rope back in the Min Heap so that
we can connect it with the remaining ropes.

Time complexity#
Given ‘N’ ropes, we need O(N*logN)O(N∗logN) to insert all the ropes in the heap. In each step, while processing the heap, we take out two elements from the heap and insert one. This means we will have a total of ‘N’ steps, having a total time complexity of O(N*logN)O(N∗logN).

Space complexity#
The space complexity will be O(N)O(N) because we need to store all the ropes in the heap.



import java.util.*;
class ConnectRopes {

  public static int minimumCostToConnectRopes(int[] ropeLengths) {
    PriorityQueue<Integer> q = new PriorityQueue<Integer>();
    for (int num : ropeLengths) {
      q.offer(num);
    }
    int res = 0;
    while (q.size() > 1) {
      int smallest = q.poll();
      int secondSmallest = q.poll();
      int curLen = smallest + secondSmallest; 
      res += curLen;
      q.offer(curLen);
    }
    return res;
  }

  public static void main(String[] args) {
    int result = ConnectRopes.minimumCostToConnectRopes(new int[] { 1, 3, 11, 5 });
    System.out.println("Minimum cost to connect ropes: " + result);
    result = ConnectRopes.minimumCostToConnectRopes(new int[] { 3, 4, 5, 6 });
    System.out.println("Minimum cost to connect ropes: " + result);
    result = ConnectRopes.minimumCostToConnectRopes(new int[] { 1, 3, 11, 5, 2 });
    System.out.println("Minimum cost to connect ropes: " + result);
  }
}
