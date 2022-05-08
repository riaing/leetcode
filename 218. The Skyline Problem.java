A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance. Given the locations and heights of all the buildings, return the skyline formed by these buildings collectively.

The geometric information of each building is given in the array buildings where buildings[i] = [lefti, righti, heighti]:

lefti is the x coordinate of the left edge of the ith building.
righti is the x coordinate of the right edge of the ith building.
heighti is the height of the ith building.
You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.

The skyline should be represented as a list of "key points" sorted by their x-coordinate in the form [[x1,y1],[x2,y2],...]. Each key point is the left endpoint of some horizontal segment in the skyline except the last point in the list, which always has a y-coordinate 0 and is used to mark the skyline's termination where the rightmost building ends. Any ground between the leftmost and rightmost buildings should be part of the skyline's contour.

Note: There must be no consecutive horizontal lines of equal height in the output skyline. For instance, [...,[2 3],[4 5],[7 5],[11 5],[12 7],...] is not acceptable; the three lines of height 5 should be merged into one in the final output as such: [...,[2 3],[4 5],[12 7],...]

 

Example 1:


Input: buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
Output: [[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
Explanation:
Figure A shows the buildings of the input.
Figure B shows the skyline formed by those buildings. The red points in figure B represent the key points in the output list.
Example 2:

Input: buildings = [[0,2,3],[2,5,3]]
Output: [[0,3],[5,0]]
 

Constraints:

1 <= buildings.length <= 104
0 <= lefti < righti <= 231 - 1
1 <= heighti <= 231 - 1
buildings is sorted by lefti in non-decreasing order.
  
  
  ------------- heap + object design ------------------------------
  /* 
Sweep line algorithm 
https://www.youtube.com/watch?v=8Kd-Tn_Rz7s&ab_channel=HuaHua 
*/ 

/*
在每个有building的index上，判断当前楼和此index上最高楼的关系，来决定有没有个point

所以data structure：用一个heap来记录当前的index上的最高building
从左到右扫，如果是enter，和堆顶的height比较，
          - 如果大于堆顶： 加一个point(cur.坐标, cur.height), buiding入栈
          - 如果小于堆顶，入栈
          如果是exit，先移出heap，再和堆顶heigh比。
           - 如果移出的height比堆顶大，则要加个point(cur.坐标，堆顶height)
           - 否则，直接移出

要注意corner case（本题的难点）
        // 1. 同一个坐标的话又有进又有出，先处理进，再处理出
        // 2. 同一个坐标，如果都是移出，先处理高度小的
        // 3. 同一个坐标，如果都是移进，先处理高度大的
        

*/
class Building {
    int id; // index 
    int height;
    
    public Building(int id, int height) {
        this.id = id;
        this.height = height;
    }
}

class Event {
    int xCoordinate;
    int enter; // enter = 1, exit = -1
    Building building; 
    public Event(int xCoordinate, int enter, Building building) {
        this.xCoordinate = xCoordinate;
        this.enter = enter;
        this.building = building; 
    }
}


class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<Event> events = new ArrayList<Event>();
        for (int i = 0; i < buildings.length; i++) {
            int[] b = buildings[i];
            Building curBuilding = new Building(i, b[2]);
            events.add(new Event(b[0], 1, curBuilding));
            events.add(new Event(b[1], -1, curBuilding));
        }
        
        // 1. 同一个坐标的话又有进又有出，先处理进，再处理出
        // 2. 同一个坐标，如果都是移出，先处理高度小的
        // 3. 同一个坐标，如果都是移进，先处理高度大的
        Comparator<Event> comparator = new Comparator<Event>() {
            @Override
            public int compare(Event a, Event b) {
                if (a.xCoordinate == b.xCoordinate) {
                    if (a.enter == 1 && b.enter == 1) { //3 都是进的话，先处理高度大的，descending
                        return b.building.height - a.building.height;
                    }
                    else if (a.enter == -1 && b.enter == -1) { // 2 都是出的话，先处理高度小的，ascending
                        return a.building.height - b.building.height;
                    }
                    else { // 1
                        return b.enter - a.enter;  // 先处理进（1），再处理出（-1），所以要从大到小排列
                    }
                }
                return a.xCoordinate - b.xCoordinate; 
            }
        };
        Collections.sort(events, comparator);
        // events.stream().forEach(e -> System.out.println("index " + e.id + " :x " + e.xCoordinate + " enter? " + e.enter + " height " + e.height));
        
        PriorityQueue<Building> q = new PriorityQueue<Building>((a, b) -> b.height - a.height); // max height heap 
        
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        // 2. 扫每个坐标
        for (Event e : events) {
            if (e.enter == 1) {
                if (q.isEmpty() || e.building.height > q.peek().height) {
                    res.add(Arrays.asList(e.xCoordinate, e.building.height));
                }
                q.offer(e.building); 
            }
            
            if (e.enter == -1) {
                q.remove(e.building); 
                if (q.isEmpty() || e.building.height > q.peek().height) {
                    int height = q.isEmpty() ? 0 : q.peek().height;
                    res.add(Arrays.asList(e.xCoordinate, height));
                }
            }
        }
        
        return res; 
    }
}
