package in.khushi.votezy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CandidateRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String party;

    @NotNull
    private Long electionId;
}