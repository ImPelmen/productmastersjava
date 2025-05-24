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
        return safeList.toArray(a);
    }

    @Override
    public boolean add(E e) {
        if (e == null || safeList.contains(e)) {
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
        return safeList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
}
