package in.khushi.votezy.service;

import java.util.List;
import java.util.Optional;

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

    private CandidateRepository candidateRepository;
    private ElectionResultRepository electionResultRepository;
    private VoteRepository voteRepository;
    private ElectionRepository electionRepository;

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

        Optional<ElectionResult> existingResult =
                electionResultRepository
                        .findByElection_Id(
                                election.getId());

        if (existingResult.isPresent()) {
            return existingResult.get();
        }

        long voteCount =
                voteRepository.countByElection_Id(
                        election.getId());

        if (voteCount == 0) {
            throw new IllegalStateException(
                    "No votes cast for this election");
        }

        List<Candidate> allCandidates =
                candidateRepository
                        .findByElectionIdOrderByVoteCountDesc(
                                election.getId());

        if (allCandidates.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No candidates available");
        }

        Candidate winner = allCandidates.get(0);

        int totalVotes = 0;

        for (Candidate candidate : allCandidates) {
            totalVotes += candidate.getVoteCount();
        }

        ElectionResult result = new ElectionResult();

        result.setElection(election);
        result.setWinner(winner);
        result.setTotalVotes(totalVotes);

        return electionResultRepository.save(result);
    }

    public List<ElectionResult> getAllResults() {
        return electionResultRepository.findAll();
    }
}