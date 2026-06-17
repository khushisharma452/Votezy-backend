package in.khushi.votezy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class ElectionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "election_id")
    @JsonIgnore
    private Election election;

    private int totalVotes;

    @OneToOne
    @JoinColumn(name = "winner_id")
    @JsonIgnore
    private Candidate winner;

    @JsonProperty("winnerId")
    public Long getWinnerId() {
        return winner != null ? winner.getId() : null;
    }

    @JsonProperty("electionName")
    public String getElectionName() {
        return election != null
                ? election.getElectionName()
                : null;
    }
}
