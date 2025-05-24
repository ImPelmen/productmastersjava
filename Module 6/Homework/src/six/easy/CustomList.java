package six.easy;

import java.util.ArrayList;
import java.util.List;

public class CustomList {

    private List<Integer> list;

    public CustomList(List<Integer> list) {
        this.list = list;
    }

    public List<Integer> getList() {
        return list;
    }

    public Integer findMinimum() {
        if (list.isEmpty()) {
            throw new RuntimeException("Empty list given");
        }
        Integer minimum = list.get(0);
        for (Integer el : list) {
            if (el < minimum) {
                minimum = el;
            }
        }

        return minimum;
    }

    public Integer findMaximum() {
        if (list.isEmpty()) {
            throw new RuntimeException("Empty list given");
        }
        Integer maximum = list.get(0);
        for (Integer el : list) {
            if (el > maximum) {
                maximum = el;
            }
        }

        return maximum;
    }

    public void sort() {
        int n = list.size();
        for (int i = 0; i < n; i++) {
            for (int k = i + 1; k < n; k++) {
                if (list.get(i) > list.get(k)) {
                    int temp = list.get(i);
                    list.set(i, list.get(k));
                    list.set(k, temp);
                }
            }
        }
    }

    public void reverseSort() {
        int n = list.size();
        for (int i = 0; i < n; i++) {
            for (int k = i + 1; k < n; k++) {
                if (list.get(i) < list.get(k)) {
                    int temp = list.get(i);
                    list.set(i, list.get(k));
                    list.set(k, temp);
                }
            }
        }
    }

    public Integer findValue(Integer value) {
        int left = 0;
        int right = list.size() - 1;
        this.sort();
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid).equals(value)) {
                return mid;
            }
            if (value > list.get(mid)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null;
    }

    public List<Integer> getAllMoreThan(Integer target) {
        List<Integer> filtered = new ArrayList<>();
        for (Integer element : list) {
            if (element > target) {
                filtered.add(element);
            }
        }

        return filtered;
    }

    public Integer getSum() {
        Integer sum = 0;
        for (Integer element:
             list) {
            sum += element;
        }

        return sum;
    }

}
