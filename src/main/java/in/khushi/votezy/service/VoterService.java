package in.khushi.votezy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.khushi.votezy.entity.Candidate;
import in.khushi.votezy.entity.Vote;
import in.khushi.votezy.entity.Voter;
import in.khushi.votezy.exception.DuplicateResourceException;
import in.khushi.votezy.exception.ResourceNotFoundException;
import in.khushi.votezy.repository.CandidateRepository;
import in.khushi.votezy.repository.VoterRepository;
import jakarta.transaction.Transactional;

@Service
public class VoterService {

    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;

    @Autowired
    public VoterService(
            VoterRepository voterRepository,
            CandidateRepository candidateRepository) {

        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
    }

    public Voter registerVoter(Voter voter) {

        if (voterRepository.existsByEmail(voter.getEmail())) {
            throw new DuplicateResourceException(
                    "Voter with email id: "
                            + voter.getEmail()
                            + " already exists");
        }

        return voterRepository.save(voter);
    }

    public List<Voter> getAllVoters() {
        return voterRepository.findAll();
    }

    public Voter getVoterById(Long id) {

        return voterRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Voter with id: "
                                        + id
                                        + " not found"));
    }

    public Voter updateVoter(
            Long id,
            Voter updatedVoter) {

        Voter voter = voterRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Voter with id: "
                                        + id
                                        + " not found"));

        if (updatedVoter.getName() != null) {
            voter.setName(updatedVoter.getName());
        }

        if (updatedVoter.getEmail() != null) {
            voter.setEmail(updatedVoter.getEmail());
        }

        return voterRepository.save(voter);
    }

    @Transactional
    public void deleteVoter(Long id) {

        Voter voter = voterRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cannot delete voter with id: "
                                        + id));

        if (voter.getVotes() != null) {

            for (Vote vote : voter.getVotes()) {

                Candidate candidate = vote.getCandidate();

                if (candidate != null) {

                    candidate.setVoteCount(
                            Math.max(
                                    candidate.getVoteCount() - 1,
                                    0));

                    candidateRepository.save(candidate);
                }
            }
        }

        voterRepository.delete(voter);
    }
}
