package seven;

public class Post {

    private final String id;

    private final User author;

    private final String content;

    private Integer likes;

    private Integer reposts;

    public Post(User author, String content, Long iterator) {
        this.author = author;
        this.content = content;
        this.likes = 0;
        this.reposts = 0;
        this.id = generateId(iterator);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", author=" + author +
                ", content='" + content + '\'' +
                ", likes=" + likes +
                ", reposts=" + reposts +
                '}';
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public void setReposts(Integer reposts) {
        this.reposts = reposts;
    }

    private String generateId(Long iterator) {
        long unixTime = System.currentTimeMillis();
        if (author.getName() == null) {
            throw new RuntimeException("Ну удалось считать имя пользователя");
        }
        iterator++;
        return author.getName() + "_" + unixTime + "_" + iterator;
    }

    public String getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Integer getLikes() {
        return likes;
    }

    public Integer getReposts() {
        return reposts;
    }
}
