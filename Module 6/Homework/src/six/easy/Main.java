package six.easy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(2);
        list.add(6);
        list.add(1);
//        list.add(null);
        Set<Integer> set = new HashSet<>();
        set.add(null);
        CustomList customList = new CustomList(list);
//        customList.reverseSort();
        list = customList.getList();
//        Integer index = customList.findValue(15);
        List<Integer> filtered = customList.getAllMoreThan(2);
        Integer sum = customList.getSum();
        System.out.println(list);
//        System.out.println(index);
        System.out.println(filtered);
        System.out.println(sum);
    }
}
