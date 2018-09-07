https://evelynn.gitbooks.io/google-interview/longest_absolute_file_path.html 
Suppose we abstract our file system by a string in the following manner:

The string "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext" represents:

dir
    subdir1
    subdir2
        file.ext
The directory dir contains an empty sub-directory subdir1 and a sub-directory subdir2 containing a file file.ext.

The string "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext" represents:

dir
    subdir1
        file1.ext
        subsubdir1
    subdir2
        subsubdir2
            file2.ext
The directory dir contains two sub-directories subdir1 and subdir2. subdir1 contains a file file1.ext and an empty second-level sub-directory subsubdir1. subdir2 contains a second-level sub-directory subsubdir2 containing a file file2.ext.

We are interested in finding the longest (number of characters) absolute path to a file within our file system. For example, in the second example above, the longest absolute path is "dir/subdir2/subsubdir2/file2.ext", and its length is 32 (not including the double quotes).

Given a string representing the file system in the above format, return the length of the longest absolute path to file in the abstracted file system. If there is no file in the system, return 0.

Note:
The name of a file contains at least a . and an extension.
The name of a directory or sub-directory will not contain a ..
Time complexity required: O(n) where n is the size of the input string.

Notice that a/aa/aaa/file1.txt is not the longest file path, if there is another path aaaaaaaaaaaaaaaaaaaaa/sth.png.


---------解法一：用array as a map, index is the level, and value at that index is the length of current level.  
/*
dir\n\tsub\n\t\tfile.txt

level 0: 0 char 
level 1: dir(3) - redundantNumOft (0) + \(1) +  last level's char(0) = 4 char 
level 2: \tsub(4) - redundantNumOft(1) + \(1) + last level's char(4) = 8 char 
level 3: \t\tfile.txt(10) - redundantNumOft(2) + \(1 which will be remove later) + last level's char(7) = 15; 

so level = reduendantNumOft + 1
*/
class Solution {
    public int lengthLongestPath(String input) {
   
        // split the string by \n
        String[] files = input.split("\n");
        // initialize the map to keep track of each level(index) and the length of each level(value at that index);  
        // Add 1 because we have a 0 level. 
        int[] stack = new int[files.length + 1]; 
        stack[0] = 0; 
        int maxLength = 0; 
        for (String s : files) {
            // Get how many \t to delete from the string. if no \t, lastIndexOf will return -1.
            // This num is also the previous level number. 
            int redundantNumOft = s.lastIndexOf("\t") + 1; 
            int curLength = stack[redundantNumOft] + s.length() - redundantNumOft + 1; // +1 is for the /. 
            // Put the length to its level,  level = redundantNumOft + 1
            stack[redundantNumOft + 1] = curLength; 
            
            if (s.contains(".")) {
                maxLength = Math.max(maxLength, curLength -1); // -1 is to remove the auto added / from above 
            }
        }
        return maxLength; 
    }
}
