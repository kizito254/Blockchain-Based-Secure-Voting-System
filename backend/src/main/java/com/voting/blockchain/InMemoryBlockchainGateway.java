package com.voting.blockchain;

import com.voting.vote.VoteResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryBlockchainGateway implements BlockchainGateway {

    private final Map<String, Map<String, Integer>> chain = new ConcurrentHashMap<>();

    @Override
    public String writeVote(String electionId, String candidateId) {
        chain.computeIfAbsent(electionId, key -> new HashMap<>())
                .merge(candidateId, 1, Integer::sum);
        return "tx-" + UUID.randomUUID();
    }

    @Override
    public List<VoteResult> tallyVotes(String electionId) {
        Map<String, Integer> electionVotes = chain.getOrDefault(electionId, Map.of());
        List<VoteResult> results = new ArrayList<>();
        electionVotes.forEach((candidate, count) -> results.add(new VoteResult(candidate, count)));
        results.sort(Comparator.comparing(VoteResult::count).reversed());
        return results;
    }
}
