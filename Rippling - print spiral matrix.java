give last 边长，print spiral matrix

eg: N = 21 

[x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x,  ]
[ ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , x,  ]
[ ,  , x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x,  , x,  ]
[ ,  , x,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , x,  , x,  ]
[ ,  , x,  , x, x, x, x, x, x, x, x, x, x, x, x, x,  , x,  , x,  ]
[ ,  , x,  , x,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , x,  , x,  , x,  ]
[ ,  , x,  , x,  , x, x, x, x, x, x, x, x, x,  , x,  , x,  , x,  ]
[ ,  , x,  , x,  , x,  ,  ,  ,  ,  ,  ,  , x,  , x,  , x,  , x,  ]
[ ,  , x,  , x,  , x,  , x, x, x, x, x,  , x,  , x,  , x,  , x,  ]
[ ,  , x,  , x,  , x,  , x,  ,  ,  , x,  , x,  , x,  , x,  , x,  ]
[ ,  , x,  , x,  , x,  , x,  , x,  , x,  , x,  , x,  , x,  , x,  ]
[ ,  , x,  , x,  , x,  , x,  , x, x, x,  , x,  , x,  , x,  , x,  ]
[ ,  , x,  , x,  , x,  , x,  ,  ,  ,  ,  , x,  , x,  , x,  , x,  ]
[ ,  , x,  , x,  , x,  , x, x, x, x, x, x, x,  , x,  , x,  , x,  ]
[ ,  , x,  , x,  , x,  ,  ,  ,  ,  ,  ,  ,  ,  , x,  , x,  , x,  ]
[ ,  , x,  , x,  , x, x, x, x, x, x, x, x, x, x, x,  , x,  , x,  ]
[ ,  , x,  , x,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , x,  , x,  ]
[ ,  , x,  , x, x, x, x, x, x, x, x, x, x, x, x, x, x, x,  , x,  ]
[ ,  , x,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  , x,  ]
[ ,  , x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x, x,  ]
[ ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ]
[ ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ,  ]

----------------------------------------------------------- 
public class Main {
    public static void main(String[] args) {
        
        
        printSpiral(21);
    }
    
    public static void printSpiral(int n) {
        char[][] matrix = new char[n+1][n+1]; 
        Arrays.stream(matrix).forEach(o ->Arrays.fill(o, '-'));
       
        int r = n/2;
        int c = n/2;
        matrix[r][c] = 'x';
        int[] row = {1, 0, -1,  0};
        int[] col = {0, 1, 0, -1};
        int step = 1;
        while (step < n) {
            for (int i = 0; i < row.length; i++) {
                for (int k = 0; k < step; k++) {

                    int newRow = r + row[i]; 
                    // System.out.println("newRow " + r + " " + row[i]);
                    int newC = c + col[i];
                    // System.out.println(r + " " + c + " location " + newRow + " " + newC);
                    matrix[newRow][newC] = 'x';
                    r = newRow;
                    c = newC; 
                }
                System.out.println(step);
                step++;
            }
             if (step == n) {
                    // print
                    Arrays.stream(matrix).forEach(o -> System.out.println(Arrays.toString(o)));
                    return; 
                }
        }
        
    }
}
