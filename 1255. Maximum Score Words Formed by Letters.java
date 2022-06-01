Given a list of words, list of  single letters (might be repeating) and score of every character.

Return the maximum score of any valid set of words formed by using the given letters (words[i] cannot be used two or more times).

It is not necessary to use all characters in letters and each letter can only be used once. Score of letters 'a', 'b', 'c', ... ,'z' is given by score[0], score[1], ... , score[25] respectively.

 

Example 1:

Input: words = ["dog","cat","dad","good"], letters = ["a","a","c","d","d","d","g","o","o"], score = [1,0,9,5,0,0,3,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0]
Output: 23
Explanation:
Score  a=1, c=9, d=5, g=3, o=2
Given letters, we can form the words "dad" (5+1+5) and "good" (3+2+2+5) with a score of 23.
Words "dad" and "dog" only get a score of 21.
Example 2:

Input: words = ["xxxz","ax","bx","cx"], letters = ["z","a","b","c","x","x","x"], score = [4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,10]
Output: 27
Explanation:
Score  a=4, b=4, c=4, x=5, z=10
Given letters, we can form the words "ax" (4+5), "bx" (4+5) and "cx" (4+5) with a score of 27.
Word "xxxz" only get a score of 25.
Example 3:

Input: words = ["leetcode"], letters = ["l","e","t","c","o","d"], score = [0,0,1,1,1,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0,0,0,0]
Output: 0
Explanation:
Letter "e" can only be used once.
 

Constraints:

1 <= words.length <= 14
1 <= words[i].length <= 15
1 <= letters.length <= 100
letters[i].length == 1
score.length == 26
0 <= score[i] <= 10
words[i], letters[i] contains only lower case English letters.


------------------------------ DFS ----------------------------------------------------
/*
没做过此类题型的话，可以看一下此题的前置题型，我是打完周赛碰到这类题型特地找这些题型练手的，链接如下：2151：基于陈述统计最多好人数

1、map数组用于记录可用的字母个数
2、words数组长度不超过15，所以联想到状态压缩
3、对于每个子集是否合理的判断，不能直接在map数组上进行操作，因为每个子集状态都需要map的原始状态，所以copy一个数组，这里注意需要深度拷贝，不能改变原数组map中的值
4、在子集所包括的单词分数的枚举过程中，如果字母个数不够用了，需要退出当前子集状态，并先将scores置零，否则会记录到此前已经累加到的分数



作者：yuzihan
链接：https://leetcode.cn/problems/maximum-score-words-formed-by-letters/solution/jian-ji-yi-dong-de-javawei-ya-suo-jie-fa-x2to/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

*/

/*
参考的解法：
https://leetcode.cn/problems/maximum-score-words-formed-by-letters/solution/java-dfs-bei-bao-shuang-100-by-zsq/
*/


class Solution {
    int max; 
    String[] words;
    List<String> output; // print出所有的string
    public int maxScoreWords(String[] words, char[] letters, int[] score) {
        this.words = words; 
        this.output = new ArrayList<>(); 
        // 1. build wordScore map 
        int[] weight = new int[score.length];
        for (int i = 0; i < score.length; i++) {
            weight[i] = score[i];
        }
        // 2. build number map 
        int[] wordNum = new int[26];
        for (char c : letters) {
            wordNum[c - 'a'] += 1;
        }
        
        DFS(0, 0, wordNum, weight, new ArrayList<String>());
        System.out.println(output); 
        return max; 

    }

    // 每个单词可选，可不选
    private void DFS(int index, int sum, int[] wordNum, int[] weight, List<String> tmpOutput) {
        if (index == words.length) {
            return;
        }
        // 试图组成当前词
        int[] curWordNum = Arrays.copyOf(wordNum, wordNum.length); // 这轮来祸害
        int curSum = sum; 
        for(char c : words[index].toCharArray()) {
            if (curWordNum[c - 'a'] <= 0) { // 当前词用不了,试下个词
                DFS(index+1, sum, wordNum, weight, tmpOutput); 
                return;
            }
            // 当前字母能用，更新
            curWordNum[c - 'a'] -= 1; 
            curSum += weight[c - 'a']; 
        }
        
        // 更新output string list
        tmpOutput.add(words[index]); 
        if (max < curSum) {
            output = new ArrayList<>(tmpOutput); 
        }
        // 更新Max
        max = Math.max(max, curSum);
        //这时候说明当前词能用了，但也有两个选择，取或者不取
        DFS(index+1, curSum, curWordNum, weight, tmpOutput);  // 取
        tmpOutput.remove(tmpOutput.size() - 1); // 回溯
        DFS(index+1, sum, wordNum, weight, tmpOutput); // 不取
    }

}

