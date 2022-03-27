Given a string s and an integer k, rearrange s such that the same characters are at least distance k from each other. If it is not possible to rearrange the string, return an empty string "".

 

Example 1:

Input: s = "aabbcc", k = 3
Output: "abcabc"
Explanation: The same letters are at least a distance of 3 from each other.
Example 2:

Input: s = "aaabc", k = 3
Output: ""
Explanation: It is not possible to rearrange the string.
Example 3:

Input: s = "aaadbbcc", k = 2
Output: "abacabcd"
Explanation: The same letters are at least a distance of 2 from each other.
 

Constraints:

1 <= s.length <= 3 * 105
s consists of only lowercase English letters.
0 <= k <= s.length


------------------------------ heap + queue ------------------------------------------------
/*
解法：  since we were inserting a character back in the heap in the next iteration, in this problem, we will re-insert the character after ‘K’ iterations. We can keep track of previous characters in a queue to insert them back in the heap after ‘K’ iterations. 

时间假设string没有重复字母
The time complexity of the above algorithm is O(N*logN)
 where ‘N’ is the number of characters in the input string.

Space complexity#
The space complexity will be O(N)
, as in the worst case, we need to store all the ‘N’ characters in the HashMap.
*/
class Node {
    char c;
    int occurance;
    public Node(char c, int occurance) {
        this.c = c;
        this.occurance = occurance; 
    }
}

class Solution {
    public String rearrangeString(String s, int k) {
        // Map to store the char - occurance 
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        // put node into heap sorted by max occurance 
        PriorityQueue<Node> q = new PriorityQueue<Node>((a, b) -> b.occurance - a.occurance);
        for (Map.Entry<Character,Integer> entry : map.entrySet()) {
            q.offer(new Node(entry.getKey(), entry.getValue()));
        }
        
        // construct the new string  
        Queue<Node> backlogs = new LinkedList<Node>(); // size k 
        StringBuilder builder = new StringBuilder();
        while (q.size() != 0) {
            Node cur = q.poll();
            builder.append(cur.c);
            cur.occurance = cur.occurance - 1;
            backlogs.add(cur); // 这时候就算是0也得加
            if (backlogs.size() >= k) { // 这还不能是while，如果k=0就错了
                Node toAdd = backlogs.poll();
                if (toAdd.occurance > 0) {
                    q.offer(toAdd);
                }
            }
        }
        // 最后判断是否valid
        return builder.length() == s.length() ? builder.toString() : "";
    }
}
