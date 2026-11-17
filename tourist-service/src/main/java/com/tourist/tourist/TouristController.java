package com.tourist.tourist;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/tourists")
public class TouristController {
    private final TouristRepository repository;
    public TouristController(TouristRepository repository) { this.repository = repository; }

    @GetMapping
    public List<Tourist> getAllTourists() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Tourist getTouristById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Tourist not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tourist addTourist(@Valid @RequestBody TouristRequest request) {
        return repository.save(new Tourist(request.fullName(), request.email(),
                request.country(), request.destination()));
    }

    @PutMapping("/{id}")
    public Tourist updateTourist(@PathVariable Long id, @Valid @RequestBody TouristRequest request) {
        Tourist tourist = getTouristById(id);
        tourist.setFullName(request.fullName());
        tourist.setEmail(request.email());
        tourist.setCountry(request.country());
        tourist.setDestination(request.destination());
        return repository.save(tourist);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTourist(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tourist not found");
        }
        repository.deleteById(id);
    }

    public record TouristRequest(@NotBlank String fullName, @Email @NotBlank String email,
                                 @NotBlank String country, @NotBlank String destination) {}
}
