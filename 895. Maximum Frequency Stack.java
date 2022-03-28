Design a stack-like data structure to push elements to the stack and pop the most frequent element from the stack.

Implement the FreqStack class:

FreqStack() constructs an empty frequency stack.
void push(int val) pushes an integer val onto the top of the stack.
int pop() removes and returns the most frequent element in the stack.
If there is a tie for the most frequent element, the element closest to the stack's top is removed and returned.
 

Example 1:

Input
["FreqStack", "push", "push", "push", "push", "push", "push", "pop", "pop", "pop", "pop"]
[[], [5], [7], [5], [7], [4], [5], [], [], [], []]
Output
[null, null, null, null, null, null, null, 5, 7, 5, 4]

Explanation
FreqStack freqStack = new FreqStack();
freqStack.push(5); // The stack is [5]
freqStack.push(7); // The stack is [5,7]
freqStack.push(5); // The stack is [5,7,5]
freqStack.push(7); // The stack is [5,7,5,7]
freqStack.push(4); // The stack is [5,7,5,7,4]
freqStack.push(5); // The stack is [5,7,5,7,4,5]
freqStack.pop();   // return 5, as 5 is the most frequent. The stack becomes [5,7,5,7,4].
freqStack.pop();   // return 7, as 5 and 7 is the most frequent, but 7 is closest to the top. The stack becomes [5,7,5,4].
freqStack.pop();   // return 5, as 5 is the most frequent. The stack becomes [5,7,4].
freqStack.pop();   // return 4, as 4, 5 and 7 is the most frequent, but 4 is closest to the top. The stack becomes [5,7].
 

Constraints:

0 <= val <= 109
At most 2 * 104 calls will be made to push and pop.
It is guaranteed that there will be at least one element in the stack before calling pop.

-------------- stack o(1) 写法 -------------------------------------------------
https://labuladong.gitee.io/algo/2/22/67/ 
------------------- Heap 写法 --------------------------------------------
/*
解决tie的方法：用sequenceNumber来代表每次加入的顺序

Space: We will need O(N) space for the heap and the map, so the overall space complexity of the algorithm is O(N)

*/

class Node {
    int sequenceNum; // 代表是第几次每次push
    int frequency;
    int val;
    
    public Node(int sequenceNum, int frequency, int val) {
        this.sequenceNum = sequenceNum;
        this.frequency = frequency;
        this.val = val;
    }
}

class FreqStack {
    int sequence;
    Map<Integer, Integer> freqMap;
    PriorityQueue<Node> q;
    public FreqStack() { 
        Comparator<Node> comparator = new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                int res = n2.frequency - n1.frequency;
                if (res == 0) {
                    return n2.sequenceNum - n1.sequenceNum;
                }
                return res;
            }
        };
        this.sequence = 0;
        this.freqMap = new HashMap<Integer, Integer>();
        this.q = new PriorityQueue<Node>(comparator);
    }
    
    public void push(int val) { // o(lgN)
        freqMap.put(val, freqMap.getOrDefault(val, 0) + 1);
        q.offer(new Node(sequence++, freqMap.get(val), val));
    }
    
    public int pop() { // o(lgN)
        Node maxFreqNode = q.poll();
        // update map 
        freqMap.put(maxFreqNode.val, freqMap.get(maxFreqNode.val) - 1);
        return maxFreqNode.val; 
    }
}

/**
 * Your FreqStack object will be instantiated and called as such:
 * FreqStack obj = new FreqStack();
 * obj.push(val);
 * int param_2 = obj.pop();
 */
