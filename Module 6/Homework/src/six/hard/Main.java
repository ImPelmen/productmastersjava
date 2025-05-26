package six.hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    List<Integer> safeList = new SafeList<>();
    safeList.add(1);
    safeList.add(2);
    safeList.add(3);
    safeList.add(4);
    safeList.add(5);
    safeList.add(9999, null);
    System.out.println(Arrays.toString(safeList.subList(5, 4).toArray()));
    List<String> fruits = new SafeList<>();
    fruits.add("banana");
    fruits.add("apple");
    fruits.add("apple");
    System.out.println(Arrays.toString(fruits.toArray()));
    //Так и не понял для чего здесь метод removeDuplicates
  }

  public static ArrayList<Integer> removeDuplicates(ArrayList<Integer> list) {
    return null;
  }
}
