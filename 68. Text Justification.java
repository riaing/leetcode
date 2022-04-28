Given an array of strings words and a width maxWidth, format the text such that each line has exactly maxWidth characters and is fully (left and right) justified.

You should pack your words in a greedy approach; that is, pack as many words as you can in each line. Pad extra spaces ' ' when necessary so that each line has exactly maxWidth characters.

Extra spaces between words should be distributed as evenly as possible. If the number of spaces on a line does not divide evenly between words, the empty slots on the left will be assigned more spaces than the slots on the right.

For the last line of text, it should be left-justified and no extra space is inserted between words.

Note:

A word is defined as a character sequence consisting of non-space characters only.
Each word's length is guaranteed to be greater than 0 and not exceed maxWidth.
The input array words contains at least one word.
 

Example 1:

Input: words = ["This", "is", "an", "example", "of", "text", "justification."], maxWidth = 16
Output:
[
   "This    is    an",
   "example  of text",
   "justification.  "
]
Example 2:

Input: words = ["What","must","be","acknowledgment","shall","be"], maxWidth = 16
Output:
[
  "What   must   be",
  "acknowledgment  ",
  "shall be        "
]
Explanation: Note that the last line is "shall be    " instead of "shall     be", because the last line must be left-justified instead of fully-justified.
Note that the second line is also left-justified becase it contains only one word.
Example 3:

Input: words = ["Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"], maxWidth = 20
Output:
[
  "Science  is  what we",
  "understand      well",
  "enough to explain to",
  "a  computer.  Art is",
  "everything  else  we",
  "do                  "
]
 

Constraints:

1 <= words.length <= 300
1 <= words[i].length <= 20
words[i] consists of only English letters and symbols.
1 <= maxWidth <= 100
words[i].length <= maxWidth

---------- 纯coding 题 -------------------
/*
1. 计算每行能放多少个word 
2. 计算总共有多少extra space要添加，以及有几个slot要添加space
3.1 处理最后一行： a，遇到最后一个单词，把剩余的space全加上 b.因为最后一行不用format，所以单词与单词间加一个space就行
3.2 处理非最后一行：a, 如果只有一个单词，append所有space 
                 b.如果有多个单词：要加的空格 = 总空格 / 总slot。如果 总空格 / 总slot不能整除 = k, 则前k个slot要多加个空格
                 c. 跟新总空格和总slot
*/
class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        int right = 0;
        List<String> res = new ArrayList<String>(); 
        
       // 1. 计算每行能放多少个word. 注意每个word后要有至少一个space，否则可能一行堆满word 
        while (right < words.length) {
            int lineWordsLen = 0;
            int spaceLen = 0;
            List<String> lineWords = new ArrayList<String>();
            while (lineWordsLen < maxWidth && right < words.length) {
                if (lineWordsLen + spaceLen + words[right].length() > maxWidth) {
                    break;
                }
                lineWordsLen += words[right].length();
                spaceLen++; // add a space after each words
                lineWords.add(words[right]);
                right++;
            }
            // right pointing to the next now.  
            
            // 2. 计算总共有多少extra space要添加，以及有几个slot要添加space
            int extraPad = maxWidth - lineWordsLen; 
            int spaceCntToAdd = lineWords.size() - 1; 
            
            // System.out.println("-------- extraPad " + extraPad + " len " + lineWordsLen); 
            StringBuilder b = new StringBuilder();
            // 3.1 处理最后一行
            if (right == words.length) {
                for (int i = 0; i < lineWords.size(); i++) {
                    b.append(lineWords.get(i));
                    // a，遇到最后一个单词，把剩余的space全加上
                    if (i == lineWords.size() - 1) {
                        for (int n = 0; n < extraPad; n++) {
                            b.append(" ");
                        }
                    }
                    // b.因为最后一行不用format，所以单词与单词间加一个space就行
                    else {
                        b.append(" "); 
                        extraPad--;
                    }
                }
            }
            
            else {
                for (String w : lineWords) {
                    b.append(w);
                    // add space after word
                     // a, default to if the line has only one word. eg: spaceCntToAdd == 0 
                    int padCnt = extraPad;
                    // b.如果有多个单词：要加的空格 = 总空格 / 总slot。如果 总空格 / 总slot不能整除 = k, 则前k个slot要多加个空格
                    if (spaceCntToAdd != 0) {  
                        padCnt =  extraPad / spaceCntToAdd; 
                        if (extraPad % spaceCntToAdd != 0) { // 如果不能整除，k = extraPad % spaceCntToAdd, 说明前k个slot每个都要多加1个空格，才能分完k个多余的
                            padCnt++;
                        }
                    }
                    
                    // System.out.println("pad to add " + padCnt); 
                    // c. 跟新总空格和总slot
                    for (int i = 0; i < padCnt; i++) {
                        b.append(" ");
                    }
                    extraPad -= padCnt; 
                    spaceCntToAdd--;
                }   
            }
            res.add(b.toString());
        }
        return res; 
    }
}
