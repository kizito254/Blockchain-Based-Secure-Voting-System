package com.voting.vote;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
@Validated
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<VoteReceipt> castVote(@Valid @RequestBody VoteRequest request) {
        return ResponseEntity.ok(voteService.castVote(request));
    }

    @GetMapping("/results")
    public ResponseEntity<List<VoteResult>> getResults(@RequestParam @NotBlank String electionId) {
        return ResponseEntity.ok(voteService.tally(electionId));
    }
}
