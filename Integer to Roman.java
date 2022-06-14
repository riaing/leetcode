I: 1
V: 5
X: 10
L: 50
C: 100
D: 500
M: 1000

字母可以重复，但不超过三次，当需要超过三次时，用与下一位的组合表示：
I: 1, II: 2, III: 3, IV: 4
C: 100, CC: 200, CCC: 300, CD: 400

s = 3978
3978/1000 = 3: MMM
978>(1000-100), 998/900 = 1: CM
78<(100-10), 78/50 = 1 :L
28<(50-10), 28/10 = XX
8<(100-1), 8/5 = 1: V
3<(5-1), 3/1 = 3: III
ret = MMMCMLXXVIII

所以可以将单个罗马字符扩展成组合形式，来避免需要额外处理类似IX这种特殊情况。
I: 1
IV: 4
V: 5
IX: 9
X: 10
XL: 40
L: 50
XC: 90
C: 100
CD: 400
D: 500
CM: 900
M: 1000

// 思路是从最大的找起
class Solution {
    private static final int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};    
    private static final String[] symbols = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
    
    public String intToRoman(int num) {
        String res =""; 
        for (int i = 0; i < values.length; i++) {
            if (num >= values[i]) {
                int count = num / values[i];
                num = num % values[i];
                while (count > 0) {
                    res += symbols[i];
                    count--;
                }
            }
        }
        return res; 
    }
}

------------ 比较general的写法，太长 -----------
    class Node {
    int smallVal;
    char smallSymbol;
    int largeVal;
    char largeSymbol;
    
    public Node(int smallVal, char smallSymbol, int largeVal, char largeSymbol) {
        this.smallVal = smallVal;
        this.smallSymbol = smallSymbol;
        this.largeVal = largeVal;
        this.largeSymbol = largeSymbol;
    }
}

class Solution {
    Map<Integer, Node> nodeMap = new HashMap<>();
    Map<Integer,Integer> countMap = new HashMap<>();
    Map<Integer, String> symbolMap = new HashMap<>(); 
    public String intToRoman(int num) {
        nodeMap.put(4, new Node(1000, 'M', 5000, ' '));
        nodeMap.put(3, new Node(100, 'C', 500, 'D'));
        nodeMap.put(2, new Node(10, 'X', 50, 'L'));
        nodeMap.put(1, new Node(1, 'I', 5, 'V'));
        
        countMap.put(4, 1000);
        countMap.put(3, 100);
        countMap.put(2, 10);
        countMap.put(1, 1);
        
        symbolMap.put(4, "IV");
        symbolMap.put(9, "IX");
        symbolMap.put(40, "XL");
        symbolMap.put(90, "XC");
        symbolMap.put(400, "CD");
        symbolMap.put(900, "CM");
        symbolMap.put(1, "I");
        symbolMap.put(5, "V");
        symbolMap.put(10, "X");
        symbolMap.put(50, "L");
        symbolMap.put(500, "D");
        symbolMap.put(1000, "M");
        
        
        String res = "";
        while (num > 0) {
            int count = String.valueOf(num).length(); 
            int toCal = num - num % countMap.get(count);
            res += build(toCal);
            num = num % countMap.get(count);
        }
        
        return res; 
    }
    
    private String build(int num) {
        // 1. find it's count 
        String res = "";
       
        while (num > 0) {
            if (symbolMap.containsKey(num)) {
                res += symbolMap.get(num);
                num -= num; 
            }
            else {
                int len = String.valueOf(num).length(); 
                if (num > nodeMap.get(len).largeVal) { // 大于5
                    res += nodeMap.get(len).largeSymbol;
                    num -= nodeMap.get(len).largeVal; 
                }
                int oneNeeded = num / nodeMap.get(len).smallVal; // 小于5 
                for (int i = 0; i < oneNeeded; i++) {
                    res += nodeMap.get(len).smallSymbol;
                    num -= nodeMap.get(len).smallVal;
                }
            }
        }
        return res; 
    }
}
