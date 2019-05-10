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
