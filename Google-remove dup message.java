https://leetcode.com/playground/koYwNWmD 


class Message {
    int time; 
    String msg; 
    
    public Message(int time, String msg) {
        this.time = time;
        this.msg = msg; 
    }
}

// 第二问用
class MessageInfo {
    int lastTime; // last time seeing this message 
    boolean seenDup; // in the 10s window, this message has duplicates

    public MessageInfo(int lastTime, boolean seenDup) {
        this.lastTime = lastTime;
        this.seenDup = seenDup;
    } 
}

// "static void main" must be defined in a public class.
public class Main {
    public static void main(String[] args) {
        Message m1 = new Message(10, "a");
        Message m2 = new Message(11, "b");
        Message m3 = new Message(12, "c");
        Message m4 = new Message(13, "a");
        Message m5 = new Message(14, "b");
        Message m6 = new Message(21, "a");
        Message m7 = new Message(35, "a");
        List<Message> input = Arrays.asList(m1, m2, m3, m4, m5, m6, m7);
        removeDup(input); // return。 return [10: a, 11: b, 12: c, 35: a] 
        // removeAllDups(input); //第二问. return [12: c, 35: a] 顺序不重要
    }
    
    // Q1: Remove duplicate message in each 10 second window. 解法：sliding window 
    // window中有重复时，remove重复的那一个
    public static List<Message> removeDup(List<Message> inputs) {
        int right = 0;
        int left = right;
        Map<String, Integer> map = new HashMap<String, Integer>();
        List<Message> output = new LinkedList<Message>();
        while (left < inputs.size()) {
            Message leftMsg = inputs.get(left);
            Message rightMsg = inputs.get(right);
            map.put(leftMsg.msg, map.getOrDefault(leftMsg.msg, 0) + 1); 
            
            while (leftMsg.time -  inputs.get(right).time > 10) { // 注意：这里的rightMsg是变化的
                rightMsg = inputs.get(right);
                // remove from map and move forward right pointer 
                map.put(rightMsg.msg, map.get(rightMsg.msg) - 1);
                right++;
            }
            if (map.get(leftMsg.msg) == 1) {
                output.add(leftMsg);
            }
            left++;
        }
        output.forEach(o -> System.out.println("output " + o.time + ": " + o.msg));
        return output; 
    }
    // 只要发现是有重复，把两个message全remove掉
    // Q2: Remove all messages that are duplicated in each 10 second window. 
    /*
    解法: 类似rate limit，对于每个msg，查看它之前的10s有没有出现过-> 用map来记录每个message最后出现的时间。
    但这样还不够：比如A出现在10， 19，30. 尽管19-30过了10s，但因为在10-19中有了重复，所以10的A和19的A都要删除：那么就需要一个boolean记录msg是否出现过重复。
    
    */
    public static List<Message> removeAllDups(List<Message> inputs) {
        Map<String, MessageInfo> map = new HashMap<String, MessageInfo>(); // key - message, value: the last time seen this message, and if seen duplicates in 10s window
        List<Message> output = new LinkedList<Message>();
        for (int i = 0; i < inputs.size(); i++) {
            Message cur = inputs.get(i);
            if (!map.containsKey(cur.msg)) {
                map.put(cur.msg, new MessageInfo(cur.time, false));
            }
            else {
                // check if it's within 10s window 
                if (cur.time - map.get(cur.msg).lastTime <= 10) {
                    // update MessageInfo
                    MessageInfo toUpdateInfo = map.get(cur.msg);
                    toUpdateInfo.lastTime = cur.time;
                    toUpdateInfo.seenDup = true;
                }
                else { // 超过了10s window 
                    // 如果上一个没有dup，加到output里
                    if (!map.get(cur.msg).seenDup) {
                        output.add(new Message(map.get(cur.msg).lastTime, cur.msg));
                    }
                    // 更新window
                    MessageInfo toUpdateInfo = map.get(cur.msg);
                    toUpdateInfo.lastTime = cur.time;
                    toUpdateInfo.seenDup = false;
                }
            }
        }
        // 把map中没有写到output的item 写出
        for (String key : map.keySet()) {
            MessageInfo info = map.get(key);
            if (!info.seenDup) {
                output.add(new Message(info.lastTime, key));
            }
        }
        output.forEach(o -> System.out.println("output " + o.time + ": " + o.msg));
        return output; 
    }
}
