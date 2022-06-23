Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an integer k, return the k closest points to the origin (0, 0).

The distance between two points on the X-Y plane is the Euclidean distance (i.e., √(x1 - x2)2 + (y1 - y2)2).

You may return the answer in any order. The answer is guaranteed to be unique (except for the order that it is in).

 

Example 1:


Input: points = [[1,3],[-2,2]], k = 1
Output: [[-2,2]]
Explanation:
The distance between (1, 3) and the origin is sqrt(10).
The distance between (-2, 2) and the origin is sqrt(8).
Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
We only want the closest k = 1 points from the origin, so the answer is just [[-2,2]].
Example 2:

Input: points = [[3,3],[5,-1],[-2,4]], k = 2
Output: [[3,3],[-2,4]]
Explanation: The answer [[-2,4],[3,3]] would also be accepted.


--------------------- max heap ---------------------------
/*
max heap: time o(nlgk), space o(k)
*/
class Solution {
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> q = new PriorityQueue<int[]>((a, b) -> Double.compare(calculate(b), calculate(a))); 
        for (int[] p : points) {
            // double dis = calculate(p);
            q.offer(p);
            if (q.size() > k) {
                q.poll();
            }
        }
        int[][] res = new int[q.size()][2];
        int size = q.size();
        for (int i = 0; i < size; i++) {
            res[i] = q.poll();
        }
        return res; 
    }
    
    private double calculate(int[] point) {
        return Math.sqrt(point[0]* point[0] + point[1] * point[1]); 
    }
} 

------------------ binary search ** -----------------------------------------
  /*
binary search on distance. 找个mid distance，把array分成两半。如果小的那半的个数大于k，说明要找的在小的那半中，因此可以丢到大的那半。反之，小的那半全算进结果，再binary search大的那半。
通过不断丢掉一半的array和更新k，来找到最终结果

time o(n), 切分时要iterate whole cur list，  第一遍O(n)， 第二遍O（n/2), n/4, ... n/n = 2n 
space o(n)： required for the arrays containing distances and reference indices. 
*/

class Node {
    double distance;
    int index;
    
    public Node(double distance, int index) {
        this.distance = distance;
        this.index = index; 
    }
}

class Solution {
    public int[][] kClosest(int[][] points, int k) {
        List<Node> distance = new ArrayList<>();
        double hi = -1;
        double lo = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            double dis = calculate(points[i]);
            hi = Math.max(hi, dis);
            lo = Math.min(lo, dis);
            distance.add(new Node(dis, i));
        }
        
        List<Node> res = new ArrayList<>();
        // 
        while (k > 0) {
            double mid = (hi + lo) / 2; 
            List<List<Node>> divide = split(distance, mid);
            List<Node> smaller = divide.get(0);
            List<Node> larger = divide.get(1);
            if (smaller.size() > k) {
                hi = mid;
                distance = smaller; 
            }
            else { // mid往左的全要
                res.addAll(smaller);
                k -= smaller.size();
                lo = mid; 
                distance = larger; 
            }
        }
        
        int[][] output = new int[res.size()][2];
        for (int i = 0; i < res.size(); i++) {
            output[i] = points[res.get(i).index];
        }
        return output; 
    }
    
    private List<List<Node>> split(List<Node> distance, double k) {
        List<Node> larger = new ArrayList<Node>();
        List<Node> smaller = new ArrayList<Node>();
        for (Node n : distance) {
            if (n.distance <= k) {
                smaller.add(n);
            }
            else {
                larger.add(n);
            }
        }
        List<List<Node>> res = new ArrayList<>();
        res.add(smaller);
        res.add(larger);
        return res; 
    }    
    
    private double calculate(int[] point) {
        return Math.sqrt(point[0]* point[0] + point[1] * point[1]); 
    }
}

--------------- quick select ------------------------------
 /*
标准quick select模板

time o(n), 切分时要iterate whole cur list，  第一遍O(n)， 第二遍O（n/2), n/4, ... n/n = 2n 

space o(1)：
*/

class Solution {
    private double calculate(int[] point) {
        return Math.sqrt(point[0]* point[0] + point[1] * point[1]); 
    }
    
    public int[][] kClosest(int[][] points, int k) {
        int lo = 0;
        int hi = points.length - 1;
        k = k -1; // k要变成index base
        while (lo <= hi) {
            int p = partition(points, lo, hi);
            if (p == k) {
                return Arrays.copyOfRange(points, 0, p+1);
            }
            else if (p < k) {
                lo = p + 1;
            }
            else {
                hi = p - 1;
            }
        }
        return points; 
    }
    
    // quick select 代码
    private int partition(int[][] points, int lo, int hi) {
        int i = lo;
        int j = hi;
        double p = calculate(points[lo]);
        while (i <= j) {
            // 移动i
            while (i < hi && calculate(points[i]) <= p) {
                i++;
            }
            while (j > lo && calculate(points[j]) > p) {
                j--;
            }
            if (i >= j) {
                break;
            } 
            swap(points, i, j); 
        }
        swap(points, lo, j);
        return j;
    }
    
    private void swap(int[][] points, int a, int b) {
        int[] tmp = points[a];
        points[a] = points[b];
        points[b] = tmp;
    }

}
