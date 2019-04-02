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
