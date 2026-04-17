package javaapplication6;

public class ResultRecord {
    private final int candidateId;
    private final String candidateName;
    private final String party;
    private final String boothName;
    private final int totalVotes;

    public ResultRecord(int candidateId, String candidateName, String party, String boothName, int totalVotes) {
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.party = party;
        this.boothName = boothName;
        this.totalVotes = totalVotes;
    }

    public int getCandidateId() { return candidateId; }
    public String getCandidateName() { return candidateName; }
    public String getParty() { return party; }
    public String getBoothName() { return boothName; }
    public int getTotalVotes() { return totalVotes; }
}
