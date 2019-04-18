Design your implementation of the linked list. You can choose to use the singly linked list or the doubly linked list. A node in a singly linked list should have two attributes: val and next. val is the value of the current node, and next is a pointer/reference to the next node. If you want to use the doubly linked list, you will need one more attribute prev to indicate the previous node in the linked list. Assume all nodes in the linked list are 0-indexed.

Implement these functions in your linked list class:

get(index) : Get the value of the index-th node in the linked list. If the index is invalid, return -1.
addAtHead(val) : Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
addAtTail(val) : Append a node of value val to the last element of the linked list.
addAtIndex(index, val) : Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
deleteAtIndex(index) : Delete the index-th node in the linked list, if the index is valid.
Example:

MyLinkedList linkedList = new MyLinkedList();
linkedList.addAtHead(1);
linkedList.addAtTail(3);
linkedList.addAtIndex(1, 2);  // linked list becomes 1->2->3
linkedList.get(1);            // returns 2
linkedList.deleteAtIndex(1);  // now the linked list is 1->3
linkedList.get(1);            // returns 3
Note:

All values will be in the range of [1, 1000].
The number of operations will be in the range of [1, 1000].
Please do not use the built-in LinkedList library.

----------------------------------------------------------------------------------------------------------------
class MyLinkedList {
    ListNode head;
    ListNode tail;
    int size;
    
    class ListNode {
        int val;
        ListNode next;
        
        public ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }
    
    /** Initialize your data structure here. */
    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    
    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if (index >= size || index < 0 || (index == 0 && head == null)) {
            return -1;
        }
        if (index == 0) {
            return head.val;
        }
        else if (index == size - 1) {
            return tail.val;
        }
        else {
             ListNode pre = head;
            for (int i = 0; i < index - 1; i++) {
                pre = pre.next;
            }
            return pre.next.val;
        }
    }
    private void print() {
        ListNode cur = head;
        String s = "";
        while (cur != null) {
            s = s+ " -> " + cur.val;
            cur = cur.next;
        }
        System.out.println(s);
    }
    
    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
       
        ListNode newHead = new ListNode(val);
        newHead.next = head;
        head = newHead;
        if (tail == null) {
            tail = head;
        }
        size++;
    }
    
    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
         ListNode newTail = new ListNode(val);
        if (head == null) {
            head = tail = newTail;
        }
        // if (tail == null) { 因为走到这里时tail不会为null了，所以可以不写。
        //     tail = newTail;
        //     head.next = tail;
        // }
        else {
            tail.next = newTail;
            tail = newTail;
        }
        size++;  
    }
    
    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if (index > size) {
            return;
        }
        // 应题目要求，addIndex(-1, ..)时自动加在头上。。。
        else if (index <= 0) {
            addAtHead(val);
        }
        else if (index == size) {
            addAtTail(val);
        }
        else {
            // find the node pre to the postion
            ListNode pre = head;
            for (int i = 0; i < index - 1; i++) {
                pre = pre.next;
            }
            // insert node after pre
            ListNode tmp = pre.next;
            pre.next = new ListNode(val);
            pre.next.next = tmp;
            size++;
             
        }
       
    }
    
    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if (index >= size || index < 0 || (index == 0 && head == null)) {
            return;
        }
        else if (index == 0 ) {
            head = head.next;
        }
        else {
            ListNode pre = head; 
            for (int i = 0; i < index - 1; i++) {
                    pre = pre.next;
            }
            pre.next = pre.next.next;
            if (index == size - 1) {
                tail = pre;
            }
        }
        size--;
          print();
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */
