https://leetcode.com/playground/oMiutZdw

/*
Design a browser history 
follow up 1: multi tab 
follow up 2: bookmark 
*/


class Node {
    Node next;
    Node pre;
    String val;
    
    public Node(String val) {
        this.val = val; 
    }
}

class BrowserHistory {
    Set<String> marks; 
    Node sudo; 
    Node cur; 
    
    public BrowserHistory(String homepage, Set<String> marks) {
        this.sudo = new Node(homepage);
        sudo.pre = null;
        sudo.next = null;
        this.cur = sudo; 
        this.marks = marks; 
    }
    
    public void visit(String url) {
        Node newNode = new Node(url);
        newNode.pre = cur; 
        newNode.next = null;
        cur.next = newNode; // 关键
        cur = newNode; 
        // System.out.println("cur." + cur.next);
    }
    
    public String back(int steps) {
        int move = steps; 
        while (move > 0 && cur.pre != null) {
            cur = cur.pre;
            move--;
        }
       
        return cur.val;
    }
    
    public String forward(int steps) {
        int move = steps; 
        while (move > 0 && cur.next != null) {
            cur = cur.next;
            move--;
        }
        return cur.val;
    }
    
    // for bookmark 
    public void setBookmark() {
        //去重过程
        marks.add(cur.val); 
    }
}

class Browser {
    Map<Integer, BrowserHistory> tabMap = new HashMap<Integer, BrowserHistory>();
    // for book mark 
    Set<String> bookmark = new HashSet<String>(); 
    public Browser() {
    }
    public int openTab(String homePage) {
        BrowserHistory newTab = new BrowserHistory(homePage, bookmark);
        int tabID = tabMap.size();
        tabMap.put(tabID, newTab);
        return tabID;
    }
    
    public String closeTab(int tabID) {
        if (!tabMap.containsKey(tabID)) {
            return "Error";
        }
        tabMap.remove(tabID);
        return "succeed";
    }
    
    // for bookmark 
    public Set<String> getBookmark() {
        return bookmark;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        // folow up 1： multi tabs 
        Browser b = new Browser();
        int tabID = b.openTab("google.com"); 
        b.tabMap.get(tabID).visit("leetcode.com");
        int tab2 = b.openTab("ria.com");
        
        // follow up 2: bookmark 
        b.tabMap.get(tabID).setBookmark(); 
        b.tabMap.get(tab2).setBookmark();
        System.out.println(b.getBookmark());
    }
}
