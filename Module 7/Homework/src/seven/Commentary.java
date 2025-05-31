package seven;

public class Commentary {

    private final Long id;

    private final String content;

    public Commentary(Long iterator, String content) {
        this.id = iterator;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Commentary{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
