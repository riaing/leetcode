/*
1. 给定一个m，n，打印一个m*n的矩阵。
m=4，n=7, 画出：
.......
.......
.......
.......


2. 给定一些方块（每个方块有名字，起始位置和大小），要求在矩阵里画出来, 后面加的方块可能覆盖前面的。
起始矩阵大小m=4, n=7
add_rect('a', 0, 0, 2, 2)
add_rect('b', 1, 1, 1, 2)
画出
aa . . . . .
abb. . . .
. . . . . . .
. . . . . . .

3. 移动方块到新的位置，如果之前覆盖了方块，挪开以后还得显示。
move_rect('b', 2，0）
aa . . . . .
aa . . . . .
bb. . . . .
. . . . . . .


4. 挪动方块后如果方块的位置超过了一开始矩阵的外延，打印以后整个矩阵要相应变大。
- Sparce matrix problem 
俩var：min， max。每次画或者移动时更新这两值。最后按这两值loop画 
*/

class RecArea {
    int startRow;
    int startCol;
    int width;
    int length; 
    public RecArea(int startRow, int startCol, int width, int length) {
        this.startRow = startRow; 
        this.startCol = startCol;
        this.width = width;
        this.length = length; 
    }
    
}

public class Main {
    static Map<Character, RecArea> letter2location = new HashMap<Character, RecArea>(); // letter -> 左上角坐标，宽，长。 
    static Map<String, Deque<Character>> location2letter = new HashMap<>(); // pixel -> 放的字母
    static char[][] canvas = new char[3][3];
    
    // 第三问：如果是只能挪最上面那层，每个pixcel上用个stack(Map{location, letter})。Map 记录每个方块的位子。移动时先pop老位子，再在新位置push
    public static char[][] moveSquare(int[] squareToMove) {
        char letter = (char) (squareToMove[0] + '0');
        RecArea letterArea = letter2location.get(letter); 
        int[] oldEnd = findRightConer(letterArea.startRow, letterArea.startCol, letterArea.width, letterArea.length);
        // 1. 移掉原本有这个letter的pixel
        wipe(canvas, letter, new int[]{letterArea.startRow, letterArea.startCol}, oldEnd);
        // 2. 画到新的place
        int[] newStart = new int[]{squareToMove[1], squareToMove[2]}; 
        int[] newEnd = findRightConer(newStart[0], newStart[1], letterArea.width, letterArea.length); 
        draw(canvas, letter, newStart, newEnd);
        // 3. 跟新letter2locationMap
        letterArea.startRow = newStart[0];
        letterArea.startCol = newStart[1];
        // 4. 画图
        char[][] res = drawEntire(location2letter);
        System.out.println("Q3 " + Arrays.deepToString(res));    
        return res; 
    }
    
    // 第三问, 从每个pixel上移出那个letter
    private static void wipe(char[][] canvas, char letter, int[] leftConer, int[] rightConer) {
        for (int i = Math.max(0, leftConer[0]); i <= Math.min(rightConer[0], canvas.length-1); i++) {
            for (int j = Math.max(0, leftConer[1]); j <= Math.min(rightConer[1], canvas[0].length-1); j++) {
                canvas[i][j] = '.';
                location2letter.get(i+""+j).pop();
            }
        }
    }
    // 画整个canvas
    private static char[][] drawEntire(Map<String, Deque<Character>> location2letter) {
        char[][] newCanvas = new char[canvas.length][canvas[0].length];
        for (int i = 0; i < canvas.length; i++) {
            for (int j = 0; j < canvas[0].length; j++) {
                if (location2letter.containsKey(i + "" + j) && !location2letter.get(i + "" + j).isEmpty()) {
                    newCanvas[i][j] = location2letter.get(i + "" + j).peek();
                }
                else {
                    newCanvas[i][j] = '.';
                }
            }
        }
        return newCanvas; 
    }
    
    // 第二问： 重点是算出左上和右下坐标 
    public static char[][] drawSquare(List<int[]> square) {
        // char[][] canvas = new char[m][n];
        for (char[] row : canvas) {
            Arrays.fill(row, '.');
        }
        
        for (int[] s : square) {
            int[] start = {s[1], s[2]};
            // int[] end = {s[1] + s[3] -1, s[2] + s[4] - 1};
            int[] end = findRightConer(s[1], s[2], s[3], s[4]);
            char letter = (char) (s[0] + '0');
            draw(canvas, letter, start, end);
            // for 第三问 更新letterMap
            letter2location.put(letter, new RecArea(s[1], s[2], s[3], s[4])); 
        }
        
        System.out.println("Q2 " + Arrays.deepToString(canvas));
        return canvas; 
    }
    
    // 画出specific 图在canvas上
    private static void draw(char[][] canvas, char letter, int[] leftConer, int[] rightConer) {
        for (int i = Math.max(0, leftConer[0]); i <= Math.min(rightConer[0], canvas.length-1); i++) {
            for (int j = Math.max(0, leftConer[1]); j <= Math.min(rightConer[1], canvas[0].length-1); j++) {
                canvas[i][j] = letter;
                // 第三问, 更新locationMap
                location2letter.putIfAbsent(i +"" + j, new LinkedList<Character>());
                location2letter.get(i+""+j).push(letter);
            }
        }
    }
    
    // 找到右下角坐标
    private static int[] findRightConer(int startR, int startC, int width, int length) {
        return new int[]{startR + width - 1, startC + length - 1}; 
    }
    
    public static void main(String[] args) {
        drawSquare( Arrays.asList(new int[]{1,0,0,2,2}, new int[]{2,1,1,1,2}));
        moveSquare(new int[]{2, 2, 0});  
    }
}
