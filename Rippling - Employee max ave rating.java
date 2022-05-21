根据需求描述，构建Employee的数据结构，有姓名，薪水，Team的组织关系(manager 和 subordinates)，performance rating, etc （主要是构建树形结构和最基本的类结构）

1. 根据Employee的team树形结构，遍历和找到employee whose team has the highest performance rating average（基本的树的DFS）
2. 现在要求invite员工参加会议，有两个要求
  a. sum of ratings of employees invited is maximum       
  b. 其中的任意两个员工不能是直接report的关系。
  
  -------------------- 第一问 ----------------------
  
  /*
 * 
 1. 根据需求描述，构建Employee的数据结构，有姓名，薪水，Team的组织关系(manager 和 subordinates)，performance rating, etc （主要是构建树形结构和最基本的类结构）
2. 根据Employee的team树形结构，遍历和找到employee whose team has the highest performance rating average（基本的树的DFS）
 */

import java.io.*;
import java.util.*;


class Employee {
  String name; 
  int salary; 
  Employee manager;
  List<Employee> subordinates; 
  double rating; 

  public Employee(String name, Employee manager, List<Employee> subordinates, double rating) {
    this.name = name; 
    this.manager = manager;
    this.subordinates = subordinates; 
    this.rating = rating; 
  }
}

class Node {
 Employee employee;
 double totalRating;  // total rating include himself
 int teamSize;  // total team size include himself

 public Node (Employee employee, double totalRating, int teamSize) {
   this.employee = employee;
   this.totalRating = totalRating;
   this.teamSize = teamSize; 
 }
}

class Solution {
  static double maxAveRating = -1.0; 
  static Employee res; 

  public static Employee highestAveRating(Employee root) {
    // // 1. 包括A
    //   traverse(root);
    // 2. 不包括A 
    for (Employee sub : root.subordinates) {
      traverse(sub);
    }
      return res; 
  }

  private static Node traverse(Employee root) {
    if (root == null) {
      return new Node(null, 0.0, 0);
    }
    if (root.subordinates == null) { // 最底层员工
      return new Node(root, root.rating, 1);  
    }

    int totalSize = 1; 
    double totalRating = root.rating;
    for (Employee sub : root.subordinates) {
      Node curSub = traverse(sub); 
      totalSize += curSub.teamSize;
      totalRating += curSub.totalRating;
    }
    // update the maximum 
    System.out.println("totalRating " + totalRating + " name " + root.name);
    if (totalSize != 1 && totalRating / totalSize > maxAveRating) {
      res = root; 
      maxAveRating = totalRating / totalSize; 
    }
    return new Node(root, totalRating, totalSize);
  }


  public static void main(String[] args) {

    Employee A = new Employee("A", null, null, 5.0);
    Employee B = new Employee("B", A, null, 1.0); 
    Employee C = new Employee("C", A, null, 1.0);
    List<Employee> Bs = Arrays.asList(new Employee("D", B, null, 1.0), new Employee("E", B, null, 2.0));
    B.subordinates = Bs; 
    List<Employee> Cs = new ArrayList<>(); 
    Cs.add(new Employee("F", C, null, 1.0));
    C.subordinates = Cs;

    A.subordinates = Arrays.asList(B,C); 


    Employee test1 = highestAveRating(A);
    System.out.println(test1.name);
  }
}

----------------------------- 第二问，参考leetcode 337 -------------------------------
  /**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

/*
衍生：print所有房子
存这层取，or不取的值，以及当前最大值 
取： root.val + 下一层不取
不取(!)：下一层可取可不取，取决于大的值 
*/

class Node {
    TreeNode root; 
    int withNodeSum;
    int withoutNodeSum; 
    
    List<TreeNode> withNodeChain;  // 衍生，print所有值
    List<TreeNode> withoutNodeChain; 
    public Node(TreeNode root, int withNodeSum, int withoutNodeSum, List<TreeNode> withNodeChain, List<TreeNode> withoutNodeChain) {
        this.root = root;
        this.withNodeSum = withNodeSum;
        this.withoutNodeSum = withoutNodeSum;
        this.withNodeChain = withNodeChain;
        this.withoutNodeChain = withoutNodeChain; 
    }
}

class Solution {
    public int rob(TreeNode root) {
        Node res = traverse(root);
        int returnRes = 0; 
        if (res.withNodeSum > res.withoutNodeSum) {
            returnRes = res.withNodeSum;
            res.withNodeChain.forEach(o -> System.out.println("res: " + o.val));
        }
        else {
            returnRes = res.withoutNodeSum;
            res.withoutNodeChain.forEach(o -> System.out.println("res: " + o.val));
        }
        return returnRes; 
    }
    
    private Node traverse(TreeNode root) {
        if (root == null) {
            return new Node(null, 0, 0, new ArrayList<>(), new ArrayList<>());
        }
        if (root.left == null && root.right == null) {
            List<TreeNode> withoutChain = new ArrayList<>();

            List<TreeNode> chain = new ArrayList<>();
            chain.add(root);
            return new Node(root, root.val, 0, chain, withoutChain); 
        }
        Node leftNode = traverse(root.left); 
        Node rightNode = traverse(root.right);
        int includeRoot = root.val + leftNode.withoutNodeSum + rightNode.withoutNodeSum;
        // 注意这里！ 如果这层不偷，那么就取下层的最大值（下层可偷或者不偷）
        int notIncludeRoot = Math.max(leftNode.withNodeSum, leftNode.withoutNodeSum) + 
                                Math.max(rightNode.withNodeSum, rightNode.withoutNodeSum);
        
        // 衍生：build chain。返回所有要偷的房子
        List<TreeNode> withoutCur = new ArrayList<TreeNode>(); 
   
            withoutCur.addAll(leftNode.withNodeSum > leftNode.withoutNodeSum ? 
                                        leftNode.withNodeChain : leftNode.withoutNodeChain);


            withoutCur.addAll(rightNode.withNodeSum > rightNode.withoutNodeSum ? 
                                        rightNode.withNodeChain : rightNode.withoutNodeChain);


        
        
        
        List<TreeNode> withCur = new ArrayList<TreeNode>(); 
        withCur.add(root);
        withCur.addAll(leftNode.withoutNodeChain);
        withCur.addAll(rightNode.withoutNodeChain);
        
        if (root.val == 4) {
            
        }

        return new Node(root, includeRoot, notIncludeRoot, withCur, withoutCur);
    }
}
  
