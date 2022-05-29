https://leetcode.com/playground/RHsbHzDJ 

/*

- 类似于你可以put(a, 1)，然后get (a)直接返回1，
- 你也可以call一个function begin() ，自己要实现这个function，然后宣布进入transaction，然后put(a,1)，这个时候如果马上get(a)，你还是要返回1，但put完之后，你可以call rollback()，这个function也要自己实现，rollback() 之后你再call get(a)，就返回null，你也可以 call commit(）,那么这个结果就storage就持久化了。

follow up是如何实现多transaction，transaction可以嵌套，就是在一个transaction下面可以再开一个新的子transaction，返回一个transaction id，最后call commit rollback带这个id，会一次性commit和rollback所有的这个transaction和他的子transaction.
*/

/*
参考： https://leetcode.com/discuss/interview-question/279913/Key-Value-Store-with-transaction 
定义： https://docs.oracle.com/cd/E17276_01/html/gsg_xml_txn/java/nestedtxn.html 
*/

class OneTime { // 用一个boolean来通知是否在commit中
    Map<String, String> txn;
    Map<String, String> storage; 
    boolean inTxn; 
    public OneTime() {
        this.txn = new HashMap<>(); 
        this.storage = new HashMap<>();
        this.inTxn = false; 
    }
    public void set(String key, String val) {
        if (inTxn) {
            txn.put(key, val);
        }
        else {
            storage.put(key, val);
        }
    }
    
    public String get(String key) {
        if (txn.containsKey(key)) {
            return txn.get(key);
        }
        // assume always valid 
        return storage.get(key);
    }
    
    public void begin() {
        inTxn = true;
    }
    public void commit() {
        for (String k : txn.keySet()) {
            storage.put(k, txn.get(k));
        }
        inTxn = false; 
    }
    public void rollBack() {
        txn = new HashMap<>(); 
        inTxn = false; 
    }
}


class KVStore {
    List<Map<String, String>> txns; // list 模拟stack
    Map<String, String> storage;
    public KVStore() {
        this.txns = new ArrayList<>(); 
        this.storage = new HashMap<String, String>(); 
    }
    public void set(String key, String val) {
        // 此时没有txn
        if (txns.size() == 0) {
            storage.put(key, val);
        }
        else {
            int top = txns.size() - 1;
            Map<String, String> cur = txns.get(top);
            cur.put(key, val);
        }
    }
    
    public String get(String key) {
        // 先从stack里找
        int index = txns.size() - 1;
        while (index >= 0) {
            Map<String, String> cur = txns.get(index);
            if (cur.containsKey(key)) {
                return cur.get(key);
            }
            index--; 
        }
        // 可能在stroage里
        if (storage.containsKey(key)) {
            return storage.get(key);
        }
        return "no such element"; 
    }
    
    public void begin() {
        //开始准备stack的一层
        txns.add(new HashMap<>()); 
    }
    
    public void commit() {
        // 首先拿出栈顶
        int size = txns.size(); 
        Map<String, String> topLevel = txns.get(size - 1); 
        txns.remove(size -1);
        // 如果不是最外层：，并合并到前一层
        if (size > 1) {
            Map<String, String> cur = txns.get(txns.size() - 1); 
            for (String k : topLevel.keySet()) { // 说明最底层的commit会覆盖上一层的相同key
                cur.put(k, topLevel.get(k));
            }
        }
        // 如果是第一层的txn，写到storage中，并从stack中删除
        else {
            for (String k : topLevel.keySet()) {
                storage.put(k, topLevel.get(k));
            }
        }
    }
    public void rollBack() {
        txns.remove(txns.size() - 1); // remove 掉栈顶
    }
}

public class Main {
    public static void main(String[] args) {
        // one time test 
        OneTime ot = new OneTime();
        ot.set("1", "a");
        ot.begin();
        ot.set("1", "b");
        ot.rollBack();
        ot.begin();
        ot.set("2", "b");
        ot.commit();
        // System.out.println(ot.get("2"));
        
        // nested TXN
        KVStore nested = new KVStore();
        nested.set("not txn", "A");
        nested.begin();
        nested.set("1", "A");
        nested.begin();
        nested.set("1", "B");
        nested.begin();
        nested.set("1", "C");
        System.out.println(nested.txns); // [{1=A}, {1=B}, {1=C}]
        // 大情景A 
        // nested.commit();
        // System.out.println(nested.txns); // [{1=A}, {1=C}]
        // nested.rollBack();
        // System.out.println(nested.txns); // [{1=A}]
        // // 情景1 
        // // nested.rollBack(); 
        // //  System.out.println(nested.txns); // []
        // // System.out.println(nested.storage); //{} 
        // // 情景2 
        // nested.commit(); 
        // System.out.println(nested.txns); // []
        // System.out.println(nested.storage); //{1=A } 
        
        // 大情景B 
        nested.commit();
        System.out.println(nested.txns); // [{1=A}, {1=C}]
        nested.commit();
        System.out.println(nested.txns); // [{1=C}]
        nested.commit();
        nested.set("not txn", "B");
        System.out.println(nested.txns); // []
        System.out.println(nested.storage); //{1=C, not txn=B}
        
        
        
        
    }
}
