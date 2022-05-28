/*
given a array, find all the peak indices in the array. A peak is defined by an element that is strictly greater than elements to the left and right of it.
Example:
Input:  [1,3,2,4,3]
Output: [1, 3]
Explanation: the element at index 1 has 1 and 2 as its adjacent elements which are smaller than itself, hence it is a peak. Same reason for the element at index 3.

[1, 2, 2] 返回 [2, 2] (plateau peak)
[3, 2, 5] 返回 [3, 5] (multiple peaks)

1. Follow up：also find the plateau peaks

- 就是比如有连着一样的element [1,2,2,2,1]的话，里面的每一个2都是plateau peak。只需要return一个。
Example:
Input:[1,2,2,2,1,0,0,0,1]
Output: [1, 8] or [2, 8] or [3, 8] are accepted. In particular, 1 and 2 and 3 are platrau peaks.
- 用height consolidation, (1,1,2,2,5,5,5)这种input变化成[(1,2), (2, 2), (5,3)]这种，每一个tuple的第一个代表height，第二个数字代表连续出现的次数，这样处理过之后可以确保每一个高度数字只出现过一次。

2. followup： 如果array很大需要parallel运行的话该怎么处理。

- 答案应该是multithreading或mapreduce，我没说出细节因为我不熟这一块，但总体应该还是positive。
- 最后的mapreduce我觉得应该是分时进行，比如这1小时统一处理，然后再下一个小时。boundary的话，就头和尾都多加一个数据，最后ignore这个数据就行了。
比如：3 2 1 2 3，我们不知道现在头和尾到底是不是peak，就需要多加一个数字在头和尾，比如（4）3 2 1 2 3 （2）。这样4和2不算，尾的3算peak，但是头的3不是peak
- 第三问解： [https://www.1point3acres.com/bbs/thread-836408-1-1.html](https://www.1point3acres.com/bbs/thread-836408-1-1.html)
*/

class Node {
    int val;
    List<Integer> indexes; 
    
    public Node(int val, List<Integer> indexes) {
        this.val = val;
        this.indexes = indexes; 
    }
}

public class Main {
    public static void main(String[] args) {
        int[] test1 = {1,3,2,4,3}; // 1, 3
        int[] test2 = {3,2,5}; // 0, 2
        int[] test3 = {4,4,4,2,5,5,5,2}; // 0, 4
        int[] test4 = {1 , 2, 2, 2,3}; //4
        int[] test5 = {5,5,5,4,1}; // 0
        int[] test6 = {1,3,4,5,5,5}; //3 
        int[] test7 = {1,2,2,2,1,0,0,0,1}; // 1,8
        int[][] all = {test1, test2, test3,test4,test5,test6, test7};
        for (int i = 0; i < all.length; i++) {
            System.out.println(withPlateau(all[i])); 
        }
       
    }
    public static List<Integer> withPlateau(int[] arr) {
        // pre-processing 
        List<Node> newInput = new ArrayList<Node>();
        for (int i = 0; i < arr.length; i++) {
            if (newInput.size() == 0 || newInput.get(newInput.size() - 1).val != arr[i]) {
                newInput.add(new Node(arr[i], new ArrayList<>()));
            }
            newInput.get(newInput.size() - 1).indexes.add(i);
        }
        // 2. same logic to find peaks without platau 
        List<Integer> res = new ArrayList<Integer>();
        int i = 0; 
        while (i < newInput.size()) {
            int curIndex = newInput.get(i).indexes.get(0);
            if (i == 0) {
                if (newInput.get(i+1).val < newInput.get(i).val) {
                    res.add(curIndex);
                }
            }
            else if (i == newInput.size() - 1) {
                if (newInput.get(i-1).val < newInput.get(i).val) {
                    res.add(curIndex);
                }
            }
            else {
                if (newInput.get(i-1).val < newInput.get(i).val && newInput.get(i+1).val < newInput.get(i).val) {
                    res.add(curIndex);
                }
            }
            i++;
        }
        return res; 
        // newInput.forEach(o -> System.out.println(o.indexes));
    }
    
    public static List<Integer> findPeaks(int[] arr) {
        List<Integer> res = new ArrayList<Integer>();
        int i = 0; 
        while (i < arr.length) {
            if (i == 0) {
                if (arr[i+1] < arr[i]) {
                    res.add(i);
                }
            }
            else if (i == arr.length - 1) {
                if (arr[i-1] < arr[i]) {
                    res.add(i);
                }
            }
            else {
                if (arr[i-1] < arr[i] && arr[i+1] < arr[i]) {
                    res.add(i);
                }
            }
            i++;
        }
        return res; 
    }
}
