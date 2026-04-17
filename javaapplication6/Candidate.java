package javaapplication6;

public class Candidate {
    private final int candidateId;
    private final String name;
    private final String party;
    private final int age;
    private final String symbol;
    private final int boothId;

    public Candidate(int candidateId, String name, String party, int age, String symbol, int boothId) {
        this.candidateId = candidateId;
        this.name = name;
        this.party = party;
        this.age = age;
        this.symbol = symbol;
        this.boothId = boothId;
    }

    public int getCandidateId() { return candidateId; }
    public String getName() { return name; }
    public String getParty() { return party; }
    public int getAge() { return age; }
    public String getSymbol() { return symbol; }
    public int getBoothId() { return boothId; }
}
