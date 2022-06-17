Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane, return the maximum number of points that lie on the same straight line.

 

Example 1:


Input: points = [[1,1],[2,2],[3,3]]
Output: 3
Example 2:


Input: points = [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
Output: 4
 

Constraints:

1 <= points.length <= 300
points[i].length == 2
-104 <= xi, yi <= 104
All the points are unique.

------------------------------------------

/*
Ref： https://www.youtube.com/watch?v=7FPL7nAi9aM 
Time :O (n^2)
space: O(n) map to store lines 

0. 判断第三个点在一条线上：假设两个点的坐标分别为[x1, y1], [x2, y2], 则斜率计算公式应为：(y2 - y1) / (x2 - x1）
    - 垂直线和水平线
    - 怎么描述不同的垂直线？
1. 有重叠的点
2. 用分数来表示斜率以免double不准确。但是要约分：1/3 = 3/9. 考点：找最大公约数

算法：
枚举每一个点，找比他大的点的斜率。用map来统计每个斜率出现多少次。斜率出现最多的就是经过当前点的直线的最优解。
 - 加上重复的点
对每个点有个局部最优解，枚举完后能找到全局解

sudo：
for pi:
    充值count[]
   for p j (j > i) :
    if pi == pj; dup ++
    else : count(slope(pi, pj))++
answer = max(answer, max{count} + dup) 


*/
class Solution {
    public int maxPoints(int[][] points) {
        int max = 0;
        for (int i = 0; i < points.length; i++) {
            // 找从这个点往后的直线，返回直线上点最多的就是local max
            Map<String, Integer> slopeCount = new HashMap<>(); // key: 1/3 约分过后的斜率， value：这条斜率上的点的个数
            int localMax = 0;
            int dup = 1; // localMax 要算上dup的点
            for (int j = i+1; j < points.length; j++) {
                int[] a = points[i];
                int[] b = points[j]; 
                // 1. 检查dup
                if (a[0] == b[0] && a[1] == b[1]) {
                    dup++;
                    continue;
                }
                // 2. 查slope看在哪一条线上
                String slope = calSlope(a, b); 
                slopeCount.put(slope, slopeCount.getOrDefault(slope, 0) + 1);
                localMax = Math.max(localMax, slopeCount.get(slope));
            }
            // 找到了经过这个点的所有线，+dup算localMax，更新globalMax
            localMax += dup;
            max = Math.max(max, localMax);
        }
        return max; 
    }
    
    // (y2 - y1) / (x2 - x1）
    private String calSlope(int[] a, int[] b) {
        int numerator = b[1] - a[1];
        int denominator = b[0] - a[0];
        // 水平线 (1, 0) (2, 0). 返回一个特定值
        if (numerator == 0) {
            return "horizontal";
        }
        else if (denominator == 0) {
            return "vertical";
        }
        int d = gcd(numerator, denominator);
        return (numerator / d) + "/" + (denominator / d);
        
    }
    
    // 求最大公约数
    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a%b);
    }
}
