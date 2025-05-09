package medium;

public class UserData extends Data {

    private final String name;

    private final String email;

    public UserData(int id, String name, String email) {
        super(id);
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
