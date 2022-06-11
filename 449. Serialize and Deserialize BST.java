Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a 
file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another 
computer environment.

Design an algorithm to serialize and deserialize a binary search tree. There is no restriction on how your 
serialization/deserialization algorithm should work. You just need to ensure that a binary search tree can be serialized to a 
string and this string can be deserialized to the original tree structure.

The encoded string should be as compact as possible.

Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be 
stateless.

-----------------DFS （D&C）           -------------------------------------------------------------------
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

/**
对比 297. Serialize and Deserialize Binary Tree 
与binary tree不同的是，preorder后，小于root的肯定在左子树，etc。所以我们serialize时不需要存null来记录位置。
2，deserialize时，用范围min, max来确定当前node到底属于左子树还是右子树，并且同样用到了linkedlist删元素O(1)的性质。
*/
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        return reSerialize(root);
        
    }
    
    private String reSerialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        String left = reSerialize(root.left);
        String right = reSerialize(root.right);
        if (left.length() != 0 && right.length() != 0) {
            return root.val + "." + left + "." + right;
        }
        else if (left.length() != 0) {
            return root.val + "." + left;
        }
        else if (right.length() != 0) {
            return root.val + "." + right;
        }
        else {
            return root.val+"";
        }
       
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] array = data.split("\\."); // "." is regex. 
         
        List<String> list = new LinkedList<String>(Arrays.asList(array));
        
        return reDeserialize(list, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private TreeNode reDeserialize(List<String> list, int min, int max) {
        // corner case, for tree [] -> "" -> after split is still ""
        if (list.size() == 0 || list.get(0).isEmpty()) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.valueOf(list.get(0)));
        if (root.val < min || root.val > max) {
            return null;
        }
        list.remove(0);
        root.left = reDeserialize(list, min, root.val);
        root.right = reDeserialize(list, root.val, max);
        return root; 
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));


------------- 2022.6 ----------------------------
    /**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
/*
对比 297 题普通二叉树的序列化，利用 BST 左小右大的特性主要可以避免序列化空指针，利用 min, max 边界来划定一棵子树的边界，从而提升算法效率。
*/
public class Codec {
    private String deli = ",";
    // 前序遍历
    public String serialize(TreeNode root) { 
        StringBuilder b = new StringBuilder();
        serialize(root, b); 
       
        return b.toString();
    }
    private void serialize(TreeNode root, StringBuilder b) {
        if (root == null) {
            return;
        }
        b.append(root.val+deli);
        serialize(root.left, b);
        serialize(root.right, b); 
    }

    // 重点是min，max边界用来确定左右子树
    public TreeNode deserialize(String data) {
        if (data.isEmpty()) return null;
        // 1. 变成linkedlist
        String[] arr = data.split(","); // 先拆开
        List<Integer> tmp = Arrays.stream(arr).map(o -> Integer.parseInt(o)).collect(Collectors.toList()); // 转成integer
        LinkedList<Integer> input = new LinkedList<Integer>(tmp); // 转成linkedList
        // 2. 给最大最小值
        return deserialize(input, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private TreeNode deserialize(LinkedList<Integer> input, int min, int max) {
        // 用min, max判断子树边界
        if (input.isEmpty() || input.getFirst() < min || input.getFirst() > max) {
            return null;
        }
        TreeNode root = new TreeNode(input.getFirst());
        input.removeFirst();
        // 造左右子树
        root.left = deserialize(input, min, root.val);
        root.right = deserialize(input, root.val, max); 
        return root; 
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// String tree = ser.serialize(root);
// TreeNode ans = deser.deserialize(tree);
// return ans;
