package com.voting.vote;

import jakarta.validation.constraints.NotBlank;

public record VoteRequest(
        @NotBlank String voterId,
        @NotBlank String electionId,
        @NotBlank String candidateId,
        @NotBlank String accessToken
) {
}
