Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.

Example: 

You may serialize the following tree:

    1
   / \
  2   3
     / \
    4   5

as "[1,2,3,null,null,4,5]"
Clarification: The above format is the same as how LeetCode serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.

Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.


--------------------------解-----------------------------------------------------------------------------------------
Time complexity : in both serialization and deserialization functions, we visit each node exactly once, thus the time 
complexity is O(N), where N is the number of nodes, i.e. the size of tree.

Space complexity : in both serialization and deserialization functions, we keep the entire tree, either at the beginning 
or at the end, therefore, the space complexity is O(N).

---------------------BFS解法，自己想出来的。。。---------------------------------------------------------------------
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder b = new StringBuilder();
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        if (root == null) {
            return "";
        }
        
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            if (cur == null) {
                b.append("null");
                b.append(",");
            }
            else{
                b.append(cur.val + ",");
                q.offer(cur.left);
                q.offer(cur.right);
            }
        }
        return b.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
       String[] s = data.split(",");
       if (s[0].length() == 0) {
           return null;
       } 
        
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        TreeNode root = new TreeNode(Integer.valueOf(s[0]));
        q.offer(root);
        int i = 1; 
        while (i < s.length) {
            TreeNode cur = q.poll();
            while (cur == null) {
                cur = q.poll();
            } 
            cur.left = s[i].equals("null") ? null : new TreeNode(Integer.valueOf(s[i]));
            q.offer(cur.left);
            i++;
            cur.right = s[i].equals("null") ? null : new TreeNode(Integer.valueOf(s[i]));
            q.offer(cur.right);
            i++;
        }
        
        return root;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));


------------------- preorder： DFS serialize + iteration(stack) deserialize-------------------------------------------
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        System.out.println( rSerialize(root, ""));
       return rSerialize(root, "");
    }
    
    // Preorder traversal the tree 
       private String rSerialize(TreeNode root) {
         if (root == null) {
            return "null";
        }
        return root.val + "," + rSerialize(root.left) + "," + rSerialize(root.right);  
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
       String[] data_array = data.split(",");
        if (data_array[0].equals("null")) {
            return null;
        }
        // 加入第一个元素
        Deque<TreeNode> s = new LinkedList<TreeNode>();
        TreeNode root = new TreeNode(Integer.valueOf(data_array[0]));
        s.push(root);
    
        int i = 0;
        // iterate through the whole list.
        while (i < data_array.length-1) {
            i++;
            TreeNode cur = s.peek();
            //如果cur node是null，拿出来，再拿出他的上一个元素（肯定就是parent），iterate parent的右边
            if (cur == null) {
                s.pop(); // pop out the null node 
                cur = s.pop(); //再次pop拿出他的parent   
                cur.right =  data_array[i].equals("null") ? null : new TreeNode(Integer.valueOf(data_array[i]));
                s.push(cur.right); 
            }
            // iterate node的左边，这时候不pop node
            else {
            cur.left = data_array[i].equals("null") ? null : new TreeNode(Integer.valueOf(data_array[i]));
            s.push(cur.left);
            }

        }
        return root;
     
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));

---------------------------- preorder: DFS serialize + deserialize --------------------------------------------------
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
       return rSerialize(root, "");
    }
    
    // Preorder traversal the tree 
       private String rSerialize(TreeNode root, String s) {
         if (root == null) {
            return "null";
        }
        return root.val + "," + rSerialize(root.left, s) + "," + rSerialize(root.right, s);  
    }

    //每次删list的第一个元素，进行d&c. 因为是linked list，所以删第一个元素也是o(1)
    public TreeNode deserialize(String data) {
       String[] data_array = data.split(",");
       
        List<String> list = new LinkedList<String>(Arrays.asList(data_array));
       return helper(list);

    }
    
    private TreeNode helper(List<String> list) {
         if (list.size() == 0) {
             return null;
         }
        
        if (list.get(0).equals("null")) {
            list.remove(0);
            return null;
        }
        TreeNode root = new TreeNode(Integer.valueOf(list.get(0)));
        // remove the head of the list. O(1)
        list.remove(0);
        root.left = helper(list);
        root.right = helper(list);
        return root;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));

----------------- 2022.6 前序遍历 + 用空字符区分左右子树 -------------------
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
PS：一般语境下，单单前序遍历结果是不能还原二叉树结构的，因为缺少空指针的信息，至少要得到前、中、后序遍历中的两种才能还原二叉树。但是这里的 node 列表包含空指针的信息，所以只使用 node 列表就可以还原二叉树。
*/
public class Codec {
    String SEP = ",";
    String NULL = "#";

    /* 主函数，将二叉树序列化为字符串 */
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        return sb.toString();
    }

    /* 辅助函数，将二叉树存入 StringBuilder */
    void serialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append(NULL).append(SEP);
            return;
        }

        /******前序遍历位置******/
        sb.append(root.val).append(SEP);
        /***********************/

        serialize(root.left, sb);
        serialize(root.right, sb);
    }

    /* 主函数，将字符串反序列化为二叉树结构 */
    public TreeNode deserialize(String data) {
        // 将字符串转化成列表
        LinkedList<String> nodes = new LinkedList<>();
        for (String s : data.split(SEP)) {
            nodes.addLast(s);
        }
        return deserialize(nodes);
    }

    /* 辅助函数，通过 nodes 列表构造二叉树 */
    TreeNode deserialize(LinkedList<String> nodes) {
        if (nodes.isEmpty()) return null;

        /******前序遍历位置******/
        // 列表最左侧就是根节点
        String first = nodes.removeFirst();
        if (first.equals(NULL)) return null;
        TreeNode root = new TreeNode(Integer.parseInt(first));
        /***********************/

        root.left = deserialize(nodes);
        root.right = deserialize(nodes);

        return root;
    }
}
// 详细解析参见：
// https://labuladong.github.io/article/?qno=297

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
