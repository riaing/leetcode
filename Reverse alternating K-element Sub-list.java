Given the head of a LinkedList and a number ‘k’, reverse every alternating ‘k’ sized sub-list starting from the head.

If, in the end, you are left with a sub-list with less than ‘k’ elements, reverse it too.

before 1-2-3-4-5-6-7-8, k = 2
after 2-1-3-4-6-5-7-8 

----------------------- reverse linked list应用----------------------------------
import java.util.*;

class ListNode {
  int value = 0;
  ListNode next;

  ListNode(int value) {
    this.value = value;
  }
}

class ReverseEveryKElements {

  public static ListNode reverse(ListNode head, int k) {
    ListNode cur = new ListNode(-1);
    cur.next = head; 
    ListNode sudo = cur;
    while (cur.next != null) {
      int moveTimes = k; 
      // reverse the node 
      ListNode reversedTail = cur.next;
      ListNode reversedHead = reverseKNodes(reversedTail, k); 
      cur.next = reversedHead;
      cur = reversedTail;  // 接到 reverse 完了的尾巴上
      while (cur.next != null && moveTimes > 0) {
        moveTimes--;
        cur = cur.next;
      }
    }
    return sudo.next;
  }

  private static ListNode reverseKNodes(ListNode head, int k) {
    ListNode cur = head;
    ListNode pre = null;
    while (cur != null && k > 0) {
      k--;
      ListNode tmp = cur.next;
      cur.next = pre; 
      pre = cur;
      cur = tmp;
    }
    // 注意！把右边第一个不要翻的 node 接到原 head 后： 1->2->3, k = 2 => 2->1->3
    head.next = cur;
    return pre;
  }

  public static void main(String[] args) {
    ListNode head = new ListNode(1);
    head.next = new ListNode(2);
    head.next.next = new ListNode(3);
    head.next.next.next = new ListNode(4);
    head.next.next.next.next = new ListNode(5);
    head.next.next.next.next.next = new ListNode(6);
    head.next.next.next.next.next.next = new ListNode(7);
    head.next.next.next.next.next.next.next = new ListNode(8);

    ListNode result = ReverseEveryKElements.reverse(head, 2);
    System.out.print("Nodes of the reversed LinkedList are: ");
    while (result != null) {
      System.out.print(result.value + " ");
      result = result.next;
    }
  }
}
