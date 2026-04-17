package javaapplication6;

public class Booth {
    private final int boothId;
    private final String boothName;
    private final String location;

    public Booth(int boothId, String boothName, String location) {
        this.boothId = boothId;
        this.boothName = boothName;
        this.location = location;
    }

    public int getBoothId() {
        return boothId;
    }

    @Override
    public String toString() {
        return boothName + " - " + location;
    }
}
