package six.hard;

import java.util.*;

public class SafeList<E> implements List<E> {

    private final List<E> safeList;

    public SafeList() {
        this.safeList = new ArrayList<>();
    }

    @Override
    public int size() {
        return safeList.size();
    }

    @Override
    public boolean isEmpty() {
        return safeList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return o != null && safeList.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return safeList.iterator();
    }

    @Override
    public Object[] toArray() {
        return safeList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        //Тут тоже не понял
        return safeList.toArray(a);
    }

    @Override
    public boolean add(E e) {
        if (e == null || safeList.contains(e)) {
            //В реализации ArrayList он бы просто выдал ошибку на 467 строке, из-за add, так что возвращаю false.
            //Ругается, хотя возвращаю false когда элемент не добавлен, думаю что норм
            return false;
        }
        return safeList.add(e);
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            return false;
        }
        return safeList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            return false;
        }
        //Так и не понял, для чего здесь HashSet, который предлагает Intellij
        return safeList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) {
            return false;
        }
        boolean isModified = false;
        for (E e : c) {
            if (e != null && !safeList.contains(e)) {
                safeList.add(e);
                isModified = true;
            }
        }
        return isModified;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c == null || index > safeList.size() || index < 0) {
            return false;
        }
        boolean isModified = false;
        for (E e : c) {
            if (e != null && !safeList.contains(e)) {
                safeList.add(index, e);
                index++;
                isModified = true;
            }
        }
        return isModified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            return false;
        }
        return safeList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            return false;
        }
        return safeList.retainAll(c);
    }

    @Override
    public void clear() {
        safeList.clear();
    }

    @Override
    public E get(int index) {
        if (index < 0 || index > safeList.size()) {
            return null;
        }
        return safeList.get(index);
    }

    @Override
    public E set(int index, E element) {
        if (element == null || index < 0 || index > safeList.size() || safeList.contains(element)) {
            return null;
        }
        return safeList.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        if (element != null && index >= 0 && index <= safeList.size() && !safeList.contains(element)) {
            safeList.add(index, element);
        }
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index > safeList.size()) {
            return null;
        }
        return safeList.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            return -1;
        }
        return safeList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            return -1;
        }
        return safeList.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return safeList.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > safeList.size()) {
            //Вот тут думал вернуть null, но ListIterator не может быть null. Пришлось гуглить как вернуть пустой итератор
            return Collections.emptyListIterator();
        }
        return safeList.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex > safeList.size() || toIndex < 0 || toIndex > safeList.size() || fromIndex > toIndex) {
            //Здесь не очень будет проставлять emptyList, говорят, что он immutable
            return new SafeList<>();
        }
        return safeList.subList(fromIndex, toIndex);
    }
}
