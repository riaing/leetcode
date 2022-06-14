Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.

 

Example 1:

Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]
Example 2:

Input: nums = [1], k = 1
Output: [1]
 

Constraints:

1 <= nums.length <= 105
k is in the range [1, the number of unique elements in the array].
It is guaranteed that the answer is unique.
 

Follow up: Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 
  
  ------------------- heap ----------------------------------------
  class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        // num -> count
        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
        for (int n : nums) {
            count.put(n, count.getOrDefault(n, 0) + 1); 
        }
        // heap of the number which sorted by frequency 
        // 注意这个 comparator 可以不用 queue 里的东西
        PriorityQueue<Integer> q = new PriorityQueue<Integer>((n1, n2) -> count.get(n1) - count.get(n2));
        
        // 维持 k size 的 min heap，当 cur num > heap 头时，拿出并放入 cur num
        for (int num : count.keySet()) { //O(nlgk)
            q.offer(num);
            if (q.size() > k) {
                q.poll(); //拿出来的肯定是最小的。
            }
        }
        
        int[] res = new int[k];
        int i = 0;
        while (q.size() > 0) {
            res[i] = (q.poll());
            i++;
        }
        return res;
    }
}
------------- quick select 方法1：边找边print。不好--------------
 class Node {
    int val;
    int count;
    
    public Node(int val, int count) {
        this.val = val;
        this.count = count; 
    }
}

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        // 1. build 
        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
        for (int n : nums) {
            count.put(n, count.getOrDefault(n, 0) + 1); 
        }
        int minFreq = nums.length;
        int maxFreq = 0;
        LinkedList<Node> input = new LinkedList<Node>();
        for (int num : count.keySet()) {
           input.addLast(new Node(num, count.get(num)));
            minFreq = Math.min(minFreq, count.get(num));
            maxFreq = Math.max(maxFreq, count.get(num));
        }
        
        // 2, quick select 
        int lo = 0;
        int hi = input.size()-1; 
        List<Integer> res = new ArrayList<>();
        while (lo <= hi) {
            int p = partition(input, lo, hi); // 某个frequency的index，左边的frequency都比他小或等
            int largerPartSize = input.size() - p;
            if (largerPartSize <= k) { // 从p到最后都是result。
                for (int i = 0; i < largerPartSize; i++) {
                    Node cur = input.removeLast();
     
                    res.add(cur.val); 
                }
                if (largerPartSize < k) {
                    k = k - largerPartSize;
                    hi = p - 1;
                }
                else {
                    break;
                }  
            }
            else if (largerPartSize > k) {
                lo = p + 1;
            }
        }
        return res.stream().mapToInt(Integer::intValue).toArray();
    }
    
    private int partition(List<Node> input, int lo, int hi) {
        int pivot = input.get(lo).count;
        int i = lo;
        int j = hi;
        while (i <= j) {
            while (i < hi && input.get(i).count <= pivot) {
                i++;
            } // 出来后 n[i] > pivot 
            while (j > lo && input.get(j).count > pivot) {
                j--; 
            }// 出来后n[j] <= pivot 
            if (i >= j) {
                break; 
            } // 扫完整个array都是sorted的，不用swap
            swap(input, i, j);  // swap完后就保证了 nums[lo, i] <= pivot, nums[j, hi] > pivot 
        }
        
        swap(input, lo, j); //要与j swap，因为这时候 i >= j 了 ** 
        
        return j;
    }
    
    private void swap(List<Node> input, int i, int j) {
        Node tmp = input.get(j);
        input.set(j, input.get(i));
        input.set(i, tmp);
    }
}

------------- quick select 干净的模板 ------------------------
 class Node {
    int val;
    int count;
    
    public Node(int val, int count) {
        this.val = val;
        this.count = count; 
    }
}

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        int originalK = k; 
        // 1. build 
        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
        for (int n : nums) {
            count.put(n, count.getOrDefault(n, 0) + 1); 
        }
        int minFreq = nums.length;
        int maxFreq = 0;
        List<Node> input = new ArrayList<Node>();
        for (int num : count.keySet()) {
           input.add(new Node(num, count.get(num)));
            minFreq = Math.min(minFreq, count.get(num));
            maxFreq = Math.max(maxFreq, count.get(num));
        }
        
        // 2, quick select 
        int lo = 0;
        int hi = input.size()-1; 
        int divide = 0; // 从哪切可以拿到top k
        List<Integer> res = new ArrayList<>();
        while (lo <= hi) {
            int p = partition(input, lo, hi); // 某个frequency的index，左边的frequency都比他小或等
            int largerPartSize = hi - p + 1;
            if (largerPartSize == k) {
                divide = p; 
                break;
            }
            else if (largerPartSize < k) { // 从p到最后都是result。
                k = k - largerPartSize;
                hi = p - 1;
            }
            else if (largerPartSize > k) {
                lo = p + 1;
            }
        }
        // System.out.println("divide " + divide);
        // input.forEach(o -> System.out.println("val " + o.val + " freq " + o.count)); 
        int[] output = new int[originalK];
        for (int i = divide; i < input.size(); i++) {
            output[i-divide] = input.get(i).val;
        }
        return output; 
    }
    
    
    private int partition(List<Node> input, int lo, int hi) { // 模板代码
        int pivot = input.get(lo).count;
        int i = lo;
        int j = hi;
        while (i <= j) {
            while (i < hi && input.get(i).count <= pivot) {
                i++;
            } // 出来后 n[i] > pivot 
            while (j > lo && input.get(j).count > pivot) {
                j--; 
            }// 出来后n[j] <= pivot 
            if (i >= j) {
                break; 
            } // 扫完整个array都是sorted的，不用swap
            swap(input, i, j);  // swap完后就保证了 nums[lo, i] <= pivot, nums[j, hi] > pivot 
        }
        
        swap(input, lo, j); //要与j swap，因为这时候 i >= j 了 ** 
        
        return j;
    }
    
    private void swap(List<Node> input, int i, int j) {
        Node tmp = input.get(j);
        input.set(j, input.get(i));
        input.set(i, tmp);
    }
}
