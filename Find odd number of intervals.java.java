/*
Verkada 考题

Same exact input as segment joiner except for now you want to find the segments with an odd number of active segments. A helpful way to think about this is to think of the start index as when someone is born and the end index as when someone dies. You want to output the intervals where an odd number of people are alive

Sampl‍‌‍‍‌‍‍‌‍‍‌‍‌‍‍‌‌‍e Input:`[(1,7), (3,5), (4,9)]` should yield `[(1,3), (4,5), (7,9)]`

解法：求出各个时间段interval的个数，返回个数为odd的

- 当成是meeting room来想，求哪些时间段需要odd number of rooms。
- 将开始时间段当成左括号，结束时间当成右括号。横扫，当括号数为偶数时，说明之前时间有奇数个，找到一个解

*/

class Pair {
    int time;
    char type;
    public Pair(int time, char type) {
        this.time = time;
        this.type = type;
    }
}
public class Main {
    
    public static List<int[]> findOddNum(List<int[]> input) {
        List<Pair> trans = new ArrayList<Pair>();
        for (int[] c : input) {
            trans.add(new Pair(c[0], '('));
            trans.add(new Pair(c[1], ')'));
        }
        Collections.sort(trans, (a,b) -> a.time - b.time); // 按时间点来sort
        List<int[]> res = new ArrayList<>();
        int cnt = 0; 
        for (int i = 0; i < trans.size(); i++) {
            Pair cur = trans.get(i); 
            if (cur.type == '(') {
                cnt++;
            }
            else if (cur.type == ')') {
                cnt--;
            }
            // 说明到此刻为止，前段时间有odd个interval数量
            if (cnt % 2 == 0) {
                if (trans.get(i-1).time == cur.time) { // 说明长度为0。两个时间相同的同时加入或退出： [4,5], [4,5]
                    continue; 
                }
                int[] findOne = new int[]{trans.get(i-1).time, cur.time};
                res.add(findOne);
            }
        }
        res.forEach(o -> System.out.println(Arrays.toString(o)));
        return res; 
    }
    
    public static void main(String[] args) {
        // List<int[]> input = Arrays.asList(new int[]{1,7}, new int[]{3,5}, new int[]{4,9}, new int[]{4,5}); // 1,3 ; 7,9 => 测试同时加入
        // List<int[]> input = Arrays.asList(new int[]{1,7}, new int[]{3,5}, new int[]{4,9}); //  1,3 ; 4,5 ; 7,9 
        List<int[]> input = Arrays.asList(new int[]{1,7}, new int[]{1,7}, new int[]{8,9});  // 同时加入同时退出
        // List<int[]> input = Arrays.asList(new int[]{1,2}, new int[]{2,3}); // 1,2 ; 2,3 
        findOddNum(input);
    }
}
