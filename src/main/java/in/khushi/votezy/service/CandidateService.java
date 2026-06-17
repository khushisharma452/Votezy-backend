package in.khushi.votezy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.khushi.votezy.entity.Candidate;
import in.khushi.votezy.entity.Vote;
import in.khushi.votezy.exception.ResourceNotFoundException;
import in.khushi.votezy.repository.CandidateRepository;
import in.khushi.votezy.dto.CandidateRequestDTO;
import in.khushi.votezy.entity.Election;
import in.khushi.votezy.repository.ElectionRepository;

@Service

public class CandidateService {
	private CandidateRepository candidateRepository;
	private ElectionRepository electionRepository;
    @Autowired
    public CandidateService(
            CandidateRepository candidateRepository,
            ElectionRepository electionRepository) {

        this.candidateRepository = candidateRepository;
        this.electionRepository = electionRepository;
    }
    public Candidate addCandidate(CandidateRequestDTO request) {

        Election election = electionRepository
                .findById(request.getElectionId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Election not found"));

        Candidate candidate = new Candidate();

        candidate.setName(request.getName());
        candidate.setParty(request.getParty());
        candidate.setElection(election);

        return candidateRepository.save(candidate);
    }
    public List<Candidate>getAllCandidates(){
    	return candidateRepository.findAll();
    	}
    public Candidate getCandidateById(Long id) {
    	Candidate candidate=candidateRepository.findById(id).orElse(null);
    	if(candidate==null) {
    		throw new ResourceNotFoundException("Candidate with id:"+id+" not found");
    	}
    	return candidate;
    }
    public Candidate updateCandidate(Long id,Candidate updatedCandidate) {
    	Candidate candidate=getCandidateById(id);
    	if(updatedCandidate.getName()!=null) {
    		candidate.setName(updatedCandidate.getName());
    	}
    	if(updatedCandidate.getParty()!=null) {
    		candidate.setParty(updatedCandidate.getParty());
    	}
    	return candidateRepository.save(candidate);
    }
    public void deleteCandidate(Long id) {
    	Candidate candidate=getCandidateById(id);
    	List<Vote>votes=candidate.getVote();
    	for(Vote v:votes) {
    		v.setCandidate(null);
    	}
    	candidate.getVote().clear();
    	candidateRepository.delete(candidate);
    }

}
