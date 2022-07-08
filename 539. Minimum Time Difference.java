Given a list of 24-hour clock time points in "HH:MM" format, return the minimum minutes difference between any two time-points in the list.
 

Example 1:

Input: timePoints = ["23:59","00:00"]
Output: 1
Example 2:

Input: timePoints = ["00:00","23:59","00:00"]
Output: 0
 

Constraints:

2 <= timePoints.length <= 2 * 104
timePoints[i] is in the format "HH:MM".


---------------------------------------
/* 时间o(N), 把所有的时间都转成分钟，最大是 23*60 + 59. 所以开辟一个 24*60的boolean bucket
空间： o(1440)

方法2： 换成时间后排序：时间o（nlgn），空间小一些O（n）
*/

class Solution {
    public int findMinDifference(List<String> timePoints) {
        int range = 24 * 60; 
        int hr = 60;
        boolean[] bucket = new boolean[range];
        
        for (String s : timePoints) {
            // 1. transfer to time 
            String[] arr = s.split(":");
            int hour = Integer.parseInt(arr[0]);
            int min =  Integer.parseInt(arr[1]);
            int curTime = hour * 60 + min;
            if (bucket[curTime]) { // 找到重复值，返回0
                return 0;
            }
            bucket[curTime] = true; 
        }
        
        // 找到start
        int pre = 0;
        while (!bucket[pre]) {
            pre++;
        }
        // 找到end 
        int lastTime = bucket.length - 1; 
        while (!bucket[lastTime]) {
            lastTime--;
        }
        // 1. 比较首尾
        int res =  range - lastTime + pre;
        // 2. 遍历bucket两两比较，注意头尾要比较
        for (int i = pre+1; i <= lastTime; i++) {
            if (bucket[i]) {
                res = Math.min(res, i - pre);
                pre = i;
                
            }
        }
        return res; 
    }
}
