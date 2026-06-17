package in.khushi.votezy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.khushi.votezy.entity.Election;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {

    Optional<Election> findByElectionName(String electionName);

}