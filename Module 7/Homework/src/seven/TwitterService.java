package seven;

import javax.swing.text.html.Option;
import java.util.*;

public class TwitterService {

    private final List<Post> posts = new ArrayList<>();

    public void initializePosts() {
        // posts.add(new User(1, "Alice"), "Привет, мир!");
        // posts.add(new User(2, "Bob"), "Сегодня отличный день!");
        // posts.add(new User(3, "Charlie"), "Люблю программировать на Java.");
        // System.out.println("Добавлены стартовые посты.");
    }

    public Post createPost(User author, String content, Long iterator) {
        Post post = new Post(author, content, iterator);
        posts.add(post);
        return post;
    }

    public void showPosts() {
        posts.forEach(System.out::println);
    }

    public Optional<Post> searchByAuthorAndIterator(String username, Long iterator) {
        if (username == null || iterator == null) {
            return Optional.empty();
        }
        String iteratorStr = "_" + iterator;
        return posts.stream().filter(post -> post.getId().startsWith(username) && post.getId().endsWith(iteratorStr)).findFirst();
    }

    public List<Post> getMostLikedPosts() {
        int maxLikes = posts.stream().mapToInt(Post::getLikes).max().orElse(0);
//        return posts.stream().max(Comparator.comparingInt(Post::getLikes)).stream().toList();
        return posts.stream().filter(post -> post.getLikes() == maxLikes).toList();
    }

    public List<Post> getAuthorPosts(User author) {
        if (author == null) {
            return Collections.emptyList();
        }
        return posts.stream().filter(post -> post.getAuthor().getName().equals(author.getName())).toList();
    }
}
