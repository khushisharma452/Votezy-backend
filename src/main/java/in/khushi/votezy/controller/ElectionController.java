package in.khushi.votezy.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.khushi.votezy.entity.Election;
import in.khushi.votezy.service.ElectionService;

@RestController
@RequestMapping("/api/elections")
@CrossOrigin
public class ElectionController {

    private ElectionService electionService;

    public ElectionController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @PostMapping("/add")
    public ResponseEntity<Election> addElection(
            @RequestBody Election election) {

        return new ResponseEntity<>(
                electionService.addElection(election),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Election>> getAllElections() {

        return ResponseEntity.ok(
                electionService.getAllElections());
    }
}