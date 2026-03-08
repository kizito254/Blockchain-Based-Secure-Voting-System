package com.voting.vote;

import com.voting.blockchain.BlockchainGateway;
import com.voting.common.DomainException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VoteService {

    private final BlockchainGateway blockchainGateway;
    private final Set<String> voterRegistry = ConcurrentHashMap.newKeySet();

    public VoteService(BlockchainGateway blockchainGateway) {
        this.blockchainGateway = blockchainGateway;
    }

    public VoteReceipt castVote(VoteRequest request) {
        String dedupeKey = request.electionId() + ":" + request.voterId();
        if (!voterRegistry.add(dedupeKey)) {
            throw new DomainException("Voter has already voted in this election.");
        }

        String txId = blockchainGateway.writeVote(request.electionId(), request.candidateId());
        return new VoteReceipt(txId, request.electionId(), request.candidateId(), "RECORDED_ON_CHAIN");
    }

    public List<VoteResult> tally(String electionId) {
        return blockchainGateway.tallyVotes(electionId);
    }
}
