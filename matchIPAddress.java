/**
Hi Kevin! I've finished the implementation. The idea is to have a map with IP range as key and corresponding country as value. And for every input, we check whether it's laid into any country's IP range. I also made some structural change so it's clearer.
Some optimization: 
1, for IP range, instead of using in[] to represent, we can create a new class Range(or use com.google.common.collect.Range) that contains lower and upper bound for a certain range, which is clearer than int[] with index. 
2, We can have another function to do range check(line 46-47), in that case, if the logic needs to be changed(eg: exclusive the upper bound, etc), we have more modulized code 

Again, Thank you so much for your time and I really enjoy working through the question with you!

*/
import java.io.*;
import java.util.*;
import org.junit.*;
import org.junit.runner.*;
 
public class Solution {   
  // The goal is to write a function `lookup_country` that, given a ip address,
  // returns a country from our set of ip rules, or 'UNKNOWN'.

  // The rules block contains the rules for which IPs map to which countries.
  // IPs are IPv4 in dot notation ("a.b.c.d", where a ranges from 0 to 255, etc.)
  // assume each IP has a unique country, or is not in the set.
  
  // IP ranges with the start and end separated by a dash. Ranges are inclusive.
  // For example, '103.15.0.0-103.15.0.125'

   public static final List<String> SAMPLE_RULES = Arrays.asList(
     "103.1.108.1:AU",
     "3.254.254.254:US",
     "5.10.64.17:CN",
     "0.0.0.0-0.12.13.14:FR",
     "209.88.2.2-209.88.2.255:AZ"
   );
  
  /* Mapping to IP */
  public static final Map<List<int[]>, String> countryMap = constructMap(SAMPLE_RULES);

  // ip_addr will be a IPv4 address in dot notation: 'a.b.c.d'
  // assume all addresses passed are valid.
   static class GeoIP {
     public String lookupCountry(String ipAddress) {

       String[] splitInput = ipAddress.split("\\.");
       // Iterate through map's key and check if input is in certain country's IP range
       for(List<int[]> key : countryMap.keySet()) {
         int index = 0;
          for(index = 0; index < key.size(); index ++) {
            if (!(Integer.parseInt(splitInput[index]) >= key.get(index)[0] 
                  && Integer.parseInt(splitInput[index]) <= key.get(index)[1])) 
            {
                break;
            }
          }
          // If input is in range, return the corresponding country
         if (index == 4) {
           return countryMap.get(key);
         }
       }
        return "UNKNOWN";
      }
     }
  
  // Construct map which has key as IP range and value as country 
  private static Map<List<int[]>, String> constructMap(List<String> SAMPLE_RULES) {
      Map<List<int[]>, String> countryMap = new HashMap<List<int[]>, String>();
       for(String s : SAMPLE_RULES) {
         String[] rules = s.split(":");
         String[] IPs = rules[0].split("-");
         // Split the IP address by "." 
         String[][] ipSplit = new String[2][4];
         for (int i = 0; i < IPs.length; i++) {
           String[] cur = IPs[i].split("\\."); 
           ipSplit[i] = cur;
         }
        
         // Add range into map
         List<int[]> ipRange = new ArrayList<int[]>();
         for (int i = 0; i < 4; i++) {
           int[] curRange = new int[2];
          if (IPs.length == 1) {
            curRange[0] = Integer.parseInt(ipSplit[0][i]);
            curRange[1] = Integer.parseInt(ipSplit[0][i]);
          }
          else{
            curRange[0] = Integer.parseInt(ipSplit[0][i]);
            curRange[1] = Integer.parseInt(ipSplit[1][i]);
          }
          ipRange.add(curRange); 
         }
         countryMap.put(ipRange, rules[1]);
       }
    return countryMap;
  }
   

   @Test
   public void testLookupCountry() {
    Map<String, String> tests = new HashMap<String, String>();
    tests.put("103.1.108.1", "AU");
    tests.put("3.254.254.254", "US");
    tests.put("5.10.64.17", "CN");
    tests.put("127.0.0.1", "UNKNOWN");
    tests.put("0.1.1.1", "FR");
    tests.put("209.88.2.123", "AZ");

    GeoIP geoIP = new GeoIP();
    for (Map.Entry<String, String> test : tests.entrySet()) {
     String country = geoIP.lookupCountry(test.getKey());
     Assert.assertEquals(test.getValue(), country);
    }
   }
 
   public static void main(String[] args) {
    JUnitCore.main("Solution");
   }
 }
