package hard;

import hard.box.Box;
import medium.*;

public class Main {

  public static void main(String[] args) {
    DataSource<MyData> myDataDataSource = new Repository<>(
            new CachedDataSource<>(), new MyDataCloudDataSource());

    DataSource<GeoData> geoDataDataSource = new GeoRepository(
            new CachedDataSource<>(), new GeoDataCloudDataSource());

    DataSource<UserData> userDataDataSource = new UserRepository(
            new CachedDataSource<>(), new UserDataCloudDataSource());

    MyData myData = myDataDataSource.getData();
    GeoData geoData = geoDataDataSource.getData();
    UserData userData = userDataDataSource.getData();
    Box<UserData> userDataBox = new Box<>(userData);
    Box<MyData> myDataBox = new Box<>(myData);
    Box<GeoData> geoDataBox = new Box<>(geoData);
    userDataBox.showType();
    System.out.println(userDataBox.getItem().toString());
    myDataBox.showType();
    System.out.println(myDataBox.getItem().toString());
    geoDataBox.showType();
    System.out.println(geoDataBox.getItem().toString());
  }

}
