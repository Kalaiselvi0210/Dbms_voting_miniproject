package javaapplication6;

public class Voter {
    private final int voterId;
    private final String name;
    private final int age;
    private final String gender;
    private final String address;
    private final String username;
    private boolean hasVoted;
    private final int boothId;

    public Voter(int voterId, String name, int age, String gender, String address,
                 String username, boolean hasVoted, int boothId) {
        this.voterId = voterId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.username = username;
        this.hasVoted = hasVoted;
        this.boothId = boothId;
    }

    public int getVoterId() { return voterId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getAddress() { return address; }
    public String getUsername() { return username; }
    public boolean isHasVoted() { return hasVoted; }
    public void setHasVoted(boolean hasVoted) { this.hasVoted = hasVoted; }
    public int getBoothId() { return boothId; }
}
