https://leetcode.com/playground/JiUn2ycd 

/*
coding 数据OOD那种，题目特别长。大概意思是一个track 运动路线的功能，用户有4种request：start, stop, pause, resume. 意思分别是
start: 表示运动开始
stop: 表示运动结束
pause: 运动并没有结束，只是暂停，所以pause和resume之前的时间和mile都不能算。每一对start和stop之间以有多个pause/resume
resume: 一旦resume表示又要开始计算时间的mile
可以assume 已经有一个gps 的service可以一直告诉你的coordinate

因为location是可以为null的，比如gps的service坏了，没有传过来，我那个面试官是个搞front end的，特别强调这一点，需要我说出来。。。

第二问，就是写method的，是一个具体的method，举个例子给你，假设你已经collect了数据，算出下面这种形式， 用户在
跑第一个mile: spent 8 mins
跑第二个mile: spent 12 mins
跑第三个mile: spend 20 mins
这里就是要注意idle的时候，是不能算时间和距离的
*/

/*
Status{START, PAUSE, RESUME, STOP}
Location {longt, lat}
UserStatus{userId, status, time, Loc}
RunInfo(mile, time) 

input: List of UserStatus 
output: List of RunInfo for one user 

processing: Map{userId, UserStatus}, Map{userId, List(RunInfo)} 
getInfo: calculate from RunInfo 
*/

/*
方法： 
Round double 到一位小数： Math.round(number*10.0) / 10.0 
*/
enum Status {
    START,
    PAUSE,
    RESUME,
    STOP
}

class Location {
    long longt;
    long lat;
    public Location(long longt, long lat) {
        this.longt = longt;
        this.lat = lat; 
    }
}

class UserStatus {
    int userId;
    Status status;
    long time;
    Location loc; 
    public UserStatus( int userId, Status status, long time, Location loc) {
        this.userId = userId;
        this.status = status;
        this.time = time;
        this.loc = loc;
    }
}

class RunInfo {
    double mile; 
    double duration; //问：结果可以round吗？比如1mile 跑了0.8分钟可以round成1分钟？不行这就得成double
    
    public RunInfo(double mile, double duration) {
        this.mile = mile;
        this.duration = duration; 
    }
}



public class Main {
    static Map<Integer, List<RunInfo>> userData = new HashMap<>();
    static Map<Integer, UserStatus> userState = new HashMap<>();
    
    public static void processing(List<UserStatus> activities) {
        for (UserStatus activity : activities) {
            // sanity check 
            if (activity.status == null || activity.loc == null) {
                continue; 
            }
            int curUser = activity.userId;
            Status curStatus = activity.status;
            long curTime = activity.time;
            Location curLoc = activity.loc; 
            
            if (curStatus == Status.START) {
                userState.put(curUser, new UserStatus(curUser, curStatus, curTime, curLoc)); 
            }
            if (curStatus == Status.PAUSE) {
                if (!userState.containsKey(curUser) || userState.get(curUser).status == Status.STOP) {  // sanity check
                    continue; 
                }
                // 1. calculate 这段的 runInfo. 算euclidean distance
                UserStatus lastStatus = userState.get(curUser);
                Location lastLoc = lastStatus.loc;
                double mileRan = Math.hypot(Math.abs(lastLoc.longt - curLoc.longt), Math.abs(lastLoc.longt - curLoc.longt));
                userData.putIfAbsent(curUser, new ArrayList<>());
                // 2. add 一段，round 一位
                userData.get(curUser).add(new RunInfo(Math.round(mileRan * 10.0) / 10.0, curTime - lastStatus.time));
                // 3. 更新user state
                lastStatus.status = curStatus;
                lastStatus.time = curTime;
                lastStatus.loc = curLoc;
            }
            if (curStatus == Status.RESUME) {
                // add sanity check 
                    userState.put(curUser, new UserStatus(curUser, curStatus, curTime, curLoc));
            }
            if (curStatus == Status.STOP) {
                 if (!userState.containsKey(curUser) || userState.get(curUser).status == Status.PAUSE) {  // sanity check
                    continue; 
                }
                // 1. calculate 这段的 runInfo. 算euclidean distance
                 Location lastLoc = userState.get(curUser).loc; 
                 double mileRan = Math.hypot(Math.abs(lastLoc.longt - curLoc.longt), Math.abs(lastLoc.longt - curLoc.longt));
                userData.putIfAbsent(curUser, new ArrayList<>());
                // 2. add 一段，round 一位
                userData.get(curUser).add(new RunInfo(Math.round(mileRan * 10.0) / 10.0, curTime - userState.get(curUser).time));
                // 3. remove user state
                userState.remove(curUser); 
            }
        }
        userData.forEach((k,v) -> v.forEach(o -> System.out.println("user" + k + " mile: " +o.mile + " time:" + o.duration)));
    }
    
    public static List<RunInfo> getExcerciseData(int userId) {
        List<RunInfo> res = new ArrayList<>(); //记住: 结果的时间变成double
        List<RunInfo> rawData = userData.get(userId); 
        // test 
        // List<RunInfo> rawData = Arrays.asList(new RunInfo(0.5, 2), new RunInfo(2, 1)); 
        
        double mileRun = 1.0; 
        // bucket 
        RunInfo bucket = null; 
        for (RunInfo cur : rawData) {
            double avg = cur.duration / cur.mile; 
            while (cur.mile > 0) {
                // 1, 补到bucket上
                if (bucket != null) {
                    if (cur.mile < 1.0 - bucket.mile) {
                        bucket.mile += cur.mile;
                        bucket.duration += cur.duration;
                        // 更新cur 
                        cur.mile = 0.0; cur.duration = 0; 
                    }
                    else { // 凑成一个mile
                        double time2add = avg * (1.0 - bucket.mile);
                        System.out.println("time2add " + time2add + " total in bucket " + bucket.duration + time2add + " round " + Math.round((bucket.duration + time2add)  * 10.0) / 10.0); 
                        
                        res.add(new RunInfo(mileRun, bucket.duration + time2add));
                        // 更新bucket和cur和mile计数
                        cur.mile -= (1.0 - bucket.mile); 
                        cur.duration -= time2add;
                        mileRun = mileRun+1;
                        bucket = null;
                    }
                }
                else {
                    if (cur.mile < 1) {
                        bucket = new RunInfo(cur.mile, cur.duration);
                        // 更新cur 
                        cur.mile = 0.0; cur.duration = 0; 
                    }
                    else {
                        // System.out.println("avg" + Math.round(avg));
                        // 添加个新mile + 更新cur
                        res.add(new RunInfo(mileRun, Math.round(avg*10.0) / 10.0));
                        cur.mile -= 1;
                        cur.duration -= avg;
                        mileRun = mileRun+1;
                    }
                }
            }
        }
        // 可能bucket里还有一点剩余
        if (bucket != null) {
            res.add(new RunInfo(bucket.mile, bucket.duration)); 
        }
        res.forEach(o -> System.out.println("result" + " mile: " +o.mile + " time:" + o.duration));
        return res; 
    }
    
    
    public static void main(String[] args) {
        List<UserStatus> input = Arrays.asList(
            new UserStatus(1, Status.START, 0, new Location(0,0)),
            new UserStatus(1, Status.PAUSE, 1, new Location(1,0)),
            new UserStatus(1, Status.RESUME, 2, new Location(1,1)),
            new UserStatus(1, Status.STOP, 4, new Location(3,3)),
            new UserStatus(2, Status.START, 2, new Location(1,1)),
            new UserStatus(2, Status.STOP, 5, new Location(3,3)));
        // processing(input);
        getExcerciseData(1);
        
    }
}
