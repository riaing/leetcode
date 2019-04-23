----1, reverse list,转换成Add Two Numbers --------------------------------------------------------------------

---------2， 用stack---------------------------------------------------------------------
我们首先遍历两个链表，将所有数字分别压入两个栈s1和s2中，我们建立一个值为0的res节点，然后开始循环，如果栈不为空，则将栈顶数字加入sum中，
然后将res节点值赋为sum%10，然后新建一个进位节点head，赋值为sum/10，如果没有进位，那么就是0，然后我们head后面连上res，将res指向head，
这样循环退出后，我们只要看res的值是否为0，为0返回res->next，

class Solution {
public:
    ListNode* addTwoNumbers(ListNode* l1, ListNode* l2) {
        stack<int> s1, s2;
        while (l1) {
            s1.push(l1->val);
            l1 = l1->next;
        }
        while (l2) {
            s2.push(l2->val);
            l2 = l2->next;
        }
        int sum = 0;
        ListNode *res = new ListNode(0);
        while (!s1.empty() || !s2.empty()) {
            if (!s1.empty()) {sum += s1.top(); s1.pop();}
            if (!s2.empty()) {sum += s2.top(); s2.pop();}
            res->val = sum % 10;
            ListNode *head = new ListNode(sum / 10);
            head->next = res;
            res = head;
            sum /= 10;
        }
        return res->val == 0 ? res->next : res;
    }
};

------------3，用递归 ---------------------------------------------------------------------------------------------------
    /**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
// 用一个新的class保存carry，找出较长list的length，如果diff>=1,则递归较长list的next，和较短list的cur
class Solution {
    class Node {
        ListNode node;
        boolean carry;
        Node(ListNode x, boolean carry) {
            this.node = x;
            this.carry = carry;
        }
    }
    
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int n1 = getLen(l1);
        int n2 = getLen(l2);
        
        Node res = n1 >= n2 
            ? helper( l1,  l2, n1-n2) 
            :  helper( l2,  l1, n2-n1); 
        ListNode head = new ListNode(0);
        if (res.carry) {
            head.val = 1;
             head.next = res.node;
            return head;
          }
       
        return res.node; 
    }
    
    private int getLen(ListNode n) {
        if (n == null) {
            return 0;
        }
        int cnt = 0;
        while (n != null) {
            cnt ++;
            n = n.next; 
        }
        return cnt; 
    }
    
    // l1 is always longer than l2. 
    private Node helper(ListNode l1, ListNode l2, int diff) {
        if (l1.next == null) {
            int sum = l1.val + l2.val;
            ListNode cur = new ListNode(sum % 10);
            return new Node(cur, sum / 10 == 1);
        }
        
        Node postNode = diff >= 1 ? helper(l1.next, l2, diff-1) : helper(l1.next, l2.next, 0);
        
        
        int res = l1.val + (diff >= 1 ? 0 : l2.val) + (postNode.carry ? 1 : 0); 
        ListNode cur = new ListNode(res % 10);
        cur.next = postNode.node;
        
        return new Node(cur, res / 10 == 1);
    }
}
