package in.khushi.votezy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.khushi.votezy.entity.Election;
import in.khushi.votezy.repository.ElectionRepository;

@Service
public class ElectionService {

    private ElectionRepository electionRepository;

    public ElectionService(
            ElectionRepository electionRepository) {

        this.electionRepository = electionRepository;
    }

    public Election addElection(Election election) {

        return electionRepository.save(election);
    }

    public List<Election> getAllElections() {

        return electionRepository.findAll();
    }
}