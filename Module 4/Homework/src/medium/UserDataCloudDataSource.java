package medium;

public class UserDataCloudDataSource implements DataSource<UserData> {
    @Override
    public UserData getData() {
        return new UserData(1, "Aibek", "aibek@test.com");
    }
}
