---------------------多余DS的解法：userData(userId,gameID -> 总时长，总轮数）多余了，只需要记录user -> 所有游戏的总时长，总轮数 --------------
https://leetcode.com/playground/ApGq9aXv 

/*
We want to know what the Top Game is, defined by: The Top Game is the game users spent the most time in.
Each line of the file has the following information (comma separated):

- timestamp in seconds (long)
- user id (string)
- game id (int)
- action (string, either "join" or "quit")
e.g.
[
"1000000000, user1, 1001, join", // user 1 joined game 1001
"1000000005, user2, 1002, join", // user 2 joined game 1002
"1000000010, user1, 1001, quit", // user 1 quit game 1001 after 10 seconds
"1000000020, user2, 1002, quit", // user 2 quit game 1002 after 15 seconds
];
In this log,
The total time spent in game 1001 is 10 seconds.
The total time spent in game 1002 is 15 seconds.
Hence game 1002 is the Top Game. -> 1002

This file could be missing some lines of data (e.g. the user joined, but then the app crashed).
If data for a session (join to quit) is incomplete, please discard the session.

Follow up - 
To recover some data, we attempt to estimate session length with this rule: If a user joined a game but did not leave, assume they spent the minimum of 

- time spent before they joined a diffe‍‌‍‍‌‍‍‌‍‍‌‍‌‍‍‌‌‍rent game; and 

- average time spent across the same user's gaming sessions (from join to leave)

 e.g. 

"1500000000,user1,1001,join" 

"1500000010,user1,1002,join" 

"1500000015,user1,1002,quit" 

The user spent 5 seconds in game 2, so we assume they spent 5 seconds in game 1

Write a function that returns the top game ID, given an array of strings representing
each line in the log file.
*/

class UserStatus {
    String inGame; 
    long joinTime;
    
    public UserStatus(String inGame, long joinTime) {
        this.inGame = inGame; 
        this.joinTime = joinTime; 
    }
}

class Info {
    long totalTime;
    int round;
    
    public Info(long totalTime, int round) {
        this.totalTime = totalTime;
        this.round = round;
    }
}
public class Main {   
    public static String topGame(String[] arr) {
        Map<String, UserStatus> userStatusMap = new HashMap<>(); // userid -> {in which game, join time}
        Map<String, Info> userData = new HashMap<>(); // userId+","+gameId -> {total time, total round} 
        Map<String, List<Long>> invalidSession = new HashMap(); // userId + gameId -> List: time spent in last game 
        
        
        // 1. 通过user status map知道user上次游戏的加入时间。同时更新 userData
        for (String log : arr) {
            String[] cur = log.split(",");
            long timeStamp = Long.parseLong(cur[0]); 
            String user = cur[1];
            String game = cur[2];
            String type = cur[3]; 
            
            userStatusMap.putIfAbsent(user, new UserStatus(null, 0)); 
            userData.putIfAbsent(user+","+game, new Info(0,0)); 
            // 
            String lastGame = userStatusMap.get(user).inGame;
            if (type.equals("quit")) {
                // 1. drop the invalid quit game 
                if (lastGame == null || !lastGame.equals(game)) { 
                    continue;  
                }
                // 2. calculate one session 
                long session = timeStamp - userStatusMap.get(user).joinTime; 
                userData.get(user+","+game).totalTime = userData.get(user+","+game).totalTime + session;
                userData.get(user+","+game).round = userData.get(user+","+game).round + 1;
                // 3. 记得复原 
                userStatusMap.put(user, new UserStatus(null, 0)); 
            }
            else if (type.equals("join")) {
                // follow up: 判断invalid session. lastGame的时间 = 持续到这次join的时间
                if (lastGame != null) {
                    long lastTime = userStatusMap.get(user).joinTime; 
                    invalidSession.putIfAbsent(user+","+lastGame, new ArrayList<>()); 
                    invalidSession.get(user+","+lastGame).add(timeStamp - lastTime);
                }
                // 更新user status map
                userStatusMap.get(user).inGame = game;
                userStatusMap.get(user).joinTime = timeStamp;
            }
        }
        
        // 2. 统计每个game的时间
        long maxTime = 0;
        String topGame = "";
        Map<String, Info> gameData = new HashMap<String, Info>(); 
        for (String data : userData.keySet()) {
            String game = data.split(",")[1];
            gameData.putIfAbsent(game, new Info(0, 0));
            gameData.get(game).totalTime = gameData.get(game).totalTime + userData.get(data).totalTime;
            gameData.get(game).round = gameData.get(game).round + userData.get(data).round;
            if (gameData.get(game).totalTime > maxTime) {
                maxTime = gameData.get(game).totalTime;
                topGame = game; 
            }
        }
        
        // --------- follow up, recover data by 2 rules -------------------------------------------------- 
        // 1. 统计所有invalid session，which 之后user不在join游戏 -> userStatusMap 中user 不为null的
        for (String user : userStatusMap.keySet()) {
            if (userStatusMap.get(user).inGame != null) {
                String userGame = user + "," + userStatusMap.get(user).inGame;
                // 加到invalid session map中， 它对应的游戏时间此时为0 （因为没有下个游戏进来）
                invalidSession.putIfAbsent(userGame, new ArrayList<>());
                invalidSession.get(userGame).add(Long.MAX_VALUE);       
            }
        }
        // 2. 计算average time
        // 2.1 每个user的总游戏时长，轮数 
        Map<String, Info> userTotal = new HashMap<String, Info>(); 
        for (String userGame : userData.keySet()) {
            if (userData.get(userGame).totalTime != 0) { // ！！ 只考虑非零的session！！
                 String user = userGame.split(",")[0];
                 userTotal.putIfAbsent(user, new Info(0, 0));
                 userTotal.get(user).totalTime = userTotal.get(user).totalTime + userData.get(userGame).totalTime;
                 userTotal.get(user).round = userTotal.get(user).round + userData.get(userGame).round;
            }            
        }
        // 2.2 计算每个user的ave 
        Map<String, Long> userAveTime = new HashMap<String, Long>();
        for (String user : userTotal.keySet()) {
            userAveTime.put(user, userTotal.get(user).totalTime / userTotal.get(user).round);
        }
        // 3. 给invalid session 取min, 修复的game session加到game map
        for (String key : invalidSession.keySet()) {
            String user = key.split(",")[0];
            String game = key.split(",")[1];
            for (Long option1 : invalidSession.get(key)) {
                Long recoveredTime = Math.min(option1, userAveTime.get(user));
                // add it to game map 
                gameData.putIfAbsent(game, new Info(0,0));
                gameData.get(game).totalTime = gameData.get(game).totalTime + recoveredTime;
                gameData.get(game).round = gameData.get(game).round + 1;
            }
        }
        
        
        System.out.println("user total ");
        userTotal.forEach((k,v) -> System.out.println(k + " : " + v.totalTime + " round " + v.round)); 
        System.out.println("------------------ user ave ");
        userAveTime.forEach((k,v) -> System.out.println(k + " : " + v)); 
        System.out.println("game map ");
        gameData.forEach((k,v) -> System.out.println(k + " : " + v.totalTime + " round " + v.round)); 
        System.out.println("user map ");
        userData.forEach((k,v) -> System.out.println(k + " : " + v.totalTime + " round " + v.round)); 
        System.out.println("-------------------- userStatusMap ");
        userStatusMap.forEach((k,v) -> System.out.println(k + " : " + v.inGame + " startTime " + v.joinTime)); 
        System.out.println("-------------------------- invalid sessin");
        invalidSession.forEach((k,v) -> System.out.println(k + " : " + v)); 
        return topGame; 
    }
    
      public static void main(String[] args) {
          String[] arr = {
                "0,user1,1000,join", // invalid 
                "1,user1,1001,join", // user 1 joined game 1001
                "5,user2,1002,join", // user 2 joined game 1002
                "10,user1,1001,quit", // user 1 quit game 1001 after 10 seconds
                "20,user2,1002,quit", // user 2 quit game 1002 after 15 seconds}
                "24,user2,A,join", // invalid 
                "30,user2,B,join",}; // invalid 
              
        System.out.println(topGame(arr));
    }
}
                                      
              ---------------------------- update的解法：只记录user -> 总游玩时间，不需要：user+game ->总游玩时间 -----------------------------------------
class UserStatus {
    String inGame; 
    long joinTime;
    
    public UserStatus(String inGame, long joinTime) {
        this.inGame = inGame; 
        this.joinTime = joinTime; 
    }
}

class Info {
    long totalTime;
    int round;
    
    public Info(long totalTime, int round) {
        this.totalTime = totalTime;
        this.round = round;
    }
}
public class Main {   
    public static String topGame(String[] arr) {
        Map<String, UserStatus> userStatusMap = new HashMap<>(); // userid -> {in which game, join time}
        Map<String, Info> gameData = new HashMap<String, Info>(); // gameId -> {total time, total round}
        // follow up 
        Map<String, Info> userData = new HashMap<>(); // userId -> {所有游戏的total time, total round} 
        Map<String, List<Long>> invalidSession = new HashMap(); // userId + gameId -> List: time spent in last game 
    
        
        // 1. 通过user status map知道user上次游戏的加入时间。同时更新 userData
        for (String log : arr) {
            String[] cur = log.split(",");
            long timeStamp = Long.parseLong(cur[0]); 
            String user = cur[1];
            String game = cur[2];
            String type = cur[3]; 
            
            userStatusMap.putIfAbsent(user, new UserStatus(null, 0)); 
            String lastGame = userStatusMap.get(user).inGame;
            if (type.equals("quit")) {
                // 1. drop the invalid quit game 
                if (lastGame == null || !lastGame.equals(game)) { 
                    continue;  
                }
                // 2. calculate one session 
                long session = timeStamp - userStatusMap.get(user).joinTime; 
                // add the session into game map 
                gameData.putIfAbsent(game, new Info(0,0));
                gameData.get(game).totalTime += session;
                gameData.get(game).round += 1;
                //----  follow up, add to user map ------ 
                userData.putIfAbsent(user, new Info(0,0)); 
                userData.get(user).totalTime += session;
                userData.get(user).round += 1; 
                // ---follow up ---------
                // 3. 记得复原 
                userStatusMap.put(user, new UserStatus(null, 0)); 
            }
            else if (type.equals("join")) {
                // follow up: 判断invalid session. lastGame的时间 = 持续到这次join的时间
                if (lastGame != null) {
                    long lastTime = userStatusMap.get(user).joinTime; 
                    invalidSession.putIfAbsent(user+","+lastGame, new ArrayList<>()); 
                    invalidSession.get(user+","+lastGame).add(timeStamp - lastTime);
                }
                // ------ follow up 完 ----------------- 
                // 更新user status map
                userStatusMap.get(user).inGame = game;
                userStatusMap.get(user).joinTime = timeStamp;
            }
        }
        
        // 2. 统计每个game的时间
        long maxTime = 0;
        String topGame = "";
        for (String g : gameData.keySet()) {
            if (gameData.get(g).totalTime > maxTime) {
                maxTime = gameData.get(g).totalTime;
                topGame = g; 
            }
        }
        
        // --------- follow up, recover data by 2 rules -------------------------------------------------- 
        // 1. 统计所有invalid session，which 之后user不在join游戏 -> userStatusMap 中user 不为null的
        for (String user : userStatusMap.keySet()) {
            if (userStatusMap.get(user).inGame != null) {
                String userGame = user + "," + userStatusMap.get(user).inGame;
                // 加到invalid session map中， 它对应的游戏时间此时为0 （因为没有下个游戏进来）
                invalidSession.putIfAbsent(userGame, new ArrayList<>());
                invalidSession.get(userGame).add(Long.MAX_VALUE);       
            }
        }
        
        // 2 计算每个user的ave 
        Map<String, Long> userAveTime = new HashMap<String, Long>();
        for (String user : userData.keySet()) {
            userAveTime.put(user, userData.get(user).totalTime / userData.get(user).round);
        }
        // 3. 给invalid session 取min, 修复的game session加到game map
        for (String key : invalidSession.keySet()) {
            String user = key.split(",")[0];
            String game = key.split(",")[1];
            for (Long option1 : invalidSession.get(key)) {
                Long recoveredTime = Math.min(option1, userAveTime.get(user));
                // add it to game map 
                gameData.putIfAbsent(game, new Info(0,0));
                gameData.get(game).totalTime = gameData.get(game).totalTime + recoveredTime;
                gameData.get(game).round = gameData.get(game).round + 1;
            }
        }
        
        System.out.println("------------------ user ave ");
        userAveTime.forEach((k,v) -> System.out.println(k + " : " + v)); 
        System.out.println("----------------------game map");
        gameData.forEach((k,v) -> System.out.println(k + " : " + v.totalTime + " round " + v.round)); 
        System.out.println("------------------user map ");
        userData.forEach((k,v) -> System.out.println(k + " : " + v.totalTime + " round " + v.round)); 
        System.out.println("-------------------- userStatusMap");
        userStatusMap.forEach((k,v) -> System.out.println(k + " : " + v.inGame + " startTime " + v.joinTime)); 
        System.out.println("-------------------------- invalid sessin");
        invalidSession.forEach((k,v) -> System.out.println(k + " : " + v)); 
        return topGame; 
    }
    
      public static void main(String[] args) {
          String[] arr = {
                "0,user1,1000,join", // invalid 
                "1,user1,1001,join", // user 1 joined game 1001
                "5,user2,1002,join", // user 2 joined game 1002
                "10,user1,1001,quit", // user 1 quit game 1001 after 10 seconds
                "20,user2,1002,quit", // user 2 quit game 1002 after 15 seconds}
                "24,user2,A,join", // invalid 
                "30,user2,B,join",}; // invalid 
              
        System.out.println(topGame(arr));
    }
}
