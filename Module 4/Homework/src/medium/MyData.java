package medium;

public class MyData extends Data {
    private final String description;

    public MyData(int id, String description) {
        super(id);
        this.description = description;
    }

    public String toString() {
        return "medium.lesson.MyData: id = " + id + ", description = " + description;
    }
}
