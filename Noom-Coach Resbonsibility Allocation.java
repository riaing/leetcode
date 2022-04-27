https://www.notion.so/Noom-06333938bc8c43cca4e70a9800267a48 

global baseline responsibility allocation: (A: a%, B: b%, C: c%)

- 每一个 temporary allocation override 会覆盖一段时间段： (start_date, end_date, (A: a%, B: b%, C: c%) )
- 有override的date 按照override 计算， 没有override的date 按照baseline 计算。override 不会 overlap。
- 给定时间段 start date, end date. 计算这个时间段内的average responsibility allocation: (A: a%, B: b%, C: c%)
算法很简单，就是把list of overrides 过一遍，计算sum of percentages 再 average 就可以了。

------------------------------------------------------------------ 
enum Work {
    GROUP,
    PERSONAL,
    MANAGEMENT
}

class Overlap { // 100% overlap
    int startDate;
    int endDate;
    List<Responsibility> overlapBreakDown; // 
    public Overlap(int startDate, int endDate, List<Responsibility> overlapBreakDown) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.overlapBreakDown = overlapBreakDown;
    }
}

class Responsibility {
    Work work;
    double percentage; 
    
    public Responsibility(Work work, double percentage) {
        this.work = work;
        this.percentage = percentage; 
    }
}

public class Main {
    // get a coach's work allocation in a certain period 
    public static List<Responsibility> report(List<Responsibility> base, int start, int end, List<Overlap> overlaps) {
        int overlapDates = 0;
         // initialize the starting time for each work 
        Map<Work, Double> timeMap = new HashMap<Work, Double>();
        for (Work w : Work.values()) {
            timeMap.put(w, 0.0);
        }
        // sum up overlap time 
        for (Overlap o : overlaps) {
            // get overlap range 
            int overlapRange = o.endDate - o.startDate + 1;
            overlapDates += overlapRange;
            for (Responsibility r : o.overlapBreakDown) {
                timeMap.put(r.work, timeMap.get(r.work) + r.percentage * overlapRange);
            }
        }
        
        // base responsibility for the rest times 
        int restTime = end - start + 1 - overlapDates;
        System.out.println("rest time" + restTime);
        for (Responsibility r : base) {
             timeMap.put(r.work, timeMap.get(r.work) + r.percentage * restTime);
        }
     
        List<Responsibility> finalReport = new ArrayList<Responsibility>();
        int timeRange = end - start + 1;
        for (Work w : timeMap.keySet()) {
            double finalPercentage = timeMap.get(w) / (double) timeRange; 
            // if need to round to 2 decimals 
            finalPercentage = Math.round(finalPercentage * 100.0) / 100.0;
            
            finalReport.add(new Responsibility(w, finalPercentage));
            System.out.println("work " + w + " : " + finalPercentage + " -- total time " + timeMap.get(w) + " time range: " + timeRange); 
        }
        return finalReport;
    } 
    
    public static void main(String[] args) {
        List<Responsibility> overlap1 = Arrays.asList(
            new Responsibility(Work.GROUP, 0.5), 
            new Responsibility(Work.PERSONAL, 0.5));
        List<Responsibility> overlap2 = Arrays.asList(
            new Responsibility(Work.GROUP, 0.5), 
            new Responsibility(Work.PERSONAL, 0.5));
        
        List<Overlap> overlaps = Arrays.asList(
            new Overlap(3, 4, overlap1), new Overlap(5, 6, overlap2)); 
        
         List<Responsibility> input = Arrays.asList(
            new Responsibility(Work.PERSONAL, 0.4), 
            new Responsibility(Work.GROUP, 0.1),
            new Responsibility(Work.MANAGEMENT, 0.5)); 
        report(input, 1, 6, overlaps);
    }
}
