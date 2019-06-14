给一堆（员工，经理）的数组，让找出每个经理管理员工的数量。例如：输入是（A, B）(C, B) (B, D)，结果就是（A, 0）(B, 2) (C, 0) (D, 3)

----------------------------Solution 1. 类似拓扑排序，通过更新indegree来决定每个node的值-----------------------------------

/**
 * solution 1, 类似拓扑排序，先把indegree为0的node放入queue，每次拿出node，跟新他的neibor的值by neibor本身值+1+这个node值
 * 再把neibor的indegree-1，当indegree为0时，加入queue
 *
 * @author riapan 6/13/19
 */
 
package com.AmazonOnsite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class 经理管理员工数量 {
  public static Map<Character, Integer> solution (List<char[]> input) {
    Map<Character, Integer> indegreeMap = new HashMap<>();
    Map<Character, Character> relation = new HashMap<>();
    Map<Character, Integer> result = new HashMap();
    // construct two maps
    for (char[] i : input) {
      relation.put(i[0], i[1]);
      indegreeMap.put(i[1], indegreeMap.getOrDefault(i[1], 0) + 1);
      if (!indegreeMap.containsKey(i[0])) {
        indegreeMap.put(i[0], 0);
      }
      result.put(i[0], 0);
      result.put(i[1], 0);

    }
    // put indegree 0 into queue
    Queue<Character> q = new LinkedList<>();
    for (Character c : indegreeMap.keySet()) {
      if (indegreeMap.get(c) == 0) {
        q.offer(c);
      }
    }

    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 0; i < size; i++) {
        char cur = q.poll();
        if (!relation.containsKey(cur)) { //说明这个node没有neibor
          continue;
        }
        // update it's neibornode
        char neibor = relation.get(cur);
        result.put(neibor, result.get(neibor) + 1 + result.get(cur));
        indegreeMap.put(neibor, indegreeMap.get(neibor) - 1);
        if (indegreeMap.get(neibor) == 0) {
          q.offer(neibor);
        }
      }

    }
    return result;
  }

  public static void main(String[] args) {


//    char[] a = {'a', 'b'};
//    char[] b = {'c', 'b'};
//    char[] c = {'b', 'd'};
//    char[] d = {'d', 'g'};
//    char[] e = {'f', 'g'};
//    char[] f = {'e', 'f'};
//
//    List<char[]> input = new ArrayList<>(Arrays.asList(a,b,c,e,d,f));
//    System.out.println(solution(input));
    // {a=0, b=2, c=0, d=3, e=0, f=1, g=6}


    char[] a = {'a', 'b'};
    char[] b = {'c', 'b'};
    char[] c = {'b', 'f'};
    char[] d = {'f', 'g'};
    char[] e = {'e', 'f'};

    List<char[]> input = new ArrayList<>(Arrays.asList(a,b,c,d,e));
    System.out.println(solution(input));
    // {a=0, b=2, c=0, e=0, f=4, g=5}

  }
}




-------------------solution 2： 看成树，D & C ----------------------------------------------

 /*
 * Solution 2，看成树，首先把node上的每个值都为-1，然后遍历每个node来update值，当值为-1时，说明这个node还没被赋值过，则调用
 * helper函数。helper中，给node赋值的方法是查看这个node所有的children是否已被赋值，是的话，就所有children的值加children个数（edge）
 * 为当前的值。children没被赋值的话，递归给children赋值。
 */
 
 package com.AmazonOnsite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class 经理管理员工数量 {
  public static Map<Character, Integer> solution (List<char[]> input) {

    Map<Character, List<Character>> relation = new HashMap<>();
    Map<Character, Integer> result = new HashMap();
    // construct two maps
    for (char[] i : input) {
      if (!relation.containsKey(i[1])) {
        relation.put(i[1], new ArrayList<>());
      }
      relation.get(i[1]).add(i[0]);
      result.put(i[0], -1);
      result.put(i[1], -1);

    }
    // put indegree 0 into queue
    for (char c : result.keySet()) {
      if (result.get(c) == -1) {
        dfs(relation, result, c);
      }
    }
    return result;
  }

  private static int dfs(Map<Character, List<Character>> relation,  Map<Character, Integer> result, char cur) {
    int res = 0;
    if (!relation.containsKey(cur)) { //如果是leaf，那么返回0
      result.put(cur, 0);
      return 0;
    }
    List<Character> children = relation.get(cur);
    for (char c : children) {
      if (result.get(c) == -1) {
        res = res + 1 + dfs(relation, result, c);
      }
      else {
        res = res + 1 + result.get(c);
      }
    }
    result.put(cur, res);
    return res;
  }

  public static void main(String[] args) {


    char[] a = {'a', 'b'};
    char[] b = {'c', 'b'};
    char[] c = {'b', 'd'};
    char[] d = {'d', 'g'};
    char[] e = {'f', 'g'};
    char[] f = {'e', 'f'};

    List<char[]> input = new ArrayList<>(Arrays.asList(a,b,c,e,d,f));
    System.out.println(solution(input));
   //  {a=0, b=2, c=0, d=3, e=0, f=1, g=6}

//
//    char[] a = {'a', 'b'};
//    char[] b = {'c', 'b'};
//    char[] c = {'b', 'f'};
//    char[] d = {'f', 'g'};
//    char[] e = {'e', 'f'};
//
//    List<char[]> input = new ArrayList<>(Arrays.asList(a,b,c,d,e));
//    System.out.println(solution(input));
    // {a=0, b=2, c=0, e=0, f=4, g=5}

  }
}
 
 
