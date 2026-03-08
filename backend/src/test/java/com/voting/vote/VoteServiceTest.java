package com.voting.vote;

import com.voting.blockchain.InMemoryBlockchainGateway;
import com.voting.common.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoteServiceTest {

    private VoteService voteService;

    @BeforeEach
    void setUp() {
        voteService = new VoteService(new InMemoryBlockchainGateway());
    }

    @Test
    void shouldRejectDuplicateVoteForSameElection() {
        VoteRequest first = new VoteRequest("voter-1", "election-2026", "candidate-a", "token");
        VoteRequest duplicate = new VoteRequest("voter-1", "election-2026", "candidate-b", "token");

        voteService.castVote(first);

        DomainException ex = assertThrows(DomainException.class, () -> voteService.castVote(duplicate));
        assertEquals("Voter has already voted in this election.", ex.getMessage());
    }
}
