package com.AmazonOnsite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * 设计一个appointment system,当插入一个appointment with date, start time, end time,会回应是否和 之前的appointment有冲突。
 * @author riapan 6/6/19
 */
public class CalanderNonOverlappingEvent {

  static class Event {
    int startTime;
    int endTime;
    int date;

    public Event(int start, int end, int date) {
      this.startTime = start;
      this.endTime = end;
      this.date = date;
    }
  }
  Comparator<Event> dataComparator;
  Map<Integer, List<Event>> map;

  public CalanderNonOverlappingEvent(Event... event) {
    this.map = new HashMap<>();
    this.dataComparator = new Comparator<Event>() {
      @Override
      public int compare(Event o1, Event o2) {
        return o1.startTime - o2.startTime;
      }
    };

    List<Event> events = new ArrayList<Event>(Arrays.asList(event));
    for (Event e : events) {
      if (!map.containsKey((e.date))) {
        map.put(e.date, new LinkedList<Event>());
      }
      map.get(e.date).add(e);
    }
    // sort value list
    for (Integer date : map.keySet()) {
      Collections.sort(map.get(date), dataComparator);
    }
  }

  public void insert(Event e) {
    if (!map.containsKey(e.date)) {
      map.put(e.date, new LinkedList<>());
      map.get(e.date).add(e);
    }
    else {
      // find the first event that has smaller or equal start time than current
      List<Event> events = map.get(e.date);
      int preEventIndex = binarySearch(events, e);
      // add at head
      if (preEventIndex < 0) {
        if (events.get(0).startTime > e.endTime) {
          events.add(0, e);
          return;
        }
      }

      if (events.get(preEventIndex).startTime == e.startTime) {
        System.out.println("cannot insert such event");
        return;
      }

      // add at tail
      else if (preEventIndex == events.size() -1) {
        if (events.get(preEventIndex).endTime < e.startTime) {
          events.add(e);
          return;
        }
      }
      else {
        if (events.get(preEventIndex).endTime < e.startTime && events.get(preEventIndex+1).startTime > e.endTime) {
          events.add(preEventIndex+1, e);
          return;
        }
      }
      System.out.println("cannot insert such event");
    }
  }
  // find the first event that has smaller or equal start date than cur
  private int binarySearch(List<Event> events, Event cur) {
    int start = 0;
    int end = events.size()-1;
    while (start < end) {
      int mid = (start + end) / 2;
      if (events.get(mid).startTime <= cur.startTime) {
        end = mid;
      }
      else {
        start = mid + 1;
      }
    }
    return events.get(start).startTime > cur.startTime ? -1 : start;
  }

  public static void main(String[] args) {
    Event a = new Event(1,2, 0);
    Event b = new Event(5,6, 0);
    Event test = new Event(0,0, 0);

    CalanderNonOverlappingEvent c = new CalanderNonOverlappingEvent(b, a);
    c.insert(test);


  }
}
