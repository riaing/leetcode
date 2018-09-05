Given a nested list of integers, implement an iterator to flatten it.

Each element is either an integer, or a list -- whose elements may also be integers or other lists.

Example 1:

Input: [[1,1],2,[1,1]]
Output: [1,1,2,1,1]
Explanation: By calling next repeatedly until hasNext returns false, 
             the order of elements returned by next should be: [1,1,2,1,1].
Example 2:

Input: [1,[4,[6]]]
Output: [1,4,6]
Explanation: By calling next repeatedly until hasNext returns false, 
             the order of elements returned by next should be: [1,4,6].
             
http://www.cnblogs.com/grandyang/p/5358793.html              
-------------一， 构建时用递归

/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
public class NestedIterator implements Iterator<Integer> {
    Queue<Integer> q = new LinkedList<Integer>();
    public NestedIterator(List<NestedInteger> nestedList) {
        flatten(nestedList);
    }

    @Override
    public Integer next() {
        return q.poll();
    }
    
    @Override
    public boolean hasNext() {
        return !q.isEmpty();
    }
    
    private void flatten(List<NestedInteger> nestedList) {
        for (NestedInteger a : nestedList) {
            if (a.isInteger()) {
                q.offer(a.getInteger());
            }
            else{
                flatten(a.getList());
            }    
        }
    }
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */
 


-----------------二，用stack，注意corner case [], [[]]

/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
public class NestedIterator implements Iterator<Integer> {
    Deque<NestedInteger> s = new LinkedList<NestedInteger>();
    public NestedIterator(List<NestedInteger> nestedList) {
        
        for (int i = nestedList.size() -1; i>=0; i--) {
            s.push(nestedList.get(i));
        }
    }

    @Override
    public Integer next() {
        return s.pop().getInteger();
    }
    
    // Be careful of corner case: [], [[]].
    @Override
    public boolean hasNext() {
        //Return false include TWO cases: 1, empty; 2, NOT empty but having empty list! [[]]. 
        // So we CANNOT just do "if(empty) return false" check at the beginning, because that doesn't cover all cases.
        // To conclude: ONLY when a Integer at top should we return True, otherwise, iterate through the list. 
        while (!s.isEmpty()) {
            if (s.peek().isInteger()) {
                return true;
            }
            // If top is an empty list, it won't go into the for loop. So it will return false.
            List<NestedInteger> top = s.pop().getList();
            for (int i = top.size() -1; i>=0; i--) {
                s.push(top.get(i));
            }
        }
        return false;
    }
    
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */
 
 
