package medium;

public abstract class Data implements Printable {

    protected final int id;

    public Data(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                '}';
    }

    @Override
    public void print() {
        System.out.println(this.getClass().getSimpleName());
    }
}
