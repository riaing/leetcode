Clone an undirected graph. Each node in the graph contains a label and a list of its neighbors.


OJ's undirected graph serialization:
Nodes are labeled uniquely.

We use # as a separator for each node, and , as a separator for node label and each neighbor of the node.
As an example, consider the serialized graph {0,1,2#1,2#2,2}.

The graph has a total of three nodes, and therefore contains three parts as separated by #.

First node is labeled as 0. Connect node 0 to both nodes 1 and 2.
Second node is labeled as 1. Connect node 1 to node 2.
Third node is labeled as 2. Connect node 2 to node 2 (itself), thus forming a self-cycle.
Visually, the graph looks like the following:

       1
      / \
     /   \
    0 --- 2
         / \
         \_/


/**
 * Definition for undirected graph.
 * class UndirectedGraphNode {
 *     int label;
 *     List<UndirectedGraphNode> neighbors;
 *     UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
 * };
 */
 本题要考虑的是如何区别neighbor Node为一个新node或是本身，如果用set，遍历每个neighbor时加入新的node，则无法区分。方法是用map，
 将《old node， new initialized node》加入map，每次判断map是否containsKey，将map的value加入要返回的list。 
 -------方法一-------------------
 //不能只用set，{0,0,0} 时说明只有一个node两条指回本身的箭头，用set的话怎么说明neighbor就是自己或者neighbor是新的node. map的话如果n ==本身，从map中调出这个数，map中若没有此数，先加入此数。
public class Solution {
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        //use Map containing visited node
        Map<UndirectedGraphNode,UndirectedGraphNode> visited = new HashMap<UndirectedGraphNode, UndirectedGraphNode>(); 
        if(node == null){
            return null;
        }
        
        //set the head for new graph
        UndirectedGraphNode head = new UndirectedGraphNode(node.label);
        dfs(node, head, visited);
        return head; 
        
    }
    
    private void dfs(UndirectedGraphNode node, UndirectedGraphNode head, Map<UndirectedGraphNode, UndirectedGraphNode> visited){
        
        visited.put(node, head);

        for(UndirectedGraphNode n : node.neighbors){
            UndirectedGraphNode neibrNode = new UndirectedGraphNode(n.label);
           // if(visited.containsKey(n),add visited.get(n)进 head.neighbors.
            //没必要写这个if：但如果visited!contains(n),仍要在后面加一行 add visited。get(n).所以直接在后面写一行就行，所以这里的if不必要
           
            if(!visited.containsKey(n)){
               dfs(n, neibrNode, visited);
            }
          head.neighbors.add(visited.get(n));
          
        }
          
    }
} 

-------------方法二-------------------
public class Solution {
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) {
            return null;
        }
        
        HashMap<UndirectedGraphNode, UndirectedGraphNode> map = 
            new HashMap<UndirectedGraphNode, UndirectedGraphNode>();
        cloneGraph(node, map); //只用两个变量。
        return map.get(node);
    }
    
    void cloneGraph(UndirectedGraphNode node, HashMap<UndirectedGraphNode, UndirectedGraphNode> map) {
   
        
        UndirectedGraphNode copyNode = new UndirectedGraphNode(node.label);
        map.put(node, copyNode);
        
        for (UndirectedGraphNode neighbor : node.neighbors) {
         
         错误方法：无法区分neighbor是本身还是个新node
         /* UndirectedGraphNode neibrNode = new UndirectedGraphNode(neighbor.label);
          copyNode.neighbors.add(neibrNode); //不能加入newNode会出现需要{0,0,0} but {0,0,0#0#0}的情况
            if (!map.containsKey(neighbor)) {
                cloneGraph(neighbor, map);
             } */
             
 
            if (!map.containsKey(neighbor)) {
                cloneGraph(neighbor, map);
             }
            copyNode.neighbors.add(map.get(neighbor)); //right, but why ? 
            
            
       }
    }
}
