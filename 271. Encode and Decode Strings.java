Design an algorithm to encode a list of strings to a string. The encoded string is then sent over the network and is decoded back to the original list of strings.

Machine 1 (sender) has the function:

string encode(vector<string> strs) {
  // ... your code
  return encoded_string;
}
Machine 2 (receiver) has the function:
vector<string> decode(string s) {
  //... your code
  return strs;
}
So Machine 1 does:

string encoded_string = encode(strs);
and Machine 2 does:

vector<string> strs2 = decode(encoded_string);
strs2 in Machine 2 should be the same as strs in Machine 1.

Implement the encode and decode methods.

You are not allowed to solve the problem using any serialize methods (such as eval).

 

Example 1:

Input: dummy_input = ["Hello","World"]
Output: ["Hello","World"]
Explanation:
Machine 1:
Codec encoder = new Codec();
String msg = encoder.encode(strs);
Machine 1 ---msg---> Machine 2

Machine 2:
Codec decoder = new Codec();
String[] strs = decoder.decode(msg);
Example 2:

Input: dummy_input = [""]
Output: [""]
 

Constraints:

1 <= strs.length <= 200
0 <= strs[i].length <= 200
strs[i] contains any possible characters out of 256 valid ASCII characters.
 

Follow up: Could you write a generalized algorithm to work on any possible set of characters?

-------------------------------- 把string encode成 （长度）原string ----------------------------------------
/*
trunked string : serialize时记录length 和 string (5)aaaaa(100)b...b 
*/
public class Codec {

    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder b = new StringBuilder();
        for (String s : strs) {
            b.append("(" + s.length() + ")" + s);
        }
        return b.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> res = new ArrayList<String>();
        int i = 0; 
        while (i < s.length()) {
            char cur = s.charAt(i);
            if (cur == '(') {
                // get len 
                int len = 0; 
                i++;
                while (s.charAt(i) != ')') {
                    len = len*10 + (s.charAt(i) - '0');
                    i++;
                }
                i++; // skip ')'
                // add the string 
                res.add(s.substring(i, i + len)); 
                i = i + len;
            }
        }
        return res;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(strs));

-----------------------------------更简单， string encode成 长度+原string ----------------------
  public class Codec {

    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder b = new StringBuilder();
        for (String s : strs) {
            b.append(s.length() + "+" + s);
        }
        return b.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) { // 2+a13+abc
        int i = 0;
        List<String> res = new ArrayList<>();
        while (i < s.length()) {
            int len = 0; 
            while (i < s.length() && Character.isDigit(s.charAt(i))) {
                len = len * 10 + (s.charAt(i) - '0');
                i++;
            }
            //  i = '+' now 
            i++; // skip '+';
            String cur = s.substring(i, i+len);
            res.add(cur);
            i = i+len; 
        }
        return res; 
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(strs));
