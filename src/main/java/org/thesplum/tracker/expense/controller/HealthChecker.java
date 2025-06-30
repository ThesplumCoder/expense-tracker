package org.thesplum.tracker.expense.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthChecker {

    @GetMapping
    public ResponseEntity<String> serverHealth() {
        return ResponseEntity.ok("The server is up");
    }
}
