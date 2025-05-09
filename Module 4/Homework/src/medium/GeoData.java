package medium;

public class GeoData extends Data {
    private final double longitude;
    private final double latitude;

    public GeoData(int id, double longitude, double latitude) {
        super(id);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "medium.lesson.GeoData{" +
                "id=" + id +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
