Your car starts at position 0 and speed +1 on an infinite number line. Your car can go into negative positions. Your car drives automatically according to a sequence of instructions 'A' (accelerate) and 'R' (reverse):

When you get an instruction 'A', your car does the following:
position += speed
speed *= 2
When you get an instruction 'R', your car does the following:
If your speed is positive then speed = -1
otherwise speed = 1
Your position stays the same.
For example, after commands "AAR", your car goes to positions 0 --> 1 --> 3 --> 3, and your speed goes to 1 --> 2 --> 4 --> -1.

Given a target position target, return the length of the shortest sequence of instructions to get there.

 

Example 1:

Input: target = 3
Output: 2
Explanation: 
The shortest instruction sequence is "AA".
Your position goes from 0 --> 1 --> 3.
Example 2:

Input: target = 6
Output: 5
Explanation: 
The shortest instruction sequence is "AAARA".
Your position goes from 0 --> 1 --> 3 --> 7 --> 7 --> 6.
 

Constraints:

1 <= target <= 104

----------------------------- BFS + pruning -----
/*
BFS, Node(position, speed)， visited set 
重点：限制范围否则会无穷大无穷小
- R的话每次都加进queue
- A的话只有在 -target， 2*target之间才进queue

Time：O（2^D) D-所需要的步数
*/
class Solution {

/**
* For this problem we can use BFS, to find the shortest path.
* Since we are trying to reach a target and our best way to get
* there is by accelerating we must try to optimize the number of 
* accelerations. Imagine the number line is a road and our car can
* go further each time it can succesively accelerate. By doing this
* we can reach close to our target with the least number of instructions.
* However it is very common to overshoot. WE can use BFS as we are trying to
* find the shortest possible route. Therefore by searching the shortest routes
* at O(nlogn) we can find a target. However if our car has passed the target
* we need to think of alternate stratergy of reversing to our target as well
* as going forward from the previous location. We also need to reverse if we are 
* going further away from the target. 
**/
public int racecar(int target) {     
    if (target == 0) {
        return 0;
    }
    Queue<RaceCarData> queue = new LinkedList<RaceCarData>();
    Set<RaceCarData> visitedPositions = new HashSet<RaceCarData>();
    // // If we are reaching a target that is negative we can simply add reverse 不需要这一步，就算target是负后面的code也行
    // // and change the target to a positive value and apply the same logic
    // if (target < 1) {
    //     RaceCarData rev = new RaceCarData(0, -1, 1);
    //     queue.add(rev);             
    //     target = target*-1;
    // } else {
    //     RaceCarData start = new RaceCarData(0, 1, 0); 
    //     queue.add(start);             
    // }
    
    
     RaceCarData start = new RaceCarData(0, 1); 
     queue.add(start);
     visitedPositions.add(start);
    int steps = 0; 
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            RaceCarData currData = queue.poll();
            // If we reach our target
            if (currData.position==target) {
                // return currData.moves;
                return steps;
            } 
            // A, 有限制的加
            int newPos = currData.position + currData.speed;
            int newSpeed = currData.speed *2;
            RaceCarData next = new RaceCarData(newPos, newSpeed);
            if (!visitedPositions.contains(next) && newPos <= target*2 && newPos > -target) { // ！重点 从2T走回T的步数= 0到T的步数，所以最优解肯定不会是2T。同理：0到-T的步数等于-T到0的步数，肯定不会到-T 
                queue.add(next);
                visitedPositions.add(next);
            } 
            // R，任何时候都加 
            newSpeed = currData.speed > 0 ? -1 : 1;
            next = new RaceCarData(currData.position, newSpeed);
            if (!visitedPositions.contains(next)) {
                queue.add(next);
                visitedPositions.add(next);
            }
        }
        steps++;
    }
    // If no moves are necessary
    return 0;        
}    

private class RaceCarData {
    int position;
    int speed;
    
    public RaceCarData(int pos, int spd) {
        this.position = pos;
        this.speed = spd;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RaceCarData)) {
            return false;
        }
        RaceCarData rc2  = (RaceCarData) o;
        return rc2.position==position&&rc2.speed==speed;
    }
    
    @Override
    public int hashCode() {
        return 1;
    }
    
    @Override
    public String toString() {
        return "Race car data: {position:"+position+" ,speed:"+speed+"}";
    }
}
}
