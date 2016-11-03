Given an encoded string, return it's decoded string.

The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.

You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.

Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].

Examples:

s = "3[a]2[bc]", return "aaabcbc".
s = "3[a2[c]]", return "accaccacc".
s = "2[abc]3[cd]ef", return "abcabccdcdcdef".

//用两个stack，一个记录num，一个记录【前的string。 一个res记录每次括号里的string。 在遇到】时，pop出之前的string，pop出num，根据num把
//【】里的新的string加到之前的上面去。组成新的string。 下一次再遇到【时，把这个新的存入stack。。。
public class Solution {
    public String decodeString(String s) {
         String res = "";
        if (s == null || s.length() == 0 ){
            return res;
        }
        Deque<Integer> count = new LinkedList<Integer>();
        Deque<String> string = new LinkedList<String>();//存【前的previous string
        
        int i =0;
        int num =0; //计算出现的次数
        while(i < s.length()){
            if(Character.isDigit(s.charAt(i))){ //如果是数字
            while(Character.isDigit(s.charAt(i))){
                num = num*10 + (s.charAt(i) -'0');
                i ++; 
            }
            count.push(num);
            num = 0; 
            }
            
            else if(s.charAt(i) == '['){
                string.push(res);//存入之前的元素
                res = ""; //变为空，以便记录括号里的数。 
                i ++;
            }
            else if ( s.charAt(i) ==']'){
                StringBuilder str = new StringBuilder(string.pop()); //get previous string
                int c = count.pop();
                for (int j =0; j < c; j ++ ){
                    str.append(res);//之前的加上此时括号里的
                }
                res = str.toString(); 
                i ++ ;
            }
            else{ //走到这说明是【后的string了，记录下来
                res = res +s.charAt(i); 
                i++;
            }
            
        }
        return res; 
    }
}
