package com.voting.vote;

public record VoteReceipt(String txId, String electionId, String candidateId, String status) {
}
