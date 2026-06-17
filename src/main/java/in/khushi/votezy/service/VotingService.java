package in.khushi.votezy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.khushi.votezy.entity.Candidate;
import in.khushi.votezy.entity.Vote;
import in.khushi.votezy.entity.Voter;
import in.khushi.votezy.exception.ResourceNotFoundException;
import in.khushi.votezy.exception.VoteNotAllowedException;
import in.khushi.votezy.repository.CandidateRepository;
import in.khushi.votezy.repository.VoteRepository;
import in.khushi.votezy.repository.VoterRepository;
import jakarta.transaction.Transactional;
import in.khushi.votezy.entity.Election;
import in.khushi.votezy.repository.ElectionRepository;

@Service
public class VotingService {
       private VoteRepository voteRepository;
       private CandidateRepository candidateRepository;
       private VoterRepository voterRepository;
       private ElectionRepository electionRepository;
       public VotingService(
    	        VoteRepository voteRepository,
    	        CandidateRepository candidateRepository,
    	        VoterRepository voterRepository,
    	        ElectionRepository electionRepository) {

    	    this.voteRepository = voteRepository;
    	    this.candidateRepository = candidateRepository;
    	    this.voterRepository = voterRepository;
    	    this.electionRepository = electionRepository;
    	}
       @Transactional
       public Vote castVote(Long voterId,
                            Long candidateId,
                            Long electionId) {

           Voter voter = voterRepository.findById(voterId)
                   .orElseThrow(() ->
                           new ResourceNotFoundException(
                                   "Voter not found"));

           Candidate candidate = candidateRepository.findById(candidateId)
                   .orElseThrow(() ->
                           new ResourceNotFoundException(
                                   "Candidate not found"));

           Election election = electionRepository.findById(electionId)
                   .orElseThrow(() ->
                           new ResourceNotFoundException(
                                   "Election not found"));

           // Candidate must belong to election
           if (!candidate.getElection()
                   .getId()
                   .equals(electionId)) {

               throw new VoteNotAllowedException(
                       "Candidate does not belong to selected election");
           }

           // Check duplicate vote in same election
           if (voteRepository.existsByVoter_IdAndElection_Id(
        	        voterId,
        	        electionId)) {

               throw new VoteNotAllowedException(
                       "Voter has already voted in this election");
           }

           Vote vote = new Vote();

           vote.setVoter(voter);
           vote.setCandidate(candidate);
           vote.setElection(election);

           voteRepository.save(vote);

           candidate.setVoteCount(
                   candidate.getVoteCount() + 1);

           candidateRepository.save(candidate);

           return vote;
       
	   }
	   public List<Vote> getAllVotes(){
		   return voteRepository.findAll();
	   }
	   

}

