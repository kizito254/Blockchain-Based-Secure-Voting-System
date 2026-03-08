package com.voting.blockchain;

import com.voting.vote.VoteResult;

import java.util.List;

public interface BlockchainGateway {
    String writeVote(String electionId, String candidateId);

    List<VoteResult> tallyVotes(String electionId);
}
