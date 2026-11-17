package com.tourist.tourist;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TouristDataLoader implements CommandLineRunner {
    private final TouristRepository repository;
    public TouristDataLoader(TouristRepository repository) { this.repository = repository; }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.save(new Tourist("Aarav Sharma", "aarav@example.com", "India", "Taj Mahal"));
            repository.save(new Tourist("Emily Johnson", "emily@example.com", "USA", "Goa"));
            repository.save(new Tourist("Chen Wei", "chen@example.com", "China", "Kerala"));
        }
    }
}
