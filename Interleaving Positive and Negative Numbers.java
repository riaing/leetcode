Given an array with positive and negative integers. Re-range it to interleaving with positive and negative integers.

Example
Given [-1, -2, -3, 4, 5, 6], after re-range, it will be [-1, 5, -2, 4, -3, 6] or any other reasonable answer.

Challenge
Do it in-place and without extra memory.

Notice
You are not necessary to keep the original order of positive integers or negative integers.
------------------------------------------------------------------------
public class Solution {
    /*
     * @param A: An integer array.
     * @return: nothing
     
     1, 先通过swap，将所有正负数放在一边
     2，指针指向start,end，依次swap。难点是走几个例子判断start，end的值
     Time (n)； space (1)
     */
    public void rerange(int[] A) {
        // two pointers to get positive at first then negative 
        int start = 0;
        int end = A.length-1;
        while (start < end) {
            while (A[start] > 0 && start < end) { //注意这里的条件是 start < end 
                start++;
            }
            while (A[end] < 0 && end > start) {
                end--;
            }
           
            swap(A, start, end);
            start++;
            end--;
        }
        
        // 1. Negative # == positive #, swap start/end untill start == end
        // 2. Negative # != positive #, if mid point is positive, end = end - 1; if mid point is negative, start = start + 1. 
        int posStart = 0;
        int negEnd = A.length - 1;
        if (A.length % 2 != 0) {
            if (A[negEnd / 2] > 0) {
                posStart++;
            } else {
            negEnd--;
            }    
        }

        while (posStart < negEnd) {
            swap(A, posStart, negEnd);
            posStart += 2;
            negEnd -= 2;
        }
    }
    
    private void swap(int[] A, int start, int end) {
        int tmp = A[end];
        A[end] = A[start];
        A[start] = tmp;
    }
}
