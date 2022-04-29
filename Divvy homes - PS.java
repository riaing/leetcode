

/* -------------------------- Q1 --------------------------------------------------------------

// This problem is to rotate a given array to the right by n steps.

// For example:

// Given [1, 2, 3] and n = 1, you should return [3, 1, 2]

// Each step, the last element in the array is moved to the front of the array, and the rest are shifted right.

// Another example:

// Given [1, 2, 3, 4, 5] and n = 3, you should return [3, 4, 5, 1, 2]
/*

n = 11.  11 % 5 = 1 . torotateIndex 4. 

[1,2,3] n = -1; [ 2,3,1]  => n = 2 : len + n = desired postive rotation 
[1, 2, 3, 4, 5] n = -1; [23451] => n =4 : len + n = 4 
n = -1 
 */

import java.io.*;
import java.util.*;

class Solution {

  private static int[] rotate(int[] input, int n) {
    // 0. precheck n.  
      //  if n < 0. n = len % n + len 
    
    // 1. find index to split 
    int tmp = n % input.length; 
    int index = input.length - tmp; 
    // 2. split and put into new list 
    int[] output = new int[input.length];
    int pointer = 0;
    for (int i = index; i < input.length; i++) {
      output[pointer] = input[i];
      pointer++;
    }
    for (int i =0; i < index; i++) {
      output[pointer] = input[i];
      pointer++;
    }
    System.out.println(Arrays.toString(output));
    return output; 
  }

---------------------------------- Q2 -------------------------------------------------------------------------------------
  /*
  // Given an array[] of n integers where n > 1, return an array such that the new array is equal to the product of all the elements of the array 
  except array[currentIndex].

// Example: [1, 2, 3, 4]

// Result: [2*3*4, 1*3*4, 1*2*4, 1*2*3] or [24, 12, 8, 6]

// [0,1,2,3,0] =>  [0, 0, 0, 0, 0].  

[0, 1, 2 ] => [2, 0, 0] 
*/

  private static int[] calculation(int[] input) {
    int[] output = new int[input.length];
    // preprocessing 
    int totalZeros = 0;
    int totalValue = 1; // without 0s from index
    for (int i = 0; i < input.length; i++) {
      if (input[i] == 0) {
        totalZeros++;
        if (totalZeros > 1) {
          return output;
        }
      }
      else{
        totalValue = totalValue * input[i];
      }
    }

    for (int i = 0; i < input.length; i++ ){
      int cur = input[i];
      if (cur == 0) {
        output[i] = totalValue; 
      }
      else {
        if (totalZeros > 0) {
          output[i] = 0;
        }
        else {
          output[i] = totalValue / input[i];
        }
      }
    }

    return output; 
  }

    public static void main(String[] args) {
    // Q1 
    // int[] input = new int[] {1, 2, 3};
    // int n = 1;
    // int[] input = new int[] {1, 2, 3, 4, 5};
    // int n = -1;
    // rotate(input, n);
    // for (String string : strings) {
    //   System.out.println(string);
    // }

    // Q2: 
    // Example: [1, 2, 3, 4]  
    // Result: [2*3*4, 1*3*4, 1*2*4, 1*2*3] or [24, 12, 8, 6]
    int[] input = new int[] {1,2,3,4};
    //  [0,1,2,3,0] => [0, 0, 0, 0, 0].  
    // input = new int[] {0, 1, 2, 3, 0};
    input = new int[] {0, 1, 2};
    // [0, 1, 2 ] => [2, 0, 0] 
    
    System.out.println("Q2: " + Arrays.toString(calculation(input)));
  }
}
