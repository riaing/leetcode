package com.amazonOA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 *
 求一个set里数字组合的sum是否能够等于target 类似combination sum 数字不重复使用 都是unique。
 传统backtracking做法不能使面试官满意.
 【1， 3， 4， 7】 求target 10 是否可以组合成功 3 + 7 = 10 true

 O（2^n）- 但一般都会小余，因为去了重
 * @author riapan 6/6/19
 */
public class CombinationSum {

  // m[i][t] = m[i-1][t] or m[i-1][t-Ni]
  // 如果input都为正数，还可以判断当 加上 > 10时，不放进newSet里
  public static boolean canFormTarget(List<Integer> input, int target) {
    Set<Integer> old = new HashSet<>();
    old.add(0);


    for (Integer i : input) {
      Set<Integer> newSet = new HashSet<>();
      for (int sum : old) {
        newSet.add(sum);
        newSet.add(sum+i);
        if (sum == target || sum+i == target) {
          return true;
        }
      }
      old = newSet;
    }
    return false;
  }

  public static void main(String[] args) {
    Integer[] array = {1,3,4,7,5};
    List<Integer> input = new ArrayList(Arrays.asList(array));
    System.out.println(canFormTarget(input, 60));
  }
}
