------------------------1 normal stack 1 max stack to store the max value corresponding to curent value in normal stack ----------------
class MaxStack {
    Deque<Integer> maxValStack; 
    Deque<Integer> stack;

    
    /** initialize your data structure here. */
    public MaxStack() {
        maxValStack = new LinkedList<Integer>();
        stack = new LinkedList<Integer>();
       
    }
    
    public void push(int x) {
        if (!maxValStack.isEmpty()) {
            maxValStack.push(Math.max(x, maxValStack.peek()));
        }
        else {
            maxValStack.push(x);
        }
        stack.push(x);
    }
    
    public int pop() {
        maxValStack.pop();
        return stack.pop();
       
    }
    
    public int top() {
        return stack.peek();
        
    }
    
    public int peekMax() {
        return maxValStack.peek(); 
    }
    
    // here need a buffer stack 
    public int popMax() { //O(n) 
        Deque<Integer> buffer = new LinkedList<Integer>();
        int res = 0;
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            int max = maxValStack.pop();
            if (cur == max) {
                res = cur; 
                break;
            }
            buffer.push(cur);
        }
        
        while (!buffer.isEmpty()) {
            this.push(buffer.pop());
        }
        return res; 
    }
}

/**
 * Your MaxStack object will be instantiated and called as such:
 * MaxStack obj = new MaxStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.peekMax();
 * int param_5 = obj.popMax();
 */

----------------double LinkedList + TreeMap解法-----------------------------------------
    https://leetcode.com/problems/max-stack/solution/ 

/*
维持stack：用doubly linkedlist来实现pop的O(1)
找max值：用treeMap存 值-> list<Node>. TreeMap 是O（lgn）的操作

重点是map中一定要移出空的元素
*/
class Node {
    int val;
    Node pre;
    Node next;
    
    public Node(int val) {
        this.val = val;
    }
}

class MaxStack {
    // double linkedList
    Node head;
    Node tail;
    // treeMap to record Max 
    TreeMap<Integer, List<Node>> map;
    
    public MaxStack() {
        this.head = new Node(-1);
        this.tail = head;
        // build map sorted by min -> max
        this.map = new TreeMap<Integer, List<Node>>(); 
    }
    
    public void push(int x) { // o(lgn)
        // 更新linkedList
        Node cur = new Node(x);
        tail.next = cur;
        cur.pre = tail;
        tail = tail.next; 
        // 更新map
        map.putIfAbsent(x, new ArrayList<Node>());
        map.get(x).add(cur);
    }
    
    public int pop() { // O(logN)
        if (tail == head) { // 没有元素了
            return -1;
        }
        Node tailNode = tail;
        tail = tail.pre;
        
        // 更新map,记得list空时remove
        List<Node> thisNodes = map.get(tailNode.val);
        thisNodes.remove(thisNodes.size()-1);
        if (thisNodes.size() == 0) {
            map.remove(tailNode.val);
        }
        
        return tailNode.val;
    }
    
    public int top() { // O(1)
        return tail == head ? null : tail.val;
    }
    
    public int peekMax() { // O(1)
        if (!map.isEmpty()) {
            return map.lastKey();
        }
        return -1; // edge 处理了，看面试官
    }
    
    public int popMax() { // O(lgn) 
        // 1. 找到max from map
        int maxVal = map.lastKey();
        // 2. remove it from map ，记得list为空时remove
        Node maxNode = map.get(maxVal).remove(map.get(maxVal).size() - 1);
        if (map.get(maxVal).size() == 0) {
            map.remove(maxVal);
        }
        
        // 3. remove from LinkedList
        if (maxNode == tail) {
            Node preTail = tail.pre;
            preTail.next = null; 
            tail = preTail;
        }
        else {
            Node pre = maxNode.pre;
            Node next = maxNode.next; 
            pre.next = next;
            next.pre = pre;  
        }
        return maxVal;
    }
}

/**
 * Your MaxStack object will be instantiated and called as such:
 * MaxStack obj = new MaxStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.peekMax();
 * int param_5 = obj.popMax();
 */
