


解法1：
if 2 stacks: 
PopMax (On)
pop, push, peek: o(1)
    
解法2
if Double LinkedList + TreeMap  
popMax, pop, push(Olgn) -> 追求popMax最优用这种 
peek: o(1) 
------------------------1 normal stack 1 max stack to store the max value corresponding to curent value in normal stack ----------------
/*
stack1. 正常stack
stack2. 到当前元素为止的max value

注意：两stack size衡一样。pop和push操作会apply到两stack上

push 
- 如果当前元素ith 小于max stack顶，max stack再push一遍栈顶，说明栈顶是ith. i-1th的最大值

pop 
- 要同时pop stack和 max stack的值。
如果stack栈顶小于 maxStack栈顶，pop直到相等。
再把倒出的元素push回去
*/
class MaxStack {
    Deque<Integer> stack;
    Deque<Integer> maxStack;
    public MaxStack() {
        this.stack = new LinkedList<>();
        this.maxStack = new LinkedList<>();
    }
    
    public void push(int x) {
        // 1. stack 直接push
        stack.push(x);
        //2. max stack push 
        if (maxStack.isEmpty()) {
            maxStack.push(x);
        }
        else {
           int maxVal = Math.max(x, maxStack.peek());
            maxStack.push(maxVal);
        }
    }
    
    public int pop() {
        // 1. max stack 
        maxStack.pop();
        // 2. stack 
        return stack.pop();
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int peekMax() {
        return maxStack.peek();    
    }
    
    public int popMax() {
        Deque<Integer> tmp = new LinkedList<Integer>(); 
        int curMax = maxStack.peek();  // 要单独记录，因为接下来的push pop会产生不同的最大值
        // 1, 找值
        while (stack.peek() != curMax) {
            tmp.push(pop()); // 注意：同时改变两个stack
        }
        // 2. 移除值
        pop(); //从stack和maxStack中移除当前最大值
        // 3. 加回max之后的值
        pushBack(tmp);
        return curMax; 
    }
    
    // 把倒出来的元素丢回stack
    private void pushBack(Deque<Integer> tmp) {
        while (!tmp.isEmpty()) {
            push(tmp.pop()); // 注意：必须同时作用于两stack上
        }
    }
}
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
