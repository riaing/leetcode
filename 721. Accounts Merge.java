Given a list of accounts where each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name, and the rest of the elements are emails representing emails of the account.

Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some common email to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.

After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails in sorted order. The accounts themselves can be returned in any order.

 

Example 1:

Input: accounts = [["John","johnsmith@mail.com","john_newyork@mail.com"],["John","johnsmith@mail.com","john00@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]
Output: [["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]
Explanation:
The first and second John's are the same person as they have the common email "johnsmith@mail.com".
The third John and Mary are different people as none of their email addresses are used by other accounts.
We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John', 'johnnybravo@mail.com'], 
['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.
Example 2:

Input: accounts = [["Gabe","Gabe0@m.co","Gabe3@m.co","Gabe1@m.co"],["Kevin","Kevin3@m.co","Kevin5@m.co","Kevin0@m.co"],["Ethan","Ethan5@m.co","Ethan4@m.co","Ethan0@m.co"],["Hanzo","Hanzo3@m.co","Hanzo1@m.co","Hanzo0@m.co"],["Fern","Fern5@m.co","Fern1@m.co","Fern0@m.co"]]
Output: [["Ethan","Ethan0@m.co","Ethan4@m.co","Ethan5@m.co"],["Gabe","Gabe0@m.co","Gabe1@m.co","Gabe3@m.co"],["Hanzo","Hanzo0@m.co","Hanzo1@m.co","Hanzo3@m.co"],["Kevin","Kevin0@m.co","Kevin3@m.co","Kevin5@m.co"],["Fern","Fern0@m.co","Fern1@m.co","Fern5@m.co"]]
 

Constraints:

1 <= accounts.length <= 1000
2 <= accounts[i].length <= 10
1 <= accounts[i][j].length <= 30
accounts[i][0] consists of English letters.
accounts[i][j] (for j > 0) is a valid email.


---------------------- DFS走图 --------------------------------------
/*
Email -> List<Email> 构造图
Time 最差 o(nklognk). n - accounts; k - maximum length of an account 
In the worst case, all the emails will end up belonging to a single person. 
The total number of emails will be N*K, and we need to sort these emails. 
DFS traversal will take NKNK operations as no email will be traversed more than once.

Space: O(nk) to build the map 
*/
class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        // 1. build graph. Email -> List neibors ： 这步就是union
        Map<String, List<String>> neiborMap = new HashMap<>(); 
        for (List<String> account : accounts) {
            String firstEmail = account.get(1);
            neiborMap.putIfAbsent(firstEmail, new ArrayList<>());
            for (int i = 2; i < account.size(); i++) {
                neiborMap.get(firstEmail).add(account.get(i));
                // 给第二，三，，个email连上第一个email
                neiborMap.putIfAbsent(account.get(i), new ArrayList<>());
                neiborMap.get(account.get(i)).add(firstEmail); // 把图连起来，只要每个node和第一个email连就够了
            }
        }
        
        // 2. dfs来输出结果
        List<List<String>> res = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (List<String> account : accounts) {
            if (visited.contains(account.get(1))) {
                continue;
            }
            List<String> cur = new ArrayList<>(); 
            dfs(account.get(1), cur, neiborMap, visited);
            // sort, and add userName 
            Collections.sort(cur); 
            cur.add(0, account.get(0));
            res.add(cur); 
        }
        return res; 
    }
    
    // DFS 染色，把所有的neibor都加到account里
    private void dfs(String email, List<String> cur,  Map<String, List<String>> neiborMap, Set<String> visited) {
        // 1. 把当前email加入cur
        cur.add(email);
        visited.add(email);
        
        // dfs走neibor
        for (String n : neiborMap.get(email)) {
            if (visited.contains(n)) {
                continue; 
            }
            dfs(n, cur, neiborMap, visited);
        }
    }
}

------------ DFS 直接return list的写法 ----------
 class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, Set<String>> emailMap = new HashMap<>();
        for (List<String> account: accounts) {
            String firstEmail = account.get(1);
            emailMap.putIfAbsent(firstEmail, new HashSet<>());
            for (int i = 2; i < account.size(); i++) {
                String nextEmail = account.get(i);
                emailMap.get(firstEmail).add(nextEmail);
                
                emailMap.putIfAbsent(nextEmail, new HashSet<>());
                emailMap.get(nextEmail).add(firstEmail);
                }
        }
        
        Set<String> visited = new HashSet<>();
        List<List<String>> res = new ArrayList<>();
        for (List<String> account : accounts) { 
            String name = account.get(0);
            String first = account.get(1);
            if (!visited.contains(first)) {
                List<String> oneAccount = new ArrayList<>();
                oneAccount.add(name);
                List<String> emails = getEmails(first, visited, emailMap);
                Collections.sort(emails); 
                oneAccount.addAll(emails);
                res.add(oneAccount);
            }
        }
        return res;
    }
    
    private List<String> getEmails(String cur, Set<String> visited, Map<String, Set<String>> emailMap) {
        List<String> res = new ArrayList<>();
        res.add(cur);
        visited.add(cur);
        for (String neibor : emailMap.get(cur)) {
            if (!visited.contains(neibor)) {
                res.addAll(getEmails(neibor, visited, emailMap));
            }
        }
        return res; 
    }
}
