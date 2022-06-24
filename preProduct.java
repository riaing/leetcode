https://leetcode.com/playground/faDLLKgS 


//  实现两个函数： add（）takes a number, get(int k) 算最后k个数字的product
//          eg  1 2 0 3 4  2  : a)算prefixProduct时碰到零重置（为1），记录最后一个零位置。 b) 找k时如果包含零返回零，否则返回prefixProdcut
//preProduct = 1 2 1 3 12 24
// lastZero = 2 
public class Main { 
    static int lastZero = 0; // 最后一个零的index 
    static List<Integer> prefixProduct = new ArrayList<>(); // 从0到i的preProduct，如果前一个是0则重置
    public static void main(String[] args) {
        
        add(1);
        add(2);
        add(0);
        System.out.println("get " + getK(2));
        add(3);
        System.out.println("get " + getK(4));
        add(4);
        add(2);
        System.out.println("get " + getK(5));
        
    }
    
    public static void add(int n) {
        if (n == 0) {
            prefixProduct.add(1); // 碰到零就重置
            lastZero = prefixProduct.size() - 1; 
        }
        else {
            // 如果前一个是0或者list为空，直接加进
            if (prefixProduct.size() == 0) {
                prefixProduct.add(n);
            }
            else {
                int lastProduct =  prefixProduct.get(prefixProduct.size() - 1);
                prefixProduct.add(lastProduct * n);
            }
        }
        System.out.println("in add " + prefixProduct); 
    }
    
    // return the last K item's product 
    public static int getK(int k) {
        int left = prefixProduct.size() - k;  // 找到k的左index，右index就是list尾
        if (left < 0 || left <= lastZero) { // 要求的range包含零
            return 0; 
        }
        else {
            int right = prefixProduct.size() - 1; 
            return prefixProduct.get(right) / prefixProduct.get(left-1);
        }
    }
}
