The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value and the median is the mean of the two middle values.

For example, for arr = [2,3,4], the median is 3.
For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
Implement the MedianFinder class:

MedianFinder() initializes the MedianFinder object.
void addNum(int num) adds the integer num from the data stream to the data structure.
double findMedian() returns the median of all elements so far. Answers within 10-5 of the actual answer will be accepted.
 

Example 1:

Input
["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
[[], [1], [2], [], [3], []]
Output
[null, null, null, 1.5, null, 2.0]

Explanation
MedianFinder medianFinder = new MedianFinder();
medianFinder.addNum(1);    // arr = [1]
medianFinder.addNum(2);    // arr = [1, 2]
medianFinder.findMedian(); // return 1.5 (i.e., (1 + 2) / 2)
medianFinder.addNum(3);    // arr[1, 2, 3]
medianFinder.findMedian(); // return 2.0
 

Constraints:

-105 <= num <= 105
There will be at least one element in the data structure before calling findMedian.
At most 5 * 104 calls will be made to addNum and findMedian.
 

Follow up:

If all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?
If 99% of all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?

----------------------- 2 heap --------------------------------------------------
/*
用 heap 空间是 O（n)
Time： (logN), worst case happens in balance heap 

如果用 insertion sort
1. 如果是 array，那么 addNum 可以用 binary search 找 insert 的地方，insert 是 lgn。但 insert 后，后段 array 需要往后挪一格，最坏是 O（n）。所以整体是 O（lgn + n) 
find median 就是 O（1）
2. 如果是 linkedlist，那么add num找中点只能是 O（n), 然后 insert 这个 action 是 O（1), 所以整体是 O（n）
find median可以做到O(1）：用一指针指向 median node，每次add num 时调整指针。那么 find median 就是 (cur + cur.next)/2 
空间是 O（n）

follow up
1. If all integer numbers from the stream are in the range [0, 100], how would you optimize your solution? 
- 可以用 count sort，建立 0.。 100的 bucket（可以就是个 array），存每个 number 的数量。那么 add是 o(1), findMedium就最多是 O（100），找第 medium 个数在哪个 bucket 即可
空间就是 O（100）的 array

2. If 99% of all integer numbers from the stream are in the range [0, 100], how would you optimize your solution?
也可以用 bucket sort。就多两个 bucket：<0 的和>100的。坏情况是最开始 add 的 num 如果是在 range 外的，需要先把<0和>100这两块数存起来并 sort。当数量足够大时，比如 total num 已经超过100了，那就可以不用存数了，median 大概率在1-100间
这种 solution 不 work 那1%的情况

*/
class MedianFinder {
    PriorityQueue<Integer> firstPart; 
    PriorityQueue<Integer> secondPart; 

    public MedianFinder() {
        Comparator<Integer> comparator = new Comparator<Integer>(){
            @Override 
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        };
        this.firstPart = new PriorityQueue<Integer>(comparator);; // store the first part of the list sorted by max 
        this.secondPart = new PriorityQueue<Integer>(); // store the second part of the list sorted by min 
    }
    
    public void addNum(int num) { //O(lgn/2) = O(lgn)
        if (firstPart.size() == 0) { // 处理最开始的一个 num
            firstPart.offer(num);
        }
        else {
            if (num <= firstPart.peek()) { // 如果num 小于第一段的最大数，肯定就放第一段中
                firstPart.offer(num);
            }
            else {
                secondPart.offer(num);
            }
        }
        // 调整，使 first 总是最多多一个
        while (firstPart.size() - secondPart.size() > 1) {
            int curNum = firstPart.poll();
            secondPart.offer(curNum);
        }
        while (secondPart.size() - firstPart.size() > 0) {
            int curNum = secondPart.poll();
            firstPart.offer(curNum);
        }
        return;
    }
    
    public double findMedian() { // o(1) 
        if (secondPart.size() == firstPart.size()) {
            int m1 = secondPart.peek();
            int m2 = firstPart.peek();
            return (m1 + m2) * 1.0 / 2; 
        }
        return firstPart.peek();
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
