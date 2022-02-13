https://www.educative.io/courses/grokking-the-coding-interview/myAqDMyRXn3 

Given ‘M’ sorted arrays, find the K’th smallest number among all the arrays.

Example 1:

Input: L1=[2, 6, 8], L2=[3, 6, 7], L3=[1, 3, 4], K=5
Output: 4
Explanation: The 5th smallest number among all the arrays is 4, this can be verified from 
the merged list of all the arrays: [1, 2, 3, 3, 4, 6, 6, 7, 8]
Example 2:

Input: L1=[5, 8, 9], L2=[1, 7], K=3
Output: 7
Explanation: The 3rd smallest number among all the arrays is 7. 
  
------------------------heap ------------------------------
  /*
  heap solution, 需要记住 num 的 array index 和 element index。找到第 k 个时返回
  Time complexity #
Since we’ll be going through at most ‘K’ elements among all the arrays, and we will remove/add one element in the heap in each step, the time complexity of the above algorithm will be O(K*logM)O(K∗logM) where ‘M’ is the total number of input arrays.

Space complexity #
The space complexity will be O(M)O(M) because, at any time, our min-heap will be storing one number from all the ‘M’ input arrays.
  */
import java.util.*;

class Node {
  int elementIndex;
  int arrayIndex;

  Node(int arrayIndex, int elementIndex) {
    this.elementIndex = elementIndex;
    this.arrayIndex = arrayIndex;
  }
}


class KthSmallestInMSortedArrays {

  public static int findKthSmallest(List<Integer[]> lists, int k) {
    PriorityQueue<Node> q = new PriorityQueue<Node>(
       (a, b) -> lists.get(a.arrayIndex)[a.elementIndex] - lists.get(b.arrayIndex)[b.elementIndex]);
    for (int i = 0; i < lists.size(); i++) {
        if (lists.get(i) != null) {
          Node n = new Node(i,0);
            q.offer(n);
        }
    }
    while (q.size() > 0) {
      // get the smallest one, check if it's the Kth
      Node curSmall = q.poll();
      System.out.println("ele " + lists.get(curSmall.arrayIndex)[curSmall.elementIndex]);
      k--;
      if (k == 0) {
        return lists.get(curSmall.arrayIndex)[curSmall.elementIndex];
      }
      // add the next one in the same array to heap 
      curSmall.elementIndex ++;
      if (curSmall.elementIndex < lists.get(curSmall.arrayIndex).length) {
        q.offer(curSmall);
      } 
    }
    return -1;
  }

  public static void main(String[] args) {
    Integer[] l1 = new Integer[] { 2, 6, 8 };
    Integer[] l2 = new Integer[] { 3, 6, 7 };
    Integer[] l3 = new Integer[] { 1, 3, 4 };
    List<Integer[]> lists = new ArrayList<Integer[]>();
    lists.add(l1);
    lists.add(l2);
    lists.add(l3);
    int result = KthSmallestInMSortedArrays.findKthSmallest(lists, 5);
    System.out.println("Kth smallest number is: " + result);

    // example 2 
    Integer[] l11 = new Integer[] { 5, 8, 9 };
    Integer[] l22 = new Integer[] { 1, 7 };
    List<Integer[]> lists2 = new ArrayList<Integer[]>();
    lists2.add(l11);
    lists2.add(l22);
    int result2 = KthSmallestInMSortedArrays.findKthSmallest(lists2, 3);
    System.out.println("Kth smallest number is: " + result2);

  }
}
