/*
扫一遍string，遇到（ balance+1 :push stack， 遇到 ）balance-1 : pop stack 
1. remove 多余的) -> when balance is 0 : stack is empty 
2. remove 多余的( -> 当最后stack还有item时，说明是多于的 ( 

Time: 
Time complexity : O(n)O(n), where nn is the length of the input string.

There are 3 loops we need to analyze. We also need to check carefully for any library functions that are not constant time.

The first loop iterates over the string, and for each character, either does nothing, pushes to a stack or adds to a set. Pushing to a stack and adding to a set are both O(1)O(1). Because we are processing each character with an O(1)O(1) operation, this overall loop is O(n)O(n).

The second loop (hidden in library function calls for the Python code) pops each item from the stack and adds it to the set. Again, popping items from a stack is O(1)O(1), and there are at most nn characters on the stack, and so it too is O(n)O(n).

The third loop iterates over the string again, and puts characters into a StringBuilder/ list. Checking if an item is in a set and appending to the end of a String Builder or list is O(1)O(1). Again, this is O(n)O(n) overall.

The StringBuilder.toString() method is O(n)O(n), and so is the "".join(...). So again, this operation is O(n)O(n).

So this gives us O(4n)O(4n), and we drop the 44 because it is a constant.

Space complexity : O(n)O(n), where nn is the length of the input string.

We are using a stack, set, and string builder, each of which could have up to n characters in them, and so require up to O(n)O(n) space.
*/
class Solution {
    public String minRemoveToMakeValid(String s) {
        // stack to store index 
        Deque<Integer> stack = new LinkedList<Integer>();
        Set<Integer> invalidRightParam = new HashSet<Integer>(); // use to record ) needing to be removed 
        
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            if (cur == '(') {
                stack.push(i);
            }
            if (cur == ')') {
                // check if string is balanced at this moment 
                if (stack.size() == 0){
                    invalidRightParam.add(i);
                }
                else { // ( is more than ), so can remove one 
                    stack.pop();
                }
            }
        }
        
        // remove invalid ( and )： 
        // 方法一： 把stack加到set中，扫一遍stringBuilder，当index 出现在set中时，remove, 注意： builder remove is O(n)
        // 方法二：找到要remove的之后，重新build一遍string -> 更好
        // 
        while (stack.size() != 0) {
            invalidRightParam.add(stack.pop());
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!invalidRightParam.contains(i)) {
                builder.append(s.charAt(i));
            }
        }
        
        return builder.toString();
    }
}

------------------------ 2 path 直白的写法 ------------------------------
    /*
这题的本质是要保证左右括号相等，所以可以扫两遍来分别移出多于的左右括号
1. remove 多余的(： 从左到右扫，遇到(加一，遇到)时，如果balance已经等于0了，说明是多于的。记录下来之后移除
2. remove 多余的) -> 反过来扫，反之

Time: 
*/
class Solution {
    public String minRemoveToMakeValid(String s) {
        int balance = 0; 
        Set<Integer> toRemove = new HashSet<Integer>();
        
        // scan 左到右，remove invlid ）
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            if (cur == '(') {
                balance++;
            }
            if (cur == ')') {
                if (balance == 0) {
                    toRemove.add(i);
                }
                else {
                    balance--;
                }
            }
        }
        // scan 右到左，remove invalid （ 
        balance = 0; 
        for (int i = s.length()-1; i >= 0; i--) {
            char cur = s.charAt(i);
            if (cur == ')') {
                balance++;
            }
            if (cur == '(') {
                if (balance == 0) {
                    toRemove.add(i);
                }
                else {
                    balance--;
                }
            }
        }
        
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!toRemove.contains(i)) {
                builder.append(s.charAt(i));
            }
        }
        
        return builder.toString();
    }
}

---------------------- 2 path 精炼 --------------------------------------------
    
    /*
这题的本质是要保证左右括号相等，所以可以扫两遍来分别移出多于的左右括号
1. remove 多余的(： 从左到右扫，遇到(加一，遇到)时，如果balance已经等于0了，说明是多于的。记录下来之后移除
2. remove 多余的) -> 反过来扫，反之

Time: 
*/
class Solution {
    public String minRemoveToMakeValid(String s) {
        StringBuilder pass1 = buildValidString(s, '(', ')'); // remove 右括号
        StringBuilder pass2 = buildValidString(pass1.reverse(), ')', '('); // remove 左括号。 注意 string这时候要reverse一下，因为要从尾往头扫
        
        // 还要reverse 回来。 reverse 是O（n）
        return pass2.reverse().toString();
    }
    
    
    private StringBuilder buildValidString(CharSequence s, char open, char close) {
        int balance = 0; 
          StringBuilder builder = new StringBuilder();
          for (int i = 0; i < s.length(); i++) {
                char cur = s.charAt(i);
                if (cur == open) {
                    balance++;
                }
                if (cur == close) {
                    if (balance == 0) {
                       continue; // 这是要移出的括号
                    }
                    else {
                        balance--;
                    }
                }
                builder.append(cur); // 同时 build string
            }
        return builder; 
    }
}
