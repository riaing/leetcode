
https://leetcode.com/playground/HiiLTtcw 

/*
https://leetcode.com/discuss/interview-question/345744 

Given a list of k sorted iterators. Implement MergingIterator to merge them. If you are not familiar with Iterators check similar questions.

class MergingIterator implements Iterator<Integer> {
	public MergingIterator(List<Iterator<Integer>> iterators) {
	}

	public boolean hasNext() {
	}

	public Integer next() {
	}
}
Example:

MergingIterator itr = new MergingIterator([[2, 5, 9], [], [4, 10]]);
itr.hasNext(); // true
itr.next(); // 2
itr.next(); // 4
itr.next(); // 5
itr.next(); // 9
itr.next(); // 10
itr.hasNext(); // false
itr.next(); // error

思路：还是heap merge，需要记每个element对应的element： Map<Interator<Integer>, Integer> : iterator ->拿出来的element  
空间高点：O(n) n - iterator list
to improve: implment peeking iterator: https://leetcode.com/discuss/interview-question/345744: 加iterator.peek()  

*/

    class MergingIterator implements Iterator<Integer> {
        Map<Iterator<Integer>, Integer> map;
        PriorityQueue<Iterator<Integer>> pq; 
        
        
        /**
         * O(size * log(size)) where size is Length of iterators list
         */
        public MergingIterator(List<Iterator<Integer>> iterators) {
            Comparator<Iterator<Integer>> com = new Comparator<>() {
                @Override 
                public int compare(Iterator<Integer> a, Iterator<Integer> b) {
                   return map.get(a) - map.get(b); 
                }
            };
                
            this.pq = new PriorityQueue<>(com);
            
            this.map = new HashMap<>();
            for (Iterator<Integer> cur : iterators) {
                if (cur.hasNext()) {
                    map.put(cur, cur.next());
                    pq.offer(cur); // 加入第一个元素
                }
            }
        }

        /**
         * O(1) where size is Length of iterators list
         */
        public boolean hasNext() {
            return !pq.isEmpty(); 
        }
        
        /**
         * O(log(size))
         */
        public Integer next() {
            if (pq.isEmpty()) {
                throw new NoSuchElementException("Iterator is empty");
            }
            
            // 1. get from q 
            Iterator<Integer> cur = pq.poll(); 
            int val = map.get(cur);
            // 2. if has more, add its next 
            if (cur.hasNext()) {
                map.put(cur, cur.next()); // 顺序不能错
                pq.offer(cur); 
            }
            System.out.println(val); 
            return val; 
        }
    }

public class Main {
    public static void main(String[] args) {

        List<Iterator<Integer>> input = Arrays.asList(Arrays.asList(2,5,9).iterator(), Arrays.asList(4,10).iterator());
         MergingIterator itr = new MergingIterator(input);
        // MergingIterator itr = new MergingIterator([[2, 5, 9], [], [4, 10]]);
         System.out.println(itr.hasNext()); // true
        itr.next(); // 2
        itr.next(); // 4
        itr.next(); // 5
        itr.next(); // 9
        itr.next(); // 10
         System.out.println(itr.hasNext()); // false
        itr.next(); // error
    }
}
