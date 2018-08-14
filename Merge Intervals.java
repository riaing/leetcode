Given a collection of intervals, merge all overlapping intervals.

Example 1:

Input: [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
Example 2:

Input: [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervals [1,4] and [4,5] are considerred overlapping. 

首先需要sort整个list，按照interval的start。之后，go over every emelment in the list, 每个current interval
和要ruturn的list中的最后一个interval比，如果两个不重合，则把current加到return list中的最后一个，
