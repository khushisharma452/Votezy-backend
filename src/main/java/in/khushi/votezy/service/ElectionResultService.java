package in.khushi.votezy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.khushi.votezy.entity.Candidate;
import in.khushi.votezy.entity.Election;
import in.khushi.votezy.entity.ElectionResult;
import in.khushi.votezy.exception.ResourceNotFoundException;
import in.khushi.votezy.repository.CandidateRepository;
import in.khushi.votezy.repository.ElectionRepository;
import in.khushi.votezy.repository.ElectionResultRepository;
import in.khushi.votezy.repository.VoteRepository;

@Service
public class ElectionResultService {

```
private final CandidateRepository candidateRepository;
private final ElectionResultRepository electionResultRepository;
private final VoteRepository voteRepository;
private final ElectionRepository electionRepository;

@Autowired
public ElectionResultService(
        CandidateRepository candidateRepository,
        ElectionResultRepository electionResultRepository,
        VoteRepository voteRepository,
        ElectionRepository electionRepository) {

    this.candidateRepository = candidateRepository;
    this.electionResultRepository = electionResultRepository;
    this.voteRepository = voteRepository;
    this.electionRepository = electionRepository;
}

public ElectionResult declareElectionResult(String electionName) {

    Election election = electionRepository
            .findByElectionName(electionName)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Election not found"));

    long totalVotes = voteRepository.countByElection_Id(
            election.getId());

    if (totalVotes == 0) {
        throw new IllegalStateException(
                "No votes cast for this election");
    }

    List<Candidate> candidates =
            candidateRepository
                    .findByElectionIdOrderByVoteCountDesc(
                            election.getId());

    if (candidates.isEmpty()) {
        throw new ResourceNotFoundException(
                "No candidates available");
    }

    Candidate winner = candidates.get(0);

    ElectionResult result =
            electionResultRepository
                    .findByElection_Id(election.getId())
                    .orElse(new ElectionResult());

    result.setElection(election);
    result.setWinner(winner);
    result.setTotalVotes((int) totalVotes);

    return electionResultRepository.save(result);
}

public List<ElectionResult> getAllResults() {
    return electionResultRepository.findAll();
}
```

}
