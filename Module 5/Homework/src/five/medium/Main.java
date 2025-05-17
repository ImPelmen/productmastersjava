package five.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import five.medium.DayOfWeek;

public class Main {

  public static void main(String[] args) {
    List<DayOfWeek> dayOfWeeks = new ArrayList<>();
    dayOfWeeks.addAll(Arrays.asList(DayOfWeek.values()));
    for (DayOfWeek day:
            dayOfWeeks) {
      System.out.println(day.getTitle());
    }

    System.out.println(isWeekend(null));
  }
  public static boolean isWeekend(DayOfWeek day) {
    return DayOfWeek.SATURDAY.equals(day) || DayOfWeek.SUNDAY.equals(day);
  }

}
