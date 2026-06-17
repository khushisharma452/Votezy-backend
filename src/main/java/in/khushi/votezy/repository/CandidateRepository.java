package in.khushi.votezy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.khushi.votezy.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findByElectionId(Long electionId);

    List<Candidate> findByElectionIdOrderByVoteCountDesc(Long electionId);
}