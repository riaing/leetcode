1. 写一个function 转换 n 成 string
// n  - f(n)
// 0  - ""
// 1  - "0"
// 2  - "1"
// 3  - "00"
// 4  - "01"
// 5  - "10"
// 6  - "11"
// 7  - "000"

followup: 如果不是2进位

思路：1，先确定位数： 。。。算了这题不知道怎么形容，反正就是7-2-2^2中求得result长度为3； 
2，把 （7-2-w^2）这个结果-1，再去换成2进制，得0
3，因为最终结果为3，上一步算得就结果为0，就在0的前面补两位0

什么奇葩题目

public class 十进制转换二进制 {

  // 3, 写一个新的fun，给2步返回的结果前面补0 
  
  // 2, 把上一步返回的（input-1）换成2进制
  public static String solution(int input) {
    String s = "";
    while (input != 0) {
      s = (input % 2) + s;
      input = input / 2;
    }
    return s;
  }

  // 1，找到string 长度
  public static int findK(int input, int base) { //例子中base是2
    int product = base;
    int numBits = 1;
    while (input > product) {
      input -= product;
      product *= base;
      numBits++;
    } // return input as n-m-m^2-m^3这种
    System.out.println("Reminder: " + (input - 1)); //这里应该同时返回input-1，
    return numBits;
  }
  public static void main(String[] args) {
    System.out.println(solution(6));
    System.out.println(findK(2, 2));
    System.out.println(findK(3, 2));
    System.out.println(findK(6, 2));
    System.out.println(findK(7, 2));
  }
}
