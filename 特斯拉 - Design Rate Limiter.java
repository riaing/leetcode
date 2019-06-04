/**

This design is using fix window algorithm in which the time window is
considered from the start of the time-unit to the end of the time-unit, to
decide which service should be rejected or accepted per user. 


*/
import java.io.*;
import java.util.*;


class Solution {
 
  // This RateLimit should do 3 things: 
  // 1, if the user is new, set his starting time, increase his count 
  // 2, if the user exist and cur time - starting time >= 60, repeat above
  // 3, if cur time - starting time < 60, do check()
  // { if count < upper bound, count++,
  //   else: reject the request 
  // }
  class RateLimit {
    Map<String, User> userMap = new HashMap<String, User>();
    
    public boolean isAllow(String userId) {
      if (!userMap.containsKey(userId)) {
        map.put(userId, new User(userId, 20, 1, time.now()));
      }
      else {
        User curUser = userMap.get(userId);
        Time curTime = time.now();
        if (curTime - curUser.timpStamp >= 60) {
          curUser.setTime().resetCount();
        }
        else {
          check(curUser);
        }
      }
    }
    
    private void check(User user) {
      if (user.count < user.numQueries) {
        user.increaseCount();
      }
      else {
        // reject the request 
        System.out.println("429 - Too many requests");
      }
    }
  }
  
  class User {
    int numQueries; // upperBound
    int id;
    int count;
    Time timpStamp;
    
    public user(int id, int numQueries, int count, Time timpStamp) {
      this.numQueries = numQueries;
      this.id = id;
      this.count = count;
      this.timpStamp = timpStamp;
    }
    
    public User increaseCount() {
      this.count = this.count+1;
      return this; 
    }
    
    public User resetCount() {
      this.count = 1;
      return this; 
    }
    
    public User setTime() {
      this.timeStamp = time.now();
      return this;
    }
}
