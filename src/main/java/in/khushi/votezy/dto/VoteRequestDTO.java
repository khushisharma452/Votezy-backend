package in.khushi.votezy.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteRequestDTO {

    @NotNull(message="Voter ID is required")
    private Long voterId;

    @NotNull(message="Candidate ID is required")
    private Long candidateId;

    @NotNull(message="Election ID is required")
    private Long electionId;
}