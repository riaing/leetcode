

/*
第一题是给定一组<time, value>，然后再给一个threshold，要求返回一个list of interval，在该interval期间value是大于等于threshold的，比如：
假定threshold是0.8
list[<1, 0.8>] 返回[[1, 1]],
list[<1, 0.7>] 返回[[]],
list[<1, 0.8>, <3, 0.9>, <4, 0.3>, <6, 1>] 返回[[1, 3], [6, 6]]  
-> 解法：扫一遍，找到一个解时，查看其连着的后面解是否满足

第二题是假定是List<List<time, value>>, 给你一个threshold，要求返回一个list of interval，在该interval期间，所有的outter list都必须cover该interval。
实际上就是上面的方程挨个一遍，就会有一个list<list<Interval>>, 把所有的list<Interval>两两合并，最后返回一个common interval，两两合并过程基本上是标准的merge interval
我第二题做错了，因为一个Interval为了满足commonly shared，有可能会被拆分成好几个Interval。

*/

class Interval {
    public int start;
    public int end;

    public Interval() {}

    public Interval(int _start, int _end) {
        start = _start;
        end = _end;
    }
};

public class Main {
    // 第一问
    public static List<double[]> findIntervals(List<double[]> input, double threshould) {
        List<double[]> res = new ArrayList<double[]>();
        int i = 0;
        while (i < input.size()) {
            if (input.get(i)[1] >= threshould) {
                double[] cur = {input.get(i)[0], input.get(i)[0]};
                while (i + 1 < input.size() && input.get(i+1)[1] >= threshould) {
                    System.out.println("chec " + input.get(i+1)[1]);
                    cur[1] = input.get(i+1)[0];
                    i = i+1;
                }
                res.add(cur);
            }
            i++;
        }
        res.forEach( o -> System.out.println(Arrays.toString(o)));
        return res;
    }
    
     // 第二问
      public static List<Interval> intersectionOfKList(List<List<Interval>> schedule) { // 有k个interval list，找到所有的intersection 
          List<Interval> res = new ArrayList<Interval>();
          int[] listIndex = new int[schedule.size()];  // 记录在某个list中的interval的index。listIndex[k] = val : 在第k个list中，拿val个interval
          Arrays.fill(listIndex, 0);
         
          while (true) {
              int index = 0;  // index of the whole array 
           
              Interval common = new Interval(schedule.get(0).get(listIndex[0]).start, schedule.get(0).get(listIndex[0]).end);
              for (int i = 1; i < schedule.size(); i ++) {
                  // 两两相比找intersect 
                  Interval cur = schedule.get(i).get(listIndex[i]);
                  common.start = Math.max(common.start, cur.start);
                  common.end = Math.min(common.end, cur.end);
                  // 更新smallestEndTime在哪个list
                  if (cur.end <  schedule.get(index).get(listIndex[index]).end) {
                      index = i;
                  }
              }
              // 判断是否找到common
              if (common.start <= common.end) {
                  res.add(new Interval(common.start, common.end));
              }
              // 以及更新index 
              List<Interval> toUpdate = schedule.get(index);
              if (toUpdate.size() <= listIndex[index] + 1)  {
                  break;
              }
              else {
                  listIndex[index] = listIndex[index] + 1; 
              }
               System.out.println("index " + index + "inner " + listIndex[index]);
          }
          return res; 
      }
    
    
    public static void main(String[] args) {
        List<double[]> input = Arrays.asList(new double[]{1, 0.8},  new double[]{2, 0.4}, new double[]{3, 0.9}, new double[]{4, 0.3}, new double[]{6, 1}, new double[]{9, 1});
        findIntervals(input, 0.8);
    }
} 
