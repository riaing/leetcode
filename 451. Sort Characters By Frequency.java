Given a string s, sort it in decreasing order based on the frequency of the characters. The frequency of a character is the number of times it appears in the string.

Return the sorted string. If there are multiple answers, return any of them.

 

Example 1:

Input: s = "tree"
Output: "eert"
Explanation: 'e' appears twice while 'r' and 't' both appear once.
So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
Example 2:

Input: s = "cccaaa"
Output: "aaaccc"
Explanation: Both 'c' and 'a' appear three times, so both "cccaaa" and "aaaccc" are valid answers.
Note that "cacaca" is incorrect, as the same characters must be together.
Example 3:

Input: s = "Aabb"
Output: "bbAa"
Explanation: "bbaA" is also a valid answer, but "Aabb" is incorrect.
Note that 'A' and 'a' are treated as two different characters.
 

Constraints:

1 <= s.length <= 5 * 105
s consists of uppercase and lowercase English letters and digits.
 
 ---------- 解析 ----------------------------------
 Remember, Strings are Immutable!
The input type for this question is a String. When dealing with Strings, we need to be careful to not inadvertently convert what should have been an O(n)O(n) algorithm into an O(n^2)O(n 
2
 ) one.

Strings in most programming languages are immutable. This means that once a String is created, we cannot modify it. We can only create a new String. Consider the following Java code.

String a = "Hello ";
a += "Leetcode";
This code creates a String called a with the value "Hello ". It then sets a to be a new String, made with the letters from the old a and the additional letters "Leetcode". It then assigns this new String to the variable a, throwing away the reference to the old one. It does NOT actually "modify" a.

For the most part, we don't run into problems with Strings being treated like this. But consider this code for reversing a String.

String s = "Hello There";
String reversedString = "";
for (int i = s.length() - 1; i >= 0; i--) {
    reversedString += s.charAt(i);
}
System.out.println(reversedString);
Each time a character is added to reverseString, a new String is created. Creating a new String has a cost of nn, where nn is the length of the String. The result? Simply reversing a String has a cost of O(n^2)O(n 
2
 ) using the above algorithm.

The solution is to use a StringBuilder. A StringBuilder collects up the characters that will be converted into a String so that only one String needs to be created—once all the characters are ready to go. Recall that inserting an item at the end of an Array has a cost of O(1)O(1), and so the total cost of inserting the nn characters into the StringBuilder is O(n)O(n), and it is also O(n)O(n) to then convert that StringBuilder into a String, giving a total of O(n)O(n).

String s = "Hello There";
StringBuilder sb = new StringBuilder();
for (int i = s.length() - 1; i >= 0; i--) {
    sb.append(s.charAt(i));
}
String reversedString = sb.toString();
System.out.println(reversedString);
If you're unsure what to do for your particular programming language, it shouldn't be too difficult to find using a web search. The algorithms provided in the solutions here all do string building efficiently.


  
  ------------------- heap ----------------------------------------------------
  class Solution {
    public String frequencySort(String s) {
         // num -> count
        Map<Character, Integer> count = new HashMap<Character, Integer>();
        for (int i = 0; i < s.length(); i++) {
            count.put(s.charAt(i), count.getOrDefault(s.charAt(i), 0) + 1); 
        }
        // max heap of the number which sorted by frequency 
        // 注意这个 comparator 可以不用 queue 里的东西
        PriorityQueue<Character> q = new PriorityQueue<Character>((n1, n2) -> count.get(n2) - count.get(n1));
        for (char c : count.keySet()) {
            q.offer(c);
        }
        
        StringBuilder b = new StringBuilder();
        while(q.size() > 0) {
            char c = q.poll();
            for (int i = 0; i < count.get(c); i++) {
                b.append(c);
            }
        }
        return b.toString();
    }
}
