// https://www.notion.so/957c35a025b24fdb9646c55736f63ad3?v=3917df87096f4920983dd4b108ed0e56&p=1a01ff3b28d64320b019dbaa4f539266&pm=s 
class Node {
    int id;
    int startTime;
    
    public Node(int id, int startTime) {
        this.id = id;
        this.startTime = startTime;
    }
}
// linkedhashMap 解法
class RPCTimeOut {
    int timeout;
    LinkedHashMap<Integer, Node> ddlMap = new LinkedHashMap<>();
    
    public RPCTimeOut(int timeout) {
        this.timeout = timeout;
    }
    public int arriveStart(int id, int timestamp) { // return -1 if no log timeout
        int oldestKey = -1; 
        if (isTimeOut(timestamp)) {
            oldestKey = ddlMap.keySet().iterator().next();
        }
        ddlMap.put(id, new Node(id, timestamp));
        return oldestKey; 
    }
    
    public int arriveEnd(int id, int timestamp) { // return -1 if no log timeout
        int oldestKey = -1; 
        if (isTimeOut(timestamp)) {
            oldestKey = ddlMap.keySet().iterator().next();
        }
        ddlMap.remove(id);
        return oldestKey; 
    }
    
    
    private boolean isTimeOut(int curTime) {
        if (ddlMap.keySet().size() != 0) {
            int oldestKey = ddlMap.keySet().iterator().next();
            return curTime - ddlMap.get(oldestKey).startTime >= timeout;
        }
        return false; 
    }
}

public class Main {
    public static void main(String[] args) {
        RPCTimeOut test = new RPCTimeOut(3);
        System.out.println(test.arriveStart(1, 0));
        System.out.println(test.arriveStart(2, 1));
        System.out.println(test.arriveEnd(1, 2));
    
        System.out.println(test.arriveStart(3, 6)); // 2 
        System.out.println(test.arriveEnd(2, 7));
        System.out.println(test.arriveEnd(3, 8));
    }
}
