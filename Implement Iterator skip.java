package com.AmazonOnsite;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * 然后就是算法实现Iterator的skip()功能，就是传一个参数然后跳过这个数，可以修改next()和hasNext()方法。
 * 比如list里面有1，2，3，4 这四个数，skip(3)以后，再调用三次next()就分别返回1，2，4，此时在调用hasNext()就返回false了
 *
 * https://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=529290&extra=page%3D1%26filter%3Dsortid%26sortid%3D311%26searchoption%5B3046%5D%5Bvalue%5D%3D5%26searchoption%5B3046%5D%5Btype%5D%3Dradio%26sortid%3D311%26orderby%3Ddateline
 * @author riapan 6/4/19
 */
public class IteratorSkip {
  Iterator iterator;
  Set<Integer> skips;
  Integer next; // 一个valid的temp var
  IteratorSkip(List<Integer> input) {
    this.iterator = input.iterator();
    this.skips = new HashSet<Integer>();
    this.next = null;
  }

  public boolean hasNext() {
    if (next != null) { // 首先得查是否有合格的元素
      return true;
    }

    if (!iterator.hasNext()) {
      return false;
    }
    next = (Integer) iterator.next();
    while (skips.contains(next)) {
      if (!iterator.hasNext()) {
        return false;
      }
      next = (Integer) iterator.next();
    }
    return true;

  }
  public int next() {
    if (next != null) { // 先查是否有合格的元素
      int temp = next;
      next = null;
      return temp;
    }
      int next = (Integer) iterator.next();
      while (skips.contains(next)) {
        next = (Integer) iterator.next();
      }
      return next;
  }

  public void skip(int num) {
    // if valid
    skips.add(num);
  }

  public static void main(String[] args) {
    Integer[] x = {1,2,3,4};

    IteratorSkip i = new IteratorSkip(Arrays.asList(x));
    i.skip(3);
    i.skip(1);
    System.out.println(i.hasNext());
    System.out.println(i.hasNext());
    System.out.println(i.hasNext());
    System.out.println(i.next());
    System.out.println(i.next());
    System.out.println(i.next());



  }

}
