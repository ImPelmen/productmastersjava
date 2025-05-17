package five.hard;

import java.util.*;

public class Main {

  public static void main(String[] args) {
    ArrayList<Integer> list = new ArrayList<>();
    ArrayList<Integer> noDuplicatesList = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < 10; i++) {
      list.add(random.nextInt(10));
    }
    System.out.println(0 == list.get(0));
    System.out.println(list);
    //Решение в лоб
    noDuplicatesList = removeDuplicates(list);
    System.out.println("Решение в лоб");
    System.out.println(noDuplicatesList);
    //Решил через HashSet, однако порядок может быть изменен, так как HashSet не гарантирует порядок
    noDuplicatesList = removeDuplicatesSet(list);
    System.out.println("HashSet без гарантии порядка");
    System.out.println(noDuplicatesList);
    //Вот тут тоже HashSet, только сделал так, чтобы была гарантия порядка
    noDuplicatesList = removeDuplicatesSet2(list);
    System.out.println("HashSet с гарантией порядка");
    System.out.println(noDuplicatesList);
    //Однако можно сделать так, чтобы Set давал порядок
    noDuplicatesList = removeDuplicatesLinkedHashSet(list);
    System.out.println("LinkedHashSet");
    System.out.println(noDuplicatesList);
  }

  public static ArrayList<Integer> removeDuplicates(ArrayList<Integer> list) {
    ArrayList<Integer> noDuplicateList = new ArrayList<>();
    for (Integer number:
         list) {
      if (!isInArray(number, noDuplicateList)) {
        noDuplicateList.add(number);
      }
    }
    return noDuplicateList;
  }

  private static boolean isInArray(Integer number, ArrayList<Integer> list) {
    for (Integer n : list) {
      if (n.equals(number)) {
        return true;
      }
    }
    return false;
  }

  public static ArrayList<Integer> removeDuplicatesSet(ArrayList<Integer> list) {
    Set<Integer> noDuplicateSet = new HashSet<>(list);
    return new ArrayList<>(noDuplicateSet);
  }

  public static ArrayList<Integer> removeDuplicatesSet2(ArrayList<Integer> list) {
    Set<Integer> buffer = new HashSet<>();
    ArrayList<Integer> noDuplicateList = new ArrayList<>();
    for (Integer number : list) {
      if (buffer.add(number)) {
        noDuplicateList.add(number);
      }
    }

    return noDuplicateList;
  }

  public static ArrayList<Integer> removeDuplicatesLinkedHashSet(ArrayList<Integer> list) {
    Set<Integer> noDuplicatesSet = new LinkedHashSet<>(list);
    return new ArrayList<>(noDuplicatesSet);
  }
}
