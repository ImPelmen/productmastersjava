package hard.box;

import medium.Data;
import medium.Printable;

public class Box<T extends Data & Printable> {

    private T item;

    public Box(T item) {
        this.item = item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void showType() {
        item.print();
    }
}
