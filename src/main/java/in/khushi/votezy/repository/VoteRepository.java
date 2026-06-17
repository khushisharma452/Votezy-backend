package in.khushi.votezy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.khushi.votezy.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    boolean existsByVoter_IdAndElection_Id(
            Long voterId,
            Long electionId);

    long countByElection_Id(Long electionId);
}