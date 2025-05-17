package five.medium;

public enum DayOfWeek {
    MONDAY("Понедельник"),
    TUESDAY("Вторник"),
    WEDNESDAY("Среда"),
    THURSDAY("Четверг"),
    FRIDAY("Пятница"),
    SATURDAY("Суббота"),
    SUNDAY("Воскресенье");

    private final String title;

    DayOfWeek(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static DayOfWeek findByTitle(String title) {
        for (DayOfWeek day : values()) {
            if (day.getTitle().equalsIgnoreCase(title)) {
                return day;
            }
        }

        return null;
    }
}
