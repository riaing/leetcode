Compare two version numbers version1 and version2.
If version1 > version2 return 1, if version1 < version2 return -1, otherwise return 0.

You may assume that the version strings are non-empty and contain only digits and the . character.
The . character does not represent a decimal point and is used to separate number sequences.
For instance, 2.5 is not "two and a half" or "half way to version three", it is the fifth second-level revision of the second first-level revision.

Here is an example of version numbers ordering:
0.1 < 1.1 < 1.2 < 13.37


public class Solution {
    public int compareVersion(String version1, String version2) {
        if(version1 == null || version2 == null){
            return 0; 
        }
        String[] v1 = version1.split("\\."); //"." in regular expression mean ANY CHAR! so need use \\. instead 
        String[] v2 = version2.split("\\.");
        
        int curV1 = 0;
        int curV2 = 0; 
        for(int i = 0; i < Math.max(v1.length, v2.length); i++){
            //if one version is reach end, add 0 to it 
            if(i > v1.length-1){ 
                 curV1 =0;
                 curV2 = Integer.parseInt(v2[i]);
            }
            else if(i > v2.length-1){
                curV2 =0;
                 curV1 = Integer.parseInt(v1[i]);
            }
            else{
                curV1 = Integer.parseInt(v1[i]);
                curV2 = Integer.parseInt(v2[i]);
            }
            
            //do condition 
            if(curV1>curV2){
                return 1;
            }
            else if( curV1 < curV2){
                return -1;
            }
            else {
                continue; 
            }
                
        }
        return 0 ; 
    }
}
