package seven;

import java.util.*;


public class TwitterConsoleApp {

    private static Long iterator = 0L;
    private static final Scanner scanner = new Scanner(System.in);
    private static final TwitterService twitterService = new TwitterService();

    public static void main(String[] args) {
        new TwitterConsoleApp().run();
    }

    public void run() {
        System.out.print("Введите ваше имя: ");
        String userName = scanner.nextLine().trim();
        User currentUser = new User(userName);
        System.out.println("Добро пожаловать, " + currentUser.getName() + "!");

        twitterService.initializePosts();

        while (true) {
            showMenu();
            int choice = getIntInput();
            switch (choice) {
                case 1 -> {
                    System.out.println("Введите текст для поста(не более 280 символов): ");
                    String content = scanner.nextLine().trim();
                    if (content.length() > 280) {
                        System.out.println("Текст привышает лимит в 280 символов для поста");
                        break;
                    }
                    Post post = twitterService.createPost(currentUser, content, iterator);
                    iterator += 1;
                    System.out.println("Ваш пост сохранен и имеет порядковый номер: " + iterator);
                    System.out.println(post.toString());
                }
                case 2 -> {
                    System.out.println("Введите автора Поста: ");
                    String posAuthorName = scanner.nextLine().trim();
                    System.out.println("Введите номер поста: ");
                    Long postNumber = Long.valueOf(scanner.nextLine().trim());
                    Optional<Post> postOptional = twitterService.searchByAuthorAndIterator(posAuthorName, postNumber);
                    if (postOptional.isEmpty()) {
                        System.out.println("Пост от автора " + posAuthorName + " с номером " + postNumber + " не найден!");
                    } else {
                        Post post = postOptional.get();
                        post.setLikes(post.getLikes() + 1);
                        System.out.println("Вы поставили like посту № " + postNumber + " от автора " + post.getAuthor().getName() + ". Общее количество лайков у поста: " + post.getLikes());
                    }
                }
                case 3 -> {
                    System.out.println("Введите автора Поста: ");
                    String posAuthorName = scanner.nextLine().trim();
                    System.out.println("Введите номер поста: ");
                    Long postNumber = Long.valueOf(scanner.nextLine().trim());
                    Optional<Post> postOptional = twitterService.searchByAuthorAndIterator(posAuthorName, postNumber);
                    if (postOptional.isEmpty()) {
                        System.out.println("Пост от автора " + posAuthorName + " с номером " + postNumber + " не найден!");
                    } else {
                        Post post = postOptional.get();
                        post.setReposts(post.getReposts() + 1);
                        System.out.println("Вы сделали repost поста № " + postNumber + " от автора " + post.getAuthor().getName() + ". Общее количество репостов у поста: " + post.getLikes());
                    }
                }
                case 4 -> twitterService.showPosts();
                case 5 -> {
                    List<Post> mostLikedPosts = twitterService.getMostLikedPosts();
                    System.out.println(mostLikedPosts);
                }
                case 6 -> {
                    List<Post> authorPosts = twitterService.getAuthorPosts(currentUser);
                    if (authorPosts.isEmpty()) {
                        System.out.println("У Вас пока нет постов");
                        break;
                    }
                    System.out.println("Ваши посты: ");
                    System.out.println(authorPosts);
                }
                case 7 -> {
                    System.out.println("Введите автора Поста: ");
                    String posAuthorName = scanner.nextLine().trim();
                    System.out.println("Введите номер поста: ");
                    Long postNumber = Long.valueOf(scanner.nextLine().trim());
                    Optional<Post> postOptional = twitterService.searchByAuthorAndIterator(posAuthorName, postNumber);
                    if (postOptional.isEmpty()) {
                        System.out.println("Пост от автора " + posAuthorName + " с номером " + postNumber + " не найден!");
                    } else {
                        Post post = postOptional.get();
                        System.out.println("Введите комментарий: ");
                        String comment = scanner.nextLine().trim();
                        twitterService.createComment(comment, post);
                        System.out.println("Вы комментарий к посту № " + postNumber + " от автора " + post.getAuthor().getName());
                    }
                }
                case 8 -> {
                    System.out.println("Введите автора Поста: ");
                    String posAuthorName = scanner.nextLine().trim();
                    System.out.println("Введите номер поста: ");
                    Long postNumber = Long.valueOf(scanner.nextLine().trim());
                    Optional<Post> postOptional = twitterService.searchByAuthorAndIterator(posAuthorName, postNumber);
                    if (postOptional.isEmpty()) {
                        System.out.println("Пост от автора " + posAuthorName + " с номером " + postNumber + " не найден!");
                    } else {
                        Post post = postOptional.get();
                        List<Commentary> commentaries = post.getCommentaries();
                        if (commentaries.isEmpty()) {
                            System.out.println("К данному посту пока нет комментариев");
                        } else {
                            System.out.println(commentaries);
                        }
                    }
                }
                case 9 -> {
                    System.out.println("Введите автора Поста: ");
                    String posAuthorName = scanner.nextLine().trim();
                    System.out.println("Введите номер поста: ");
                    Long postNumber = Long.valueOf(scanner.nextLine().trim());
                    Optional<Post> postOptional = twitterService.searchByAuthorAndIterator(posAuthorName, postNumber);
                    if (postOptional.isEmpty()) {
                        System.out.println("Пост от автора " + posAuthorName + " с номером " + postNumber + " не найден!");
                    } else {
                        Post post = postOptional.get();
                        twitterService.deletePost(post);
                        System.out.println("Вы поставили like посту № " + postNumber + " от автора " + post.getAuthor().getName() + ". Общее количество лайков у поста: " + post.getLikes());
                    }
                }
                case 10 -> {
                    System.out.println("Выход...");
                    return;
                }
                default -> System.out.println("Некорректный ввод. Попробуйте снова.");
            }
        }
    }

    private int getIntInput() {
        int input;
        try {
            input = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод.");
            return -1;
        }
        return input;
    }

    private static void showMenu() {
        System.out.println("\n=== Twitter Console ===");
        System.out.println("1. Написать пост");
        System.out.println("2. Лайкнуть пост");
        System.out.println("3. Сделать репост");
        System.out.println("4. Показать все посты");
        System.out.println("5. Показать популярные посты");
        System.out.println("6. Показать мои посты");
        System.out.println("7. Написать коммент под пост");
        System.out.println("8. Получить комментарий поста");
        System.out.println("9. Удалить пост");
        System.out.println("10. Выход");
        System.out.print("Выберите действие: ");
    }

}
