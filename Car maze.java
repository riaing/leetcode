/*

4min - Q
24min - start

Given a maze with R rows and C columns. Each cell is either a wall or a road cell. Given two cars with their start and goal. The cars will take turns to move and the goal is to let both cars arrive at their goals. In each turn a car can:
1. Move in 4 directions for 1 step if the next step is not a wall nor another car
2. Don't move. Skip this turn.

Given a maze and the starts and goals of the two cars. Is it possible to let the cars arrive at their goals? (note that even though a car might reach it's goal, it can still move.)

# indicates a wall
. indicates a road cell
a indicates the starting cell of the first car
A indicates the goal of the first car
b indicates the starting cell of the second car
B indicates the goal of the second car

Input 1
R=3, C=3
a.A
###
b.B

Output 1
True

Input 2
R=1,C=4
aBAb

Counter example of why using two boolean to record both car arrive doesn't work 
car 1 move right: .aAb
car 2 skip: .aAb
car 1 move right: ..ab
car 2 skip:
car 1 move left: .aAb
car 2 move left: .ab.
car 1 move left: aBb.
car 2 move left: abA.

Output 2
False

Input 3
R=3,C=4
###.
BaAb
###.

Output 3
True

first (x,y)
second (a,b)

(x+1,y)
(a,b)

(x,y)
(a+1,b)

visited: "x+y+a+b+true"
*/

class State {
    boolean car1Turn; 
    int[] car1Loc;
    int[] car2Loc;
}


/*
algo 
BFS
1. initial : 
2. termination : both at the location 

Time: think about visited set 
each car can visit all cells: mn 
mn*mn*2

*/
public class XXX {
    public xxx
    
    
}

public boolean canArrive(int[][] matrix, int[] car1Start, int[] car2Start, int[] car1Dest, int[] car2Dest) {
        Set<String> visitedLoc = new HashSet<>(); // 1 & 2's location 
        Queue<State> q = new LinkedList<>();
        // initalization 
        q.offer(new State(true, car1Start, car2Start);
        
        while(q.size() != 0) {
            State cur = q.poll();
            int[] car1Loc = cur.car1Loc;
            int[] car2Loc = cur.car2Loc;
            // check 
            if (car1Loc[0] == car1Dest[0] && car1Loc[1] == car1Dest[1] && car2Loc[0] == car2Dest[0] && car2Loc[1] == car2Dest[1]) {
                return true;
            }
            int[] old = cur.car1Turn ? car1Loc : car2Loc;
            int[] anotherCarLoc = cur.car1Turn ? car2Loc : car1Loc;
            
            // next state 
            int[] row = {1,-1, 0 ,0, 0}; //
            int[] col = {0, 0, 1, -1, 0}; // !! [0,0] means this round the car doesn't move(skip)
            for (int k = 0; k < 4; k++) {
                int[] newLoc = {old[0] + row[k], old[1] + col[k]}; 
                String potentialVisited = newLoc[0] +  "+" + newLoc[1] + "+" + anotherCarLoc[0] + "+" + anotherCarLoc[1] + "+" + !cur.car1Turn; 
                if (newLoc[0] >= 0 && newLoc[0] < matrix.length && newLoc[1] >= 0 && newLoc[1] < matrix[0].length && matrix[newloc[0]][newLoc[1]] != '#' && (newLoc[0] != anotherCarLoc[0] || newLoc[1] != anotherCarLoc[1]) && !visitedLoc.contains(potentialVisited)) {
                    visitedLoc.add(potentialVisited);
                    q.offer(new State(!cur.car1Turn, newLoc, anotherCarLoc);
                }
            }
        }
        return false; 
}


 
